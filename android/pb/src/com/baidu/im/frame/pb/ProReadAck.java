// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ProReadAck {
  private ProReadAck() {}
  @SuppressWarnings("hiding")
  public static final class ReadAckReq extends
      com.google.protobuf.micro.MessageMicro {
    public ReadAckReq() {}

    // repeated .ChatConversation conversations = 1;
    public static final int CONVERSATIONS_FIELD_NUMBER = 1;
    private java.util.List<com.baidu.im.frame.pb.ObjChatConversation.ChatConversation> conversations_ =
      java.util.Collections.emptyList();
    public java.util.List<com.baidu.im.frame.pb.ObjChatConversation.ChatConversation> getConversationsList() {
      return conversations_;
    }
    public int getConversationsCount() { return conversations_.size(); }
    public com.baidu.im.frame.pb.ObjChatConversation.ChatConversation getConversations(int index) {
      return conversations_.get(index);
    }
    public ReadAckReq setConversations(int index, com.baidu.im.frame.pb.ObjChatConversation.ChatConversation value) {
      if (value == null) {
        throw new NullPointerException();
      }
      conversations_.set(index, value);
      return this;
    }
    public ReadAckReq addConversations(com.baidu.im.frame.pb.ObjChatConversation.ChatConversation value) {
      if (value == null) {
        throw new NullPointerException();
      }
      if (conversations_.isEmpty()) {
        conversations_ = new java.util.ArrayList<com.baidu.im.frame.pb.ObjChatConversation.ChatConversation>();
      }
      conversations_.add(value);
      return this;
    }
    public ReadAckReq clearConversations() {
      conversations_ = java.util.Collections.emptyList();
      return this;
    }

    public final ReadAckReq clear() {
      clearConversations();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      for (com.baidu.im.frame.pb.ObjChatConversation.ChatConversation element : getConversationsList()) {
        if (!element.isInitialized()) return false;
      }
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      for (com.baidu.im.frame.pb.ObjChatConversation.ChatConversation element : getConversationsList()) {
        output.writeMessage(1, element);
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
      for (com.baidu.im.frame.pb.ObjChatConversation.ChatConversation element : getConversationsList()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeMessageSize(1, element);
      }
      cachedSize = size;
      return size;
    }

    @Override
    public ReadAckReq mergeFrom(
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
            com.baidu.im.frame.pb.ObjChatConversation.ChatConversation value = new com.baidu.im.frame.pb.ObjChatConversation.ChatConversation();
            input.readMessage(value);
            addConversations(value);
            break;
          }
        }
      }
    }

    public static ReadAckReq parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (ReadAckReq) (new ReadAckReq().mergeFrom(data));
    }

    public static ReadAckReq parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new ReadAckReq().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class ReadAckRsp extends
      com.google.protobuf.micro.MessageMicro {
    public ReadAckRsp() {}

    public final ReadAckRsp clear() {
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
    public ReadAckRsp mergeFrom(
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

    public static ReadAckRsp parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (ReadAckRsp) (new ReadAckRsp().mergeFrom(data));
    }

    public static ReadAckRsp parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new ReadAckRsp().mergeFrom(input);
    }

  }

}