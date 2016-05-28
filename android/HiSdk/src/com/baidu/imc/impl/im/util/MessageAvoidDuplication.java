package com.baidu.imc.impl.im.util;

import android.text.TextUtils;

public class MessageAvoidDuplication {

    private static final int MAX_SIZE = 500;

    private CacheEntity<String, Object> messageKeyCache = new CacheEntity<String, Object>(MAX_SIZE);

    public MessageAvoidDuplication() {

    }

    public boolean isMessageDuplicated(String messageType, String addresseeID, String addresserID, long clientMsgId,
            Object object) {
        if (!TextUtils.isEmpty(messageType) && !TextUtils.isEmpty(addresseeID) && !TextUtils.isEmpty(addresseeID)
                && clientMsgId > -1) {
            String key;
            if (addresserID.compareTo(addresseeID) < 0) {
                key = messageType + ":" + addresserID + ":" + addresseeID + clientMsgId;
            } else {
                key = messageType + ":" + addresseeID + ":" + addresserID + clientMsgId;
            }
            if (!messageKeyCache.containsKey(key)) {
                messageKeyCache.put(key, object);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void clear() {
        messageKeyCache.clear();
    }
}
