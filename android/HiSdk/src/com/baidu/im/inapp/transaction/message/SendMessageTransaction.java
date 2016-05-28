package com.baidu.im.inapp.transaction.message;

import com.baidu.im.frame.BizBaseTransaction;
import com.baidu.im.frame.ITransResend;
import com.baidu.im.frame.ITransactionTimeoutCallback;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.TransactionTimeout;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.inappCallback.HeartbeatCallback;
import com.baidu.im.frame.inappCallback.SendMessageCallback;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.inapp.messagecenter.MessageCenter;
import com.baidu.im.inapp.transaction.message.processor.SendMessageProsessor;
import com.baidu.im.inapp.transaction.session.LoginRegTransaction;
import com.baidu.im.inapp.transaction.session.processor.HeartbeatProsessor;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.IMessageCallback;

public class SendMessageTransaction extends BizBaseTransaction implements SendMessageCallback,
        ITransactionTimeoutCallback, HeartbeatCallback {

    public static final String TAG = "SendMessage";

    private BinaryMessage message;
    private String sessionId;
    private PreferenceUtil mPref = null;
    private MessageCenter mMsgCenter = null;
    private boolean mRetried = false;
    private IMessageCallback mCallback = null;
    private ITransResend mResend = null;
    private TransactionTimeout mTransTimeout = null;
    private boolean mBizStart = false;
    private long mStartTime = System.currentTimeMillis();

    public SendMessageTransaction(BinaryMessage message, PreferenceUtil pref, MessageCenter msgCenter,
            ITransResend resend) {
        this.mPref = pref;
        this.message = message;
        mMsgCenter = msgCenter;
        String sessionId = InAppApplication.getInstance().getSession().getSessionInfo().getSessionId();
        if (null != sessionId) {
            this.sessionId = sessionId;
        }
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
        if (!InAppApplication.getInstance().isConnected() || 
        	InAppApplication.getInstance().getSession().getStatus() == 1)
        {
            mTransTimeout.startCountDown();
            return new ProcessorResult(ProcessorCode.SUCCESS);
        }
     
        mResend.removeTransaction(this.hashCode());
        // appLogin.
        mCallback = callback;
        mMsgCenter.cacheSendingMessage(this.hashCode(), message, callback);
        LogUtil.i(getThreadName(), "SendMessageTransaction transactionId=" + this.hashCode());
        LogUtil.i(getThreadName(), "Send SendMsg");
        startHeartbeat();
        return new ProcessorResult(ProcessorCode.SUCCESS);
    }

    private void startHeartbeat() {
    	
    	/*if(InAppApplication.getInstance().NeedToSendHeartBeat())
    	{
    		HeartbeatProsessor heartbeatProsessor = new HeartbeatProsessor(mPref, this, this, false);
    		heartbeatProsessor.startWorkFlow();
    	}else*/
    	{
    		heartBeatResult(new ProcessorResult(ProcessorCode.SUCCESS));
    	}
    }
    
    @Override
    public void SendMessageCallbackResult(ProcessorResult result) {
        if (result.getProcessorCode() == ProcessorCode.SUCCESS) {
            LogUtil.i(getThreadName(), "SendMsg OK");
            mTransTimeout.stopCountDown();
            transactionCallback(this.hashCode(), result);
        } else if (mRetried) {
            LogUtil.i(getThreadName(), "SendMsg error. Can not retry.");
            mTransTimeout.stopCountDown();
            transactionCallback(this.hashCode(), result);
        } else if (result.getProcessorCode() == ProcessorCode.UNREGISTERED_APP
                || result.getProcessorCode() == ProcessorCode.SESSION_ERROR) {
            transactionCallback(0, result);
            LogUtil.i(getThreadName(), "SendMsg error, Retry RegLogin.");
            LoginRegTransaction retryTransaction =
                    new LoginRegTransaction(this.hashCode(), getThreadName(), this, mCallback, mMsgCenter, mPref,
                            mResend);
            retryTransaction.TryRecoverProcessResult(result);
            mRetried = true;
        } else if (result.getProcessorCode() == ProcessorCode.SEND_TIME_OUT)
        {
        	long currentTimeSpan = System.currentTimeMillis() - mStartTime;
        	if(currentTimeSpan >= 5*60*1000)
        	{
        		LogUtil.printMainProcess("SendMsg Fail in 5 minutes.");
        		mTransTimeout.stopCountDown();
                transactionCallback(this.hashCode(), result);
        	}else 
        	{
        		LogUtil.printMainProcess("SendMsg Fail timeout retry it.");
        		this.startWorkFlow(mCallback);
        	}
        }
        else {
            LogUtil.i(getThreadName(), "SendMsg Fail.");
            mTransTimeout.stopCountDown();
            transactionCallback(this.hashCode(), result);
        }
    }

    @Override
    public void onTimeOut() {
    	LogUtil.printMainProcess(getThreadName(), "SendMsg Fail in timeout...");
    	mTransTimeout.stopCountDown();
    	transactionCallback(this.hashCode(), new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
    }

	@Override
	public void heartBeatResult(ProcessorResult Result) {
        SendMessageProsessor sendMessageProcessor = new SendMessageProsessor(message, sessionId, mPref, this, this);
        sendMessageProcessor.startWorkFlow();
	}
}
