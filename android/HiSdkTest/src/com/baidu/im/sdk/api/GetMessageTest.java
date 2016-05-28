//package com.baidu.im.sdk.api;
//
//import java.util.Random;
//
//import junit.framework.Assert;
//
//import android.test.InstrumentationTestCase;
//
//import com.baidu.im.sdk.ChannelSdk;
//import com.baidu.im.testutil.SetUpUtil;
//import com.baidu.im.testutil.Sleeper;
//
//public class GetMessageTest extends InstrumentationTestCase {
//
//    @Override
//    protected void setUp() {
//        try {
//            SetUpUtil.initialize(this.getInstrumentation());
//            Sleeper.waitWithoutBlock(new Random().nextInt(5000));
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }
//
//    /**
//     * Call the interface method when sdk is not initialized;
//     * 
//     * @throws InterruptedException
//     */
//    public void testNotInitialize() {
//        try {
//
//            SetUpUtil.destroy();
//            ChannelSdk.getMessage("");
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }
//
//    /**
//     * Multi-operation by single thread.
//     * 
//     * @throws InterruptedException
//     */
//    public void testMultyOperation() {
//        try {
//
//            int multyCount = 100;
//            for (int i = 0; i < multyCount; i++) {
//                ChannelSdk.getMessage("");
//            }
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }
//
//    /**
//     * Multi-thread test.
//     * 
//     * @throws InterruptedException
//     */
//    public void testMultyThread() {
//        try {
//
//            int multyCount = 100;
//            for (int i = 0; i < multyCount; i++) {
//                new Thread() {
//                    public void run() {
//                        ChannelSdk.getMessage("");
//                    };
//                }.start();
//            }
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }
//
//}
