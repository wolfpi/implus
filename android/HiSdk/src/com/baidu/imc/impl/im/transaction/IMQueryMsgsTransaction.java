package com.baidu.imc.impl.im.transaction;

import java.util.List;

import android.support.v4.util.LongSparseArray;
import android.text.TextUtils;

import com.baidu.im.frame.IMCTransactionTimeout;
import com.baidu.im.frame.ITransactionTimeoutCallback;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.callback.PageableResult;
import com.baidu.imc.callback.PageableResultCallback;
import com.baidu.imc.impl.im.callback.PageableResultImpl;
import com.baidu.imc.impl.im.message.BDHiIMMessage;
import com.baidu.imc.impl.im.store.IMsgStore;
import com.baidu.imc.impl.im.store.MemoryMsgStore;
import com.baidu.imc.impl.im.transaction.processor.IMQueryMsgsProcessor;
import com.baidu.imc.impl.im.transaction.processor.callback.QueryMsgCallback;
import com.baidu.imc.impl.im.transaction.request.QueryMsgsRequest;
import com.baidu.imc.impl.im.transaction.response.QueryMsgsResponse;
import com.baidu.imc.message.IMMessage;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.IMMessageStatus;

public class IMQueryMsgsTransaction implements QueryMsgCallback, IMTransactionStart, ITransactionTimeoutCallback {

    private static final String TAG = "QueryMsgs";

    private IMsgStore msgStore;
    private AddresseeType addresseeType;
    private String addresseeID;
    private String addresserID;
    private long endID;
    private int count;
    private int timeout;
    private PageableResultCallback<IMMessage> callback;
    private PageableResult<IMMessage> mResult = null;
    private IMCTransactionTimeout mTransationCallback = null;
    private boolean mCallbackFinished = false;
    private QueryMsgCallback selfcallback = this;

    public IMQueryMsgsTransaction(IMsgStore msgStore, AddresseeType addresseeType, String addresseeID,
            String addresserID, long endID, int count, int timeout, PageableResultCallback<IMMessage> callback) {
        this.msgStore = msgStore;
        this.addresseeType = addresseeType;
        this.addresseeID = addresseeID;
        this.addresserID = addresserID;
        this.endID = endID;
        this.count = count;
        this.timeout = timeout;
        this.callback = callback;
        if(timeout > 0) {
        	mTransationCallback = new IMCTransactionTimeout(this,timeout);
        }
    }

    private void transactionCallback(PageableResult<IMMessage> result) {
    	mTransationCallback.stopCountDown();
    	if(!mCallbackFinished && (callback != null)) {
    		mCallbackFinished = true;
    		callback.result(result, null);	
    	}
    }
    
    @Override
    public void startWorkFlow() throws Exception {
    	new Thread(new Runnable() {
      		 
    		@Override
    		public void run() {
    			 LogUtil.printIm(getThreadName(), "IMQueryMsgsTransaction transactionId=" + this.hashCode());
    		        PageableResult<IMMessage> result = new PageableResultImpl() ;

    		        // 1. 保证消息ID由小到大 查找断点
    		        if (count == 0) {
    		            LogUtil.printIm(getThreadName(), "Params error.");
    		            transactionCallback(result);
    		            return;
    		        } else {
    		            LogUtil.printIm(getThreadName(), "Params endID:" + endID + " count:" + count);
    		        }
    		        result = msgStore.getMessageListByMsgId2(addresseeType, addresseeID, endID, count);
    		        mResult = result;
    		        mTransationCallback.startCountDown();
    		        if (timeout <= 0) {
    		            LogUtil.printIm(getThreadName(), "Do not request form server.");
    		            
    		            transactionCallback(result);
    		            return;
    		        } else {
    		            LogUtil.printIm(getThreadName(), "Timeout:" + timeout);
    		        }
    		        if (null != result && null != result.getList() && !result.getList().isEmpty()) {
    		            LogUtil.printIm(getThreadName(), "Result.getList() size:" + result.getList().size());
    		            long localStartSeq = ((BDHiIMMessage) (result.getList().get(0))).getMsgSeq();
    		            long localEndSeq = localStartSeq;
    		            for (IMMessage imMessage : result.getList()) {
    		                long seq = ((BDHiIMMessage) imMessage).getMsgSeq();
    		                if (seq < localStartSeq) {
    		                    localStartSeq = seq;
    		                }
    		                if (seq > localEndSeq) {
    		                    localEndSeq = seq;
    		                }
    		            }
    		            if (endID == 0) {
    		                LogUtil.printIm(getThreadName(), "Need to request form server even if it don't contain any gaps.");
    		            } else if (localEndSeq - localStartSeq == count - 1) {
    		                LogUtil.printIm(getThreadName(), "Do not need to request form server. localStartSeq:" + localStartSeq
    		                        + " localEndSeq:" + localEndSeq + " count:" + count);
    		                transactionCallback(result);
    		                return;
    		            } else {
    		                LogUtil.printIm(getThreadName(), "Found gap locally between localStartSeq:" + localStartSeq
    		                        + " and localEndSeq:" + localEndSeq + " count:" + count);
    		            }
    		        } else {
    		            LogUtil.printIm(getThreadName(), "Can't get result from local.");
    		        }

    		        // 2. get msgSeq
    		        BDHiIMMessage lastMessage = (BDHiIMMessage) msgStore.getMessageByDBId(endID);
    		        long startSeq = 0;
    		        long endSeq = 0;
    		        if (null != lastMessage) {
    		            endSeq = lastMessage.getMsgSeq();
    		            if (endSeq == 1) {
    		            	transactionCallback(result);
    		                LogUtil.printIm(getThreadName(), "It is already the last message. endSeq" + endSeq);
    		                return;
    		            }
    		            endSeq -= 1;
    		        }
    		        LogUtil.printIm(getThreadName(), "Params endSeq:" + endSeq);
    		        LogUtil.printIm(getThreadName() + endSeq);
    		        // 3. send queryMsgsRequest
    		        QueryMsgsRequest queryMsgsRequest =
    		                new QueryMsgsRequest(TAG, addresseeType, addresseeID, addresserID, startSeq, endSeq, count, callback);
    		        IMQueryMsgsProcessor queryMsgsProcessor = new IMQueryMsgsProcessor(TAG, queryMsgsRequest, selfcallback);

    		        try {
						queryMsgsProcessor.startWorkFlow();
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
    public void onQueryMsgCallback(QueryMsgsResponse queryMsgsResponse) {
        if (null != callback) {
            if (null != queryMsgsResponse && queryMsgsResponse.getErrCode() == 0) {
                LogUtil.printIm(getThreadName(), "Return server result.");
                PageableResultImpl result = new PageableResultImpl();
                LongSparseArray<IMMessage> array = new LongSparseArray<IMMessage>();
                int msgResultSize = 0;
				List<IMMessage> list = queryMsgsResponse.getPageableResult().getList();
				for (IMMessage imMessage : list) {
                    // save msg
                    if (null != imMessage && !TextUtils.isEmpty(imMessage.getAddresseeID())
                            && !TextUtils.isEmpty(addresserID)) {
                        if (addresserID.equals(imMessage.getAddresseeID())) {
                            ((BDHiIMMessage) imMessage).setStatus(IMMessageStatus.READ);
                        } else {
                            ((BDHiIMMessage) imMessage).setStatus(IMMessageStatus.SENT);
                        }
                        long msgID = ((MemoryMsgStore) msgStore).saveIMMessage(imMessage, true, true);
                        if (msgID > -1) {
                            ((BDHiIMMessage) imMessage).setMessageID(msgID);
                            if (null == array.get(msgID)) {
                                array.put(msgID, imMessage);
                                result.addMessage(imMessage);
                                msgResultSize++;
                            }
                        }
                        LogUtil.printIm(TAG, "Receive message. MsgId:" + msgID + " msgSeq:"
                                + ((BDHiIMMessage) imMessage).getMsgSeq() + " clientMsgId:"
                                + ((BDHiIMMessage) imMessage).getClientMessageID() + " msgResultSize" + msgResultSize);
                    }
                }
                LogUtil.printIm(TAG, "Result endID:" + endID + " msgResultSize:" + msgResultSize);
                if (msgResultSize <= 0) {
                    LogUtil.printIm(TAG, "Can not get msgs from server, try to get local msgs.");
                }
                result =
                        (PageableResultImpl) msgStore.getMessageListByMsgId2(addresseeType, addresseeID, endID,
                                count);
                transactionCallback(result);
            } else {
                LogUtil.printIm(getThreadName(), "Params error or request timeout, return local result.");
                transactionCallback(mResult);
            }
        }

    }

	@Override
	public void onTimeOut() {
		 LogUtil.printDebug(TAG, "query timeout return local");
		 transactionCallback(mResult);
	}

}
