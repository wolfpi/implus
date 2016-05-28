package com.baidu.imc.impl.im.protocol;

public enum MethodNameEnum {

    NULL(""),

    QUERY_ACTIVE_CONTACTS("QueryActiveContacts"),

    QUERY_MSGS("QueryMsgs"),

    READ_ACK("ReadAck"),

    SEND("Send"),

    GET_UPLOAD_SIGN("GetUploadSign"),

    UPLOAD_SUCCESS("UploadSuccess"),

    GET_DOWNLOAD_SIGN("GetDownloadSign"),

    // used in IMPlusChatSetting

    GET("Get"),

    SET("Set"),

    QUERY("Query");

    private String name;

    private MethodNameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MethodNameEnum parse(String name) {
        MethodNameEnum[] methodNames = MethodNameEnum.values();
        if (null != methodNames) {
            for (MethodNameEnum methodName : methodNames) {
                if (methodName.getName().equals(name)) {
                    return methodName;
                }
            }
        }
        return NULL;
    }

}
