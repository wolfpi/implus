package com.baidu.im.sdk;


public interface IMessageResultCallback extends IMessageCallback {

	void onSuccess(String description, byte[]data);

	void onFail(int errorCode);
}
