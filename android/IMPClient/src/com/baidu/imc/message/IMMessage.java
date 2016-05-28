package com.baidu.imc.message;

import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.IMMessageStatus;


/**
 *
 * <b>即时消息</b>
 * <p>
 * 定义一个即时消息
 * </p>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface IMMessage {
	/**
	 * <b>收件人类型[必须]</b>
	 * 
	 * @since 1.0
	 * 
	 * @return AddresseeType
	 */
	public AddresseeType getAddresseeType();
	
	/**
	 * <b>收件人ID[必须]</b>
	 * 
	 * @since 1.0
	 * 
	 * @return 收件人ID
	 */
	public String getAddresseeID();
	
	/**
	 * <b>发件人ID[必须]</b>
	 * 
	 * @since 1.0
	 * 
	 * @return 发件人ID
	 */
	public String getAddresserID();
	
	/**
	 * <b>发件人名称[必须]</b>
	 * <p>用于通知中发件人名称的展示，以及当端上暂未同步到当前用户信息时消息的发件人名称展示</p>
	 * 
	 * @since 1.0
	 * 
	 * @return 发件人名称
	 */
	public String getAddresserName();
	
	/**
	 * <b>发送时间[必须]</b>
	 * <p>从 1970 年 1 月 1 日 0 点 0 分 0 秒开始到现在的毫秒数</p>
	 * 
	 * @since 1.0
	 * 
	 * @return 发送时间
	 */
	public long getSendTime();
	
	/**
	 * <b>消息ID</b>
	 * <p>使用本地数据库ID，保证消息ID不变</p>
	 * 
	 * @since 1.0
	 * 
	 * @return 消息ID
	 */
	public long getMessageID();
	
	/**
	 * <b>当前消息状态</b>
	 * 
	 * @since 1.0
	 * 
	 * @return 当前消息状态
	 */
	public IMMessageStatus getStatus();
	
	/**
	 * <b>消息兼容性显示文本内容</b>
	 * 
	 * @since 1.0
	 * @return 
	 */
	public String getCompatibleText();
	
	/**
	 * <b>附加属性[可选]</b>
	 * 
	 * <p>任何消息开发者均可设置附加信息，SDK原封传递该信息</p>
	 * 
	 * @since 1.0
	 * 
	 * @return 附加属性
	 */
	public String getExtra();

}
