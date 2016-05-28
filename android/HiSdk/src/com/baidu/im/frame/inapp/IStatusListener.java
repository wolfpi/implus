package com.baidu.im.frame.inapp;

import com.baidu.im.frame.NetworkChannel.NetworkChannelStatus;

public interface IStatusListener {
	
	public void  statusChange(NetworkChannelStatus networkChannelStatus);

}
