package com.baidu.im.inapp.transaction.message;

// 暂时无用
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;

public class ReceiveMessageTransaction  {

    public static final String TAG = "ReceiveMessage";

    DownPacket downPacket;

    public String getThreadName() {
        return TAG;
    }

    public ReceiveMessageTransaction(DownPacket downPacket) {
        this.downPacket = downPacket;
    }

    public ProcessorResult startWorkFlow() {

    	/*
        // appLogin.
        ReceiveMessageProsessor receiveMessageProsessor = new ReceiveMessageProsessor(downPacket);
        ProcessorResult processResult = receiveMessageProsessor;
        switch (processResult.getProcessorCode()) {
            case SUCCESS:
                return processResult;
            case EMPTY_PUSH:
                LogUtil.printMainProcess(TAG + ", Receive a empty push.");
                return processResult;
            default:
                LogUtil.printMainProcess(TAG + ", Receive message error.");
                return processResult;
        }*/
    	return null;
    }
}
