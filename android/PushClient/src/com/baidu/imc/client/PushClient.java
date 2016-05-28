package com.baidu.imc.client;

import com.baidu.imc.listener.ClientStatusListener;
import com.baidu.imc.listener.PushMessageListener;
import com.baidu.imc.type.ClientConnectStatus;
import com.baidu.imc.type.UserStatus;

/**
 *
 * <b>推送客户端控制类</b>
 * 
 * <p>
 * 端状态说明：
 * </p>
 * <b>连接状态</b><br>
 * 应用启动时，连接状态为DISCONNECTED，调用 connect/login 方法后 进入连接保持状态（断线自动重连）状态进入CONNECTING状态，连接成功后进入
 * CONNECTED状态，断网后进入CONNECTING状态，调用 disconnect方法后进入 DISCONNECTED状态<br>
 * <b>用户状态状态</b><br>
 * 应用启动时，如果上次应用退出时用户状态为LOGOUT，则仍为LOGOUT状态，否则为OFFLINE状态（但在login方法被调用前，即使调用了connect方法，也不会自动登录），
 * 在LOGOUT状态调用login进入OFFLINE状态，同时进入登录保持状态（断网重连后自动登录），连接并登录成功后进入ONLINE状态， 离线后进入OFFLINE状态，调用 logout后，进入LOGOUT状态<br>
 * 
 * 实现类提供静态工厂类，和两个方法：init 和 getPushClient
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface PushClient {
    /**
     *
     * <b>客户端状态监听器设置</b>
     * <p>
     * 用于监听客户端状态变更等相关信息，应当在connect/login之前设置
     * </p>
     *
     * @since 1.0
     *
     * @param listener 客户端状态监听器
     */
    public void setClientStatusListener(ClientStatusListener listener);

    /**
     *
     * <b>启动连接</b>
     * <p>
     * 连接一旦启动，将自动进行重连，并开始接收在线消息和离线消息，匿名登录时使用该方法启动连接。
     * </p>
     *
     * @since 1.0
     *
     */
    public void connect();

    /**
     *
     * <b>断开连接</b>
     * <p>
     * 当应用主动断开连接，该客户端将进入离线状态，不在接收任何在线消息
     * </p>
     *
     * @since 1.0
     *
     */
    public void disconnect();

    /**
     *
     * <b>使用用户名登录</b>
     * <p>
     * 可直接调用该方法替代调用connect方法，该方法应自动进行connect如果当前端未连接， login完成后，即将该设备与该用户进行了绑定，同一设备只能绑定一个用户。
     * 当login时的userID与已登录userID不同时，自动调用上个用户的logout，确保同一时间只能有一个用户登录
     * </p>
     *
     * @since 1.0
     *
     * @param userID 用户ID
     * @param userToken 用户Token，使用用户ID从IM+服务器端获取
     */
    public void login(String userID, String userToken);

    /**
     *
     * <b>用户注销</b>
     * <p>
     * 用户注销不等同于disconnect，注销后同样会收到发往指定端的通知
     * </p>
     *
     * @since 1.0
     *
     */
    public void logout();

    /**
     *
     * <b>当前客户端连接状态</b>
     *
     * @since 1.0
     *
     * @return ClientConnectStatus 当前连接状态
     */
    public ClientConnectStatus getCurrentClientConnectStatus();

    /**
     *
     * <b>获取当前登录用户ID</b>
     * <p>
     * 如果状态为未登录，则返回null
     * </p>
     *
     * @since 1.0
     *
     * @return 当前登录用户ID
     */
    public String getCurrentUserID();

    /**
     * 
     *
     * <b>当前端的用户登录状态</b>
     *
     * @since 1.0
     *
     * @return UserStatus 当前用户登录状态
     */
    public UserStatus getCurrentUserStatus();

    /**
     *
     * <b>设置推送消息监听器对象</b>
     * <p>
     * 应在调用connect/login方法前设置该对象，否则可能出现SDK漏掉消息的情况
     * </p>
     *
     * @since 1.0
     *
     * @param listener 推送消息监听器
     */
    public void setPushMessageListener(PushMessageListener listener);

    /**
     *
     * <b>设置接收通知</b>
     * <p>
     * 默认为开启，iOS SDK不实现该方法，应提示用户使用系统设置方式
     * </p>
     *
     * @since 1.0
     *
     */
    public void enableNotification();

    /**
     *
     * <b>禁止接收通知</b>
     * <p>
     * iOS SDK不实现该方法，应提示用户使用系统设置方式
     * </p>
     * <br>
     * 关闭通知后，IM+的SDK将不会处理通知信息，本开关不影响message的推送
     *
     * @since 1.0
     *
     */
    public void disableNotification();

    /**
     *
     * <b>查询是否接收通知消息</b>
     *
     * @since 1.0
     *
     * @return 是否接收
     */
    public boolean isNotificationEnabled();

    /**
     *
     * <b>设置免打扰时段</b>
     * <p>
     * iOS SDK不实现该方法。指定时间内处于免打扰模式，通知到达时将去除通知的提示音，震动以及提示删除。 如果都设置为0则为关闭免打扰模式
     * </p>
     *
     * @since 1.0
     *
     * @param startHour 开始时间-小时
     * @param startMinute 开始时间-分钟
     * @param endHour 结束时间-小时
     * @param endMinute 结束时间-分钟
     */
    public void setNoDisturbMode(int startHour, int startMinute, int endHour, int endMinute);

    /**
     * <b>设置回调函数任务执行器</b>
     * <p>
     * 用户可设置回调函数的执行线程，如果不设置，SDK将使用主线程进行函数回调
     * </p>
     * 
     * @param executor
     * 
     * @since 1.0
     */
    // public void setCallbackThread(Executor executor);
}
