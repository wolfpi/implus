package com.baidu.imc.impl.im.transaction.processor;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.impl.im.protocol.file.BossHttpRequest;
import com.baidu.imc.impl.im.protocol.file.HttpResult;
import com.baidu.imc.impl.im.transaction.processor.callback.BosUploadFileCallback;
import com.baidu.imc.impl.im.transaction.request.BOSUploadFileRequest;
import com.baidu.imc.impl.im.transaction.response.BOSUploadFileResponse;

public class BOSUploadFileProcessor implements IMProcessorStart, BosHttpRequestCallback {

    private static final String TAG = "UploadFileProcessor";

    private BOSUploadFileRequest request;
    private BOSUploadFileResponse response;
    private BossHttpRequest mHttpRequest = null;
    private BosUploadFileCallback mCallback = null;

    public BOSUploadFileProcessor(BOSUploadFileRequest request, BosUploadFileCallback callback) {
        this.request = request;
        this.mCallback = callback;
    }

    public String getProcessorName() {
        return TAG;
    }

    @Override
    public void startWorkFlow() {
        if (null != request) {
            mHttpRequest = request.createRequest();
            if (null != mHttpRequest) {
                mHttpRequest.setHttpRequestCallback(this);
                BosService.sendHttpRequest(mHttpRequest);
                return;
            } else {
                LogUtil.printIm(getProcessorName(), "Can not get httpRequest.");
            }
        } else {
            LogUtil.printIm(getProcessorName(), "Can not get request");
        }
        if (mCallback != null) {
            mCallback.onBosUploadFileCallback(response);
        }
    }

    public BOSUploadFileResponse getResponse() {
        return response;
    }

    @Override
    public void onBosCallbackResult(HttpResult result) {

        BOSUploadFileResponse response = null;
        if (null != result) {
            response = new BOSUploadFileResponse(result.getStatusCode(), 0, null);
            LogUtil.printIm(getProcessorName(), "Get response succeseed.");
        } else {
            LogUtil.printIm(getProcessorName(), "Can not get httpResult");
        }

        if (mCallback != null) {
            mCallback.onBosUploadFileCallback(response);
        }
    }
}
