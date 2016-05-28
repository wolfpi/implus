/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.frame;

import android.os.RemoteException;
import android.os.SystemClock;

import com.baidu.im.frame.inapp.ProcessorTimeout;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.utils.LogUtil;

/**
 * 封装了processor内通用的方法：<br>
 * 实现了网络收包监听，并按照seq匹配。<br>
 * 
 * 
 */
public class BaseProcessor implements Processor, SequenceListener {

    public static final String TAG = "BaseProcessor";

    private ProcessorTimeout timeout = new ProcessorTimeout();
    private int listenerSeq;
    protected DownPacket downPacket;
    protected UpPacket mUpPacket = null;
    private long mBeginTime = 0;
    private SequenceSender sequenceSender;
    private SequenceDispatcher sequenceDispatcher;
    // private JSONArray inAppProtocolArray = new JSONArray();
    private MessageResponser mMsgListener = null;

    public BaseProcessor(SequenceSender sequenceSender, SequenceDispatcher sequenceDispatcher,
            MessageResponser msgListener) {
        this.sequenceSender = sequenceSender;
        this.sequenceDispatcher = sequenceDispatcher;
        this.mMsgListener = msgListener;
        if (sequenceDispatcher != null)
            sequenceDispatcher.register(this);
    }

    @Override
    public ProcessorTimeout getTimeout() {
        return timeout;
    }

    @Override
    public boolean onReceive(DownPacket downPacket) {

        if (downPacket == null) {
            LogUtil.printMainProcess("回包为空");
            return false;
        }
        this.downPacket = downPacket;
        // long endTime = SystemClock.elapsedRealtime();
        // long timespan = endTime - mBeginTime;

        if (this.mUpPacket != null) {
            LogUtil.printMainProcess("收到回包  网络耗时=" + (SystemClock.elapsedRealtime() - mBeginTime) + ", seq="
                    + downPacket.getSeq() + ", methodname=" + mUpPacket.getMethodName());
        }
        /*
         * else if ( downPacket != null) { LogUtil.printMainProcess("收到回包  网络耗时=" + (SystemClock.elapsedRealtime() -
         * mBeginTime) + ", seq=" + downPacket.getSeq() + ", methodname=" + downPacket.getMethodName()); }
         */

        // JSONObject protocolObj = new JSONObject();
        // try {
        // protocolObj.put(
        // ID,
        // MurmurHash3.murmurhash3_x86_32(DeviceInfoUtil.getDeviceToken(InAppApplication.getInstance()
        // .getContext()) + listenerSeq));
        // protocolObj.put(NAME, mMsgListener.getProcessorName());
        // protocolObj.put(COST, timespan);
        // protocolObj.put(RCODE, downPacket.getBizPackage().getBizCode());
        // protocolObj.put(TIME, endTime);
        // } catch (JSONException e) {
        // e.printStackTrace();
        // }
        // inAppProtocolArray.put(protocolObj);
        //
        // JSONObject statObject = new JSONObject();
        // try {
        // JSONObject clientObject =
        // DeviceInfoMapUtil.getStatClient(InAppApplication.getInstance().getContext(), new JSONObject());
        // if (null != clientObject) {
        // statObject.put(CLIENT, clientObject);
        // }
        // statObject.put(PROTOCOL, inAppProtocolArray);
        // } catch (JSONException e) {
        // e.printStackTrace();
        // }
        // LogUtil.printStat(statObject.toString());

        LogUtil.printMainProcess("Message received  baseProcessor");
        if (mMsgListener != null) {
            // ProcessorResult result =
            mMsgListener.onReceive(downPacket, mUpPacket);
        }
        // ChannelSdkImpl.callback(downPacket.getSeq(),result);

        /*
         * LogUtil.printMainProcess("没有收到回包  协议总共耗时=" + (SystemClock.elapsedRealtime() - beginTime) + ", seq=" +
         * upPacket.getSeq() + ", methodname=" + upPacket.getMethodName());
         * 
         * LogUtil.printMainProcess("NetworkTimeout, seq=" + upPacket.getSeq() + ",serviceName=" +
         * upPacket.getServiceName() + ",methodName=" + upPacket.getMethodName() + ",  left retry times = " +
         * getTimeout().getLeftRetryTimes());
         */

        terminate();
        return true;
    }

    @Override
    public int getListenSeq() {
        return listenerSeq;
    }

    protected void setListenSeq(int listenerSeq) {
        this.listenerSeq = listenerSeq;
    }

    public boolean send(UpPacket upPacket) {

        if (upPacket == null) {
            return false;
        }

        setListenSeq(upPacket.getSeq());

        mBeginTime = SystemClock.elapsedRealtime();
        {
            try {
                if (sequenceSender != null) {
                    sequenceSender.send(upPacket);
                    return true;
                }
            } catch (RemoteException e) {
                LogUtil.printError(e);
                LogUtil.printMainProcess("NetworkError, seq=" + upPacket.getSeq() + ",serviceName="
                        + upPacket.getServiceName() + ",methodName=" + upPacket.getMethodName()
                        + ", processor send packet IoException" + e.getMessage());
            } catch (Exception e1) {
                LogUtil.printMainProcess("Quit by fatal error, seq=" + upPacket.getSeq() + ",serviceName="
                        + upPacket.getServiceName() + ",methodName=" + upPacket.getMethodName()
                        + ", processor send packet IoException" + e1.getMessage());
            }
        }

        return true;
		//it will rebind service, wait timeout to retry
    }

    /**
     * Need to be inherited.
     * 
     * @return
     * @throws Exception
     */

    protected void terminate() {
        sequenceDispatcher.unRegister(this);
    }

    @Override
    public void sendReconnect() {
        if (sequenceSender != null) {
            sequenceSender.sendReconnect();
        }
    }

}
