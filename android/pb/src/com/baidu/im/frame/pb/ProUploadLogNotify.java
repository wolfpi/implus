// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ProUploadLogNotify {
  private ProUploadLogNotify() {}
  // enum ELogLevel
  public static final int IM_LOG_LEVEL_TRACE = 0;
  public static final int IM_LOG_LEVEL_DEBUG = 1;
  public static final int IM_LOG_LEVEL_INFO = 2;
  public static final int IM_LOG_LEVEL_WARN = 3;
  public static final int IM_LOG_LEVEL_ERROR = 4;
  public static final int IM_LOG_LEVEL_FATAL = 5;

  @SuppressWarnings("hiding")
  public static final class UploadLogNotify extends
      com.google.protobuf.micro.MessageMicro {
    public UploadLogNotify() {}

    // optional int32 startTime = 1;
    public static final int STARTTIME_FIELD_NUMBER = 1;
    private boolean hasStartTime;
    private int startTime_ = 0;
    public int getStartTime() { return startTime_; }
    public boolean hasStartTime() { return hasStartTime; }
    public UploadLogNotify setStartTime(int value) {
      hasStartTime = true;
      startTime_ = value;
      return this;
    }
    public UploadLogNotify clearStartTime() {
      hasStartTime = false;
      startTime_ = 0;
      return this;
    }

    // optional int32 endTime = 2;
    public static final int ENDTIME_FIELD_NUMBER = 2;
    private boolean hasEndTime;
    private int endTime_ = 0;
    public int getEndTime() { return endTime_; }
    public boolean hasEndTime() { return hasEndTime; }
    public UploadLogNotify setEndTime(int value) {
      hasEndTime = true;
      endTime_ = value;
      return this;
    }
    public UploadLogNotify clearEndTime() {
      hasEndTime = false;
      endTime_ = 0;
      return this;
    }

    // optional .ELogLevel logLevel = 3;
    public static final int LOGLEVEL_FIELD_NUMBER = 3;
    private boolean hasLogLevel;
    private int logLevel_ = com.baidu.im.frame.pb.ProUploadLogNotify.IM_LOG_LEVEL_TRACE;
    public boolean hasLogLevel() { return hasLogLevel; }
    public int getLogLevel() { return logLevel_; }
    public UploadLogNotify setLogLevel(int value) {
      hasLogLevel = true;
      logLevel_ = value;
      return this;
    }
    public UploadLogNotify clearLogLevel() {
      hasLogLevel = false;
      logLevel_ = com.baidu.im.frame.pb.ProUploadLogNotify.IM_LOG_LEVEL_TRACE;
      return this;
    }

    // optional string token = 4;
    public static final int TOKEN_FIELD_NUMBER = 4;
    private boolean hasToken;
    private java.lang.String token_ = "";
    public java.lang.String getToken() { return token_; }
    public boolean hasToken() { return hasToken; }
    public UploadLogNotify setToken(java.lang.String value) {
      hasToken = true;
      token_ = value;
      return this;
    }
    public UploadLogNotify clearToken() {
      hasToken = false;
      token_ = "";
      return this;
    }

    // optional int32 syncTime = 5;
    public static final int SYNCTIME_FIELD_NUMBER = 5;
    private boolean hasSyncTime;
    private int syncTime_ = 0;
    public int getSyncTime() { return syncTime_; }
    public boolean hasSyncTime() { return hasSyncTime; }
    public UploadLogNotify setSyncTime(int value) {
      hasSyncTime = true;
      syncTime_ = value;
      return this;
    }
    public UploadLogNotify clearSyncTime() {
      hasSyncTime = false;
      syncTime_ = 0;
      return this;
    }

    public final UploadLogNotify clear() {
      clearStartTime();
      clearEndTime();
      clearLogLevel();
      clearToken();
      clearSyncTime();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasStartTime()) {
        output.writeInt32(1, getStartTime());
      }
      if (hasEndTime()) {
        output.writeInt32(2, getEndTime());
      }
      if (hasLogLevel()) {
        output.writeInt32(3, getLogLevel());
      }
      if (hasToken()) {
        output.writeString(4, getToken());
      }
      if (hasSyncTime()) {
        output.writeInt32(5, getSyncTime());
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
      if (hasStartTime()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeInt32Size(1, getStartTime());
      }
      if (hasEndTime()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeInt32Size(2, getEndTime());
      }
      if (hasLogLevel()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeInt32Size(3, getLogLevel());
      }
      if (hasToken()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(4, getToken());
      }
      if (hasSyncTime()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeInt32Size(5, getSyncTime());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public UploadLogNotify mergeFrom(
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
            setStartTime(input.readInt32());
            break;
          }
          case 16: {
            setEndTime(input.readInt32());
            break;
          }
          case 24: {
              setLogLevel(input.readInt32());
            break;
          }
          case 34: {
            setToken(input.readString());
            break;
          }
          case 40: {
            setSyncTime(input.readInt32());
            break;
          }
        }
      }
    }

    public static UploadLogNotify parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (UploadLogNotify) (new UploadLogNotify().mergeFrom(data));
    }

    public static UploadLogNotify parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new UploadLogNotify().mergeFrom(input);
    }

  }

}