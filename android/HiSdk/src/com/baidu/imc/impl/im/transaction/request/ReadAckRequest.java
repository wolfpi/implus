package com.baidu.imc.impl.im.transaction.request;

import android.text.TextUtils;

import com.baidu.im.frame.pb.EnumChatType;
import com.baidu.im.frame.pb.ObjChatConversation.ChatConversation;
import com.baidu.im.frame.pb.ProReadAck.ReadAckReq;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.impl.im.message.IMInboxEntryImpl;
import com.baidu.imc.impl.im.message.OneMsgConverter;
import com.baidu.imc.impl.im.protocol.MethodNameEnum;
import com.baidu.imc.impl.im.protocol.ServiceNameEnum;
import com.baidu.imc.message.IMInboxEntry;
import com.baidu.imc.type.AddresseeType;

public class ReadAckRequest extends IMBaseRequest {

    private static final String TAG = "ReadActRequest";

    private AddresseeType addresseeType;
    private String addresserID;
    private String addresseeID;
    private IMInboxEntry entry;
    private IMessageResultCallback callback;

    public ReadAckRequest(AddresseeType addresseeType, String addresserID, String addresseeID, IMInboxEntry entry,
            IMessageResultCallback callback) {
        this.addresserID = addresserID;
        this.addresseeID = addresseeID;
        this.entry = entry;
        this.callback = callback;
        this.addresseeType = addresseeType;
    }

    @Override
    public BinaryMessage createRequest() {
    	
        if (null != addresseeType && !TextUtils.isEmpty(addresserID) && !TextUtils.isEmpty(addresseeID)
                && null != entry) {
            LogUtil.printIm(getRequestName(), "ID:" + entry.getID() + " addresserID:" + addresserID + " addresseeID:"
                    + addresseeID);
            ReadAckReq reqBuilder = new ReadAckReq(); // migrate from builder
            ChatConversation conversationBuilder = new ChatConversation(); // migrate from builder

            String chatId;
            if (addresserID.compareTo(addresseeID) > 0) {
                chatId = addresseeID + ":" + addresserID;
            } else {
                chatId = addresserID + ":" + addresseeID;
            }

            conversationBuilder.setChatId(chatId);
            int enumChatType = OneMsgConverter.enumChatTypeOf(addresseeType, -1);
            if(enumChatType != -1) {
                conversationBuilder.setChatType(enumChatType);
            }else{
                LogUtil.printIm(getRequestName(), "Can not accept other addresseeType.");
                return null;
            }

            conversationBuilder.setLastReadMsgSeq(((IMInboxEntryImpl) entry).getLastReadMessageID());
            conversationBuilder.setLastReadMsgTime(((IMInboxEntryImpl) entry).getLastReadMessageTime());
            conversationBuilder.setLastRecvMsgSeq(((IMInboxEntryImpl) entry).getLastReceiveMessageID());
            conversationBuilder.setLastRecvMsgTime(((IMInboxEntryImpl) entry).getLastReceiveMessageTime());

            reqBuilder.addConversations(conversationBuilder);

            BinaryMessage binaryMessage = new BinaryMessage();
            binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_MSG.getName());
            binaryMessage.setMethodName(MethodNameEnum.READ_ACK.getName());
            binaryMessage.setData(reqBuilder.toByteArray());
            return binaryMessage;
        } else {
            LogUtil.printIm(getRequestName(), "Params error.");
            return null;
        }
    }

    @Override
    public String getRequestName() {
        return TAG;
    }

    public IMessageResultCallback getCallback() {
        return callback;
    }

}
