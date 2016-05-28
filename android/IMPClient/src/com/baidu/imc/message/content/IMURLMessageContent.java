package com.baidu.imc.message.content;


/**
*
* <b>URL内容[接收]</b>
* 
* @since 1.0
* @author WuBin
*
*/
public interface IMURLMessageContent extends IMMessageContent {
	/**
	 * <b>获得URL文本</b>
	 * 
	 * @since 1.0
	 * @return
	 */
	public String getText();
	
	/**
	 * <b>获得URL</b>
	 * 
	 * @since 1.0
	 * @return
	 */
	public String getURL();
}
