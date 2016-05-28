package com.baidu.im.sdk.performance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;
import android.os.SystemClock;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.im.sdk.ChannelSdk;
//import com.baidu.im.testutil.SetUpUtil;

public class LoginPerformanceTest extends InstrumentationTestCase {

    int successCount = 0;
    int failCount = 0;

    @Override
    protected void setUp() {
        try {
            //SetUpUtil.initialize(this.getInstrumentation());
        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail("Fail because of exception.");
        }
    }

    /**
     * Multi-operation by single thread.
     * 
     * @throws InterruptedException
     */
    private void testMultyOperationByBduss() {
    	 /*try {
           
            int multyCount = 1;

            int operationCount = 1;
            successCount = 0;
            failCount = 0;
            final CountDownLatch countDownLatch = new CountDownLatch(multyCount);
            String bduss = SetUpUtil.getBduss();
            long startTime = SystemClock.elapsedRealtime();

            for (int i = 0; i < multyCount; i++) {
                ChannelSdk.login(bduss, new IMessageResultCallback() {

                    @Override
                    public void onSuccess(String description) {
                        SetUpUtil.asyncThreadResult = true;
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onFail(int errorCode) {
                        SetUpUtil.asyncThreadResult = false;
                        countDownLatch.countDown();
                    }
                });                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
            }
            // 等待
            countDownLatch.await((15 + multyCount) * 1000, TimeUnit.MILLISECONDS);

            long costTime = SystemClock.elapsedRealtime() - startTime;

            Log.e("PerformanceTest", "LoginPerformanceTest result:");
            Log.e("PerformanceTest", "operation count = " + operationCount);
            Log.e("PerformanceTest", "costTime = " + costTime);
            Log.e("PerformanceTest", "success count = " + successCount);
            Log.e("PerformanceTest", "fail count = " + failCount);

        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail("Fail because of exception.");
        }*/
    }

    public void testPerformance() {
        for (int i = 0; i < 10; i++) {
            testMultyOperationByBduss();
        }
    }
}
