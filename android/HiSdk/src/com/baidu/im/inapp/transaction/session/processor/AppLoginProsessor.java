//package com.baidu.im.inapp.transaction.session.processor;
//
//import com.baidu.im.frame.MessageResponser;
//import com.baidu.im.frame.Processor;
//import com.baidu.im.frame.ProcessorCode;
//import com.baidu.im.frame.ProcessorResult;
//import com.baidu.im.frame.ProcessorStart;
//import com.baidu.im.frame.ProcessorTimeCallback;
//import com.baidu.im.frame.RootProcessor;
//import com.baidu.im.frame.TransactionLog;
//import com.baidu.im.frame.inapp.InAppApplication;
//import com.baidu.im.frame.inapp.InAppBaseProcessor;
//import com.baidu.im.frame.inappCallback.AppLoginCallback;
//import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
//import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
//import com.baidu.im.frame.pb.ProAppLogin.AppLoginReq;
//import com.baidu.im.frame.utils.BizCodeProcessUtil;
//import com.baidu.im.frame.utils.LogUtil;
//import com.baidu.im.frame.utils.PreferenceUtil;
//import com.baidu.im.frame.utils.StringUtil;
//import com.baidu.im.outapp.network.ProtocolConverter;
//import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
//import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
//import com.google.protobuf.micro.ByteStringMicro;
//
//public class AppLoginProsessor implements MessageResponser, ProcessorStart, ProcessorTimeCallback {
//
//    public static final String TAG = "AppLogin";
//    private Processor mProcessor = new InAppBaseProcessor(this);
//    private PreferenceUtil mPref = null;
//    private AppLoginCallback mCallback = null;
//    private TransactionLog mTransactionLog = null;
//    private RootProcessor mRootProcessor = null;
//
//    public AppLoginProsessor(PreferenceUtil pref, AppLoginCallback callback, TransactionLog transactionLog) {
//        mPref = pref;
//        mCallback = callback;
//        mTransactionLog = transactionLog;
//        mRootProcessor = new RootProcessor(this, this);
//    }
//
//    @Override
//    public String getProcessorName() {
//        return TAG;
//    }
//
//    public ProcessorResult startWorkFlow() {
//        ProcessorResult processorResult = null;
//        if (null != mTransactionLog) {
//            mTransactionLog.startProcessor(getProcessorName());
//        }
//        LogUtil.printMainProcess("AppLogin start");
//
//        String Channelkey = InAppApplication.getInstance().getSession().getChannel().getChannelKey();
//
//        if (!StringUtil.isStringInValid(Channelkey)) {
//            mRootProcessor.startCountDown();
//
//            AppLoginReq appLoginReqBuilder = new AppLoginReq();  // migrate from builder
//            appLoginReqBuilder.setChannelKey(InAppApplication.getInstance().getSession().getChannel().getChannelKey());
//            AppLoginReq appLoginReq = appLoginReqBuilder;
//
//            com.baidu.im.frame.pb.ObjUpPacket.UpPacket upPacketBuilder = new UpPacket();  // migrate from builder
//            upPacketBuilder.setServiceName(ServiceNameEnum.CoreSession.name());
//            upPacketBuilder.setMethodName(MethodNameEnum.AppLogin.name());
//
//            UpPacket upPacket = ProtocolConverter
//                    .convertUpPacket(ByteStringMicro.copyFrom(appLoginReq.toByteArray()), upPacketBuilder);
//
//            // 发包并等待, 如果超过重试次数没有回包则处理失败。
//            if (!mProcessor.send(upPacket)) {
//                processorResult = new ProcessorResult(ProcessorCode.SERVER_ERROR);
//            } else {
//                processorResult = new ProcessorResult(ProcessorCode.SUCCESS);
//            }
//        } else {
//            processorResult = new ProcessorResult(ProcessorCode.NO_CHANNEL_KEY);
//        }
//        if (processorResult.getProcessorCode() != ProcessorCode.SUCCESS) {
//            if (mTransactionLog != null) {
//                mTransactionLog.endProcessor(getProcessorName(), 0, processorResult);
//            }
//            if (mCallback != null) {
//                mCallback.appLoginCallbackResult(processorResult);
//            }
//        }
//        return processorResult;
//    }
//
//    public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {
//        ProcessorResult processorResult = null;
//        mRootProcessor.stopCountDown();
//        if (downPacket != null) {
//            ProcessorCode result = BizCodeProcessUtil.procProcessorCode(getProcessorName(), downPacket);
//            processorResult = new ProcessorResult(result);
//            if (result.getCode() == 0) {
//                InAppApplication.getInstance().getSession().appLoginSuccess(downPacket.getSessionId());
//                LogUtil.printMainProcess("AppLogin success, sessionId=" + downPacket.getSessionId());
//            } else {
//                InAppApplication.getInstance().getSession().appLoginFailed();
//            }
//        } else {
//            processorResult = new ProcessorResult(ProcessorCode.SERVER_ERROR);
//        }
//        if (mTransactionLog != null) {
//            mTransactionLog.endProcessor(getProcessorName(), null != downPacket ? downPacket.getSeq() : 0,
//                    processorResult);
//        }
//        if (mCallback != null) {
//            mCallback.appLoginCallbackResult(processorResult);
//        }
//        return processorResult;
//    }
//
//    @Override
//    public void processorTimeout() {
//        mProcessor.sendReconnect();
//        ProcessorResult processorResult = new ProcessorResult(ProcessorCode.SEND_TIME_OUT);
//        if (mTransactionLog != null) {
//            mTransactionLog.endProcessor(getProcessorName(), 0, processorResult);
//        }
//        if (mCallback != null) {
//            mCallback.appLoginCallbackResult(processorResult);
//        }
//    }
//}
