package com.baidu.im.inapp.transaction.session;

import com.baidu.im.frame.BizBaseTransaction;
import com.baidu.im.frame.ITransResend;
import com.baidu.im.frame.ITransactionTimeoutCallback;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.TransactionTimeout;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.inappCallback.UnRegAppCallback;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.inapp.messagecenter.MessageCenter;
import com.baidu.im.inapp.transaction.session.processor.UnRegAppProsessor;
import com.baidu.im.sdk.IMessageCallback;

public class UnRegAppTransaction extends BizBaseTransaction implements UnRegAppCallback, ITransactionTimeoutCallback {

    public static final String TAG = "UnRegApp";
    private PreferenceUtil mPref = null;
    private MessageCenter mMsgCenter = null;
    private ITransResend mResend = null;
    private TransactionTimeout mTransTimeout = null;
    private boolean mBizStart = false;

    public UnRegAppTransaction(PreferenceUtil pref, MessageCenter msgCenter, ITransResend resend) {
        mPref = pref;
        mMsgCenter = msgCenter;
        mResend = resend;
        mTransTimeout = new TransactionTimeout(this);
    }

    public String getThreadName() {
        return TAG;
    }

    @Override
    public ProcessorResult startWorkFlow(IMessageCallback callback) {
        if (!mBizStart) {
            transactionStart(this.hashCode());
            mBizStart = true;
        }
        if (mResend != null) {
            mResend.addTransaction(this.hashCode(), this, callback);
        }
        if (!InAppApplication.getInstance().isConnected()) {
            mTransTimeout.startCountDown();
            return new ProcessorResult(ProcessorCode.SESSION_ERROR);
        }
        mTransTimeout.stopCountDown();

        ProcessorResult processorResult = null;
        if (InAppApplication.getInstance().getSession().getApp().getAppId() != 0) {
            // Register app.
            UnRegAppProsessor unRegAppProsessor = new UnRegAppProsessor(mPref, this, this);
            mMsgCenter.cacheSendingMessage(this.hashCode(), null, callback);
            processorResult = unRegAppProsessor.startWorkFlow();
        } else {
            LogUtil.printMainProcess("Need not to unRegApp, skip this transaction.");
            processorResult = new ProcessorResult(ProcessorCode.NO_APP_ID);
            transactionCallback(this.hashCode(), processorResult);
        }
        return processorResult;
    }

    @Override
    public void UnRegAppCallbackResult(ProcessorResult result) {
        switch (result.getProcessorCode()) {
            case SUCCESS: {
                LogUtil.printMainProcess("unRegApp success.");
            }
                break;
            case NO_APP_ID:
                LogUtil.printMainProcess("Need not to unRegApp, skip this transaction.");
                break;

            default:
                LogUtil.printMainProcess("Register app error.");
                break;
        }
        transactionCallback(this.hashCode(), result);
    }

    @Override
    public void onTimeOut() {
        transactionCallback(this.hashCode(), new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
    }
}
