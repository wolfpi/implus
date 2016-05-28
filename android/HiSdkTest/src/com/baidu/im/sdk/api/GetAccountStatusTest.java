//package com.baidu.im.sdk.api;
//
//import java.util.Random;
//
//import junit.framework.Assert;
//import android.test.InstrumentationTestCase;
//
//import com.baidu.im.sdk.EAccountStatus;
//import com.baidu.im.sdk.ChannelSdk;
//import com.baidu.im.testutil.MockUser;
//import com.baidu.im.testutil.SetUpUtil;
//import com.baidu.im.testutil.Sleeper;
//
//public class GetAccountStatusTest extends InstrumentationTestCase {
//
//    @Override
//    protected void setUp() {
//        try {
//            SetUpUtil.initialize(this.getInstrumentation());
//            SetUpUtil.getUserLoginReady(MockUser.UserEnum.imrd_333);
//            Sleeper.waitWithoutBlock(new Random().nextInt(2000));
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
// /*   public void testNotInitialize() {
//        try {
//
//            SetUpUtil.destroy();
//            EAccountStatus accountStatus =
//                    ChannelSdk.getAccountStatus(MockUser.UserEnum.imrd_333.name(), MockUser.UserEnum.imrd_333.getPassword());
//            Assert.assertEquals(accountStatus, EAccountStatus.NotLogin);
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }*/
//
//    /**
//     * 
//     * @throws InterruptedException
//     */
// /*   public void testLogout() {
//        try {
//
//            ChannelSdk.logout(null);
//            Sleeper.waitWithoutBlock(2000);
//            EAccountStatus accountStatus =
//                    ChannelSdk.getAccountStatus(MockUser.UserEnum.imrd_333.name(), MockUser.UserEnum.imrd_333.getPassword());
//            Assert.assertEquals(accountStatus, EAccountStatus.NotLogin);
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }*/
//
//    /**
//     * @throws InterruptedException
//     */
//   /* public void testOnline() {
//        try {
//            ChannelSdk.login(MockUser.UserEnum.imrd_333.name(), MockUser.UserEnum.imrd_333.getPassword(), null);
//            Sleeper.waitWithoutBlock(2000);
//            EAccountStatus accountStatus =
//                    ChannelSdk.getAccountStatus(MockUser.UserEnum.imrd_333.name(), MockUser.UserEnum.imrd_333.getPassword());
//            Assert.assertEquals(accountStatus, EAccountStatus.Online);
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }
//*/
//    /**
//     * Multi-operation by single thread.
//     * 
//     * @throws InterruptedException
//     */
//  /*  public void testMultyOperationByAcount() {
//        try {
//
//            int multyCount = 50;
//            for (int i = 0; i < multyCount; i++) {
//                ChannelSdk.getAccountStatus("asdasda");
//            }
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }*/
//
//    /**
//     * Multi-thread test.
//     * 
//     * @throws InterruptedException
//     */
//   /* public void testMultyThreadByAcount() {
//        try {
//
//            int multyCount = 2;
//            for (int i = 0; i < multyCount; i++) {
//                new Thread() {
//                    public void run() {
//                        ChannelSdk.getAccountStatus("asdasda");
//                    };
//                }.start();
//            }
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }*/
//
//}
