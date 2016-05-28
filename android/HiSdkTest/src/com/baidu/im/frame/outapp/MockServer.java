package com.baidu.im.frame.outapp;

import com.baidu.im.frame.NetworkChannel;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.outapp.OutAppApplication;

public class MockServer {
	private NetworkChannel oldNetworkChannel;
	private NetworkChannel newNetworkChannel;

	/**
	 * @return the newNetworkChannel
	 */
	public NetworkChannel getNewNetworkChannel() {
		return newNetworkChannel;
	}

	/**
	 * @param newNetworkChannel
	 *            the newNetworkChannel to set
	 */
	public void setNewNetworkChannel(NetworkChannel newNetworkChannel) {
		this.newNetworkChannel = newNetworkChannel;
		OutAppApplication.getInstance().getNetworkLayer()
				.setNetworkChannel(newNetworkChannel);
	}


	public void sendDownPakcet(DownPacket downPacket) {
		newNetworkChannel.receive(downPacket);
	}

	public synchronized void startMockServer() {
		if (oldNetworkChannel == null) {
			this.oldNetworkChannel = OutAppApplication.getInstance()
					.getNetworkLayer().getNetworkChannel();
			OutAppApplication.getInstance().getNetworkLayer()
					.setNetworkChannel(newNetworkChannel);
		}
	}

	public void stopMockServer() {
		if (oldNetworkChannel != null) {
			OutAppApplication.getInstance().getNetworkLayer()
					.setNetworkChannel(oldNetworkChannel);
			oldNetworkChannel = null;
		}
	}
}
