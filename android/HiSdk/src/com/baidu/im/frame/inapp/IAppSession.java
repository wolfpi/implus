package com.baidu.im.frame.inapp;

import com.baidu.im.frame.NetworkChannel.NetworkChannelStatus;
import com.baidu.im.frame.utils.PreferenceUtil;

public interface IAppSession {
    public static final int STATUS_NOT_INIT = 0;

    /**
     * 初始化后状态置为channel_offline
     */
    public static final int STATUS_CHANNEL_OFFLINE = 1;

    /**
     * RegChannel成功后状态置为channel_online
     */
    public static final int STATUS_CHANNEL_ONLINE = 2;

    /**
     * AppLogin失败后状态置为app_online； AppLogout成功后置为app_offline； 销毁后app_offline； RegApp成功后app_offline；
     * RegChannel成功有appid后app_offline； UserLogout成功或者失败后app_offline； Heartbeat失败后大于app_offline置为app_offline；
     * 初始化后大于app_offline全部置为app_offline； 网络变化后大于app_offline全部置为app_offline； sessionError后大于app_offline全部置为app_offline；
     */
    public static final int STATUS_APP_OFFLINE = 3;

    /**
     * Heartbeat成功后恢复为app_online, app_login or user_online
     */
    public static final int STATUS_APP_ONLINE = 4;

    /**
     * AppLogin成功后；
     */
    public static final int STATUS_APP_LOGIN = 5;

    /**
     * UserLogin成功后；
     */
    public static final int STATUS_USER_LOGIN = 6;

    public void networkChannelStatusChanged(NetworkChannelStatus networkChannelStatus);
    
    public NetworkChannelStatus getChannelStatus();

    public int getStatus();

    public void setStatus(int newStatus);

    public ISession getSessionInfo();

    public void init(String apiKey, PreferenceUtil preference);

    public void regAppSuccess(int appId, String deviceId, int deviceTypeId);

    public void unregAppSuccess();

    public void regChannelSuccess(String channelKey);

    public void heartbeatSuccess(boolean sessionError);

    public void sessionError();

    public void sessionSuccess();

    public void appLoginFailed();

    public void appLoginSuccess(String sessionId);

    public void userLoginFailed();

    public void userLoginFinished(String sessionId, String accountId, String token, long uid);

    public void appLogoutSuccess();

    public void userLogoutSuccess();

    public void destroy();

    public String getPackageName();

    public void setPackageName(String packageName);

    public int getClientId();

    public void setClientId(int clientId);

    public IApp getApp();

    public void setListener(IAppSessionListener listener);

    public IChannel getChannel();

    public interface IChannel {
        public boolean isValidChannel();

        public void clearChannelData();

        public String getChannelKey();

        public void setChannelKey(String channelKey);
    }

    public interface IApp {

        public boolean isValidApp();

        public void clearData();

        public int getAppId();

        public void setAppId(int appId);

        public int getClientId();

        public void setClientId(int clientId);

        public String getPackageName();

        public void setPackageName(String packageName);

        public String getApiKey();

        public void setApiKey(String apiKey);

        public String getDeviceId();

        public void setDeviceId(String deviceId);

        public int getDeviceTypeId();

        public void setDeviceTypeId(int deviceTypeId);
    }

    public interface ISession {
        public boolean isValidUserSession();

        public boolean isValidAppSession();

        public String getSessionId();

        public long getUid();

        public String getAccountId();

        public String getToken();

        public long getCreateTime();

        // public void refresh();

        // public void save();

        public void appLoginFinished(String sessionId);

        public void userLoginFinished(String sessionId, String accountId, String token, long uid);

        public void clearSession();

        public void setSessionId(String sessionId);

        public void setUid(long uid);

        public void setAccountId(String accountId);

        public void setToken(String token);

        public void setCreateTime(long createTime);
    }

    public static interface IAppSessionListener {
        public void statusChanged(int oldStatus, int newStatus);
    }
}
