package com.baidu.imc.client;

import java.util.List;

import com.baidu.imc.listener.IMInboxListener;
import com.baidu.imc.message.IMInboxEntry;
import com.baidu.imc.type.AddresseeType;


/**
 *
 * <b>会话管理器</b>
 * <p>
 * 管理会话相关操作
 * </p>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface IMInbox {
	/**
	 *
	 * <b>设置IM会话监听器对象</b>
	 * <p>
	 * 应在调用connect/login方法前设置该对象，否则可能出现SDK漏掉消息的情况，当有新消息到达时，将更新对应的回话条目
	 * </p>
	 *
	 * @since 1.0
	 *
	 * @param listener IM消息监听器
	 */
	public void setIMInboxListener(IMInboxListener listener);
	
	/**
	 *
	 * <b>获得最近会话条目列表</b>
	 * <p>
	 *  最多获取最近的100个会话条目
	 * </p>
	 *
	 * @since 1.0
	 *
	 * @return 会话条目列表
	 */
	public List<IMInboxEntry> getIMInboxEntryList();
	
	/**
	 *
	 * <b>删除指定会话条目</b>
	 *
	 * @since 1.0
	 *
	 * @param ID 会话条目ID
	 */
	public void deleteIMInboxEntry(String ID);
	
	/**
	 * <b>删除指定会话条目</b>
	 * 
	 * @since 1.1
	 * @param addresseeType  对方消息类型
	 * @param addresseeID     对方消息ID
	 */
	public void deleteIMInboxEntry(AddresseeType addresseeType, String addresseeID);
	
	/**
	 *
	 * <b>清空会话条目列表</b>
	 *
	 * @since 1.0
	 *
	 */
	public void deleteAllIMInboxEntry();
}
