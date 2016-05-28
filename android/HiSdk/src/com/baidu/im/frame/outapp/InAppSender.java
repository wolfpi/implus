package com.baidu.im.frame.outapp;

import android.os.Messenger;

import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;

public interface InAppSender {
	  public void send(int seq, int appId, byte[]data);
	  public void sendNetworkChange(byte[] data, Messenger msger);
	  public void sendChannelKey(byte[] data, Messenger msger);
	  public void send(DownPacket downPacket);
}
