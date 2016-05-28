package com.baidu.im.sdk;

import android.content.Context;

import com.baidu.im.frame.inapp.ChannelSdkImpl;

/**
 * Interface of SDK.
 * 
 * @version 0.1
 * 
 */
public final class ChannelSdk {

    public static int getVersionCode() {
        return 32;
    }

    public static String getVersionName() {
        return "v1.0.0.0";
    }

    /**
     * SDK初始化，有方法同步锁。apiKey是在im平台申请的app唯一的key。
     */
    public static void initialize(final Context context, final String apiKey, IMessageResultCallback regAppCallback,
            IMessageResultCallback heartbeatCallback) {
        ChannelSdkImpl.initialize(context, apiKey, regAppCallback, heartbeatCallback);
    }

    /**
     * 获取message broadcast filter key.
     */
    public static String getBroadcastFilter() {
        return ChannelSdkImpl.getBroadcastFilter();
    }

    public static void appLogin(IMessageCallback callback) {
        ChannelSdkImpl.appLogin(callback);
    }

    public static void appLogout(IMessageCallback callback) {
        ChannelSdkImpl.appLogout(callback);
    }

    /**
     * 账户bduss登陆
     */
    public static void login(String accountId, String token, IMessageCallback callback) {
        ChannelSdkImpl.loginByToken(accountId, token, callback);
    }

    /**
     * 
     */
    public static void logout(IMessageCallback callback) {
        ChannelSdkImpl.logout(callback);
    }

    /**
     * 验证账户是否登陆
     */
    public static EAccountStatus getAccountStatus() {
        return ChannelSdkImpl.getAccountStatus();
    }

    /**
     * Send message.
     */
    public static void send(BinaryMessage message, IMessageCallback callback) {
        ChannelSdkImpl.send(message, callback);
    }

    /**
     * 通过message id获取message。
     */
    public static ImMessage getMessage(String messageId) {
        return ChannelSdkImpl.getMessage(messageId);
    }

    /**
     * 销毁SDK实例。
     */
    public static void destroy() {
        ChannelSdkImpl.destroy();
    }

}
