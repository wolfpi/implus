package com.baidu.imc.impl.im.transaction.processor;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.ChannelSdk;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.impl.im.transaction.processor.callback.FileGetUploadSignCallback;
import com.baidu.imc.impl.im.transaction.request.IMFileGetUploadSignRequest;
import com.baidu.imc.impl.im.transaction.response.IMFileGetUploadSignResponse;

public class IMFileGetUploadSignProcessor implements IMProcessorStart {

    private static final String TAG = "UploadFileGetSignProcessor";

    private IMFileGetUploadSignRequest request;
    // private IMFileGetUploadSignResponse response;
    private FileGetUploadSignCallback mCallback = null;

    public IMFileGetUploadSignProcessor(IMFileGetUploadSignRequest request, FileGetUploadSignCallback callback) {
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
                ChannelSdk.send(msg, new IMessageResultCallback() {

                    @Override
                    public void onSuccess(String description, byte[] data) {
                        if (mCallback != null) {
                            mCallback.onFileGetUploadSignCallback(new IMFileGetUploadSignResponse(description, data, 0,
                                    request.getBmd5()));
                        }
                    }

                    @Override
                    public void onFail(int errorCode) {
                        if (mCallback != null) {
                            mCallback.onFileGetUploadSignCallback(new IMFileGetUploadSignResponse(null, null,
                                    errorCode, request.getBmd5()));
                        }
                    }
                });
                return;

                /*
                 * BinaryMessage binaryMessage = request.createRequest(); if (null != binaryMessage) { if
                 * (send(binaryMessage)) { response = new IMFileGetUploadSignResponse(getDescription(), getData(),
                 * getErrCode()); return; } else { LogUtil.printImE(getProcessorName() + " timeout."); } } else {
                 * LogUtil.printImE(getProcessorName() + " can not get binaryMessage."); }
                 */
            } else {
                LogUtil.printIm(getProcessorName(), "Can not get BinaryMessage.");
            }
        } else {
            LogUtil.printIm(getProcessorName(), "Can not get request.");
        }
        if (mCallback != null) {
            mCallback.onFileGetUploadSignCallback(new IMFileGetUploadSignResponse(null, null, -1, null));
        }
    }

    // public IMFileGetUploadSignResponse getResponse() {
    // return response;
    // }

}
