package com.baidu.im.inapp.transaction.config;

import com.baidu.im.frame.BizBaseTransaction;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.inapp.transaction.config.processor.ConfigChangeNotifyProcessor;
import com.baidu.im.sdk.IMessageCallback;
import com.google.protobuf.micro.ByteStringMicro;

public class ConfigChangeNotifyTransaction extends BizBaseTransaction {

    public static final String TAG = "ConfigChangeNotify";

    private ByteStringMicro byteBuffer;
    private PreferenceUtil mPref = null;

    @Override
    public String getThreadName() {
        return TAG;
    }

    public ConfigChangeNotifyTransaction(ByteStringMicro byteBuffer, PreferenceUtil pref) {
        this.byteBuffer = byteBuffer;
        mPref = pref;
    }

    @Override
    public ProcessorResult startWorkFlow(IMessageCallback callback) {
        ProcessorResult processorResult = null;
        transactionStart(this.hashCode());
        // config change notify.
        if (byteBuffer != null && mPref != null) {
            ConfigChangeNotifyProcessor receiveMessageProsessor = new ConfigChangeNotifyProcessor(byteBuffer, mPref);
            processorResult = receiveMessageProsessor.startWorkFlow();
            switch (processorResult.getProcessorCode()) {
                case SUCCESS:
                    break;
                default:
                    LogUtil.printMainProcess(TAG, "Receive config change notify error.");
                    break;
            }
        } else {
            processorResult = new ProcessorResult(ProcessorCode.PARAM_ERROR);
        }
        transactionCallback(0, processorResult);
        return processorResult;
    }
}
