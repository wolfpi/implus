package com.baidu.imc.listener;

import com.baidu.imc.type.ClientConnectStatus;
import com.baidu.imc.type.UserStatus;

/**
 *
 * <b>客户端状态监听器</b>
 * <p>
 * 当客户端链接状态发生变化时，通过该监听器通知应用
 * </p>
 * 
 * <table>
 *   <tr>
 *     <th>ErrorCode</th>
 *     <th>ErrorMessage</th>
 *     <th>说明</th>
 *   </tr>
 *   <tr>
 *     <td>100001</td>
 *     <td>Illegal App Key</td>
 *     <td>AppKey 非法。</td>
 *   </tr>
 *   <tr>
 *     <td>100002</td>
 *     <td>Incorrect User Token</td>
 *     <td>用户Token错误。</td>
 *   </tr>
 *   <tr>
 *     <td>100003</td>
 *     <td>Another Client Logined</td>
 *     <td>另外一个客户端登陆，当前客户端被登出。</td>
 *   </tr>
 * </table>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface ClientStatusListener {
	
	/**
	 *
	 * <b>当链接建立时</b>
	 * <p>
	 * 返回当前链接设备的设备ID，应用应将该设备ID上报应用服务器以便向该设备发送推送。
	 * </p>
	 *
	 * @since 1.0
	 *
	 * @param deviceID 当前设备ID
	 */
	public void onConnect(String deviceID);
	
	/**
	 *
	 * <b>连接错误</b>
	 * <p>
	 * 当connect发生错误时回调该函数
	 * </p>
	 *
	 * @since 1.0
	 *
	 * @param code 错误码
	 * @param errMessage  错误信息
	 */
	public void onConnectError(int code,String errMessage);
	
	/**
	 *
	 * <b>登录错误</b>
	 * <p>
	 * 当login出错时回调该函数
	 * </p>
	 *
	 * @since 
	 *
	 * @param code 错误码
	 * @param errMessage  错误信息
	 */
	public void onLoginError(int code , String errMessage);
	
	/**
	 *
	 * <b>客户端连接状态发生变更</b>
	 * <p>
	 * 当客户端链接状态发生变更时回调
	 * </p>
	 *
	 * @since 1.0
	 *
	 * @param status
	 */
	public void onClientConnectStatusChanged(ClientConnectStatus status);
	
	/**
	 *
	 * <b>用户登录状态发生变更</b>
	 * <p>
	 * 当用户登录状态发生变更时回调
	 * </p>
	 *
	 * @since 1.0
	 *
	 * @param status
	 */
	public void onUserStatusChanged(UserStatus status);
}
