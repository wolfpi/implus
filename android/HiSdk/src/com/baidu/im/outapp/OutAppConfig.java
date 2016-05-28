package com.baidu.im.outapp;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.baidu.im.frame.utils.LogUtil;

/**
 * OutApp 配置
 * 
 * @author liubinzhe
 */
public class OutAppConfig {

    private static final String TAG = "OutAppConfig";
    private static final String BDIM_RUN_MODE = "BDIM_RUN_MODE";

    private RUN_MODE mode = RUN_MODE.PRODUCT;

    public RUN_MODE getRunMode() {
        if (null != mode) {
            return mode;
        } else {
            return RUN_MODE.PRODUCT;
        }
    }

    public void init(Context context) {
        if (null != context) {
            try {
                ApplicationInfo applicationInfo =
                        context.getApplicationContext().getPackageManager()
                                .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if ((null != applicationInfo) && (null != applicationInfo.metaData)) {
                    String modeStr = applicationInfo.metaData.getString(BDIM_RUN_MODE);
                    if (!TextUtils.isEmpty(modeStr)) {
                        RUN_MODE tmpMode = RUN_MODE.parse(modeStr);
                        if (null != tmpMode) {
                            this.mode = tmpMode;
                        }
                        LogUtil.printMainProcess(TAG, "GetOutAppConfig mode" + mode);
                    } else {
                        LogUtil.printMainProcess(TAG, "Can not get BDIM_RUN_MODE string.");
                    }
                } else {
                    LogUtil.printMainProcess(TAG, "Can not get meta data.");
                }
            } catch (Exception e) {
                LogUtil.printError("GetOutAppConfig error", e);
            }
        }
    }
}
