package com.baidu.imc.impl.im.message;

import com.baidu.imc.message.IMMessage;
import com.baidu.imc.message.IMTransientMessage;

public interface IMMessageListener {
    public void onNewMessageReceived(IMMessage message);

    public void onNewTransientMessageReceived(IMTransientMessage message);
}
