package com.baidu.imc.message.content;


/**
*
* <b>纯文本内容[发送]</b>
* 
* @since 1.0
* @author WuBin
*
*/
public interface TextMessageContent extends IMTextMessageContent{
	
	/**
	 * 
	 * <b>设置文本内容</b>
	 * 
	 * @param text    文本内容
	 * 
	 * @since 1.0
	 */
	public void setText(String text);
}
