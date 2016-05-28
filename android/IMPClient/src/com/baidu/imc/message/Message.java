package com.baidu.imc.message;


/**
*
* <b>基本发送消息</b>
* <p>
* 包含文本字段的基本消息
* </p>
*
* @since 1.0
* @author WuBin
*
*/
public interface Message {
	
	/**
	 * <b>发件人名称[必须]</b>
	 * <p>用于通知中发件人名称的展示，以及当端上暂未同步到当前用户信息时消息的发件人名称展示</p>
	 * 
	 * @since 1.0
	 */
	public void setAddresserName(String addresserName);
	
	/**
	 * 
	 * <b>文本内容[可选]</b>
	 * <p>消息文本，将用于离线Push的内容，如果不填，将按照具体消息类型发送给用户默认的推送文字。</p>
	 * 
	 * @since 1.0
	 */
	public void setNotificationText(String text);
	
	/**
	 * 
	 * <b>兼容性消息文本内容[可选]</b>
	 * <p>当旧版本不能识别该消息时，可使用该文本用于用户显示。</p>
	 * 
	 * @since 1.0
	 */
	public void setCompatibleText(String text);
	
	/**
	 * <b>附加属性[可选]</b>
	 * 
	 * <p>任何消息开发者均可设置附加信息，SDK原封传递该信息</p>
	 * 
	 * @since 1.0
	 */
	public void setExtra(String extra);

}
