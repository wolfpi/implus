package com.baidu.imc.impl.im.store;

import java.util.List;

import com.baidu.imc.callback.PageableResult;
import com.baidu.imc.listener.IMInboxListener;
import com.baidu.imc.message.IMInboxEntry;
import com.baidu.imc.message.IMMessage;
import com.baidu.imc.message.IMTransientMessage;
import com.baidu.imc.type.AddresseeType;

public interface IMsgStore {
    public void addIMInboxListener(IMInboxListener listener);

    public void removeIMInboxListener(IMInboxListener listener);

    public List<IMInboxEntry> getIMInboxEntryList();

    public IMInboxEntry getIMInbox(AddresseeType addresseeType, String addresseeID);

    public long saveIMMessage(IMMessage message);

    long saveIMMessage(IMMessage message, boolean updateInbox);

    public void saveIMTransientMessage(IMTransientMessage message);

    public void saveIMInboxEntryList(List<IMInboxEntry> entryList);

    public void deleteIMInboxEntry(AddresseeType addresseeType, String addresseeID);

    public void deleteIMInboxEntry(String key);

    public void deleteAllIMInboxEntry();

    public IMInboxEntry clearUnread(AddresseeType addresseeType, String addresseeID);

    public PageableResult<IMMessage> getMessageList(AddresseeType addresseeType, String addresseeID, int pageNum,
            int pageSize);

    public PageableResult<IMMessage> getMessageListByMsgId(AddresseeType addresseeType, String addresseeID,
            long beforeMessageID, int num);
    public PageableResult<IMMessage> getMessageListByMsgId2(AddresseeType addresseeType, String addresseeID,
            long beforeMessageID, int num);

    // public IMMessage getMessageByMsgId(AddresseeType addresseeType, String addresseeID, String addresserID,
    // long messageID);

    public IMMessage getMessageByDBId(long id);

    public IMMessage getLastSuccessfulMessage(AddresseeType addreseeType, String addreseeID);

    public void deleteAllMessage(AddresseeType addresseeType, String addresseeID);

    // public void deleteMessage(AddresseeType addresseeType, String addresseeID, long messageID);

    public void deleteMessage(long id, AddresseeType addresseeType, String addresseeID);

    public void saveSyncSuccessRange(AddresseeType addresseeType, String addresseeID, ISyncSuccessRange range);

    public List<ISyncSuccessRange> getSyncSuccessRanges(AddresseeType addresseeType, String addresseeID);

    public void clearAllDB();

    public static interface ISyncSuccessRange {
        public int getStartSeq();

        public int getEndSeq();
    }

    public long getLastQueryInboxTime();

    public void setLastQueryInboxTime(long lastQueryInboxTime);
}
