package com.baidu.im.inapp.transaction.session;

import com.baidu.im.frame.BizBaseTransaction;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.inappCallback.SetAppStatusCallback;
import com.baidu.im.frame.pb.EnumAppStatus;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.inapp.messagecenter.MessageCenter;
import com.baidu.im.inapp.transaction.session.processor.SetAppStatusProsessor;
import com.baidu.im.sdk.IMessageCallback;

/**
 * 
 * 
 */

public class SetAppStatusTransaction extends BizBaseTransaction implements SetAppStatusCallback {

    public static final String TAG = "SetAppStatus";

    private /*EAppStatus*/ int eAppStatus;
    private PreferenceUtil mPref = null;
    private MessageCenter mMsgCenter = null;

    public SetAppStatusTransaction(PreferenceUtil pref, MessageCenter msgCenter) {
        mPref = pref;
        mMsgCenter = msgCenter;
    }

    public String getThreadName() {
        return TAG;
    }

    public SetAppStatusTransaction(int eAppStatus) {
        this.eAppStatus = eAppStatus;
    }

    @Override
    public ProcessorResult startWorkFlow(IMessageCallback callback) {
        ProcessorResult processorResult = null;
        transactionStart(this.hashCode());
        mMsgCenter.cacheSendingMessage(this.hashCode(), null, callback);
        if (eAppStatus == -1) {
            LogUtil.printMainProcess("SetAppStatus, param error: " + eAppStatus);
            processorResult = new ProcessorResult(ProcessorCode.NO_APP_STATUS);
            transactionCallback(this.hashCode(), processorResult);
        } else {
            SetAppStatusProsessor setAppStatusProsessor = new SetAppStatusProsessor(eAppStatus, mPref, this, this);
            processorResult = setAppStatusProsessor.startWorkFlow();
        }
        return processorResult;
    }

    @Override
    public void SetAppStatucCallbackResult(ProcessorResult result) {
        switch (result.getProcessorCode()) {
            case SUCCESS:
                break;
            case NO_APP_STATUS:
                LogUtil.printMainProcess("SetAppStatus, param error: " + eAppStatus);
                break;
            default:
                LogUtil.printMainProcess("Set app status error.");
        }

        transactionCallback(this.hashCode(), result);
    }
}
