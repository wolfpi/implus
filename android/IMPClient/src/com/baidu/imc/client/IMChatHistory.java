package com.baidu.imc.client;

import com.baidu.imc.callback.PageableResult;
import com.baidu.imc.message.IMMessage;

/**
 *
 * <b>基本聊天接口</b>
 * <p>
 * 聊天接口对象用于描述一个聊天过程的记录，提供聊天过程记录的查询操作
 * </p>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface IMChatHistory {
	
	/**
	 *
	 * <b>获取本地历史消息列表</b>
	 * <p>
	 * 返回值为按时间从旧到新，start从0开始，当start为小于0的值时，表示获取最后limit条数
	 * </p>
	 *
	 * @since 1.0
	 *
	 * @param pageNum 页码
	 * @param pageSize  每页个数
	 * @return
	 */
	public PageableResult<IMMessage> getMessageList(int start, int limit);
	
	/**
	 *
	 * <b>删除所有消息</b>
	 * 
	 * <p>删除该用户的所有消息，仅本地删除，不删除同步记录，对删除的消息不再进行同步。</p>
	 *
	 * @since 1.0
	 *
	 */
	public void deleteAllMessage();
	
	/**
	 * 删除一个指定消息
	 * 
	 * @param messageID 消息ID
	 */
	public void deleteMessage(long messageID);
	
}
