// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ObjUpPacket {
  private ObjUpPacket() {}
  @SuppressWarnings("hiding")
  public static final class UpPacket extends
      com.google.protobuf.micro.MessageMicro {
    public UpPacket() {}

    // optional string sessionId = 1;
    public static final int SESSIONID_FIELD_NUMBER = 1;
    private boolean hasSessionId;
    private java.lang.String sessionId_ = "";
    public java.lang.String getSessionId() { return sessionId_; }
    public boolean hasSessionId() { return hasSessionId; }
    public UpPacket setSessionId(java.lang.String value) {
      hasSessionId = true;
      sessionId_ = value;
      return this;
    }
    public UpPacket clearSessionId() {
      hasSessionId = false;
      sessionId_ = "";
      return this;
    }

    // required string serviceName = 2;
    public static final int SERVICENAME_FIELD_NUMBER = 2;
    private boolean hasServiceName;
    private java.lang.String serviceName_ = "";
    public java.lang.String getServiceName() { return serviceName_; }
    public boolean hasServiceName() { return hasServiceName; }
    public UpPacket setServiceName(java.lang.String value) {
      hasServiceName = true;
      serviceName_ = value;
      return this;
    }
    public UpPacket clearServiceName() {
      hasServiceName = false;
      serviceName_ = "";
      return this;
    }

    // required string methodName = 3;
    public static final int METHODNAME_FIELD_NUMBER = 3;
    private boolean hasMethodName;
    private java.lang.String methodName_ = "";
    public java.lang.String getMethodName() { return methodName_; }
    public boolean hasMethodName() { return hasMethodName; }
    public UpPacket setMethodName(java.lang.String value) {
      hasMethodName = true;
      methodName_ = value;
      return this;
    }
    public UpPacket clearMethodName() {
      hasMethodName = false;
      methodName_ = "";
      return this;
    }

    // required uint32 seq = 4;
    public static final int SEQ_FIELD_NUMBER = 4;
    private boolean hasSeq;
    private int seq_ = 0;
    public int getSeq() { return seq_; }
    public boolean hasSeq() { return hasSeq; }
    public UpPacket setSeq(int value) {
      hasSeq = true;
      seq_ = value;
      return this;
    }
    public UpPacket clearSeq() {
      hasSeq = false;
      seq_ = 0;
      return this;
    }

    // optional uint64 uid = 5;
    public static final int UID_FIELD_NUMBER = 5;
    private boolean hasUid;
    private long uid_ = 0L;
    public long getUid() { return uid_; }
    public boolean hasUid() { return hasUid; }
    public UpPacket setUid(long value) {
      hasUid = true;
      uid_ = value;
      return this;
    }
    public UpPacket clearUid() {
      hasUid = false;
      uid_ = 0L;
      return this;
    }

    // optional uint32 appId = 6;
    public static final int APPID_FIELD_NUMBER = 6;
    private boolean hasAppId;
    private int appId_ = 0;
    public int getAppId() { return appId_; }
    public boolean hasAppId() { return hasAppId; }
    public UpPacket setAppId(int value) {
      hasAppId = true;
      appId_ = value;
      return this;
    }
    public UpPacket clearAppId() {
      hasAppId = false;
      appId_ = 0;
      return this;
    }

    // optional .BUA bua = 7;
    public static final int BUA_FIELD_NUMBER = 7;
    private boolean hasBua;
    private com.baidu.im.frame.pb.ObjBua.BUA bua_ = null;
    public boolean hasBua() { return hasBua; }
    public com.baidu.im.frame.pb.ObjBua.BUA getBua() { return bua_; }
    public UpPacket setBua(com.baidu.im.frame.pb.ObjBua.BUA value) {
      if (value == null) {
        throw new NullPointerException();
      }
      hasBua = true;
      bua_ = value;
      return this;
    }
    public UpPacket clearBua() {
      hasBua = false;
      bua_ = null;
      return this;
    }

    // required bool sysPackage = 8;
    public static final int SYSPACKAGE_FIELD_NUMBER = 8;
    private boolean hasSysPackage;
    private boolean sysPackage_ = false;
    public boolean getSysPackage() { return sysPackage_; }
    public boolean hasSysPackage() { return hasSysPackage; }
    public UpPacket setSysPackage(boolean value) {
      hasSysPackage = true;
      sysPackage_ = value;
      return this;
    }
    public UpPacket clearSysPackage() {
      hasSysPackage = false;
      sysPackage_ = false;
      return this;
    }

    // required .BizUpPackage bizPackage = 9;
    public static final int BIZPACKAGE_FIELD_NUMBER = 9;
    private boolean hasBizPackage;
    private com.baidu.im.frame.pb.ObjBizUpPackage.BizUpPackage bizPackage_ = null;
    public boolean hasBizPackage() { return hasBizPackage; }
    public com.baidu.im.frame.pb.ObjBizUpPackage.BizUpPackage getBizPackage() { return bizPackage_; }
    public UpPacket setBizPackage(com.baidu.im.frame.pb.ObjBizUpPackage.BizUpPackage value) {
      if (value == null) {
        throw new NullPointerException();
      }
      hasBizPackage = true;
      bizPackage_ = value;
      return this;
    }
    public UpPacket clearBizPackage() {
      hasBizPackage = false;
      bizPackage_ = null;
      return this;
    }

    public final UpPacket clear() {
      clearSessionId();
      clearServiceName();
      clearMethodName();
      clearSeq();
      clearUid();
      clearAppId();
      clearBua();
      clearSysPackage();
      clearBizPackage();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      if (!hasServiceName) return false;
      if (!hasMethodName) return false;
      if (!hasSeq) return false;
      if (!hasSysPackage) return false;
      if (!hasBizPackage) return false;
      if (!getBizPackage().isInitialized()) return false;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasSessionId()) {
        output.writeString(1, getSessionId());
      }
      if (hasServiceName()) {
        output.writeString(2, getServiceName());
      }
      if (hasMethodName()) {
        output.writeString(3, getMethodName());
      }
      if (hasSeq()) {
        output.writeUInt32(4, getSeq());
      }
      if (hasUid()) {
        output.writeUInt64(5, getUid());
      }
      if (hasAppId()) {
        output.writeUInt32(6, getAppId());
      }
      if (hasBua()) {
        output.writeMessage(7, getBua());
      }
      if (hasSysPackage()) {
        output.writeBool(8, getSysPackage());
      }
      if (hasBizPackage()) {
        output.writeMessage(9, getBizPackage());
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
      if (hasSessionId()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(1, getSessionId());
      }
      if (hasServiceName()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(2, getServiceName());
      }
      if (hasMethodName()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(3, getMethodName());
      }
      if (hasSeq()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt32Size(4, getSeq());
      }
      if (hasUid()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt64Size(5, getUid());
      }
      if (hasAppId()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt32Size(6, getAppId());
      }
      if (hasBua()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeMessageSize(7, getBua());
      }
      if (hasSysPackage()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeBoolSize(8, getSysPackage());
      }
      if (hasBizPackage()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeMessageSize(9, getBizPackage());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public UpPacket mergeFrom(
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
            setSessionId(input.readString());
            break;
          }
          case 18: {
            setServiceName(input.readString());
            break;
          }
          case 26: {
            setMethodName(input.readString());
            break;
          }
          case 32: {
            setSeq(input.readUInt32());
            break;
          }
          case 40: {
            setUid(input.readUInt64());
            break;
          }
          case 48: {
            setAppId(input.readUInt32());
            break;
          }
          case 58: {
            com.baidu.im.frame.pb.ObjBua.BUA value = new com.baidu.im.frame.pb.ObjBua.BUA();
            input.readMessage(value);
            setBua(value);
            break;
          }
          case 64: {
            setSysPackage(input.readBool());
            break;
          }
          case 74: {
            com.baidu.im.frame.pb.ObjBizUpPackage.BizUpPackage value = new com.baidu.im.frame.pb.ObjBizUpPackage.BizUpPackage();
            input.readMessage(value);
            setBizPackage(value);
            break;
          }
        }
      }
    }

    public static UpPacket parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (UpPacket) (new UpPacket().mergeFrom(data));
    }

    public static UpPacket parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new UpPacket().mergeFrom(input);
    }

  }

}