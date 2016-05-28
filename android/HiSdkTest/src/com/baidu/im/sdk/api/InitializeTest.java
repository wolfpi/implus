package com.baidu.im.sdk.api;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.sdk.ChannelSdk;

public class InitializeTest extends InstrumentationTestCase {

   /* public void testMultyOperation() {
        try {
            int multyCount = 100;
            for (int i = 0; i < multyCount; i++) {
                ChannelSdk.initialize(this.getInstrumentation().getTargetContext(), "testKey1");
            }
        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail("Fail because of exception.");
        }
    }

    public void testMultyThread() {
        try {
            int multyCount = 100;
            for (int i = 0; i < multyCount; i++) {
                new Thread() {
                    public void run() {
                        ChannelSdk.initialize(InitializeTest.this.getInstrumentation().getTargetContext(), "testKey1");
                    };
                }.start();
            }
        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail("Fail because of exception.");
        }
    }*/
}
