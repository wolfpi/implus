package com.baidu.im.constant;

public enum ErrorCode {

    Ok(0, "ok"),

    NotInitialize(-1, "not initialized."),

    WrongParams(-2, "wrong params"),

    Timeout(-3, "time out"),

    Network_Error(-4, "network error"),

    BdussAuthorizationFailed(-5, "authorization failed, please login first."),

    WrongUsernameOrPassword(-6, "wrong username or password"),

    WrongAppKey(-8, "App key error."),

    FormatError(-9, "format error"),

    wrongRpcName(520, "Wrong service name or method name."),

    Unknown(999, "Unknown error");

    private int code;
    private String descriptoin;

    private ErrorCode(int code, String description) {
        this.code = code;
        this.descriptoin = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescriptoin() {
        return descriptoin;
    }
}
