package com.baidu.im.frame;

import com.baidu.im.frame.utils.LogUtil;

public enum ProcessorCode {

    SUCCESS(0, "Success."),

    USER_LOGOUT_NOTIFY(1, "User logout notify."),

    // local error
    NO_BDUSS(-1001, "No bduss"),

    NO_CHANNEL_KEY(-1002, "No channel key"),

    NO_SESSION_ID(-1003, "No session_id"),

    NO_SESSION_ID_FAILURE(-1003, "No session_id"),

    NO_API_KEY(-1004, "No api key"),

    NO_FINGER_PRINT(-1005, "No finger print"),

    NO_APP_ID(-1006, "No app id"),

    NO_APP_STATUS(-1007, "No app status"),

    SEND_TIME_OUT(-1011, "Time out. Do not receive response from server."),

    EMPTY_PUSH(-1021, "Receive a empty push. Do not have any message."),

    // parameter error
    PARAM_ERROR(-1110, "Parameter error"),

    TOKEN_ERROR(-1111, "Token error. eg: bduss does not exist or is overdue."),

    INVALID_APIKEY_SECRET_KEY(-1112, "Invalid api key or invalid secret key"),

    UNREGISTERED_APP(-1113, "App has not been registered on server."),

    // session error
    SESSION_ERROR(-1120, "Session error"),

    SESSION_OTHER_ERROR(-1121, "Session other error"),

    // server error
    SERVER_ERROR(-1190, "Server error"),

    // config error
    CONFIG_UPDATE_ERROR(-1200, "Can not get config from server."),

    // channel_server_error
    CHANNEL_SERVER_ERROR(-1210, "Channel error"),

    CHANNEL_DISPATCH_ERROR(-1211, "Channel Wrong service name or method name."),

    // unknown error
    UNKNOWN_ERROR(-999, "Unknown error.");

    private int code;
    private String msg;

    private ProcessorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ProcessorCode parse(int code) {
        try {
            ProcessorCode[] processorCodes = ProcessorCode.values();
            int length = processorCodes.length;
            for (int i = 0; i < length; i++) {
                ProcessorCode bizCode = processorCodes[i];
                if (null != bizCode && bizCode.getCode() == code) {
                    return bizCode;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            LogUtil.printError("fError parse processor code error. code = " + code, e);
        }
        return UNKNOWN_ERROR;
    }
}
