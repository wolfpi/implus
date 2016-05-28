package com.baidu.im.inapp.messagecenter;

import android.content.Context;
import android.content.Intent;

import com.baidu.im.frame.inapp.ChannelSdkImpl;
import com.baidu.im.frame.utils.StringUtil;
import com.baidu.im.inapp.messagecenter.SendingMessageContainer.SendingMessage;
import com.baidu.im.sdk.IMessageCallback;
import com.baidu.im.sdk.ImMessage;

public class MessageCenter {

    public void initialize() {

    }

    public static void sendBroadcast(Context context, String messageId) {

    	if(context == null || messageId == null || messageId.isEmpty() )
    		return;
        Intent localIntent = new Intent(ChannelSdkImpl.getBroadcastFilter());
        localIntent.putExtra("messageId", messageId);
        context.sendOrderedBroadcast(localIntent, null);
    }

    /**
     * Return the message from server.
     */
    public ImMessage getReceivedMessage(String messageId) {
    	if(StringUtil.isStringInValid(messageId))
    		return null;
    	
        return ReceivingMessageContainer.getMessage(messageId);
    }

    public ImMessage getAndRemoveReceivedMessage(String messageId) {
    	if(StringUtil.isStringInValid(messageId))
    		return null;
        return ReceivingMessageContainer.remove(messageId);
    }

    /**
     * Save the message from server.
     */
    public void addReceivedMessage(String messageId, ImMessage message) {
    	if(StringUtil.isStringInValid(messageId))
    		return ;
        ReceivingMessageContainer.add(messageId, message);
    }

    public ImMessage removeReceivedMessage(String messageId) {
    	if(StringUtil.isStringInValid(messageId))
    		return null;
        return ReceivingMessageContainer.remove(messageId);
    }

    /**
     * Return the message from user.
     */
    public SendingMessage getSendingMessage(int transactionId) {
        return SendingMessageContainer.getMessage(transactionId);
    }

    /**
     * Save the sending message from user.
     */
    public void cacheSendingMessage(int transactionId, ImMessage message, IMessageCallback callback) {
        SendingMessageContainer.add(transactionId, message, callback);
    }

    public SendingMessage removeSendingMessage(int transactionId) {
        return SendingMessageContainer.remove(transactionId);
    }

    public void clear() {
        ReceivingMessageContainer.clear();
        SendingMessageContainer.clear();
    }

    public void destroy() {
    }

}
