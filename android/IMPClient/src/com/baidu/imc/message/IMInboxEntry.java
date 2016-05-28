package com.baidu.imc.message;

import com.baidu.imc.type.AddresseeType;


/**
 *
 * <b>收件箱条目</b>
 * <p>
 *   最近对话等都会产生一个收件箱条目
 * </p>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface IMInboxEntry {
	
	/**
	 * <b>收件箱条目ID</b>
	 * 
	 * @since 1.0
	 * 
	 * @return ID
	 */
	public String getID();
	
	/**
	 * <b>收件人类型</b>
	 * 
	 * @since 1.1
	 * 
	 * @return AddresseeType
	 */
	public AddresseeType getAddresseeType();
	
	/**
	 * <b>收件人ID</b>
	 * 
	 * @since 1.1
	 * 
	 * @return 收件人ID
	 */
	public String getAddresseeID();
	
	/**
	 * <b>回话的最后一条消息</b>
	 * 
	 * @since 1.0
	 * 
	 * @return 最后一条消息
	 */
	public IMMessage getLastMessage();
	
	/**
	 * <b>未读计数</b>
	 * 
	 * @since 1.0
	 * 
	 * @return 未读计数
	 */
	public int getUnreadCount();
}
