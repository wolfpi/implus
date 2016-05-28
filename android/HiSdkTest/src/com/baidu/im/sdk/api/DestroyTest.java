//package com.baidu.im.sdk.api;
//
//import java.util.Random;
//
//import junit.framework.Assert;
//
//import android.test.InstrumentationTestCase;
//
//import com.baidu.im.sdk.ChannelSdk;
//import com.baidu.im.testutil.MockUser;
//import com.baidu.im.testutil.SetUpUtil;
//import com.baidu.im.testutil.Sleeper;
//
//public class DestroyTest extends InstrumentationTestCase {
//
//    @Override
//    protected void setUp() {
//    }
//
//    public void testMultySessionCase() {
//        try {
//            int multyCount = 7;
//            for (int i = 0; i < multyCount; i++) {
//                switch (i % 10) {
//                    case 0:
//                        SetUpUtil.initialize(this.getInstrumentation());
//                        SetUpUtil.getAppLoginReady();
//                        break;
//
//                    case 1:
//                        SetUpUtil.initialize(this.getInstrumentation());
//                        SetUpUtil.getRegAppReady();
//                        break;
//
//                    case 2:
//                        SetUpUtil.initialize(this.getInstrumentation());
//                        SetUpUtil.getUserLoginReady(MockUser.UserEnum.imrd_333);
//                        break;
//
//                    case 3:
//                        SetUpUtil.destroy();
//                        break;
//
//                    default:
//                        SetUpUtil.initialize(this.getInstrumentation());
//                        Sleeper.waitWithoutBlock(new Random().nextInt(5000));
//                        break;
//                }
//                ChannelSdk.destroy();
//            }
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }
//
//    public void testStablity() {
//        try {
//            int multyCount = 100;
//            for (int i = 0; i < multyCount; i++) {
//                switch (i % 10) {
//                    case 0:
//                        SetUpUtil.initialize(this.getInstrumentation());
//                        break;
//
//                    case 1:
//                        SetUpUtil.initialize(this.getInstrumentation());
//                        ChannelSdk.appLogin(null);
//                        break;
//
//                    case 2:
//                        SetUpUtil.initialize(this.getInstrumentation());
//                       // ChannelSdk.login("wrong bduss", null);
//                        break;
//
//                    case 3:
//                        SetUpUtil.destroy();
//                        break;
//
//                    default:
//                        SetUpUtil.initialize(this.getInstrumentation());
//                        break;
//                }
//                ChannelSdk.destroy();
//            }
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }
//
//    public void testMultyOperation() {
//        try {
//            int multyCount = 100;
//            for (int i = 0; i < multyCount; i++) {
//                ChannelSdk.destroy();
//            }
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }
//
//    public void testMultyThread() {
//        try {
//            SetUpUtil.initialize(this.getInstrumentation());
//            SetUpUtil.getUserLoginReady(MockUser.UserEnum.imrd_333);
//            int multyCount = 100;
//            for (int i = 0; i < multyCount; i++) {
//                new Thread() {
//                    public void run() {
//                        ChannelSdk.destroy();
//                    };
//                }.start();
//            }
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }
//}
