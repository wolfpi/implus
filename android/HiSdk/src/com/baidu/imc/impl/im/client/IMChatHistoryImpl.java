package com.baidu.imc.impl.im.client;

import com.baidu.im.frame.utils.StringUtil;
import com.baidu.imc.callback.PageableResult;
import com.baidu.imc.client.IMChatHistory;
import com.baidu.imc.exception.InitializationException;
import com.baidu.imc.impl.im.store.IMsgStore;
import com.baidu.imc.impl.im.transaction.IMTransactionFlow;
import com.baidu.imc.message.IMMessage;
import com.baidu.imc.type.AddresseeType;

/**
 * 
 * <b>基本聊天接口</b>
 * <p>
 * 聊天接口对象用于描述一个聊天过程的记录，提供聊天过程记录的查询操作
 * </p>
 * 
 * @since 1.0
 * @author WuBin
 * 
 */
public class IMChatHistoryImpl implements IMChatHistory {
    protected IMsgStore msgStore;
    protected IMTransactionFlow transactionFlow;
    protected AddresseeType addresseeType;
    protected String addresseeID;
    protected String addresserID;

    // private String peerID;

    public IMChatHistoryImpl(IMsgStore msgStore, IMTransactionFlow transactionFlow, AddresseeType addresseeType,
            String addresseeID, String addresserID) {

        if (addresseeType == null || StringUtil.isStringInValid(addresserID) || StringUtil.isStringInValid(addresseeID)) {
            throw new InitializationException();
        }

        this.msgStore = msgStore;
        this.transactionFlow = transactionFlow;
        this.addresseeType = addresseeType;
        this.addresseeID = addresseeID;
        this.addresserID = addresserID;

    }

    @Override
    public PageableResult<IMMessage> getMessageList(int pageNum, int pageSize) {
        if (pageSize <= 0)
            return null;
        return msgStore.getMessageList(addresseeType, addresseeID, pageNum, pageSize);
    }

    @Override
    public void deleteAllMessage() {
        if (StringUtil.isStringInValid(addresseeID) || msgStore == null)
            return;
        msgStore.deleteAllMessage(addresseeType, addresseeID);
    }

    @Override
    public void deleteMessage(long messageID) {
        if (msgStore != null) {
            msgStore.deleteMessage(messageID, addresseeType, addresseeID);
        }
    }

    // public String getPeerID() {
    // return peerID;
    // }

    public IMTransactionFlow getTransactionFlow() {
        return transactionFlow;
    }

    public AddresseeType getAddresseeType() {
        return addresseeType;
    }

    public String getAddresseeID() {
        return addresseeID;
    }

    public String getAddresserID() {
        return addresserID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((addresseeID == null) ? 0 : addresseeID.hashCode());
        result = prime * result + ((addresseeType == null) ? 0 : addresseeType.hashCode());
        result = prime * result + ((addresserID == null) ? 0 : addresserID.hashCode());
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
        IMChatHistoryImpl other = (IMChatHistoryImpl) obj;
        if (addresseeID == null) {
            if (other.addresseeID != null)
                return false;
        } else if (!addresseeID.equals(other.addresseeID))
            return false;
        if (addresseeType != other.addresseeType)
            return false;
        if (addresserID == null) {
            if (other.addresserID != null)
                return false;
        } else if (!addresserID.equals(other.addresserID))
            return false;
        return true;
    }

}
