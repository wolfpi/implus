// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ProBindApnsToken {
  private ProBindApnsToken() {}
  @SuppressWarnings("hiding")
  public static final class BindApnsTokenReq extends
      com.google.protobuf.micro.MessageMicro {
    public BindApnsTokenReq() {}

    // optional string deviceToken = 1;
    public static final int DEVICETOKEN_FIELD_NUMBER = 1;
    private boolean hasDeviceToken;
    private java.lang.String deviceToken_ = "";
    public java.lang.String getDeviceToken() { return deviceToken_; }
    public boolean hasDeviceToken() { return hasDeviceToken; }
    public BindApnsTokenReq setDeviceToken(java.lang.String value) {
      hasDeviceToken = true;
      deviceToken_ = value;
      return this;
    }
    public BindApnsTokenReq clearDeviceToken() {
      hasDeviceToken = false;
      deviceToken_ = "";
      return this;
    }

    // required string apnsToken = 2;
    public static final int APNSTOKEN_FIELD_NUMBER = 2;
    private boolean hasApnsToken;
    private java.lang.String apnsToken_ = "";
    public java.lang.String getApnsToken() { return apnsToken_; }
    public boolean hasApnsToken() { return hasApnsToken; }
    public BindApnsTokenReq setApnsToken(java.lang.String value) {
      hasApnsToken = true;
      apnsToken_ = value;
      return this;
    }
    public BindApnsTokenReq clearApnsToken() {
      hasApnsToken = false;
      apnsToken_ = "";
      return this;
    }

    public final BindApnsTokenReq clear() {
      clearDeviceToken();
      clearApnsToken();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      if (!hasApnsToken) return false;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasDeviceToken()) {
        output.writeString(1, getDeviceToken());
      }
      if (hasApnsToken()) {
        output.writeString(2, getApnsToken());
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
      if (hasApnsToken()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(2, getApnsToken());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public BindApnsTokenReq mergeFrom(
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
          case 18: {
            setApnsToken(input.readString());
            break;
          }
        }
      }
    }

    public static BindApnsTokenReq parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (BindApnsTokenReq) (new BindApnsTokenReq().mergeFrom(data));
    }

    public static BindApnsTokenReq parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new BindApnsTokenReq().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class BindApnsTokenResp extends
      com.google.protobuf.micro.MessageMicro {
    public BindApnsTokenResp() {}

    public final BindApnsTokenResp clear() {
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
    public BindApnsTokenResp mergeFrom(
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

    public static BindApnsTokenResp parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (BindApnsTokenResp) (new BindApnsTokenResp().mergeFrom(data));
    }

    public static BindApnsTokenResp parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new BindApnsTokenResp().mergeFrom(input);
    }

  }

}
