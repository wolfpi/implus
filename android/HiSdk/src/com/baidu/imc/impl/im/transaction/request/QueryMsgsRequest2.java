package com.baidu.imc.impl.im.transaction.request;

import java.util.List;

import android.text.TextUtils;

import com.baidu.im.frame.pb.EnumChatType;
import com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange;
import com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsReq;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.imc.impl.im.protocol.MethodNameEnum;
import com.baidu.imc.impl.im.protocol.ServiceNameEnum;
import com.baidu.imc.impl.im.util.ChatID;
import com.baidu.imc.message.IMInboxEntry;

public class QueryMsgsRequest2 extends IMBaseRequest {

    private String tag = "QueryMsgsRequest";
    private String myID;
    private List<IMInboxEntry> imInboxs;

    public QueryMsgsRequest2(String tag, String myID, List<IMInboxEntry> imInboxs) {

        this.tag = tag;
        this.myID = myID;
        this.imInboxs = imInboxs;

    }

    @Override
    public BinaryMessage createRequest() {
        if (!TextUtils.isEmpty(myID) && null != imInboxs && !imInboxs.isEmpty()) {
            QueryMsgsReq queryMsgsReqBuilder = new QueryMsgsReq(); // migrate from builder
            for (IMInboxEntry inbox : imInboxs) {
                if (null != inbox && null != inbox.getAddresseeType() && !TextUtils.isEmpty(inbox.getAddresseeID())) {
                    LogUtil.printIm(getRequestName(), "AddresseeType:" + inbox.getAddresseeType() + " addresseeID:"
                            + inbox.getAddresseeID() + " myID:" + myID);
                    QueryMsgsRange rangeBuilder = new QueryMsgsRange();  // migrate from builder
                    String chatId = ChatID.getChatID(inbox.getAddresseeType(), inbox.getAddresseeID(), myID);
                    if (TextUtils.isEmpty(chatId)) {
                        LogUtil.printIm(getRequestName(), "Can not get ChatID.");
                        return null;
                    }
                    switch (inbox.getAddresseeType()) {
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
                    rangeBuilder.setCount(1);
                    rangeBuilder.setStartSeq(0);
                    rangeBuilder.setEndSeq(0);
                    queryMsgsReqBuilder.addMsgRanges(rangeBuilder);
                }
            }
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

    @Override
    public String getRequestName() {
        return tag;
    }

}
