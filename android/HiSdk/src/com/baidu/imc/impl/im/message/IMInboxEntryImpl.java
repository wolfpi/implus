package com.baidu.imc.impl.im.message;

import java.util.Arrays;

import com.baidu.imc.impl.im.util.InboxKey;
import com.baidu.imc.message.IMInboxEntry;
import com.baidu.imc.message.IMMessage;
import com.baidu.imc.type.AddresseeType;

public class IMInboxEntryImpl implements IMInboxEntry, Comparable<IMInboxEntryImpl> {
    private BDHiIMMessage lastMessage;
    private int unreadCount;

    private long lastReadMessageID;
    private long lastReadMessageTime;
    private long lastReceiveMessageID;
    private long lastReceiveMessageTime;

    private AddresseeType addresseeType;
    private String addresseeID;
    private String addresseeName;

    private byte[] msgBody;
    private boolean ineffective = false;

    private String ext0;
    private String ext1;
    private String ext2;
    private String ext3;
    private String ext4;

    @Override
    public String getID() {
        return InboxKey.getInboxKey(addresseeType, addresseeID);
    }

    @Override
    public IMMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(BDHiIMMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public long getLastReadMessageID() {
        return lastReadMessageID;
    }

    public void setLastReadMessageID(long lastReadMessageID) {
        this.lastReadMessageID = lastReadMessageID;
    }

    public long getLastReadMessageTime() {
        return lastReadMessageTime;
    }

    public void setLastReadMessageTime(long lastReadMessageTime) {
        this.lastReadMessageTime = lastReadMessageTime;
    }

    public long getLastReceiveMessageID() {
        return lastReceiveMessageID;
    }

    public void setLastReceiveMessageID(long lastReceiveMessageID) {
        this.lastReceiveMessageID = lastReceiveMessageID;
    }

    public long getLastReceiveMessageTime() {
        return lastReceiveMessageTime;
    }

    public void setLastReceiveMessageTime(long lastReceiveMessageTime) {
        this.lastReceiveMessageTime = lastReceiveMessageTime;
    }

    public AddresseeType getAddresseeType() {
        return addresseeType;
    }

    public void setAddresseeType(AddresseeType conversationType) {
        this.addresseeType = conversationType;
    }

    public String getAddresseeID() {
        return addresseeID;
    }

    public void setAddresseeID(String conversationID) {
        this.addresseeID = conversationID;
    }

    public String getAddresseeName() {
        return addresseeName;
    }

    public void setAddresseeName(String addresseeName) {
        this.addresseeName = addresseeName;
    }

    public byte[] getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(byte[] msgBody) {
        this.msgBody = msgBody;
    }

    public boolean isIneffective() {
        return ineffective;
    }

    public void setIneffective(boolean ineffective) {
        this.ineffective = ineffective;
    }

    public String getExt0() {
        return ext0;
    }

    public void setExt0(String ext0) {
        this.ext0 = ext0;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public String getExt4() {
        return ext4;
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4;
    }

    @Override
    public int compareTo(IMInboxEntryImpl another) {
        if (this.equals(another)) {
            return 0;
        }
        if (another == null) {
            return Integer.MAX_VALUE;
        }
        if (lastMessage == null && ((IMInboxEntryImpl) another).getLastMessage() == null) {
            return this.hashCode() - another.hashCode();
        }
        if (lastMessage == null) {
            return Integer.MIN_VALUE;
        }
        long lastMsgTime = lastMessage.getServerTime();
        return (int) (lastMsgTime - ((BDHiIMMessage) another.getLastMessage()).getServerTime());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final IMInboxEntryImpl other = (IMInboxEntryImpl) obj;

        if (addresseeType == null || addresseeID == null || other.addresseeID == null || other.addresseeType == null) {
            return false;
        }
        return addresseeType == other.addresseeType && addresseeID.equals(other.addresseeID);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((addresseeType == null) ? 0 : addresseeType.hashCode());
        result = prime * result + ((addresseeID == null) ? 0 : addresseeID.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "IMInboxEntryImpl [lastMessage=" + lastMessage + ", unreadCount=" + unreadCount + ", lastReadMessageID="
                + lastReadMessageID + ", lastReadMessageTime=" + lastReadMessageTime + ", lastReceiveMessageID="
                + lastReceiveMessageID + ", lastReceiveMessageTime=" + lastReceiveMessageTime + ", addresseeType="
                + addresseeType + ", addresseeID=" + addresseeID + ", addresseeName=" + addresseeName + ", msgBody="
                + Arrays.toString(msgBody) + ", ineffective=" + ineffective + ", ext0=" + ext0 + ", ext1=" + ext1
                + ", ext2=" + ext2 + ", ext3=" + ext3 + ", ext4=" + ext4 + "]";
    }

}
