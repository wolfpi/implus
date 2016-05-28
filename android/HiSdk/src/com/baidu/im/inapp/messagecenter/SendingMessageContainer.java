package com.baidu.im.inapp.messagecenter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;

import com.baidu.im.sdk.IMessageCallback;
import com.baidu.im.sdk.ImMessage;

public class SendingMessageContainer {

    @SuppressLint("UseSparseArrays")
    private static Map<Integer, SendingMessage> sendingMessages = Collections
            .synchronizedMap(new HashMap<Integer, SendingMessage>());

    static SendingMessage getMessage(int transactionId) {
        return sendingMessages.get(transactionId);
    }

    public int getCount() {
        return sendingMessages.size();
    }

    static void add(int transactionId, SendingMessage message) {
        sendingMessages.put(transactionId, message);
    }

    static void add(int transactionId, ImMessage message, IMessageCallback callback) {
        SendingMessage sendingMessage = new SendingMessage();
        sendingMessage.message = message;
        sendingMessage.callback = callback;
        sendingMessages.put(transactionId, sendingMessage);
        
       // LogUtil.e("snd",String.format("%d",transactionId));
    }

    static SendingMessage remove(int transactionId) {
    	
    	//LogUtil.e("snd", "sending message callback");
    	//LogUtil.e("snd",String.format("%d",transactionId));
        return sendingMessages.remove(transactionId);
    }

    static void clear() {
        sendingMessages.clear();
    }

    public static class SendingMessage {
        public ImMessage message;
        public IMessageCallback callback;
    }
}
