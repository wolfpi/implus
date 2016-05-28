package com.baidu.imc.message.content;

/**
*
* <b>图片内容[发送]</b>
*
* @since 1.0
* @author WuBin
*
*/
public interface ImageMessageContent extends IMImageMessageContent,FileMessageContent{
	/**
	 * <b>设置图片宽度</b>
	 * 
	 * @param width
	 * 
	 * @since 1.0
	 */
	public void setWidth(int width);

	/**
	 * <b>设置图片高度</b>
	 * @param height
	 * 
	 * @since 1.0
	 */
	public void setHeight(int height);
}
