package com.baidu.hi.sdk.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

public class ResponsesParser {

    private static final String TAG = "ResponsesParser";

    public static Map<String, String> parseResponseString(String responseString) {
        Map<String, String> responseMap = new HashMap<String, String>();
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            parseJSONObject(responseMap, jsonObject);
        } catch (Exception e) {
            Log.e(TAG, "parseResponseString", e);
        }
        return responseMap;
    }

    private static void parseJSONObject(Map<String, String> responseMap, JSONObject jsonObject) {
        try {
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (!TextUtils.isEmpty(key)) {
                    Object valueObject = jsonObject.get(key);
                    if (valueObject instanceof JSONArray) {
                        // jsonArray
                        parseJSONArray(responseMap, key, (JSONArray) valueObject);
                    } else if (valueObject instanceof JSONObject) {
                        // jsonObject
                        parseJSONObject(responseMap, (JSONObject) valueObject);
                    } else {
                        // string
                        parseObject(responseMap, key, valueObject);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "parseJSONObject", e);
        }
    }

    private static void parseJSONArray(Map<String, String> responseMap, String key, JSONArray jsonArray) {
        try {
            responseMap.put(key, jsonArray.toString());
        } catch (Exception e) {
            Log.e(TAG, "parseJSONArray", e);
        }
    }

    private static void parseObject(Map<String, String> responseMap, String key, Object value) {
        try {
            responseMap.put(key, String.valueOf(value));
        } catch (Exception e) {
            Log.e(TAG, "parseObject", e);
        }
    }
}
