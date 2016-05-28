// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ProHeartbeat {
  private ProHeartbeat() {}
  @SuppressWarnings("hiding")
  public static final class HeartbeatInfo extends
      com.google.protobuf.micro.MessageMicro {
    public HeartbeatInfo() {}

    // optional uint32 appId = 1;
    public static final int APPID_FIELD_NUMBER = 1;
    private boolean hasAppId;
    private int appId_ = 0;
    public int getAppId() { return appId_; }
    public boolean hasAppId() { return hasAppId; }
    public HeartbeatInfo setAppId(int value) {
      hasAppId = true;
      appId_ = value;
      return this;
    }
    public HeartbeatInfo clearAppId() {
      hasAppId = false;
      appId_ = 0;
      return this;
    }

    // optional string sessionId = 2;
    public static final int SESSIONID_FIELD_NUMBER = 2;
    private boolean hasSessionId;
    private java.lang.String sessionId_ = "";
    public java.lang.String getSessionId() { return sessionId_; }
    public boolean hasSessionId() { return hasSessionId; }
    public HeartbeatInfo setSessionId(java.lang.String value) {
      hasSessionId = true;
      sessionId_ = value;
      return this;
    }
    public HeartbeatInfo clearSessionId() {
      hasSessionId = false;
      sessionId_ = "";
      return this;
    }

    // optional bool background = 3;
    public static final int BACKGROUND_FIELD_NUMBER = 3;
    private boolean hasBackground;
    private boolean background_ = false;
    public boolean getBackground() { return background_; }
    public boolean hasBackground() { return hasBackground; }
    public HeartbeatInfo setBackground(boolean value) {
      hasBackground = true;
      background_ = value;
      return this;
    }
    public HeartbeatInfo clearBackground() {
      hasBackground = false;
      background_ = false;
      return this;
    }

    public final HeartbeatInfo clear() {
      clearAppId();
      clearSessionId();
      clearBackground();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasAppId()) {
        output.writeUInt32(1, getAppId());
      }
      if (hasSessionId()) {
        output.writeString(2, getSessionId());
      }
      if (hasBackground()) {
        output.writeBool(3, getBackground());
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
      if (hasAppId()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt32Size(1, getAppId());
      }
      if (hasSessionId()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(2, getSessionId());
      }
      if (hasBackground()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeBoolSize(3, getBackground());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public HeartbeatInfo mergeFrom(
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
            setAppId(input.readUInt32());
            break;
          }
          case 18: {
            setSessionId(input.readString());
            break;
          }
          case 24: {
            setBackground(input.readBool());
            break;
          }
        }
      }
    }

    public static HeartbeatInfo parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (HeartbeatInfo) (new HeartbeatInfo().mergeFrom(data));
    }

    public static HeartbeatInfo parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new HeartbeatInfo().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class HeartbeatReq extends
      com.google.protobuf.micro.MessageMicro {
    public HeartbeatReq() {}

    // optional string channelKey = 1;
    public static final int CHANNELKEY_FIELD_NUMBER = 1;
    private boolean hasChannelKey;
    private java.lang.String channelKey_ = "";
    public java.lang.String getChannelKey() { return channelKey_; }
    public boolean hasChannelKey() { return hasChannelKey; }
    public HeartbeatReq setChannelKey(java.lang.String value) {
      hasChannelKey = true;
      channelKey_ = value;
      return this;
    }
    public HeartbeatReq clearChannelKey() {
      hasChannelKey = false;
      channelKey_ = "";
      return this;
    }

    // optional bool background = 2;
    public static final int BACKGROUND_FIELD_NUMBER = 2;
    private boolean hasBackground;
    private boolean background_ = false;
    public boolean getBackground() { return background_; }
    public boolean hasBackground() { return hasBackground; }
    public HeartbeatReq setBackground(boolean value) {
      hasBackground = true;
      background_ = value;
      return this;
    }
    public HeartbeatReq clearBackground() {
      hasBackground = false;
      background_ = false;
      return this;
    }

    // repeated .HeartbeatInfo info = 3;
    public static final int INFO_FIELD_NUMBER = 3;
    private java.util.List<com.baidu.im.frame.pb.ProHeartbeat.HeartbeatInfo> info_ =
      java.util.Collections.emptyList();
    public java.util.List<com.baidu.im.frame.pb.ProHeartbeat.HeartbeatInfo> getInfoList() {
      return info_;
    }
    public int getInfoCount() { return info_.size(); }
    public com.baidu.im.frame.pb.ProHeartbeat.HeartbeatInfo getInfo(int index) {
      return info_.get(index);
    }
    public HeartbeatReq setInfo(int index, com.baidu.im.frame.pb.ProHeartbeat.HeartbeatInfo value) {
      if (value == null) {
        throw new NullPointerException();
      }
      info_.set(index, value);
      return this;
    }
    public HeartbeatReq addInfo(com.baidu.im.frame.pb.ProHeartbeat.HeartbeatInfo value) {
      if (value == null) {
        throw new NullPointerException();
      }
      if (info_.isEmpty()) {
        info_ = new java.util.ArrayList<com.baidu.im.frame.pb.ProHeartbeat.HeartbeatInfo>();
      }
      info_.add(value);
      return this;
    }
    public HeartbeatReq clearInfo() {
      info_ = java.util.Collections.emptyList();
      return this;
    }

    // optional bytes extra = 4;
    public static final int EXTRA_FIELD_NUMBER = 4;
    private boolean hasExtra;
    private com.google.protobuf.micro.ByteStringMicro extra_ = com.google.protobuf.micro.ByteStringMicro.EMPTY;
    public com.google.protobuf.micro.ByteStringMicro getExtra() { return extra_; }
    public boolean hasExtra() { return hasExtra; }
    public HeartbeatReq setExtra(com.google.protobuf.micro.ByteStringMicro value) {
      hasExtra = true;
      extra_ = value;
      return this;
    }
    public HeartbeatReq clearExtra() {
      hasExtra = false;
      extra_ = com.google.protobuf.micro.ByteStringMicro.EMPTY;
      return this;
    }

    public final HeartbeatReq clear() {
      clearChannelKey();
      clearBackground();
      clearInfo();
      clearExtra();
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
      if (hasBackground()) {
        output.writeBool(2, getBackground());
      }
      for (com.baidu.im.frame.pb.ProHeartbeat.HeartbeatInfo element : getInfoList()) {
        output.writeMessage(3, element);
      }
      if (hasExtra()) {
        output.writeBytes(4, getExtra());
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
      if (hasBackground()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeBoolSize(2, getBackground());
      }
      for (com.baidu.im.frame.pb.ProHeartbeat.HeartbeatInfo element : getInfoList()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeMessageSize(3, element);
      }
      if (hasExtra()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeBytesSize(4, getExtra());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public HeartbeatReq mergeFrom(
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
          case 16: {
            setBackground(input.readBool());
            break;
          }
          case 26: {
            com.baidu.im.frame.pb.ProHeartbeat.HeartbeatInfo value = new com.baidu.im.frame.pb.ProHeartbeat.HeartbeatInfo();
            input.readMessage(value);
            addInfo(value);
            break;
          }
          case 34: {
            setExtra(input.readBytes());
            break;
          }
        }
      }
    }

    public static HeartbeatReq parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (HeartbeatReq) (new HeartbeatReq().mergeFrom(data));
    }

    public static HeartbeatReq parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new HeartbeatReq().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class HeartbeatResp extends
      com.google.protobuf.micro.MessageMicro {
    public HeartbeatResp() {}

    // optional bytes extra = 1;
    public static final int EXTRA_FIELD_NUMBER = 1;
    private boolean hasExtra;
    private com.google.protobuf.micro.ByteStringMicro extra_ = com.google.protobuf.micro.ByteStringMicro.EMPTY;
    public com.google.protobuf.micro.ByteStringMicro getExtra() { return extra_; }
    public boolean hasExtra() { return hasExtra; }
    public HeartbeatResp setExtra(com.google.protobuf.micro.ByteStringMicro value) {
      hasExtra = true;
      extra_ = value;
      return this;
    }
    public HeartbeatResp clearExtra() {
      hasExtra = false;
      extra_ = com.google.protobuf.micro.ByteStringMicro.EMPTY;
      return this;
    }

    // optional uint64 timestamp = 2;
    public static final int TIMESTAMP_FIELD_NUMBER = 2;
    private boolean hasTimestamp;
    private long timestamp_ = 0L;
    public long getTimestamp() { return timestamp_; }
    public boolean hasTimestamp() { return hasTimestamp; }
    public HeartbeatResp setTimestamp(long value) {
      hasTimestamp = true;
      timestamp_ = value;
      return this;
    }
    public HeartbeatResp clearTimestamp() {
      hasTimestamp = false;
      timestamp_ = 0L;
      return this;
    }

    public final HeartbeatResp clear() {
      clearExtra();
      clearTimestamp();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasExtra()) {
        output.writeBytes(1, getExtra());
      }
      if (hasTimestamp()) {
        output.writeUInt64(2, getTimestamp());
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
      if (hasExtra()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeBytesSize(1, getExtra());
      }
      if (hasTimestamp()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt64Size(2, getTimestamp());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public HeartbeatResp mergeFrom(
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
            setExtra(input.readBytes());
            break;
          }
          case 16: {
            setTimestamp(input.readUInt64());
            break;
          }
        }
      }
    }

    public static HeartbeatResp parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (HeartbeatResp) (new HeartbeatResp().mergeFrom(data));
    }

    public static HeartbeatResp parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new HeartbeatResp().mergeFrom(input);
    }

  }

}
