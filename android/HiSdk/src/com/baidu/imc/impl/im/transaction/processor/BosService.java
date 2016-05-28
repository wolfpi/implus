package com.baidu.imc.impl.im.transaction.processor;

import java.io.IOException;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.impl.im.protocol.file.BossHttpClient;
import com.baidu.imc.impl.im.protocol.file.BossHttpRequest;
import com.baidu.imc.impl.im.protocol.file.HttpResult;

public class BosService {

    private static IMBosThreadPool mThreadPool = new IMBosThreadPool();

    public static boolean sendHttpRequest(BossHttpRequest httpRequest) {
        BosTransaction transaction = new BosTransaction(httpRequest);
        mThreadPool.executeBosTransaction(transaction);
        return true;
    }

    public static void destroy() {
        mThreadPool.destroy();
    }
}

final class BosTransaction implements Runnable {

    private static final String TAG = "BosService";

    private BossHttpRequest mHttpRequest = null;

    public BosTransaction(BossHttpRequest httpRequest) {
        mHttpRequest = httpRequest;
    }

    @Override
    public void run() {
        BosHttpRequestCallback callback = mHttpRequest.getHttpRequestCallback();
        try {
            HttpResult tmpResult = BossHttpClient.sendRequest(mHttpRequest);
            if (null != tmpResult && tmpResult.getStatusCode() != 400) {
                LogUtil.printIm(TAG, "BOSHttp request is succeed.");
            } else {
                LogUtil.printIm(TAG, "BOSHttp request is failed.");
            }
            if (callback != null) {
                callback.onBosCallbackResult(tmpResult);
            }
        } catch (IOException e) {
            LogUtil.printImE(TAG, "SendHttpRequest error", e);

            if (callback != null) {
                callback.onBosCallbackResult(null);
            }
        }
    }
}