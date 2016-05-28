// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ObjFile {
  private ObjFile() {}
  @SuppressWarnings("hiding")
  public static final class File extends
      com.google.protobuf.micro.MessageMicro {
    public File() {}

    // optional string fileId = 1;
    public static final int FILEID_FIELD_NUMBER = 1;
    private boolean hasFileId;
    private java.lang.String fileId_ = "";
    public java.lang.String getFileId() { return fileId_; }
    public boolean hasFileId() { return hasFileId; }
    public File setFileId(java.lang.String value) {
      hasFileId = true;
      fileId_ = value;
      return this;
    }
    public File clearFileId() {
      hasFileId = false;
      fileId_ = "";
      return this;
    }

    // required string md5 = 2;
    public static final int MD5_FIELD_NUMBER = 2;
    private boolean hasMd5;
    private java.lang.String md5_ = "";
    public java.lang.String getMd5() { return md5_; }
    public boolean hasMd5() { return hasMd5; }
    public File setMd5(java.lang.String value) {
      hasMd5 = true;
      md5_ = value;
      return this;
    }
    public File clearMd5() {
      hasMd5 = false;
      md5_ = "";
      return this;
    }

    // required uint32 size = 3;
    public static final int SIZE_FIELD_NUMBER = 3;
    private boolean hasSize;
    private int size_ = 0;
    public int getSize() { return size_; }
    public boolean hasSize() { return hasSize; }
    public File setSize(int value) {
      hasSize = true;
      size_ = value;
      return this;
    }
    public File clearSize() {
      hasSize = false;
      size_ = 0;
      return this;
    }

    // optional string fileUrl = 4;
    public static final int FILEURL_FIELD_NUMBER = 4;
    private boolean hasFileUrl;
    private java.lang.String fileUrl_ = "";
    public java.lang.String getFileUrl() { return fileUrl_; }
    public boolean hasFileUrl() { return hasFileUrl; }
    public File setFileUrl(java.lang.String value) {
      hasFileUrl = true;
      fileUrl_ = value;
      return this;
    }
    public File clearFileUrl() {
      hasFileUrl = false;
      fileUrl_ = "";
      return this;
    }

    // required string name = 5;
    public static final int NAME_FIELD_NUMBER = 5;
    private boolean hasName;
    private java.lang.String name_ = "";
    public java.lang.String getName() { return name_; }
    public boolean hasName() { return hasName; }
    public File setName(java.lang.String value) {
      hasName = true;
      name_ = value;
      return this;
    }
    public File clearName() {
      hasName = false;
      name_ = "";
      return this;
    }

    // optional string extInfo = 6;
    public static final int EXTINFO_FIELD_NUMBER = 6;
    private boolean hasExtInfo;
    private java.lang.String extInfo_ = "";
    public java.lang.String getExtInfo() { return extInfo_; }
    public boolean hasExtInfo() { return hasExtInfo; }
    public File setExtInfo(java.lang.String value) {
      hasExtInfo = true;
      extInfo_ = value;
      return this;
    }
    public File clearExtInfo() {
      hasExtInfo = false;
      extInfo_ = "";
      return this;
    }

    public final File clear() {
      clearFileId();
      clearMd5();
      clearSize();
      clearFileUrl();
      clearName();
      clearExtInfo();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      if (!hasMd5) return false;
      if (!hasSize) return false;
      if (!hasName) return false;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasFileId()) {
        output.writeString(1, getFileId());
      }
      if (hasMd5()) {
        output.writeString(2, getMd5());
      }
      if (hasSize()) {
        output.writeUInt32(3, getSize());
      }
      if (hasFileUrl()) {
        output.writeString(4, getFileUrl());
      }
      if (hasName()) {
        output.writeString(5, getName());
      }
      if (hasExtInfo()) {
        output.writeString(6, getExtInfo());
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
      if (hasFileId()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(1, getFileId());
      }
      if (hasMd5()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(2, getMd5());
      }
      if (hasSize()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeUInt32Size(3, getSize());
      }
      if (hasFileUrl()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(4, getFileUrl());
      }
      if (hasName()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(5, getName());
      }
      if (hasExtInfo()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(6, getExtInfo());
      }
      cachedSize = size;
      return size;
    }

    @Override
    public File mergeFrom(
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
            setFileId(input.readString());
            break;
          }
          case 18: {
            setMd5(input.readString());
            break;
          }
          case 24: {
            setSize(input.readUInt32());
            break;
          }
          case 34: {
            setFileUrl(input.readString());
            break;
          }
          case 42: {
            setName(input.readString());
            break;
          }
          case 50: {
            setExtInfo(input.readString());
            break;
          }
        }
      }
    }

    public static File parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (File) (new File().mergeFrom(data));
    }

    public static File parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new File().mergeFrom(input);
    }

  }

}
