package com.baidu.im.inapp.transaction.session;

import android.text.TextUtils;

import com.baidu.im.frame.BizBaseTransaction;
import com.baidu.im.frame.ITransResend;
import com.baidu.im.frame.ITransactionTimeoutCallback;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.TransactionTimeout;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.inappCallback.UserLoginoutCallback;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.inapp.messagecenter.MessageCenter;
import com.baidu.im.inapp.transaction.session.processor.UserLogoutProsessor;
import com.baidu.im.sdk.IMessageCallback;

public class UserLogoutTransaction extends BizBaseTransaction implements UserLoginoutCallback,
        ITransactionTimeoutCallback {

    public static final String TAG = "UserLogout";
    private PreferenceUtil mPref = null;
    private MessageCenter mMsgCenter = null;
    private ITransResend mResend = null;
    private TransactionTimeout mTransTimeout = null;
    private boolean mBizStart = false;

    public UserLogoutTransaction(PreferenceUtil pref, MessageCenter msgCenter, ITransResend resend) {
        mPref = pref;
        mMsgCenter = msgCenter;
        mResend = resend;
        mTransTimeout = new TransactionTimeout(this);
    }

    public String getThreadName() {
        return TAG;
    }

    /*
     * callback 现在还没有合理利用
     * 
     * @see com.baidu.im.frame.BizTransaction#startWorkFlow(com.baidu.im.sdk.IMessageCallback)
     */
    @Override
    public ProcessorResult startWorkFlow(IMessageCallback callback) {
        if (!mBizStart) {
            transactionStart(this.hashCode());
            mBizStart = true;
        }
        if (mResend != null) {
            LogUtil.e(TAG, String.format("transaction added in:%d", this.hashCode()));
            mResend.addTransaction(this.hashCode(), this, callback);
        }
        if (!InAppApplication.getInstance().isConnected()) {
            mTransTimeout.startCountDown();
            return new ProcessorResult(ProcessorCode.SESSION_ERROR);
        }
        mTransTimeout.stopCountDown();

        ProcessorResult processorResult = null;

        if (TextUtils.isEmpty(InAppApplication.getInstance().getSession().getSessionInfo().getSessionId())) {
            LogUtil.printMainProcess("User logout success. Can not get seesionId, skip this transaction.");
            processorResult = new ProcessorResult(ProcessorCode.NO_SESSION_ID);
            transactionCallback(this.hashCode(), processorResult);
        } else {
            // user Login.
            mMsgCenter.cacheSendingMessage(this.hashCode(), null, callback);
            UserLogoutProsessor userLogoutProsessor = new UserLogoutProsessor(mPref, this);
            processorResult = userLogoutProsessor.startWorkFlow();
        }
        InAppApplication.getInstance().getSession().userLogoutSuccess();
        return processorResult;
    }

    @Override
    public void userLoginoutCallbackResult(ProcessorResult result) {
        transactionCallback(this.hashCode(), result);
    }

    @Override
    public void onTimeOut() {
        transactionCallback(this.hashCode(), new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
    }
}
