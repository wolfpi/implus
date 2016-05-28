package com.baidu.imc.client;

import com.baidu.imc.message.CustomMessage;
import com.baidu.imc.message.FileMessage;
import com.baidu.imc.message.ImageMessage;
import com.baidu.imc.message.TextMessage;
import com.baidu.imc.message.TransientMessage;
import com.baidu.imc.message.VoiceMessage;
import com.baidu.imc.message.content.FileMessageContent;
import com.baidu.imc.message.content.ImageMessageContent;
import com.baidu.imc.message.content.TextMessageContent;
import com.baidu.imc.message.content.URLMessageContent;
import com.baidu.imc.message.content.VoiceMessageContent;


/**
*
* <b>消息对象创建对象</b>
* <p>
* 用户创建发送用的各类相对对象。
* </p>
*
* @since 1.0
* @author WuBin
*
*/
public interface MessageHelper {
	
	/**
	 * <b>创建一个新的文本消息</b>
	 * 
	 * @since 1.0
	 * @return
	 */
	public TextMessage newTextMessage();
	
	/**
	 * <b>创建一个新的文本内容</b>
	 * @param text
	 * 
	 * @since 1.0
	 * @return
	 */
	public TextMessageContent newTextMessageContent(String text);
	
	/**
	 * <b>创建一个新的URL内容</b>
	 * 
	 * @param url   超链接，不可为null
	 * @param text  文本，可以为null
	 * 
	 * @since 1.0
	 * @return
	 */
	public URLMessageContent newURLMessageContent(String url,String text);
	
	/**
	 * <b>创建一个新的文件消息</b>
	 * 
	 * 
	 * @since 1.0
	 * @return
	 */
	public FileMessage newFileMessage();
	
	/**
	 * 
	 * @param filePath   文件本地资源库路径[必须]
	 * @return
	 */
	public FileMessageContent newFileMessageContent(String filePath);
	
	/**
	 * <b>创建一个新的图片消息</b>
	 * 
	 * @since 1.0
	 * @return
	 */
	public ImageMessage newImageMessage();
	
	/**
	 * <b>创建一个新的图片消息内容</b>
	 * 
	 * @since 1.0
	 * @return
	 */
	public ImageMessageContent newImageMessageContent();
	
	/**
	 * <b>创建一个新的语音消息</b>
	 * 
	 * @since 1.0
	 * @return
	 */
	public VoiceMessage newVoiceMessage();
	
	/**
	 * <b>创建一个新的语音消息内容</b>
	 * 
	 * @since 1.0
	 * @return
	 */
	public VoiceMessageContent newVoiceMessageContent();
	
	/**
	 * <b>创建一个新的临时消息</b>
	 * 
	 * @since 1.0
	 * @return
	 */
	public TransientMessage newTransientMessage();
	
	/**
     * <b>创建一个新的自定义消息</b>
     * 
     * @since 1.0
     * @return
     */
	public CustomMessage newCustomMessage();
}
