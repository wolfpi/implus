package com.baidu.im.sdk;

public enum ProgressEnum {

    /**
     * Login
     */
    Login_Start(0, "开始登陆"),

    Login_Convert_Message_Success(10, "Convert message success."),

    Login_Passport_Auth_Success(30, "成功取到bduss."),

    Login_Connect_HiCoreService_Success(40, "Success to connect to HiCore service."),

    RegAppSuccess(50, "注册App成功"),
    
    LoginUserSuccess(80, "用户登陆成功"),

    HeartbeatSuccess(90, "第一个心跳发送成功"),
    
    Login_Success(100, "Login success."),

    /**
     * Text Message
     */
    TextMessage_Start(0, "Start the bussiness."),

    TextMessage_Convert_Message_Success(10, "Convert message success."),

    TextMessage_Send_To_Server(20, "Success to connect to HiCore service."),

    TextMessage_Receive_Ack_From_Server(80, "Success to connect to HiCore service."),

    TextMessage_Save_Message(90, "Success to save message in message center."),

    TextMessage_Success(100, "Send success."),

    /**
     * Common
     */
    Finish(100, "finish!");

    private int progress;
    private String description;

    private ProgressEnum(int progress, String description) {
        this.progress = progress;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getProgress() {
        return progress;
    }
}
