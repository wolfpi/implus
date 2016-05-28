package com.baidu.im.outapp.transaction;

import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.outapp.transaction.processor.OutAppSetAppOffLineProsessor;

public class OutAppSetAppOffLineTransaction  /* extends OutAppBaseTransaction*/ {

    public static final String TAG = "OutAppSetAppOffLine";
    private int mAppId = 0;
    
    
    public OutAppSetAppOffLineTransaction(int appId )
    {
    	mAppId = appId;
    }


    public String getThreadName() {
        return TAG;
    }


    public ProcessorResult startWorkFlow() {
        // set offline app status
        OutAppSetAppOffLineProsessor processor = new OutAppSetAppOffLineProsessor(mAppId);
        ProcessorResult processorResult = processor.startWorkFlow();
        switch (processorResult.getProcessorCode()) {
            case SUCCESS:
                break;
            default:
                LogUtil.printMainProcess("Set offline app status error.");
                break;
        }
        return processorResult;
    }

}
