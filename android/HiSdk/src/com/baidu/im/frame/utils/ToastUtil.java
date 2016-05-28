/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.frame.utils;

import com.baidu.im.constant.Constant;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.outapp.OutAppApplication;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * @author zhaowei10
 */
public class ToastUtil {
    public static final String TAG = "ToastUtil";
    public static boolean SWITCH = false;

    private static Handler handler = new Handler(Looper.getMainLooper());
    private static Toast toast = null;
    private static final Object synObj = new Object();

    public static void toast(String text) {
        if (!SWITCH || !Constant.DEBUG) {
            return;
        }
        try {

            if (InAppApplication.getInstance().getContext() != null) {
                showMessage(InAppApplication.getInstance().getContext(), text);
            } else if (OutAppApplication.getInstance().getContext() != null) {
                showMessage(OutAppApplication.getInstance().getContext(), text);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e);
        }
    }

    public static void showMessage(final Context act, final String msg) {
        showMessage(act, msg, Toast.LENGTH_SHORT);
    }

    public static void showMessageLong(final Context act, final String msg) {
        showMessage(act, msg, Toast.LENGTH_LONG);
    }

    /**
     * Toast发送消息
     *
     * @param act
     * @param msg
     * @param len
     *
     * @author WikerYong Email:<a href="#">yw_312@foxmail.com</a>
     * @version 2012-5-22 上午11:14:27
     */
    private static void showMessage(final Context act, final String msg, final int len) {
        if (act == null) {
            return;
        }
        handler.post(new Runnable() {

            @Override
            public void run() {
                synchronized(synObj) {
                    if (toast != null) {
                        toast.cancel();
                        toast.setText(msg);
                        toast.setDuration(len);
                    }else{
                        toast = Toast.makeText(act, msg, len);
                    }
                    toast.show();
                }
            }
        });

    }

    public static void cancelCurrentToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
