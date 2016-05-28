package com.baidu.imc.impl.im.message;

public enum BDHI_MESSAGE_CONTENT_ID {

    TEXT("text_"),

    URL("url_"),

    FILE("file"),

    IMAGE("image"),

    THUMBNAIL("thumbnail"),

    VOICE("voice");

    String name;

    public String getName() {
        return name;
    }

    BDHI_MESSAGE_CONTENT_ID(String name) {
        this.name = name;
    }

    public static BDHI_MESSAGE_CONTENT_ID parse(String name) {
        BDHI_MESSAGE_CONTENT_ID[] messageIDs = BDHI_MESSAGE_CONTENT_ID.values();
        if (null != messageIDs) {
            for (BDHI_MESSAGE_CONTENT_ID messageID : messageIDs) {
                if (messageID.getName().equals(name)) {
                    return messageID;
                }
            }
        }
        return TEXT;
    }
}
