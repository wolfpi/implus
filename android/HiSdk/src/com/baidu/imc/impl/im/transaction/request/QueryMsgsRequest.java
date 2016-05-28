package com.baidu.imc.impl.im.transaction.request;

import android.text.TextUtils;

import com.baidu.im.frame.pb.EnumChatType;
import com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange;
import com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsReq;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.imc.callback.PageableResultCallback;
import com.baidu.imc.impl.im.protocol.MethodNameEnum;
import com.baidu.imc.impl.im.protocol.ServiceNameEnum;
import com.baidu.imc.impl.im.util.ChatID;
import com.baidu.imc.message.IMMessage;
import com.baidu.imc.type.AddresseeType;

public class QueryMsgsRequest extends IMBaseRequest {

    private String tag = "QueryMsgsRequest";
    private AddresseeType addresseeType;
    private String addresseeID;
    private String addresserID;
    private long startSeq;
    private long endSeq;
    private int count;
    private PageableResultCallback<IMMessage> callback;

    // private void parameterCheck()
    // {
    // if(addresseeType == null || startSeq < 0 || endSeq<0 || count < 0)
    // throw new InitializationException();
    // }

    public QueryMsgsRequest(String tag, AddresseeType addresseeType, String addresseeID, String addresserID,
            long startSeq, long endSeq, int count, final PageableResultCallback<IMMessage> callback) {

        this.tag = tag;
        this.addresseeType = addresseeType;
        this.addresseeID = addresseeID;
        this.addresserID = addresserID;
        this.startSeq = startSeq;
        this.endSeq = endSeq;
        this.count = count;
        this.callback = callback;
        // parameterCheck();
    }

    @Override
    public BinaryMessage createRequest() {
        LogUtil.printIm(getRequestName(), "AddresseeType:" + addresseeType + " addresseeID:" + addresseeID
                + " addresserID:" + addresserID + "startSeq:" + startSeq + " endSeq:" + endSeq + " count:" + count);
        if (null != addresseeType && !TextUtils.isEmpty(addresseeID) && !TextUtils.isEmpty(addresserID)) {
            QueryMsgsReq queryMsgsReqBuilder = new QueryMsgsReq(); // migrate from builder
            QueryMsgsRange rangeBuilder = new QueryMsgsRange(); // migrate from builder
            String chatId = ChatID.getChatID(addresseeType, addresseeID, addresserID);
            if (TextUtils.isEmpty(chatId)) {
                LogUtil.printIm(getRequestName(), "Can not get ChatID.");
                return null;
            }
            switch (addresseeType) {
                case USER:
                    rangeBuilder.setChatType(EnumChatType.CHAT_P2P);
                    break;
                case GROUP:
                    rangeBuilder.setChatType(EnumChatType.CHAT_P2G);
                    break;
                case SERVICE:
                    rangeBuilder.setChatType(EnumChatType.CHAT_PA);
                    break;
                default:
                    LogUtil.printIm(getRequestName(), "Can not accept other addresseeType.");
                    return null;
            }
            rangeBuilder.setChatId(chatId);
            rangeBuilder.setCount(count);
            rangeBuilder.setStartSeq(startSeq < 0 ? 0 : startSeq);
            rangeBuilder.setEndSeq(endSeq < 0 ? Integer.MAX_VALUE : endSeq);
            queryMsgsReqBuilder.addMsgRanges(rangeBuilder);

            BinaryMessage binaryMessage = new BinaryMessage();
            binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_MSG.getName());
            binaryMessage.setMethodName(MethodNameEnum.QUERY_MSGS.getName());
            binaryMessage.setData(queryMsgsReqBuilder.toByteArray());
            return binaryMessage;
        } else {
            LogUtil.printIm(getRequestName(), "Params error.");
            return null;
        }
    }

    public PageableResultCallback<IMMessage> getCallback() {
        return callback;
    }

    @Override
    public String getRequestName() {
        return tag;
    }

}
