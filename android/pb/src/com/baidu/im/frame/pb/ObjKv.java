// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ObjKv {
  private ObjKv() {}
  @SuppressWarnings("hiding")
  public static final class KV extends
      com.google.protobuf.micro.MessageMicro {
    public KV() {}

    // required string key = 1;
    public static final int KEY_FIELD_NUMBER = 1;
    private boolean hasKey;
    private java.lang.String key_ = "";
    public java.lang.String getKey() { return key_; }
    public boolean hasKey() { return hasKey; }
    public KV setKey(java.lang.String value) {
      hasKey = true;
      key_ = value;
      return this;
    }
    public KV clearKey() {
      hasKey = false;
      key_ = "";
      return this;
    }

    // required string data = 2;
    public static final int DATA_FIELD_NUMBER = 2;
    private boolean hasData;
    private java.lang.String data_ = "";
    public java.lang.String getData() { return data_; }
    public boolean hasData() { return hasData; }
    public KV setData(java.lang.String value) {
      hasData = true;
      data_ = value;
      return this;
    }
    public KV clearData() {
      hasData = false;
      data_ = "";
      return this;
    }

    public final KV clear() {
      clearKey();
      clearData();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      if (!hasKey) return false;
      if (!hasData) return false;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasKey()) {
        output.writeString(1, getKey());
      }
      if (hasData()) {
        output.writeString(2, getData());
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
      if (hasKey()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(1, getKey());
      }
      if (hasData()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(2, getData());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public KV mergeFrom(
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
            setKey(input.readString());
            break;
          }
          case 18: {
            setData(input.readString());
            break;
          }
        }
      }
    }

    public static KV parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (KV) (new KV().mergeFrom(data));
    }

    public static KV parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new KV().mergeFrom(input);
    }

  }

}
