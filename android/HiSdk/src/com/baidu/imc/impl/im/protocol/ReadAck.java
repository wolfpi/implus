package com.baidu.imc.impl.im.protocol;


public class ReadAck {
    // public static void sendReadAck(IMInboxEntry entry, final IMessageResultCallback callback) {
    //
    // if(entry == null)
    // return;
    //
    // List<IMInboxEntry> entryList = new ArrayList<IMInboxEntry>();
    //
    // entryList.add(entry);
    //
    // sendReadAck(entryList, callback);
    // }
    //
    // public static void sendReadAck(List<IMInboxEntry> entryList, final IMessageResultCallback callback) {
    // if(entryList == null)
    // return ;
    //
    // ReadAckReq reqBuilder = new  ReadAckReq();  // migrate from builder
    // for (IMInboxEntry entry : entryList) {
    // ChatConversation conversationBuilder = new  ChatConversation();  // migrate from builder
    //
    // conversationBuilder.setChatId(((IMInboxEntryImpl) entry).getAddresseeID());
    // conversationBuilder.setChatType(EChatType.CHAT_P2P);
    // conversationBuilder.setLastReadMsgSeq(((IMInboxEntryImpl) entry).getLastReadMessageID());
    // conversationBuilder.setLastReadMsgTime(((IMInboxEntryImpl) entry).getLastReadMessageTime());
    // conversationBuilder.setLastRecvMsgSeq(((IMInboxEntryImpl) entry).getLastReceiveMessageID());
    // conversationBuilder.setLastRecvMsgTime(((IMInboxEntryImpl) entry).getLastReceiveMessageTime());
    //
    // reqBuilder.addConversations(conversationBuilder);
    //
    // BinaryMessage binaryMessage = new BinaryMessage();
    // binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_MSG.getName());
    // binaryMessage.setMethodName(MethodNameEnum.READ_ACK.getName());
    // binaryMessage.setData(reqBuilder.toByteArray());
    // ChannelSdk.send(binaryMessage, callback);
    // }
    // }
}
