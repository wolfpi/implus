package com.baidu.imc.message;

/**
 *
 * <b>临时消息</b>
 * <p>
 * 该类消息不会记录到消息记录，只会发送给在线的端，如果接收方离线则发送失败
 * </p>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface TransientMessage {
	
	/**
	 * <b>消息内容[必须]</b>
	 * 
	 * <p>需要使用字符串格式，最大长度5k</p>
	 * 
	 * @since 1.0
	 */
	public void setContent(String content);
}
