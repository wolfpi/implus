package com.baidu.imc.message;

import com.baidu.imc.message.content.ImageMessageContent;


/**
 *
 * <b>图片消息</b>
 * 
 * @since 1.0
 * @author WuBin
 *
 */
public interface IMImageMessage extends IMMessage {
	
	/**
	 * <b>获得图片内容</b>
	 * 
	 * @return
	 */
	public ImageMessageContent getImage();
	
	
	/**
	 * <b>获得缩略图内容</b>
	 * 
	 * @return
	 */
	public ImageMessageContent getThumbnailImage();
}
