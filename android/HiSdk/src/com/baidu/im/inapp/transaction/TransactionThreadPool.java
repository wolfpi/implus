package com.baidu.im.inapp.transaction;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

import com.baidu.im.frame.Transaction;

public class TransactionThreadPool {

    private ExecutorService sessionThreadPool;
    private ExecutorService messageThreadPool;

    public void initSessionThreadPool() {
        if (sessionThreadPool == null || sessionThreadPool.isShutdown() || sessionThreadPool.isTerminated()) {

            // 单线程执行，大等待队列，等待队列满时则直接丢弃新任务。
            sessionThreadPool =
                    new ThreadPoolExecutor(1, 1000, Integer.MAX_VALUE, TimeUnit.SECONDS,
                            new ArrayBlockingQueue<Runnable>(1000), new DiscardPolicy());
        }
    }

    private void initMessageThreadPool() {
        if (messageThreadPool == null || messageThreadPool.isShutdown() || messageThreadPool.isTerminated()) {
            messageThreadPool =
                    new ThreadPoolExecutor(10, 1000, Integer.MAX_VALUE, TimeUnit.SECONDS,
                            new ArrayBlockingQueue<Runnable>(1000), new DiscardPolicy());
        }
    }

    void executeSessionTransaction(Transaction transaction) {
        initSessionThreadPool();
        sessionThreadPool.execute(transaction);
    }

    void executeMessageTransaction(Transaction transaction) {
        initMessageThreadPool();
        messageThreadPool.execute(transaction);
    }

    void destroy() {
        if (sessionThreadPool != null) {
            sessionThreadPool.shutdown();
        }
        if (messageThreadPool != null) {
            messageThreadPool.shutdown();
        }
    }

}
