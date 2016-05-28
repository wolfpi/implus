package com.baidu.imc.impl.im.store;

import java.util.Collections;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.impl.im.message.BDHI_IMMESSAGE_TYPE;
import com.baidu.imc.impl.im.message.BDHiIMCustomMessage;
import com.baidu.imc.impl.im.message.BDHiIMFileMessage;
import com.baidu.imc.impl.im.message.BDHiIMImageMessage;
import com.baidu.imc.impl.im.message.BDHiIMMessage;
import com.baidu.imc.impl.im.message.BDHiIMTextMessage;
import com.baidu.imc.impl.im.message.BDHiIMVoiceMessage;
import com.baidu.imc.impl.im.message.OneMsgConverter;
import com.baidu.imc.impl.im.message.content.BDHiVoiceMessageContent;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.IMMessageStatus;

public class IMMessageDBUtil extends DataBaseUtil<BDHiIMMessage> {

    private static final String TAG = "IMMessageDBUtil";
    private static IMMessageDBUtil dbUtil;

    public IMMessageDBUtil(Context context, String databaseName) {
        super(context, databaseName);
    }

    public static IMMessageDBUtil getDB(Context context, String databaseName) {
        if (null != context && null != databaseName && databaseName.length() > 0) {
            String dbName = databaseName + "_" + TAG;
            dbUtil = (IMMessageDBUtil) dbUtilPool.get(dbName);
            synchronized (IMMessageDBUtil.class) {
                if (null == dbUtil) {
                    dbUtil = new IMMessageDBUtil(context, databaseName);
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
    protected ContentValues getContentValues(BDHiIMMessage value) {
        if (value == null)
            return null;
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMMessageMetaData.ADDRESSEE_ID, value.getAddresseeID());
        contentValues.put(IMMessageMetaData.ADDRESSEE_NAME, value.getAddresseeName());
        contentValues.put(IMMessageMetaData.ADDRESSEE_TYPE, value.getAddresseeType().name());
        contentValues.put(IMMessageMetaData.ADDRESSER_ID, value.getAddresserID());
        contentValues.put(IMMessageMetaData.ADDRESSER_NAME, value.getAddresserName());
        contentValues.put(IMMessageMetaData.MSG_SEQ, value.getMsgSeq());
        contentValues.put(IMMessageMetaData.CLIENT_MSG_ID, value.getClientMessageID());
        contentValues.put(IMMessageMetaData.MSG_TYPE, value.getMessageType().name());
        contentValues.put(IMMessageMetaData.MSG_STATUS, value.getStatus().name());
        contentValues.put(IMMessageMetaData.SEND_TIME, value.getSendTime());
        contentValues.put(IMMessageMetaData.SERVER_TIME, value.getServerTime());
        contentValues.put(IMMessageMetaData.MSG_COMPATIBLE_TEXT, value.getCompatibleText());
        contentValues.put(IMMessageMetaData.MSG_NOTIFICATION_TEXT, value.getNotificationText());
        contentValues.put(IMMessageMetaData.MSG_EXTRA, value.getExtra());
        contentValues.put(IMMessageMetaData.MSG_BODY, value.getBody());
        contentValues.put(IMMessageMetaData.MSG_VIEW, value.getMsgView());
        contentValues.put(IMMessageMetaData.MSG_TEMPLATE, value.getMsgTemplate());
        contentValues.put(IMMessageMetaData.PREVIOUS_MSG_ID, value.getPreviousMsgID());
        contentValues.put(IMMessageMetaData.INEFFECTIVE, value.isIneffective() ? 1 : 0);
        contentValues.put(IMMessageMetaData.EXT0, value.getExt0());
        contentValues.put(IMMessageMetaData.EXT1, value.getExt1());
        contentValues.put(IMMessageMetaData.EXT2, value.getExt2());
        contentValues.put(IMMessageMetaData.EXT3, value.getExt3());
        contentValues.put(IMMessageMetaData.EXT4, value.getExt4());
        return contentValues;
    }

    @Override
    protected BDHiIMMessage create(Cursor cursor) {
        if (cursor == null)
            return null;
        BDHiIMMessage message = null;
        BDHI_IMMESSAGE_TYPE messageType =
                BDHI_IMMESSAGE_TYPE.valueOf(cursor.getString(cursor.getColumnIndex(IMMessageMetaData.MSG_TYPE)));
        switch (messageType) {
            case FILE:
                message = new BDHiIMFileMessage();
                break;
            case IMAGE:
                message = new BDHiIMImageMessage();
                break;
            case VOICE:
                message = new BDHiIMVoiceMessage();
                break;
            case TEXT:
                message = new BDHiIMTextMessage();
                break;
            default:
                message = new BDHiIMCustomMessage();
                break;
        }
        message.setMessageID(cursor.getLong(cursor.getColumnIndex(IMMessageMetaData.ID)));
        message.setAddresseeID(cursor.getString(cursor.getColumnIndex(IMMessageMetaData.ADDRESSEE_ID)));
        message.setAddresseeName(cursor.getString(cursor.getColumnIndex(IMMessageMetaData.ADDRESSEE_NAME)));
        message.setAddresseeType(AddresseeType.valueOf(cursor.getString(cursor
                .getColumnIndex(IMMessageMetaData.ADDRESSEE_TYPE))));
        message.setAddresserID(cursor.getString(cursor.getColumnIndex(IMMessageMetaData.ADDRESSER_ID)));
        message.setAddresserName(cursor.getString(cursor.getColumnIndex(IMMessageMetaData.ADDRESSER_NAME)));
        message.setMsgSeq(cursor.getLong(cursor.getColumnIndex(IMMessageMetaData.MSG_SEQ)));
        message.setClientMessageID(cursor.getLong(cursor.getColumnIndex(IMMessageMetaData.CLIENT_MSG_ID)));
        message.setMessageType(messageType);
        message.setStatus(IMMessageStatus.valueOf(cursor.getString(cursor.getColumnIndex(IMMessageMetaData.MSG_STATUS))));
        message.setSendTime(cursor.getLong(cursor.getColumnIndex(IMMessageMetaData.SEND_TIME)));
        message.setServerTime(cursor.getLong(cursor.getColumnIndex(IMMessageMetaData.SERVER_TIME)));
        message.setCompatibleText(cursor.getString(cursor.getColumnIndex(IMMessageMetaData.MSG_COMPATIBLE_TEXT)));
        message.setNotificationText(cursor.getString(cursor.getColumnIndex(IMMessageMetaData.MSG_NOTIFICATION_TEXT)));
        message.setExtra(cursor.getString(cursor.getColumnIndex(IMMessageMetaData.MSG_EXTRA)));
        message.setBody(cursor.getBlob(cursor.getColumnIndex(IMMessageMetaData.MSG_BODY)));
        message.setMsgView(cursor.getString(cursor.getColumnIndex(IMMessageMetaData.MSG_VIEW)));
        message.setMsgTemplate(cursor.getString(cursor.getColumnIndex(IMMessageMetaData.MSG_TEMPLATE)));
        message.setPreviousMsgID(cursor.getLong(cursor.getColumnIndex(IMMessageMetaData.PREVIOUS_MSG_ID)));
        message.setIneffective(cursor.getInt(cursor.getColumnIndex(IMMessageMetaData.INEFFECTIVE)) == 1);
        message.setExt0(cursor.getString(cursor.getColumnIndex(IMMessageMetaData.EXT0)));
        message.setExt1(cursor.getString(cursor.getColumnIndex(IMMessageMetaData.EXT1)));
        message.setExt2(cursor.getString(cursor.getColumnIndex(IMMessageMetaData.EXT2)));
        message.setExt3(cursor.getString(cursor.getColumnIndex(IMMessageMetaData.EXT3)));
        message.setExt4(cursor.getString(cursor.getColumnIndex(IMMessageMetaData.EXT4)));
        return message;
    }

    @Override
    protected String[] getQueryKeyList() {
        return new String[] { IMMessageMetaData.ID, IMMessageMetaData.ADDRESSEE_ID, IMMessageMetaData.ADDRESSEE_ID,
                IMMessageMetaData.ADDRESSEE_NAME, IMMessageMetaData.ADDRESSEE_TYPE, IMMessageMetaData.ADDRESSER_ID,
                IMMessageMetaData.ADDRESSER_NAME, IMMessageMetaData.MSG_SEQ, IMMessageMetaData.CLIENT_MSG_ID,
                IMMessageMetaData.MSG_TYPE, IMMessageMetaData.MSG_STATUS, IMMessageMetaData.SEND_TIME,
                IMMessageMetaData.SERVER_TIME, IMMessageMetaData.MSG_COMPATIBLE_TEXT,
                IMMessageMetaData.MSG_NOTIFICATION_TEXT, IMMessageMetaData.MSG_EXTRA, IMMessageMetaData.MSG_BODY,
                IMMessageMetaData.MSG_VIEW, IMMessageMetaData.MSG_TEMPLATE, IMMessageMetaData.PREVIOUS_MSG_ID,
                IMMessageMetaData.INEFFECTIVE, IMMessageMetaData.EXT0, IMMessageMetaData.EXT1, IMMessageMetaData.EXT2,
                IMMessageMetaData.EXT3, IMMessageMetaData.EXT4 };
    }

    @Override
    protected String getTableName() {
        return IMMessageMetaData.TABLE_NAME;
    }

    /**
     * Remove a message list
     * 
     * @param addresseeType
     * @param addresseeID
     * @return boolean
     */
    public boolean removeMessageList(AddresseeType addresseeType, String addresseeID) {
        if (null != addresseeType && null != addresseeID && addresseeID.length() > 0) {
        	 String whereClause;
        	 String[] whereArgs;
        	 
        	if(addresseeType == AddresseeType.USER)
        	{
        		whereClause =
                    IMMessageMetaData.ADDRESSEE_TYPE + "=? and (" + IMMessageMetaData.ADDRESSEE_ID + "=? or "
                            + IMMessageMetaData.ADDRESSER_ID + "=?)";
                whereArgs = new String[] { addresseeType.name(), addresseeID, addresseeID };
        	}
        	else
        	{
        		whereClause =
                        IMMessageMetaData.ADDRESSEE_TYPE + "=? and " + IMMessageMetaData.ADDRESSEE_ID + "=? ";
                whereArgs = new String[] { addresseeType.name(), addresseeID };
        	}
            ContentValues contentValues = new ContentValues();
            contentValues.put(IMMessageMetaData.INEFFECTIVE, 1);
            return update(contentValues, whereClause, whereArgs);
            // return delete(whereClause, whereArgs);
        } else {
            return false;
        }
    }

    /**
     * Remove a message
     * 
     * @param addresseeType
     * @param addresseeID
     * @param messageID
     */
    public boolean removeMessage(AddresseeType addresseeType, String addresseeID, long messageID) {
        if (null != addresseeType && null != addresseeID && addresseeID.length() > 0 && messageID > -1) {
        	 String whereClause;
        	 String[] whereArgs;
        	if(addresseeType == AddresseeType.USER)
        	{
        		whereClause =
        				IMMessageMetaData.ADDRESSEE_TYPE + "=? and (" + IMMessageMetaData.ADDRESSEE_ID + "=? or "
                            + IMMessageMetaData.ADDRESSER_ID + "=?) and " + IMMessageMetaData.ID + "=?";
        		whereArgs =
        				new String[] { addresseeType.name(), addresseeID, addresseeID, Long.toString(messageID) };
        	}
        	else
        	{
                whereClause =
                        IMMessageMetaData.ADDRESSEE_TYPE + "=? and " + IMMessageMetaData.ADDRESSEE_ID + "=? and " + IMMessageMetaData.ID + "=?";
                whereArgs =
                        new String[] { addresseeType.name(), addresseeID, addresseeID, Long.toString(messageID) };
        	}
        	
            ContentValues contentValues = new ContentValues();
            contentValues.put(IMMessageMetaData.INEFFECTIVE, 1);
            return update(contentValues, whereClause, whereArgs);
            // return delete(whereClause, whereArgs);
        } else {
            return false;
        }
    }

    public boolean removeMessage(long id) {
        if (id > -1) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(IMMessageMetaData.INEFFECTIVE, 1);
            return update(contentValues, IMMessageMetaData.ID, id);
            // return delete(id);
        } else {
            return false;
        }
    }

    /**
     * Get a message list
     * 
     * @param addresseeType
     * @param addresseeID
     * @return List<IMMessageImpl>
     */
    
    /*
    public List<BDHiIMMessage> getMessageList(AddresseeType addresseeType, String addresseeID) {
        if (null != addresseeType && null != addresseeID && addresseeID.length() > 0) {
            String whereClause =
                    IMMessageMetaData.ADDRESSEE_TYPE + "=? and (" + IMMessageMetaData.ADDRESSEE_ID + "=? or "
                            + IMMessageMetaData.ADDRESSER_ID + "=?) and " + IMMessageMetaData.INEFFECTIVE + "=?";
            String[] whereArgs = new String[] { addresseeType.name(), addresseeID, addresseeID, Integer.toString(0) };
            String orderBy = IMMessageMetaData.MSG_SEQ + ", " + IMMessageMetaData.CLIENT_MSG_ID + " asc";
            List<BDHiIMMessage> messageList = findAllList(whereClause, whereArgs, orderBy);
            for (BDHiIMMessage imMessage : messageList) {
                if (null != imMessage && null != imMessage.getBody() && imMessage.getBody().length > 0) {
                    OneMsgConverter.convertServerMsgContent(imMessage, imMessage.getBody());
                }
            }
            return messageList;
        } else {
            return null;
        }
    }

    public List<BDHiIMMessage> getMessageList2(AddresseeType addresseeType, String addresseeID) {
        if (null != addresseeType && null != addresseeID && addresseeID.length() > 0) {
            String whereClause =
                    IMMessageMetaData.ADDRESSEE_TYPE + "=? and (" + IMMessageMetaData.ADDRESSEE_ID + "=? or "
                            + IMMessageMetaData.ADDRESSER_ID + "=?) and " + IMMessageMetaData.INEFFECTIVE + "=?";
            String[] whereArgs = new String[] { addresseeType.name(), addresseeID, addresseeID, Integer.toString(0) };
            String orderBy = IMMessageMetaData.SERVER_TIME ;
            List<BDHiIMMessage> messageList = findAllList(whereClause, whereArgs, orderBy);
            for (BDHiIMMessage imMessage : messageList) {
                if (null != imMessage && null != imMessage.getBody() && imMessage.getBody().length > 0) {
                    OneMsgConverter.convertServerMsgContent(imMessage, imMessage.getBody());
                }
            }
            return messageList;
        } else {
            return null;
        }
    }*/
    
    public List<BDHiIMMessage> getMessageList3(AddresseeType addresseeType, String addresseeID,long servertime,long num) {
    	
        if (null != addresseeType && null != addresseeID && addresseeID.length() > 0) {
        	if(servertime == 0)
        		servertime = Long.MAX_VALUE;
        	String whereClause = "";
        	String[] whereArgs= null;
        	
        	LogUtil.printIm("servertime is:"+ servertime + "_"+ num);
            
        	if(addresseeType == AddresseeType.USER)
        	{
        		whereClause =
                    IMMessageMetaData.ADDRESSEE_TYPE + "=? and (" + IMMessageMetaData.ADDRESSEE_ID + "=? or "
                            + IMMessageMetaData.ADDRESSER_ID + "=?) and " + IMMessageMetaData.INEFFECTIVE + "=?" + 
                    		" and " + IMMessageMetaData.SERVER_TIME + "<?";
        		 whereArgs = new String[] { addresseeType.name(), addresseeID, addresseeID, Integer.toString(0), String.valueOf(servertime)};
        		
        	}else
        	{
        		whereClause =
                        IMMessageMetaData.ADDRESSEE_TYPE + "=? and " + IMMessageMetaData.ADDRESSEE_ID + "=?  and " + IMMessageMetaData.INEFFECTIVE + "=?" 
        		         +" and " + IMMessageMetaData.SERVER_TIME + "<? ";
        		 whereArgs = new String[] { addresseeType.name(), addresseeID, Integer.toString(0), String.valueOf(servertime)};
        		 
        		 LogUtil.printIm(whereClause + whereArgs);
        	}
        	
        	
            String orderBy = IMMessageMetaData.SERVER_TIME + " desc" ;
            List<BDHiIMMessage> messageList = findAllList(whereClause, whereArgs, orderBy,String.valueOf(num));
            for (BDHiIMMessage imMessage : messageList) {
                if (null != imMessage && null != imMessage.getBody() && imMessage.getBody().length > 0) {
                    OneMsgConverter.convertServerMsgContent(imMessage, imMessage.getBody());
                }
            }
            
            Collections.reverse(messageList);
            return messageList;
        } else {
            return null;
        }
    }
    

    /**
     * Get a message
     * 
     * @param addresseeType
     * @param addresseeID
     * @param messageID
     */
    public BDHiIMMessage getMessageByMessageID(AddresseeType addresseeType, String addresseeID, long messageID) {
        if (null != addresseeType && null != addresseeID && addresseeID.length() > 0 && messageID > -1) {
        	String whereClause = "";
        	String[] whereArgs= null;
        	
        	if(addresseeType == AddresseeType.USER)
        	{
        		whereClause =
        					IMMessageMetaData.ADDRESSEE_TYPE + "=? and (" + IMMessageMetaData.ADDRESSEE_ID + "=? or "
                            + IMMessageMetaData.ADDRESSER_ID + "=?) and " + IMMessageMetaData.ID + "=? and "
                            + IMMessageMetaData.INEFFECTIVE + "=?";
        		whereArgs =
        				new String[] { addresseeType.name(), addresseeID, addresseeID, Long.toString(messageID),
                            Integer.toString(0) };
        	}else
        	{
        		whereClause =
    					IMMessageMetaData.ADDRESSEE_TYPE + "=? and (" + IMMessageMetaData.ADDRESSEE_ID + "=?) and " + IMMessageMetaData.ID + "=? and "
                        + IMMessageMetaData.INEFFECTIVE + "=?";
        		whereArgs =
    				new String[] { addresseeType.name(), addresseeID, Long.toString(messageID),
                        Integer.toString(0) };
        	}
            return get(whereClause, whereArgs);
        } else {
            return null;
        }
    }

    /**
     * Get a message by dbId
     * 
     * @param dbId
     * @return BDHiIMMessage
     */
    public BDHiIMMessage getMessageByDBId(long id) {
        if (id > -1) {
            String whereClause = IMMessageMetaData.ID + "=? and " + IMMessageMetaData.INEFFECTIVE + "=?";
            String[] whereArgs = new String[] { Long.toString(id), Integer.toString(0) };
            // return get(id);
            return get(whereClause, whereArgs);
        } else {
            return null;
        }
    }

    /**
     * Get last successful message.
     * 
     * @param addresseeType
     * @param addresseeID
     * 
     * @return BDHiIMMessage
     */
    public BDHiIMMessage getLastSuccessfulMessage(AddresseeType addresseeType, String addresseeID) {
    	
    	String whereClause ="";
    	String[] whereArgs =null;
    	
    	if(addresseeType == AddresseeType.USER)
    	{
    		 whereClause =
                IMMessageMetaData.ADDRESSEE_TYPE + "=? and (" + IMMessageMetaData.ADDRESSEE_ID + "=? or "
                        + IMMessageMetaData.ADDRESSER_ID + "=?) ";
                        		//+ "and (" + IMMessageMetaData.MSG_STATUS + "=? or "
                       // + IMMessageMetaData.MSG_STATUS + "=?)";
    		 whereArgs =
                new String[] { addresseeType.name(), addresseeID, addresseeID 
    				 //IMMessageStatus.UNREAD.name(),
                      //  IMMessageStatus.SENT.name() 
    				 };
    	}
    	else
    	{
    		 whereClause =
    	                IMMessageMetaData.ADDRESSEE_TYPE + "=? and " + IMMessageMetaData.ADDRESSEE_ID + "=? ";
    	                		//+ "and (" + IMMessageMetaData.MSG_STATUS + "=? or "
    	                        //+ IMMessageMetaData.MSG_STATUS + "=?)";
    	     whereArgs =
    	                new String[] { addresseeType.name(), addresseeID
    	    		 			//, IMMessageStatus.UNREAD.name(),
    	                        //IMMessageStatus.SENT.name() 
    	    		                };
    	}
        String orderby = IMMessageMetaData.SERVER_TIME + " desc";
        return get(whereClause, whereArgs, orderby, " 1");
    }

    /**
     * Get last effective message.
     * 
     * @param addresseeType
     * @param addresseeID
     * 
     * @return BDHiIMMessage
     */
    public BDHiIMMessage getLastEffectiveMessage(AddresseeType addresseeType, String addresseeID) {
    	String whereClause = "";
    	String[] whereArgs = null;
    	if(addresseeType == AddresseeType.USER)
    	{
    		whereClause =
                IMMessageMetaData.ADDRESSEE_TYPE + "=? and (" + IMMessageMetaData.ADDRESSEE_ID + "=? or "
                        + IMMessageMetaData.ADDRESSER_ID + "=?) and " + IMMessageMetaData.INEFFECTIVE + "=?";
            whereArgs = new String[] { addresseeType.name(), addresseeID, addresseeID, Integer.toString(0) };
    	}else
    	{
    		whereClause =
                    IMMessageMetaData.ADDRESSEE_TYPE + "=? and " + IMMessageMetaData.ADDRESSEE_ID + "=?  and " + IMMessageMetaData.INEFFECTIVE + "=?";
                whereArgs = new String[] { addresseeType.name(), addresseeID , Integer.toString(0) };
    	}
        String orderby = IMMessageMetaData.SERVER_TIME + " desc";
        return get(whereClause, whereArgs, orderby);
    }

    /**
     * Save a message
     * 
     * @param message
     * @return messageId -1 if failed
     */
    public long saveMessage(BDHiIMMessage message) {
        if (null != message) {
            if (0 != message.getMessageID()) {
                // update
                boolean result = update(message, message.getMessageID());
                if (result) {
                    return message.getMessageID();
                } else {
                    return -1;
                }
            } else {
                String whereClause =
                        IMMessageMetaData.ADDRESSEE_TYPE + "=? and " + IMMessageMetaData.ADDRESSEE_ID + "=? and "
                                + IMMessageMetaData.ADDRESSER_ID + "=? and " + IMMessageMetaData.CLIENT_MSG_ID + "=?";
                String[] whereArgs =
                        new String[] { message.getAddresseeType().name(), message.getAddresseeID(),
                                message.getAddresserID(), Long.toString(message.getClientMessageID()) };
                BDHiIMMessage tempMessageImpl = get(whereClause, whereArgs);
                if (tempMessageImpl == null) {
                    return insert(message);
                } else {
                    message.setIneffective(tempMessageImpl.isIneffective());
                    boolean result = update(message, tempMessageImpl.getMessageID());
                    if (result) {
                        return tempMessageImpl.getMessageID();
                    } else {
                        return -1;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Insert a message
     *
     * ZX 参考iOS, 尝试修改为: 如果数据库已存在该条消息, 则完全 ignore 掉. 需要验证
     *
     * @param message
     * @return messageId -1 if failed
     */
    public long insertMessage(BDHiIMMessage message){
        if (null != message) {
            String whereClause =
                    IMMessageMetaData.ADDRESSEE_TYPE + "=? and " + IMMessageMetaData.ADDRESSEE_ID + "=? and "
                            + IMMessageMetaData.ADDRESSER_ID + "=? and " + IMMessageMetaData.CLIENT_MSG_ID + "=?";
            String[] whereArgs =
                    new String[] { message.getAddresseeType().name(), message.getAddresseeID(),
                            message.getAddresserID(), Long.toString(message.getClientMessageID()) };
            int count = count(whereClause, whereArgs);
            if(count == 0){
                long newId = insert(message);
                message.setMessageID(newId);
                return newId;
            }
        }
        return -1;
    }
    /**
     * Batch save messages TODO
     */
}
