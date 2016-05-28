package com.baidu.im.inapp.transaction.session;

import com.baidu.im.frame.BizBaseTransaction;
import com.baidu.im.frame.ITransResend;
import com.baidu.im.frame.ITransactionTimeoutCallback;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.TransactionTimeout;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.inappCallback.HeartbeatCallback;
import com.baidu.im.frame.inappCallback.RegChannelCallback;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.inapp.messagecenter.MessageCenter;
import com.baidu.im.inapp.transaction.session.processor.HeartbeatProsessor;
import com.baidu.im.inapp.transaction.session.processor.RegChannelProsessor;
import com.baidu.im.sdk.IMessageCallback;

public class ChannelReconnectTransaction extends BizBaseTransaction implements RegChannelCallback, HeartbeatCallback,
        ITransactionTimeoutCallback {

    public static final String TAG = "ChannelReconnect";
    private PreferenceUtil mPref = null;
    private MessageCenter mMsgCenter = null;
    private ITransResend mResend = null;
    private TransactionTimeout mTransTimeout = null;
    private boolean mBizStart = false;

    public ChannelReconnectTransaction(PreferenceUtil pref, MessageCenter msgCenter, ITransResend resend) {
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
            LogUtil.e(TAG, String.format("transaction added in:%d", this.hashCode()));
            mResend.addTransaction(this.hashCode(), this, callback);
        }
        if (!InAppApplication.getInstance().isConnected()) {
            mTransTimeout.startCountDown();
            return new ProcessorResult(ProcessorCode.SESSION_ERROR);
        }
        mTransTimeout.stopCountDown();

        transactionStart(this.hashCode());
        mMsgCenter.cacheSendingMessage(this.hashCode(), null, callback);
        RegChannelProsessor registerChannelProsessor = new RegChannelProsessor(this, this, mPref);
        ProcessorResult registerChannelProsessorResult = registerChannelProsessor.startWorkFlow();
        return registerChannelProsessorResult;
    }

    @Override
    public void regChannelSuccess() {
        HeartbeatProsessor heartbeatProsessor = new HeartbeatProsessor(mPref, this, this, false);
        heartbeatProsessor.startWorkFlow();
        LogUtil.printMainProcess("Register channel ok. send heartbeat");
    }

    @Override
    public void regChannelFail(ProcessorResult result) {
        LogUtil.printMainProcess("Register channel error.");
        transactionCallback(this.hashCode(), result);
    }

    @Override
    public void heartBeatResult(ProcessorResult Result) {
        transactionCallback(this.hashCode(), Result);
        if(Result.getProcessorCode() == ProcessorCode.SUCCESS)
        {
        	 InAppApplication.getInstance().getTransactionFlow().resend();
        }
    }

    @Override
    public void onTimeOut() {
        transactionCallback(this.hashCode(), new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
    }
}
