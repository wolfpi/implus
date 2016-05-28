package com.baidu.imc.listener;

import com.baidu.imc.message.PushMessage;

/**
 *
 * <b>消息监听器接口</b>
 * <p>
 * 实现该接口以获得推送消息
 * </p>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface PushMessageListener {
	
	/**
	 * 
	 *
	 * <b>收到推送消息</b>
	 * <p>
	 * 当SDK收到推送消息时，将回调该函数，当收到在线通知时，应用可返回true表示自己处理该通知，
	 * 返回false交由IM+SDK处理，显示标准通知，对于iOS当应用处于前端时，IM+SDK无法显示标准通知，
	 * 如果应用没有设置推送监听器，则IM+SDK总会尝试将收到通知显示在通知栏
	 * </p>
	 *
	 * @since 1.0
	 *
	 * @param pushMessage 推送消息
	 * @return 是否自己处理通知消息
	 */
	public boolean onPushMessageReceived(PushMessage pushMessage);
}
