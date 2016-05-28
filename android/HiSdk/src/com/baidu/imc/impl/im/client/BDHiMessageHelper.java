package com.baidu.imc.impl.im.client;

import java.io.File;

import com.baidu.imc.client.MessageHelper;
import com.baidu.imc.impl.im.message.BDHiIMCustomMessage;
import com.baidu.imc.impl.im.message.BDHiIMFileMessage;
import com.baidu.imc.impl.im.message.BDHiIMImageMessage;
import com.baidu.imc.impl.im.message.BDHiIMTextMessage;
import com.baidu.imc.impl.im.message.BDHiIMVoiceMessage;
import com.baidu.imc.impl.im.message.BDHiTransientMessage;
import com.baidu.imc.impl.im.message.content.BDHiFileMessageContent;
import com.baidu.imc.impl.im.message.content.BDHiImageMessageContent;
import com.baidu.imc.impl.im.message.content.BDHiTextMessageContent;
import com.baidu.imc.impl.im.message.content.BDHiURLMessageContent;
import com.baidu.imc.impl.im.message.content.BDHiVoiceMessageContent;
import com.baidu.imc.message.CustomMessage;
import com.baidu.imc.message.FileMessage;
import com.baidu.imc.message.ImageMessage;
import com.baidu.imc.message.TextMessage;
import com.baidu.imc.message.TransientMessage;
import com.baidu.imc.message.VoiceMessage;
import com.baidu.imc.message.content.FileMessageContent;
import com.baidu.imc.message.content.ImageMessageContent;
import com.baidu.imc.message.content.TextMessageContent;
import com.baidu.imc.message.content.URLMessageContent;
import com.baidu.imc.message.content.VoiceMessageContent;

public class BDHiMessageHelper implements MessageHelper {

    public BDHiMessageHelper() {

    }

    @Override
    public FileMessage newFileMessage() {
        FileMessage fileMessage = new BDHiIMFileMessage();
        return fileMessage;
    }

    @Override
    public FileMessageContent newFileMessageContent(String filePath) {
        if (null != filePath && filePath.length() > 0 && new File(filePath).exists()) {
            FileMessageContent messageContent = new BDHiFileMessageContent();
            messageContent.setFilePath(filePath);
            messageContent.setFileName(new File(filePath).getName());
            return messageContent;
        } else {
            return null;
        }
    }

    @Override
    public ImageMessage newImageMessage() {
        ImageMessage message = new BDHiIMImageMessage();
        return message;
    }

    @Override
    public ImageMessageContent newImageMessageContent() {
        ImageMessageContent messageContent = new BDHiImageMessageContent();
        return messageContent;
    }

    @Override
    public TextMessage newTextMessage() {
        TextMessage message = new BDHiIMTextMessage();
        return message;
    }

    @Override
    public TextMessageContent newTextMessageContent(String text) {
        if (null != text && text.length() > 0) {
            TextMessageContent messageContent = new BDHiTextMessageContent();
            messageContent.setText(text);
            return messageContent;
        } else {
            return null;
        }
    }

    @Override
    public TransientMessage newTransientMessage() {
        TransientMessage message = new BDHiTransientMessage();
        return message;
    }

    @Override
    public URLMessageContent newURLMessageContent(String url, String text) {
        if (null != url && url.length() > 0 && null != text && text.length() > 0) {
            URLMessageContent messageContent = new BDHiURLMessageContent();
            messageContent.setURL(url);
            messageContent.setText(text);
            return messageContent;
        } else {
            return null;
        }
    }

    @Override
    public VoiceMessage newVoiceMessage() {
        VoiceMessage message = new BDHiIMVoiceMessage();
        return message;
    }

    @Override
    public VoiceMessageContent newVoiceMessageContent() {
        VoiceMessageContent messageContent = new BDHiVoiceMessageContent();
        return messageContent;
    }

    @Override
    public CustomMessage newCustomMessage() {
        CustomMessage customMessage = new BDHiIMCustomMessage();
        return customMessage;
    }

}
