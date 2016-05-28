package com.baidu.imc.listener;

import java.util.List;

import com.baidu.imc.message.IMInboxEntry;
import com.baidu.imc.message.IMTransientMessage;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.NotificationType;

/**
 *
 * <b>会话条目监听器</b>
 * <p>
 * 当最近会话发生变化时，将回调该监听器
 * </p>
 *
 * @since 
 * @author WuBin
 *
 */
public interface IMInboxListener {
	
	/**
	 *
	 * <b>回话条目更新通知</b>
	 *
	 * @since 1.0
	 *
	 * @param list 变更的会话条目列表
	 */
	public void onIMInboxEntryChanged(List<IMInboxEntry> list);
	
	/**
	 *
	 * <b>当收到新的实时数据消息</b>
	 *
	 * @since 1.0
	 *
	 * @param dataMessage
	 */
	public void onNewIMTransientMessageReceived(IMTransientMessage transientMessage);

	/**
	 *
	 * TODO ZX Doc
	 *
	 * @since 1.0
	 */
	public void onNotificationTypeSetting(AddresseeType addresseeType, String addresseeID, NotificationType
			notificationType);
}
