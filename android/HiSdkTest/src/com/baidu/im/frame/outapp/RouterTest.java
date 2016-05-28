package com.baidu.im.frame.outapp;

import junit.framework.Assert;
import android.os.Handler;
import android.os.Messenger;
import android.test.AndroidTestCase;

public class RouterTest extends AndroidTestCase {

    public void testInit() {

        Router router = new Router();
        router.register(1, new Messenger(new Handler()));
        router.register("1", new ClientHandler(1, new Messenger(new Handler())));
        Assert.assertTrue(router.getAllClientIds() != null);
        Assert.assertTrue(router.getClientHandler(1) != null);
        Assert.assertTrue(router.getClientHandler("1")!= null);
        Assert.assertTrue(router.getMessenger(1)!= null);
        router.unRegister(1);
        
        Assert.assertTrue(router.getAllClientIds().size() == 0);
        Assert.assertTrue(router.getClientHandler(1) == null);
        Assert.assertTrue(router.getClientHandler("1") != null);
        Assert.assertTrue(router.getMessenger(1) == null);
        router.destroy();
    }
}
