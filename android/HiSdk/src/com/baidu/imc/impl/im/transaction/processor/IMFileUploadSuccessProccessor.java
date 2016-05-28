package com.baidu.imc.impl.im.transaction.processor;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.ChannelSdk;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.impl.im.transaction.processor.callback.FileUploadSuccessCallback;
import com.baidu.imc.impl.im.transaction.request.IMFileUploadSuccessRequest;
import com.baidu.imc.impl.im.transaction.response.IMFileUploadSuccessResponse;

public class IMFileUploadSuccessProccessor implements IMProcessorStart {

    private static final String TAG = "UploadFileSuccessProccessor";

    private IMFileUploadSuccessRequest request;
    private IMFileUploadSuccessResponse response;
    private FileUploadSuccessCallback mCallback = null;

    public IMFileUploadSuccessProccessor(IMFileUploadSuccessRequest request, FileUploadSuccessCallback callback) {
        this.request = request;
        this.mCallback = callback;
    }

    public String getProcessorName() {
        return TAG;
    }

    @Override
    public void startWorkFlow() throws Exception {
        if (null != request) {
            BinaryMessage msg = request.createRequest();
            if (msg != null) {
                ChannelSdk.send(request.createRequest(), new IMessageResultCallback() {

                    @Override
                    public void onSuccess(String description, byte[] data) {
                        LogUtil.printIm(getProcessorName(), "UploadSuccess Callback is called here");
                        if (mCallback != null) {
                            mCallback
                                    .onFileUploadSuccessCallback(new IMFileUploadSuccessResponse(description, data, 0));
                        }
                    }

                    @Override
                    public void onFail(int errorCode) {
                        if (mCallback != null) {
                            mCallback
                                    .onFileUploadSuccessCallback(new IMFileUploadSuccessResponse(null, null, errorCode));
                        }
                    }
                });
                return;
            } else {
                LogUtil.printIm(getProcessorName(), "Can not get message.");
            }
        } else {
            LogUtil.printIm(getProcessorName(), "Can not get request.");
        }
        if (mCallback != null) {
            mCallback.onFileUploadSuccessCallback(new IMFileUploadSuccessResponse(null, null, -1));
        }
    }

    public IMFileUploadSuccessResponse getResponse() {
        return response;
    }

}
