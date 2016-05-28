package com.baidu.im.frame;

import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;

/**
 * 数据包监听接口。
 * 
 * @author zhaowei10
 * 
 */
public interface SequenceListener {

	/**
	 * 收到并处理来自网络的包，
	 * 
	 * @param data
	 * @return true if you don't want other listener to receive the packet,
	 *         otherwise false.
	 */
	boolean onReceive(DownPacket downPacket);

	/**
	 * 获取监听包的seq
	 * 
	 * @return
	 */
	int getListenSeq();

}
