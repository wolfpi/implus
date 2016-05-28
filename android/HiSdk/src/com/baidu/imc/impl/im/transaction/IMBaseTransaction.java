package com.baidu.imc.impl.im.transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.impl.im.transaction.processor.IMProcessor;

public abstract class IMBaseTransaction implements IMTransaction {

    private JSONArray imProtocolArray = new JSONArray();

    @Override
    public final void run() {
        long beginTime = System.currentTimeMillis();
        try {
            Thread.currentThread().setName(getThreadName());
            startWorkFlow();
        } catch (Throwable e) {
            LogUtil.printImE(getThreadName(), e);
        } finally {
            long endTime = System.currentTimeMillis();
            JSONObject statObject = new JSONObject();
            try {
                statObject.put(KEY, this.hashCode());
                statObject.put(ACTION, getThreadName());
                statObject.put(COST, endTime - beginTime);
                statObject.put(TIME, endTime);
                statObject.put(PROTOCOL, imProtocolArray);
            } catch (JSONException e) {
                LogUtil.printImE(getThreadName(), e);
            }
            LogUtil.printIm(statObject.toString());
            terminate();
        }
    }

    @Override
    public void terminate() {

    }

    @Override
    public void runProcessor(IMProcessor processor) throws Exception {

        long beginTime = System.currentTimeMillis();
        processor.process();
        long endTime = System.currentTimeMillis();
        JSONObject protocolObj = new JSONObject();
        try {
            protocolObj.put(ID, processor.hashCode());
            protocolObj.put(NAME, processor.getProcessorName());
            protocolObj.put(COST, endTime - beginTime);
            protocolObj.put(TIME, endTime);
        } catch (JSONException e) {
            LogUtil.printImE(getThreadName(), e);
        }
        imProtocolArray.put(protocolObj);
    }

}
