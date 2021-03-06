// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ObjBua {
  private ObjBua() {}
  @SuppressWarnings("hiding")
  public static final class BUAInfo extends
      com.google.protobuf.micro.MessageMicro {
    public BUAInfo() {}

    // optional string deviceToken = 1;
    public static final int DEVICETOKEN_FIELD_NUMBER = 1;
    private boolean hasDeviceToken;
    private java.lang.String deviceToken_ = "";
    public java.lang.String getDeviceToken() { return deviceToken_; }
    public boolean hasDeviceToken() { return hasDeviceToken; }
    public BUAInfo setDeviceToken(java.lang.String value) {
      hasDeviceToken = true;
      deviceToken_ = value;
      return this;
    }
    public BUAInfo clearDeviceToken() {
      hasDeviceToken = false;
      deviceToken_ = "";
      return this;
    }

    // optional .ENetworkType network = 2;
    public static final int NETWORK_FIELD_NUMBER = 2;
    private boolean hasNetwork;
    private int network_ = com.baidu.im.frame.pb.EnumNetworkType.APN_WWW;
    public boolean hasNetwork() { return hasNetwork; }
    public int getNetwork() { return network_; }
    public BUAInfo setNetwork(int value) {
      hasNetwork = true;
      network_ = value;
      return this;
    }
    public BUAInfo clearNetwork() {
      hasNetwork = false;
      network_ = com.baidu.im.frame.pb.EnumNetworkType.APN_WWW;
      return this;
    }

    // optional .EAPNInfo apn = 3;
    public static final int APN_FIELD_NUMBER = 3;
    private boolean hasApn;
    private int apn_ = com.baidu.im.frame.pb.EnumApnInfo.CHINA_MOBILE;
    public boolean hasApn() { return hasApn; }
    public int getApn() { return apn_; }
    public BUAInfo setApn(int value) {
      hasApn = true;
      apn_ = value;
      return this;
    }
    public BUAInfo clearApn() {
      hasApn = false;
      apn_ = com.baidu.im.frame.pb.EnumApnInfo.CHINA_MOBILE;
      return this;
    }

    // optional string osVer = 4;
    public static final int OSVER_FIELD_NUMBER = 4;
    private boolean hasOsVer;
    private java.lang.String osVer_ = "";
    public java.lang.String getOsVer() { return osVer_; }
    public boolean hasOsVer() { return hasOsVer; }
    public BUAInfo setOsVer(java.lang.String value) {
      hasOsVer = true;
      osVer_ = value;
      return this;
    }
    public BUAInfo clearOsVer() {
      hasOsVer = false;
      osVer_ = "";
      return this;
    }

    // optional string sdkVer = 5;
    public static final int SDKVER_FIELD_NUMBER = 5;
    private boolean hasSdkVer;
    private java.lang.String sdkVer_ = "";
    public java.lang.String getSdkVer() { return sdkVer_; }
    public boolean hasSdkVer() { return hasSdkVer; }
    public BUAInfo setSdkVer(java.lang.String value) {
      hasSdkVer = true;
      sdkVer_ = value;
      return this;
    }
    public BUAInfo clearSdkVer() {
      hasSdkVer = false;
      sdkVer_ = "";
      return this;
    }

    public final BUAInfo clear() {
      clearDeviceToken();
      clearNetwork();
      clearApn();
      clearOsVer();
      clearSdkVer();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasDeviceToken()) {
        output.writeString(1, getDeviceToken());
      }
      if (hasNetwork()) {
        output.writeInt32(2, getNetwork());
      }
      if (hasApn()) {
        output.writeInt32(3, getApn());
      }
      if (hasOsVer()) {
        output.writeString(4, getOsVer());
      }
      if (hasSdkVer()) {
        output.writeString(5, getSdkVer());
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
      if (hasDeviceToken()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(1, getDeviceToken());
      }
      if (hasNetwork()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeInt32Size(2, getNetwork());
      }
      if (hasApn()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeInt32Size(3, getApn());
      }
      if (hasOsVer()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(4, getOsVer());
      }
      if (hasSdkVer()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(5, getSdkVer());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public BUAInfo mergeFrom(
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
            setDeviceToken(input.readString());
            break;
          }
          case 16: {
              setNetwork(input.readInt32());
            break;
          }
          case 24: {
              setApn(input.readInt32());
            break;
          }
          case 34: {
            setOsVer(input.readString());
            break;
          }
          case 42: {
            setSdkVer(input.readString());
            break;
          }
        }
      }
    }

    public static BUAInfo parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (BUAInfo) (new BUAInfo().mergeFrom(data));
    }

    public static BUAInfo parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new BUAInfo().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class BUA extends
      com.google.protobuf.micro.MessageMicro {
    public BUA() {}

    // optional string appVer = 1;
    public static final int APPVER_FIELD_NUMBER = 1;
    private boolean hasAppVer;
    private java.lang.String appVer_ = "";
    public java.lang.String getAppVer() { return appVer_; }
    public boolean hasAppVer() { return hasAppVer; }
    public BUA setAppVer(java.lang.String value) {
      hasAppVer = true;
      appVer_ = value;
      return this;
    }
    public BUA clearAppVer() {
      hasAppVer = false;
      appVer_ = "";
      return this;
    }

    // optional uint32 mem = 2;
    public static final int MEM_FIELD_NUMBER = 2;
    private boolean hasMem;
    private int mem_ = 0;
    public int getMem() { return mem_; }
    public boolean hasMem() { return hasMem; }
    public BUA setMem(int value) {
      hasMem = true;
      mem_ = value;
      return this;
    }
    public BUA clearMem() {
      hasMem = false;
      mem_ = 0;
      return this;
    }

    // optional uint32 sdCard = 3;
    public static final int SDCARD_FIELD_NUMBER = 3;
    private boolean hasSdCard;
    private int sdCard_ = 0;
    public int getSdCard() { return sdCard_; }
    public boolean hasSdCard() { return hasSdCard; }
    public BUA setSdCard(int value) {
      hasSdCard = true;
      sdCard_ = value;
      return this;
    }
    public BUA clearSdCard() {
      hasSdCard = false;
      sdCard_ = 0;
      return this;
    }

    // optional .BUAInfo buaInfo = 4;
    public static final int BUAINFO_FIELD_NUMBER = 4;
    private boolean hasBuaInfo;
    private com.baidu.im.frame.pb.ObjBua.BUAInfo buaInfo_ = null;
    public boolean hasBuaInfo() { return hasBuaInfo; }
    public com.baidu.im.frame.pb.ObjBua.BUAInfo getBuaInfo() { return buaInfo_; }
    public BUA setBuaInfo(com.baidu.im.frame.pb.ObjBua.BUAInfo value) {
      if (value == null) {
        throw new NullPointerException();
      }
      hasBuaInfo = true;
      buaInfo_ = value;
      return this;
    }
    public BUA clearBuaInfo() {
      hasBuaInfo = false;
      buaInfo_ = null;
      return this;
    }

    public final BUA clear() {
      clearAppVer();
      clearMem();
      clearSdCard();
      clearBuaInfo();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasAppVer()) {
        output.writeString(1, getAppVer());
      }
      if (hasMem()) {
        output.writeUInt32(2, getMem());
      }
      if (hasSdCard()) {
        output.writeUInt32(3, getSdCard());
      }
      if (hasBuaInfo()) {
        output.writeMessage(4, getBuaInfo());
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
      if (hasAppVer()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(1, getAppVer());
      }
      if (hasMem()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt32Size(2, getMem());
      }
      if (hasSdCard()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt32Size(3, getSdCard());
      }
      if (hasBuaInfo()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeMessageSize(4, getBuaInfo());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public BUA mergeFrom(
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
            setAppVer(input.readString());
            break;
          }
          case 16: {
            setMem(input.readUInt32());
            break;
          }
          case 24: {
            setSdCard(input.readUInt32());
            break;
          }
          case 34: {
            com.baidu.im.frame.pb.ObjBua.BUAInfo value = new com.baidu.im.frame.pb.ObjBua.BUAInfo();
            input.readMessage(value);
            setBuaInfo(value);
            break;
          }
        }
      }
    }

    public static BUA parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (BUA) (new BUA().mergeFrom(data));
    }

    public static BUA parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new BUA().mergeFrom(input);
    }

  }

}
