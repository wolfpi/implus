package com.baidu.im.frame.inapp;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.im.constant.ErrorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.upgrade.UpgradeManager;
import com.baidu.im.frame.utils.CallbackUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.NetworkUtil;
import com.baidu.im.frame.utils.OsUtil;
import com.baidu.im.inapp.messagecenter.SendingMessageContainer.SendingMessage;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.EAccountStatus;
import com.baidu.im.sdk.IMessageCallback;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.im.sdk.ImMessage;
import com.baidu.im.sdk.LoginMessage;

public class ChannelSdkImpl {

    private static final String TAG = "ChannelSdkImpl";

    // 进程名称--out-app-service
    private static final String PROCESS_NAME_OUT_APP_SERVICE = "backgroundService";
    // 进程名称--贴吧service的守护进程
    private static final String PROCESS_NAME_DAEMON_SERVICE = "daemon";

    private static CallbackUtil mCallback = new CallbackUtil();

    /**
     * broadcast的filter key，SDK初始化时生成。
     */
    private static String broadcastFilter = null;

    public static int getVersionCode() {
        return 35;
    }

    public static String getVersionName() {
        return "v1.0.1.5";
    }

    /**
     * SDK初始化，有方法同步锁。apiKey是在im平台申请的app唯一的key。
     */
    public static synchronized void initialize(Context context, String apiKey, IMessageResultCallback regAppCallback,
            IMessageResultCallback heartbeatCallback) {

        try {
            if (TextUtils.isEmpty(broadcastFilter)) {
                broadcastFilter = context.hashCode() + "";

                // 如果有配置了多个进程且在application onCreate()时调用此方法，则会启动多次。
                // 对上述case做优化：则只在APP的进程中启动SDK，如果APP自身拥有多个进程，则无法避免。
                String processName = OsUtil.getProcessNameBy(context);
                if (!TextUtils.isEmpty(processName)
                        && (processName.endsWith(PROCESS_NAME_OUT_APP_SERVICE) || processName
                                .endsWith(PROCESS_NAME_DAEMON_SERVICE))) {
                    LogUtil.printMainProcess("ChannelSdkImpl initialize skip, processname=" + processName);
                    return;
                }

                LogUtil.printMainProcess("ChannelSdkImpl initializing... processname=" + processName);
                InAppApplication.getInstance().initialize(context, apiKey, regAppCallback, heartbeatCallback);
            } else {
                LogUtil.printMainProcess("ChannelSdkImpl is running, skip initialize operation.");
            }
        } catch (Exception e) {
            LogUtil.printError(e);
        }
    }

    /**
     * 获取message broadcast filter key.
     */
    public static String getBroadcastFilter() {
        return broadcastFilter;
    }

    public static void appLogin(IMessageCallback callback) {
        try {
            if (!isInitialized() && callback != null) {
                mCallback.onFail(callback, ErrorCode.NotInitialize.getCode());
                return;
            }
            /*
            if (!NetworkUtil.isNetworkConnected(InAppApplication.getInstance().getContext()) && callback != null) {
                mCallback.onFail(callback, ErrorCode.Network_Error.getCode());
            }
            else*/ {
            //InAppApplication.getInstance().getTransactionFlow().appLogin(callback);
            }

        } catch (Exception e) {
            LogUtil.printError(e);
        }
    }

    public static void appLogout(IMessageCallback callback) {
        try {
            if (!isInitialized()) {
                mCallback.onFail(callback, ErrorCode.NotInitialize.getCode());
                return;
            }
            /*if (!NetworkUtil.isNetworkConnected(InAppApplication.getInstance().getContext())) {
                mCallback.onFail(callback, ErrorCode.Network_Error.getCode());
            }else {*/
            //InAppApplication.getInstance().getTransactionFlow().appLogout(callback);
            //}
        } catch (Exception e) {
            LogUtil.printError(e);
        }
    }

    /**
     * 账户密码登陆
     */
    public static void loginByToken(String accountId, String token, IMessageCallback callback) {
        try {
            if (!isInitialized()) {
                mCallback.onFail(callback, ErrorCode.NotInitialize.getCode());
                return;
            }
            if (TextUtils.isEmpty(token) || TextUtils.isEmpty(accountId)) {
                mCallback.onFail(callback, ErrorCode.WrongParams.getCode());
                return;
            }
           
            /*if (!NetworkUtil.isNetworkConnected(InAppApplication.getInstance().getContext())) {
                mCallback.onFail(callback, ErrorCode.Network_Error.getCode());
            }else*/
            {
            LoginMessage loginMessage = new LoginMessage();
            loginMessage.setToken(token);
            loginMessage.setAccountId(accountId);
            InAppApplication.getInstance().getTransactionFlow().userLogin(loginMessage, callback);
            }
        } catch (Exception e) {
            LogUtil.printError(e);
        }
    }

    /**
     * 验证账户是否登陆
     */
    public static EAccountStatus getAccountStatus() {
        if (InAppApplication.getInstance().getSession().getStatus() == IAppSession.STATUS_USER_LOGIN) {
            return EAccountStatus.Online;
        }

        if (InAppApplication.getInstance().getSession().getSessionInfo().isValidUserSession()) {
            return EAccountStatus.Offline;
        }

        return EAccountStatus.NotLogin;
    }

    public static void logout(IMessageCallback callback) {
        try {
            if (!isInitialized()) {
                mCallback.onFail(callback, ErrorCode.NotInitialize.getCode());
                return;
            }
            if (!NetworkUtil.isNetworkConnected(InAppApplication.getInstance().getContext())) {
                mCallback.onFail(callback, ErrorCode.Network_Error.getCode());
            }else
            {
            	InAppApplication.getInstance().getTransactionFlow().userLogout(callback);
            }
        } catch (Exception e) {
            LogUtil.printError(e);
        }
    }

    /**
     * Send message.
     */
    public static void send(BinaryMessage message, IMessageCallback callback) {
        try {
            if (!isInitialized()) {
                mCallback.onFail(callback, ErrorCode.NotInitialize.getCode());
                return;
            }
           
            /*if (!NetworkUtil.isNetworkConnected(InAppApplication.getInstance().getContext())) {
                mCallback.onFail(callback, ErrorCode.Network_Error.getCode());
            }*/
            if (message.getData() == null) {
                mCallback.onFail(callback, ErrorCode.WrongParams.getCode());
            }
            if (TextUtils.isEmpty(message.getMethodName()) && !TextUtils.isEmpty(message.getServiceName())) {
                mCallback.onFail(callback, ErrorCode.WrongParams.getCode());
            } else if (!TextUtils.isEmpty(message.getMethodName()) && TextUtils.isEmpty(message.getServiceName())) {
                mCallback.onFail(callback, ErrorCode.WrongParams.getCode());
            } else {
            	InAppApplication.getInstance().getTransactionFlow().sendMessage(message, callback);
            }
        } catch (Exception e) {
            LogUtil.printError(e);
        }
    }

    /**
     * 通过message id获取message。
     */
    public static ImMessage getMessage(String messageId) {
        try {
            if (!isInitialized()) {
                return null;
            }
            return InAppApplication.getInstance().getMessageCenter().getAndRemoveReceivedMessage(messageId);
        } catch (Exception e) {
            LogUtil.printError(e);
        }
        return null;
    }

    /**
     * 销毁SDK实例。
     */
    public static void destroy() {
        try {
            InAppApplication.getInstance().destroy();
            broadcastFilter = null;
        } catch (Exception e) {
            LogUtil.printError(e);
        }
    }

    private static boolean isInitialized() {

        // 没有初始化
        if (TextUtils.isEmpty(broadcastFilter)) {
            return false;
        }
        return true;
    }

    /**
     * callback回调
     * 
     * @param transactionId
     * @param processorCode
     */
    public static void callback(int transactionId, ProcessorResult processorResult) {

        try {
            if (transactionId == 0) {
                return;
            }
            InAppApplication.getInstance().getTransactionFlow().removeTransactionId(transactionId);
            SendingMessage sendingMessage =
                    InAppApplication.getInstance().getMessageCenter().removeSendingMessage(transactionId);

            if (sendingMessage == null) {
                LogUtil.e(TAG, "sending message is nil");
            }

            if (sendingMessage != null && sendingMessage.callback == null) {
                LogUtil.e(TAG, "send msg callback is null");
            }
            if (sendingMessage != null && sendingMessage.callback != null) {
                // LogUtil.e(TAG, "sendMessege is not nil");
                switch (processorResult.getProcessorCode()) {
                    case SUCCESS:
                    case NO_SESSION_ID: // App logout; User logout
                    case NO_APP_ID: // App Logout; UnReg App
                    case EMPTY_PUSH:
                        mCallback.onSuccess(sendingMessage.callback, processorResult.getProcessorCode().getMsg(),
                                processorResult.getData());
                        break;
                    case NO_BDUSS: // User Login;
                    case NO_SESSION_ID_FAILURE: // Receive Msg; Send Msg
                    case NO_API_KEY: // Reg App
                    case NO_FINGER_PRINT: // Reg App
                    case NO_APP_STATUS: // Set App Status
                    case SEND_TIME_OUT: // every case
                    case PARAM_ERROR: // every case
                    case TOKEN_ERROR: // every case
                    case INVALID_APIKEY_SECRET_KEY:
                    case UNREGISTERED_APP: // App Login; User Login
                    case SESSION_ERROR: // every case
                    case SERVER_ERROR: // every case
                    case UNKNOWN_ERROR: // every case
                    default:
                        mCallback.onFail(sendingMessage.callback, processorResult.getProcessorCode().getCode());
                        break;
                }
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e);
        }
    }

    public static boolean upgrade() {
        return UpgradeManager.upgrade();
    }
}
