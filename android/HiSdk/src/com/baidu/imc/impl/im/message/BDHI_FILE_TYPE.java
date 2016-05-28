package com.baidu.imc.impl.im.message;

public enum BDHI_FILE_TYPE {

    FILE("file"),

    IMAGE("image"),

    VOICE("voice");

    String name;

    BDHI_FILE_TYPE(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BDHI_FILE_TYPE parse(String name) {
        BDHI_FILE_TYPE[] fileTypes = BDHI_FILE_TYPE.values();
        if (null != fileTypes) {
            for (BDHI_FILE_TYPE fileType : fileTypes) {
                if (fileType.getName().equals(name)) {
                    return fileType;
                }
            }
        }
        return FILE;
    }
}
