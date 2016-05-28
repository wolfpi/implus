package com.baidu.imc.client;

import com.baidu.imc.callback.PageableResultCallback;
import com.baidu.imc.exception.IllegalParameterException;
import com.baidu.imc.listener.IMConversationListener;
import com.baidu.imc.message.Message;
import com.baidu.imc.message.IMMessage;

public interface IMConversation extends IMChatHistory{
	
	/**
	 *
	 * <b>开始接收消息</b>
	 *
	 * @since 1.0
	 *
	 */
	public void start();
	
	/**
	 *
	 * <b>停止接收消息</b>
	 * <p>对话窗口注销后，应调用该方法</p>
	 *
	 * @since 1.0
	 *
	 */
	public void close();
	
	
	/**
	 *
	 * <b>激活当前对话</b>
	 * <p>激活后，接收到的新消息系统自动标记已读</p>
	 *
	 * @since 1.0
	 *
	 */
	public void active();
	
	/**
	 *
	 * <b>取消激活当前对话</b>
	 * <p>取消激活后，该会话收到的新消息不会自动标记已读</p>
	 *
	 * @since 1.0
	 *
	 */
	public void deactive();
	
	
	/**
	 *
	 * <b>设置对话监听器</b>
	 * 
	 * <p>应在调用start方法前设置监听器</p>
	 *
	 * @since 1.0
	 *
	 * @param listener 对话监听器
	 */
	public void setIMConversationListener(IMConversationListener listener);
	
	/**
	 *
	 * <b>获取指定消息之前的指定条目的消息</b>
	 * <p>
	 * beforeMessageID给0，表示返回最近的num个消息，返回值中的total值等于返回的总条目，排序按发送顺序从新到旧
	 * 本方法会去服务器同步本地缺失的消息，可设定超时时间，最大值不超过30s，设置为0则只读取本地数据
	 * </p>
	 * 无论是本方法还是getMessageList方法，对返回的消息，统一进行已读标记处理
	 *
	 * @since 1.0
	 *
	 * @param beforeMessageID       起始消息ID
	 * @param num                           个数
	 * @param timeout                      设置超时时间，单位秒
	 * @param callback                      结果回调
	 */
	public void getMessageList(long beforeMessageID,int num,int timeout,PageableResultCallback<IMMessage> callback);
	
	/**
	 *
	 * <b>发送消息</b>
	 * 
	 * <p>
	 * 在非LOGOUT状态下均可以发送消息
	 * </p>
	 * <b>消息重发逻辑</b>
	 * 当消息发出，并在ONLINE状态时，进行消息发送，发送时，先发送附件，成功后，发送消息，
	 * 发送队列由附件队列和消息队列两个队列组成，总按从旧到新的顺序发送，每条消息/附件的有效发送时间由配置值决定(以发出时间计算)，
	 * 超出的记为发送失败。
	 *
	 * @since 1.0
	 *
	 * @param message 具体消息
	 * @throws IllegalParameterException 消息参数不全时抛出
	 * 
	 */
	public void sendMessage(Message message) throws IllegalParameterException;
}
