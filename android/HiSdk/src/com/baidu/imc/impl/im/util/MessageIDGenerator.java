package com.baidu.imc.impl.im.util;

import java.util.concurrent.atomic.AtomicInteger;

import com.baidu.im.frame.utils.LogUtil;

public class MessageIDGenerator {

    private static final String TAG = "MessageIDGenerator";

    private static volatile MessageIDGenerator instance;

    private MessageIDGenerator() {

    }

    public static MessageIDGenerator getInstance() {
        if (instance == null) {
            synchronized (MessageIDGenerator.class) {
                if (instance == null) {
                    instance = new MessageIDGenerator();
                }
            }
        }
        return instance;
    }

    private AtomicInteger msgID = new AtomicInteger(0);

    public long generateClientMessageId() {
        long nowtime = System.currentTimeMillis();
        long basementID = nowtime << 20;
        int messageID = msgID.getAndIncrement();
        long clientMessageID = basementID + messageID;
        LogUtil.printIm(TAG + "[nowtime]" + nowtime + "[basementID]" + basementID + "[clientMessageID]"
                + clientMessageID);
        return clientMessageID;
    }
}
