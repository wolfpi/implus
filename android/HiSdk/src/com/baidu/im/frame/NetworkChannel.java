package com.baidu.im.frame;

import java.io.IOException;

import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;

/**
 * 定义了网络通道的接口
 * 
 * @author zhaowei10
 * 
 */
public interface NetworkChannel {

    public enum NetworkChannelStatus {

        Initializing,

                Connecting,

                Connected,

                Disconnected,

                Closed
    }

    /**
     * 打开
     */
    void open(String[] ips, int[] ports);

    /**
     * 发送消息。
     * 
     * @param upPacket
     * @return
     * @throws IOException
     */
    void send(UpPacket upPacket) throws IOException;

    /**
     * 取得唯一的seq
     * 
     * @return
     */
    int getSeq();

    /**
     * 接收消息。
     * 
     * @param downPacket
     */
    void receive(DownPacket downPacket);

    /**
     * 
     * @return
     */
    void setNetworkChannelListener(INetworkChannelListener networkChannelListener);

    /**
     * 关闭。
     */
    void close();

    /**
     * 一次心跳。
     */
    void heartbeat();

    /**
     * 网络变化
     * 
     * @param value 1:connected 2:disConnected
     */
    void networkChanged(int value);

    /**
     * Dump调试信息
     */
    void dump();

    NetworkChannelStatus getNetworkChannelStatus();
}
