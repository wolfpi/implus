package com.baidu.im.inapp.transaction.session;

import com.baidu.im.frame.BizTransaction;
import com.baidu.im.frame.ITransResend;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.inapp.ChannelSdkImpl;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.utils.IRegAppCallback;
import com.baidu.im.frame.utils.IReloginCallback;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.inapp.messagecenter.MessageCenter;
import com.baidu.im.sdk.IMessageCallback;
import com.baidu.im.sdk.LoginMessage;

public class LoginRegTransaction implements IRegAppCallback, IReloginCallback {

    private String tag;
    private int transactionID;
    private BizTransaction mRetryTransaction = null;
    private MessageCenter mMsgCenter = null;
    private PreferenceUtil mPref = null;
    private IMessageCallback mBizCallback = null;
    private ITransResend mResend = null;

    public LoginRegTransaction(int transactionID, String tag, BizTransaction transaction, IMessageCallback callback,
            MessageCenter msgCenter, PreferenceUtil pref, ITransResend resend) {
        this.transactionID = transactionID;
        this.tag = tag;
        mRetryTransaction = transaction;
        mMsgCenter = msgCenter;
        mPref = pref;
        mBizCallback = callback;
        mResend = resend;
    }

    public void TryRecoverProcessResult(ProcessorResult processorResult) {
        if (processorResult.getProcessorCode() != ProcessorCode.SUCCESS) {
            processProcessorCode(processorResult);
        }
    }

    protected void processProcessorCode(ProcessorResult processorResult) {
        if (processorResult.getProcessorCode() == ProcessorCode.UNREGISTERED_APP) {
            LogUtil.i(tag, "LoginReg Try reRegApp.");
            reRegApp();
        } else if (processorResult.getProcessorCode() == ProcessorCode.SESSION_ERROR) {
            LogUtil.i(tag, "LoginReg Try reLogin.");
            reUserLogin();
        } else {
            LogUtil.i(tag, "LoginReg other error.");
            ChannelSdkImpl.callback(transactionID, processorResult);
        }
    }

    /**
     * Re-Register App 1. regApp 2. heart_beat
     */
    private void reRegApp() {
        BizTransaction regAppTransaction = new RegAppTransaction(mPref, mMsgCenter, mResend);
        regAppTransaction.startWorkFlow(this);
    }

    /**
     * Re-Login User 1. UserLogin 2. RegApp if needed 3. UserLogin if needed 4. HeartBeat
     */
    private void reUserLogin() {
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setToken(InAppApplication.getInstance().getSession().getSessionInfo().getToken());
        loginMessage.setAccountId(InAppApplication.getInstance().getSession().getSessionInfo().getAccountId());

        BizTransaction userLogin = new UserLoginTransaction(loginMessage, mPref, mMsgCenter, mResend);
        userLogin.startWorkFlow(this);
    }

    @Override
    public void regAppSucess() {
        LogUtil.i(tag, "ReRegApp OK.");
        // ChannelSdkImpl.callback(transactionID, new ProcessorResult(ProcessorCode.SUCCESS));
        LogUtil.i(tag, "RetryTransaction.");
        this.mRetryTransaction.startWorkFlow(mBizCallback);
    }

    @Override
    public void regAppFail(int errCode) {
        LogUtil.i(tag, "ReRegApp error");
        ChannelSdkImpl.callback(transactionID, new ProcessorResult(ProcessorCode.parse(errCode)));
    }

    @Override
    public void reloginSuccess() {
        LogUtil.i(tag, "ReUserLogin OK.");
        // ChannelSdkImpl.callback(transactionID, new ProcessorResult(ProcessorCode.SUCCESS));
        LogUtil.i(tag, "RetryTransaction.");
        this.mRetryTransaction.startWorkFlow(mBizCallback);
    }

    @Override
    public void reloginFail(int errCode) {
        if (errCode == ProcessorCode.UNREGISTERED_APP.getCode()) {
            LogUtil.i(tag, "ReUserLogin error. Send RegApp.");
            reRegApp();
        } else {
            LogUtil.i(tag, "ReUserLogin error.");
            ChannelSdkImpl.callback(transactionID, new ProcessorResult(ProcessorCode.parse(errCode)));
        }
    }

}
