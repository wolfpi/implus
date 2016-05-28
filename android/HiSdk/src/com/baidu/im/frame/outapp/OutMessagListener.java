package com.baidu.im.frame.outapp;

import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;

public interface OutMessagListener {
	void onReceive(DownPacket downPacket);
	void onChannelKeyRecv(String channelKey);
}
