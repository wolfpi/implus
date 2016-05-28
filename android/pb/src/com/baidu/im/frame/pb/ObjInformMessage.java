// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.baidu.im.frame.pb;

public final class ObjInformMessage {
  private ObjInformMessage() {}
  @SuppressWarnings("hiding")
  public static final class InformMessage extends
      com.google.protobuf.micro.MessageMicro {
    public InformMessage() {}

    // optional string title = 1;
    public static final int TITLE_FIELD_NUMBER = 1;
    private boolean hasTitle;
    private java.lang.String title_ = "";
    public java.lang.String getTitle() { return title_; }
    public boolean hasTitle() { return hasTitle; }
    public InformMessage setTitle(java.lang.String value) {
      hasTitle = true;
      title_ = value;
      return this;
    }
    public InformMessage clearTitle() {
      hasTitle = false;
      title_ = "";
      return this;
    }

    // required string description = 2;
    public static final int DESCRIPTION_FIELD_NUMBER = 2;
    private boolean hasDescription;
    private java.lang.String description_ = "";
    public java.lang.String getDescription() { return description_; }
    public boolean hasDescription() { return hasDescription; }
    public InformMessage setDescription(java.lang.String value) {
      hasDescription = true;
      description_ = value;
      return this;
    }
    public InformMessage clearDescription() {
      hasDescription = false;
      description_ = "";
      return this;
    }

    // repeated .InformDesc imformDescs = 3;
    public static final int IMFORMDESCS_FIELD_NUMBER = 3;
    private java.util.List<com.baidu.im.frame.pb.ObjInformDesc.InformDesc> imformDescs_ =
      java.util.Collections.emptyList();
    public java.util.List<com.baidu.im.frame.pb.ObjInformDesc.InformDesc> getImformDescsList() {
      return imformDescs_;
    }
    public int getImformDescsCount() { return imformDescs_.size(); }
    public com.baidu.im.frame.pb.ObjInformDesc.InformDesc getImformDescs(int index) {
      return imformDescs_.get(index);
    }
    public InformMessage setImformDescs(int index, com.baidu.im.frame.pb.ObjInformDesc.InformDesc value) {
      if (value == null) {
        throw new NullPointerException();
      }
      imformDescs_.set(index, value);
      return this;
    }
    public InformMessage addImformDescs(com.baidu.im.frame.pb.ObjInformDesc.InformDesc value) {
      if (value == null) {
        throw new NullPointerException();
      }
      if (imformDescs_.isEmpty()) {
        imformDescs_ = new java.util.ArrayList<com.baidu.im.frame.pb.ObjInformDesc.InformDesc>();
      }
      imformDescs_.add(value);
      return this;
    }
    public InformMessage clearImformDescs() {
      imformDescs_ = java.util.Collections.emptyList();
      return this;
    }

    // repeated .KV customContents = 4;
    public static final int CUSTOMCONTENTS_FIELD_NUMBER = 4;
    private java.util.List<com.baidu.im.frame.pb.ObjKv.KV> customContents_ =
      java.util.Collections.emptyList();
    public java.util.List<com.baidu.im.frame.pb.ObjKv.KV> getCustomContentsList() {
      return customContents_;
    }
    public int getCustomContentsCount() { return customContents_.size(); }
    public com.baidu.im.frame.pb.ObjKv.KV getCustomContents(int index) {
      return customContents_.get(index);
    }
    public InformMessage setCustomContents(int index, com.baidu.im.frame.pb.ObjKv.KV value) {
      if (value == null) {
        throw new NullPointerException();
      }
      customContents_.set(index, value);
      return this;
    }
    public InformMessage addCustomContents(com.baidu.im.frame.pb.ObjKv.KV value) {
      if (value == null) {
        throw new NullPointerException();
      }
      if (customContents_.isEmpty()) {
        customContents_ = new java.util.ArrayList<com.baidu.im.frame.pb.ObjKv.KV>();
      }
      customContents_.add(value);
      return this;
    }
    public InformMessage clearCustomContents() {
      customContents_ = java.util.Collections.emptyList();
      return this;
    }

    public final InformMessage clear() {
      clearTitle();
      clearDescription();
      clearImformDescs();
      clearCustomContents();
      cachedSize = -1;
      return this;
    }

    public final boolean isInitialized() {
      if (!hasDescription) return false;
      for (com.baidu.im.frame.pb.ObjInformDesc.InformDesc element : getImformDescsList()) {
        if (!element.isInitialized()) return false;
      }
      for (com.baidu.im.frame.pb.ObjKv.KV element : getCustomContentsList()) {
        if (!element.isInitialized()) return false;
      }
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.micro.CodedOutputStreamMicro output)
                        throws java.io.IOException {
      if (hasTitle()) {
        output.writeString(1, getTitle());
      }
      if (hasDescription()) {
        output.writeString(2, getDescription());
      }
      for (com.baidu.im.frame.pb.ObjInformDesc.InformDesc element : getImformDescsList()) {
        output.writeMessage(3, element);
      }
      for (com.baidu.im.frame.pb.ObjKv.KV element : getCustomContentsList()) {
        output.writeMessage(4, element);
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
      if (hasTitle()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(1, getTitle());
      }
      if (hasDescription()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeStringSize(2, getDescription());
      }
      for (com.baidu.im.frame.pb.ObjInformDesc.InformDesc element : getImformDescsList()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeMessageSize(3, element);
      }
      for (com.baidu.im.frame.pb.ObjKv.KV element : getCustomContentsList()) {
        size += com.google.protobuf.micro.CodedOutputStreamMicro
          .computeMessageSize(4, element);
      }
      cachedSize = size;
      return size;
    }

    @Override
    public InformMessage mergeFrom(
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
            setTitle(input.readString());
            break;
          }
          case 18: {
            setDescription(input.readString());
            break;
          }
          case 26: {
            com.baidu.im.frame.pb.ObjInformDesc.InformDesc value = new com.baidu.im.frame.pb.ObjInformDesc.InformDesc();
            input.readMessage(value);
            addImformDescs(value);
            break;
          }
          case 34: {
            com.baidu.im.frame.pb.ObjKv.KV value = new com.baidu.im.frame.pb.ObjKv.KV();
            input.readMessage(value);
            addCustomContents(value);
            break;
          }
        }
      }
    }

    public static InformMessage parseFrom(byte[] data)
        throws com.google.protobuf.micro.InvalidProtocolBufferMicroException {
      return (InformMessage) (new InformMessage().mergeFrom(data));
    }

    public static InformMessage parseFrom(
            com.google.protobuf.micro.CodedInputStreamMicro input)
        throws java.io.IOException {
      return new InformMessage().mergeFrom(input);
    }

  }

}
