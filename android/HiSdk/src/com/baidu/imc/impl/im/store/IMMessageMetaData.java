package com.baidu.imc.impl.im.store;

public class IMMessageMetaData {

    public static final String TABLE_NAME = "im_msg";

    /**
     * 本地消息ID
     */
    public static final String ID = "_id";

    /** addressee **/

    /**
     * 收件人ID
     */
    public static final String ADDRESSEE_ID = "addresseeID";

    /**
     * 收件人类型
     */
    public static final String ADDRESSEE_TYPE = "addresseeType";

    /**
     * 收件人名称
     */
    public static final String ADDRESSEE_NAME = "addresseeName";

    /** addresser **/

    /**
     * 发件人ID
     */
    public static final String ADDRESSER_ID = "addresserID";

    /**
     * 发件人名称
     */
    public static final String ADDRESSER_NAME = "addresserName";

    /** msg **/

    /**
     * 消息ID
     */
    public static final String MSG_SEQ = "msgSeq";

    /**
     * 本地消息ID
     */
    public static final String CLIENT_MSG_ID = "cmsgId";

    /**
     * 消息类型
     */
    public static final String MSG_TYPE = "msgType";

    /**
     * 当前消息状态(optional)
     */
    public static final String MSG_STATUS = "msgStatus";

    /**
     * 发送时间
     */
    public static final String SEND_TIME = "msgSendTime";

    /**
     * 服务器时间
     */
    public static final String SERVER_TIME = "msgServerTime";

    /**
     * 兼容消息(optional)
     */
    public static final String MSG_COMPATIBLE_TEXT = "compatibleText";

    /**
     * 通知消息(optional)
     */
    public static final String MSG_NOTIFICATION_TEXT = "notificationText";

    /**
     * 附件属性(optional)
     */
    public static final String MSG_EXTRA = "msgExtra";

    /**
     * 消息体(optional)
     */
    public static final String MSG_BODY = "msgBody";

    /**
     * 消息界面(optional)
     */
    public static final String MSG_VIEW = "msgView";

    /**
     * 消息模版(optional)
     */
    public static final String MSG_TEMPLATE = "msgTemplate";

    /**
     * 前一条消息MessageID
     */
    public static final String PREVIOUS_MSG_ID = "prevMsgId";

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
     * Create table
     *
     * 从版本 4 后 去掉了非索引列的 not null 约束
     */
    public static final String CREATE_IMMESSAGE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + ADDRESSEE_ID + " TEXT NOT NULL, " + ADDRESSEE_TYPE
            + " TEXT NOT NULL, " + ADDRESSEE_NAME + " TEXT, " + ADDRESSER_ID + " TEXT NOT NULL, " + ADDRESSER_NAME
            + " TEXT, " + MSG_SEQ + " LONG, " + CLIENT_MSG_ID + " LONG, " + MSG_TYPE
            + " TEXT, " + MSG_STATUS + " TEXT, " + SEND_TIME + " LONG, " + SERVER_TIME + " LONG, "
            + MSG_COMPATIBLE_TEXT + " TEXT, " + MSG_NOTIFICATION_TEXT + " TEXT, " + MSG_EXTRA + " TEXT, " + MSG_BODY
            + " BLOB, " + MSG_VIEW + " TEXT, " + MSG_TEMPLATE + " TEXT, " + PREVIOUS_MSG_ID + " LONG, " + INEFFECTIVE
            + " INTEGER, " + EXT0 + " TEXT, " + EXT1 + " TEXT, " + EXT2 + " TEXT, " + EXT3 + " TEXT, " + EXT4
            + " TEXT)";

    /**
     * Remove table
     */
    public static final String REMOVE_IMMESSAGE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * INDEX
     */
    public static final String UNIQUE_INDEX_NAME = "key_idx";

    public static final java.lang.String CREATE_INDEX = "CREATE UNIQUE INDEX " +
            UNIQUE_INDEX_NAME + " ON " + TABLE_NAME +
            " (" +
            ADDRESSEE_TYPE + ", " +
            ADDRESSEE_ID + ", " +
            ADDRESSER_ID + ", " +
            ID +
            ")";
}
