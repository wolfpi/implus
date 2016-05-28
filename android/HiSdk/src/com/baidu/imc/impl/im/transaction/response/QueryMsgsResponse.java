package com.baidu.imc.impl.im.transaction.response;

import com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRsp;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.impl.im.callback.PageableResultImpl;
import com.baidu.imc.impl.im.message.BDHiIMMessage;
import com.baidu.imc.impl.im.message.OneMsgConverter;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class QueryMsgsResponse extends IMBaseResponse {

    private static final String TAG = "QueryMsgsResponse";
    private PageableResultImpl pageableResult;

    public QueryMsgsResponse(String description, byte[] data, int errCode) {
        super(description, data, errCode);
        if (errCode == 0 && null != getData()) {
            createResponse();
        }
    }

    @Override
    public void createResponse() {
        LogUtil.printIm(getResponseName() + " code:" + errCode);
        try {
            QueryMsgsRsp rspBuilder = new QueryMsgsRsp(); // migrate from builder

            rspBuilder.mergeFrom(data);

            pageableResult = new PageableResultImpl();

            long previousMsgSeq = 0;
            // 保证消息ID由小到大
            for (int i = 0; i < rspBuilder.getMsgsCount(); i++) {
                BDHiIMMessage imMessage = OneMsgConverter.convertServerMsg(rspBuilder.getMsgs(i));
                long msgSeq = imMessage.getMsgSeq();
                if (0 == previousMsgSeq) {
                    previousMsgSeq = msgSeq;
                }
                if (msgSeq < previousMsgSeq) {
                    pageableResult.addMessageFirst(imMessage);
                } else {
                    pageableResult.addMessageLast(imMessage);
                }
                previousMsgSeq = msgSeq;
            }
            pageableResult.setTotal(rspBuilder.getMsgsCount());
        } catch (InvalidProtocolBufferMicroException e) {
            LogUtil.printImE(getResponseName(), e);
        }
    }

    @Override
    public String getResponseName() {
        return TAG;
    }

    public PageableResultImpl getPageableResult() {
        return pageableResult;
    }

}
