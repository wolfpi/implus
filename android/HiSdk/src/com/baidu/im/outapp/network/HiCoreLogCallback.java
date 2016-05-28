package com.baidu.im.outapp.network;

import java.io.UnsupportedEncodingException;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.outapp.network.hichannel.ILogCallback;

public class HiCoreLogCallback extends ILogCallback {

    private final static String TAG = "HiChannel";

    public void d(byte[] data, int len) {
        String msg = "";
        try {
            msg = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            LogUtil.e(TAG, "", e1);
        }
        LogUtil.d(TAG, msg);
    }

    public void i(byte[] data, int len) {
        String msg = "";
        try {
            msg = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            LogUtil.e(TAG, "", e1);
        }
        LogUtil.i(TAG, msg);
    }

    public void w(byte[] data, int len) {
        String msg = "";
        try {
            msg = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            LogUtil.e(TAG, "", e1);
        }
        LogUtil.w(TAG, msg);
    }

    public void e(byte[] data, int len) {
        String msg = "";
        try {
            msg = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            LogUtil.e(TAG, "", e1);
        }
        LogUtil.e(TAG, msg);
    }

}
