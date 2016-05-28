package com.baidu.im.frame.outapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.im.frame.BaseProcessor;
import com.baidu.im.frame.ProcessorCode;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.Transaction;
import com.baidu.im.frame.utils.DeviceInfoMapUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.outapp.OutAppApplication;

/**
 * Transaction基类，实现final的run方法。
 * 
 * 
 */
public abstract class OutAppBaseTransaction implements Transaction {

    private JSONArray protocolArray = new JSONArray();

    @Override
    public final void run() {
        long beginTime = System.currentTimeMillis();
        ProcessorResult processorResult = null;
        try {
            Thread.currentThread().setName(getThreadName());
            processorResult = startWorkFlow();
        } catch (Exception e) {
            LogUtil.printError(e);
            processorResult = new ProcessorResult(ProcessorCode.UNKNOWN_ERROR);
        } finally {
            long endTime = System.currentTimeMillis();
            JSONObject statObject = new JSONObject();
            try {
                statObject.put(TIME, endTime);
                statObject.put(KEY, this.hashCode());
                statObject.put(ACTION, getThreadName());
                statObject.put(RCODE, processorResult.getProcessorCode().getCode());
                statObject.put(COST, endTime - beginTime);
                JSONObject clientObject =
                        DeviceInfoMapUtil.getStatClient(OutAppApplication.getInstance().getContext(), new JSONObject());
                if (null != clientObject) {
                    statObject.put(CLIENT, clientObject);
                }
                statObject.put(PROTOCOL, protocolArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogUtil.printStat(statObject.toString());
            terminate();
        }

    }

    @Override
    public void terminate() {
    }

    
    @Override
    public ProcessorResult runProcessor(BaseProcessor processor) throws Exception {

    	/*
        long beginTime = System.currentTimeMillis();
        ProcessorResult result = processor.process();
        long endTime = System.currentTimeMillis();
        JSONObject protocolObj = new JSONObject();
        try {
            protocolObj.put(ID, processor.hashCode());
            protocolObj.put(NAME, processor.getProcessorName());
            protocolObj.put(COST, endTime - beginTime);
            protocolObj.put(RCODE, result.getProcessorCode().getCode());
            protocolObj.put(TIME, endTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        protocolArray.put(protocolObj);
        return result; */
    	return null;
    }
}
