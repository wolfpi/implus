package com.baidu.imc.listener;

import java.util.List;

import com.baidu.imc.message.IMMessage;

/**
 *
 * <b>对话消息监听器</b>
 * <p>
 * 监听会话消息
 * </p>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface IMConversationListener {

    /**
     *
     * <b>消息状态变更通知</b>
     * <p>
     * 目前可能发生变化的字段：
     * </p>
     * <ul>
     * <li>status ： 消息状态</li>
     * </ul>
     *
     * @since 1.0
     *
     * @param newMessage 变化后的消息
     * @param changes 变化内容
     */
    public void onMessageChanged(IMMessage newMessage, List<String> changes);
    // public void onMessageChanged(IMMessage newMessage, Map<IMMessageChange, Object> changes);

    /**
     * 
     *
     * <b>收到新消息</b>
     *
     * @since 1.0
     *
     * @param message 消息内容
     */
    public void onNewMessageReceived(IMMessage message);

    /**
     * <b>刷新当前会话消息通知</b>
     * <p>
     * 当激活的会话窗口收到大量离线消息时，一条一条返回将不太现实，因此将会触发重新读取事件，此时UI应重新调用 getMessageList方法重新获取最新消息，而后通过向上拉取动作获取更多离线消息
     * </p>
     * 
     * @since 1.1
     */
    // public void onNeedReload();
}
