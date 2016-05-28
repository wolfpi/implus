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

/**
 * For local test, mock server response.
 * 
 * @author zhaowei10
 * 
 */
public class LocalMockNetworkChannel implements NetworkChannel {

    public LocalMockNetworkChannel() {
        //LogUtil.fProtocol("开始使用 local mock channel....");
    }

    private int seq = 0;

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

            // try {
            // Thread.sleep(new Random().nextInt(99));
            // } catch (InterruptedException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }

            MockNetworkFramework.getInstance().receiveDownPacketByUpPacket(upPacket);
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
            //LogUtil.fProtocol("received a null downPacket.");
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
        return NetworkChannelStatus.Connected;
    }

	@Override
	public void open(String[] ips, int[] ports) {
		// TODO Auto-generated method stub
		
	}

}
