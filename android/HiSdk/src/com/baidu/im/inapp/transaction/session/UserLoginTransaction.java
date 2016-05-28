package com.baidu.im.inapp.transaction.session;

import com.baidu.im.frame.BizBaseTransaction;
import com.baidu.im.frame.ITransResend;
import com.baidu.im.frame.ITransactionTimeoutCallback;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.TransactionTimeout;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.inappCallback.HeartbeatCallback;
import com.baidu.im.frame.inappCallback.RegAppCallback;
import com.baidu.im.frame.inappCallback.UserLoginCallback;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.inapp.messagecenter.MessageCenter;
import com.baidu.im.inapp.transaction.session.processor.HeartbeatProsessor;
import com.baidu.im.inapp.transaction.session.processor.RegAppProsessor;
import com.baidu.im.inapp.transaction.session.processor.UserLoginProsessor;
import com.baidu.im.inapp.transaction.setting.QueryChatSettingTransaction;
import com.baidu.im.sdk.IMessageCallback;
import com.baidu.im.sdk.LoginMessage;
import com.baidu.imc.IMPlusSDK;
import com.baidu.imc.impl.im.client.IMInboxImpl;
import com.baidu.imc.impl.im.client.IMPClientImpl;
import com.baidu.imc.impl.im.store.IMsgStore;

/**
 * 1. UserLogin 2. RegApp 3. Heart beat
 */
public class UserLoginTransaction extends BizBaseTransaction implements UserLoginCallback, RegAppCallback,
        HeartbeatCallback, ITransactionTimeoutCallback {

    public static final String TAG = "UserLogin";

    private LoginMessage message;
    private PreferenceUtil mPref = null;
    private MessageCenter mMsgCenter = null;
    private ITransResend mResend = null;
    private boolean mBizStart = false;
    private TransactionTimeout mTransTimeout = null;

    public String getThreadName() {
        return TAG;
    }

    public UserLoginTransaction(LoginMessage message, PreferenceUtil pref, 
        MessageCenter msgCenter, ITransResend resend) {
        this.message = message;
        mPref = pref;
        mMsgCenter = msgCenter;
        mResend = resend;
        mTransTimeout = new TransactionTimeout(this);
    }
    
    @Override
    public ProcessorResult startWorkFlow(IMessageCallback callback) {
    	new Thread(new UserLoginThread(this,callback)).start();
    	return new ProcessorResult(ProcessorCode.SUCCESS);
    }

    public ProcessorResult startWorkFlow1(IMessageCallback callback) {

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

        LogUtil.i(getThreadName(), "UserLoginTransaction transactionId=" + this.hashCode());
        // user Login.
        mMsgCenter.cacheSendingMessage(this.hashCode(), null, callback);
        LogUtil.i(getThreadName(), "Send UserLogin" + message.getToken());
        UserLoginProsessor userLoginProsessor = new UserLoginProsessor(message, mPref, this, this);
        ProcessorResult userLoginProsessorResult = userLoginProsessor.startWorkFlow();
        // 登陆后的第一个心跳，根据bizCode判断是否一切就绪。
        return userLoginProsessorResult;
    }

    @Override
    public void userLoginCallbackResult(ProcessorResult result) {
        switch (result.getProcessorCode()) {
            case UNREGISTERED_APP:
                LogUtil.i(getThreadName(), "UserLogin error. Send RegApp.");
                // Register app.
                RegAppProsessor registerAppProsessor = new RegAppProsessor(this, this, mPref);
                registerAppProsessor.startWorkFlow();
                break;
            case SUCCESS:
                LogUtil.i(getThreadName(), "UserLogin OK, Send Heartbeat.");
                HeartbeatProsessor heartbeatProsessor = new HeartbeatProsessor(mPref, this, this, false);
                heartbeatProsessor.startWorkFlow();
                break;
            default:
                LogUtil.i(getThreadName(), "UserLogin error.");
                transactionCallback(this.hashCode(), result);
                break;
        }
    }

    @Override
    public void regAppSucess() {
        LogUtil.i(getThreadName(), "RegApp OK. Send Userlogin.");
        UserLoginProsessor reUserLoginProsessor = new UserLoginProsessor(message, mPref, this, this);
        reUserLoginProsessor.startWorkFlow();
    }

    @Override
    public void regAppFail(ProcessorResult result) {
        LogUtil.i(getThreadName(), "RegApp Error.");
        transactionCallback(this.hashCode(), result);
    }

    @Override
    public void heartBeatResult(ProcessorResult Result) {
        switch (Result.getProcessorCode()) {
            case SUCCESS:
                LogUtil.i(getThreadName(), "Heartbeat ok.");
                break;
            default:
                LogUtil.i(getThreadName(), "Heartbeat error.");
                break;
        }
        transactionCallback(this.hashCode(), Result);
    }

    @Override
    public void onTimeOut() {
        transactionCallback(this.hashCode(), new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
    }
}

class UserLoginThread implements Runnable
{
	private UserLoginTransaction transaction;
	private IMessageCallback mCallback = null;
	public UserLoginThread(UserLoginTransaction obj,IMessageCallback callback){
		transaction = obj;
		mCallback = callback;
	}
	@Override
	public void run() {
		try {
			transaction.startWorkFlow1(mCallback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
