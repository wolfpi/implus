package com.baidu.im.frame;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.im.frame.inapp.ChannelSdkImpl;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.utils.DeviceInfoMapUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.MurmurHash3;
import com.baidu.im.sdk.IMessageCallback;

public abstract class BizBaseTransaction implements BizTransaction, TransactionLog {

    private JSONObject transactionObject = new JSONObject();
    private Map<String, JSONObject> processorMap = new HashMap<String, JSONObject>();
    private JSONArray processorArray = new JSONArray();
    private boolean   mTransactionCallbackfinish = false;

    @Override
    public abstract ProcessorResult startWorkFlow(IMessageCallback callback);

    @Override
    public abstract String getThreadName();

    /**
     * A processor start
     * 
     * @param processorName(should not be null)
     */
    @Override
    public void startProcessor(String processorName) {
        try {
            JSONObject processorObject = new JSONObject();
            long startTime = System.currentTimeMillis();
            processorObject.put(TIME, startTime);
            processorObject.put(NAME, processorName);
            processorMap.put(processorName, processorObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * A processor end.
     * 
     * @param processorName(should not be null)
     * @param seq of down package
     * @param processorResult
     */
    @Override
    public void endProcessor(String processorName, int seq, ProcessorResult processorResult) {
        if (processorMap.containsKey(processorName)) {
            JSONObject processorObject = processorMap.remove(processorName);
            if (null != processorObject && processorObject.has(TIME)) {
                try {
                    long startTime;
                    startTime = processorObject.getLong(TIME);
                    long endTime = System.currentTimeMillis();
                    long cost = endTime - startTime;
                    
                    String logstr = InAppApplication.getInstance().getSession().getChannel().getChannelKey() + seq;
                    processorObject.put(COST, cost);
                    processorObject.put(RCODE, processorResult.getProcessorCode().getCode());
                    processorObject.put(
                            ID,
                            String.valueOf(MurmurHash3.hash64(logstr.getBytes(), logstr.getBytes().length, 0)));
                    processorArray.put(processorObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Send callback when a transaction end
     * 
     * @param transactionHashCode
     * @param transactionResult
     */
    @Override
    public void transactionCallback(int transactionHashCode, ProcessorResult transactionResult) {
        try {
            long endTime = System.currentTimeMillis();
            long startTime = transactionObject.getLong(TIME);
            transactionObject.put(RCODE, transactionResult.getProcessorCode().getCode());
            transactionObject.put(COST, endTime - startTime);
            JSONObject clientObject =
                    DeviceInfoMapUtil.getStatClient(InAppApplication.getInstance().getContext(), new JSONObject());
            if (null != clientObject) {
                transactionObject.put(CLIENT, clientObject);
            }
            transactionObject.put(PROTOCOL, processorArray);
            LogUtil.printStat(transactionObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        if(!mTransactionCallbackfinish) {
        	ChannelSdkImpl.callback(transactionHashCode, transactionResult);
        	mTransactionCallbackfinish = true;
        }
    }

    /**
     * A transaction start.
     * 
     * @param callback
     */
    @Override
    public void transactionStart(int transactionHashCode) {
        long beginTime = System.currentTimeMillis();
        try {
            transactionObject.put(KEY, transactionHashCode);
            transactionObject.put(ACTION, getThreadName());
            transactionObject.put(TIME, beginTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
