package com.baidu.imc.impl.im.protocol;

public class QueryMsgs {
    // public static void queryMessages(AddresseeType addresseeType, String addresseeID, String addresserID,
    // long startSeq, long endSeq, int count, final PageableResultCallback<IMMessage> callback) {
    // QueryMsgsReq queryMsgsReqBuilder = new  QueryMsgsReq();  // migrate from builder
    // QueryMsgsRange rangeBuilder = new  QueryMsgsRange();  // migrate from builder
    // rangeBuilder.setChatType(EChatType.CHAT_P2P);
    //
    // String chatId;
    // if (addresseeID.compareTo(addresserID) > 0) {
    // chatId = addresserID + ":" + addresseeID;
    // } else {
    // chatId = addresseeID + ":" + addresserID;
    // }
    //
    // rangeBuilder.setChatId(chatId);
    // rangeBuilder.setCount(count);
    // rangeBuilder.setStartSeq(startSeq < 0 ? 0 : startSeq);
    // rangeBuilder.setEndSeq(endSeq < 0 ? Integer.MAX_VALUE : endSeq);
    // queryMsgsReqBuilder.addMsgRanges(rangeBuilder);
    //
    // BinaryMessage binaryMessage = new BinaryMessage();
    // binaryMessage.setServiceName(ServiceNameEnum.IM_PLUS_MSG.getName());
    // binaryMessage.setMethodName(MethodNameEnum.QUERY_MSGS.getName());
    // binaryMessage.setData(queryMsgsReqBuilder.toByteArray());
    // ChannelSdk.send(binaryMessage, new IMessageResultCallback() {
    // @Override
    // public void onSuccess(String description, byte[] data) {
    // try {
    // QueryMsgsRsp rspBuilder = new  QueryMsgsRsp();  // migrate from builder
    //
    // rspBuilder.mergeFrom(data);
    //
    // PageableResultImpl pageableResult = new PageableResultImpl();
    //
    // for (int i = 0; i < rspBuilder.getMsgsCount(); i++) {
    // IMMessage imMessage = OneMsgConverter.convertServerMsg(rspBuilder.getMsgs(i));
    // pageableResult.addMessage(imMessage);
    // }
    //
    // callback.result(pageableResult, null);
    // } catch (Exception e) {
    // callback.result(null, new Exception("server error:" + e.getMessage()));
    // }
    // }
    //
    // @Override
    // public void onFail(int errorCode) {
    // callback.result(null, new Exception("server error:" + errorCode));
    // }
    //
    // });
    // }
}
