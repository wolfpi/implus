package com.baidu.im.frame.inapp;

import android.text.TextUtils;

import com.baidu.im.frame.NetworkChannel.NetworkChannelStatus;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceKey;
import com.baidu.im.frame.utils.PreferenceUtil;

public class AppSessionImpl implements IAppSession {
    public static final String TAG = "AppSession";

    private DefaultChannelImpl channel = new DefaultChannelImpl();
    private DefaultAppImpl app = new DefaultAppImpl();
    private DefaultSessionImpl session = new DefaultSessionImpl();
    private IAppSessionListener listener = null;
    private NetworkChannelStatus oldNetworkStatus;
    private PreferenceUtil mPreference = null;

    public NetworkChannelStatus getChannelStatus()
    {
    	return oldNetworkStatus;
    }
    
    @Override
    public void networkChannelStatusChanged(NetworkChannelStatus networkChannelStatus) {
        if (networkChannelStatus != NetworkChannelStatus.Connected) {
            // switch (getStatus()) {
            // case STATUS_APP_ONLINE:
            // case STATUS_APP_LOGIN:
            // case STATUS_USER_LOGIN:
            // this.setStatus(STATUS_APP_OFFLINE);
            // break;
            // }
            this.setStatus(STATUS_CHANNEL_OFFLINE);
        } else if (oldNetworkStatus != networkChannelStatus) {
            // 网络重连后，需要进行初始化
            this.setStatus(STATUS_CHANNEL_ONLINE);

            if (InAppApplication.getInstance().isConnected()) {
            	new Thread(new Runnable() {
            		@Override
            		public void run() {
            			 InAppApplication.getInstance().getTransactionFlow().channelReconnect();
            		}
            	}).start();
               
            }
            else {
            	 InAppApplication.getInstance().getTransactionFlow().regApp(null);
            }
        }

        if (oldNetworkStatus != networkChannelStatus) {
            InAppApplication.getInstance().netWorkChanged(networkChannelStatus);
        }

        oldNetworkStatus = networkChannelStatus;

    }

    @Override
    public int getStatus() {
        return mPreference.getInt(PreferenceKey.sessionStatus);
    }

    @Override
    public void setStatus(int newStatus) {
        int oldStatus = getStatus();
        LogUtil.printMainProcess("Set Status ..................." + oldStatus + ">>>>>>" + newStatus);
        mPreference.save(PreferenceKey.sessionStatus, newStatus);
        if (newStatus != oldStatus && listener != null) {
            listener.statusChanged(oldStatus, newStatus);
        }
    }

    @Override
    public ISession getSessionInfo() {
        // session.refresh();
        return session;
    }

    @Override
    public void init(String apiKey, PreferenceUtil preference) {
        mPreference = preference;
        String oldApiKey = app.getApiKey();

        if (!apiKey.equals(oldApiKey)) {
            channel.clearChannelData();
            app.clearData();
            session.clearSession();
            app.setApiKey(apiKey);
            this.setStatus(STATUS_CHANNEL_OFFLINE);
        } else {
            // apiKey没变化，需要检查状态是否需要变更
            switch (getStatus()) {
                case STATUS_APP_ONLINE:
                case STATUS_APP_LOGIN:
                case STATUS_USER_LOGIN:
                    this.setStatus(STATUS_APP_OFFLINE);
                    break;
            }
        }
    }

    /**
     * Save appId, deviceId and deviceTypeId after RegApp is successful.<br/>
     * Will set status as app_online if it is channel_online already after hear-beat.
     */
    @Override
    public void regAppSuccess(int appId, String deviceId, int deviceTypeId) {
        app.setAppId(appId);
        app.setDeviceId(deviceId);
        app.setDeviceTypeId(deviceTypeId);
        // switch (getStatus()) {
        // case STATUS_CHANNEL_ONLINE:
        // this.setStatus(STATUS_APP_OFFLINE);
        // break;
        // }
    }

    /**
     * Clear session, app and channel data after UpregApp is successful.<br/>
     * Set status as app_offline. Do not need to send heart-beat.
     */
    @Override
    public void unregAppSuccess() {
        session.clearSession();
        app.clearData();
        channel.clearChannelData();
        // this.setStatus(STATUS_APP_OFFLINE);
        sessionError();
    }

    /**
     * Save channelKey after RegChannel is successful.<br/>
     * set status as app_offline if it already has appId or set status as channel_online
     */
    @Override
    public void regChannelSuccess(String channelKey) {
        channel.setChannelKey(channelKey);
        // LogUtil.e(TAG, "reg channel success in channkey");
        InAppApplication.getInstance().getTransactionFlow().resend();
        switch (getStatus()) {
            case STATUS_CHANNEL_OFFLINE:
                if (app.getAppId() > 0) {
                    this.setStatus(STATUS_APP_OFFLINE);
                } else {
                    this.setStatus(STATUS_CHANNEL_ONLINE);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Set app_status after heart-beat.
     */
    @Override
    public void heartbeatSuccess(boolean sessionError) {
        if (sessionError) {
            sessionError();
        } else {
            sessionSuccess();
        }
    }

    @Override
    public void sessionError() {
        session.clearSession();
        switch (getStatus()) {
            case STATUS_APP_ONLINE:
            case STATUS_APP_LOGIN:
            case STATUS_USER_LOGIN:
                this.setStatus(STATUS_APP_OFFLINE);
                break;
            default:
                break;
        }
    }

    @Override
    public void sessionSuccess() {
        if (session.isValidUserSession()) {
            this.setStatus(STATUS_USER_LOGIN);
        } else if (session.isValidAppSession()) {
            this.setStatus(STATUS_APP_LOGIN);
        } else {
            this.setStatus(STATUS_APP_ONLINE);
        }
    }

    /**
     * Clear session after AppLogin is failed.<br/>
     * Will set status as app_offline right now.
     */
    @Override
    public void appLoginFailed() {
        // app登录失败时，需要清理会话；同时设置为app离线状态
        // session.clearSession();
        // this.setStatus(STATUS_APP_OFFLINE);
        sessionError();
    }

    /**
     * Save sessionId after AppLogin is successful.<br/>
     * Will set status as app_login after heart-beat.
     */
    @Override
    public void appLoginSuccess(String sessionId) {
        session.appLoginFinished(sessionId);
        // this.setStatus(STATUS_APP_LOGIN);
    }

    /**
     * Clear session if UserLogin is failed.<br/>
     * Set status as app_offline right now.
     */
    @Override
    public void userLoginFailed() {
        // session.clearSession();
        // this.setStatus(STATUS_APP_OFFLINE);
        sessionError();
    }

    /**
     * Save sessionId, accountId, token and u-id after UserLogin is finished. <br/>
     * Will set status as user_login after heart-beat.
     */
    @Override
    public void userLoginFinished(String sessionId, String accountId, String token, long uid) {
        session.userLoginFinished(sessionId, accountId, token, uid);
        // this.setStatus(STATUS_USER_LOGIN);
    }

    /**
     * After AppLogout is successful, set status as app_offline; Do not need heart-beat.
     */
    @Override
    public void appLogoutSuccess() {
        // session.clearSession();
        // this.setStatus(STATUS_APP_OFFLINE);
        //sessionError();
    }

    /**
     * After UserLogout is successful, set status as app_offline; Do not need heart-beat.
     */
    @Override
    public void userLogoutSuccess() {
        // session.clearSession();
        // this.setStatus(STATUS_APP_OFFLINE);
        sessionError();
    }

    @Override
    public void destroy() {
        this.setStatus(STATUS_APP_OFFLINE);
    }

    @Override
    public String getPackageName() {
        return app.getPackageName();
    }

    @Override
    public void setPackageName(String packageName) {
        app.setPackageName(packageName);
    }

    @Override
    public int getClientId() {
        return app.getClientId();
    }

    @Override
    public void setClientId(int clientId) {
        app.setClientId(clientId);
    }

    @Override
    public IApp getApp() {
        return app;
    }

    @Override
    public void setListener(IAppSessionListener listener) {
        this.listener = listener;
    }

    @Override
    public IChannel getChannel() {
        return channel;
    }

    private class DefaultAppImpl implements IApp {

        @Override
        public boolean isValidApp() {
            if (getAppId() != 0) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void clearData() {
            setAppId(0);
            setClientId(0);
            setPackageName(null);
            setClientId(0);
            setDeviceId(null);
            setDeviceTypeId(0);
        }

        @Override
        public int getAppId() {
            return mPreference.getInt(PreferenceKey.appId);
        }

        @Override
        public void setAppId(int appId) {
            mPreference.save(PreferenceKey.appId, appId);
        }

        @Override
        public int getClientId() {
            return mPreference.getInt(PreferenceKey.clientId);
        }

        @Override
        public void setClientId(int clientId) {
            mPreference.save(PreferenceKey.clientId, clientId);
        }

        @Override
        public String getPackageName() {
            return mPreference.getString(PreferenceKey.packageName);
        }

        @Override
        public void setPackageName(String packageName) {
            mPreference.save(PreferenceKey.packageName, packageName);
        }

        @Override
        public String getApiKey() {
            return mPreference.getString(PreferenceKey.apiKey);
        }

        @Override
        public void setApiKey(String apiKey) {
            mPreference.save(PreferenceKey.apiKey, apiKey);
        }

        @Override
        public String getDeviceId() {
            return mPreference.getString(PreferenceKey.deviceId);
        }

        @Override
        public void setDeviceId(String deviceId) {
            mPreference.save(PreferenceKey.deviceId, deviceId);
        }

        @Override
        public int getDeviceTypeId() {
            return mPreference.getInt(PreferenceKey.deviceTypeId, 0);
        }

        @Override
        public void setDeviceTypeId(int deviceTypeId) {
            mPreference.save(PreferenceKey.deviceTypeId, deviceTypeId);
        }
    }

    private class DefaultSessionImpl implements ISession {
        private String mSessionId = null;
        private long mUid = Long.MAX_VALUE;
        private String mAccountId = null;
        private String mToken = null;
        private long mCreateTime = System.currentTimeMillis();

        @Override
        public boolean isValidUserSession() {
            String sessionId = getSessionId();
            long uid = getUid();
            if (!TextUtils.isEmpty(sessionId) && uid != 0 && uid != Long.MAX_VALUE) {
                return true;
            }
            return false;
        }

        @Override
        public boolean isValidAppSession() {
            String sessionId = getSessionId();
            long uid = getUid();
            if (!TextUtils.isEmpty(sessionId) && uid == 0) {
                return true;
            }
            return false;
        }

        @Override
        public String getSessionId() {
        	if(mSessionId == null)
        	{
        		mSessionId =  mPreference.getString(PreferenceKey.sessionId);
        	}
            return mSessionId;
        }

        @Override
        public long getUid() {
            mUid = mPreference.getLong(PreferenceKey.uid);  
            return mUid;
        }

        @Override
        public String getAccountId() {
        	if(mAccountId == null)
        	{
        		mAccountId =  mPreference.getString(PreferenceKey.accountId);
        	}
            return mAccountId;
        }

        @Override
        public String getToken() {
        	if(mToken == null)
        	{
        		mToken =  mPreference.getString(PreferenceKey.token);
        	}
            return mToken;
        }

        @Override
        public long getCreateTime() {
            return mCreateTime;
        }

        @Override
        public void appLoginFinished(String sessionId) {
            setSessionId(sessionId);
            setUid(0);
            setAccountId(null);
            setToken(null);
            setCreateTime(System.currentTimeMillis());
        }

        @Override
        public void userLoginFinished(String sessionId, String accountId, String token, long uid) {
            setSessionId(sessionId);
            setUid(uid);
            setAccountId(accountId);
            setToken(token);
            setCreateTime(System.currentTimeMillis());
        }

        @Override
        public void clearSession() {
            setSessionId(null);
            setUid(Long.MAX_VALUE);
            setAccountId(null);
            setToken(null);
            setCreateTime(System.currentTimeMillis());
        }

        @Override
        public void setSessionId(String sessionId) {
            mSessionId = sessionId;
            mPreference.save(PreferenceKey.sessionId, sessionId);
        }

        @Override
        public void setUid(long uid) {
            mUid = uid;
            mPreference.save(PreferenceKey.uid, uid);
        }

        @Override
        public void setAccountId(String accountId) {
            mAccountId = accountId;
            mPreference.save(PreferenceKey.accountId, accountId);
        }

        @Override
        public void setToken(String token) {
            mToken = token;
            mPreference.save(PreferenceKey.token, token);
        }

        @Override
        public void setCreateTime(long createTime) {
            mCreateTime = createTime;
            mPreference.save(PreferenceKey.createTime, createTime);
        }

    }

    private class DefaultChannelImpl implements IChannel {

        /**
         * InApp 现在可能没有channelKey了
         */
        private String mChannelKey = null;

        @Override
        public boolean isValidChannel() {
            if (null != mChannelKey && mChannelKey.length() > 0) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void clearChannelData() {
            setChannelKey(null);
        }

        @Override
        public String getChannelKey() {
            if (mChannelKey == null) {
                return "";
            }
            return mChannelKey;
        }

        @Override
        public void setChannelKey(String channelKey) {
            mChannelKey = channelKey;
            // mPreference.saveChanneKey(channelKey);
        }

    }
}
