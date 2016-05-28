package com.baidu.imc.message;

import java.util.List;

import com.baidu.imc.message.content.IMMessageContent;


/**
 *
 * <b>自定义消息</b>
 * 
 * <p>文本消息由被允许的消息内容构成
 * </p>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface IMCustomMessage extends IMMessage{
	
	/**
	 * <b>获得指定Key的消息内容列表</b>
	 * 
	 * @param key
	 * @return
	 */
	public List<IMMessageContent> getMessageContentList(String key);
	
	
	/**
	 * <b>获得指定Key的消息内容列表中的第一个元素</b>
	 * 
	 * @param key
	 * @return
	 */
	public IMMessageContent getMessageContent(String key);
}
