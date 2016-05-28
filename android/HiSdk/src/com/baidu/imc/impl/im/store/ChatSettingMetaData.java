package com.baidu.imc.impl.im.store;

/**
 * Created by gerald on 3/25/16.
 */
public interface ChatSettingMetaData {

    String TABLE_NAME = "chat_setting";
    String ID = "_id";
    String CHAT_TYPE = "chatType"; //会话类型
    String TARGET_ID = "targetID"; //
    String RECEIVE_MODE = "receiveMode";
    String LAST_UPDATE = "lastUpdate";

    String EXT0 = "ext0";
    String EXT1 = "ext1";
    String EXT2 = "ext2";
    String EXT3 = "ext3";
    String EXT4 = "ext4";

    /**
     * Create table
     */
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + CHAT_TYPE + " TEXT NOT NULL, " + TARGET_ID
            + " TEXT NOT NULL, " + RECEIVE_MODE + " INTEGER, " + LAST_UPDATE + " LONG, "+ EXT0 + " TEXT, " + EXT1
            + " TEXT, " + EXT2 + " TEXT, " + EXT3 + " TEXT, " + EXT4 + " TEXT)";

    /**
     * Remove table
     */
    public static final String REMOVE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
