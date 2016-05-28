package com.baidu.im.inapp.transaction.message.processor;

import android.text.TextUtils;

import com.baidu.im.frame.MessageResponser;
import com.baidu.im.frame.Processor;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.ProcessorStart;
import com.baidu.im.frame.ProcessorTimeCallback;
import com.baidu.im.frame.RootProcessor;
import com.baidu.im.frame.TransactionLog;
import com.baidu.im.frame.inapp.InAppBaseProcessor;
import com.baidu.im.frame.inappCallback.SendMessageCallback;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.outapp.network.ProtocolConverter;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.baidu.im.sdk.BinaryMessage;
import com.google.protobuf.micro.ByteStringMicro;

public class SendMessageProsessor implements MessageResponser, ProcessorStart, ProcessorTimeCallback {

    public static final String TAG = "SendMessage";

    @Override
    public String getProcessorName() {
        return TAG;
    }

    private BinaryMessage message;
    private String sessionId;
    private Processor processor = new InAppBaseProcessor(this);
    private SendMessageCallback mCallback = null;
    private TransactionLog mTransactionLog = null;
    private RootProcessor mRootProcessor = null;

    public SendMessageProsessor(BinaryMessage message, String sessionId, PreferenceUtil pref,
            SendMessageCallback callback, TransactionLog transactionLog) {
        this.message = message;
        this.sessionId = sessionId;
        mCallback = callback;
        mTransactionLog = transactionLog;
        mRootProcessor = new RootProcessor(this, this);
    }

    @Override
    public ProcessorResult startWorkFlow() {
        ProcessorResult processorResult = null;
        if (mTransactionLog != null) {
            mTransactionLog.startProcessor(this.getProcessorName());
        }
        com.baidu.im.frame.pb.ObjUpPacket.UpPacket upPacketBuilder = new  UpPacket();  // migrate from builder
        if (!TextUtils.isEmpty(message.getServiceName()) && !TextUtils.isEmpty(message.getMethodName())) {
            upPacketBuilder.setServiceName(message.getServiceName());
            upPacketBuilder.setMethodName(message.getMethodName());
        } else {
            upPacketBuilder.setServiceName(ServiceNameEnum.CoreMsg.name());
            upPacketBuilder.setMethodName(MethodNameEnum.SendData.name());
        }

        if (!TextUtils.isEmpty(sessionId)) {
            upPacketBuilder.setSessionId(sessionId);

            mRootProcessor.startCountDown();
            UpPacket upPacket =
                    ProtocolConverter.convertUpPacket(ByteStringMicro.copyFrom(message.getData()), true, false, false,
                            upPacketBuilder);

            // 发包并等待, 如果超过重试次数没有回包则处理失败。
            if (!processor.send(upPacket)) {
            	
                processorResult = new ProcessorResult(ProcessorCode.SERVER_ERROR);
            } else {
                processorResult = new ProcessorResult(ProcessorCode.SUCCESS);
            }
        } else {
            LogUtil.printMainProcess("Send message error. Can not get seesionId.");
            processorResult = new ProcessorResult(ProcessorCode.NO_SESSION_ID_FAILURE);
        }
        if (processorResult.getProcessorCode() != ProcessorCode.SUCCESS) {
            if (mTransactionLog != null) {
                mTransactionLog.endProcessor(getProcessorName(), 0, processorResult);
            }
            if (mCallback != null) {
                mCallback.SendMessageCallbackResult(processorResult);
            }
        }
        return processorResult;
    }

    public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {
        mRootProcessor.stopCountDown();
        ProcessorResult processorResult = null;
        if (downPacket != null) {
            LogUtil.printMainProcess("Send message callback is called");
            processorResult =
                    new ProcessorResult(BizCodeProcessUtil.procProcessorCode(getProcessorName(), downPacket),
                            downPacket.getBizPackage().getBizData().toByteArray());
        } else {
            processorResult = new ProcessorResult(ProcessorCode.SERVER_ERROR);
        }
        if (mTransactionLog != null) {
            mTransactionLog.endProcessor(getProcessorName(), null != downPacket ? downPacket.getSeq() : 0,
                    processorResult);
        }
        if (mCallback != null) {
            mCallback.SendMessageCallbackResult(processorResult);
        }
        return processorResult;
    }

    @Override
    public void processorTimeout() {
        processor.sendReconnect();
        ProcessorResult processorResult = new ProcessorResult(ProcessorCode.SEND_TIME_OUT);
        if (mTransactionLog != null) {
            mTransactionLog.endProcessor(getProcessorName(), 0, processorResult);
        }
        if (mCallback != null) {
            mCallback.SendMessageCallbackResult(processorResult);
        }
    }
}
