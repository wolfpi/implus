package com.baidu.im.frame;

import com.baidu.im.frame.NetworkChannel.NetworkChannelStatus;

public interface INetworkChannelListener {

    void onChanged(NetworkChannelStatus networkChannelStatus);
}
