package com.baidu.im.inapp.transaction.session;

import java.nio.charset.Charset;

import com.baidu.im.frame.BizBaseTransaction;
import com.baidu.im.frame.ITransResend;
import com.baidu.im.frame.ITransactionTimeoutCallback;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.TransactionTimeout;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.inappCallback.HeartbeatCallback;
import com.baidu.im.frame.inappCallback.RegAppCallback;
import com.baidu.im.frame.inappCallback.RegChannelCallback;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.frame.utils.StringUtil;
import com.baidu.im.inapp.messagecenter.MessageCenter;
import com.baidu.im.inapp.transaction.session.processor.HeartbeatProsessor;
import com.baidu.im.inapp.transaction.session.processor.RegAppProsessor;
import com.baidu.im.inapp.transaction.session.processor.RegChannelProsessor;
import com.baidu.im.sdk.IMessageCallback;

/**
 * 1. RegChannel 2. RegApp 3. Heart beat
 */
public class RegAppTransaction extends BizBaseTransaction implements RegChannelCallback, RegAppCallback,
        HeartbeatCallback, ITransactionTimeoutCallback {

    public static final String TAG = "RegApp";
    private PreferenceUtil mPref = null;
    private MessageCenter mMsgCenter = null;
    private ITransResend mResend = null;
    private TransactionTimeout mTransTimeout = null;
    private boolean mBizStart = false;
    private int		mRetryCount = 3;
    private IMessageCallback mCallback = null;

    public RegAppTransaction(PreferenceUtil pref, MessageCenter msgCenter, ITransResend resend) {
        mPref = pref;
        mMsgCenter = msgCenter;
        mResend = resend;
        mTransTimeout = new TransactionTimeout(this);
    }

    @Override
    public String getThreadName() {
        return TAG;
    }

    @Override
    public ProcessorResult startWorkFlow(IMessageCallback callback) {
    	new Thread(new RegAppThread(this,callback)).start();
    	--mRetryCount;
    	mCallback = callback;
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
        String channelKey = InAppApplication.getInstance().getSession().getChannel().getChannelKey();
        if(channelKey != null)
        {
        	LogUtil.i(TAG, channelKey);
        }else
        {
        	LogUtil.i(TAG, "werid");
        }
        
        if (StringUtil.isStringInValid(channelKey)) {
        	LogUtil.i(TAG, "reg App not started");
            mTransTimeout.startCountDown();
            return new ProcessorResult(ProcessorCode.SESSION_ERROR);
        }
        mTransTimeout.stopCountDown();

        transactionStart(this.hashCode());
        LogUtil.i(getThreadName(), "RegAppTransaction transactionId=" + this.hashCode());

        mMsgCenter.cacheSendingMessage(this.hashCode(), null, callback);
        if (InAppApplication.getInstance().isConnected()) {
            LogUtil.i(getThreadName(), "Dont need to RegChannel");
            transactionCallback(this.hashCode(), new ProcessorResult(ProcessorCode.SUCCESS));
            return new ProcessorResult(ProcessorCode.SUCCESS);
        } else {
            LogUtil.i(getThreadName(), "Send RegChannel");
            // Register channel.
            RegChannelProsessor registerChannelProsessor = new RegChannelProsessor(this, this, mPref);
            ProcessorResult result = registerChannelProsessor.startWorkFlow();
            return result;
        }
    }

    @Override
    public void regChannelSuccess() {
        if (InAppApplication.getInstance().getSession().getApp().getAppId() == 0) {
            LogUtil.i(getThreadName(), "RegChannel OK; New app, Send RegApp.");
            // Register app.
            RegAppProsessor registerAppProsessor = new RegAppProsessor(this, this, mPref);
            registerAppProsessor.startWorkFlow();
        } else {
            LogUtil.i(getThreadName(), "RegChannel OK; Old app, Send Heartbeat");
            HeartbeatProsessor heartbeatProsessor = new HeartbeatProsessor(mPref, this, this, false);
            heartbeatProsessor.startWorkFlow();
        }
    }

    @Override
    public void regChannelFail(ProcessorResult result) {
        LogUtil.i(getThreadName(), "RegChannel error.");
        transactionCallback(this.hashCode(), result);
    }

    @Override
    public void regAppSucess() {
        LogUtil.i(getThreadName(), "RegApp OK.");
        HeartbeatProsessor heartbeatProsessor = new HeartbeatProsessor(mPref, this, this, false);
        heartbeatProsessor.startWorkFlow();
    }

    @Override
    public void regAppFail(ProcessorResult result) {
        LogUtil.i(getThreadName(), "RegApp error.");
        transactionCallback(this.hashCode(), result);
    }

    @Override
    public void heartBeatResult(ProcessorResult Result) {
        switch (Result.getProcessorCode()) {
            case SUCCESS:
                LogUtil.i(getThreadName(), "Heartbeat OK.");
                // 设置SDK为可用状态
                InAppApplication.getInstance().setConnected(true);

                if (null != InAppApplication.getInstance().getSession().getApp().getDeviceId()
                        && InAppApplication.getInstance().getSession().getApp().getDeviceId().length() != 0) {
                    Result.setData(InAppApplication.getInstance().getSession().getApp().getDeviceId()
                            .getBytes(Charset.forName("utf-8")));
                }
                break;
            default:
            {
            	if(Result.getProcessorCode() == ProcessorCode.SESSION_ERROR)
            	{
            		InAppApplication.getInstance().getSession().sessionError();
            	}
            	
            	LogUtil.i(getThreadName(), "Heartbeat error.");
            	if(mRetryCount >0)
            	{
            		InAppApplication.getInstance().getSession().getApp().setAppId(0);
            		this.startWorkFlow(mCallback);
            		return;
            	}
               
            }
                break;
        }
        transactionCallback(this.hashCode(), Result);

        if (InAppApplication.getInstance().isConnected()) {
            LogUtil.i(TAG, "resend in all regApp");
            InAppApplication.getInstance().getTransactionFlow().resend();
        }
    }

    @Override
    public void onTimeOut() {
        transactionCallback(this.hashCode(), new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
    }
}

class RegAppThread implements Runnable
{
	private RegAppTransaction transaction;
	private IMessageCallback mCallback = null;
	public RegAppThread(RegAppTransaction obj,IMessageCallback callback){
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