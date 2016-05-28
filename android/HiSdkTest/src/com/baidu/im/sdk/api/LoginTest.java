//package com.baidu.im.sdk.api;
//
//import java.util.Random;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import junit.framework.Assert;
//import android.test.InstrumentationTestCase;
//
//import com.baidu.im.constant.ErrorCode;
//import com.baidu.im.sdk.IMessageResultCallback;
//import com.baidu.im.sdk.ChannelSdk;
//import com.baidu.im.testutil.MockUser;
//import com.baidu.im.testutil.SetUpUtil;
//import com.baidu.im.testutil.Sleeper;
//
//public class LoginTest extends InstrumentationTestCase {
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
//    /*public void testNotInitialize() {
//        try {
//
//            SetUpUtil.destroy();
//            final CountDownLatch countDownLatch = new CountDownLatch(1);
//            ChannelSdk.login(MockUser.UserEnum.imrd_333.name(), MockUser.UserEnum.imrd_333.getPassword(),
//                    new IMessageResultCallback() {
//
//                        @Override
//                        public void onSuccess(String description) {
//                            Assert.fail("not initialize.");
//                            countDownLatch.countDown();
//                        }
//
//                        @Override
//                        public void onFail(int errorCode) {
//                            Assert.assertEquals(errorCode, ErrorCode.NotInitialize.getCode());
//                            countDownLatch.countDown();
//                        }
//                    });
//
//            // 等待10秒
//            countDownLatch.await(10 * 1000, TimeUnit.MILLISECONDS);
//            Assert.assertEquals(countDownLatch.getCount(), 0);
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
//            int multyCount = 2;
//            final CountDownLatch countDownLatch = new CountDownLatch(multyCount);
//            for (int i = 0; i < multyCount; i++) {
//                ChannelSdk.login(MockUser.UserEnum.imrd_333.name(), MockUser.UserEnum.imrd_333.getPassword(),
//                        new IMessageResultCallback() {
//
//                            @Override
//                            public void onSuccess(String description) {
//                                SetUpUtil.asyncThreadResult = true;
//                                countDownLatch.countDown();
//                            }
//
//                            @Override
//                            public void onFail(int errorCode) {
//                                SetUpUtil.asyncThreadResult = false;
//                                countDownLatch.countDown();
//                            }
//                        });
//            }
//            // 等待
//            countDownLatch.await(multyCount * 1000 + 10 * 1000, TimeUnit.MILLISECONDS);
//            Assert.assertEquals(SetUpUtil.asyncThreadResult, true);
//            Assert.assertEquals(countDownLatch.getCount(), 0);
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
//    /*public void testMultyThreadByAcount() {
//        try {
//
//            int multyCount = 2;
//            final CountDownLatch countDownLatch = new CountDownLatch(multyCount);
//            for (int i = 0; i < multyCount; i++) {
//                new Thread() {
//                    public void run() {
//                        ChannelSdk.login(MockUser.UserEnum.imrd_333.name(), MockUser.UserEnum.imrd_333.getPassword(),
//                                new IMessageResultCallback() {
//
//                                    @Override
//                                    public void onSuccess(String description) {
//                                        SetUpUtil.asyncThreadResult = true;
//                                        countDownLatch.countDown();
//                                    }
//
//                                    @Override
//                                    public void onFail(int errorCode) {
//                                        SetUpUtil.asyncThreadResult = false;
//                                        countDownLatch.countDown();
//                                    }
//                                });
//                    };
//                }.start();
//            }
//            // 等待
//            countDownLatch.await(15 * 1000, TimeUnit.MILLISECONDS);
//            Assert.assertEquals(SetUpUtil.asyncThreadResult, true);
//            Assert.assertEquals(countDownLatch.getCount(), 0);
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
//    public void testMultyOperationByBduss() {
//   /*     try {
//
//            int multyCount = 2;
//            final CountDownLatch countDownLatch = new CountDownLatch(multyCount);
//            String bduss = SetUpUtil.getBduss();
//
//            for (int i = 0; i < multyCount; i++) {
//                ChannelSdk.login(bduss, new IMessageResultCallback() {
//
//                    @Override
//                    public void onSuccess(String description) {
//                        SetUpUtil.asyncThreadResult = true;
//                        countDownLatch.countDown();
//                    }
//
//                    @Override
//                    public void onFail(int errorCode) {
//                        SetUpUtil.asyncThreadResult = false;
//                        countDownLatch.countDown();
//                    }
//                });
//            }
//            // 等待
//            countDownLatch.await(15 * 1000, TimeUnit.MILLISECONDS);
//            Assert.assertEquals(SetUpUtil.asyncThreadResult, true);
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
//    /*public void testMultyThreadByBduss() {
//        try {
//
//            int multyCount = 2;
//            final String bduss = SetUpUtil.getBduss();
//            final CountDownLatch countDownLatch = new CountDownLatch(multyCount);
//            for (int i = 0; i < multyCount; i++) {
//                new Thread() {
//                    public void run() {
//                        ChannelSdk.login(bduss, new IMessageResultCallback() {
//
//                            @Override
//                            public void onSuccess(String description) {
//                                SetUpUtil.asyncThreadResult = true;
//                                countDownLatch.countDown();
//                            }
//
//                            @Override
//                            public void onFail(int errorCode) {
//                                SetUpUtil.asyncThreadResult = false;
//                                countDownLatch.countDown();
//                            }
//                        });
//                    };
//                }.start();
//            }
//            // 等待
//            countDownLatch.await(15 * 1000, TimeUnit.MILLISECONDS);
//            Assert.assertEquals(SetUpUtil.asyncThreadResult, true);
//            Assert.assertEquals(countDownLatch.getCount(), 0);
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }*/
//    }
//}
