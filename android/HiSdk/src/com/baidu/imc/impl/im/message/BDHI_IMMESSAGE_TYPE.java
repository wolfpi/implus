package com.baidu.imc.impl.im.message;

public enum BDHI_IMMESSAGE_TYPE {

    TEXT("bdim_hi_text"),

    FILE("bdim_hi_file"),

    IMAGE("bdim_hi_image"),

    VOICE("bdim_hi_voice"),

    CUSTOM("bdim_hi_custom");

    private String name;

    public String getName() {
        return name;
    }

    BDHI_IMMESSAGE_TYPE(String name) {
        this.name = name;
    }

    public static BDHI_IMMESSAGE_TYPE parse(String name) {
        BDHI_IMMESSAGE_TYPE[] types = BDHI_IMMESSAGE_TYPE.values();
        if (null != types) {
            for (BDHI_IMMESSAGE_TYPE type : types) {
                if (type.getName().equals(name)) {
                    return type;
                }
            }
        }
        return CUSTOM;
    }
}
