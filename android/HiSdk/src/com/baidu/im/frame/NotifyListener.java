package com.baidu.im.frame;

import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;

public interface NotifyListener {
	ProcessorResult receiveNotify(DownPacket downPacket);
}
