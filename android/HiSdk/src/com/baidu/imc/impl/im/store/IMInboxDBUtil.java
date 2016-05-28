package com.baidu.imc.impl.im.store;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.baidu.imc.impl.im.message.IMInboxEntryImpl;
import com.baidu.imc.impl.im.message.OneMsgConverter;
import com.baidu.imc.type.AddresseeType;

public class IMInboxDBUtil extends DataBaseUtil<IMInboxEntryImpl> {

    private static final String TAG = "IMInboxDBUtil";
    private static IMInboxDBUtil dbUtil;

    // private static final String IMP_DB_NAME = "imp";

    public IMInboxDBUtil(Context context, String databaseName) {
        super(context, databaseName);
    }

    public static IMInboxDBUtil getDB(Context context, String databaseName) {
        if (null != context && null != databaseName && databaseName.length() > 0) {
            String dbName = databaseName + "_" + TAG;
            dbUtil = (IMInboxDBUtil) dbUtilPool.get(dbName);
            synchronized (IMInboxDBUtil.class) {
                if (null == dbUtil) {
                    dbUtil = new IMInboxDBUtil(context, databaseName);
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
        return IMInboxMetaData.TABLE_NAME;
    }

    @Override
    protected ContentValues getContentValues(IMInboxEntryImpl value) {
        if (value == null)
            return null;
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMInboxMetaData.LAST_READ_MSG_ID, value.getLastReadMessageID());
        contentValues.put(IMInboxMetaData.LAST_READ_MSG_TIME, value.getLastReadMessageTime());
        contentValues.put(IMInboxMetaData.LAST_RECV_MSG_ID, value.getLastReceiveMessageID());
        contentValues.put(IMInboxMetaData.LAST_RECV_MSG_TIME, value.getLastReceiveMessageTime());
        contentValues.put(IMInboxMetaData.UNREAD_COUNT, value.getUnreadCount());
        contentValues.put(IMInboxMetaData.ADDRESSEE_TYPE, value.getAddresseeType().name());
        contentValues.put(IMInboxMetaData.ADDRESSEE_ID, value.getAddresseeID());
        contentValues.put(IMInboxMetaData.ADDRESSEE_NAME, value.getAddresseeName());
        contentValues.put(IMInboxMetaData.MSG_BODY, value.getMsgBody());
        contentValues.put(IMInboxMetaData.INEFFECTIVE, value.isIneffective() ? 1 : 0);
        contentValues.put(IMInboxMetaData.EXT0, value.getExt0());
        contentValues.put(IMInboxMetaData.EXT1, value.getExt1());
        contentValues.put(IMInboxMetaData.EXT2, value.getExt2());
        contentValues.put(IMInboxMetaData.EXT3, value.getExt3());
        contentValues.put(IMInboxMetaData.EXT4, value.getExt4());
        return contentValues;
    }

    @Override
    protected IMInboxEntryImpl create(Cursor cursor) {
        if (cursor == null)
            return null;
        IMInboxEntryImpl inbox = new IMInboxEntryImpl();
        inbox.setLastReadMessageID(cursor.getLong(cursor.getColumnIndex(IMInboxMetaData.LAST_READ_MSG_ID)));
        inbox.setLastReadMessageTime(cursor.getLong(cursor.getColumnIndex(IMInboxMetaData.LAST_READ_MSG_TIME)));
        inbox.setLastReceiveMessageID(cursor.getLong(cursor.getColumnIndex(IMInboxMetaData.LAST_RECV_MSG_ID)));
        inbox.setLastReadMessageTime(cursor.getLong(cursor.getColumnIndex(IMInboxMetaData.LAST_RECV_MSG_TIME)));
        inbox.setUnreadCount(cursor.getInt(cursor.getColumnIndex(IMInboxMetaData.UNREAD_COUNT)));
        inbox.setAddresseeType(AddresseeType.valueOf(cursor.getString(cursor
                .getColumnIndex(IMInboxMetaData.ADDRESSEE_TYPE))));
        inbox.setAddresseeID(cursor.getString(cursor.getColumnIndex(IMInboxMetaData.ADDRESSEE_ID)));
        inbox.setAddresseeName(cursor.getString(cursor.getColumnIndex(IMInboxMetaData.ADDRESSEE_NAME)));
        inbox.setMsgBody(cursor.getBlob(cursor.getColumnIndex(IMInboxMetaData.MSG_BODY)));
        if (null != inbox.getMsgBody() && inbox.getMsgBody().length > 0) {
            inbox.setLastMessage(OneMsgConverter.convertServerMsg(inbox.getMsgBody()));
        }
        inbox.setIneffective(cursor.getInt(cursor.getColumnIndex(IMInboxMetaData.INEFFECTIVE)) == 1 ? true : false);
        inbox.setExt0(cursor.getString(cursor.getColumnIndex(IMInboxMetaData.EXT0)));
        inbox.setExt1(cursor.getString(cursor.getColumnIndex(IMInboxMetaData.EXT1)));
        inbox.setExt2(cursor.getString(cursor.getColumnIndex(IMInboxMetaData.EXT2)));
        inbox.setExt3(cursor.getString(cursor.getColumnIndex(IMInboxMetaData.EXT3)));
        inbox.setExt4(cursor.getString(cursor.getColumnIndex(IMInboxMetaData.EXT4)));
        return inbox;
    }

    @Override
    protected String[] getQueryKeyList() {
        return new String[] { IMInboxMetaData.ID, IMInboxMetaData.LAST_READ_MSG_ID, IMInboxMetaData.LAST_READ_MSG_TIME,
                IMInboxMetaData.LAST_RECV_MSG_ID, IMInboxMetaData.LAST_RECV_MSG_TIME, IMInboxMetaData.UNREAD_COUNT,
                IMInboxMetaData.ADDRESSEE_TYPE, IMInboxMetaData.ADDRESSEE_ID, IMInboxMetaData.ADDRESSEE_NAME,
                IMInboxMetaData.MSG_BODY, IMInboxMetaData.INEFFECTIVE, IMInboxMetaData.EXT0, IMInboxMetaData.EXT1,
                IMInboxMetaData.EXT2, IMInboxMetaData.EXT3, IMInboxMetaData.EXT4 };
    }

    @Override
    protected String getTableName() {
        return IMInboxMetaData.TABLE_NAME;
    }

    /**
     * Get a IMInbox
     * 
     * @param addresseeType
     * @param addresseeID
     * @return IMInboxEntryImpl
     */
    public IMInboxEntryImpl getIMInbox(AddresseeType addresseeType, String addresseeID) {
        if (null != addresseeType && null != addresseeID && addresseeID.length() > 0) {
            String where = IMInboxMetaData.ADDRESSEE_TYPE + "= ? and " + IMInboxMetaData.ADDRESSEE_ID + "=?";
            String[] whereArgs = new String[] { addresseeType.name(), addresseeID };
            IMInboxEntryImpl entry = get(where, whereArgs);
            return entry;
        } else {
            return null;
        }
    }

    /**
     * Get a IMInbox
     * 
     * @param id
     * @return IMInboxEntryImpl
     */
    // public IMInboxEntryImpl getIMInbox(String id) {
    // if (null != id && id.length() > 0) {
    // String where = IMInboxMetaData.ID + "=?";
    // String[] whereArgs = new String[] { id };
    // IMInboxEntryImpl entry = get(where, whereArgs);
    // return entry;
    // } else {
    // return null;
    // }
    // }

    /**
     * Get all IMInboxes
     * 
     * @return List<IMInboxEntryImpl>
     */
    public List<IMInboxEntryImpl> getAllIMInboxEntry() {
        String selection = IMInboxMetaData.INEFFECTIVE + "=?";
        String[] selectionArgs = { Integer.toString(0) };
        return findAllList(selection, selectionArgs, null);
    }

    /**
     * Remove a IMInbox
     * 
     * @param addresseeType
     * @param addresseeID
     * @return boolean
     */
    public boolean removeIMInbox(AddresseeType addresseeType, String addresseeID) {
        if (null != addresseeType && null != addresseeID && addresseeID.length() > 0) {
            String whereClause = IMInboxMetaData.ADDRESSEE_TYPE + "= ? and " + IMInboxMetaData.ADDRESSEE_ID + "=?";
            String[] whereArgs = new String[] { addresseeType.name(), addresseeID };
            ContentValues contentValues = new ContentValues();
            contentValues.put(IMInboxMetaData.INEFFECTIVE, 1);
            // return delete(whereClause, whereArgs);
            return update(contentValues, whereClause, whereArgs);
        } else {
            return false;
        }
    }

    /**
     * Remove a IMInbox
     * 
     * @param id
     * @return boolean
     */
    // public boolean removeIMInbox(String id) {
    // if (null != id && id.length() > 0) {
    // String whereClause = IMInboxMetaData.ID + "=?";
    // String[] whereArgs = new String[] { id };
    // return delete(whereClause, whereArgs);
    // } else {
    // return false;
    // }
    // }

    /**
     * Remove all IMInboxes
     * 
     * @return boolean
     */
    public boolean removeAllImInbox() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMInboxMetaData.INEFFECTIVE, 1);
        return update(contentValues, null, null);
        // return deleteAllFromDB();
    }

    /**
     * Save a IMInbox
     * 
     * @param inbox
     * @return boolean
     */
    public boolean saveIMInbox(IMInboxEntryImpl inbox) {
        if (null != inbox && null != inbox.getAddresseeType() && null != inbox.getAddresseeID()
                && inbox.getAddresseeID().length() > 0) {
            String whereClause = IMInboxMetaData.ADDRESSEE_TYPE + "= ? and " + IMInboxMetaData.ADDRESSEE_ID + "=?";
            String[] whereArgs = new String[] { inbox.getAddresseeType().name(), inbox.getAddresseeID() };
            IMInboxEntryImpl tempInbox = get(whereClause, whereArgs);
            if (null != tempInbox) {
                return update(inbox, whereClause, whereArgs);
            } else {
                return insert(inbox) > -1;
            }
        }
        return false;
    }

    /**
     * Save a list of IMInbox
     * 
     * @param list
     * @return boolean
     */
    public boolean saveIMInbox(List<IMInboxEntryImpl> list) {
        return replace(list);
    }
}
