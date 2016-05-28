//package com.baidu.im.inapp.transaction.session;
//
//import com.baidu.im.frame.BizBaseTransaction;
//import com.baidu.im.frame.ITransResend;
//import com.baidu.im.frame.ITransactionTimeoutCallback;
//import com.baidu.im.frame.ProcessorCode;
//import com.baidu.im.frame.ProcessorResult;
//import com.baidu.im.frame.inappCallback.AppLoginCallback;
//import com.baidu.im.frame.inappCallback.HeartbeatCallback;
//import com.baidu.im.frame.inappCallback.RegAppCallback;
//import com.baidu.im.frame.utils.LogUtil;
//import com.baidu.im.frame.utils.PreferenceUtil;
//import com.baidu.im.inapp.messagecenter.MessageCenter;
//import com.baidu.im.inapp.transaction.session.processor.AppLoginProsessor;
//import com.baidu.im.inapp.transaction.session.processor.HeartbeatProsessor;
//import com.baidu.im.inapp.transaction.session.processor.RegAppProsessor;
//import com.baidu.im.sdk.IMessageCallback;
//
///**
// * 注册相关逻辑。</p>
// * 
// * 1）channel register。一台新设备需要用设备信息注册自己，获得唯一的channelId。 <br>
// * 如果sdk可以找到手机公共区域内存储的channelId，则使用；如果找不到则需要注册。</p>
// * 
// * 2）app register。一个新的app，需要用平台申请的apiKey注册自己的，使之与channelId绑定，方便服务器push消息。 <br>
// * 如果成功则注册成功，通道建立；失败则通知开发者。</p> 如果sdk可以在app的私有存储区域内找到appReg过的信息，
// * 
// * 
// */
//public class AppLoginTransaction extends BizBaseTransaction implements AppLoginCallback, RegAppCallback,
//        HeartbeatCallback, ITransactionTimeoutCallback {
//    public static final String TAG = "AppLoginTransaction";
//
//    private PreferenceUtil mPref = null;
//    private MessageCenter mMsgCenter = null;
//    private boolean hasRegApp = false;
//    private ITransResend mResend = null;
//
//    // private TransactionTimeout mTransTimeout = null;
//    // private boolean mBizStart = false;
//
//    public AppLoginTransaction(PreferenceUtil pref, MessageCenter msgCenter, ITransResend resend) {
//        mPref = pref;
//        mMsgCenter = msgCenter;
//        mResend = resend;
//        // mTransTimeout = new TransactionTimeout(this);
//    }
//
//    @Override
//    public String getThreadName() {
//        return TAG;
//    }
//
//    @Override
//    public ProcessorResult startWorkFlow(IMessageCallback callback) {
//        // appLogin.
//        transactionStart(this.hashCode());
//        if (mResend != null) {
//            mResend.addTransaction(this.hashCode(), this, callback);
//        }
//        mMsgCenter.cacheSendingMessage(this.hashCode(), null, callback);
//        AppLoginProsessor appLoginProsessor = new AppLoginProsessor(mPref, this, this);
//        ProcessorResult appLoginProsessorResult = appLoginProsessor.startWorkFlow();
//        return appLoginProsessorResult;
//    }
//
//    private void startHeartbeat() {
//        HeartbeatProsessor heartbeatProsessor = new HeartbeatProsessor(mPref, this, this, false);
//        heartbeatProsessor.startWorkFlow();
//    }
//
//    @Override
//    public void appLoginCallbackResult(ProcessorResult result) {
//        switch (result.getProcessorCode()) {
//            case SUCCESS:
//                startHeartbeat();
//                break;
//            case UNREGISTERED_APP:
//                if (!hasRegApp) {
//                    LogUtil.printMainProcess("App has not been registered on im server");
//                    // Register app.
//                    RegAppProsessor registerAppProsessor = new RegAppProsessor(this, this, mPref);
//                    registerAppProsessor.startWorkFlow();
//                } else {
//                    LogUtil.printMainProcess("App login error.");
//                    transactionCallback(this.hashCode(), result);
//                }
//                break;
//            default:
//                LogUtil.printMainProcess("App login error.");
//                transactionCallback(this.hashCode(), result);
//                break;
//        }
//    }
//
//    @Override
//    public void regAppSucess() {
//        hasRegApp = true;
//        AppLoginProsessor reAppLoginProsessor = new AppLoginProsessor(mPref, this, this);
//        reAppLoginProsessor.startWorkFlow();
//    }
//
//    @Override
//    public void regAppFail(ProcessorResult result) {
//        transactionCallback(this.hashCode(), result);
//    }
//
//    @Override
//    public void heartBeatResult(ProcessorResult result) {
//        switch (result.getProcessorCode()) {
//            case SUCCESS:
//                break;
//            default:
//                LogUtil.printMainProcess("heartbeat  error.");
//                break;
//        }
//        transactionCallback(this.hashCode(), result);
//    }
//
//    @Override
//    public void onTimeOut() {
//        transactionCallback(this.hashCode(), new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
//    }
//
//}
