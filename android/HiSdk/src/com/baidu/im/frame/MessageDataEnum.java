package com.baidu.im.frame;

/**
 * 内部进程间通信消息数据类型的枚举，
 * 
 * @author zhaowei10
 * 
 */
public enum MessageDataEnum {

    NORMAL("0"),

    NETWORK_CHANGE("1"),
   
    NETWORK_CHECK("2"),

    CHANNELKEYRECEIVE("3"),
    
    SYSTEM("9"),
    
    APPKEY("1000"),
    APPID("1001"),
    
    ALIVE("1002"),
    Reconnect("1003"),
    SEQFETCH("1004"),
    CHECKOFFLINE("1005");

    String type;

    private MessageDataEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
