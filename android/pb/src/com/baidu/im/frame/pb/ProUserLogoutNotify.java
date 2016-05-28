// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ProUserLogoutNotify {
  private ProUserLogoutNotify() {}
  @SuppressWarnings("hiding")
  public static final class LogoutNotify extends
      com.google.protobuf.micro.MessageMicro {
    public LogoutNotify() {}

    // optional string cause = 1;
    public static final int CAUSE_FIELD_NUMBER = 1;
    private boolean hasCause;
    private java.lang.String cause_ = "";
    public java.lang.String getCause() { return cause_; }
    public boolean hasCause() { return hasCause; }
    public LogoutNotify setCause(java.lang.String value) {
      hasCause = true;
      cause_ = value;
      return this;
    }
    public LogoutNotify clearCause() {
      hasCause = false;
      cause_ = "";
      return this;
    }

    public final LogoutNotify clear() {
      clearCause();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasCause()) {
        output.writeString(1, getCause());
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
      if (hasCause()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(1, getCause());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public LogoutNotify mergeFrom(
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
            setCause(input.readString());
            break;
          }
        }
      }
    }

    public static LogoutNotify parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (LogoutNotify) (new LogoutNotify().mergeFrom(data));
    }

    public static LogoutNotify parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new LogoutNotify().mergeFrom(input);
    }

  }

}