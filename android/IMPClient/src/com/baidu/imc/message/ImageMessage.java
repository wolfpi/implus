package com.baidu.imc.message;

import com.baidu.imc.message.content.ImageMessageContent;

/**
*
* <b>图片消息</b>
* <p>当前类留作以后扩展使用</p>
* @since 1.0
* @author WuBin
*
*/
public interface ImageMessage extends Message {
	
	/**
	 * <b>设置消息图片[必须]</b>
	 * 
	 * @since 1.0
	 * 
	 * @param image 图片内容
	 */
	public void setImage(ImageMessageContent image);
	
	/**
	 * <b>设置缩略图文件[可选]</b>
	 * 
	 * @since 1.0
	 * 
	 * @param image 图片内容
	 */
	public void setThumbnailImage(ImageMessageContent image);
}
