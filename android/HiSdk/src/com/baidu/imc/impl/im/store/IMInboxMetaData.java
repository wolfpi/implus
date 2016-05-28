package com.baidu.imc.impl.im.store;

public class IMInboxMetaData {

    public static final String TABLE_NAME = "im_inbox";

    /**
     * 本地会话ID
     */
    public static final String ID = "_id";

    /** ----------------------- **/

    /**
     * 最后一条已读消息ID
     */
    public static final String LAST_READ_MSG_ID = "lastReadMessageID";

    /**
     * 最后一条已读消息时间
     */
    public static final String LAST_READ_MSG_TIME = "lastReadMessageTime";

    /**
     * 最后一条收到消息ID
     */
    public static final String LAST_RECV_MSG_ID = "lastReceiveMessageID";

    /**
     * 最后一条收到消息时间
     */
    public static final String LAST_RECV_MSG_TIME = "lastReceiveMessageTime";

    public static final String UNREAD_COUNT = "unreadCount";

    /** ----------------------- **/

    /**
     * 收件人类型
     */
    public static final String ADDRESSEE_TYPE = "addresseeType";

    /**
     * 收件人ID
     */
    public static final String ADDRESSEE_ID = "addresseeID";

    /**
     * 收件人名称
     */
    public static final String ADDRESSEE_NAME = "addresseeName";

    /**
     * 最后一条消息对象
     */
    public static final String MSG_BODY = "msgBody";

    /**
     * 是否有效
     */
    public static final String INEFFECTIVE = "ineffective";

    public static final String EXT0 = "ext0";
    public static final String EXT1 = "ext1";
    public static final String EXT2 = "ext2";
    public static final String EXT3 = "ext3";
    public static final String EXT4 = "ext4";

    /** sql **/

    /**
     * Create Table
     */
    public static final String CREATE_IMINBOX_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + LAST_READ_MSG_ID + " LONG, " + LAST_READ_MSG_TIME
            + " LONG, " + LAST_RECV_MSG_ID + " LONG, " + LAST_RECV_MSG_TIME + " LONG, " + UNREAD_COUNT
            + " INTEGER NOT NULL, " + ADDRESSEE_TYPE + " TEXT NOT NULL, " + ADDRESSEE_ID + " TEXT NOT NULL, "
            + ADDRESSEE_NAME + " TEXT, " + MSG_BODY + " BLOB, " + INEFFECTIVE + " INTEGER, " + EXT0 + " TEXT, " + EXT1
            + " TEXT, " + EXT2 + " TEXT, " + EXT3 + " TEXT, " + EXT4 + " TEXT)";

    /**
     * Remove Table
     */
    public static final String REMOVE_INBOX_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
