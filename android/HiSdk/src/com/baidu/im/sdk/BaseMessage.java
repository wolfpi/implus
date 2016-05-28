package com.baidu.im.sdk;

import android.os.Parcel;
import android.os.Parcelable;


public class BaseMessage implements ImMessage, Parcelable {

    // message id
    protected String messageId;
    protected MessageType messageType;

    public BaseMessage() {
    }

    protected BaseMessage(Parcel paramParcel) {
        this.messageId = paramParcel.readString();
        messageType = MessageType.valueOf(paramParcel.readString());
    }

    public static final Creator<BaseMessage> CREATOR = new Parcelable.Creator<BaseMessage>() {
        public BaseMessage createFromParcel(Parcel paramParcel) {
            return new BaseMessage(paramParcel);
        }

        public BaseMessage[] newArray(int paramInt) {
            return new BaseMessage[paramInt];
        }
    };

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public MessageType getType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String toString() {
        return "messageId:" + this.messageId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(messageId);
        dest.writeString(messageType.name());
    }

    public int describeContents() {
        return 0;
    }
}
