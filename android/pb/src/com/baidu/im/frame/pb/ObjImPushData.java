// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ObjImPushData {
  private ObjImPushData() {}
  @SuppressWarnings("hiding")
  public static final class ImPushData extends
      com.google.protobuf.micro.MessageMicro {
    public ImPushData() {}

    // required .EImPushType imPushType = 1;
    public static final int IMPUSHTYPE_FIELD_NUMBER = 1;
    private boolean hasImPushType;
    private int imPushType_ = com.baidu.im.frame.pb.EnumImPushType.IM_PUSH_CHAT_MSG;
    public boolean hasImPushType() { return hasImPushType; }
    public int getImPushType() { return imPushType_; }
    public ImPushData setImPushType(int value) {
      hasImPushType = true;
      imPushType_ = value;
      return this;
    }
    public ImPushData clearImPushType() {
      hasImPushType = false;
      imPushType_ = com.baidu.im.frame.pb.EnumImPushType.IM_PUSH_CHAT_MSG;
      return this;
    }

    // required bytes pushData = 2;
    public static final int PUSHDATA_FIELD_NUMBER = 2;
    private boolean hasPushData;
    private com.google.protobuf.micro.ByteStringMicro pushData_ = com.google.protobuf.micro.ByteStringMicro.EMPTY;
    public com.google.protobuf.micro.ByteStringMicro getPushData() { return pushData_; }
    public boolean hasPushData() { return hasPushData; }
    public ImPushData setPushData(com.google.protobuf.micro.ByteStringMicro value) {
      hasPushData = true;
      pushData_ = value;
      return this;
    }
    public ImPushData clearPushData() {
      hasPushData = false;
      pushData_ = com.google.protobuf.micro.ByteStringMicro.EMPTY;
      return this;
    }

    public final ImPushData clear() {
      clearImPushType();
      clearPushData();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      if (!hasImPushType) return false;
      if (!hasPushData) return false;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasImPushType()) {
        output.writeInt32(1, getImPushType());
      }
      if (hasPushData()) {
        output.writeBytes(2, getPushData());
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
      if (hasImPushType()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeInt32Size(1, getImPushType());
      }
      if (hasPushData()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeBytesSize(2, getPushData());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public ImPushData mergeFrom(
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
              setImPushType(input.readInt32());
            break;
          }
          case 18: {
            setPushData(input.readBytes());
            break;
          }
        }
      }
    }

    public static ImPushData parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (ImPushData) (new ImPushData().mergeFrom(data));
    }

    public static ImPushData parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new ImPushData().mergeFrom(input);
    }

  }

}