package com.baidu.imc.impl.im.transaction;

import com.baidu.im.frame.pb.ObjOneMsg.OneMsg;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.callback.ResultCallback;
import com.baidu.imc.impl.im.message.IMMessageConvertor;
import com.baidu.imc.impl.im.message.BDHiIMTransientMessage;
import com.baidu.imc.impl.im.message.OneMsgConverter;
import com.baidu.imc.impl.im.transaction.processor.IMSendMsgProcessor;
import com.baidu.imc.impl.im.transaction.processor.callback.SendMsgCallback;
import com.baidu.imc.impl.im.transaction.request.SendMsgRequest;
import com.baidu.imc.impl.im.transaction.response.SendMsgResponse;
import com.baidu.imc.message.TransientMessage;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.IMMessageStatus;

public class IMSendTransientMessageTransaction implements IMTransactionStart, SendMsgCallback {

    private static final String TAG = "SendTransientMessage";
    private AddresseeType addresseeType;
    private String addresseeID;
    private String addresserID;
    private TransientMessage message;
    private ResultCallback<Boolean> callback;
    private BDHiIMTransientMessage mImMessageImpl = null;

    public IMSendTransientMessageTransaction(AddresseeType addresseeType, String addresseeID, String addresserID,
            TransientMessage message, ResultCallback<Boolean> callback) {
        this.addresseeType = addresseeType;
        this.addresseeID = addresseeID;
        this.addresserID = addresserID;
        this.message = message;
        this.callback = callback;
    }

    @Override
    public void startWorkFlow() throws Exception {
        LogUtil.printIm(getThreadName(), "IMSendTransientMessageTransaction transactionID=" + this.hashCode());
        LogUtil.printIm(getThreadName(), "AddresseeType: " + addresseeType + " AddresseeID:" + addresseeID
                + " AddresserID:" + addresserID);
        // Convert Message to IMMessage
        mImMessageImpl =
                IMMessageConvertor.convertTransientMessageToIMTransientMessage(addresseeType, addresseeID, addresserID,
                        message);
        if (null != mImMessageImpl) {
            LogUtil.printIm(getThreadName(), "Send transientMessage " + mImMessageImpl.toString());
            // Convert IMMessage to OneMsg
            OneMsg messageBuilder = OneMsgConverter.convertIMTransientMessageToOneMsg(mImMessageImpl);
            // Send OneMsg
            SendMsgRequest sendMsgRequest = new SendMsgRequest(messageBuilder);
            IMSendMsgProcessor sendMsgProcessor = new IMSendMsgProcessor(sendMsgRequest, this);
            sendMsgProcessor.startWorkFlow();
        } else {
            LogUtil.printIm(getThreadName(), "Can not get transientMessage. Send failed.");
            if (callback != null) {
                callback.result(false, null);
            }
        }
        // notify user
    }

    public String getThreadName() {
        return TAG;
    }

    @Override
    public void onSendMsgCallback(SendMsgResponse sendMsgResponse) {
        if (null != sendMsgResponse && sendMsgResponse.getErrCode() == 0) {
            LogUtil.printIm(getThreadName(), "TransientMessage sent.");
            mImMessageImpl.setMessageID(sendMsgResponse.getSeq());
            mImMessageImpl.setServerTime(sendMsgResponse.getServerTime());
            mImMessageImpl.setStatus(IMMessageStatus.SENT);
        } else {
            LogUtil.printIm(getThreadName(), "TransientMessage failed.");
            mImMessageImpl.setStatus(IMMessageStatus.FAILED);
        }
        if (null != callback) {
            if (null != mImMessageImpl && null != mImMessageImpl.getStatus()
                    && mImMessageImpl.getStatus() == IMMessageStatus.SENT) {
                callback.result(true, null);
            } else {
                callback.result(false, null);
            }
        }
    }

}
