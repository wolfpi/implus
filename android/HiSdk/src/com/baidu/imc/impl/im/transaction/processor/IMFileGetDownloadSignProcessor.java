package com.baidu.imc.impl.im.transaction.processor;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.ChannelSdk;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.impl.im.transaction.processor.callback.FileGetDownloadSignCallback;
import com.baidu.imc.impl.im.transaction.request.IMFileGetDownloadSignRequest;
import com.baidu.imc.impl.im.transaction.response.IMFileGetDownloadSignResponse;

public class IMFileGetDownloadSignProcessor implements IMProcessorStart {

    private static final String TAG = "DownloadFileGetSignProcessor";

    private IMFileGetDownloadSignRequest request;
    private IMFileGetDownloadSignResponse response;
    private FileGetDownloadSignCallback mCallback = null;

    public IMFileGetDownloadSignProcessor(IMFileGetDownloadSignRequest request, FileGetDownloadSignCallback callback) {
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
                        LogUtil.printIm(getProcessorName(), "Callback is called here");
                        if (mCallback != null) {
                            mCallback.onFileGetDownloadSignCallback(new IMFileGetDownloadSignResponse(description,
                                    data, 0));
                        }
                    }

                    @Override
                    public void onFail(int errorCode) {
                        if (mCallback != null) {
                            mCallback.onFileGetDownloadSignCallback(new IMFileGetDownloadSignResponse(null, null,
                                    errorCode));
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
            mCallback.onFileGetDownloadSignCallback(new IMFileGetDownloadSignResponse(null, null, -1));
        }
    }

    public IMFileGetDownloadSignResponse getResponse() {
        return response;
    }

}
