package com.baidu.imc.impl.im.message;

import com.baidu.imc.message.IMMessage;
import com.baidu.imc.message.Message;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.IMMessageStatus;

public class BDHiIMMessage implements IMMessage, Message, Comparable<BDHiIMMessage> {
    private long id;
    private AddresseeType addresseeType;
    private String addresseeID;
    private String addresseeName;
    private String addresserID;
    private String addresserName;
    private BDHI_IMMESSAGE_TYPE messageType;
    private long sendTime;
    private long serverTime;
    private long msgSeq;
    private long clientMessageID;
    private IMMessageStatus status;
    private String compatibleText;
    private String notificationText;
    private String extra;
    private byte[] body;
    private String msgView;
    private String msgTemplate;
    private long previousMsgID;
    private boolean ineffective = false;
    private String ext0;
    private String ext1;
    private String ext2;
    private String ext3;
    private String ext4;

    @Override
    public long getMessageID() {
        return id;
    }

    /**
     * Set message id
     * 
     * From: Get IMMessage from DB; Insert IMMessage to DB before sending IMMessage
     */
    public void setMessageID(long id) {
        this.id = id;
    }

    public long getMsgSeq() {
        return msgSeq;
    }

    /**
     * Set msgSeq
     * 
     * From: convert OneMsg to IMMessage; Get IMMessage from DB; Update IMMessage to DB after sent IMMessage
     */
    public void setMsgSeq(long msgSeq) {
        this.msgSeq = msgSeq;
    }

    @Override
    public AddresseeType getAddresseeType() {
        return addresseeType;
    }

    public void setAddresseeType(AddresseeType addresseeType) {
        this.addresseeType = addresseeType;
    }

    @Override
    public String getAddresseeID() {
        return addresseeID;
    }

    public void setAddresseeID(String addresseeID) {
        this.addresseeID = addresseeID;
    }

    @Override
    public String getAddresserID() {
        return addresserID;
    }

    public String getAddresseeName() {
        return addresseeName;
    }

    public void setAddresseeName(String addresseeName) {
        this.addresseeName = addresseeName;
    }

    public void setAddresserID(String addresserID) {
        this.addresserID = addresserID;
    }

    @Override
    public String getAddresserName() {
        return addresserName;
    }

    @Override
    public void setAddresserName(String addresserName) {
        this.addresserName = addresserName;
    }

    public BDHI_IMMESSAGE_TYPE getMessageType() {
        return messageType;
    }

    public void setMessageType(BDHI_IMMESSAGE_TYPE messageType) {
        this.messageType = messageType;
    }

    @Override
    public long getSendTime() {
        return serverTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public long getClientMessageID() {
        return clientMessageID;
    }

    public void setClientMessageID(long clientMessageID) {
        this.clientMessageID = clientMessageID;
    }

    @Override
    public IMMessageStatus getStatus() {
        return status;
    }

    public void setStatus(IMMessageStatus status) {
        this.status = status;
    }

    @Override
    public String getCompatibleText() {
        return compatibleText;
    }

    @Override
    public void setCompatibleText(String compatibleText) {
        this.compatibleText = compatibleText;
    }

    public String getNotificationText() {
        return notificationText;
    }

    @Override
    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    @Override
    public String getExtra() {
        return extra;
    }

    @Override
    public void setExtra(String extra) {
        this.extra = extra;
    }

    public byte[] getBody() {
        return this.body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getMsgView() {
        return msgView;
    }

    public void setMsgView(String msgView) {
        this.msgView = msgView;
    }

    public String getMsgTemplate() {
        return msgTemplate;
    }

    public void setMsgTemplate(String msgTemplate) {
        this.msgTemplate = msgTemplate;
    }

    public long getPreviousMsgID() {
        return previousMsgID;
    }

    public void setPreviousMsgID(long previousMsgID) {
        this.previousMsgID = previousMsgID;
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
    public int compareTo(BDHiIMMessage another) {
        if (another == null) {
            return Integer.MAX_VALUE;
        }
        return (int) (msgSeq - another.getMsgSeq());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((addresseeID == null) ? 0 : addresseeID.hashCode());
        result = prime * result + ((addresserID == null) ? 0 : addresserID.hashCode());
        result = prime * result + (int) (msgSeq ^ (msgSeq >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BDHiIMMessage other = (BDHiIMMessage) obj;
        if (addresseeID == null) {
            if (other.addresseeID != null)
                return false;
        } else if (!addresseeID.equals(other.addresseeID))
            return false;
        if (addresserID == null) {
            if (other.addresserID != null)
                return false;
        } else if (!addresserID.equals(other.addresserID))
            return false;
        if (msgSeq != other.msgSeq)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BDHiIMMessage [id=" + id + ", addresseeType=" + addresseeType + ", addresseeID=" + addresseeID
                + ", addresseeName=" + addresseeName + ", addresserID=" + addresserID + ", addresserName="
                + addresserName + ", messageType=" + messageType + ", sendTime=" + sendTime + ", serverTime="
                + serverTime + ", msgSeq=" + msgSeq + ", clientMessageID=" + clientMessageID + ", status=" + status
                + ", compatibleText=" + compatibleText + ", notificationText=" + notificationText + ", extra=" + extra
                + ", msgView=" + msgView + ", msgTemplate=" + msgTemplate + ", previousMsgID=" + previousMsgID
                + ", ineffective=" + ineffective + "]";
    }

}
