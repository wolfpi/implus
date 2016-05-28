package com.baidu.imc.impl.im.store;

import java.util.ArrayList;
import java.util.List;

import com.baidu.im.frame.pb.EnumChatType;
import com.baidu.im.frame.pb.ProChatSetting;
import com.baidu.imc.impl.im.message.OneMsgConverter;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.NotificationType;

/**
 * Created by gerald on 3/25/16.
 */
public class ChatSetting {
    Long id; // _id
    AddresseeType chatType; //会话类型
    String targetID; //
    NotificationType receiveMode;
    long lastUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddresseeType getChatType() {
        return chatType;
    }

    public void setChatType(AddresseeType chatType) {
        this.chatType = chatType;
    }

    public String getTargetID() {
        return targetID;
    }

    public void setTargetID(String targetID) {
        this.targetID = targetID;
    }

    public NotificationType getReceiveMode() {
        return receiveMode;
    }

    public void setReceiveMode(NotificationType receiveMode) {
        this.receiveMode = receiveMode;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public static List<ChatSetting> convertFrom(List<ProChatSetting.ChatSetting> settingsList) {
        List<ChatSetting> settingList = new ArrayList<ChatSetting>();
        for (ProChatSetting.ChatSetting chatSetting : settingsList) {
            settingList.add(convertFrom(chatSetting));
        }
        return settingList;
    }

    public static ChatSetting convertFrom(ProChatSetting.ChatSetting setting) {
        ChatSetting s = new ChatSetting();
        s.setTargetID(setting.getTargetID());
        s.setLastUpdate(setting.getLastUpdate());
        s.setChatType(OneMsgConverter.addresseeTypeOfChatType(setting.getChatType(), AddresseeType.USER));
        s.setReceiveMode(notificationTypeOf(setting.getReceiveMode()));
        return s;
    }

    public ProChatSetting.ChatSetting convertTo() {
        ProChatSetting.ChatSetting s = new ProChatSetting.ChatSetting();
        s.setTargetID(getTargetID());
        s.setChatType(OneMsgConverter.enumChatTypeOf(getChatType(), EnumChatType.CHAT_P2P));
        s.setLastUpdate(getLastUpdate());
        s.setReceiveMode(receiveModeOf(getReceiveMode()));
        return s;
    }

    public static int receiveModeOf(NotificationType type) {
        switch (type) {
            case BLOCK_MESSAGE:
                return ProChatSetting.BLOCK_MESSAGE;
            case RECEIVE_NOTIFICATION:
                return ProChatSetting.RECEIVE_NOTIFICATION;
            case RECEIVE_MESSAGE_ONLY:
                return ProChatSetting.RECEIVE_MESSAGE_ONLY;
            default:
                return ProChatSetting.RECEIVE_NOTIFICATION;
        }
    }

    public static NotificationType notificationTypeOf(int typeInt) {
        switch (typeInt) {
            case ProChatSetting.BLOCK_MESSAGE:
                return NotificationType.BLOCK_MESSAGE;
            case ProChatSetting.RECEIVE_NOTIFICATION:
                return NotificationType.RECEIVE_NOTIFICATION;
            case ProChatSetting.RECEIVE_MESSAGE_ONLY:
                return NotificationType.RECEIVE_MESSAGE_ONLY;
            default:
                return NotificationType.RECEIVE_NOTIFICATION;
        }
    }
}
