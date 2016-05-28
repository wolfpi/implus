package com.baidu.im.frame.outapp;

import android.os.Handler;
import android.os.Messenger;
import android.test.AndroidTestCase;

public class ClientHandlerTest extends AndroidTestCase {

    public void testInit() {

        ClientHandler clientHandler = new ClientHandler(1, new Messenger(new Handler()));
        clientHandler.hander(null);
        clientHandler.getAppId();
        clientHandler.getClientId();
        clientHandler.getMessenger();
        clientHandler.setAppId("");
        clientHandler.setClientId(1);
        clientHandler.setMessenger(null);
    }
}
