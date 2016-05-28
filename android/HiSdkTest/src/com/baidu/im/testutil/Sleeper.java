package com.baidu.im.testutil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue.IdleHandler;

public class Sleeper {

    public static void waitWithoutBlock(long milliseconds) {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        try {
            countDownLatch.await(milliseconds, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 阻塞handler所在的线程，直至先一个postRunnalbe。
     * 
     * @param handler
     */
    public static void waitForHandlerPost(Handler handler) {
        final Idler idler = new Idler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Looper.myQueue().addIdleHandler(idler);
            }
        });

        handler.post(new Runnable() {
            @Override
            public void run() {
            }
        });
        idler.waitForIdle();
    }

    private static class Idler implements IdleHandler {
        private boolean mIdle;

        public Idler() {
            mIdle = false;
        }

        @Override
        public final boolean queueIdle() {
            synchronized (this) {
                mIdle = true;
                notifyAll();
            }
            return false;
        }

        public void waitForIdle() {
            synchronized (this) {
                while (!mIdle) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        // Do nothing.
                    }
                }
            }
        }
    }

}
