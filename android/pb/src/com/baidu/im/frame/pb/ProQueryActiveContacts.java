// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ProQueryActiveContacts {
  private ProQueryActiveContacts() {}
  @SuppressWarnings("hiding")
  public static final class QueryActiveContactsReq extends
      com.google.protobuf.micro.MessageMicro {
    public QueryActiveContactsReq() {}

    // required uint64 lastQueryTime = 1;
    public static final int LASTQUERYTIME_FIELD_NUMBER = 1;
    private boolean hasLastQueryTime;
    private long lastQueryTime_ = 0L;
    public long getLastQueryTime() { return lastQueryTime_; }
    public boolean hasLastQueryTime() { return hasLastQueryTime; }
    public QueryActiveContactsReq setLastQueryTime(long value) {
      hasLastQueryTime = true;
      lastQueryTime_ = value;
      return this;
    }
    public QueryActiveContactsReq clearLastQueryTime() {
      hasLastQueryTime = false;
      lastQueryTime_ = 0L;
      return this;
    }

    // optional uint32 needReturnMsgsContactsNum = 2;
    public static final int NEEDRETURNMSGSCONTACTSNUM_FIELD_NUMBER = 2;
    private boolean hasNeedReturnMsgsContactsNum;
    private int needReturnMsgsContactsNum_ = 0;
    public int getNeedReturnMsgsContactsNum() { return needReturnMsgsContactsNum_; }
    public boolean hasNeedReturnMsgsContactsNum() { return hasNeedReturnMsgsContactsNum; }
    public QueryActiveContactsReq setNeedReturnMsgsContactsNum(int value) {
      hasNeedReturnMsgsContactsNum = true;
      needReturnMsgsContactsNum_ = value;
      return this;
    }
    public QueryActiveContactsReq clearNeedReturnMsgsContactsNum() {
      hasNeedReturnMsgsContactsNum = false;
      needReturnMsgsContactsNum_ = 0;
      return this;
    }

    // optional uint32 needReturnMsgsNum = 3;
    public static final int NEEDRETURNMSGSNUM_FIELD_NUMBER = 3;
    private boolean hasNeedReturnMsgsNum;
    private int needReturnMsgsNum_ = 0;
    public int getNeedReturnMsgsNum() { return needReturnMsgsNum_; }
    public boolean hasNeedReturnMsgsNum() { return hasNeedReturnMsgsNum; }
    public QueryActiveContactsReq setNeedReturnMsgsNum(int value) {
      hasNeedReturnMsgsNum = true;
      needReturnMsgsNum_ = value;
      return this;
    }
    public QueryActiveContactsReq clearNeedReturnMsgsNum() {
      hasNeedReturnMsgsNum = false;
      needReturnMsgsNum_ = 0;
      return this;
    }

    public final QueryActiveContactsReq clear() {
      clearLastQueryTime();
      clearNeedReturnMsgsContactsNum();
      clearNeedReturnMsgsNum();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      if (!hasLastQueryTime) return false;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasLastQueryTime()) {
        output.writeUInt64(1, getLastQueryTime());
      }
      if (hasNeedReturnMsgsContactsNum()) {
        output.writeUInt32(2, getNeedReturnMsgsContactsNum());
      }
      if (hasNeedReturnMsgsNum()) {
        output.writeUInt32(3, getNeedReturnMsgsNum());
      }
    }

    private int cachedSize = -1;
    @Override
    public int getCachedSize() {
      if (cachedSize < 0) {
        // getSerializedSize sets cachedSize
        getSerializedSize();
      }
      return cachedSize;
    }

    @Override
    public int getSerializedSize() {
      int size = 0;
      if (hasLastQueryTime()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt64Size(1, getLastQueryTime());
      }
      if (hasNeedReturnMsgsContactsNum()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt32Size(2, getNeedReturnMsgsContactsNum());
      }
      if (hasNeedReturnMsgsNum()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt32Size(3, getNeedReturnMsgsNum());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public QueryActiveContactsReq mergeFrom(
        com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      while (true) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            return this;
          default: {
            if (!parseUnknownField(input, tag)) {
              return this;
            }
            break;
          }
          case 8: {
            setLastQueryTime(input.readUInt64());
            break;
          }
          case 16: {
            setNeedReturnMsgsContactsNum(input.readUInt32());
            break;
          }
          case 24: {
            setNeedReturnMsgsNum(input.readUInt32());
            break;
          }
        }
      }
    }

    public static QueryActiveContactsReq parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (QueryActiveContactsReq) (new QueryActiveContactsReq().mergeFrom(data));
    }

    public static QueryActiveContactsReq parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new QueryActiveContactsReq().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class QueryActiveContactsRsp extends
      com.google.protobuf.micro.MessageMicro {
    public QueryActiveContactsRsp() {}

    // required uint64 queryTime = 1;
    public static final int QUERYTIME_FIELD_NUMBER = 1;
    private boolean hasQueryTime;
    private long queryTime_ = 0L;
    public long getQueryTime() { return queryTime_; }
    public boolean hasQueryTime() { return hasQueryTime; }
    public QueryActiveContactsRsp setQueryTime(long value) {
      hasQueryTime = true;
      queryTime_ = value;
      return this;
    }
    public QueryActiveContactsRsp clearQueryTime() {
      hasQueryTime = false;
      queryTime_ = 0L;
      return this;
    }

    // repeated .ChatConversation conversations = 2;
    public static final int CONVERSATIONS_FIELD_NUMBER = 2;
    private java.util.List<com.baidu.im.frame.pb.ObjChatConversation.ChatConversation> conversations_ =
      java.util.Collections.emptyList();
    public java.util.List<com.baidu.im.frame.pb.ObjChatConversation.ChatConversation> getConversationsList() {
      return conversations_;
    }
    public int getConversationsCount() { return conversations_.size(); }
    public com.baidu.im.frame.pb.ObjChatConversation.ChatConversation getConversations(int index) {
      return conversations_.get(index);
    }
    public QueryActiveContactsRsp setConversations(int index, com.baidu.im.frame.pb.ObjChatConversation.ChatConversation value) {
      if (value == null) {
        throw new NullPointerException();
      }
      conversations_.set(index, value);
      return this;
    }
    public QueryActiveContactsRsp addConversations(com.baidu.im.frame.pb.ObjChatConversation.ChatConversation value) {
      if (value == null) {
        throw new NullPointerException();
      }
      if (conversations_.isEmpty()) {
        conversations_ = new java.util.ArrayList<com.baidu.im.frame.pb.ObjChatConversation.ChatConversation>();
      }
      conversations_.add(value);
      return this;
    }
    public QueryActiveContactsRsp clearConversations() {
      conversations_ = java.util.Collections.emptyList();
      return this;
    }

    // repeated .OneMsg msgs = 3;
    public static final int MSGS_FIELD_NUMBER = 3;
    private java.util.List<com.baidu.im.frame.pb.ObjOneMsg.OneMsg> msgs_ =
      java.util.Collections.emptyList();
    public java.util.List<com.baidu.im.frame.pb.ObjOneMsg.OneMsg> getMsgsList() {
      return msgs_;
    }
    public int getMsgsCount() { return msgs_.size(); }
    public com.baidu.im.frame.pb.ObjOneMsg.OneMsg getMsgs(int index) {
      return msgs_.get(index);
    }
    public QueryActiveContactsRsp setMsgs(int index, com.baidu.im.frame.pb.ObjOneMsg.OneMsg value) {
      if (value == null) {
        throw new NullPointerException();
      }
      msgs_.set(index, value);
      return this;
    }
    public QueryActiveContactsRsp addMsgs(com.baidu.im.frame.pb.ObjOneMsg.OneMsg value) {
      if (value == null) {
        throw new NullPointerException();
      }
      if (msgs_.isEmpty()) {
        msgs_ = new java.util.ArrayList<com.baidu.im.frame.pb.ObjOneMsg.OneMsg>();
      }
      msgs_.add(value);
      return this;
    }
    public QueryActiveContactsRsp clearMsgs() {
      msgs_ = java.util.Collections.emptyList();
      return this;
    }

    public final QueryActiveContactsRsp clear() {
      clearQueryTime();
      clearConversations();
      clearMsgs();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      if (!hasQueryTime) return false;
      for (com.baidu.im.frame.pb.ObjChatConversation.ChatConversation element : getConversationsList()) {
        if (!element.isInitialized()) return false;
      }
      for (com.baidu.im.frame.pb.ObjOneMsg.OneMsg element : getMsgsList()) {
        if (!element.isInitialized()) return false;
      }
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasQueryTime()) {
        output.writeUInt64(1, getQueryTime());
      }
      for (com.baidu.im.frame.pb.ObjChatConversation.ChatConversation element : getConversationsList()) {
        output.writeMessage(2, element);
      }
      for (com.baidu.im.frame.pb.ObjOneMsg.OneMsg element : getMsgsList()) {
        output.writeMessage(3, element);
      }
    }

    private int cachedSize = -1;
    @Override
    public int getCachedSize() {
      if (cachedSize < 0) {
        // getSerializedSize sets cachedSize
        getSerializedSize();
      }
      return cachedSize;
    }

    @Override
    public int getSerializedSize() {
      int size = 0;
      if (hasQueryTime()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt64Size(1, getQueryTime());
      }
      for (com.baidu.im.frame.pb.ObjChatConversation.ChatConversation element : getConversationsList()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeMessageSize(2, element);
      }
      for (com.baidu.im.frame.pb.ObjOneMsg.OneMsg element : getMsgsList()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeMessageSize(3, element);
      }
      cachedSize = size;
      return size;
    }

    @Override
    public QueryActiveContactsRsp mergeFrom(
        com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      while (true) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            return this;
          default: {
            if (!parseUnknownField(input, tag)) {
              return this;
            }
            break;
          }
          case 8: {
            setQueryTime(input.readUInt64());
            break;
          }
          case 18: {
            com.baidu.im.frame.pb.ObjChatConversation.ChatConversation value = new com.baidu.im.frame.pb.ObjChatConversation.ChatConversation();
            input.readMessage(value);
            addConversations(value);
            break;
          }
          case 26: {
            com.baidu.im.frame.pb.ObjOneMsg.OneMsg value = new com.baidu.im.frame.pb.ObjOneMsg.OneMsg();
            input.readMessage(value);
            addMsgs(value);
            break;
          }
        }
      }
    }

    public static QueryActiveContactsRsp parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (QueryActiveContactsRsp) (new QueryActiveContactsRsp().mergeFrom(data));
    }

    public static QueryActiveContactsRsp parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new QueryActiveContactsRsp().mergeFrom(input);
    }

  }

}