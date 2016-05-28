package com.baidu.im.frame.utils;

import com.baidu.im.outapp.OutAppConfig;
import com.baidu.im.outapp.RUN_MODE;
import com.baidu.im.outapp.network.hichannel.IpList;

public class HiChannelConfigUtil {
	
	public static IpList readIpList(OutAppConfig outAppConfig)
	{
		IpList ips = new IpList();
		 if (outAppConfig.getRunMode() == RUN_MODE.PRODUCT) {
	            ips.setIp1("119.75.222.74");
	        } else {
	        	ips.setIp1("10.44.88.50");
	        }
		 ips.setIp2(null);
		 ips.setIp3(null);
		 ips.setIp4(null);
		 ips.setIp5(null);
		return ips;
	}

	/*
    public static JSONObject readConfig(OutAppConfig outAppConfig, Context context) {

        if (context == null) {
            return null;
        }

        String config;
        if (outAppConfig.getRunMode() == RUN_MODE.PRODUCT) {
            config = HiChannelConfigProduct.jsonStr;
        } else {
            config = HiChannelConfigDev.jsonStr;
        }

        if (config == null || config.length() <= 0) {
            return null;
        }

        JSONObject jsonObj = null;

        try {
            // the config info. should be read from a config file, later
            jsonObj = new JSONObject(config);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public static JSONObject setChannelKey(JSONObject jsonObj, String channelKey, String deviceToken) {

        if (jsonObj == null) {
            return null;
        }
        JSONObject channelObj;
        try {
            channelObj = jsonObj.getJSONObject("channel");
            if (channelObj != null) {
                channelObj.put("key", channelKey);
                channelObj.put("token", deviceToken);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }*/

}
