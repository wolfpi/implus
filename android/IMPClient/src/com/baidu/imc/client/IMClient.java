package com.baidu.imc.client;

import com.baidu.imc.callback.ResultCallback;
import com.baidu.imc.message.Message;
import com.baidu.imc.message.TransientMessage;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.NotificationType;

/**
 * 
 * <b>IM+端控制类</b>
 * <p>
 * 兼容PushClient关于推送和通知的所有操作，并增加IM相关功能支持
 * </p>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface IMClient extends PushClient{
	
	/**
	 *
	 * <b>获得收件箱对象</b>
	 *
	 * @since 1.0
	 *
	 * @return IMInbox
	 */
	public IMInbox getIMInbox();
	
	/**
	 *
	 * <b>获得聊天对象的历史消息管理器</b>
	 *
	 * @since 1.0
	 *
	 * @param addresseeType   聊天对象类型
	 * @param addresseeID       聊天对象ID
	 * @return IMChat 历史聊天消息管理器
	 */
	public IMChatHistory getIMChatHistory(AddresseeType addresseeType,String addresseeID);
	
	/**
	 *
	 * <b>打开对话对象</b>
	 * <p>
	 * 可不处理 addresseeType和addresseeID 产生多个对象的情况，但消息监听应都发送到。
	 * </p>
	 *
	 * @since 1.0
	 *
	 * @param addresseeType
	 * @param addresseeID
	 * @return 
	 */
	public IMConversation openIMConversation(AddresseeType addresseeType,String addresseeID);
	
	/**
	 *
	 * <b>发送实时数据</b>
	 *
	 * @since  1.0
	 *
	 * @param addresseeType  收件人类型
	 * @param addresseeID      收件人ID
	 * @param dataMessage    数据消息
	 * @param callback            发送状态接收
	 */
	public void sendTransientMessage(AddresseeType addresseeType,String addresseeID,TransientMessage transientMessage,ResultCallback<Boolean> callback);
	
	/**
	 *
	 * <b>替换默认本地资源管理器</b>
	 * 
	 * <p>应在connect/login之前设置</p>
	 *
	 * @since 1.0
	 *
	 * @param localResourceManager 开发者自己的本地资源管理器
	 */
	public void setLocalResourceManager(LocalResourceManager localResourceManager);
	
	/**
	 * 
	 *
	 * <b>获取本地资源管理器</b>
	 *
	 * @since 1.0
	 *
	 * @return 当前SDK使用的资源管理器
	 */
	public LocalResourceManager getLocalResourceManager();
	
	/**
	 *
	 * <b>替换默认的远程资源管理器</b>
	 * 
	 * <p>应在connect/login之前设置</p>
	 *
	 * @since 1.0
	 *
	 * @param remoteResourceManager 开发者自己的远程资源管理器
	 */
	public void setRemoteResourceManager(RemoteResourceManager remoteResourceManager);
	
	/**
	 * <b>获得消息对象助手类</b>
	 * 
	 * @since 1.0
	 * 
	 * @return
	 */
	public MessageHelper getMessageHelper();

	/**
	 * TODO ZX Doc
	 * @param addresseeType
	 * @param addresseeID
	 * @param transientMessage
	 * @param setInbox
     */
	public void insertLocalMessage(AddresseeType addresseeType, String addresseeID, Message transientMessage,
								   boolean setInbox);

	/**
	 * TODO ZX Doc
	 * @param addresseeType
	 * @param addresseeID
     * @return
     */
	NotificationType getNotificationSetting(AddresseeType addresseeType, String addresseeID);

	void setNotificationSetting(AddresseeType addresseeType, String addresseeID, NotificationType notificationType,
																				   ResultCallback result);
}
