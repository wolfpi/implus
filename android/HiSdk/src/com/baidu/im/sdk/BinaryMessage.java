package com.baidu.im.sdk;

import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.im.frame.pb.ObjInformMessage.InformMessage;

public class BinaryMessage extends BaseMessage {
    private String serviceName;
    private String methodName;
    private byte[] data;
    private int contentType;// 0为push推送，1为im消息
    private InformMessage offlineNotify = null;

    public static final Creator<BinaryMessage> CREATOR = new Parcelable.Creator<BinaryMessage>() {
        public BinaryMessage createFromParcel(Parcel paramParcel) {
            return new BinaryMessage(paramParcel);
        }

        public BinaryMessage[] newArray(int paramInt) {
            return new BinaryMessage[paramInt];
        }
    };

    public BinaryMessage() {
        messageType = MessageType.Binary;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String toString() {
        if (data != null) {
            return "data:" + Arrays.toString(data);
        } else {
            return "data = null";
        }
    }

    public InformMessage getOfflineNotify() {
        return offlineNotify;
    }

    public void setOfflineNotify(InformMessage offlineNotify) {
        this.offlineNotify = offlineNotify;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(serviceName);
        dest.writeString(methodName);
        dest.writeInt(contentType);
        if (data == null || data.length == 0) {
            dest.writeInt(0);
        } else {
            dest.writeInt(data.length);
            dest.writeByteArray(data);
        }
        if (offlineNotify == null) {
            dest.writeInt(0);
        } else {
            byte[] offlineData = offlineNotify.toByteArray();
            dest.writeInt(offlineData.length);
            dest.writeByteArray(offlineData);
        }
    }

    public BinaryMessage(Parcel paramParcel) {
        super(paramParcel);
        serviceName = paramParcel.readString();
        methodName = paramParcel.readString();
        contentType = paramParcel.readInt();
        int len = paramParcel.readInt();
        if (len > 0) {
            data = new byte[len];
            paramParcel.readByteArray(data);
        }
        len = paramParcel.readInt();
        if (len > 0) {
            data = new byte[len];
            paramParcel.readByteArray(data);
            InformMessage offlineNotifyBulider = new  InformMessage();  // migrate from builder
            try {
                offlineNotifyBulider.mergeFrom(data);
                offlineNotify = offlineNotifyBulider;
            } catch (Exception e) {

            }
        }
    }

    @Override
    public MessageType getType() {
        return MessageType.Binary;
    }

}
