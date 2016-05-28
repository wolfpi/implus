package com.baidu.imc.message;

import com.baidu.imc.message.content.TextMessageContent;
import com.baidu.imc.message.content.URLMessageContent;


/**
*
* <b>发送的文本消息</b>
*
* @since 1.0
* @author WuBin
*
*/
public interface TextMessage extends Message {
	
	/**
	 * <b>添加一段文本</b>
	 * 
	 * @param text 文本内容
	 * 
	 * @since 1.0
	 */
	public void addText(TextMessageContent text);
	
	/**
	 * <b>添加一个超链接</b>
	 * 
	 * 
	 * @param url
	 */
	public void addURL(URLMessageContent url);
	
}
