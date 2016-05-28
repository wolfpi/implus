package com.baidu.im.outapp.network;

import org.json.JSONObject;

import android.content.Context;

import com.baidu.im.frame.utils.DeviceInfoMapUtil;
import com.baidu.im.frame.utils.DeviceInfoUtil;
import com.baidu.im.frame.utils.HiChannelConfigUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.frame.utils.StringUtil;
import com.baidu.im.outapp.OutAppConfig;
import com.baidu.im.outapp.RUN_MODE;
import com.baidu.im.outapp.network.HiChannel.HiCoreNotifyCallback;
import com.baidu.im.outapp.network.hichannel.Channelinfo;
import com.baidu.im.outapp.network.hichannel.HiCoreEnv;
import com.baidu.im.outapp.network.hichannel.HiCoreSession;
import com.baidu.im.outapp.network.hichannel.NetworkChange_T;

public class HiCoreManager {
    public static final String TAG = "HiCoreManager";

    public int isEnableHicoreLog = 1; // 0 or 1 to disable or enable hicore log,

    // for hi core
    private HiCoreEnv hiCoreEnv = new HiCoreEnv();
    private HiCoreSession hiCoreSession = null;
    private HiCoreLogCallback hiCoreLogCallback;
    private PreferenceUtil   mPref = null;
    private OutAppConfig outAppConfig;

    public HiCoreManager() {
    }

    public HiCoreManager(OutAppConfig outAppConfig, PreferenceUtil pref) {
        this.outAppConfig = outAppConfig;
        this.mPref = pref;
    }
    
    public void dumpSelf() {
        if (null != hiCoreSession) {
            hiCoreSession.dumpSelf();
        }
    }

    private Channelinfo prepareConfig(Context context) {
        
        //JSONObject jsonObj = HiChannelConfigUtil.readConfig(outAppConfig, context);
        
       // Channelinfo channelinfo = new Channelinfo();
       // channelinfo.setChannelKey(value);
        
        //PreferenceUtil globalPreference = new PreferenceUtil();
        //globalPreference.initialize(context, null);
        //mPref.saveChanneKey("");
        String channelKey = "";
        String deviceToken = mPref.getDeviceToken();
        if (StringUtil.isStringInValid(deviceToken) || (!deviceToken.endsWith("@@@"))) {
            deviceToken = DeviceInfoUtil.getDeviceToken(context);
            if(deviceToken.endsWith("@@@")) {
            	mPref.saveDeviceToken(deviceToken);
            }
        }
        LogUtil.printMainProcess("HiChannel", "prepare config: channelKey=" + channelKey + ", DeviceToken="
                + deviceToken);
        
        /*HiChannelConfigUtil.setChannelKey(jsonObj, channelKey, deviceToken);*/
        
        Channelinfo channelinfo = new Channelinfo();
        DeviceInfoMapUtil.getChannelinfo(context, channelinfo);
        
        if(channelinfo.getOsversion() == null)
        	channelinfo.setOsversion("");
        channelinfo.setExtraInfo("");
        channelinfo.setChannelKey(channelKey);
        channelinfo.setDeviceToken(deviceToken);
        channelinfo.setSdkversion("AND_1.1.1.1");
        
        return channelinfo;
       // return jsonObj.toString();
    }

    public int initHicore(Context context) {

        if (hiCoreEnv == null) {
            hiCoreEnv = new HiCoreEnv();
        }
        hiCoreEnv.initEnv("");

        hiCoreEnv.enableLog(isEnableHicoreLog);
        hiCoreLogCallback = new HiCoreLogCallback();
        hiCoreEnv.set_log_callback(hiCoreLogCallback);

        Channelinfo cinfo = prepareConfig(context);
        //LogUtil.i(TAG, data);
        hiCoreEnv.initChannelInfo(cinfo);
        hiCoreEnv.initIpist(HiChannelConfigUtil.readIpList(outAppConfig));
        if(outAppConfig.getRunMode() == RUN_MODE.PRODUCT)
        {
        	hiCoreSession = hiCoreEnv.createSession(null);
        }
        else
        {
        	hiCoreSession = hiCoreEnv.createSession("8001");
        }
        hiCoreSession.initSession();

        return 0;
    }

    public void setListener(HiCoreNotifyCallback hiCoreNotifyCallback) {

        if (hiCoreSession != null) {
            hiCoreSession.set_notify_callback(hiCoreNotifyCallback);// event callback, data comes
        }
    }

    public int connet() {
        if (hiCoreSession != null) {
            LogUtil.printMainProcess("HiCoreManager", "connet");
            return hiCoreSession.connect();
        }
        return -1;
    }

    public boolean send(byte[] bytes, int seq) {
        if (hiCoreSession != null) {
            return hiCoreSession.postMessage(bytes, bytes.length, seq);
        }
        return false;
    }

    void deinitHicore() {

        LogUtil.printMainProcess("HiCoreManager", "close");
        if (null != hiCoreSession) {
            hiCoreSession.deinitSession();

            hiCoreEnv.destroySession(hiCoreSession);

            if (null != hiCoreEnv) {
                hiCoreEnv.deinitEnv();
            }

            hiCoreSession = null;
            hiCoreEnv = null;
        }
    }

    public void disconnect() {
        if (null != hiCoreSession) {
            hiCoreSession.disconnect(true);
        }
    }

    public void heartbeat() {
        if (null != hiCoreSession) {
            hiCoreSession.sendKeepAlive();
        }
    }

    /**
     * 通知 hi core 网络变化
     */
    public void networkChanged(NetworkChange_T networkChange_T) {

        if (null != hiCoreSession) {
            hiCoreSession.networkChanged(networkChange_T);
        }
    }

}
