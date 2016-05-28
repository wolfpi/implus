package com.baidu.imc.impl.im.transaction.response;

import com.baidu.im.frame.pb.ProSendMsg.SendMsgRsp;
import com.baidu.im.frame.utils.LogUtil;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class SendMsgResponse extends IMBaseResponse {

    private static final String TAG = "SendMsgResponse";
    public long previousMessageID;
    public long serverTime;
    public long seq;

    public SendMsgResponse(String description, byte[] data, int errCode) {
        super(description, data, errCode);
        if (errCode == 0 && null != getData()) {
            createResponse();
        }
    }

    @Override
    public void createResponse() {
        LogUtil.printIm(getResponseName() + " code:" + errCode);
        try {
            SendMsgRsp rspBuilder = new SendMsgRsp(); // migrate from builder
            rspBuilder.mergeFrom(data);
            previousMessageID = rspBuilder.getPreviousSeq();
            serverTime = rspBuilder.getServerTime();
            seq = rspBuilder.getSeq();
        } catch (InvalidProtocolBufferMicroException e) {
            LogUtil.printImE(getResponseName(), e);
        }
    }

    @Override
    public String getResponseName() {
        return TAG;
    }

    public long getServerTime() {
        return serverTime;
    }

    public long getSeq() {
        return seq;
    }

    public long getPreviousMessageID() {
        return previousMessageID;
    }

}
