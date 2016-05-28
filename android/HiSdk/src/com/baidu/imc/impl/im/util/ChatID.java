package com.baidu.imc.impl.im.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import android.support.annotation.IntDef;
import android.text.TextUtils;

import com.baidu.im.frame.pb.EnumChatType;
import com.baidu.imc.impl.im.message.IMInboxEntryImpl;
import com.baidu.imc.type.AddresseeType;

public class ChatID {

    /**
     * Get a chatId by addresseeType, addresseeID and addresserID
     * 
     * @param addresseeType
     * @param addresseeID
     * @param addresserID
     * 
     * @return chatID maybe null if params error.
     */
    public static String getChatID(AddresseeType addresseeType, String addresseeID, String addresserID) {
        String chatId = null;
        if (null != addresseeType && !TextUtils.isEmpty(addresseeID) && !TextUtils.isEmpty(addresserID)) {
            switch (addresseeType) {
                case USER:
                    if (addresseeID.compareTo(addresserID) > 0) {
                        chatId = addresserID + ":" + addresseeID;
                    } else {
                        chatId = addresseeID + ":" + addresserID;
                    }
                    break;
                case GROUP:
                    chatId = addresseeID;
                    break;
                case SERVICE:
                    if (addresseeID.compareTo(addresserID) > 0) {
                        chatId = addresserID + ":" + addresseeID;
                    } else {
                        chatId = addresseeID + ":" + addresserID;
                    }
                    break;
                default:
                    break;
            }
        }
        return chatId;
    }

    /**
     * Get a chatId by addresseeType, addresseeID and addresserID
     * 
     * @param inboxEntry
     * @param myId
     * @param from
     * @param to
     * 
     * @return result maybe false if params error
     */
    public static boolean setChatID(IMInboxEntryImpl inboxEntry, @EChatType int chatType, String myId, String from,
            String fromName, String to) {
        if (null != inboxEntry && -1 != chatType && !TextUtils.isEmpty(myId) && !TextUtils.isEmpty(from)
                && !TextUtils.isEmpty(to)) {
            switch (chatType) {
                case EnumChatType.CHAT_P2P:
                    inboxEntry.setAddresseeType(AddresseeType.USER);
                    if (!myId.equals(from)) {
                        inboxEntry.setAddresseeID(from);
                        inboxEntry.setAddresseeName(fromName);
                    } else {
                        inboxEntry.setAddresseeID(to);
                        inboxEntry.setAddresseeName(to);
                    }
                    return true;
                case EnumChatType.CHAT_P2G:
                    inboxEntry.setAddresseeType(AddresseeType.GROUP);
                    inboxEntry.setAddresseeID(to);
                    inboxEntry.setAddresseeName(to);
                    return true;
                case EnumChatType.CHAT_PA:
                    inboxEntry.setAddresseeType(AddresseeType.SERVICE);
                    if (!myId.equals(from)) {
                        inboxEntry.setAddresseeID(from);
                        inboxEntry.setAddresseeName(fromName);
                    } else {
                        inboxEntry.setAddresseeID(to);
                        inboxEntry.setAddresseeName(to);
                    }
                    return true;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Set chatID into a IMInboxEntry
     * 
     * @param inboxEntry
     * @param chatType
     * @param myID
     * @param chatID
     */
    public static boolean setChatID(IMInboxEntryImpl inboxEntry, @EChatType int chatType, String myId, String chatID) {
        if (null != inboxEntry && !TextUtils.isEmpty(chatID) && !TextUtils.isEmpty(chatID)) {
            switch (chatType) {
                case EnumChatType.CHAT_P2P:
                    inboxEntry.setAddresseeType(AddresseeType.USER);
                    String[] results = chatID.split(":");
                    if (null != results && results.length == 2) {
                        String lowerID = results[0];
                        String greaterID = results[1];
                        if (myId.equals(lowerID)) {
                            inboxEntry.setAddresseeID(greaterID);
                            inboxEntry.setAddresseeName(greaterID);
                        } else {
                            inboxEntry.setAddresseeID(lowerID);
                            inboxEntry.setAddresseeName(lowerID);
                        }
                        return true;
                    } else {
                        return false;
                    }
                case EnumChatType.CHAT_P2G:
                    inboxEntry.setAddresseeType(AddresseeType.GROUP);
                    inboxEntry.setAddresseeID(chatID);
                    inboxEntry.setAddresseeName(chatID);
                    return true;
                case EnumChatType.CHAT_PA:
                    inboxEntry.setAddresseeType(AddresseeType.SERVICE);
                    String[] paResults = chatID.split(":");
                    if (null != paResults && paResults.length == 2) {
                        String paLowerID = paResults[0];
                        String paGreaterID = paResults[1];
                        if (myId.equals(paLowerID)) {
                            inboxEntry.setAddresseeID(paGreaterID);
                            inboxEntry.setAddresseeName(paGreaterID);
                        } else {
                            inboxEntry.setAddresseeID(paLowerID);
                            inboxEntry.setAddresseeName(paLowerID);
                        }
                        return true;
                    } else {
                        return false;
                    }
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {EnumChatType.CHAT_P2G, EnumChatType.CHAT_P2P, EnumChatType.CHAT_PA, -1})
    @interface EChatType{}

}
