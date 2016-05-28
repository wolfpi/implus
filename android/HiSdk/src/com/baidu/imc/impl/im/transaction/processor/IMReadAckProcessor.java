package com.baidu.imc.impl.im.transaction.processor;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.ChannelSdk;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.impl.im.transaction.processor.callback.ReadAckCallback;
import com.baidu.imc.impl.im.transaction.request.ReadAckRequest;
import com.baidu.imc.impl.im.transaction.response.ReadAckResponse;

public class IMReadAckProcessor implements IMProcessorStart {

    private static final String TAG = "IMReadAckProcessor";
    private ReadAckRequest readAck;
    private ReadAckResponse response;
    private ReadAckCallback mCallback = null;

    public IMReadAckProcessor(ReadAckRequest readAck, ReadAckCallback callback) {
        this.readAck = readAck;
        this.mCallback = callback;
    }

    public String getProcessorName() {
        return TAG;
    }

    @Override
    public void startWorkFlow() throws Exception {

        if (readAck != null) {
            BinaryMessage msg = readAck.createRequest();
            if (msg != null) {
                ChannelSdk.send(msg, new IMessageResultCallback() {

                    @Override
                    public void onSuccess(String description, byte[] data) {
                        if (mCallback != null) {
                            mCallback.onReadAckCallback(new ReadAckResponse(description, data, 0));
                        } else {
                            LogUtil.printIm(getProcessorName(), "onSuccess: can not get callback.");
                        }
                    }

                    @Override
                    public void onFail(int errorCode) {
                        if (mCallback != null) {
                            mCallback.onReadAckCallback(new ReadAckResponse(null, null, errorCode));
                        } else {
                            LogUtil.printIm(getProcessorName(), "onFail: can not get callback.");
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
            mCallback.onReadAckCallback(new ReadAckResponse(null, null, -1));
        } else {
            LogUtil.printIm(getProcessorName(), "onFail: can not get callback.");
        }
    }

    public ReadAckResponse getResponse() {
        return response;
    }

}
