/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.outapp.network;

import java.io.IOException;
import java.util.Arrays;

import android.text.TextUtils;

import com.baidu.im.frame.INetworkChannelListener;
import com.baidu.im.frame.NetworkChannel;
import com.baidu.im.frame.outapp.OutMessagListener;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.outapp.OutAppApplication;
import com.baidu.im.outapp.OutAppConfig;
import com.baidu.im.outapp.network.hichannel.IEvtCallback;
import com.baidu.im.outapp.network.hichannel.LoginResult_T;
import com.baidu.im.outapp.network.hichannel.LoginState_T;
import com.baidu.im.outapp.network.hichannel.NetworkChange_T;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

/**
 * hicore-->Hichannel
 * 
 */
public class HiChannel implements NetworkChannel {

    public static final String TAG = "HiChannel";

    private INetworkChannelListener networkChannelListener;

    private static NetworkChannelStatus networkStatus = NetworkChannelStatus.Closed;
    private HiCoreManager hiCoreManager;
    private HiCoreNotifyCallback hiCoreNotifyCallback = new HiCoreNotifyCallback();
    // 要连接的服务器Ip地址
    private String hostIp;

    // 要连接的远程服务器在监听的端口
    private int hostListenningPort;
    private PreferenceUtil mPreference = null;
    private OutMessagListener mListener = null;

    /**
     * 构造函数
     * 
     * @throws IOException
     */
    public HiChannel(OutAppConfig outAppConfig, String ip, int port, PreferenceUtil preference,
            OutMessagListener listener) {
        LogUtil.printMainProcess(TAG, "start to initialize hichannel...");
        this.hostIp = ip;
        this.hostListenningPort = port;
        this.mListener = listener;
        mPreference = preference;
        hiCoreManager = new HiCoreManager(outAppConfig, mPreference);
        open(null, null);
    }

    @Override
    public synchronized int getSeq() {
        return 0;
    }

    @Override
    public void send(final UpPacket upPacket) throws IOException {
        if (getNetworkChannelStatus() == NetworkChannelStatus.Closed) {
            LogUtil.printMainProcess(TAG, "send error for the channel had been closed.");
        }
        hiCoreManager.send(upPacket.toByteArray(), upPacket.getSeq());
    }

    @Override
    public void receive(final DownPacket downPacket) {

        if (downPacket == null) {
            LogUtil.printError("received a null downPacket.", null);
            return;
        }
      //  new Thread() {
           // public void run() {
               mListener.onReceive(downPacket);
            //}
        //}.start();

    }

    @Override
    public void setNetworkChannelListener(INetworkChannelListener networkChannelListener) {
        this.networkChannelListener = networkChannelListener;
    }

    public void close() {
        if (getNetworkChannelStatus() != NetworkChannelStatus.Closed) {
            LogUtil.printMainProcess(TAG, "event: deinit channel.");
            hiCoreManager.deinitHicore();
            setNetworkChannelStatus(NetworkChannelStatus.Closed);
        }
    }

    @Override
    public synchronized void open(String[] ips, int[] ports) {
        if (getNetworkChannelStatus() == NetworkChannelStatus.Closed) {
            LogUtil.printMainProcess(TAG, "event: init channel.");
            hiCoreManager.initHicore(OutAppApplication.getInstance().getContext());
            hiCoreManager.setListener(hiCoreNotifyCallback);
            int result = hiCoreManager.connet();
            LogUtil.printMainProcess("success to connect hichannel." + result);
            setNetworkChannelStatus(NetworkChannelStatus.Connecting);
        }
    }

    public class HiCoreNotifyCallback extends IEvtCallback {

        private static final String TAG = "HiCoreNotifyCallback";

        protected void finalize() {
            LogUtil.printMainProcess(TAG, "HiCoreNotifyCallback::finalize()");
        }

        @Override
        public void notify(byte[] data, int len, int seq) {

            if (getNetworkChannelStatus() == NetworkChannelStatus.Closed) {
                LogUtil.printMainProcess(TAG, "notify error for the channel had been closed.");
            }
            try {

                if (data == null) {
                    LogUtil.printMainProcess("HiChannel", "receive a null data");
                    return;
                }

                if (data.length != len) {
                    LogUtil.printMainProcess("HiChannel", "data.length != len.  " + data.length + ":" + len);
                    return;
                }
                try {
                    DownPacket downPacket = ProtocolConverter.convertDownPacket(data);
                    // OutAppApplication.getInstance().getNetworkLayer().onReceive(downPacket);
                    receive(downPacket);
                    
                    // 将协议日志写文件，便于在test工程mock。
                    // NetworkLayer.saveProtocolFile(downPacket);

                } catch (InvalidProtocolBufferMicroException e1) {
                    LogUtil.e("HiChannel",
                            "receive a unkown packe, len = " + data.length + "  data =" + Arrays.toString(data));
                }
            } catch (Throwable t) {

                LogUtil.printMainProcess("hichannel error in message send to in APP", t.getMessage());
            }
        }

        public void notify(LoginState_T state, long err, LoginResult_T res) {

            if (getNetworkChannelStatus() == NetworkChannelStatus.Closed) {
                LogUtil.printMainProcess(TAG, "notify loginState error for the channel had been closed.");
            }
            try {
            	String channelKey = "";
                if (state == LoginState_T.LS_LOGGEDIN) {
                	channelKey = res.getChannelkey();
                    if (!TextUtils.isEmpty(channelKey)) {
                        LogUtil.printMainProcess("save channelkey. channelkey=" + channelKey);
                        OutAppApplication.getInstance().setChannelKey(channelKey);
                        //mPreference.saveChanneKey(channelKey);
                        if(mListener != null)
                        {
                        	mListener.onChannelKeyRecv(channelKey);
                        	//LogUtil.printMainProcess("get channelKeyis:"+ mPreference.getChannelkey());
                        }
                    }
                    else
                    {
                    	channelKey = mPreference.getChannelkey();
                    	if (!TextUtils.isEmpty(channelKey))
                    	{
                    		 if(mListener != null)
                             {
                    			OutAppApplication.getInstance().setChannelKey(channelKey);
                             	mListener.onChannelKeyRecv(channelKey);
                             	LogUtil.printMainProcess("get channelkey confirm:"+ channelKey);
                             }
                    	}
                    }
                    if (TextUtils.isEmpty(channelKey)) {
                        LogUtil.printMainProcess("hichannel error, did not return channelkey from hichannel.");
                    } else {
                        LogUtil.printMainProcess("hichannel is ready now.");
                        setNetworkChannelStatus(NetworkChannelStatus.Connected);
                        //OutAppApplication.getInstance().getTransactionFlow().outAppHeartbeat();
                    }
                } else if (state == LoginState_T.LS_UNLOGIN) {
                    // 掉线
                    LogUtil.printMainProcess("hichannel is offline now.");
                    setNetworkChannelStatus(NetworkChannelStatus.Disconnected);
                } else if (state == LoginState_T.LS_LOGGING || state == LoginState_T.LS_RETRYING) {
                    // 连接或者断网重连中
                    LogUtil.printMainProcess("hichannel is connecting...");
                    setNetworkChannelStatus(NetworkChannelStatus.Disconnected);
                } else if (state == LoginState_T.LS_RETRYCOUNTING) {
                    // 重连倒计时中
                    LogUtil.printMainProcess("hichannel is counting-down...");
                    setNetworkChannelStatus(NetworkChannelStatus.Disconnected);
                } else {
                    // ignore now
                }

            } catch (Throwable t) {

                LogUtil.printMainProcess("hichannel  status error error,", t.getMessage());
            }
        }

        public void onError(long err, int arg1, byte[] data, int len) {
            if (err == 1000005) {
                return;
            }
            LogUtil.printMainProcess("收到hichannel的回包~~~~~~~~~~~~~~~~~~~~~onError(long err, int arg1, byte[] data, int len)");
            LogUtil.printMainProcess(err + "  " + arg1 + "  " + Arrays.toString(data) + "  " + len);
        }

        public void onDnsLookup(byte[] data, int len, int tag) {
        }
    }

    @Override
    public void heartbeat() {
        if (getNetworkChannelStatus() == NetworkChannelStatus.Closed) {
            LogUtil.printMainProcess(TAG, "heartbeat error for the channel had been closed.");
            return;
        }
        LogUtil.printMainProcess("hi channel heart.");
        hiCoreManager.heartbeat();

        // 这次心跳完成，推迟心跳时间。
       // PreferenceUtil.save(PreferenceKey.lastHeartbeatTime, SystemClock.elapsedRealtime());
    }

    /**
     * Notify channel that network changed.
     */
    @Override
    public void networkChanged(int value) {
        if (getNetworkChannelStatus() == NetworkChannelStatus.Closed) {
            LogUtil.printMainProcess(TAG, "network change error for the channel had been closed.");
            hiCoreManager.networkChanged(NetworkChange_T.NW_DISCONNECTED);
            return;
        }
        LogUtil.printMainProcess("network changed. current status=" + value);

        if (value == 0) {
            hiCoreManager.networkChanged(NetworkChange_T.NW_DISCONNECTED);
        } else if (value == 1) {
            hiCoreManager.networkChanged(NetworkChange_T.NW_CONNECTED);
        }
    }

    @Override
    public void dump() {
        hiCoreManager.dumpSelf();
    }

    /**
     * Notify other that networkStatus changed?
     */
    private void setNetworkChannelStatus(NetworkChannelStatus networkChannelStatus) {
        networkStatus = networkChannelStatus;
        if (networkChannelListener != null) {
            networkChannelListener.onChanged(networkChannelStatus);
        }
    }

    @Override
    public NetworkChannelStatus getNetworkChannelStatus() {
        return networkStatus;
    }
}