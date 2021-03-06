// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ProPushConfirm {
  private ProPushConfirm() {}
  // enum EPushMsgStatus
  public static final int STATUS_SUCCESS = 1;
  public static final int STATUS_FAILED = 2;
  public static final int STATUS_APP_NOT_EXIST = 3;

  @SuppressWarnings("hiding")
  public static final class PushMsgStatus extends
      com.google.protobuf.micro.MessageMicro {
    public PushMsgStatus() {}

    // required uint64 ackId = 1;
    public static final int ACKID_FIELD_NUMBER = 1;
    private boolean hasAckId;
    private long ackId_ = 0L;
    public long getAckId() { return ackId_; }
    public boolean hasAckId() { return hasAckId; }
    public PushMsgStatus setAckId(long value) {
      hasAckId = true;
      ackId_ = value;
      return this;
    }
    public PushMsgStatus clearAckId() {
      hasAckId = false;
      ackId_ = 0L;
      return this;
    }

    // optional .EPushMsgStatus onlineStatus = 2;
    public static final int ONLINESTATUS_FIELD_NUMBER = 2;
    private boolean hasOnlineStatus;
    private int onlineStatus_ = com.baidu.im.frame.pb.ProPushConfirm.STATUS_SUCCESS;
    public boolean hasOnlineStatus() { return hasOnlineStatus; }
    public int getOnlineStatus() { return onlineStatus_; }
    public PushMsgStatus setOnlineStatus(int value) {
      hasOnlineStatus = true;
      onlineStatus_ = value;
      return this;
    }
    public PushMsgStatus clearOnlineStatus() {
      hasOnlineStatus = false;
      onlineStatus_ = com.baidu.im.frame.pb.ProPushConfirm.STATUS_SUCCESS;
      return this;
    }

    // optional .EPushMsgStatus offlineStatus = 3;
    public static final int OFFLINESTATUS_FIELD_NUMBER = 3;
    private boolean hasOfflineStatus;
    private int offlineStatus_ = com.baidu.im.frame.pb.ProPushConfirm.STATUS_SUCCESS;
    public boolean hasOfflineStatus() { return hasOfflineStatus; }
    public int getOfflineStatus() { return offlineStatus_; }
    public PushMsgStatus setOfflineStatus(int value) {
      hasOfflineStatus = true;
      offlineStatus_ = value;
      return this;
    }
    public PushMsgStatus clearOfflineStatus() {
      hasOfflineStatus = false;
      offlineStatus_ = com.baidu.im.frame.pb.ProPushConfirm.STATUS_SUCCESS;
      return this;
    }

    public final PushMsgStatus clear() {
      clearAckId();
      clearOnlineStatus();
      clearOfflineStatus();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      if (!hasAckId) return false;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasAckId()) {
        output.writeUInt64(1, getAckId());
      }
      if (hasOnlineStatus()) {
        output.writeInt32(2, getOnlineStatus());
      }
      if (hasOfflineStatus()) {
        output.writeInt32(3, getOfflineStatus());
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
      if (hasAckId()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt64Size(1, getAckId());
      }
      if (hasOnlineStatus()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeInt32Size(2, getOnlineStatus());
      }
      if (hasOfflineStatus()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeInt32Size(3, getOfflineStatus());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public PushMsgStatus mergeFrom(
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
            setAckId(input.readUInt64());
            break;
          }
          case 16: {
              setOnlineStatus(input.readInt32());
            break;
          }
          case 24: {
              setOfflineStatus(input.readInt32());
            break;
          }
        }
      }
    }

    public static PushMsgStatus parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (PushMsgStatus) (new PushMsgStatus().mergeFrom(data));
    }

    public static PushMsgStatus parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new PushMsgStatus().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class PushMsgConfirmReq extends
      com.google.protobuf.micro.MessageMicro {
    public PushMsgConfirmReq() {}

    // repeated .PushMsgStatus msgStatus = 1;
    public static final int MSGSTATUS_FIELD_NUMBER = 1;
    private java.util.List<com.baidu.im.frame.pb.ProPushConfirm.PushMsgStatus> msgStatus_ =
      java.util.Collections.emptyList();
    public java.util.List<com.baidu.im.frame.pb.ProPushConfirm.PushMsgStatus> getMsgStatusList() {
      return msgStatus_;
    }
    public int getMsgStatusCount() { return msgStatus_.size(); }
    public com.baidu.im.frame.pb.ProPushConfirm.PushMsgStatus getMsgStatus(int index) {
      return msgStatus_.get(index);
    }
    public PushMsgConfirmReq setMsgStatus(int index, com.baidu.im.frame.pb.ProPushConfirm.PushMsgStatus value) {
      if (value == null) {
        throw new NullPointerException();
      }
      msgStatus_.set(index, value);
      return this;
    }
    public PushMsgConfirmReq addMsgStatus(com.baidu.im.frame.pb.ProPushConfirm.PushMsgStatus value) {
      if (value == null) {
        throw new NullPointerException();
      }
      if (msgStatus_.isEmpty()) {
        msgStatus_ = new java.util.ArrayList<com.baidu.im.frame.pb.ProPushConfirm.PushMsgStatus>();
      }
      msgStatus_.add(value);
      return this;
    }
    public PushMsgConfirmReq clearMsgStatus() {
      msgStatus_ = java.util.Collections.emptyList();
      return this;
    }

    public final PushMsgConfirmReq clear() {
      clearMsgStatus();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      for (com.baidu.im.frame.pb.ProPushConfirm.PushMsgStatus element : getMsgStatusList()) {
        if (!element.isInitialized()) return false;
      }
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      for (com.baidu.im.frame.pb.ProPushConfirm.PushMsgStatus element : getMsgStatusList()) {
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
      for (com.baidu.im.frame.pb.ProPushConfirm.PushMsgStatus element : getMsgStatusList()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeMessageSize(1, element);
      }
      cachedSize = size;
      return size;
    }

    @Override
    public PushMsgConfirmReq mergeFrom(
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
            com.baidu.im.frame.pb.ProPushConfirm.PushMsgStatus value = new com.baidu.im.frame.pb.ProPushConfirm.PushMsgStatus();
            input.readMessage(value);
            addMsgStatus(value);
            break;
          }
        }
      }
    }

    public static PushMsgConfirmReq parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (PushMsgConfirmReq) (new PushMsgConfirmReq().mergeFrom(data));
    }

    public static PushMsgConfirmReq parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new PushMsgConfirmReq().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class PushMsgConfirmRsp extends
      com.google.protobuf.micro.MessageMicro {
    public PushMsgConfirmRsp() {}

    public final PushMsgConfirmRsp clear() {
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
    public PushMsgConfirmRsp mergeFrom(
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

    public static PushMsgConfirmRsp parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (PushMsgConfirmRsp) (new PushMsgConfirmRsp().mergeFrom(data));
    }

    public static PushMsgConfirmRsp parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new PushMsgConfirmRsp().mergeFrom(input);
    }

  }

}
