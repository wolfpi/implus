//package com.baidu.im.inapp.transaction.session;
//
//import android.text.TextUtils;
//
//import com.baidu.im.frame.BizBaseTransaction;
//import com.baidu.im.frame.ITransResend;
//import com.baidu.im.frame.ITransactionTimeoutCallback;
//import com.baidu.im.frame.ProcessorCode;
//import com.baidu.im.frame.ProcessorResult;
//import com.baidu.im.frame.TransactionTimeout;
//import com.baidu.im.frame.inapp.InAppApplication;
//import com.baidu.im.frame.inappCallback.AppLogoutCallback;
//import com.baidu.im.frame.utils.LogUtil;
//import com.baidu.im.frame.utils.PreferenceUtil;
//import com.baidu.im.inapp.messagecenter.MessageCenter;
//import com.baidu.im.inapp.transaction.session.processor.AppLogoutProsessor;
//import com.baidu.im.sdk.IMessageCallback;
//
//public class AppLogoutTransaction extends BizBaseTransaction implements AppLogoutCallback, ITransactionTimeoutCallback {
//
//    public static final String TAG = "AppLogout";
//
//    private PreferenceUtil mPref = null;
//    private MessageCenter mMsgCenter = null;
//    private ITransResend mResend = null;
//    private TransactionTimeout mTransTimeout = null;
//    private boolean mBizStart = false;
//
//    public AppLogoutTransaction(PreferenceUtil pref, MessageCenter msgCenter, ITransResend resend) {
//        mPref = pref;
//        mMsgCenter = msgCenter;
//        mResend = resend;
//        mTransTimeout = new TransactionTimeout(this);
//    }
//
//    @Override
//    public String getThreadName() {
//        return TAG;
//    }
//
//    @Override
//    public ProcessorResult startWorkFlow(IMessageCallback callback) {
//
//        if (!mBizStart) {
//            transactionStart(this.hashCode());
//            mBizStart = true;
//        }
//        if (mResend != null) {
//            LogUtil.e(TAG, String.format("transaction added in:%d", this.hashCode()));
//            mResend.addTransaction(this.hashCode(), this, callback);
//        }
//        if (!InAppApplication.getInstance().isConnected()) {
//            mTransTimeout.startCountDown();
//            return new ProcessorResult(ProcessorCode.SESSION_ERROR);
//        }
//        mTransTimeout.stopCountDown();
//
//        ProcessorResult processorResult = null;
//
//        if (InAppApplication.getInstance().getSession().getApp().getAppId() == 0) {
//            LogUtil.printMainProcess("Need not to AppLogout. sessionId="
//                    + InAppApplication.getInstance().getSession().getSessionInfo().getSessionId() + " ,appid="
//                    + InAppApplication.getInstance().getSession().getApp().getAppId());
//            processorResult = new ProcessorResult(ProcessorCode.NO_APP_ID);
//            transactionCallback(this.hashCode(), processorResult);
//        } else if (TextUtils.isEmpty(InAppApplication.getInstance().getSession().getSessionInfo().getSessionId())) {
//            LogUtil.printMainProcess("Need not to AppLogout. sessionId="
//                    + InAppApplication.getInstance().getSession().getSessionInfo().getSessionId() + " ,appid="
//                    + InAppApplication.getInstance().getSession().getApp().getAppId());
//            processorResult = new ProcessorResult(ProcessorCode.NO_SESSION_ID);
//            transactionCallback(this.hashCode(), processorResult);
//        } else {
//            mMsgCenter.cacheSendingMessage(this.hashCode(), null, callback);
//            AppLogoutProsessor appLogoutProsessor = new AppLogoutProsessor(mPref, this);
//            processorResult = appLogoutProsessor.startWorkFlow();
//        }
//        return processorResult;
//    }
//
//    @Override
//    public void AppLogoutCallbackResult(ProcessorResult result) {
//        transactionCallback(this.hashCode(), result);
//    }
//
//    @Override
//    public void onTimeOut() {
//        transactionCallback(this.hashCode(), new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
//    }
//
//}
