package com.baidu.im.inapp.messagecenter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.baidu.im.frame.utils.StringUtil;
import com.baidu.im.sdk.ImMessage;

public class ReceivingMessageContainer {

    private static Map<String, ImMessage> receivingMessages = Collections
            .synchronizedMap(new HashMap<String, ImMessage>());

    static ImMessage getMessage(String messageId) {
    	if(StringUtil.isStringInValid(messageId))
    		return null;
        return receivingMessages.get(messageId);
    }

    static void add(String messageId, ImMessage message) {
    	if(StringUtil.isStringInValid(messageId) || message == null)
    		return;
    	
        receivingMessages.put(messageId, message);
    }

    static ImMessage remove(String messageId) {
    	if(StringUtil.isStringInValid(messageId))
    		return null;
    	
        return receivingMessages.remove(messageId);
    }

    static void clear() {
        receivingMessages.clear();
    }
}
