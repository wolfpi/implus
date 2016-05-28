package com.baidu.im.sdk;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginMessage extends BaseMessage implements Parcelable {

    private String accountId;
    private String token;

    public LoginMessage() {
        messageType = MessageType.Login;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * @return the bduss
     */
    public String getToken() {
        return token;
    }

    /**
     * @param bduss the bduss to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    public static final Creator<LoginMessage> CREATOR = new Parcelable.Creator<LoginMessage>() {
        public LoginMessage createFromParcel(Parcel paramParcel) {
            return new LoginMessage(paramParcel);
        }

        public LoginMessage[] newArray(int paramInt) {
            return new LoginMessage[paramInt];
        }
    };

    public String toString() {
        return "accountId:\"" + this.accountId + " \" token=\"" + this.token + "\"";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.accountId);
        dest.writeString(this.token);
    }

    private LoginMessage(Parcel paramParcel) {
        super(paramParcel);
        this.accountId = paramParcel.readString();
        this.token = paramParcel.readString();
    }

    @Override
    public MessageType getType() {
        return MessageType.Login;
    }
}
