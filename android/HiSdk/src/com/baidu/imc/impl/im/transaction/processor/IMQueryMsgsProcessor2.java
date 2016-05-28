package com.baidu.imc.impl.im.transaction.processor;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.ChannelSdk;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.impl.im.transaction.processor.callback.QueryMsgCallback;
import com.baidu.imc.impl.im.transaction.request.QueryMsgsRequest2;
import com.baidu.imc.impl.im.transaction.response.QueryMsgsResponse;

public class IMQueryMsgsProcessor2 implements IMProcessorStart {

    private String tag = "IMQueryMsgsProcessor";
    private QueryMsgsRequest2 queryMsgs;
    private QueryMsgsResponse response;
    private QueryMsgCallback mCallback = null;

    public IMQueryMsgsProcessor2(String tag, QueryMsgsRequest2 queryMsgs, QueryMsgCallback callback) {
        this.tag = tag;
        this.queryMsgs = queryMsgs;
        this.mCallback = callback;
    }

    public String getProcessorName() {
        return tag;
    }

    @Override
    public void startWorkFlow() throws Exception {
        if (queryMsgs != null) {
            BinaryMessage binaryMessage = queryMsgs.createRequest();
            if (null != binaryMessage) {
                ChannelSdk.send(binaryMessage, new IMessageResultCallback() {

                    @Override
                    public void onSuccess(String description, byte[] data) {
                        if (mCallback != null) {
                            mCallback.onQueryMsgCallback(new QueryMsgsResponse(description, data, 0));
                        }
                    }

                    @Override
                    public void onFail(int errorCode) {
                        if (mCallback != null) {
                            mCallback.onQueryMsgCallback(new QueryMsgsResponse(null, null, errorCode));
                        }
                    }
                });
                return;
            } else {
                LogUtil.printIm(tag, "Can not get binaryMessage.");
            }
        } else {
            LogUtil.printIm(tag, "Can not get Request.");
        }
        if (mCallback != null) {
            mCallback.onQueryMsgCallback(new QueryMsgsResponse(null, null, -1));
        }
    }

    public QueryMsgsResponse getResponse() {
        return response;
    }

}
