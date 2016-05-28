package com.baidu.im.frame.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.text.TextUtils;

import com.baidu.im.constant.Constant;
import com.baidu.im.constant.Constant.BuildMode;
import com.baidu.im.constant.Constant.EChannelType;

public class ConfigUtil {

    public static final String TAG = "ConfigUtil";

    private static String ip;

    private static int port;

    private static EChannelType channelType;

    private static boolean  mLoaded = false;
    
    public static void load() {
        JSONObject config;
        try {
            String json = null;

            // sdk can not be config when in online mode.
            if (Constant.buildMode != BuildMode.Online) {
                json = FileUtil.readStringFromFileInSdkFolder("config.txt");
            }
            if (TextUtils.isEmpty(json)) {
                config = generateConfig();
            } else {
                JSONTokener jsonParser = new JSONTokener(json);
                config = (JSONObject) jsonParser.nextValue();
            }
            ip = config.getString("ip");
            port = config.getInt("port");
            channelType = EChannelType.valueOf(config.getString("channel"));
        } catch (JSONException e) {
            LogUtil.e(TAG, e);
            throw new RuntimeException(e);
        }
    }

    public static String getHostIp() {
    	if(!mLoaded)
    	{
    		ConfigUtil.load();
    		mLoaded = true;
    	}
        return ip;
    }

    public static int getPort() {
    	if(!mLoaded)
    	{
    		ConfigUtil.load();
    		mLoaded = true;
    	}
        return port;
    }

    public static EChannelType getChannelType() {
    	if(!mLoaded)
    	{
    		ConfigUtil.load();
    		mLoaded = true;
    	}
        return channelType;
    }

    /**
     * Generate default configuration.
     * 
     * @return
     */
    private static JSONObject generateConfig() {
        JSONObject config = new JSONObject();
        try {
            config.put("ip", Constant.buildMode.getEChannelInfo().getIp());
            config.put("port", Constant.buildMode.getEChannelInfo().getPort());
            config.put("channel", Constant.buildMode.getEChannelInfo().getChannelType().name());

            // sdk can not be config when in online mode.
            if (Constant.buildMode != BuildMode.Online) {
                FileUtil.writeStringToFileInSdkFolder("config.txt", config.toString());
            }
        } catch (JSONException e) {
            LogUtil.e(TAG, e);
            throw new RuntimeException(e);
        }
        return config;
    }
}
