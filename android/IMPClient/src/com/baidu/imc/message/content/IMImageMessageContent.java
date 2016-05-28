package com.baidu.imc.message.content;

/**
*
* <b>图片内容[接收]</b>
* 
* @since 1.0
* @author WuBin
*
*/
public interface IMImageMessageContent extends IMFileMessageContent {
	/**
	 * <b>图片宽度</b>
	 * 
	 * @return 图片宽度
	 */
	public int getWidth();
	
	
	/**
	 * <b>图片高度</b>
	 * 
	 * @return 图片高度
	 */
	public int getHeight();
}
