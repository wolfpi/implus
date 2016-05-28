package com.baidu.im.frame.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.im.frame.utils.PreferenceKey;

/**
 * For demonstrating how to build updateConfigReq or how to parse updateConfigRsp
 */
public class DynamicConfigUtil {
    public final static String CONFIG_LOG = "log";

    public static final String CONFIG_SERVER = "server";

    private static final String MD5 = "MD5";

    private static final String TIMESTAMP = "timestamp";

    /**
     * Get the timestamp of all config group
     * 
     * @return timestamp of all config group
     */
    public static long getConfigTimestamp() {
        String configStr = GlobalInstance.Instance().preferenceInstace().getString(PreferenceKey.imConfig);
        if (null != configStr && configStr.length() > 0) {
            try {
                JSONObject jsonObj = new JSONObject(configStr);
                if (jsonObj.has(TIMESTAMP)) {
                    String timestampStr = jsonObj.getString(TIMESTAMP);
                    try {
                        long timestamp = Long.parseLong(timestampStr);
                        return timestamp;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * save the timestamp for all config group
     * 
     * @param timestamp
     */
    public static void setConfigTimestamp(long timestamp) {
        if (timestamp > 0) {
            String timestampStr = Long.toString(timestamp);
            String configStr = GlobalInstance.Instance().preferenceInstace().getString(PreferenceKey.imConfig);
            try {
                JSONObject jsonObj;
                if (null != configStr && configStr.length() > 0) {
                    jsonObj = new JSONObject(configStr);
                } else {
                    jsonObj = new JSONObject();
                }
                jsonObj.put(TIMESTAMP, timestampStr);
                GlobalInstance.Instance().preferenceInstace().save(PreferenceKey.imConfig, jsonObj.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get the md5 string of config json string
     * 
     * @param configName
     * @return md5 string of config json string
     */
    public static String getConfigMD5(String configName) {
        if (null != configName && configName.length() > 0) {
            String configStr = GlobalInstance.Instance().preferenceInstace().getString(PreferenceKey.imConfig);
            if (null != configStr && configStr.length() > 0) {
                try {
                    JSONObject jsonObj = new JSONObject(configStr);
                    String MD5KeyName = configName + MD5;
                    if (jsonObj.has(MD5KeyName)) {
                        String configMD5 = jsonObj.getString(MD5KeyName);
                        return configMD5;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * Get the config json string
     * 
     * @param configName
     * @return config json string
     */
    public static String getConfig(String configName) {
        if (null != configName && configName.length() > 0) {
            String configStr = GlobalInstance.Instance().preferenceInstace().getString(PreferenceKey.imConfig);
            if (null != configStr && configStr.length() > 0) {
                try {
                    JSONObject jsonObj = new JSONObject(configStr);
                    if (jsonObj.has(configName)) {
                        return jsonObj.getString(configName);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * Save the config MD5 and config json string into SharePreference
     * 
     * @param configName
     * @param configMD5
     * @param configJson
     */
    public static void setConfig(String configName, String configMD5, String configJson) {
        if (null != configName && configName.length() > 0 && null != configMD5 && configMD5.length() > 0
                && null != configJson && configJson.length() > 0) {
            String configStr = GlobalInstance.Instance().preferenceInstace().getString(PreferenceKey.imConfig);
            try {
                JSONObject jsonObj;
                if (null != configStr && configStr.length() > 0) {
                    jsonObj = new JSONObject(configStr);
                } else {
                    jsonObj = new JSONObject();
                }
                jsonObj.put(configName, new JSONObject(configJson));
                jsonObj.put(configName + MD5, configMD5);
                GlobalInstance.Instance().preferenceInstace().save(PreferenceKey.imConfig, jsonObj.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void clearConfig() {
    	GlobalInstance.Instance().preferenceInstace().remove(PreferenceKey.imConfig);
    }
}
