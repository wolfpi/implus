//package com.baidu.im.sdk.api;
//
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import junit.framework.Assert;
//import android.test.InstrumentationTestCase;
//
//import com.baidu.im.constant.ErrorCode;
//import com.baidu.im.sdk.IMessageResultCallback;
//import com.baidu.im.sdk.ChannelSdk;
//import com.baidu.im.testutil.SetUpUtil;
//
//public class LogoutTest extends InstrumentationTestCase {
//
//    @Override
//    protected void setUp() {
//        SetUpUtil.initialize(this.getInstrumentation());
//    }
//
//    /**
//     * Call the interface method when sdk is not initialized;
//     * 
//     * @throws InterruptedException
//     */
//    
//    
//  /*  public void testNotInitialize() {
//        try {
//
//            SetUpUtil.destroy();
//            final CountDownLatch countDownLatch = new CountDownLatch(1);
//            ChannelSdk.logout(new IMessageResultCallback() {
//
//                @Override
//                public void onSuccess(String description) {
//                    Assert.fail("not initialize.");
//                    countDownLatch.countDown();
//                }
//
//                @Override
//                public void onFail(int errorCode) {
//                    Assert.assertEquals(errorCode, ErrorCode.NotInitialize.getCode());
//                    countDownLatch.countDown();
//                }
//            });
//
//            // 等待10秒
//            countDownLatch.await(10 * 1000, TimeUnit.MILLISECONDS);
//            Assert.assertEquals(countDownLatch.getCount(), 0);
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }*/
//
//    /**
//     * Multi-operation by single thread.
//     * 
//     * @throws InterruptedException
//     */
//   /* public void testMultyOperation() {
//        try {
//
//            int multyCount = 2;
//            final CountDownLatch countDownLatch = new CountDownLatch(multyCount);
//            for (int i = 0; i < multyCount; i++) {
//                ChannelSdk.logout(new IMessageResultCallback() {
//
//                    @Override
//                    public void onSuccess(String description) {
//                        countDownLatch.countDown();
//                    }
//
//                    @Override
//                    public void onFail(int errorCode) {
//                        countDownLatch.countDown();
//                    }
//                });
//            }
//            // 等待
//            countDownLatch.await(15 * 1000, TimeUnit.MILLISECONDS);
//            Assert.assertEquals(countDownLatch.getCount(), 0);
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }
//*/
//    /**
//     * Multi-thread test.
//     * 
//     * @throws InterruptedException
//     */
//    /*public void testMultyThread() {
//        try {
//
//            int multyCount = 2;
//            final CountDownLatch countDownLatch = new CountDownLatch(multyCount);
//            for (int i = 0; i < multyCount; i++) {
//                new Thread() {
//                    public void run() {
//                        ChannelSdk.logout(new IMessageResultCallback() {
//
//                            @Override
//                            public void onSuccess(String description) {
//                                countDownLatch.countDown();
//                            }
//
//                            @Override
//                            public void onFail(int errorCode) {
//                                countDownLatch.countDown();
//                            }
//                        });
//                    };
//                }.start();
//            }
//            // 等待
//            countDownLatch.await(15 * 1000, TimeUnit.MILLISECONDS);
//            Assert.assertEquals(countDownLatch.getCount(), 0);
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }
//*/
//    /**
//     * Multi-thread test.
//     * 
//     * @throws InterruptedException
//     */
//   /* public void testContinueLogout() {
//
//        // first time: success
//        try {
//
//            SetUpUtil.asyncThreadResult = false;
//            final CountDownLatch countDownLatch = new CountDownLatch(1);
//            ChannelSdk.logout(new IMessageResultCallback() {
//
//                @Override
//                public void onSuccess(String description) {
//                    SetUpUtil.asyncThreadResult = true;
//                    countDownLatch.countDown();
//                }
//
//                @Override
//                public void onFail(int errorCode) {
//                    SetUpUtil.asyncThreadResult = false;
//                    countDownLatch.countDown();
//                }
//            });
//
//            // 等待
//            countDownLatch.await(15 * 1000, TimeUnit.MILLISECONDS);
//            Assert.assertTrue(SetUpUtil.asyncThreadResult);
//            Assert.assertEquals(countDownLatch.getCount(), 0);
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//
//        // second time: success, error code = NO_SESSION_ID
//        try {
//
//            SetUpUtil.asyncThreadResult = false;
//            final CountDownLatch countDownLatch = new CountDownLatch(1);
//            ChannelSdk.logout(new IMessageResultCallback() {
//
//                @Override
//                public void onSuccess(String description) {
//                    SetUpUtil.asyncThreadResult = true;
//                    countDownLatch.countDown();
//                }
//
//                @Override
//                public void onFail(int errorCode) {
//                    SetUpUtil.asyncThreadResult = false;
//                    countDownLatch.countDown();
//                }
//            });
//
//            // 等待
//            countDownLatch.await(15 * 1000, TimeUnit.MILLISECONDS);
//            Assert.assertTrue(SetUpUtil.asyncThreadResult);
//            Assert.assertEquals(countDownLatch.getCount(), 0);
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }*/
//}
