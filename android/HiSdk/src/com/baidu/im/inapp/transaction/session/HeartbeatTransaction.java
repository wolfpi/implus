package com.baidu.im.inapp.transaction.session;

import com.baidu.im.frame.BizBaseTransaction;
import com.baidu.im.frame.ITransResend;
import com.baidu.im.frame.ITransactionTimeoutCallback;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.TransactionTimeout;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.inappCallback.HeartbeatCallback;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.inapp.messagecenter.MessageCenter;
import com.baidu.im.inapp.transaction.session.processor.HeartbeatProsessor;
import com.baidu.im.sdk.IMessageCallback;
import com.baidu.imc.IMPlusSDK;
import com.baidu.imc.impl.im.client.IMPClientImpl;
import com.baidu.imc.impl.im.store.IMsgStore;
import com.baidu.imc.impl.im.transaction.IMTransactionFlow;

public class HeartbeatTransaction extends BizBaseTransaction implements HeartbeatCallback, ITransactionTimeoutCallback {

    public static final String TAG = "Heartbeat";
    private PreferenceUtil mPref = null;
    private MessageCenter mMsgCenter;
    private IMessageCallback mCallback = null;
    private boolean mRetried = false;
    private ITransResend mResend = null;
    private TransactionTimeout mTransTimeout = null;
    private boolean mBizStart = false;

    public HeartbeatTransaction(PreferenceUtil pref, MessageCenter msgCenter, ITransResend resend) {
        mPref = pref;
        mMsgCenter = msgCenter;
        mRetried = false;
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
            LogUtil.e(TAG, String.format("transaction heartbeat added in:%d", this.hashCode()));
            mResend.addTransaction(this.hashCode(), this, callback);
        }
        if (!InAppApplication.getInstance().isConnected()) {
            mTransTimeout.startCountDown();
            return new ProcessorResult(ProcessorCode.SESSION_ERROR);
        }
        mTransTimeout.stopCountDown();

        mCallback = callback;
        mMsgCenter.cacheSendingMessage(this.hashCode(), null, callback);
        LogUtil.i(getThreadName(), "HeartbeatTransaction transactionId=" + this.hashCode());
        LogUtil.i(getThreadName(), "Send Heartbeat");
        HeartbeatProsessor heartbeatProsessor = new HeartbeatProsessor(mPref, this, this, true);
        ProcessorResult heartbeatProsessorResult = heartbeatProsessor.startWorkFlow();
        return heartbeatProsessorResult;
    }

    @Override
    public void heartBeatResult(ProcessorResult Result) {
        if (Result.getProcessorCode() == ProcessorCode.SUCCESS) {
            transactionCallback(this.hashCode(), Result);
            LogUtil.i(getThreadName(), "Heartbeat OK.");
        } else if (mRetried) {
            transactionCallback(this.hashCode(), Result);
            LogUtil.i(getThreadName(), "Heartbeat error. Can not retry.");
        } else {
            transactionCallback(0, Result);
            LogUtil.i(getThreadName(), "Heartbeat error. Retry LoginReg.");
            LoginRegTransaction retryTransaction =
                    new LoginRegTransaction(this.hashCode(), getThreadName(), this, mCallback, mMsgCenter, mPref,
                            mResend);
            retryTransaction.TryRecoverProcessResult(Result);
            mRetried = true;
        }
    }

    @Override
    public void onTimeOut() {
        transactionCallback(this.hashCode(), new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
    }
}
