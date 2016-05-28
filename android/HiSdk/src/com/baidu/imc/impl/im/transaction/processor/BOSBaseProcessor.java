package com.baidu.imc.impl.im.transaction.processor;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.impl.im.protocol.file.HttpResult;

public abstract class BOSBaseProcessor implements BOSProcessor {

    private BOSProcessorTimeout timeout = new BOSProcessorTimeout();
    private HttpResult httpResult;

    @Override
    public void process() throws Exception {
        try {
            startWorkFlow();
        } catch (Exception e) {
            LogUtil.printImE(getProcessorName() + " process error", e);
        } finally {
            terminate();
        }
    }

    @Override
    public HttpResult getResult() {
        return httpResult;
    }

    @Override
    public BOSProcessorTimeout getTimeout() {
        return timeout;
    }

    // protected boolean sendHttpRequest(BossHttpRequest httpRequest) {
    // while (getTimeout().getLeftRetryTimes() > 0) {
    // try {
    // HttpResult tmpResult = BossHttpClient.sendRequest(httpRequest);
    // if (null != tmpResult && tmpResult.getStatusCode() != 400) {
    // httpResult = tmpResult;
    // LogUtil.printIm(getProcessorName(), "Http request is success");
    // return true;
    // }
    // } catch (IOException e) {
    // LogUtil.printImE(getProcessorName(), "SendHttpRequest error", e);
    // }
    // getTimeout().retried();
    // }
    // LogUtil.printIm(getProcessorName(), "The leftRetryTime is zero.");
    // return false;
    // }

    protected abstract void startWorkFlow();

    protected void terminate() {

    }
}
