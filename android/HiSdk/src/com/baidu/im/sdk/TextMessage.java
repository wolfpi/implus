package com.baidu.im.sdk;

import android.os.Parcel;
import android.os.Parcelable;

public class TextMessage extends BaseMessage {

	private String text;
	public static final Creator<TextMessage> CREATOR = new Parcelable.Creator<TextMessage>() {
		public TextMessage createFromParcel(Parcel paramParcel) {
			return new TextMessage(paramParcel);
		}

		public TextMessage[] newArray(int paramInt) {
			return new TextMessage[paramInt];
		}
	};

	public TextMessage() {
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String toString() {
		return "txt:\"" + this.text + "\"";
	}

	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(text);
	}

	public TextMessage(Parcel paramParcel) {
		super(paramParcel);
		this.text = paramParcel.readString();
	}

	@Override
	public MessageType getType() {
		return MessageType.Text;
	}

}
