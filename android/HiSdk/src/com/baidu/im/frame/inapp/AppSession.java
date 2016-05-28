//package com.baidu.im.frame.inapp;
//
//import android.text.TextUtils;
//
//import com.baidu.im.frame.NetworkChannel.NetworkChannelStatus;
//import com.baidu.im.frame.pb.EnumAppStatus.EAppStatus;
//import com.baidu.im.frame.pb.EnumUserStatus.EUserStatus;
//import com.baidu.im.frame.utils.LogUtil;
//import com.baidu.im.frame.utils.PreferenceUtil;
//import com.baidu.im.frame.utils.PreferenceUtil.PreferenceKey;
//
///**
// * App session数据结构，为sdk保存app信息，有些信息会持久化。
// * 
// * @author zhaowei10
// * 
// */
//public class AppSession {
//
//    public static final String TAG = "AppSession";
//
//    private int clientId;
//
//    private String packageName;
//
//    private String sessionId;
//
//    private String account;
//
//    private String password;
//
//    private String token;
//
//    private NetworkChannelStatus networkChannelStatus;
//
//    private AppSessionListener listener;
//    
//    public AppSession() {
//        LogUtil.printMainProcess("AppSession initializing. appid=" + getAppId() + "  channelKey=" + getChannelKey()
//                + "  session=" + getSessionModel().toString());
//    }
//
//    public boolean isRegApp() {
//        if (getAppId() == 0) {
//            return false;
//        }
//        return true;
//    }
//    
//    public void setSessionListener(AppSessionListener listener){
//    	this.listener = listener;
//    }
//
//    /**
//     * @return the sessionId
//     */
//    public SessionModel getSessionModel() {
//        SessionModel sessionModel = new SessionModel(PreferenceUtil.getString(PreferenceKey.session));
//        sessionId = sessionModel.getSessionId();
//        return sessionModel;
//    }
//
//    public String getSessionId() {
//        return sessionId;
//    }
//
//    /**
//     * @param sessionId the sessionId to set
//     */
//    public void setSession(SessionModel sessionModel) {
//        this.sessionId = sessionModel.getSessionId();
//        PreferenceUtil.save(PreferenceKey.session, sessionModel.toString());
//    }
//
//    public void removeSession() {
//        this.sessionId = "";
//        PreferenceUtil.remove(PreferenceKey.session);
//    }
//
//    /**
//     * @return the uid
//     */
//    public long getUid() {
//        return PreferenceUtil.getLong(PreferenceKey.uid);
//    }
//    
//    public boolean isUserSession(){
//    	return getUid() != 0L && getSessionModel() != null;
//    }
//    
//    /**
//     * @param uid the uid to set
//     */
//    public void setUid(long uid) {
//        PreferenceUtil.save(PreferenceKey.uid, uid);
//    }
//
//    public void removeUid() {
//        PreferenceUtil.remove(PreferenceKey.uid);
//    }
//
//    public void removeConfig() {
//        PreferenceUtil.remove(PreferenceKey.imConfig);
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public void removeToken() {
//    	token = null;
//    }
//
//    public int getAppId() {
//        return PreferenceUtil.getInt(PreferenceKey.appId);
//    }
//
//    public void setAppId(int appId) {
//        PreferenceUtil.save(PreferenceKey.appId, appId);
//    }
//
//    public void removeAppId() {
//        PreferenceUtil.remove(PreferenceKey.appId);
//    }
//
//    public String getChannelKey() {
//        return PreferenceUtil.getString(PreferenceKey.channelKey);
//    }
//
//    public void setChannelKey(String channelKey) {
//        PreferenceUtil.save(PreferenceKey.channelKey, channelKey);
//    }
//
//    public void removeChannelKey() {
//        PreferenceUtil.remove(PreferenceKey.channelKey);
//    }
//
//    public String getApiKey() {
//        return PreferenceUtil.getString(PreferenceKey.apiKey);
//    }
//
//    public void setApiKey(String apiKey) {
//        PreferenceUtil.save(PreferenceKey.apiKey, apiKey);
//    }
//
//    public void removeApiKey() {
//        PreferenceUtil.remove(PreferenceKey.apiKey);
//    }
//
//    public EAppStatus getAppStatus() {
//        int appStatus = PreferenceUtil.getInt(PreferenceKey.appStatus);
//        if (appStatus == 0) {
//            return EAppStatus.APP_NULL;
//        } else {
//            return EAppStatus.valueOf(appStatus);
//        }
//    }
//
//    public void setAppStatus(EAppStatus appStatus) {
//        PreferenceUtil.save(PreferenceKey.appStatus, appStatus.getNumber());
//        if(listener != null) listener.appStatusChanged(appStatus);
//    }
//
//    public void removeAppStatus() {
//        PreferenceUtil.remove(PreferenceKey.appStatus);
//        if(listener != null) listener.appStatusChanged(EAppStatus.APP_NULL);
//    }
//
//    public EUserStatus getUserStatus() {
//        int userStatus = PreferenceUtil.getInt(PreferenceKey.userStatus);
//        if (userStatus == 0) {
//            return EUserStatus.USER_NULL;
//        } else {
//            return EUserStatus.valueOf(userStatus);
//        }
//    }
//
//    public void setUserStatus(EUserStatus userStatus) {
//        PreferenceUtil.save(PreferenceKey.userStatus, userStatus.getNumber());
//        if(listener != null) listener.userStatusChanged(userStatus, getUid());
//    }
//
//    public void removeUserStatus() {
//        PreferenceUtil.remove(PreferenceKey.userStatus);
//        if(listener != null) listener.userStatusChanged(EUserStatus.USER_NULL, 0L);
//    }
//
//    /**
//     * @return the packageName
//     */
//    public String getPackageName() {
//        return packageName;
//    }
//
//    /**
//     * @param packageName the packageName to set
//     */
//    public void setPackageName(String packageName) {
//        this.packageName = packageName;
//    }
//
//    public String getAccount() {
//        return account;
//    }
//
//    public void setAccount(String account) {
//        this.account = account;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public int getClientId() {
//        return clientId;
//    }
//
//    public void setClientId(int clientId) {
//        this.clientId = clientId;
//    }
//
//    public NetworkChannelStatus getNetworkChannelStatus() {
//        return networkChannelStatus;
//    }
//
//    public void setNetworkChannelStatus(NetworkChannelStatus networkChannelStatus) {
//    	NetworkChannelStatus oldStatus = this.networkChannelStatus;
//    			
//        this.networkChannelStatus = networkChannelStatus;
//
//        if(networkChannelStatus != NetworkChannelStatus.Connected &&
//        		EUserStatus.USER_ONLINE == this.getUserStatus()){
//        	this.setUserStatus(EUserStatus.USER_OFFLINE);
//        }
//        if(networkChannelStatus != NetworkChannelStatus.Connected &&
//        		EAppStatus.APP_ONLINE == this.getAppStatus()){
//        	this.setAppStatus(EAppStatus.APP_OFFLINE);
//        }
//    	if(oldStatus != networkChannelStatus){
//    		if(listener != null) listener.networkChannelStatusChanged(networkChannelStatus);
//    	}
//    }
//
//    public void clear() {
//        // clearChannelStatus();
//        clearAppStatus();
//    }
//
//    public void clearChannelStatus() {
//        removeChannelKey();
//    }
//
//    public void clearAppStatus() {
//    	clearUserStatus();
//        removeAppStatus();
//    	
//        removeApiKey();
//        removeAppId();
//        removeConfig();
//    }
//
//    public void clearUserStatus() {
//        removeToken();
//        removeSession();
//        removeUid();
//        removeUserStatus();
//    }
//
//    public void destroy() {
//    	//告知上层session断掉
//    	if(listener != null){
//    		listener.networkChannelStatusChanged(NetworkChannelStatus.Disconnected);
//    	}
//    }
//
//    /**
//     * The model keeps the information of session id.
//     * 
//     * @author zhaowei10
//     * 
//     */
//    public static class SessionModel {
//        private String sessionId;
//
//        private String accountKey;
//
//        private long timestamp;
//        
//        private boolean isUserSession = true;
//
//        public String getSessionId() {
//            return sessionId;
//        }
//
//        public void setSessionId(String sessionId) {
//            this.sessionId = sessionId;
//        }
//
//        public String getAccountKey() {
//            return accountKey;
//        }
//
//        public void setAccountKey(String accountKey) {
//            this.accountKey = accountKey;
//        }
//
//        public long getTimestamp() {
//            return timestamp;
//        }
//
//        public void setTimestamp(long timestamp) {
//            this.timestamp = timestamp;
//        }
//        
//		public boolean isUserSession() {
//			return isUserSession;
//		}
//
//		public void setUserSession(boolean isUserSession) {
//			this.isUserSession = isUserSession;
//		}
//
//		public SessionModel() {
//
//        }
//
//        public SessionModel(String str) {
//            if (!TextUtils.isEmpty(str)) {
//                try {
//                    String[] arr = str.split(";");
//                    accountKey = arr[0];
//                    sessionId = arr[1];
//                    timestamp = Long.parseLong(arr[2]);
//                    isUserSession = Boolean.parseBoolean(arr[3]);
//                } catch (RuntimeException e) {
//                    LogUtil.e(TAG, "Error session model string: " + str);
//                }
//            }
//        }
//
//        public String toString() {
//            return accountKey + ";" + sessionId + ";" + timestamp + ";" + isUserSession;
//        }
//    }
//    
//    public static interface AppSessionListener{
//    	public void networkChannelStatusChanged(NetworkChannelStatus newStatus);
//    	public void appStatusChanged(EAppStatus newAppStatus);
//    	public void userStatusChanged(EUserStatus newUserStatus, long uid);
//    }
//}
