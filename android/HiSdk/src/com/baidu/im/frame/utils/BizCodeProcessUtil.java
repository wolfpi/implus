package com.baidu.im.frame.utils;

import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;

public class BizCodeProcessUtil {

    public static ProcessorCode procProcessorCode(String processorName, DownPacket downPacket) {
    	
    	if(processorName == null)
    		processorName = "";
    	
        if (downPacket != null) {
            // Return channelCode immediately if not success.
            ChannelCode channelCode = ChannelCode.parse(downPacket.getChannelCode());
            ProcessorCode channelResult = procChannelCode(channelCode);
            if (channelResult != ProcessorCode.SUCCESS) {
                LogUtil.printMainProcess(processorName, channelResult.getMsg());
                return channelResult;
            }

            if (downPacket.getBizPackage() != null) {
                ProcessorCode bizResult = procBizCode(downPacket.getBizPackage().getBizCode());
                LogUtil.printMainProcess(processorName, bizResult.getMsg());
                return bizResult;
            }
        } else {
            LogUtil.printMainProcess(processorName + " null downPacket or null bizPacket",
                    ProcessorCode.UNKNOWN_ERROR.getMsg());
        }
        return ProcessorCode.UNKNOWN_ERROR;
    }

    private static ProcessorCode procBizCode(int code) {
        BizCode bizCode = BizCode.parse(code);
        switch (bizCode) {
            case SESSION_SUCCESS:
                return ProcessorCode.SUCCESS;
            case SESSION_PARAM_ERROR:
            case SESSION_CLIENT_ERROR_4:
            case SESSION_CLIENT_ERROR_5:
            case SESSION_CLIENT_ERROR_6:
            case SESSION_CLIENT_ERROR_7:
            case SESSION_CLIENT_ERROR_8:
            case SESSION_CLIENT_ERROR_9:
                return ProcessorCode.PARAM_ERROR;
            case SESSION_TOKEN_NOT_EXIST:
                return ProcessorCode.TOKEN_ERROR;
            case SESSION_INVALID_APIKEY_SECRET_KEY:
                return ProcessorCode.INVALID_APIKEY_SECRET_KEY;
            case SESSION_APP_NOT_EXIST:
                return ProcessorCode.UNREGISTERED_APP;
            case SESSION_SESSION_NOT_EXIST:
                // TODO re connect
                return ProcessorCode.SESSION_ERROR;
            case SESSION_STATUS_ERROR1:
            case SESSION_STATUS_ERROR2:
            case SESSION_STATUS_ERROR3:
            case SESSION_STATUS_ERROR4:
            case SESSION_STATUS_ERROR5:
            case SESSION_STATUS_ERROR6:
            case SESSION_STATUS_ERROR7:
            case SESSION_STATUS_ERROR8:
            case SESSION_STATUS_ERROR9:
                return ProcessorCode.SESSION_OTHER_ERROR;
            case SESSION_UNKNOWN_SERVER_ERROR:
            case SESSION_UNKNOWN_SERVER_ERROR1:
            case SESSION_UNKNOWN_SERVER_ERROR2:
            case SESSION_UNKNOWN_SERVER_ERROR3:
            case SESSION_UNKNOWN_SERVER_ERROR4:
            case SESSION_UNKNOWN_SERVER_ERROR5:
            case SESSION_UNKNOWN_SERVER_ERROR6:
            case SESSION_UNKNOWN_SERVER_ERROR7:
            case SESSION_UNKNOWN_SERVER_ERROR8:
            case SESSION_UNKNOWN_SERVER_ERROR9:
                return ProcessorCode.SERVER_ERROR;
            case CONFIG_PARAM_ERROR:
            case CONFIG_JSON_PARSE_ERROR:
            case CONFIG_NO_PLATFORMTYPE:
                return ProcessorCode.CONFIG_UPDATE_ERROR;
            default:
                return ProcessorCode.UNKNOWN_ERROR;
        }
    }

    public static ProcessorCode procChannelCode(ChannelCode code) {

    	if(code == null)
    		return ProcessorCode.CHANNEL_SERVER_ERROR;
    	
        switch (code) {
            case CHANNEL_AUTHENTICATION_ERROR:
                return ProcessorCode.SESSION_ERROR;
            case CHANNEL_DISPATCH_ERROR:
                return ProcessorCode.CHANNEL_DISPATCH_ERROR;
            case CHANNEL_SUCCESS:
                return ProcessorCode.SUCCESS;
            default:
                return ProcessorCode.CHANNEL_SERVER_ERROR;
        }
    }

    public static int processBizCode(int code) {
        return procBizCode(code).getCode();
    }

    public enum ChannelCode {

        CHANNEL_SUCCESS(200, "Channel success"),

        CHANNEL_AUTHENTICATION_ERROR(400, "Authentication error."),

        CHANNEL_DISPATCH_ERROR(520, "Wrong service name or method name."),

        // unknown error
        CHANNEL_UNKNOWN_ERROR(-1, "CHANNEL_UNKNOWN_ERROR");

        private int code;
        private String msg;

        ChannelCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public static ChannelCode parse(int code) {
            ChannelCode[] channelCodes = ChannelCode.values();
            if (null != channelCodes) {
                for (ChannelCode channelCode : channelCodes) {
                    if (channelCode.getCode() == code) {
                        return channelCode;
                    }
                }
            }
            return ChannelCode.CHANNEL_UNKNOWN_ERROR;
        }
    }

    public enum BizCode {

        SESSION_SUCCESS(0, "SESSION_SUCCESS"),

        // client error，client上传的request中有错误 10100 ~ 10109
        SESSION_PARAM_ERROR(10100, "request协议中字段缺失或不正确"),

        SESSION_TOKEN_NOT_EXIST(10101, "鉴权token不存在，例如passport的bduss不存在"),

        SESSION_INVALID_APIKEY_SECRET_KEY(10102, "不正确的Apikey或者secret key"),

        SESSION_APP_NOT_EXIST(10103, "APP尚未注册"),

        SESSION_CLIENT_ERROR_4(10104, "SESSION_CLIENT_ERROR_4"),

        SESSION_CLIENT_ERROR_5(10105, "SESSION_CLIENT_ERROR_5"),

        SESSION_CLIENT_ERROR_6(10106, "SESSION_CLIENT_ERROR_6"),

        SESSION_CLIENT_ERROR_7(10107, "SESSION_CLIENT_ERROR_7"),

        SESSION_CLIENT_ERROR_8(10108, "SESSION_CLIENT_ERROR_8"),

        SESSION_CLIENT_ERROR_9(10109, "SESSION_CLIENT_ERROR_9"),

        // 登录状态错误，需要重新登录 10120 ~ 10129
        SESSION_SESSION_NOT_EXIST(10120, "session不存在"),

        SESSION_STATUS_ERROR1(10121, "SESSION_STATUS_ERROR1"),

        SESSION_STATUS_ERROR2(10122, "SESSION_STATUS_ERROR2"),

        SESSION_STATUS_ERROR3(10123, "SESSION_STATUS_ERROR3"),

        SESSION_STATUS_ERROR4(10124, "SESSION_STATUS_ERROR4"),

        SESSION_STATUS_ERROR5(10125, "SESSION_STATUS_ERROR5"),

        SESSION_STATUS_ERROR6(10126, "SESSION_STATUS_ERROR6"),

        SESSION_STATUS_ERROR7(10127, "SESSION_STATUS_ERROR7"),

        SESSION_STATUS_ERROR8(10128, "SESSION_STATUS_ERROR8"),

        SESSION_STATUS_ERROR9(10129, "SESSION_STATUS_ERROR9"),

        // 未知的server错误 10190 ~ 10199
        SESSION_UNKNOWN_SERVER_ERROR(10190, "SESSION_UNKNOWN_SERVER_ERROR"),

        SESSION_UNKNOWN_SERVER_ERROR1(10191, "SESSION_UNKNOWN_SERVER_ERROR1"),

        SESSION_UNKNOWN_SERVER_ERROR2(10192, "SESSION_UNKNOWN_SERVER_ERROR2"),

        SESSION_UNKNOWN_SERVER_ERROR3(10193, "SESSION_UNKNOWN_SERVER_ERROR3"),

        SESSION_UNKNOWN_SERVER_ERROR4(10194, "SESSION_UNKNOWN_SERVER_ERROR4"),

        SESSION_UNKNOWN_SERVER_ERROR5(10195, "SESSION_UNKNOWN_SERVER_ERROR5"),

        SESSION_UNKNOWN_SERVER_ERROR6(10196, "SESSION_UNKNOWN_SERVER_ERROR6"),

        SESSION_UNKNOWN_SERVER_ERROR7(10197, "SESSION_UNKNOWN_SERVER_ERROR7"),

        SESSION_UNKNOWN_SERVER_ERROR8(10198, "SESSION_UNKNOWN_SERVER_ERROR8"),

        SESSION_UNKNOWN_SERVER_ERROR9(10199, "SESSION_UNKNOWN_SERVER_ERROR9"),

        CONFIG_PARAM_ERROR(10601, "CONFIG_PARAM_ERROR"),

        CONFIG_JSON_PARSE_ERROR(10602, "CONFIG_JSON_PARSE_ERROR"),

        CONFIG_NO_PLATFORMTYPE(10603, "CONFIG_NO_PLATFORMTYPE"),

        // unknown error
        SESSION_UNKNOWN_ERROR(-1, "SESSION_UNKNOWN_ERROR");

        private int code;
        private String msg;

        BizCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public static BizCode parse2(int code) {
            try {
                if (code == 0) { // 0
                    return BizCode.values()[0];
                } else if (code >= 10100 && code <= 10109) { // 1 ~ 10
                    return BizCode.values()[1 + code - 10100];
                } else if (code >= 10120 && code <= 10129) { // 11 ~ 20
                    return BizCode.values()[11 + code - 10120];
                } else if (code >= 10190 && code <= 10199) { // 21 ~ 30
                    return BizCode.values()[21 + code - 10190];
                } else { // 31
                    return BizCode.values()[31];
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                LogUtil.printError("fError parse error. code = " + code, e);
            }
            return SESSION_UNKNOWN_ERROR;
        }

        public static BizCode parse(int code) {
            try {
                BizCode[] bizCodes = BizCode.values();
                int length = bizCodes.length;
                for (int i = 0; i < length; i++) {
                    BizCode bizCode = bizCodes[i];
                    if (null != bizCode && bizCode.getCode() == code) {
                        return bizCode;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                LogUtil.printError("fError parse biz code error. code = " + code, e);
            }
            return SESSION_UNKNOWN_ERROR;
        }
    }
}
