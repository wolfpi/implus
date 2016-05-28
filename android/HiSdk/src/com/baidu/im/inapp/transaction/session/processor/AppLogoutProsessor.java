//package com.baidu.im.inapp.transaction.session.processor;
//
//import android.text.TextUtils;
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
//import com.baidu.im.frame.inappCallback.AppLogoutCallback;
//import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
//import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
//import com.baidu.im.frame.pb.ProAppLogout.AppLogoutReq;
//import com.baidu.im.frame.utils.BizCodeProcessUtil;
//import com.baidu.im.frame.utils.LogUtil;
//import com.baidu.im.frame.utils.PreferenceUtil;
//import com.baidu.im.outapp.network.ProtocolConverter;
//import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
//import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
//import com.google.protobuf.micro.ByteStringMicro;
//
//public class AppLogoutProsessor implements MessageResponser, ProcessorStart, ProcessorTimeCallback {
//
//    public static final String TAG = "AppLogout";
//    private Processor mProcessor = new InAppBaseProcessor(this);
//    private PreferenceUtil mPref = null;
//    private AppLogoutCallback mCallback = null;
//    private TransactionLog mTransactionLog = null;
//    private RootProcessor mRootProcessor = null;
//
//    public AppLogoutProsessor(PreferenceUtil pref, AppLogoutCallback callback) {
//        mPref = pref;
//        mCallback = callback;
//        mRootProcessor = new RootProcessor(this, this);
//    }
//
//    @Override
//    public String getProcessorName() {
//        return TAG;
//    }
//
//    @Override
//    public ProcessorResult startWorkFlow() {
//        LogUtil.printMainProcess("AppLogout start");
//        ProcessorResult processorResult = null;
//        if (null != mTransactionLog) {
//            mTransactionLog.startProcessor(getProcessorName());
//        }
//
//        UpPacket upPacketBuilder = new  UpPacket();  // migrate from builder
//        String sessionId = InAppApplication.getInstance().getSession().getSessionInfo().getSessionId();
//        if (!TextUtils.isEmpty(sessionId)) {
//            upPacketBuilder.setSessionId(sessionId);
//
//            mRootProcessor.startCountDown();
//            upPacketBuilder.setServiceName(ServiceNameEnum.CoreSession.name());
//            upPacketBuilder.setMethodName(MethodNameEnum.AppLogout.name());
//
//            AppLogoutReq appLogoutReqBuilder = new  AppLogoutReq();  // migrate from builder
//            AppLogoutReq appLogoutReq = appLogoutReqBuilder;
//
//            UpPacket upPacket = ProtocolConverter.convertUpPacket(ByteStringMicro.copyFrom(appLogoutReq.toByteArray()), upPacketBuilder);
//
//            // 发包并等待, 如果超过重试次数没有回包则处理失败。
//            if (!mProcessor.send(upPacket)) {
//                processorResult = new ProcessorResult(ProcessorCode.SERVER_ERROR);
//            } else {
//                processorResult = new ProcessorResult(ProcessorCode.SUCCESS);
//            }
//        } else {
//            LogUtil.printMainProcess("User logout success. Can not get seesionId.");
//            processorResult = new ProcessorResult(ProcessorCode.NO_SESSION_ID);
//        }
//        if (processorResult.getProcessorCode() != ProcessorCode.SUCCESS) {
//            if (mTransactionLog != null) {
//                mTransactionLog.endProcessor(getProcessorName(), 0, processorResult);
//            }
//            if (mCallback != null) {
//                mCallback.AppLogoutCallbackResult(processorResult);
//            }
//        }
//        return processorResult;
//    }
//
//    @Override
//    public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {
//        mRootProcessor.stopCountDown();
//        ProcessorResult result = null;
//        if (downPacket != null) {
//            ProcessorCode processorCode = BizCodeProcessUtil.procProcessorCode(getProcessorName(), downPacket);
//            if (processorCode.getCode() == 0) {
//                InAppApplication.getInstance().getSession().appLogoutSuccess();
//                LogUtil.printMainProcess("AppLogout success");
//            }
//            result = new ProcessorResult(processorCode);
//        } else {
//            result = new ProcessorResult(ProcessorCode.SERVER_ERROR);
//        }
//        if (mTransactionLog != null) {
//            mTransactionLog.endProcessor(getProcessorName(), null != downPacket ? downPacket.getSeq() : 0, result);
//        }
//        if (mCallback != null) {
//            mCallback.AppLogoutCallbackResult(result);
//        }
//        return result;
//    }
//
//    @Override
//    public void processorTimeout() {
//        mProcessor.sendReconnect();
//
//        if (mTransactionLog != null) {
//            mTransactionLog.endProcessor(getProcessorName(), 0, new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
//        }
//        if (mCallback != null) {
//            mCallback.AppLogoutCallbackResult(new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
//        }
//
//    }
//}
