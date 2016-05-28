package com.baidu.imc.impl.push.client;

import java.nio.charset.Charset;

import org.json.JSONObject;

import android.content.Context;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.baidu.im.frame.inapp.IAppSession;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.utils.GlobalInstance;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceKey;
import com.baidu.im.sdk.ChannelSdk;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.client.PushClient;
import com.baidu.imc.exception.IllegalParameterException;
import com.baidu.imc.exception.InitializationException;
import com.baidu.imc.impl.im.message.MessageBroadcastReceiver;
import com.baidu.imc.impl.im.store.IMsgStore;
import com.baidu.imc.impl.im.store.MemoryMsgStore;
import com.baidu.imc.impl.im.transaction.IMTransactionFlow;
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
public class PushClientImpl implements PushClient, IAppSession.IAppSessionListener {

    public static final String TAG = "PushClientImpl";

    protected Context applicationContext;
    private String appKey;
    private ClientStatusListener clientStatusListener;
    private UserStatus currentUserStatus = UserStatus.LOGOUT;
    private ClientConnectStatus currentClientConnectStatus = ClientConnectStatus.DISCONNECTED;
    protected MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    protected MemoryMsgStore msgStoreImpl;

    public PushClientImpl() {
        this.msgStoreImpl = new MemoryMsgStore();
        LogUtil.printIm("push client impl logout");
        currentUserStatus = UserStatus.LOGOUT;
        currentClientConnectStatus = ClientConnectStatus.DISCONNECTED;
    }

    public void init(Context context, String appKey) throws IllegalParameterException {

        if (context == null || context.getApplicationContext() == null || null == appKey || appKey.length() == 0) {
            throw new IllegalParameterException();
        }
        this.applicationContext = context.getApplicationContext();
        this.appKey = appKey;
        this.msgStoreImpl.setContext(applicationContext);
        this.msgStoreImpl.setAppKey(appKey);
        this.msgStoreImpl.setUserID(null);
    }

    @Override
    public void setClientStatusListener(ClientStatusListener listener) {
        this.clientStatusListener = listener;
    }

    @Override
    public void connect() throws InitializationException {
        if (applicationContext == null) {
            throw new InitializationException();
        }

        // 注册会话状态的监听器
        InAppApplication.getInstance().setSessionListener(this);

        if (null == appKey || appKey.length() == 0) {
            if (null != clientStatusListener) {
                clientStatusListener.onConnectError(100001, "appKey is null.");
            }
            return;
        }

        // 调用connect操作时，不允许用户自动登录
        // initialize session info
        InAppApplication.getInstance().initSession(applicationContext, appKey);
        IAppSession.ISession session = InAppApplication.getInstance().getSession().getSessionInfo();
        /*
        if (session != null && session.isValidUserSession()) {
            session.clearSession();
        }*/

        // initialize会尝试进行regchannel, regapp和心跳的过程
        ChannelSdk.initialize(applicationContext, appKey, new IMessageResultCallback() {

            @Override
            public void onSuccess(String description, byte[] data) {
                // RegApp Success
                if (null != clientStatusListener) {
                    if (null != data && data.length != 0) {
                        // RegApp 或者 HeartBeat中注册App，返回DeviceId；理论上只返回一次
                        clientStatusListener.onConnect(new String(data, Charset.forName("utf-8")));
                    }
                }
            }

            @Override
            public void onFail(int errorCode) {

                if (clientStatusListener != null) {
                    clientStatusListener.onConnectError(100001, "appKey is invalid： " + errorCode);
                }

            }

        }, new IMessageResultCallback() {

            @Override
            public void onSuccess(String description, byte[] data) {
                // Heartbeat success

            }

            @Override
            public void onFail(int errorCode) {
                // Heartbeat error
                // 100001: Illegal App Key
                // 100002: Incorrect User Token
                // 100003: Another Client Logined
                // 100004: Overdue User Token
                // 100000: unknown error
                switch (errorCode) {
                    case 1: // USER_LOGOUT_NOTIFY
                        if (clientStatusListener != null) {
                            clientStatusListener.onLoginError(100003, "Another Client Logined： " + errorCode);
                        }
                        break;
                    case -1120:
                        if (clientStatusListener != null) {
                            clientStatusListener.onLoginError(100004, "Overdue User Token： " + errorCode);
                        }
                    default:
                        break;
                }
            }
        });

        // Register BroadcastReceiver for receiving push msg
        IntentFilter intentFilter = new IntentFilter(ChannelSdk.getBroadcastFilter());
        applicationContext.registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    public void disconnect() {
        if (messageBroadcastReceiver != null) {
            messageBroadcastReceiver.clearMsgListener();
            applicationContext.unregisterReceiver(messageBroadcastReceiver);
        }
        ChannelSdk.destroy();
    }

    @Override
    public void login(String userID, String userToken) {
        if (TextUtils.isEmpty(userID) || TextUtils.isEmpty(userToken)) {
            if (clientStatusListener != null)
                clientStatusListener.onLoginError(100002, "Incorrect User Token!： ");
            throw new IllegalParameterException();
        }
        connect();
        ChannelSdk.login(userID, userToken, new IMessageResultCallback() {

            @Override
            public void onSuccess(String description, byte[] data) {
                // TODO ZX 获取 Setting

            }

            @Override
            public void onFail(int errorCode) {

                if (clientStatusListener != null) {
                    // SESSION_TOKEN_NOT_EXIST = 10101; //鉴权token不存在，例如passport的bduss不存在
                    // SESSION_SESSION_NOT_EXIST = 10120; //session不存在
                    clientStatusListener.onLoginError(100002, "Login failed!： " + errorCode);
                }

            }
        });
    }

    @Override
    public void logout() {
        ChannelSdk.logout(null);
        msgStoreImpl.clearAllDB();
    }

    @Override
    public ClientConnectStatus getCurrentClientConnectStatus() {
        return currentClientConnectStatus;
    }

    @Override
    public String getCurrentUserID() {

        if (null != InAppApplication.getInstance().getSession()
                && null != InAppApplication.getInstance().getSession().getSessionInfo()
                && null != InAppApplication.getInstance().getSession().getSessionInfo().getAccountId()) {
            return InAppApplication.getInstance().getSession().getSessionInfo().getAccountId();
        } else {
            return "";
        }
    }

    @Override
    public UserStatus getCurrentUserStatus() {
        return currentUserStatus;
    }

    @Override
    public void setPushMessageListener(PushMessageListener listener) {
        messageBroadcastReceiver.addPushMsgListener(listener);
    }

    @Override
    public void enableNotification() {

        try {
            LogUtil.printMainProcess("Push client: enableNotification.");
            // GlobalInstance.Instance().preferenceInstace().initialize(applicationContext);
            GlobalInstance.Instance().preferenceInstace().save(PreferenceKey.notificationDisableFlag, false);
        } catch (Exception e) {
            LogUtil.e(TAG, e);
        }

    }

    @Override
    public void disableNotification() {

        try {
            LogUtil.printMainProcess("Push client: disableNotification.");
            // GlobalInstance.Instance().preferenceInstace().initialize(applicationContext);
            GlobalInstance.Instance().preferenceInstace().save(PreferenceKey.notificationDisableFlag, true);
        } catch (Exception e) {
            LogUtil.e(TAG, e);
        }

    }

    @Override
    public boolean isNotificationEnabled() {

        boolean flag = false;
        try {
            LogUtil.printMainProcess("Push client: isNotificationEnabled.");
            // PreferenceUtil.initialize(applicationContext);
            flag =
                    GlobalInstance.Instance().preferenceInstace()
                            .getBoolean(PreferenceKey.notificationDisableFlag, false);
        } catch (Exception e) {
            LogUtil.e(TAG, e);
        }
        LogUtil.printMainProcess("Push client: isNotificationEnabled. flag=" + flag);
        return flag;
    }

    @Override
    public void setNoDisturbMode(int startHour, int startMinute, int endHour, int endMinute)
            throws IllegalParameterException {

        if (startHour > 23 || startHour < 0 || endHour > 23 || endHour < 0 || startMinute > 59 || startMinute < 0
                || endMinute > 59 || endMinute < 0 || startHour > endHour) {
            LogUtil.printMainProcess(TAG, "setNoDisturbMode skip, for the params doesn't make sense.");
            throw new IllegalParameterException();
        }

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("startHour", startHour);
            jsonObject.put("startMinute", startMinute);
            jsonObject.put("endHour", endHour);
            jsonObject.put("endMinute", endMinute);
            LogUtil.printMainProcess("Push client: setNoDisturbMode." + jsonObject.toString());
            int duration = 0;
            if (endHour * 60 + endMinute >= startHour * 60 + startMinute) {
                duration = (endHour * 60 + endMinute - startHour * 60 - startMinute) * 60 * 1000;
            } else {
                duration = ((endHour + 24) * 60 + endMinute - startHour * 60 - startMinute) * 60 * 1000;
            }
            long startTime = (startHour * 60 + startMinute) * 60 * 1000;
            jsonObject.put("startTime", startTime);
            jsonObject.put("duration", duration);
            // PreferenceUtil.initialize(applicationContext);
            GlobalInstance.Instance().preferenceInstace()
                    .save(PreferenceKey.notificationDisableDuration, jsonObject.toString());
        } catch (Exception e) {
            LogUtil.e(TAG, e);
        }
    }

    public IMsgStore getMsgStore() {
        return msgStoreImpl;
    }

    @Override
    public void statusChanged(int oldStatus, int newStatus) {

        // Client Connect Status
        ClientConnectStatus oldClientConnectStatus = currentClientConnectStatus;
        if (InAppApplication.getInstance().getSession() == null) {
            currentClientConnectStatus = ClientConnectStatus.DISCONNECTED;
        } else if (InAppApplication.getInstance().getSession().getStatus() < IAppSession.STATUS_APP_OFFLINE) {
            currentClientConnectStatus = ClientConnectStatus.CONNECTING;
        } else {
            currentClientConnectStatus = ClientConnectStatus.CONNECTED;
        }
        if (currentClientConnectStatus != oldClientConnectStatus) {
            if (clientStatusListener != null) {
                clientStatusListener.onClientConnectStatusChanged(currentClientConnectStatus);
            }
        }

        // User Status
        final UserStatus oldUserStatus = currentUserStatus;
        if (InAppApplication.getInstance().getSession() == null) {
        	LogUtil.printIm(TAG, "session is empty, will not send");
            currentUserStatus = UserStatus.LOGOUT;
            LogUtil.printIm("userstatus logout in session is null logout");
            msgStoreImpl.setUserID(null);
        } else if (InAppApplication.getInstance().getSession().getStatus() == IAppSession.STATUS_USER_LOGIN) {
            currentUserStatus = UserStatus.ONLINE;
            msgStoreImpl.setUserID(getCurrentUserID());
        } else if (InAppApplication.getInstance().getSession().getSessionInfo().isValidUserSession()) {
            currentUserStatus = UserStatus.OFFLINE;
            msgStoreImpl.setUserID(getCurrentUserID());
        } else {
        	LogUtil.printIm("else in logout");
            currentUserStatus = UserStatus.LOGOUT;
            msgStoreImpl.setUserID(null);
        }
        if (currentUserStatus != oldUserStatus) {
            if (clientStatusListener != null) {
                clientStatusListener.onUserStatusChanged(currentUserStatus);
            }
        }


    }

}
