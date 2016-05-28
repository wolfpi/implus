/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.outapp.network;

import java.io.IOException;

import com.baidu.im.frame.INetworkChannelListener;
import com.baidu.im.frame.NetworkChannel;
import com.baidu.im.frame.Packet;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.mock.MockNetworkFramework;
import com.baidu.im.outapp.OutAppApplication;
import com.baidu.im.testutil.MockObj;

/**
 * For local test, mock server response.
 * 
 * @author zhaowei10
 * 
 */
public class LocalMockNetworkWithExceptionChannel implements NetworkChannel {

    public LocalMockNetworkWithExceptionChannel(MockObj mockPara) {
        this.mockPara = mockPara;
        //LogUtil.fProtocol("开始使用 local mock withexception channel....");
    }

    private int seq = 0;

    private MockObj mockPara;

    public int send(Packet packet) {

        return seq++;
    }

    class TestThread extends Thread {
        UpPacket upPacket;

        public TestThread(UpPacket upPacket) {
            this.upPacket = upPacket;
        }

        @Override
        public void run() {
            MockNetworkFramework.getInstance().receiveDownPacketByUpPacket(upPacket, mockPara);
        }
    }

    public void open() {
        // TODO Auto-generated method stub

    }

    @Override
    public void send(UpPacket upPacket) throws IOException {
        new TestThread(upPacket).start();
    }

    @Override
    public int getSeq() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void receive(DownPacket downPacket) {
        if (downPacket == null) {
           // LogUtil.fProtocol("received a null downPacket.");
            return;
        }
        OutAppApplication.getInstance().getNetworkLayer().onReceive(downPacket);
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public void heartbeat() {
        // TODO Auto-generated method stub

    }

    @Override
    public void networkChanged(int value) {
        // TODO Auto-generated method stub

    }

    @Override
    public void dump() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNetworkChannelListener(INetworkChannelListener networkChannelListener) {
        // TODO Auto-generated method stub

    }

    @Override
    public NetworkChannelStatus getNetworkChannelStatus() {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public void open(String[] ips, int[] ports) {
		// TODO Auto-generated method stub
		
	}

}
