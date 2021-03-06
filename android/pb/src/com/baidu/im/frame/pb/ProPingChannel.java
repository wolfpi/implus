// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ProPingChannel {
  private ProPingChannel() {}
  @SuppressWarnings("hiding")
  public static final class PingChannelReq extends
      com.google.protobuf.micro.MessageMicro {
    public PingChannelReq() {}

    // optional uint64 timestamp = 1;
    public static final int TIMESTAMP_FIELD_NUMBER = 1;
    private boolean hasTimestamp;
    private long timestamp_ = 0L;
    public long getTimestamp() { return timestamp_; }
    public boolean hasTimestamp() { return hasTimestamp; }
    public PingChannelReq setTimestamp(long value) {
      hasTimestamp = true;
      timestamp_ = value;
      return this;
    }
    public PingChannelReq clearTimestamp() {
      hasTimestamp = false;
      timestamp_ = 0L;
      return this;
    }

    public final PingChannelReq clear() {
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
      if (hasTimestamp()) {
        output.writeUInt64(1, getTimestamp());
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
      if (hasTimestamp()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt64Size(1, getTimestamp());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public PingChannelReq mergeFrom(
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
            setTimestamp(input.readUInt64());
            break;
          }
        }
      }
    }

    public static PingChannelReq parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (PingChannelReq) (new PingChannelReq().mergeFrom(data));
    }

    public static PingChannelReq parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new PingChannelReq().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class PingChannelRsp extends
      com.google.protobuf.micro.MessageMicro {
    public PingChannelRsp() {}

    // optional uint64 timestamp = 1;
    public static final int TIMESTAMP_FIELD_NUMBER = 1;
    private boolean hasTimestamp;
    private long timestamp_ = 0L;
    public long getTimestamp() { return timestamp_; }
    public boolean hasTimestamp() { return hasTimestamp; }
    public PingChannelRsp setTimestamp(long value) {
      hasTimestamp = true;
      timestamp_ = value;
      return this;
    }
    public PingChannelRsp clearTimestamp() {
      hasTimestamp = false;
      timestamp_ = 0L;
      return this;
    }

    public final PingChannelRsp clear() {
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
      if (hasTimestamp()) {
        output.writeUInt64(1, getTimestamp());
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
      if (hasTimestamp()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt64Size(1, getTimestamp());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public PingChannelRsp mergeFrom(
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
            setTimestamp(input.readUInt64());
            break;
          }
        }
      }
    }

    public static PingChannelRsp parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (PingChannelRsp) (new PingChannelRsp().mergeFrom(data));
    }

    public static PingChannelRsp parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new PingChannelRsp().mergeFrom(input);
    }

  }

}
