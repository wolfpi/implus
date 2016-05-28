package com.baidu.im.sdk.performance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;
import android.os.SystemClock;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.im.testutil.MockDownPacket;
import com.baidu.im.testutil.MockUser.UserEnum;
//import com.baidu.im.testutil.SetUpUtil;

public class ReceivePushPerformanceTest extends InstrumentationTestCase {

    int count = 0;
    int successCount = 0;
    int failCount = 0;

    @Override
    protected void setUp() {
        try {
          //  SetUpUtil.initialize(this.getInstrumentation());
          //  SetUpUtil.getUserLoginReady(UserEnum.imrd_333);
            successCount = 0;
            failCount = 0;
        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail("Fail because of exception.");
        }
    }

    /**
     * Multi-operation by single thread.
     * 
     * @throws Exception
     */
 /*
    private void testMultyOperation() {
        try {
            int operationCount = 1;
            final CountDownLatch countDownLatch = new CountDownLatch(operationCount);
            long startTime = SystemClock.elapsedRealtime();
            for (int i = 0; i < operationCount; i++) {
                new Thread() {
                    public void run() {
                        DownPacket downPacket =
                                MockDownPacket.mockOneOnlineMsgDownPacket(count++, "message".getBytes());


                        int transactionId =
                                InAppApplication.getInstance().getTransactionFlow().receiveMessage(downPacket);
                        InAppApplication.getInstance().getMessageCenter()
                                .cacheSendingMessage(transactionId, null, new IMessageResultCallback() {

                                    @Override
                                    public void onSuccess(String description) {
                                        SetUpUtil.asyncThreadResult = true;
                                        successCount++;
                                        countDownLatch.countDown();
                                    }

                                    @Override
                                    public void onFail(int errorCode) {
                                        SetUpUtil.asyncThreadResult = false;
                                        failCount++;
                                        countDownLatch.countDown();
                                    }
                                });
                    };
                }.start();

            }
            // 等待
            countDownLatch.await((operationCount + 2) * 1000, TimeUnit.MILLISECONDS);
            long costTime = SystemClock.elapsedRealtime() - startTime;

            Log.e("PerformanceTest", "SendDataPerformanceTest result:");
            Log.e("PerformanceTest", "operation count = " + operationCount);
            Log.e("PerformanceTest", "costTime = " + costTime);
        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail("Fail because of exception.");
        }
    }
*/
    public void testPerformance() {
        for (int i = 0; i < 10; i++) {
            //testMultyOperation();
        }
        Log.e("PerformanceTest", "success count = " + successCount);
        Log.e("PerformanceTest", "fail count = " + failCount);
    }
}
