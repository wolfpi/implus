// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ProGetUploadSign {
  private ProGetUploadSign() {}
  @SuppressWarnings("hiding")
  public static final class GetUploadSignReqItem extends
      com.google.protobuf.micro.MessageMicro {
    public GetUploadSignReqItem() {}

    // required string fileName = 1;
    public static final int FILENAME_FIELD_NUMBER = 1;
    private boolean hasFileName;
    private java.lang.String fileName_ = "";
    public java.lang.String getFileName() { return fileName_; }
    public boolean hasFileName() { return hasFileName; }
    public GetUploadSignReqItem setFileName(java.lang.String value) {
      hasFileName = true;
      fileName_ = value;
      return this;
    }
    public GetUploadSignReqItem clearFileName() {
      hasFileName = false;
      fileName_ = "";
      return this;
    }

    // required uint32 fileSize = 2;
    public static final int FILESIZE_FIELD_NUMBER = 2;
    private boolean hasFileSize;
    private int fileSize_ = 0;
    public int getFileSize() { return fileSize_; }
    public boolean hasFileSize() { return hasFileSize; }
    public GetUploadSignReqItem setFileSize(int value) {
      hasFileSize = true;
      fileSize_ = value;
      return this;
    }
    public GetUploadSignReqItem clearFileSize() {
      hasFileSize = false;
      fileSize_ = 0;
      return this;
    }

    // required string md5 = 3;
    public static final int MD5_FIELD_NUMBER = 3;
    private boolean hasMd5;
    private java.lang.String md5_ = "";
    public java.lang.String getMd5() { return md5_; }
    public boolean hasMd5() { return hasMd5; }
    public GetUploadSignReqItem setMd5(java.lang.String value) {
      hasMd5 = true;
      md5_ = value;
      return this;
    }
    public GetUploadSignReqItem clearMd5() {
      hasMd5 = false;
      md5_ = "";
      return this;
    }

    // required string bmd5 = 4;
    public static final int BMD5_FIELD_NUMBER = 4;
    private boolean hasBmd5;
    private java.lang.String bmd5_ = "";
    public java.lang.String getBmd5() { return bmd5_; }
    public boolean hasBmd5() { return hasBmd5; }
    public GetUploadSignReqItem setBmd5(java.lang.String value) {
      hasBmd5 = true;
      bmd5_ = value;
      return this;
    }
    public GetUploadSignReqItem clearBmd5() {
      hasBmd5 = false;
      bmd5_ = "";
      return this;
    }

    // optional string bossHost = 5;
    public static final int BOSSHOST_FIELD_NUMBER = 5;
    private boolean hasBossHost;
    private java.lang.String bossHost_ = "";
    public java.lang.String getBossHost() { return bossHost_; }
    public boolean hasBossHost() { return hasBossHost; }
    public GetUploadSignReqItem setBossHost(java.lang.String value) {
      hasBossHost = true;
      bossHost_ = value;
      return this;
    }
    public GetUploadSignReqItem clearBossHost() {
      hasBossHost = false;
      bossHost_ = "";
      return this;
    }

    // required .EFileType fileType = 6;
    public static final int FILETYPE_FIELD_NUMBER = 6;
    private boolean hasFileType;
    private int fileType_ = com.baidu.im.frame.pb.EnumFileType.FILE_IMAGE;
    public boolean hasFileType() { return hasFileType; }
    public int getFileType() { return fileType_; }
    public GetUploadSignReqItem setFileType(int value) {
      hasFileType = true;
      fileType_ = value;
      return this;
    }
    public GetUploadSignReqItem clearFileType() {
      hasFileType = false;
      fileType_ = com.baidu.im.frame.pb.EnumFileType.FILE_IMAGE;
      return this;
    }

    public final GetUploadSignReqItem clear() {
      clearFileName();
      clearFileSize();
      clearMd5();
      clearBmd5();
      clearBossHost();
      clearFileType();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      if (!hasFileName) return false;
      if (!hasFileSize) return false;
      if (!hasMd5) return false;
      if (!hasBmd5) return false;
      if (!hasFileType) return false;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasFileName()) {
        output.writeString(1, getFileName());
      }
      if (hasFileSize()) {
        output.writeUInt32(2, getFileSize());
      }
      if (hasMd5()) {
        output.writeString(3, getMd5());
      }
      if (hasBmd5()) {
        output.writeString(4, getBmd5());
      }
      if (hasBossHost()) {
        output.writeString(5, getBossHost());
      }
      if (hasFileType()) {
        output.writeInt32(6, getFileType());
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
      if (hasFileName()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(1, getFileName());
      }
      if (hasFileSize()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt32Size(2, getFileSize());
      }
      if (hasMd5()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(3, getMd5());
      }
      if (hasBmd5()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(4, getBmd5());
      }
      if (hasBossHost()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(5, getBossHost());
      }
      if (hasFileType()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeInt32Size(6, getFileType());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public GetUploadSignReqItem mergeFrom(
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
            setFileName(input.readString());
            break;
          }
          case 16: {
            setFileSize(input.readUInt32());
            break;
          }
          case 26: {
            setMd5(input.readString());
            break;
          }
          case 34: {
            setBmd5(input.readString());
            break;
          }
          case 42: {
            setBossHost(input.readString());
            break;
          }
          case 48: {
              setFileType(input.readInt32());
            break;
          }
        }
      }
    }

    public static GetUploadSignReqItem parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (GetUploadSignReqItem) (new GetUploadSignReqItem().mergeFrom(data));
    }

    public static GetUploadSignReqItem parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new GetUploadSignReqItem().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class GetUploadSignRspItem extends
      com.google.protobuf.micro.MessageMicro {
    public GetUploadSignRspItem() {}

    // required string fid = 1;
    public static final int FID_FIELD_NUMBER = 1;
    private boolean hasFid;
    private java.lang.String fid_ = "";
    public java.lang.String getFid() { return fid_; }
    public boolean hasFid() { return hasFid; }
    public GetUploadSignRspItem setFid(java.lang.String value) {
      hasFid = true;
      fid_ = value;
      return this;
    }
    public GetUploadSignRspItem clearFid() {
      hasFid = false;
      fid_ = "";
      return this;
    }

    // required string sign = 2;
    public static final int SIGN_FIELD_NUMBER = 2;
    private boolean hasSign;
    private java.lang.String sign_ = "";
    public java.lang.String getSign() { return sign_; }
    public boolean hasSign() { return hasSign; }
    public GetUploadSignRspItem setSign(java.lang.String value) {
      hasSign = true;
      sign_ = value;
      return this;
    }
    public GetUploadSignRspItem clearSign() {
      hasSign = false;
      sign_ = "";
      return this;
    }

    // required string uploadUrl = 3;
    public static final int UPLOADURL_FIELD_NUMBER = 3;
    private boolean hasUploadUrl;
    private java.lang.String uploadUrl_ = "";
    public java.lang.String getUploadUrl() { return uploadUrl_; }
    public boolean hasUploadUrl() { return hasUploadUrl; }
    public GetUploadSignRspItem setUploadUrl(java.lang.String value) {
      hasUploadUrl = true;
      uploadUrl_ = value;
      return this;
    }
    public GetUploadSignRspItem clearUploadUrl() {
      hasUploadUrl = false;
      uploadUrl_ = "";
      return this;
    }

    // required bool exist = 4;
    public static final int EXIST_FIELD_NUMBER = 4;
    private boolean hasExist;
    private boolean exist_ = false;
    public boolean getExist() { return exist_; }
    public boolean hasExist() { return hasExist; }
    public GetUploadSignRspItem setExist(boolean value) {
      hasExist = true;
      exist_ = value;
      return this;
    }
    public GetUploadSignRspItem clearExist() {
      hasExist = false;
      exist_ = false;
      return this;
    }

    public final GetUploadSignRspItem clear() {
      clearFid();
      clearSign();
      clearUploadUrl();
      clearExist();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      if (!hasFid) return false;
      if (!hasSign) return false;
      if (!hasUploadUrl) return false;
      if (!hasExist) return false;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasFid()) {
        output.writeString(1, getFid());
      }
      if (hasSign()) {
        output.writeString(2, getSign());
      }
      if (hasUploadUrl()) {
        output.writeString(3, getUploadUrl());
      }
      if (hasExist()) {
        output.writeBool(4, getExist());
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
      if (hasFid()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(1, getFid());
      }
      if (hasSign()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(2, getSign());
      }
      if (hasUploadUrl()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(3, getUploadUrl());
      }
      if (hasExist()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeBoolSize(4, getExist());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public GetUploadSignRspItem mergeFrom(
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
            setFid(input.readString());
            break;
          }
          case 18: {
            setSign(input.readString());
            break;
          }
          case 26: {
            setUploadUrl(input.readString());
            break;
          }
          case 32: {
            setExist(input.readBool());
            break;
          }
        }
      }
    }

    public static GetUploadSignRspItem parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (GetUploadSignRspItem) (new GetUploadSignRspItem().mergeFrom(data));
    }

    public static GetUploadSignRspItem parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new GetUploadSignRspItem().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class GetUploadSignReq extends
      com.google.protobuf.micro.MessageMicro {
    public GetUploadSignReq() {}

    // repeated .GetUploadSignReqItem reqList = 1;
    public static final int REQLIST_FIELD_NUMBER = 1;
    private java.util.List<com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignReqItem> reqList_ =
      java.util.Collections.emptyList();
    public java.util.List<com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignReqItem> getReqListList() {
      return reqList_;
    }
    public int getReqListCount() { return reqList_.size(); }
    public com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignReqItem getReqList(int index) {
      return reqList_.get(index);
    }
    public GetUploadSignReq setReqList(int index, com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignReqItem value) {
      if (value == null) {
        throw new NullPointerException();
      }
      reqList_.set(index, value);
      return this;
    }
    public GetUploadSignReq addReqList(com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignReqItem value) {
      if (value == null) {
        throw new NullPointerException();
      }
      if (reqList_.isEmpty()) {
        reqList_ = new java.util.ArrayList<com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignReqItem>();
      }
      reqList_.add(value);
      return this;
    }
    public GetUploadSignReq clearReqList() {
      reqList_ = java.util.Collections.emptyList();
      return this;
    }

    public final GetUploadSignReq clear() {
      clearReqList();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      for (com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignReqItem element : getReqListList()) {
        if (!element.isInitialized()) return false;
      }
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      for (com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignReqItem element : getReqListList()) {
        output.writeMessage(1, element);
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
      for (com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignReqItem element : getReqListList()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeMessageSize(1, element);
      }
      cachedSize = size;
      return size;
    }

    @Override
    public GetUploadSignReq mergeFrom(
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
            com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignReqItem value = new com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignReqItem();
            input.readMessage(value);
            addReqList(value);
            break;
          }
        }
      }
    }

    public static GetUploadSignReq parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (GetUploadSignReq) (new GetUploadSignReq().mergeFrom(data));
    }

    public static GetUploadSignReq parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new GetUploadSignReq().mergeFrom(input);
    }

  }

  @SuppressWarnings("hiding")
  public static final class GetUploadSignRsp extends
      com.google.protobuf.micro.MessageMicro {
    public GetUploadSignRsp() {}

    // repeated .GetUploadSignRspItem rspList = 1;
    public static final int RSPLIST_FIELD_NUMBER = 1;
    private java.util.List<com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignRspItem> rspList_ =
      java.util.Collections.emptyList();
    public java.util.List<com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignRspItem> getRspListList() {
      return rspList_;
    }
    public int getRspListCount() { return rspList_.size(); }
    public com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignRspItem getRspList(int index) {
      return rspList_.get(index);
    }
    public GetUploadSignRsp setRspList(int index, com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignRspItem value) {
      if (value == null) {
        throw new NullPointerException();
      }
      rspList_.set(index, value);
      return this;
    }
    public GetUploadSignRsp addRspList(com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignRspItem value) {
      if (value == null) {
        throw new NullPointerException();
      }
      if (rspList_.isEmpty()) {
        rspList_ = new java.util.ArrayList<com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignRspItem>();
      }
      rspList_.add(value);
      return this;
    }
    public GetUploadSignRsp clearRspList() {
      rspList_ = java.util.Collections.emptyList();
      return this;
    }

    public final GetUploadSignRsp clear() {
      clearRspList();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      for (com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignRspItem element : getRspListList()) {
        if (!element.isInitialized()) return false;
      }
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      for (com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignRspItem element : getRspListList()) {
        output.writeMessage(1, element);
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
      for (com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignRspItem element : getRspListList()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeMessageSize(1, element);
      }
      cachedSize = size;
      return size;
    }

    @Override
    public GetUploadSignRsp mergeFrom(
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
            com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignRspItem value = new com.baidu.im.frame.pb.ProGetUploadSign.GetUploadSignRspItem();
            input.readMessage(value);
            addRspList(value);
            break;
          }
        }
      }
    }

    public static GetUploadSignRsp parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (GetUploadSignRsp) (new GetUploadSignRsp().mergeFrom(data));
    }

    public static GetUploadSignRsp parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new GetUploadSignRsp().mergeFrom(input);
    }

  }

}