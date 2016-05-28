// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ObjNotifyMessage {
  private ObjNotifyMessage() {}
  @SuppressWarnings("hiding")
  public static final class NotifyMessage extends
      com.google.protobuf.micro.MessageMicro {
    public NotifyMessage() {}

    // required .ENotifyType notifyType = 1;
    public static final int NOTIFYTYPE_FIELD_NUMBER = 1;
    private boolean hasNotifyType;
    private int notifyType_ = com.baidu.im.frame.pb.EnumNotifyType.LOGOUT_NOTIFY;
    public boolean hasNotifyType() { return hasNotifyType; }
    public int getNotifyType() { return notifyType_; }
    public NotifyMessage setNotifyType(int value) {
      hasNotifyType = true;
      notifyType_ = value;
      return this;
    }
    public NotifyMessage clearNotifyType() {
      hasNotifyType = false;
      notifyType_ = com.baidu.im.frame.pb.EnumNotifyType.LOGOUT_NOTIFY;
      return this;
    }

    // required bytes notifyData = 2;
    public static final int NOTIFYDATA_FIELD_NUMBER = 2;
    private boolean hasNotifyData;
    private com.google.protobuf.micro.ByteStringMicro notifyData_ = com.google.protobuf.micro.ByteStringMicro.EMPTY;
    public com.google.protobuf.micro.ByteStringMicro getNotifyData() { return notifyData_; }
    public boolean hasNotifyData() { return hasNotifyData; }
    public NotifyMessage setNotifyData(com.google.protobuf.micro.ByteStringMicro value) {
      hasNotifyData = true;
      notifyData_ = value;
      return this;
    }
    public NotifyMessage clearNotifyData() {
      hasNotifyData = false;
      notifyData_ = com.google.protobuf.micro.ByteStringMicro.EMPTY;
      return this;
    }

    public final NotifyMessage clear() {
      clearNotifyType();
      clearNotifyData();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      if (!hasNotifyType) return false;
      if (!hasNotifyData) return false;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasNotifyType()) {
        output.writeInt32(1, getNotifyType());
      }
      if (hasNotifyData()) {
        output.writeBytes(2, getNotifyData());
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
      if (hasNotifyType()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeInt32Size(1, getNotifyType());
      }
      if (hasNotifyData()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeBytesSize(2, getNotifyData());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public NotifyMessage mergeFrom(
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
              setNotifyType(input.readInt32());
            break;
          }
          case 18: {
            setNotifyData(input.readBytes());
            break;
          }
        }
      }
    }

    public static NotifyMessage parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (NotifyMessage) (new NotifyMessage().mergeFrom(data));
    }

    public static NotifyMessage parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new NotifyMessage().mergeFrom(input);
    }

  }

}