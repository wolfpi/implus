package com.baidu.imc.impl.im.transaction.processor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.ChannelSdk;
import com.baidu.im.sdk.IMessageResultCallback;

public abstract class IMBaseProcessor implements IMProcessor {

    private IMProcessorTimeout timeout = new IMProcessorTimeout();

    private CountDownLatch countDownLatch;

    private String desciption;
    private byte[] data;
    private int errCode;

    @Override
    public IMProcessorTimeout getTimeout() {
        return timeout;
    }

    protected void await() throws InterruptedException {
        countDownLatch = new CountDownLatch(1);
        countDownLatch.await(timeout.getValue(), TimeUnit.MILLISECONDS);

    }

    protected void resume() {
        countDownLatch.countDown();
    }

    public boolean isWaitTimeout() {
        return countDownLatch.getCount() > 0;
    }

    protected boolean send(BinaryMessage binaryMessage) {
        while (getTimeout().getLeftRetryTimes() > 0) {
            ChannelSdk.send(binaryMessage, new IMessageResultCallback() {

                @Override
                public void onSuccess(String description, byte[] data) {

                    // LogUtil.printImE("Callback is called here###############################");
                    setDesciption(description);
                    setData(data);
                    resume();
                }

                @Override
                public void onFail(int errorCode) {
                    setErrCode(errCode);
                    resume();
                }
            });
            // waiting for result
            try {
                await();
            } catch (InterruptedException e) {
                LogUtil.printImE(getProcessorName(), e);
            }
            // waiting for
            if (!isWaitTimeout()) {
                LogUtil.printIm(getProcessorName() + " Response Received.");
                return true;
            }
            getTimeout().retried();
            LogUtil.printImE(getProcessorName(), " Timeout, retryTimes: " + getTimeout().getLeftRetryTimes(), null);
        }
        LogUtil.printIm(getProcessorName() + " Timeout, methodName: " + binaryMessage.getMethodName()
                + " serviceName: " + binaryMessage.getServiceName());
        return false;
    }

    @Override
    public final void process() throws Exception {
        try {
            startWorkFlow();
        } finally {
            terminate();
        }

    }

    /**
     * Need to be inherited.
     * 
     * @return
     * @throws Exception
     */
    protected abstract void startWorkFlow() throws Exception;

    protected void terminate() {

    }

    @Override
    public String getDescription() {
        return desciption;
    }

    @Override
    public byte[] getData() {
        return data;
    }

    @Override
    public int getErrCode() {
        return errCode;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
