package com.baidu.imc.message.content;

/**
*
* <b>语音内容[接收]</b>
* 
* @since 1.0
* @author WuBin
*
*/
public interface IMVoiceMessageContent extends IMFileMessageContent {
	/**
	 * <b>语音时长[毫秒]</b>
	 * @since 1.0
	 * 
	 * @return 语音时长
	 */
	public int getDuration();
}
