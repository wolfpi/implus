package com.baidu.imc.impl.im.message;

import java.util.ArrayList;
import java.util.List;

import com.baidu.imc.message.IMTextMessage;
import com.baidu.imc.message.TextMessage;
import com.baidu.imc.message.content.IMMessageContent;
import com.baidu.imc.message.content.TextMessageContent;
import com.baidu.imc.message.content.URLMessageContent;

public class BDHiIMTextMessage extends BDHiIMMessage implements IMTextMessage, TextMessage {

    private List<IMMessageContent> messageContents = new ArrayList<IMMessageContent>();

    public BDHiIMTextMessage() {
        setMessageType(BDHI_IMMESSAGE_TYPE.TEXT);
    }

    @Override
    public List<IMMessageContent> getMessageContentList() {
        return messageContents;
    }

    public void addMessageContentList(List<IMMessageContent> messageContents) {
        this.messageContents.addAll(messageContents);
    }

    @Override
    public void addText(TextMessageContent text) {
        messageContents.add(text);
    }

    public TextMessageContent getText(int location) {
        if (location < messageContents.size()) {
            IMMessageContent messageContent = messageContents.get(location);
            if (messageContent instanceof TextMessageContent) {
                return (TextMessageContent) messageContent;
            }
        }
        return null;
    }

    @Override
    public void addURL(URLMessageContent url) {
        messageContents.add(url);
    }

    public URLMessageContent getURL(int location) {
        if (location < messageContents.size()) {
            IMMessageContent messageContent = messageContents.get(location);
            if (messageContent instanceof URLMessageContent) {
                return (URLMessageContent) messageContent;
            }
        }
        return null;
    }
}
