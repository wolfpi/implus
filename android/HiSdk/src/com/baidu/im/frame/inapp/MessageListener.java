package com.baidu.im.frame.inapp;

import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;

public interface MessageListener {
	 public void receive(DownPacket downPacket);
}
