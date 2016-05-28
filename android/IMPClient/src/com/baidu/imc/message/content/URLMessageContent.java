package com.baidu.imc.message.content;

public interface URLMessageContent extends IMURLMessageContent {
	
	/**
	 * 
	 * <b>设置文本内容</b>
	 * 
	 * @since 1.0
	 */
	public void setText(String text);
	
	/**
	 * 
	 * <b>设置URL</b>
	 * 
	 * @since 1.0
	 */
	public void setURL(String url);
}
