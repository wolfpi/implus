package com.baidu.imc.impl.im.store;

import java.util.List;

import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.NotificationType;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Enum 用名字保存,所以 proguard 时要确认不要被proguard掉
 * Created by gerald on 3/25/16.
 */
public class ChatSettingDBUtil extends DataBaseUtil<ChatSetting> {

    private static final String TAG = "ChatSettingDBUtil";
    private static ChatSettingDBUtil dbUtil;

    public ChatSettingDBUtil(Context context) {
        super(context);
    }

    public ChatSettingDBUtil(Context context, String databaseName) {
        super(context, databaseName);
    }

    public static ChatSettingDBUtil getDB(Context context, String databaseName) {
        if (null != context && null != databaseName && databaseName.length() > 0) {
            String dbName = databaseName + "_" + TAG;
            dbUtil = (ChatSettingDBUtil) dbUtilPool.get(dbName);
            synchronized(ChatSettingDBUtil.class) {
                if (null == dbUtil) {
                    dbUtil = new ChatSettingDBUtil(context, databaseName);
                    dbUtilPool.put(dbName, dbUtil);
                } else {
                    dbHelper = (UserSQLHelper) dbHelperPool.get(databaseName);
                }
            }
            return dbUtil;
        } else {
            return null;
        }
    }

    @Override
    protected String getDBTagName() {
        return TAG;
    }

    @Override
    protected ContentValues getContentValues(ChatSetting value) {
        if(value == null){
            return null;
        }
        ContentValues values = new ContentValues();
        values.put(ChatSettingMetaData.CHAT_TYPE, value.getChatType().name());
        values.put(ChatSettingMetaData.TARGET_ID, value.getTargetID());
        values.put(ChatSettingMetaData.RECEIVE_MODE, value.getReceiveMode().name());
        values.put(ChatSettingMetaData.LAST_UPDATE, value.getLastUpdate());
        return values;
    }

    @Override
    protected ChatSetting create(Cursor cursor) {
        if(cursor == null){
            return null;
        }
        ChatSetting model = new ChatSetting();
        model.setId(cursor.getLong(cursor.getColumnIndex(ChatSettingMetaData.ID)));
        model.setChatType(
                AddresseeType.valueOf(cursor.getString(cursor.getColumnIndex(ChatSettingMetaData.CHAT_TYPE))));
        model.setTargetID(cursor.getString(cursor.getColumnIndex(ChatSettingMetaData.TARGET_ID)));
        model.setReceiveMode(
                NotificationType.valueOf(cursor.getString(cursor.getColumnIndex(ChatSettingMetaData.RECEIVE_MODE))));
        model.setLastUpdate(cursor.getLong(cursor.getColumnIndex(ChatSettingMetaData.LAST_UPDATE)));
        return model;
    }

    @Override
    protected String[] getQueryKeyList() {
        return new String[] {ChatSettingMetaData.ID, ChatSettingMetaData.CHAT_TYPE, ChatSettingMetaData.TARGET_ID, ChatSettingMetaData
                .RECEIVE_MODE, ChatSettingMetaData.LAST_UPDATE};
    }

    @Override
    protected String getTableName() {
        return ChatSettingMetaData.TABLE_NAME;
    }

    public ChatSetting getChatSetting(AddresseeType addresseeType, String addresseeID) {
        if(addresseeID == null){
            return null;
        }
        return get(ChatSettingMetaData.CHAT_TYPE + "=? AND " + ChatSettingMetaData.TARGET_ID + " = ?",
                new String[] {addresseeType.name(), addresseeID});
    }

    public int insertOrUpdateSettings(List<ChatSetting> settings) {
        if(settings == null){
            return 0;
        }

        int count = 0;
        for (ChatSetting setting : settings) {
            ChatSetting oldSettings =
                    get(ChatSettingMetaData.CHAT_TYPE + " =? AND " + ChatSettingMetaData.TARGET_ID + " =?",
                            new String[] {"" + setting.getChatType(), setting.getTargetID()});
            if(oldSettings != null){
                // exist oldSetting, update
                final boolean modeNotChanged = setting.getReceiveMode() == oldSettings.getReceiveMode();

                if(update(setting, ChatSettingMetaData.ID, oldSettings.getId())){
                    if(!modeNotChanged) {
                        count++;
                    }
                }
            }else{
                long newId = insert(setting);
                setting.setId(newId);
                count++;
            }
        }
        return count;
    }
}
