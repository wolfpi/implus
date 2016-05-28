package com.baidu.imc.impl.im.store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baidu.im.frame.utils.LogUtil;

public abstract class DataBaseUtil<T extends Object> {
    private static final String TAG = "DataBaseUtil";

    private static final boolean DB_DEBUG_LOG = false;
    public static final Object DBLOCK = new Object();

    protected static volatile UserSQLHelper dbHelper;
    protected static Map<String, Object> dbUtilPool = new HashMap<String, Object>();
    protected static Map<String, Object> dbHelperPool = new HashMap<String, Object>();

    public static UserSQLHelper getDBHelper() {
        return dbHelper;
    }

    public DataBaseUtil(Context context) {
        if (dbHelper == null) {
            synchronized (DBLOCK) {
                if (dbHelper == null) {
                    dbHelper = new UserSQLHelper(context);
                }
            }
        }
    }

    public DataBaseUtil(Context context, String databaseName) {
        synchronized (DBLOCK) {
            dbHelper = (UserSQLHelper) dbHelperPool.get(databaseName);
            if (null == dbHelper) {
                dbHelper = new UserSQLHelper(context, databaseName);
                dbHelperPool.put(databaseName, dbHelper);
            }
        }
    }

    /**
     * Return a Tag
     */
    protected abstract String getDBTagName();

    /**
     * 
     * 
     * @param value
     * @return
     */
    protected abstract ContentValues getContentValues(T value);

    /**
     * Create a T object
     * 
     * @param cursor
     * @return
     */
    protected abstract T create(Cursor cursor);

    /**
     * Get a query key list
     * 
     * 
     * @return
     */
    protected abstract String[] getQueryKeyList();

    /**
     * Get table name
     * 
     * @return
     */
    protected abstract String getTableName();

    /**
     * Get a int value from cursor
     * 
     * @param cursor
     * @param columnName
     * @return
     */
    protected static int getIntValue(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    /**
     * Get a long value from cursor
     * 
     * @param cursor
     * @param columnName
     * @return
     */
    protected static long getLongValue(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    /**
     * Get a boolean value from cursor
     * 
     * @param cursor
     * @param columnName
     * @return
     */
    protected static boolean getBooleanValue(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName)) == 1;
    }

    /**
     * Get a string value from cursor
     * 
     * @param cursor
     * @param columnName
     * @return
     */
    protected static String getStringValue(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    // protected T findBySelection(String selection, String[] selectionArgs) {
    // T reslut = null;
    // Cursor cursor = find(getQueryKeyList(), selection, selectionArgs, null, null, null);
    // if (cursor != null && cursor.moveToFirst()) {
    // reslut = create(cursor);
    // }
    // closeCursor(cursor);
    // return reslut;
    // }

    /**
     * Get a Cursor over the result set
     * 
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @return cursor
     */
    protected Cursor find(String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
            String orderBy) {
        return find(columns, selection, selectionArgs, groupBy, having, orderBy, null);
    }

    /**
     * Get a Cursor over the result set
     * 
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     * @return cursor
     */
    protected Cursor find(String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
            String orderBy, String limit) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[find]");
        }
        Cursor result = null;
        synchronized (DBLOCK) {
            try {
                result =
                        dbHelper.open().query(getTableName(), columns, selection, selectionArgs, groupBy, having,
                                orderBy, limit);
            } catch (Exception e) {
                LogUtil.e(getDBTagName(), e);
            } catch (Error e) {
                LogUtil.e(getDBTagName(), e);
            }
            return result;
        }
    }

    /**
     * Insert a new value
     * 
     * @param value
     * @return id
     */
    public long insert(T value) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[insert]" + value.toString());
        }
        long result = 0;
        synchronized (DBLOCK) {
            try {
                result = dbHelper.open().insert(getTableName(), null, getContentValues(value));
            } catch (Exception e) {
                LogUtil.e(getDBTagName(), e);
            } catch (Error e) {
                LogUtil.e(getDBTagName(), e);
            }
            return result;
        }
    }

    /**
     * Insert a list of new values
     * 
     * @param values
     * @return a list of id
     */
    public List<Long> insert(List<T> values) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[insert]" + values.toString());
        }
        List<Long> ids = new ArrayList<Long>();
        synchronized (DBLOCK) {
            SQLiteDatabase db = null;
            try {
                db = dbHelper.open();
                db.beginTransaction();
                for (T t : values) {
                    long id = db.insert(getTableName(), null, getContentValues(t));
                    ids.add(Long.valueOf(id));
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                LogUtil.e(getDBTagName(), e);
            } catch (Error e) {
                LogUtil.e(getDBTagName(), e);
            } finally {
                if (null != db) {
                    db.endTransaction();
                }
            }
            return ids;
        }
    }

    /**
     * Update one item
     * 
     * @param value
     * @param id
     * @return
     */
    public boolean update(T value, long id) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[update]" + value.toString());
        }
        boolean result = false;
        synchronized (DBLOCK) {
            try {
                result =
                        dbHelper.open().update(getTableName(), getContentValues(value), getQueryKeyList()[0] + "=?",
                                new String[] { String.valueOf(id) }) > 0;
            } catch (Exception e) {
                LogUtil.e(getDBTagName(), e);
            } catch (Error e) {
                LogUtil.e(getDBTagName(), e);
            }
            return result;
        }
    }

    /**
     * Update one item
     * 
     * @param value
     * @param idkey
     * @param id
     * @return
     */
    public boolean update(T value, String idKey, long id) {

        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[update]" + value.toString());
        }
        boolean result = false;
        synchronized (DBLOCK) {
            try {
                result =
                        dbHelper.open().update(getTableName(), getContentValues(value), idKey + "=?",
                                new String[] { String.valueOf(id) }) > 0;
            } catch (Exception e) {
                LogUtil.e(getDBTagName(), e);
            } catch (Error e) {
                LogUtil.e(getDBTagName(), e);
            }
            return result;
        }
    }

    /**
     * Update one item
     * 
     * @param value
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public boolean update(T value, String whereClause, String[] whereArgs) {

        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[update]" + value.toString());
        }
        boolean result = false;
        synchronized (DBLOCK) {
            try {
                result = dbHelper.open().update(getTableName(), getContentValues(value), whereClause, whereArgs) > 0;
            } catch (Exception e) {
                LogUtil.e(getDBTagName(), e);
            } catch (Error e) {
                LogUtil.e(getDBTagName(), e);
            }
            return result;
        }
    }

    /**
     * Update a list of items
     * 
     * @param list
     * @return
     */
    public boolean update(List<T> list) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[insert]" + list.toString());
        }
        boolean result = false;
        synchronized (DBLOCK) {
            SQLiteDatabase db = null;
            try {
                db = dbHelper.open();
                db.beginTransaction();
                for (T t : list) {
                    db.update(getTableName(), getContentValues(t), null, null);
                }
                db.setTransactionSuccessful();
                result = true;
            } catch (Exception e) {
                LogUtil.e(getDBTagName(), e);
            } catch (Error e) {
                LogUtil.e(getDBTagName(), e);
            } finally {
                if (null != db) {
                    db.endTransaction();
                }
            }
            return result;
        }
    }

    /**
     * Update
     * 
     * @param value
     * @param id
     * @return
     */
    public boolean update(ContentValues value, String item, long id) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[update]" + value.toString());
        }
        boolean result = false;
        synchronized (DBLOCK) {
            try {
                result =
                        dbHelper.open().update(getTableName(), value, item + "=?", new String[] { String.valueOf(id) }) > 0;
            } catch (Exception e) {
                LogUtil.e(getDBTagName(), e);
            } catch (Error e) {
                LogUtil.e(getDBTagName(), e);
            }
            return result;
        }
    }

    /**
     * Update
     * 
     * @param where
     * @param args
     * @return
     */
    public boolean update(ContentValues value, String where, String[] args) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[update]" + value.toString());
        }
        boolean result = false;
        synchronized (DBLOCK) {
        	SQLiteDatabase db = null;
            try {  
            	db = dbHelper.open();
            	db.beginTransaction();
                result = dbHelper.open().update(getTableName(), value, where, args) > 0;
                db.setTransactionSuccessful();
            } catch (Exception e) {
                LogUtil.e(getDBTagName(), e);
            } catch (Error e) {
                LogUtil.e(getDBTagName(), e);
            } finally
            {
            	db.endTransaction();
            }
            return result;
        }
    }

    /**
     * Delete
     * 
     * @param id
     * @return
     */
    public boolean delete(long id) {

        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[delete]" + id);
        }
        boolean result = false;
        synchronized (DBLOCK) {
            try {
                result =
                        dbHelper.open().delete(getTableName(), getQueryKeyList()[0] + "=?",
                                new String[] { String.valueOf(id) }) > 0;
            } catch (Exception e) {
                LogUtil.e(getDBTagName(), e);
            } catch (Error e) {
                LogUtil.e(getDBTagName(), e);
            }
            return result;
        }
    }

    /**
     * Delete
     * 
     * @param ids
     * @return
     */
    public boolean delete(long[] ids) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[delete]" + Arrays.toString(ids));
        }
        synchronized (DBLOCK) {
            SQLiteDatabase db = null;
            boolean result = false;
            try {
                db = dbHelper.open();
                db.beginTransaction();
                for (int i = 0; i < ids.length; i++) {
                    db.delete(getTableName(), getQueryKeyList()[0] + "=?", new String[] { String.valueOf(ids[i]) });
                }
                db.setTransactionSuccessful();
                result = true;
            } catch (Exception e) {
                LogUtil.e(getDBTagName(), e);
            } catch (Error e) {
                LogUtil.e(getDBTagName(), e);
            } finally {
                db.endTransaction();
            }
            return result;
        }
    }

    /**
     * Delete
     * 
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public boolean delete(String whereClause, String[] whereArgs) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[delete]" + whereClause + "[" + Arrays.toString(whereArgs) + "]");
        }
        boolean result = false;
        synchronized (DBLOCK) {
            SQLiteDatabase db = null;
            try {
                db = dbHelper.open();
                db.beginTransaction();
                result = db.delete(getTableName(), whereClause, whereArgs) > 0;
                db.setTransactionSuccessful();
            } catch (Exception e) {
                LogUtil.e(getDBTagName(), e);
            } catch (Error e) {
                LogUtil.e(getDBTagName(), e);
            } finally {
                if (null != db) {
                    db.endTransaction();
                }
            }
            return result;
        }
    }

    /**
     * Delete all
     */
    public boolean deleteAllFromDB() {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[deleteAll]");
        }
        boolean result = false;
        synchronized (DBLOCK) {
            SQLiteDatabase db = null;
            try {
                db = dbHelper.open();
                db.beginTransaction();
                result = db.delete(getTableName(), null, null) > 0;
                db.setTransactionSuccessful();
            } catch (Exception e) {
                LogUtil.e(getDBTagName(), e);
            } catch (Error e) {
                LogUtil.e(getDBTagName(), e);
            } finally {
                if (null != db) {
                    db.endTransaction();
                }
            }
            return result;
        }
    }

    /**
     * Get a value
     * 
     * @param id
     * @return T
     */
    public T get(long id) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[get]" + id);
        }
        T reslut = null;
        Cursor cursor =
                find(getQueryKeyList(), getQueryKeyList()[0] + "=?", new String[] { Long.toString(id) }, null, null,
                        null);
        if (cursor != null && cursor.moveToFirst()) {
            reslut = create(cursor);
        }
        closeCursor(cursor);
        return reslut;
    }

    /**
     * Get a value
     * 
     * @param key
     * @param id
     * @return T
     */
    public T get(String key, long id) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[get]" + key + id);
        }
        T reslut = null;
        Cursor cursor = find(getQueryKeyList(), key + "=?", new String[] { Long.toString(id) }, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            reslut = create(cursor);
        }
        closeCursor(cursor);
        return reslut;
    }

    /**
     * Get a value
     * 
     * @param where
     * @param whereaArgs
     * @return T
     */
    public T get(String where, String[] whereArgs) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[get]" + where + whereArgs.toString());
        }
        T reslut = null;
        Cursor cursor = find(getQueryKeyList(), where, whereArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            reslut = create(cursor);
        }
        closeCursor(cursor);
        return reslut;
    }

    /**
     * Get a value
     * 
     * @param where
     * @param whereaArgs
     * @return T
     */
    public T get(String where, String[] whereArgs, String orderby) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[get]" + where + whereArgs.toString());
        }
        T reslut = null;
        Cursor cursor = find(getQueryKeyList(), where, whereArgs, null, null, orderby);
        if (cursor != null && cursor.moveToFirst()) {
            reslut = create(cursor);
        }
        closeCursor(cursor);
        return reslut;
    }

    /**
     * Get a value
     * 
     * @param where
     * @param whereaArgs
     * @return T
     */
    public T get(String where, String[] whereArgs, String orderby, String limit) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[get]" + where + whereArgs.toString());
        }
        T reslut = null;
        Cursor cursor = find(getQueryKeyList(), where, whereArgs, null, null, orderby,limit);
        if (cursor != null && cursor.moveToFirst()) {
            reslut = create(cursor);
        }
        closeCursor(cursor);
        return reslut;
    }


    public int count(String where, String[] whereArgs){
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[count]" + where + whereArgs.toString());
        }
        Cursor result = null;
        synchronized (DBLOCK) {
            try {
                result = dbHelper.open().rawQuery("SELECT count(*) FROM "+getTableName()+" WHERE "+where, whereArgs);
                result.moveToFirst();
                return result.getInt(0);
            } catch (Exception e) {
                LogUtil.e(getDBTagName(), e);
            } catch (Error e) {
                LogUtil.e(getDBTagName(), e);
            }finally {
                if(result != null)
                result.close();
            }
        }
        return -1;
    }
    
    /**
     * Find all
     * 
     * @param orderBy
     * @return
     */
    public List<T> findAll(String orderBy) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[findAll]" + orderBy);
        }
        List<T> result = new ArrayList<T>();
        Cursor cursor = find(getQueryKeyList(), null, null, null, null, orderBy);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                result.add(create(cursor));
            }
            cursor.close();
        } else {
            return Collections.emptyList();
        }
        return result;
    }

    /**
     * Find all
     * 
     * @param selection
     * @param selectionArgs
     * @param orderBy
     * @return
     */
    public List<T> findAllList(String selection, String[] selectionArgs, String orderBy) {
        List<T> result = new ArrayList<T>();
        Cursor cursor = find(getQueryKeyList(), selection, selectionArgs, null, null, orderBy);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                result.add(create(cursor));
            }
            cursor.close();
        } else {
            return Collections.emptyList();
        }
        return result;
    }

    /**
     * Find all
     * 
     * @param selection
     * @param selectionArgs
     * @param orderBy
     * @param limit
     * @return
     */
    public List<T> findAllList(String selection, String[] selectionArgs, String orderBy, String limit) {
        List<T> result = new ArrayList<T>();
        Cursor cursor = find(getQueryKeyList(), selection, selectionArgs, null, null, orderBy, limit);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                result.add(create(cursor));
            }
            cursor.close();
        } else {
            return Collections.emptyList();
        }
        return result;
    }

    /**
     * Replace
     * 
     * @param t
     * @return
     */
    public boolean replace(T t) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[replace]" + t.toString());
        }
        boolean result = false;
        synchronized (DBLOCK) {
            try {
                dbHelper.open().replace(getTableName(), null, getContentValues(t));
                result = true;
            } catch (Exception e) {
                LogUtil.e(getDBTagName(), e);
            } catch (Error e) {
                LogUtil.e(getDBTagName(), e);
            }
        }
        return result;
    }

    /**
     * Replace a list
     * 
     * @param values
     * @return
     */
    public boolean replace(List<T> values) {
        if (DB_DEBUG_LOG) {
            LogUtil.d(getDBTagName(), "[replace list]" + values.toString());
        }
        boolean result = false;
        synchronized (DBLOCK) {
            SQLiteDatabase db = null;
            try {
                db = dbHelper.open();
                db.beginTransaction();
                for (T value : values) {
                    result = db.replace(getTableName(), null, getContentValues(value)) > -1;
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                LogUtil.e(getDBTagName(), e);
            } catch (Error e) {
                LogUtil.e(getDBTagName(), e);
            } finally {
                if (null != db) {
                    db.endTransaction();
                }
            }
            return result;
        }
    }


    protected static void closeCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
            cursor = null;
        } else {
            cursor = null;
        }
    }

    public static void clearAllDBCache() {
        LogUtil.d(TAG, "clearAllDBCache");
        closeAllDatabaseHelper(dbHelperPool);
        dbHelperPool.clear();
        clearAllDatabaseUtils(dbUtilPool);
        dbUtilPool.clear();
        dbHelper = null;
    }

    private static void closeAllDatabaseHelper(Map<String, Object> helperPool) {
        Iterator<Map.Entry<String, Object>> iterator = helperPool.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            Object val = entry.getValue();
            if (val != null && val instanceof UserSQLHelper) {
                LogUtil.d(TAG, "closeAllDatabaseHelper: " + ((UserSQLHelper) val));
                UserSQLHelper helper = ((UserSQLHelper) val);
                helper.close();
            }
        }
    }

    private static void clearAllDatabaseUtils(Map<String, Object> utilPool) {
        Iterator<Map.Entry<String, Object>> iterator = utilPool.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            Object val = entry.getValue();
            if (val != null) {
                LogUtil.d(TAG, "clearAllDatabaseUtils: " + val);
                val = null;
            }
        }
    }

    public boolean isJustRecreated() {
        return dbHelper.isJustRecreated();
    }

    public void setJustRecreated(boolean justRecreated) {
        dbHelper.setJustRecreated(justRecreated);
    }
}
