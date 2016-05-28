package com.baidu.imc.message.content;

/**
*
* <b>纯文本内容[接收]</b>
* 
* @since 1.0
* @author WuBin
*
*/
public interface IMTextMessageContent extends IMMessageContent {
	
	/**
	 * <b>获得文本内容</b>
	 * 
	 * @since 1.0
	 * @return
	 */
	public String getText();
}
