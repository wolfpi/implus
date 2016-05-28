package com.baidu.imc.impl.im.transaction.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.im.frame.pb.ObjChatConversation.ChatConversation;
import com.baidu.im.frame.pb.ProQueryActiveContacts.QueryActiveContactsRsp;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.exception.InitializationException;
import com.baidu.imc.impl.im.message.IMInboxEntryImpl;
import com.baidu.imc.impl.im.util.ChatID;
import com.baidu.imc.message.IMInboxEntry;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class QueryActiveContactsResponse extends IMBaseResponse {

    private static final String TAG = "QueryActiveContactsResponse";
    private String myUserID;
    private long lastQueryTime;
    private Map<String, IMInboxEntry> entryMap = new HashMap<String, IMInboxEntry>();
    private List<IMInboxEntry> entryList = new ArrayList<IMInboxEntry>();

    public QueryActiveContactsResponse(String myUserID, String description, byte[] data, int errCode) {
        super();
        this.description = description;
        this.data = data;
        this.errCode = errCode;
        this.myUserID = myUserID;
        if (myUserID == null) {
            throw new InitializationException();
        }
        if (errCode == 0 && null != getData()) {
            createResponse();
        }
    }

    @Override
    public void createResponse() {
        LogUtil.printIm(getResponseName(), "Code:" + errCode);
        try {
            // 00. Prepare response.
            QueryActiveContactsRsp rspBuilder = new QueryActiveContactsRsp(); // migrate from builder
            rspBuilder.mergeFrom(data);

            // 01. Prepare messages.
            // LogUtil.printIm(getResponseName(), "Start to prepare messages.");
            // Map<String, OneMsg> lastMessages = new HashMap<String, OneMsg>();
            // if (rspBuilder.getMsgsCount() > 0) {
            // for (OneMsg message : rspBuilder.getMsgsList()) {
            // if (null != message) {
            // AddresseeType addresseeType;
            // switch (message.getChatType()) {
            // case CHAT_P2P:
            // addresseeType = AddresseeType.USER;
            // break;
            // case CHAT_P2G:
            // addresseeType = AddresseeType.GROUP;
            // break;
            // case CHAT_PA:
            // addresseeType = AddresseeType.SERVICE;
            // break;
            // default:
            // continue;
            // }
            // String addresseeID = message.getToId();
            // String addresserID = message.getFromId();
            // String key = ChatID.getChatID(addresseeType, addresseeID, addresserID);
            // if (!TextUtils.isEmpty(key)) {
            // lastMessages.put(key, message);
            // LogUtil.printIm(getResponseName(),
            // "Put a message into map. chatType:" + message.getChatType() + " key:" + key);
            // }
            // }
            // }
            // } else {
            // LogUtil.printIm(getResponseName(), "Do not contains any messages.");
            // }

            // 02. Prepare contacts.
            LogUtil.printIm(getResponseName(), "Start to get contacts.");
            if (rspBuilder.getConversationsCount() > 0) {
                for (ChatConversation serverConversation : rspBuilder.getConversationsList()) {
                    if (null != serverConversation) {
                        String key = serverConversation.getChatId();
                        // if (lastMessages.containsKey(key)) {
                        // OneMsg oneMsg = lastMessages.get(key);
                        // if (null != oneMsg) {
                        IMInboxEntryImpl inboxEntry = new IMInboxEntryImpl();
                        inboxEntry.setIneffective(false);
                        // boolean result =
                        // ChatID.setChatID(inboxEntry, serverConversation.getChatType(), myUserID,
                        // oneMsg.getFromId(), oneMsg.getFromName(), oneMsg.getToId());
                        boolean result = ChatID.setChatID(inboxEntry, serverConversation.getChatType(), myUserID, key);
                        if (!result) {
                            continue;
                        }
                        // inboxEntry.setLastMessage(OneMsgConverter.convertServerMsg(oneMsg));
                        // inboxEntry.setMsgBody(oneMsg.toByteArray());
                        inboxEntry.setLastReadMessageID(serverConversation.getLastReadMsgSeq());
                        inboxEntry.setLastReadMessageTime(serverConversation.getLastReadMsgTime());
                        inboxEntry.setLastReceiveMessageID(serverConversation.getLastRecvMsgSeq());
                        inboxEntry.setLastReceiveMessageTime(serverConversation.getLastRecvMsgTime());
                        int unreadCount =
                                (int) (inboxEntry.getLastReceiveMessageID() - inboxEntry.getLastReadMessageID());
                        inboxEntry.setUnreadCount(unreadCount > 0 ? unreadCount : 0);
                        entryMap.put(key, inboxEntry);
                        if(inboxEntry != null)
                        	entryList.add(inboxEntry);
                        LogUtil.printIm(getResponseName(), "Put a inbox." + inboxEntry.toString());
                        // }
                        // }
                    }
                }
            } else {
                LogUtil.printIm(getResponseName(), "Do not contains any inboxs.");
            }

            // 03.Get QueryTime.
            lastQueryTime = rspBuilder.getQueryTime();
            LogUtil.printIm(getResponseName(), "Start to get QueryTime. " + lastQueryTime);
        } catch (InvalidProtocolBufferMicroException e) {
            LogUtil.printImE(getResponseName(), e);
        }
    }

    @Override
    public String getResponseName() {
        return TAG;
    }

    public Map<String, IMInboxEntry> getEntryMap() {
        return entryMap;
    }
    
    public List<IMInboxEntry> getEntryList() {
        return entryList;
    }

    public long getLastQueryTime() {
        return lastQueryTime;
    }

}
