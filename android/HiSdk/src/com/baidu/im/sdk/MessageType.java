package com.baidu.im.sdk;

/**
 * 枚举消息的种类。
 * 
 * @author zhaowei10
 * 
 */
public enum MessageType {

    Login(1),

    Binary(2),

    Customize(9),

    Text(21),

    Image(22),

    Voice(23),

    Video(30),

    System(999);

    private int id;

    private MessageType(int id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    public static MessageType valueOf(int id) {
        for (MessageType type : MessageType.values()) {
            if (id == type.getId()) {
                return type;
            }
        }
        return null;
    }
}
