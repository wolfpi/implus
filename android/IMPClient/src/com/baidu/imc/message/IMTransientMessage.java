package com.baidu.imc.message;

import com.baidu.imc.type.AddresseeType;
/**
*
* <b>临时消息</b>
* <p>临时消息只能使用专用消息发送接口发送，不可使用Chatcontext会话对象中的消息发送函数发送</p>
*
* @since 1.0
* @author WuBin
*
*/
public interface IMTransientMessage {
	/**
	 * <b>收件人类型</b>
	 * 
	 * @since 1.0
	 * 
	 * @return AddresseeType
	 */
	public AddresseeType getAddresseeType();
	
	/**
	 * <b>收件人ID</b>
	 * 
	 * @since 1.0
	 * 
	 * @return 收件人ID
	 */
	public String getAddresseeID();
	
	/**
	 * <b>发件人ID</b>
	 * 
	 * @since 1.0
	 * 
	 * @return 发件人ID
	 */
	public String getAddresserID();
	
	/**
	 * <b>消息内容</b>
	 * 
	 * @since 1.0
	 * @return 消息内容
	 */
	public String getContent();
}
