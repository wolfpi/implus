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
import com.baidu.im.frame.inappCallback.SetAppStatusCallback;
import com.baidu.im.frame.pb.EnumAppStatus;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.pb.ProSetAppStatus.SetAppStatusReq;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.outapp.network.ProtocolConverter;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.google.protobuf.micro.ByteStringMicro;

public class SetAppStatusProsessor implements MessageResponser, ProcessorStart, ProcessorTimeCallback {

    public static final String TAG = "SetAppStatus";
    private Processor mProcessor = new InAppBaseProcessor(this);
    // private PreferenceUtil mPref = null;
    private SetAppStatusCallback mCallback = null;
    private TransactionLog mTransactionLog = null;
    private RootProcessor mRootProcessor = null;

    @Override
    public String getProcessorName() {
        return TAG;
    }

    private int eAppStatus;

    public SetAppStatusProsessor(int eAppStatus, PreferenceUtil pref, SetAppStatusCallback callback,
            TransactionLog transactionLog) {
        this.eAppStatus = eAppStatus;
        // mPref = pref;
        mCallback = callback;
        mTransactionLog = transactionLog;
        mRootProcessor = new RootProcessor(this, this);
    }

    @Override
    public ProcessorResult startWorkFlow() {
        ProcessorResult processorResult;
        if (mTransactionLog != null) {
            mTransactionLog.startProcessor(getProcessorName());
        }
        if (eAppStatus != -1) {

            mRootProcessor.startCountDown();
            LogUtil.printMainProcess("SetAppStatus start" + eAppStatus);

            SetAppStatusReq setAppStatusReqBuilder = new  SetAppStatusReq();  // migrate from builder
            setAppStatusReqBuilder.setStatus(eAppStatus);
            setAppStatusReqBuilder.addAppIds(InAppApplication.getInstance().getSession().getApp().getAppId());
            setAppStatusReqBuilder.setChannelKey(InAppApplication.getInstance().getSession().getChannel()
                    .getChannelKey());

            UpPacket upPacketBuilder = new  UpPacket();  // migrate from builder
            upPacketBuilder.setServiceName(ServiceNameEnum.CoreSession.name());
            upPacketBuilder.setMethodName(MethodNameEnum.SetAppStatus.name());

            UpPacket upPacket =
                    ProtocolConverter.convertUpPacket(ByteStringMicro.copyFrom(setAppStatusReqBuilder.toByteArray()),
                            upPacketBuilder);

            // 发包并等待, 如果超过重试次数没有回包则处理失败。
            if (!mProcessor.send(upPacket)) {
                processorResult = new ProcessorResult(ProcessorCode.SERVER_ERROR);
            } else {
                processorResult = new ProcessorResult(ProcessorCode.SUCCESS);
            }
        } else {
            LogUtil.printMainProcess("SetAppStatus, param error: " + eAppStatus);
            processorResult = new ProcessorResult(ProcessorCode.NO_APP_STATUS);
        }
        if (processorResult.getProcessorCode() == ProcessorCode.SUCCESS) {
            if (null != mTransactionLog) {
                mTransactionLog.endProcessor(getProcessorName(), 0, processorResult);
            }
            if (null != mCallback) {
                mCallback.SetAppStatucCallbackResult(processorResult);
            }
        }
        return processorResult;
    }

    @Override
    public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {
        mRootProcessor.stopCountDown();
        ProcessorResult processorResult;
        if (downPacket != null) {
            ProcessorCode result = BizCodeProcessUtil.procProcessorCode(getProcessorName(), downPacket);

            if (result.getCode() == 0) {
                // InAppApplication.getInstance().getSession()..setAppStatus(eAppStatus);
                LogUtil.printMainProcess("SetAppStatus success, appstatus = " + eAppStatus);
            }
            processorResult = new ProcessorResult(result);
        } else {
            processorResult = new ProcessorResult(ProcessorCode.SERVER_ERROR);
        }
        if (mTransactionLog != null) {
            mTransactionLog.endProcessor(getProcessorName(), null != downPacket ? downPacket.getSeq() : 0,
                    processorResult);
        }
        if (mCallback != null) {
            mCallback.SetAppStatucCallbackResult(processorResult);
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
            mCallback.SetAppStatucCallbackResult(new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
        }
    }
}
