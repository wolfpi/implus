package com.baidu.im.inapp.transaction.config;

import com.baidu.im.frame.BizBaseTransaction;
import com.baidu.im.frame.ITransResend;
import com.baidu.im.frame.ITransactionTimeoutCallback;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.TransactionTimeout;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.inapp.transaction.config.processor.UpdateConfigProcessor;
import com.baidu.im.sdk.IMessageCallback;

public class UpdateConfigTransaction extends BizBaseTransaction implements ITransactionTimeoutCallback {

    public static final String TAG = "UpdateConfig";
    private PreferenceUtil mPref = null;
    private ITransResend mResend = null;
    private TransactionTimeout mTransTimeout = null;
    private boolean mBizStart = false;

    public UpdateConfigTransaction(PreferenceUtil pref, ITransResend resend) {
        mPref = pref;
        mResend = resend;
        mTransTimeout = new TransactionTimeout(this);
    }

    @Override
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

        ProcessorResult updateConfigProsessorResult;
        // update config
        if (mPref != null) {
            UpdateConfigProcessor updateConfigProsessor = new UpdateConfigProcessor(mPref);
            updateConfigProsessorResult = updateConfigProsessor.startWorkFlow();
            switch (updateConfigProsessorResult.getProcessorCode()) {
                case SUCCESS:
                    break;
                case CONFIG_UPDATE_ERROR:
                    LogUtil.printMainProcess(TAG, "Can not get config from server.");
                    break;
                default:
                    LogUtil.printMainProcess(TAG, "Update Config error.");
                    break;
            }
        } else {
            updateConfigProsessorResult = new ProcessorResult(ProcessorCode.PARAM_ERROR);
        }
        transactionCallback(0, updateConfigProsessorResult);
        return updateConfigProsessorResult;
    }

    @Override
    public void onTimeOut() {
        transactionCallback(this.hashCode(), new ProcessorResult(ProcessorCode.SEND_TIME_OUT));
    }
}
