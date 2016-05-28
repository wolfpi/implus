package com.baidu.im.dummy;

import java.util.List;

import com.baidu.imc.callback.PageableResult;
import com.baidu.imc.impl.im.store.IMsgStore;
import com.baidu.imc.listener.IMInboxListener;
import com.baidu.imc.message.IMInboxEntry;
import com.baidu.imc.message.IMMessage;
import com.baidu.imc.message.IMTransientMessage;
import com.baidu.imc.type.AddresseeType;

public class DummyMsgStore implements IMsgStore {

	@Override
	public void addIMInboxListener(IMInboxListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeIMInboxListener(IMInboxListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IMInboxEntry> getIMInboxEntryList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMInboxEntry getIMInbox(AddresseeType addresseeType,
			String addresseeID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long saveIMMessage(IMMessage message) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long saveIMMessage(IMMessage message, boolean updateInbox) {
		return 0;
	}

	@Override
	public void saveIMTransientMessage(IMTransientMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveIMInboxEntryList(List<IMInboxEntry> entryList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteIMInboxEntry(AddresseeType addresseeType,
			String addresseeID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteIMInboxEntry(String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllIMInboxEntry() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IMInboxEntry clearUnread(AddresseeType addresseeType,
			String addresseeID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageableResult<IMMessage> getMessageList(
			AddresseeType addresseeType, String addresseeID, int pageNum,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageableResult<IMMessage> getMessageListByMsgId(
			AddresseeType addresseeType, String addresseeID,
			long beforeMessageID, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	public IMMessage getMessageByMsgId(AddresseeType addresseeType,
			String addresseeID, String addresserID, long messageID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMMessage getMessageByDBId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllMessage(AddresseeType addresseeType, String addresseeID) {
		// TODO Auto-generated method stub
		
	}

	public void deleteMessage(AddresseeType addresseeType, String addresseeID,
			long messageID) {
		// TODO Auto-generated method stub
		
	}

	public void deleteMessage(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveSyncSuccessRange(AddresseeType addresseeType,
			String addresseeID, ISyncSuccessRange range) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ISyncSuccessRange> getSyncSuccessRanges(
			AddresseeType addresseeType, String addresseeID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearAllDB() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getLastQueryInboxTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLastQueryInboxTime(long lastQueryInboxTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IMMessage getLastSuccessfulMessage(AddresseeType addreseeType,
			String addreseeID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMessage(long id, AddresseeType addresseeType,
			String addresseeID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageableResult<IMMessage> getMessageListByMsgId2(
			AddresseeType addresseeType, String addresseeID,
			long beforeMessageID, int num) {
		// TODO Auto-generated method stub
		return null;
	}

}
