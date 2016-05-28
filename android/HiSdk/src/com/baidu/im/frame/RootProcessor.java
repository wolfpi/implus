package com.baidu.im.frame;

import java.util.TimerTask;

public class RootProcessor implements TimeoutCallback {
    private long mRetryTimes = 3;
    private int mTimeout = 8 * 1000;
    private GlobalTimer mGlobalTimer = GlobalTimerTasks.getInstance();
    private ProcessorStart mProcessor = null;
    ProcessorTimeCallback mProcessorCallback = null;

    public RootProcessor(ProcessorStart processor, ProcessorTimeCallback callback) {
        mProcessor = processor;
        mProcessorCallback = callback;
        mRetryTimes = 3;
    }

    /*
    public RootProcessor(ProcessorTimeCallback callback) {
        // mGlobalTimer = timer;
        mProcessorCallback = callback;
        mRetryTimes = 0;
    }*/

    public void startCountDown() {
        TimerTask task = new TimeoutTask(this);
        mGlobalTimer.addTask(this.hashCode(), task, mTimeout);
    }

    public void stopCountDown() {
        mGlobalTimer.removeTask(this.hashCode());
    }

    @Override
    public void timeoutCallback() {

        if (mRetryTimes > 0) {
            mProcessor.startWorkFlow();
            --mRetryTimes;
        } else {
            mProcessorCallback.processorTimeout();
            stopCountDown();
        }
    }
}
