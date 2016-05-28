package com.baidu.im.outapp.transaction;

import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.outapp.transaction.processor.OutAppShowOffLineMessageProcessor;

public class OutAppShowOffLineMessageTransaction /*extends OutAppBaseTransaction*/ {

    public final static String TAG = "OutAppShowOffLineMessage";

    DownPacket downPacket;
    public OutAppShowOffLineMessageTransaction(DownPacket downPacket) {
        this.downPacket = downPacket;
    }

    public String getThreadName() {
        return TAG;
    }

    public ProcessorResult startWorkFlow() {
        // Show offline message
        OutAppShowOffLineMessageProcessor processor = new OutAppShowOffLineMessageProcessor(downPacket);
        ProcessorResult processorResult = processor.startWorkFlow();
        switch (processorResult.getProcessorCode()) {
            case SUCCESS:
                break;
            case EMPTY_PUSH:
                LogUtil.printMainProcess("Receive a offline empty push.");
                break;
            default:
                LogUtil.printMainProcess("Receive offline message error.");
                break;
        }
        return processorResult;
    }

}
