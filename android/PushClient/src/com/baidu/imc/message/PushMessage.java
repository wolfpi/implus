package com.baidu.imc.message;

/**
 *
 * <b>推送消息</b>
 * <p>
 * 当SDK收到推送消息时将接收到该对象
 * </p>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface PushMessage {
	
	/**
	 * <b>自定义消息</b>
	 * <p>推送消息中的message字段，如果没有为null</p>
	 * 
	 * @return 自定义消息
	 * 
	 * @since 1.0
	 */
	public String getMessage();
	
	/**
	 * <b>通知</b>
	 * <p>
	 * 推送消息中的Notification设置，不同端可根据情况增加额外字段，仅在应用在线时，会通过SDK收到通知，
	 * 其他情况通知将通过离线方式发出，当用户联网时通知将出现在手机的通知栏，仅收到message时该字段为null
	 * </p>
	 * 
	 * @return 通知
	 * 
	 * @since 1.0
	 */
	public Notification getNotification();
	
	
	/**
	 *
	 * <b>通知对象</b>
	 *
	 * @since 1.0
	 * @author WuBin
	 *
	 */
	public static interface Notification{
		/**
		 * <b>通知内容</b>
		 * 
		 * @since 1.0
		 * 
		 * @return 得到通知内容
		 */
		public String getAlert();
		
		
		/**
		 * <b>通知标题[仅安卓]</b>
		 * 
		 * @since 1.0
		 * 
		 * @return 标题
		 */
		public String getTitle();
		
		
		/**
		 * <b>通知的扩展字段</b>
		 * <p>
		 * JSON字符串格式
		 * </p>
		 * 
		 * @since 1.0
		 * 
		 * @return 扩展字段
		 */
		public String getExtras();
	}
}
