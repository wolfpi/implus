package com.baidu.im.frame.utils;

import com.baidu.hi.plugin.logcenter.LogCenterLevel;

public enum LogLevel {
    // include ERROR
    ERROR(LogCenterLevel.ERROR),
    // include ERROR, PROTOCOL
    PROTOCOL(LogCenterLevel.WARN.getLevel(), "protocol", LogCenterLevel.WARN.getTag()),
    // include ERROR, PROTOCOL, MAINPROGRESS
    MAINPROGRESS(LogCenterLevel.INFO.getLevel(), "mainprogress", LogCenterLevel.INFO.getTag()),
    // include ERROR, PROTOCOL, MAINPROGRESS, DEBUG
    DEBUG(LogCenterLevel.DEBUG),
    // include ERROR, WARN
    WARN(LogCenterLevel.WARN),
    // include ERROR, WARN, INFO
    INFO(LogCenterLevel.INFO);

    private int level;

    private String name;

    private String tag;

    private LogCenterLevel logCenterLevel;

    LogLevel(int level, String name, String tag) {
        this.level = level;
        this.name = name;
        this.tag = tag;
        this.logCenterLevel = LogCenterLevel.parse(level);
    }

    LogLevel(LogCenterLevel level) {
        this.level = level.getLevel();
        this.name = level.getName();
        this.tag = level.getTag();
        this.logCenterLevel = level;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public LogCenterLevel getLogCenterLevel() {
        return logCenterLevel;
    }
}
