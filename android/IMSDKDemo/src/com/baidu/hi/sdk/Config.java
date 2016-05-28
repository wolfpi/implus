package com.baidu.hi.sdk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.baidu.hi.sdk.Constant.DEMO_MODE;

public class Config {

    private static DEMO_MODE mode = DEMO_MODE.PRODUCT;

    private static final String DEMO_RUN_MODE = "DEMO_RUN_MODE";

    public static String LOGIN_URL;

    public static String REGISTER_URL;

    public static String GROUP_URL;

    public static void init(Context context) {

        if (null != context) {
            try {
                ApplicationInfo applicationInfo =
                        context.getApplicationContext().getPackageManager()
                                .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if ((null != applicationInfo) && (null != applicationInfo.metaData)) {
                    String modeStr = applicationInfo.metaData.getString(DEMO_RUN_MODE);
                    DEMO_MODE tmpMode = DEMO_MODE.parse(modeStr);
                    if (null != tmpMode) {
                        mode = tmpMode;
                    }
                }
            } catch (Exception e) {

            }

        }

        if (mode == DEMO_MODE.PRODUCT) {
            LOGIN_URL = Constant.PRODUCT_BASE_URL + Constant.LOGIN_URL;
            REGISTER_URL = Constant.PRODUCT_BASE_URL + Constant.REGISTER_URL;
            GROUP_URL = Constant.PRODUCT_BASE_URL + Constant.GROUP_URL;
        } else {
            LOGIN_URL = Constant.DEVELOP_BASE_URL + Constant.LOGIN_URL;
            REGISTER_URL = Constant.DEVELOP_BASE_URL + Constant.REGISTER_URL;
            GROUP_URL = Constant.DEVELOP_BASE_URL + Constant.GROUP_URL;
        }
    }
}
