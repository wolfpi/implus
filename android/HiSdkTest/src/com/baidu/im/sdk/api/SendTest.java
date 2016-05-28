//package com.baidu.im.sdk.api;
//
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import junit.framework.Assert;
//import android.test.InstrumentationTestCase;
//
//import com.baidu.im.constant.ErrorCode;
//import com.baidu.im.sdk.BinaryMessage;
//import com.baidu.im.sdk.IMessageResultCallback;
//import com.baidu.im.sdk.ChannelSdk;
//import com.baidu.im.testutil.MockUser.UserEnum;
//import com.baidu.im.testutil.SetUpUtil;
//
//public class SendTest extends InstrumentationTestCase {
//
//    @Override
//    protected void setUp() {
//        try {
//
//            SetUpUtil.initialize(this.getInstrumentation());
//            SetUpUtil.getUserLoginReady(UserEnum.imrd_333);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//
//    }
//
//    /**
//     * Call the interface method when sdk is not initialized;
//     * 
//     * @throws InterruptedException
//     */
//   /* public void testNotInitialize() {
//        try {
//
//            ChannelSdk.destroy();
//
//            final CountDownLatch countDownLatch = new CountDownLatch(1);
//            BinaryMessage binaryMessage = new BinaryMessage();
//            binaryMessage.setData(new String("messsage").getBytes());
//            ChannelSdk.send(binaryMessage, new IMessageResultCallback() {
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
//    }
//*/
//    /**
//     * Multi-operation by single thread.
//     * 
//     * @throws Exception
//     */
//   /* public void testMultyOperation() {
//        try {
//
//            int multyCount = 1;
//            final CountDownLatch countDownLatch = new CountDownLatch(multyCount);
//            for (int i = 0; i < multyCount; i++) {
//                BinaryMessage binaryMessage = new BinaryMessage();
//                binaryMessage.setData(new String("messsage" + i).getBytes());
//                ChannelSdk.send(binaryMessage, new IMessageResultCallback() {
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
//            countDownLatch.await((multyCount + 2) * 1000, TimeUnit.MILLISECONDS);
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
//     * @throws Exception
//     */
//   /* public void testMultyThread() {
//        try {
//            int multyCount = 1;
//            final CountDownLatch countDownLatch = new CountDownLatch(multyCount);
//            for (int i = 0; i < multyCount; i++) {
//                new Thread() {
//                    public void run() {
//                        BinaryMessage binaryMessage = new BinaryMessage();
//                        binaryMessage.setData(new String("messsage" + this.getName()).getBytes());
//                        ChannelSdk.send(binaryMessage, new IMessageResultCallback() {
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
//            countDownLatch.await((multyCount + 5) * 1000, TimeUnit.MILLISECONDS);
//            Assert.assertEquals(SetUpUtil.asyncThreadResult, true);
//            Assert.assertEquals(countDownLatch.getCount(), 0);
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            Assert.fail("Fail because of exception.");
//        }
//    }*/
//}
