// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ProQueryMsgs {
  private ProQueryMsgs() {}
  @SuppressWarnings("hiding")
  public static final class QueryMsgsRange extends
      com.google.protobuf.micro.MessageMicro {
    public QueryMsgsRange() {}

    // required .EChatType chatType = 1;
    public static final int CHATTYPE_FIELD_NUMBER = 1;
    private boolean hasChatType;
    private int chatType_ = com.baidu.im.frame.pb.EnumChatType.CHAT_P2P;
    public boolean hasChatType() { return hasChatType; }
    public int getChatType() { return chatType_; }
    public QueryMsgsRange setChatType(int value) {
      hasChatType = true;
      chatType_ = value;
      return this;
    }
    public QueryMsgsRange clearChatType() {
      hasChatType = false;
      chatType_ = com.baidu.im.frame.pb.EnumChatType.CHAT_P2P;
      return this;
    }

    // required string chatId = 2;
    public static final int CHATID_FIELD_NUMBER = 2;
    private boolean hasChatId;
    private java.lang.String chatId_ = "";
    public java.lang.String getChatId() { return chatId_; }
    public boolean hasChatId() { return hasChatId; }
    public QueryMsgsRange setChatId(java.lang.String value) {
      hasChatId = true;
      chatId_ = value;
      return this;
    }
    public QueryMsgsRange clearChatId() {
      hasChatId = false;
      chatId_ = "";
      return this;
    }

    // optional uint64 startSeq = 3;
    public static final int STARTSEQ_FIELD_NUMBER = 3;
    private boolean hasStartSeq;
    private long startSeq_ = 0L;
    public long getStartSeq() { return startSeq_; }
    public boolean hasStartSeq() { return hasStartSeq; }
    public QueryMsgsRange setStartSeq(long value) {
      hasStartSeq = true;
      startSeq_ = value;
      return this;
    }
    public QueryMsgsRange clearStartSeq() {
      hasStartSeq = false;
      startSeq_ = 0L;
      return this;
    }

    // optional uint64 endSeq = 4;
    public static final int ENDSEQ_FIELD_NUMBER = 4;
    private boolean hasEndSeq;
    private long endSeq_ = 0L;
    public long getEndSeq() { return endSeq_; }
    public boolean hasEndSeq() { return hasEndSeq; }
    public QueryMsgsRange setEndSeq(long value) {
      hasEndSeq = true;
      endSeq_ = value;
      return this;
    }
    public QueryMsgsRange clearEndSeq() {
      hasEndSeq = false;
      endSeq_ = 0L;
      return this;
    }

    // optional uint32 count = 5;
    public static final int COUNT_FIELD_NUMBER = 5;
    private boolean hasCount;
    private int count_ = 0;
    public int getCount() { return count_; }
    public boolean hasCount() { return hasCount; }
    public QueryMsgsRange setCount(int value) {
      hasCount = true;
      count_ = value;
      return this;
    }
    public QueryMsgsRange clearCount() {
      hasCount = false;
      count_ = 0;
      return this;
    }

    public final QueryMsgsRange clear() {
      clearChatType();
      clearChatId();
      clearStartSeq();
      clearEndSeq();
      clearCount();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      if (!hasChatType) return false;
      if (!hasChatId) return false;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasChatType()) {
        output.writeInt32(1, getChatType());
      }
      if (hasChatId()) {
        output.writeString(2, getChatId());
      }
      if (hasStartSeq()) {
        output.writeUInt64(3, getStartSeq());
      }
      if (hasEndSeq()) {
        output.writeUInt64(4, getEndSeq());
      }
      if (hasCount()) {
        output.writeUInt32(5, getCount());
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
      if (hasChatType()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeInt32Size(1, getChatType());
      }
      if (hasChatId()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(2, getChatId());
      }
      if (hasStartSeq()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt64Size(3, getStartSeq());
      }
      if (hasEndSeq()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt64Size(4, getEndSeq());
      }
      if (hasCount()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt32Size(5, getCount());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public QueryMsgsRange mergeFrom(
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
              setChatType(input.readInt32());
            break;
          }
          case 18: {
            setChatId(input.readString());
            break;
          }
          case 24: {
            setStartSeq(input.readUInt64());
            break;
          }
          case 32: {
            setEndSeq(input.readUInt64());
            break;
          }
          case 40: {
            setCount(input.readUInt32());
            break;
          }
        }
      }
    }

    public static QueryMsgsRange parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (QueryMsgsRange) (new QueryMsgsRange().mergeFrom(data));
    }

    public static QueryMsgsRange parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new QueryMsgsRange().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class QueryMsgsRangeStatus extends
      com.google.protobuf.micro.MessageMicro {
    public QueryMsgsRangeStatus() {}

    // required .QueryMsgsRange requestRange = 1;
    public static final int REQUESTRANGE_FIELD_NUMBER = 1;
    private boolean hasRequestRange;
    private com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange requestRange_ = null;
    public boolean hasRequestRange() { return hasRequestRange; }
    public com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange getRequestRange() { return requestRange_; }
    public QueryMsgsRangeStatus setRequestRange(com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange value) {
      if (value == null) {
        throw new NullPointerException();
      }
      hasRequestRange = true;
      requestRange_ = value;
      return this;
    }
    public QueryMsgsRangeStatus clearRequestRange() {
      hasRequestRange = false;
      requestRange_ = null;
      return this;
    }

    // required bool needCheckMoreMsgs = 2;
    public static final int NEEDCHECKMOREMSGS_FIELD_NUMBER = 2;
    private boolean hasNeedCheckMoreMsgs;
    private boolean needCheckMoreMsgs_ = false;
    public boolean getNeedCheckMoreMsgs() { return needCheckMoreMsgs_; }
    public boolean hasNeedCheckMoreMsgs() { return hasNeedCheckMoreMsgs; }
    public QueryMsgsRangeStatus setNeedCheckMoreMsgs(boolean value) {
      hasNeedCheckMoreMsgs = true;
      needCheckMoreMsgs_ = value;
      return this;
    }
    public QueryMsgsRangeStatus clearNeedCheckMoreMsgs() {
      hasNeedCheckMoreMsgs = false;
      needCheckMoreMsgs_ = false;
      return this;
    }

    public final QueryMsgsRangeStatus clear() {
      clearRequestRange();
      clearNeedCheckMoreMsgs();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      if (!hasRequestRange) return false;
      if (!hasNeedCheckMoreMsgs) return false;
      if (!getRequestRange().isInitialized()) return false;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasRequestRange()) {
        output.writeMessage(1, getRequestRange());
      }
      if (hasNeedCheckMoreMsgs()) {
        output.writeBool(2, getNeedCheckMoreMsgs());
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
      if (hasRequestRange()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeMessageSize(1, getRequestRange());
      }
      if (hasNeedCheckMoreMsgs()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeBoolSize(2, getNeedCheckMoreMsgs());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public QueryMsgsRangeStatus mergeFrom(
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
          case 10: {
            com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange value = new com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange();
            input.readMessage(value);
            setRequestRange(value);
            break;
          }
          case 16: {
            setNeedCheckMoreMsgs(input.readBool());
            break;
          }
        }
      }
    }

    public static QueryMsgsRangeStatus parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (QueryMsgsRangeStatus) (new QueryMsgsRangeStatus().mergeFrom(data));
    }

    public static QueryMsgsRangeStatus parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new QueryMsgsRangeStatus().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class QueryMsgsReq extends
      com.google.protobuf.micro.MessageMicro {
    public QueryMsgsReq() {}

    // repeated .QueryMsgsRange msgRanges = 1;
    public static final int MSGRANGES_FIELD_NUMBER = 1;
    private java.util.List<com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange> msgRanges_ =
      java.util.Collections.emptyList();
    public java.util.List<com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange> getMsgRangesList() {
      return msgRanges_;
    }
    public int getMsgRangesCount() { return msgRanges_.size(); }
    public com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange getMsgRanges(int index) {
      return msgRanges_.get(index);
    }
    public QueryMsgsReq setMsgRanges(int index, com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange value) {
      if (value == null) {
        throw new NullPointerException();
      }
      msgRanges_.set(index, value);
      return this;
    }
    public QueryMsgsReq addMsgRanges(com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange value) {
      if (value == null) {
        throw new NullPointerException();
      }
      if (msgRanges_.isEmpty()) {
        msgRanges_ = new java.util.ArrayList<com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange>();
      }
      msgRanges_.add(value);
      return this;
    }
    public QueryMsgsReq clearMsgRanges() {
      msgRanges_ = java.util.Collections.emptyList();
      return this;
    }

    // optional uint32 count = 2;
    public static final int COUNT_FIELD_NUMBER = 2;
    private boolean hasCount;
    private int count_ = 0;
    public int getCount() { return count_; }
    public boolean hasCount() { return hasCount; }
    public QueryMsgsReq setCount(int value) {
      hasCount = true;
      count_ = value;
      return this;
    }
    public QueryMsgsReq clearCount() {
      hasCount = false;
      count_ = 0;
      return this;
    }

    public final QueryMsgsReq clear() {
      clearMsgRanges();
      clearCount();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      for (com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange element : getMsgRangesList()) {
        if (!element.isInitialized()) return false;
      }
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      for (com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange element : getMsgRangesList()) {
        output.writeMessage(1, element);
      }
      if (hasCount()) {
        output.writeUInt32(2, getCount());
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
      for (com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange element : getMsgRangesList()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeMessageSize(1, element);
      }
      if (hasCount()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt32Size(2, getCount());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public QueryMsgsReq mergeFrom(
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
          case 10: {
            com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange value = new com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRange();
            input.readMessage(value);
            addMsgRanges(value);
            break;
          }
          case 16: {
            setCount(input.readUInt32());
            break;
          }
        }
      }
    }

    public static QueryMsgsReq parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (QueryMsgsReq) (new QueryMsgsReq().mergeFrom(data));
    }

    public static QueryMsgsReq parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new QueryMsgsReq().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class QueryMsgsRsp extends
      com.google.protobuf.micro.MessageMicro {
    public QueryMsgsRsp() {}

    // repeated .OneMsg msgs = 1;
    public static final int MSGS_FIELD_NUMBER = 1;
    private java.util.List<com.baidu.im.frame.pb.ObjOneMsg.OneMsg> msgs_ =
      java.util.Collections.emptyList();
    public java.util.List<com.baidu.im.frame.pb.ObjOneMsg.OneMsg> getMsgsList() {
      return msgs_;
    }
    public int getMsgsCount() { return msgs_.size(); }
    public com.baidu.im.frame.pb.ObjOneMsg.OneMsg getMsgs(int index) {
      return msgs_.get(index);
    }
    public QueryMsgsRsp setMsgs(int index, com.baidu.im.frame.pb.ObjOneMsg.OneMsg value) {
      if (value == null) {
        throw new NullPointerException();
      }
      msgs_.set(index, value);
      return this;
    }
    public QueryMsgsRsp addMsgs(com.baidu.im.frame.pb.ObjOneMsg.OneMsg value) {
      if (value == null) {
        throw new NullPointerException();
      }
      if (msgs_.isEmpty()) {
        msgs_ = new java.util.ArrayList<com.baidu.im.frame.pb.ObjOneMsg.OneMsg>();
      }
      msgs_.add(value);
      return this;
    }
    public QueryMsgsRsp clearMsgs() {
      msgs_ = java.util.Collections.emptyList();
      return this;
    }

    // repeated .QueryMsgsRangeStatus msgsRangeStatus = 2;
    public static final int MSGSRANGESTATUS_FIELD_NUMBER = 2;
    private java.util.List<com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRangeStatus> msgsRangeStatus_ =
      java.util.Collections.emptyList();
    public java.util.List<com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRangeStatus> getMsgsRangeStatusList() {
      return msgsRangeStatus_;
    }
    public int getMsgsRangeStatusCount() { return msgsRangeStatus_.size(); }
    public com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRangeStatus getMsgsRangeStatus(int index) {
      return msgsRangeStatus_.get(index);
    }
    public QueryMsgsRsp setMsgsRangeStatus(int index, com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRangeStatus value) {
      if (value == null) {
        throw new NullPointerException();
      }
      msgsRangeStatus_.set(index, value);
      return this;
    }
    public QueryMsgsRsp addMsgsRangeStatus(com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRangeStatus value) {
      if (value == null) {
        throw new NullPointerException();
      }
      if (msgsRangeStatus_.isEmpty()) {
        msgsRangeStatus_ = new java.util.ArrayList<com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRangeStatus>();
      }
      msgsRangeStatus_.add(value);
      return this;
    }
    public QueryMsgsRsp clearMsgsRangeStatus() {
      msgsRangeStatus_ = java.util.Collections.emptyList();
      return this;
    }

    public final QueryMsgsRsp clear() {
      clearMsgs();
      clearMsgsRangeStatus();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      for (com.baidu.im.frame.pb.ObjOneMsg.OneMsg element : getMsgsList()) {
        if (!element.isInitialized()) return false;
      }
      for (com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRangeStatus element : getMsgsRangeStatusList()) {
        if (!element.isInitialized()) return false;
      }
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      for (com.baidu.im.frame.pb.ObjOneMsg.OneMsg element : getMsgsList()) {
        output.writeMessage(1, element);
      }
      for (com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRangeStatus element : getMsgsRangeStatusList()) {
        output.writeMessage(2, element);
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
      for (com.baidu.im.frame.pb.ObjOneMsg.OneMsg element : getMsgsList()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeMessageSize(1, element);
      }
      for (com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRangeStatus element : getMsgsRangeStatusList()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeMessageSize(2, element);
      }
      cachedSize = size;
      return size;
    }

    @Override
    public QueryMsgsRsp mergeFrom(
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
          case 10: {
            com.baidu.im.frame.pb.ObjOneMsg.OneMsg value = new com.baidu.im.frame.pb.ObjOneMsg.OneMsg();
            input.readMessage(value);
            addMsgs(value);
            break;
          }
          case 18: {
            com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRangeStatus value = new com.baidu.im.frame.pb.ProQueryMsgs.QueryMsgsRangeStatus();
            input.readMessage(value);
            addMsgsRangeStatus(value);
            break;
          }
        }
      }
    }

    public static QueryMsgsRsp parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (QueryMsgsRsp) (new QueryMsgsRsp().mergeFrom(data));
    }

    public static QueryMsgsRsp parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new QueryMsgsRsp().mergeFrom(input);
    }

  }

}
