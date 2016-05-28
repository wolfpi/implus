package com.baidu.imc.impl.im.transaction;

import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.impl.im.transaction.processor.IMReadAckProcessor;
import com.baidu.imc.impl.im.transaction.processor.callback.ReadAckCallback;
import com.baidu.imc.impl.im.transaction.request.ReadAckRequest;
import com.baidu.imc.impl.im.transaction.response.ReadAckResponse;
import com.baidu.imc.message.IMInboxEntry;
import com.baidu.imc.type.AddresseeType;

public class IMReadAckTransaction implements IMTransactionStart, ReadAckCallback {

    private static final String TAG = "IMReadAckTransaction";
    private AddresseeType addresseeType;
    private String addresserID;
    private String addresseeID;
    private IMInboxEntry entry;
    private IMessageResultCallback callback;

    public IMReadAckTransaction(AddresseeType addresseeType, String addresserID, String addresseeID,
            IMInboxEntry entry, IMessageResultCallback callback) {
        this.addresseeType = addresseeType;
        this.addresserID = addresserID;
        this.addresseeID = addresseeID;
        this.entry = entry;
        this.callback = callback;
    }

    @Override
    public void startWorkFlow() throws Exception {
        ReadAckRequest readAckRequest = new ReadAckRequest(addresseeType, addresserID, addresseeID, entry, callback);
        IMReadAckProcessor readAckProcessor = new IMReadAckProcessor(readAckRequest, this);
        readAckProcessor.startWorkFlow();
    }

    public String getThreadName() {
        return TAG;
    }

    @Override
    public void onReadAckCallback(ReadAckResponse readAckResponse) {
        if (null != callback) {
            if (null != readAckResponse && readAckResponse.getErrCode() == 0) {
                callback.onSuccess(readAckResponse.getDescription(), readAckResponse.getData());
            } else {
                callback.onFail(readAckResponse.getErrCode());
            }
        }
    }

}
