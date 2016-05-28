package com.baidu.imc;

import android.content.Context;
import android.util.Log;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.client.PushClient;
import com.baidu.imc.impl.push.client.PushClientImpl;

public class IMPChannelSDK {
    private static PushClientImpl pushClient = null;
    private final static String TAG = "IMPChannelSDK";
    private static String mAppkey = null;

    public static void init(String appKey, Context context) {

        if (appKey == null || appKey.isEmpty() || context == null) {
            LogUtil.e(TAG, "ClientID or context is incorrect");
            return;
        }

        if (mAppkey != null)
            return;
        mAppkey = appKey;
        try {
            if (pushClient == null) {
                synchronized (IMPChannelSDK.class) {
                    if (pushClient == null) {
                        pushClient = new PushClientImpl();
                        pushClient.init(context, appKey);
                    }
                }
            }
        } catch (Throwable t) {
            Log.e("IMPChannelSDK", t.getMessage(), t);
        }
    }

    public static PushClient getPushClient() {
        if (pushClient == null) {
            LogUtil.e(TAG, "Channel did not initialized correctly");
        }
        return pushClient;
    }
}
