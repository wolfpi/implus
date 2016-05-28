// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ProDeleteContact {
  private ProDeleteContact() {}
  @SuppressWarnings("hiding")
  public static final class DeleteActiveContactReq extends
      com.google.protobuf.micro.MessageMicro {
    public DeleteActiveContactReq() {}

    // required .EChatType chatType = 1;
    public static final int CHATTYPE_FIELD_NUMBER = 1;
    private boolean hasChatType;
    private int chatType_ = com.baidu.im.frame.pb.EnumChatType.CHAT_P2P;
    public boolean hasChatType() { return hasChatType; }
    public int getChatType() { return chatType_; }
    public DeleteActiveContactReq setChatType(int value) {
      hasChatType = true;
      chatType_ = value;
      return this;
    }
    public DeleteActiveContactReq clearChatType() {
      hasChatType = false;
      chatType_ = com.baidu.im.frame.pb.EnumChatType.CHAT_P2P;
      return this;
    }

    // repeated string chatID = 2;
    public static final int CHATID_FIELD_NUMBER = 2;
    private java.util.List<java.lang.String> chatID_ =
      java.util.Collections.emptyList();
    public java.util.List<java.lang.String> getChatIDList() {
      return chatID_;
    }
    public int getChatIDCount() { return chatID_.size(); }
    public java.lang.String getChatID(int index) {
      return chatID_.get(index);
    }
    public DeleteActiveContactReq setChatID(int index, java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  chatID_.set(index, value);
      return this;
    }
    public DeleteActiveContactReq addChatID(java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  if (chatID_.isEmpty()) {
        chatID_ = new java.util.ArrayList<java.lang.String>();
      }
      chatID_.add(value);
      return this;
    }
    public DeleteActiveContactReq clearChatID() {
      chatID_ = java.util.Collections.emptyList();
      return this;
    }

    public final DeleteActiveContactReq clear() {
      clearChatType();
      clearChatID();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      if (!hasChatType) return false;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasChatType()) {
        output.writeInt32(1, getChatType());
      }
      for (java.lang.String element : getChatIDList()) {
        output.writeString(2, element);
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
      {
        int dataSize = 0;
        for (java.lang.String element : getChatIDList()) {
          dataSize += com.google.protobuf.micro.CodedOutputStreamMicro
            .computeStringSizeNoTag(element);
        }
        size += dataSize;
        size += 1 * getChatIDList().size();
      }
      cachedSize = size;
      return size;
    }

    @Override
    public DeleteActiveContactReq mergeFrom(
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
            addChatID(input.readString());
            break;
          }
        }
      }
    }

    public static DeleteActiveContactReq parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (DeleteActiveContactReq) (new DeleteActiveContactReq().mergeFrom(data));
    }

    public static DeleteActiveContactReq parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new DeleteActiveContactReq().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class DeleteActiveContactRsp extends
      com.google.protobuf.micro.MessageMicro {
    public DeleteActiveContactRsp() {}

    public final DeleteActiveContactRsp clear() {
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output) {
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
      cachedSize = size;
      return size;
    }

    @Override
    public DeleteActiveContactRsp mergeFrom(
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
        }
      }
    }

    public static DeleteActiveContactRsp parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (DeleteActiveContactRsp) (new DeleteActiveContactRsp().mergeFrom(data));
    }

    public static DeleteActiveContactRsp parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new DeleteActiveContactRsp().mergeFrom(input);
    }

  }

}
