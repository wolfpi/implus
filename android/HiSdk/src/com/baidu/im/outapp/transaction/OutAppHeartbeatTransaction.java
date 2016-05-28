/*package com.baidu.im.outapp.transaction;

import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.outapp.transaction.processor.OutAppHeartbeatProcessor;

public class OutAppHeartbeatTransaction 
{

    public final static String TAG = "OutAppHeartbeat";
    public OutAppHeartbeatTransaction(PreferenceUtil pref)
    {
    }

    public String getThreadName() {
        return TAG;
    }

    public ProcessorResult startWorkFlow() {

        // OutAppHeartbeat
        OutAppHeartbeatProcessor processor = new OutAppHeartbeatProcessor();
        ProcessorResult processorResult = processor.startWorkFlow();
        switch (processorResult.getProcessorCode()) {
            case SUCCESS:
                break;
            default:
                LogUtil.printMainProcess("send Offline heartbeat error.");
                break;
        }
        return processorResult;
    }

}*/
