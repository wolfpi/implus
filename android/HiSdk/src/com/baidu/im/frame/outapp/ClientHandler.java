package com.baidu.im.frame.outapp;

import android.os.Messenger;

import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;

public class ClientHandler {
	private Messenger messenger;
	private int clientId;
	private String appId;

	public ClientHandler(int clientId, Messenger messenger) {
		setMessenger(messenger);
		setClientId(clientId);
	}

	public void hander(DownPacket downPacket) {

	}

	int getClientId() {
		return clientId;
	}

	void setClientId(int clientId) {
		this.clientId = clientId;
	}

	String getAppId() {
		return appId;
	}

	void setAppId(String appId) {
		this.appId = appId;
	}

	Messenger getMessenger() {
		return messenger;
	}

	void setMessenger(Messenger messenger) {
		this.messenger = messenger;
	}

}
