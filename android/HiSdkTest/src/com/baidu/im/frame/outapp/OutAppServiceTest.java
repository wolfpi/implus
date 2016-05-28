package com.baidu.im.frame.outapp;

import junit.framework.Assert;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.test.ServiceTestCase;

import com.baidu.im.sdk.OutAppService;
import com.baidu.im.testutil.Sleeper;

public class OutAppServiceTest extends ServiceTestCase<OutAppService> {

    private boolean isBind = false;

    public OutAppServiceTest() {
        super(OutAppService.class);
    }

    /**
     * Class for interacting with the interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            isBind = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            isBind = false;
        }
    };

    /**
     * Test binding to service
     */
    public void testBindable() {
        boolean result =
                this.getContext().bindService(new Intent("com.baidu.im.sdk.BackgroundService"), mConnection,
                        Context.BIND_AUTO_CREATE);
        Assert.assertTrue(result);
        Sleeper.waitWithoutBlock(1000);
        Assert.assertTrue(isBind);
    }

}
