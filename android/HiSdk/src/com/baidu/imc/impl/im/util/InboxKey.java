package com.baidu.imc.impl.im.util;

import com.baidu.imc.message.IMMessage;
import com.baidu.imc.type.AddresseeType;

public class InboxKey {
    public static String getInboxKey(IMMessage message) {
    	if(message != null)
    	{
        String key = message.getAddresseeType().name();
        // if (message.getAddresserID().compareTo(message.getAddresseeID()) < 0) {
        // key += ":" + message.getAddresserID() + ":" + message.getAddresseeID();
        // } else {
        key += ":" + message.getAddresseeID()/* + ":" + message.getAddresserID() */;
        // }
        return key;
    	}
    	return null;
    }

    public static String getInboxKey(AddresseeType addresseeType, String addresserId, String addresseeId) {
    	
    	if(addresseeType != null && addresseeId != null)
    	{
        String key = addresseeType.name();
        // if (addresserId.compareTo(addresseeId) < 0) {
        // key += ":" + addresserId + ":" + addresseeId;
        // } else {
        key += ":" + addresseeId/* + ":" + addresserId */;
        // }
        return key;
    	}
    	return null;
    }

    public static String getInboxKey(AddresseeType addresseeType, String addresseeId) {
    	if(addresseeType != null && addresseeId != null) {
        return addresseeType.name() + ":" + addresseeId;
    	}
    	return null;
    }

}
