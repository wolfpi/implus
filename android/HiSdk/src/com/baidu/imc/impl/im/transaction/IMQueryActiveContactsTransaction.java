package com.baidu.imc.impl.im.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;

import com.baidu.im.frame.pb.ObjOneMsg.OneMsg;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.impl.im.message.BDHiIMMessage;
import com.baidu.imc.impl.im.message.IMInboxEntryImpl;
import com.baidu.imc.impl.im.message.OneMsgConverter;
import com.baidu.imc.impl.im.store.IMsgStore;
import com.baidu.imc.impl.im.transaction.processor.IMQueryActiveContactsProcessor;
import com.baidu.imc.impl.im.transaction.processor.IMQueryMsgsProcessor2;
import com.baidu.imc.impl.im.transaction.processor.callback.QueryActiveContactsCallback;
import com.baidu.imc.impl.im.transaction.processor.callback.QueryMsgCallback;
import com.baidu.imc.impl.im.transaction.request.QueryActiveContactsRequest;
import com.baidu.imc.impl.im.transaction.request.QueryMsgsRequest2;
import com.baidu.imc.impl.im.transaction.response.QueryActiveContactsResponse;
import com.baidu.imc.impl.im.transaction.response.QueryMsgsResponse;
import com.baidu.imc.impl.im.util.ChatID;
import com.baidu.imc.message.IMInboxEntry;
import com.baidu.imc.message.IMMessage;

public class IMQueryActiveContactsTransaction implements IMTransactionStart, QueryActiveContactsCallback,
        QueryMsgCallback {

    private static final String TAG = "QueryActiveContacts";
    private String myUserID;
    private int needReturnMsgsContactNum;
    private IMsgStore msgStore;

    private long lastQueryTime;
    private Map<String, IMInboxEntry> entryMap;
    private int requestTimes = 0;
    private QueryActiveContactsCallback selfCallback = this;

    public IMQueryActiveContactsTransaction(String myUserID, int needReturnMsgsContactNum, IMsgStore msgStore) {
        this.myUserID = myUserID;
        this.needReturnMsgsContactNum = needReturnMsgsContactNum;
        this.msgStore = msgStore;
    }

    @Override
    public void startWorkFlow() throws Exception {
    	new Thread(new Runnable() {
    		 
    		@Override
    		public void run() {
    			 LogUtil.printIm(getThreadName(), "IMQueryActiveContactsTransaction transactionId=" + this.hashCode());
    		        LogUtil.printIm(getThreadName(), "Send QueryActiveContacts.");
    		        QueryActiveContactsRequest queryActiveContactsRequest =
    		                new QueryActiveContactsRequest(null != msgStore ? msgStore.getLastQueryInboxTime() : 0,
    		                        needReturnMsgsContactNum);
    		        IMQueryActiveContactsProcessor queryActiveContactsProcessor =
    		                new IMQueryActiveContactsProcessor(myUserID, queryActiveContactsRequest, selfCallback);
    		        try {
						queryActiveContactsProcessor.startWorkFlow();
					} catch (Exception e) {
						e.printStackTrace();
					}
    		}
    	}).start();
       
    }

    public String getThreadName() {
        return TAG;
    }

    @Override
    public void onQueryActiveContactsCallback(QueryActiveContactsResponse queryActiveContactsResponse) {
        LogUtil.printIm(getThreadName(), "Receive QueryActiveContacts response.");
        if (null != queryActiveContactsResponse && queryActiveContactsResponse.getErrCode() == 0) {
            List<IMInboxEntry> entryList = queryActiveContactsResponse.getEntryList();
            entryMap = queryActiveContactsResponse.getEntryMap();
            lastQueryTime = queryActiveContactsResponse.getLastQueryTime();
            if (null != entryList && entryList.size() > 0) {
                int totalSize = entryList.size();
                int times = totalSize / 10;
                int last = totalSize % 10;
                if (last != 0) {
                    times += 1;
                }
                requestTimes = times;
                LogUtil.printIm(getThreadName(), "Get entry list. Prepare QueryMsgsRequest. " + requestTimes);
                for (int i = 1; i <= times; i++) {
                    int end = 0;
                    if (i == times && last != 0) {
                        end = totalSize;
                    } else {
                        end = i * 10;
                    }
                    LogUtil.printIm(getThreadName(), "Send QueryMsgs Request. " + i);
                    List<IMInboxEntry> subList = entryList.subList((i - 1) * 10, end);
                    QueryMsgsRequest2 queryMsgsRequest2 = new QueryMsgsRequest2(TAG, myUserID, subList);
                    IMQueryMsgsProcessor2 queryMsgsProcessor = new IMQueryMsgsProcessor2(TAG, queryMsgsRequest2, this);
                    try {
                        queryMsgsProcessor.startWorkFlow();
                    } catch (Exception e) {
                        LogUtil.printImE(getThreadName(), "QueryMsgs Exception", e);
                    }
                }
            } else {
                LogUtil.printIm(getThreadName(), "Empty entry list.");
            }
        } else {
            LogUtil.printIm(getThreadName(), "QueryActiveContacts error. Can not get response or response error.");
        }
    }

    @Override
    public void onQueryMsgCallback(QueryMsgsResponse queryMsgsResponse) {
        LogUtil.printIm(getThreadName(), "Receive QueryMsgs response. " + requestTimes);
        requestTimes--;
        if (null != queryMsgsResponse && queryMsgsResponse.getErrCode() == 0) {
            if (null != queryMsgsResponse.getPageableResult().getList()
                    && queryMsgsResponse.getPageableResult().getList().size() > 0) {
                List<IMInboxEntry> entryList = new ArrayList<IMInboxEntry>();
                for (IMMessage imMessage : queryMsgsResponse.getPageableResult().getList()) {
                    if (null != imMessage) {
                        String key =
                                ChatID.getChatID(imMessage.getAddresseeType(), imMessage.getAddresseeID(),
                                        imMessage.getAddresserID());
                        if (!TextUtils.isEmpty(key)) {
                            IMInboxEntryImpl inboxEntry = (IMInboxEntryImpl) entryMap.get(key);
                            if (null != inboxEntry) {
                                LogUtil.printIm(getThreadName(), "Get an IMInboxEntry " + key);
                                inboxEntry.setLastMessage((BDHiIMMessage) imMessage);
                                OneMsg oneMsgBuilder =
                                        OneMsgConverter.convertIMMessage((BDHiIMMessage) imMessage);
                                inboxEntry.setMsgBody(null != oneMsgBuilder ? oneMsgBuilder.toByteArray()
                                        : null);
                                if(inboxEntry != null)
                                	entryList.add(inboxEntry);
                            }
                        }
                    }
                }
                LogUtil.printIm(getThreadName(),
                        "Save entry list and notify Inbox Manager. entryListSize" + entryList.size());
                msgStore.saveIMInboxEntryList(entryList);
            }
        }
        if (requestTimes == 0) {
            LogUtil.printIm(getThreadName(), "Save LastQueryTime. " + lastQueryTime);
            msgStore.setLastQueryInboxTime(lastQueryTime);
        } else {
            LogUtil.printIm(getThreadName(), "Can not save LastQueryTime. " + requestTimes
                    + " QueryMsgsResponses left.");
        }
    }
}
