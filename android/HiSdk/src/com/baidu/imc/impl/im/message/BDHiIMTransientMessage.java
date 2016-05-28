package com.baidu.imc.impl.im.message;

import com.baidu.imc.message.IMTransientMessage;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.IMMessageStatus;

public class BDHiIMTransientMessage implements IMTransientMessage {

    protected String addresseeID;
    protected AddresseeType addresseeType;
    protected String addresserID;
    protected String content;

    protected String addresserName;
    protected long sendTime;
    protected long serverTime;
    protected long messageID;
    protected IMMessageStatus status;

    @Override
    public String getAddresseeID() {
        return addresseeID;
    }

    @Override
    public AddresseeType getAddresseeType() {
        return addresseeType;
    }

    @Override
    public String getAddresserID() {
        return addresserID;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setAddresseeID(String addresseeID) {
        this.addresseeID = addresseeID;
    }

    public void setAddresserID(String addresserID) {
        this.addresserID = addresserID;
    }

    public void setAddresseeType(AddresseeType addresseeType) {
        this.addresseeType = addresseeType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddresserName() {
        return addresserName;
    }

    public void setAddresserName(String addresserName) {
        this.addresserName = addresserName;
    }

    public long getSendTime() {
        return sendTime;
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

    public long getMessageID() {
        return messageID;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    public IMMessageStatus getStatus() {
        return status;
    }

    public void setStatus(IMMessageStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BDHiIMTransientMessage [addresseeID=" + addresseeID + ", addresseeType=" + addresseeType
                + ", addresserID=" + addresserID + ", content=" + content + ", addresserName=" + addresserName
                + ", sendTime=" + sendTime + ", serverTime=" + serverTime + ", messageID=" + messageID + ", status="
                + status + "]";
    }

}
