package com.baidu.imc.impl.im.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.imc.IMPlusSDK;
import com.baidu.imc.impl.im.client.IMInboxImpl;
import com.baidu.imc.impl.im.message.content.BDHiVoiceMessageContent;
import com.baidu.imc.impl.im.message.content.PlayedChangedListener;
import com.baidu.imc.impl.im.message.content.PlayedChangedReporter;
import com.baidu.imc.message.CustomMessage;
import com.baidu.imc.message.IMCustomMessage;
import com.baidu.imc.message.content.IMMessageContent;
import com.baidu.imc.message.content.VoiceMessageContent;

import android.text.TextUtils;

public class BDHiIMCustomMessage extends BDHiIMMessage implements IMCustomMessage, CustomMessage,
        PlayedChangedListener {

    private Map<String, List<IMMessageContent>> messageContentMap = new HashMap<String, List<IMMessageContent>>();
    private List<IMMessageContentEntry> messageContentEntryList = new ArrayList<IMMessageContentEntry>();

    public BDHiIMCustomMessage() {
        setMessageType(BDHI_IMMESSAGE_TYPE.CUSTOM);
    }

    /**
     * <b>获得指定Key的消息内容列表</b>
     *
     * @param key
     *
     * @return
     */
    @Override
    public List<IMMessageContent> getMessageContentList(String key) {
        if (null != key && key.length() > 0) {
            return messageContentMap.get(key);
        } else {
            return null;
        }
    }

    /**
     * <b>获得指定Key的消息内容列表中的第一个元素</b>
     *
     * @param key
     *
     * @return
     */
    @Override
    public IMMessageContent getMessageContent(String key) {
        if (null != key && key.length() > 0 && messageContentMap.containsKey(key)) {
            List<IMMessageContent> messageContents = messageContentMap.get(key);
            if (null != messageContents && !messageContents.isEmpty()) {
                return messageContents.get(0);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * <b>添加一个消息内容</b>
     * <p>
     * 添加进去的消息内容放入一个区分key的消息内容数组
     * </p>
     *
     * @param key
     * @param messageContent
     */
    @Override
    public void addMessageContent(String key, IMMessageContent messageContent) {
        if (null != messageContent && null != key && key.length() > 0) {
            List<IMMessageContent> messageContents = messageContentMap.get(key);
            if (null != messageContents) {
                messageContents.add(messageContent);
            } else {
                messageContents = new ArrayList<IMMessageContent>();
                messageContents.add(messageContent);
                messageContentMap.put(key, messageContents);
            }
            messageContentEntryList.add(new IMMessageContentEntry(key, messageContent));

            // 借用这里添加 VoiceContent 的监听
            if (messageContents instanceof VoiceMessageContent) {
                VoiceMessageContent voiceMessageContent = (VoiceMessageContent) messageContent;
                OneMsgConverter.markVoicePlayedToIMMessage(this, key, voiceMessageContent.isPlayed());
                if (voiceMessageContent instanceof PlayedChangedReporter) {
                    PlayedChangedReporter reporter = (PlayedChangedReporter) voiceMessageContent;
                    reporter.setPlayChangedListener(this);
                }
            }

        }
    }

    /**
     * <b>添加一列消息内容</b>
     * <p>
     * 添加进去的消息内容放入一个区分key的消息内容数组
     * </p>
     *
     * @param key
     * @param messageContentList
     */
    public void addMessageContentList(String key, List<IMMessageContent> messageContentList) {
        if (null != messageContentList && !messageContentList.isEmpty() && null != key && key.length() > 0) {
            List<IMMessageContent> messageContents = messageContentMap.get(key);
            if (messageContents == null) {
                messageContents = new ArrayList<IMMessageContent>();
            }
            messageContents.addAll(messageContentList);
            messageContentMap.put(key, messageContents);
            for (IMMessageContent messageContent : messageContentList) {
                messageContentEntryList.add(new IMMessageContentEntry(key, messageContent));

                // 借用这里添加 VoiceContent 的监听
                if (messageContent instanceof VoiceMessageContent) {
                    VoiceMessageContent voiceMessageContent = (VoiceMessageContent) messageContent;
                    OneMsgConverter.markVoicePlayedToIMMessage(this, key, voiceMessageContent.isPlayed());
                    ((BDHiVoiceMessageContent) voiceMessageContent).setPlayChangedListener(this);
                }
            }
        }
    }

    /**
     * <b>获得所有的消息内容列表</b>
     *
     * @return
     */
    public List<IMMessageContentEntry> getAllMessageContent() {
        return messageContentEntryList;
    }

    public Map<String, List<IMMessageContent>> getAllMessageContentMap() {
        return this.messageContentMap;
    }

    @Override
    public void onVoicePlayChanged(VoiceMessageContent voiceMessageContent, boolean saveNow) {
        // 用户更改了 VoiceContent 里的 Play
        String key = findContentKey(voiceMessageContent);
        if(key == null){
            System.out.println("cannot find key of voice content: "+ voiceMessageContent.getFileName()+", give up "
                    + "saveMessage");
            return;
        }
        OneMsgConverter.markVoicePlayedToIMMessage(this, key, voiceMessageContent.isPlayed());
        if(saveNow) {
            ((IMInboxImpl) IMPlusSDK.getImpClient().getIMInbox()).updateMessage(this);
        }
    }

    private String findContentKey(VoiceMessageContent voiceMessageContent) {
        for (IMMessageContentEntry entry : messageContentEntryList) {
            if(voiceMessageContent == entry.messageContent){
                return entry.key;
            }
        }
        return null;
    }

    public class IMMessageContentEntry {
        public String key;
        public IMMessageContent messageContent;

        public IMMessageContentEntry(String key, IMMessageContent messageContent) {
            this.key = key;
            this.messageContent = messageContent;
        }

        public boolean isValid() {
            if (!TextUtils.isEmpty(key) && null != messageContent) {
                return true;
            } else {
                return false;
            }
        }
    }

}
