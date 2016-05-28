package com.baidu.im.inapp.transaction.session.processor;

import com.baidu.im.frame.MessageResponser;
import com.baidu.im.frame.Processor;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.ProcessorStart;
import com.baidu.im.frame.ProcessorTimeCallback;
import com.baidu.im.frame.RootProcessor;
import com.baidu.im.frame.TransactionLog;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.inapp.InAppBaseProcessor;
import com.baidu.im.frame.inappCallback.UnRegAppCallback;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.pb.ProUnregApp.UnRegAppReq;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.outapp.network.ProtocolConverter;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.google.protobuf.micro.ByteStringMicro;

public class UnRegAppProsessor implements MessageResponser, ProcessorStart, ProcessorTimeCallback {

    public static final String TAG = "UnRegApp";
    private Processor mProcessor = new InAppBaseProcessor(this);
    // private PreferenceUtil mPref = null;
    private UnRegAppCallback mCallback = null;
    private TransactionLog mTransactionLog = null;
    private RootProcessor mRootProcessor = null;

    public UnRegAppProsessor(PreferenceUtil pref, UnRegAppCallback callback, TransactionLog transactionLog) {
        // mPref = pref;
        mCallback = callback;
        mTransactionLog = transactionLog;
        mRootProcessor = new RootProcessor(this, this);
    }

    @Override
    public String getProcessorName() {
        return TAG;
    }

    @Override
    public ProcessorResult startWorkFlow() {
        ProcessorResult processorResult = null;
        if (mTransactionLog != null) {
            mTransactionLog.startProcessor(getProcessorName());
        }
        // 发包
        LogUtil.printMainProcess("unRegApp start");
        if (InAppApplication.getInstance().getSession().getApp().getAppId() != 0) {

            mRootProcessor.startCountDown();
            UnRegAppReq unRegAppReqBuilder = new  UnRegAppReq();  // migrate from builder
            UnRegAppReq unRegAppReq = unRegAppReqBuilder;

            UpPacket upPacketBuilder = new  UpPacket();  // migrate from builder
            upPacketBuilder.setServiceName(ServiceNameEnum.CoreSession.name());
            upPacketBuilder.setMethodName(MethodNameEnum.UnRegApp.name());

            UpPacket upPacket = ProtocolConverter.convertUpPacket(ByteStringMicro.copyFrom(unRegAppReq.toByteArray()), upPacketBuilder);

            // 发包并等待, 如果超过重试次数没有回包则处理失败。
            if (!mProcessor.send(upPacket)) {
                processorResult = new ProcessorResult(ProcessorCode.SERVER_ERROR);
            } else {
                processorResult = new ProcessorResult(ProcessorCode.SUCCESS);
            }

        } else {
            LogUtil.printMainProcess("unRegApp error,  appid = 0");
            processorResult = new ProcessorResult(ProcessorCode.NO_APP_ID);
        }
        if (processorResult.getProcessorCode() != ProcessorCode.SUCCESS) {
            if (mTransactionLog != null) {
                mTransactionLog.endProcessor(getProcessorName(), 0, processorResult);
            }
            if (mCallback != null) {
                mCallback.UnRegAppCallbackResult(processorResult);
            }
        }
        return processorResult;
    }

    @Override
    public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {
        ProcessorResult processorResult = null;
        mRootProcessor.stopCountDown();
        ProcessorCode result = ProcessorCode.SERVER_ERROR;
        if (downPacket != null) {
            result = BizCodeProcessUtil.procProcessorCode(getProcessorName(), downPacket);
            if (result.getCode() == 0) {
                InAppApplication.getInstance().getSession().unregAppSuccess();
                LogUtil.printMainProcess("UnRegApp success, remove app id");
            }
            processorResult = new ProcessorResult(result);
        }
        if (mTransactionLog != null) {
            mTransactionLog.endProcessor(getProcessorName(), null != downPacket ? downPacket.getSeq() : 0,
                    processorResult);
        }
        if (mCallback != null) {
            mCallback.UnRegAppCallbackResult(new ProcessorResult(result));
        }
        return processorResult;
    }

    @Override
    public void processorTimeout() {
        mProcessor.sendReconnect();
        if (mTransactionLog != null) {
            mTransactionLog.endProcessor(getProcessorName(), 0, new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
        }
        if (mCallback != null) {
            mCallback.UnRegAppCallbackResult(new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
        }
    }
}
