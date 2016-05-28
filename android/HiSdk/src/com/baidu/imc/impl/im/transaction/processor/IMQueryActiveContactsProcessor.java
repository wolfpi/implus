package com.baidu.imc.impl.im.transaction.processor;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.ChannelSdk;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.impl.im.transaction.processor.callback.QueryActiveContactsCallback;
import com.baidu.imc.impl.im.transaction.request.QueryActiveContactsRequest;
import com.baidu.imc.impl.im.transaction.response.QueryActiveContactsResponse;

public class IMQueryActiveContactsProcessor implements IMProcessorStart {

    private static final String TAG = "IMQueryActiveContactsProcessor";
    private String myUserID;
    private QueryActiveContactsRequest queryActiveContacts;
    private QueryActiveContactsCallback mCallback = null;

    public IMQueryActiveContactsProcessor(String myUserID, QueryActiveContactsRequest queryActiveContacts,
            QueryActiveContactsCallback callback) {
        this.myUserID = myUserID;
        this.queryActiveContacts = queryActiveContacts;
        this.mCallback = callback;
    }

    public String getProcessorName() {
        return TAG;
    }

    @Override
    public void startWorkFlow() throws Exception {

        if (queryActiveContacts != null) {
            BinaryMessage msg = queryActiveContacts.createRequest();
            if (msg != null) {
                ChannelSdk.send(msg, new IMessageResultCallback() {
                    @Override
                    public void onSuccess(String description, byte[] data) {
                        if (mCallback != null) {
                            mCallback.onQueryActiveContactsCallback(new QueryActiveContactsResponse(myUserID,
                                    description, data, 0));
                        } else {
                            LogUtil.printIm(getProcessorName(), "Can not get callback.");
                        }
                    }

                    @Override
                    public void onFail(int errorCode) {
                        if (mCallback != null) {
                            mCallback.onQueryActiveContactsCallback(new QueryActiveContactsResponse(myUserID, null,
                                    null, errorCode));
                        } else {
                            LogUtil.printIm(getProcessorName(), "Can not get callback.");
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
            mCallback.onQueryActiveContactsCallback(new QueryActiveContactsResponse(myUserID, null, null, -1));
        } else {
            LogUtil.printIm(getProcessorName(), "Can not get callback.");
        }
    }

}
