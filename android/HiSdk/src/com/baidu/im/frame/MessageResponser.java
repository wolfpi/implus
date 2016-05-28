package com.baidu.im.frame;

import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;

public interface MessageResponser {
    public  ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket);
    public  String  getProcessorName();
}
