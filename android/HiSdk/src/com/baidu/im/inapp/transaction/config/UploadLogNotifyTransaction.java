package com.baidu.im.inapp.transaction.config;

import com.baidu.im.frame.BizBaseTransaction;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.inapp.transaction.config.processor.UploadLogNotifyProcessor;
import com.baidu.im.sdk.IMessageCallback;
import com.google.protobuf.micro.ByteStringMicro;

public class UploadLogNotifyTransaction extends BizBaseTransaction {

    public static final String TAG = "UploadLogNotify";

    private ByteStringMicro byteBuffer;
    private PreferenceUtil mPref = null;

    public String getThreadName() {
        return TAG;
    }

    public UploadLogNotifyTransaction(ByteStringMicro byteBuffer, PreferenceUtil pref) {
        this.byteBuffer = byteBuffer;
        mPref = pref;
    }

    @Override
    public ProcessorResult startWorkFlow(IMessageCallback callback) {
        transactionStart(this.hashCode());
        ProcessorResult processorResult = null;
        // try {
        if (byteBuffer != null && mPref != null) {

            UploadLogNotifyProcessor uploadLogNotifyProcessor = new UploadLogNotifyProcessor(byteBuffer);
            processorResult = uploadLogNotifyProcessor.startWorkFlow();
            switch (processorResult.getProcessorCode()) {
                case SUCCESS:
                    break;
                default:
                    LogUtil.printMainProcess(TAG, "Upload log notify error.");
                    break;
            }
        } else {
            processorResult = new ProcessorResult(ProcessorCode.PARAM_ERROR);
        }
        transactionCallback(0, processorResult);
        return processorResult;
    }

}
