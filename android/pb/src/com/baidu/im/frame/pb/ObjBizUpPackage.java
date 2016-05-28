// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ObjBizUpPackage {
  private ObjBizUpPackage() {}
  @SuppressWarnings("hiding")
  public static final class BizUpPackage extends
      com.google.protobuf.micro.MessageMicro {
    public BizUpPackage() {}

    // required .EPacketType packetType = 1;
    public static final int PACKETTYPE_FIELD_NUMBER = 1;
    private boolean hasPacketType;
    private int packetType_ = com.baidu.im.frame.pb.EnumPacketType.REQUEST;
    public boolean hasPacketType() { return hasPacketType; }
    public int getPacketType() { return packetType_; }
    public BizUpPackage setPacketType(int value) {
      hasPacketType = true;
      packetType_ = value;
      return this;
    }
    public BizUpPackage clearPacketType() {
      hasPacketType = false;
      packetType_ = com.baidu.im.frame.pb.EnumPacketType.REQUEST;
      return this;
    }

    // optional bytes busiData = 2;
    public static final int BUSIDATA_FIELD_NUMBER = 2;
    private boolean hasBusiData;
    private com.google.protobuf.micro.ByteStringMicro busiData_ = com.google.protobuf.micro.ByteStringMicro.EMPTY;
    public com.google.protobuf.micro.ByteStringMicro getBusiData() { return busiData_; }
    public boolean hasBusiData() { return hasBusiData; }
    public BizUpPackage setBusiData(com.google.protobuf.micro.ByteStringMicro value) {
      hasBusiData = true;
      busiData_ = value;
      return this;
    }
    public BizUpPackage clearBusiData() {
      hasBusiData = false;
      busiData_ = com.google.protobuf.micro.ByteStringMicro.EMPTY;
      return this;
    }

    // optional uint32 chunkId = 3 [default = 0];
    public static final int CHUNKID_FIELD_NUMBER = 3;
    private boolean hasChunkId;
    private int chunkId_ = 0;
    public int getChunkId() { return chunkId_; }
    public boolean hasChunkId() { return hasChunkId; }
    public BizUpPackage setChunkId(int value) {
      hasChunkId = true;
      chunkId_ = value;
      return this;
    }
    public BizUpPackage clearChunkId() {
      hasChunkId = false;
      chunkId_ = 0;
      return this;
    }

    public final BizUpPackage clear() {
      clearPacketType();
      clearBusiData();
      clearChunkId();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      if (!hasPacketType) return false;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasPacketType()) {
        output.writeInt32(1, getPacketType());
      }
      if (hasBusiData()) {
        output.writeBytes(2, getBusiData());
      }
      if (hasChunkId()) {
        output.writeUInt32(3, getChunkId());
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
      if (hasPacketType()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeInt32Size(1, getPacketType());
      }
      if (hasBusiData()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeBytesSize(2, getBusiData());
      }
      if (hasChunkId()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt32Size(3, getChunkId());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public BizUpPackage mergeFrom(
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
              setPacketType(input.readInt32());
            break;
          }
          case 18: {
            setBusiData(input.readBytes());
            break;
          }
          case 24: {
            setChunkId(input.readUInt32());
            break;
          }
        }
      }
    }

    public static BizUpPackage parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (BizUpPackage) (new BizUpPackage().mergeFrom(data));
    }

    public static BizUpPackage parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new BizUpPackage().mergeFrom(input);
    }

  }

}
