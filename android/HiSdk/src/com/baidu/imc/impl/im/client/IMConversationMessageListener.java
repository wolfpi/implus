package com.baidu.imc.impl.im.client;

import java.util.Map;

import com.baidu.imc.message.IMMessage;
import com.baidu.imc.type.IMMessageChange;

public interface IMConversationMessageListener {

    public void onNewMessageReceived(IMMessage message);

    public void onMessageChanged(IMMessage newMessage, Map<IMMessageChange, Object> changes);

}
