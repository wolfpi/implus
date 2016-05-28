package com.baidu.imc.impl.im.transaction;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

public class IMTransactionThreadPool {
    private ExecutorService messageThreadPool;

    protected void initMessageThreadPool() {
        if (messageThreadPool == null || messageThreadPool.isShutdown() || messageThreadPool.isTerminated()) {
            messageThreadPool =
                    new ThreadPoolExecutor(10, 1000, Integer.MAX_VALUE, TimeUnit.SECONDS,
                            new ArrayBlockingQueue<Runnable>(1000), new DiscardPolicy());
        }
    }

    protected void executeMessageTransaction(Runnable transaction) {
        initMessageThreadPool();
        messageThreadPool.execute(transaction);
    }

    protected void destroy() {
        if (messageThreadPool != null) {
            messageThreadPool.shutdown();
        }
    }
}
