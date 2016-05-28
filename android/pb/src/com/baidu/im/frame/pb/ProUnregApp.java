// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ProUnregApp {
  private ProUnregApp() {}
  @SuppressWarnings("hiding")
  public static final class UnRegAppReq extends
      com.google.protobuf.micro.MessageMicro {
    public UnRegAppReq() {}

    // optional string channelKey = 1;
    public static final int CHANNELKEY_FIELD_NUMBER = 1;
    private boolean hasChannelKey;
    private java.lang.String channelKey_ = "";
    public java.lang.String getChannelKey() { return channelKey_; }
    public boolean hasChannelKey() { return hasChannelKey; }
    public UnRegAppReq setChannelKey(java.lang.String value) {
      hasChannelKey = true;
      channelKey_ = value;
      return this;
    }
    public UnRegAppReq clearChannelKey() {
      hasChannelKey = false;
      channelKey_ = "";
      return this;
    }

    public final UnRegAppReq clear() {
      clearChannelKey();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasChannelKey()) {
        output.writeString(1, getChannelKey());
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
      if (hasChannelKey()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(1, getChannelKey());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public UnRegAppReq mergeFrom(
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
            setChannelKey(input.readString());
            break;
          }
        }
      }
    }

    public static UnRegAppReq parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (UnRegAppReq) (new UnRegAppReq().mergeFrom(data));
    }

    public static UnRegAppReq parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new UnRegAppReq().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class UnRegAppRsp extends
      com.google.protobuf.micro.MessageMicro {
    public UnRegAppRsp() {}

    public final UnRegAppRsp clear() {
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
    public UnRegAppRsp mergeFrom(
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

    public static UnRegAppRsp parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (UnRegAppRsp) (new UnRegAppRsp().mergeFrom(data));
    }

    public static UnRegAppRsp parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new UnRegAppRsp().mergeFrom(input);
    }

  }

}