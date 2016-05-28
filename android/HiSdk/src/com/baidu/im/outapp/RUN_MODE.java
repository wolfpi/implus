package com.baidu.im.outapp;

/**
 * 运行模式，通过运行模式切换HiChannel的配置文件。
 * 
 * @author liubinzhe
 */
public enum RUN_MODE {

    PRODUCT("PRODUCT"),

    DEVELOP("DEVELOP");

    private String modeName;

    RUN_MODE(String modeName) {
        this.modeName = modeName;
    }

    protected String getModeName() {
        return modeName;
    }

    public static RUN_MODE parse(String modeName) {
        RUN_MODE[] modes = RUN_MODE.values();
        for (RUN_MODE mode : modes) {
            if (mode.getModeName().equalsIgnoreCase(modeName)) {
                return mode;
            }
        }
        return null;
    }

}
