package com.baidu.imc.impl.im.store;

import java.sql.SQLException;

import com.baidu.im.frame.utils.LogUtil;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserSQLHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLHelper";
    static final int DATABASE_VERSION = 4; // history: 4, 3, 2, 1

    private static final String COMMON_DATABASE_NAME = "common";
    private boolean justRecreated = false; // 如果数据库被重建, 则在此进行标记. 之后交给 MemoryMsgStore 等上层处理相关联数据, 如 IMPreference 等

    public UserSQLHelper(Context context) {
        super(context, COMMON_DATABASE_NAME, null, DATABASE_VERSION, mHandler);
    }

    public UserSQLHelper(Context context, String databaseName) {
        super(context, databaseName, null, DATABASE_VERSION, mHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    private void createTables(SQLiteDatabase db) {
        db.execSQL(IMMessageMetaData.CREATE_IMMESSAGE_TABLE);
        db.execSQL(IMInboxMetaData.CREATE_IMINBOX_TABLE);
        createIndex(db);
        db.execSQL(ChatSettingMetaData.CREATE_TABLE);
    }

    private void createIndex(SQLiteDatabase db) {
        LogUtil.i(TAG, "onCreate");
        db.execSQL(IMMessageMetaData.CREATE_INDEX);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.i(TAG, "onUpgrade oldVersion:" + oldVersion + " newVersion:" + newVersion);
        try {
            if (oldVersion < 2) {
                // Create Index from， to，chatType，clientMsgID唯一性索引
                createIndex(db);
                oldVersion = 2;
            }
            if (oldVersion < 3) {
                // 建立 ChatSettingMetaData 库
                db.execSQL(ChatSettingMetaData.CREATE_TABLE);
                oldVersion = 3;
            }
            if(oldVersion < 4){
                // 将 IMMessage 里的 addresser_name 的 not null 约束去掉 (删了重练方案)
                dropTables(db);
                createTables(db);
                oldVersion = 4;
            }
            // INSERT NEW UPGRADE ENTRY HERE
        } catch (android.database.SQLException e) {
            LogUtil.e(TAG, "Exception when upgrade database, recreate tables", e);
            dropTables(db);
            createTables(db);
        }
    }

    private void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + IMInboxMetaData.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + IMMessageMetaData.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ChatSettingMetaData.TABLE_NAME);
        justRecreated = true; // 标记数据库重建
    }

    private SQLiteDatabase db;

    /**
     * open database
     *
     * @return
     *
     * @throws SQLException
     */
    public SQLiteDatabase open() throws SQLException {
        if (null == db || !db.isOpen()) {
            db = getWritableDatabase();
            //db.enableWriteAheadLogging();
        }
        return db;
    }

    public void close() {
        if (null != db && db.isOpen()) {
            try {
                db.close();
            } catch (Exception e) {
                LogUtil.e(TAG, "close-close");
            }
        }
    }

    private static ErrHandler mHandler = new ErrHandler();

    /**
     * 数据库是否刚刚重建 (例如,表被drop). 用来让客户处理老数据相关联的其他数据, 如相关的shared preference
     * @return 老数据库已经被 drop, 返回 true. 否则返回 false
     */
    boolean isJustRecreated() {
        return justRecreated;
    }

    void setJustRecreated(boolean justRecreated) {
        this.justRecreated = justRecreated;
    }

    static class ErrHandler implements DatabaseErrorHandler {
        @Override
        public void onCorruption(SQLiteDatabase dbObj) {
            LogUtil.e(TAG, dbObj.getPath() + " database is corrupted!!!");
        }
    }
}

