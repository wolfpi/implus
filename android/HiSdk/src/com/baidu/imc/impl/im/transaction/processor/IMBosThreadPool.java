package com.baidu.imc.impl.im.transaction.processor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

public class IMBosThreadPool {
    private ExecutorService mBosThreadPool;

    protected void initThreadPool() {
        if (mBosThreadPool == null || mBosThreadPool.isShutdown() || mBosThreadPool.isTerminated()) {
        	mBosThreadPool =
                    new ThreadPoolExecutor(10, 1000, Integer.MAX_VALUE, TimeUnit.SECONDS,
                            new ArrayBlockingQueue<Runnable>(1000), new DiscardPolicy());
        }
    }

    protected void executeBosTransaction(Runnable transaction) {
    	initThreadPool();
    	mBosThreadPool.execute(transaction);
    }

    protected void destroy() {
        if (mBosThreadPool != null) {
        	mBosThreadPool.shutdown();
        }
    }
}
