/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.frame;

import com.baidu.im.frame.NetworkChannel.NetworkChannelStatus;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;

/**
 * 发送协议的接口。
 * 
 * @author zhaowei10
 * 
 */
public interface SequenceSender {

    void send(UpPacket upPacket) throws Exception;
    void sendReconnect();
    NetworkChannelStatus getNetworkChannelStatus();
}
