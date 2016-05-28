package com.baidu.im.testutil;

import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.baidu.im.outapp.network.ProtocolConverter;

public class MockObj {
	
	ProtocolConverter.ServiceNameEnum serviceName;
	
	ProtocolConverter.MethodNameEnum methodName;
	
	int seq;
	
	/**
	 * 默认值0
	 */
	BizCodeProcessUtil.BizCode bizCode = BizCodeProcessUtil.BizCode.SESSION_SUCCESS;
	
	/**
	 * 默认值200
	 */
	BizCodeProcessUtil.ChannelCode channelCode = BizCodeProcessUtil.ChannelCode.CHANNEL_SUCCESS;

	/**
	 * 是否是空的下行包
	 */
	boolean isNullDownPacket = false;
	
	/**
	 * 是否回包 mock timeout的case
	 * 默认false 都是有回包
	 */
	boolean noResponsePacketFlag = false;
	
	/**
	 * @return the serviceName
	 */
	public ProtocolConverter.ServiceNameEnum getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(ProtocolConverter.ServiceNameEnum serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the methodName
	 */
	public ProtocolConverter.MethodNameEnum getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(ProtocolConverter.MethodNameEnum methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * @return the bizCode
	 */
	public BizCodeProcessUtil.BizCode getBizCode() {
		return bizCode;
	}

	/**
	 * @param bizCode the bizCode to set
	 */
	public void setBizCode(BizCodeProcessUtil.BizCode bizCode) {
		this.bizCode = bizCode;
	}

	/**
	 * @return the channelCode
	 */
	public BizCodeProcessUtil.ChannelCode getChannelCode() {
		return channelCode;
	}

	/**
	 * @param channelCode the channelCode to set
	 */
	public void setChannelCode(BizCodeProcessUtil.ChannelCode channelCode) {
		this.channelCode = channelCode;
	}

	/**
	 * @return the isNullDownPacket
	 */
	public boolean isNullDownPacket() {
		return isNullDownPacket;
	}

	/**
	 * @param isNullDownPacket the isNullDownPacket to set
	 */
	public void setNullDownPacket(boolean isNullDownPacket) {
		this.isNullDownPacket = isNullDownPacket;
	}

	/**
	 * @return the noResponsePacketFlag
	 */
	public boolean isNoResponsePacketFlag() {
		return noResponsePacketFlag;
	}

	/**
	 * @param noResponsePacketFlag the noResponsePacketFlag to set
	 */
	public void setNoResponsePacketFlag(boolean noResponsePacketFlag) {
		this.noResponsePacketFlag = noResponsePacketFlag;
	}

}
