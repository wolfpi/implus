package com.baidu.im.frame.utils;

import com.baidu.im.sdk.IMessageCallback;
import com.baidu.im.sdk.IMessageProgressCallback;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.im.sdk.ProgressEnum;

public class CallbackUtil {

    public static final String TAG = "CallbackUtil";

    public  void onStart(IMessageCallback callback) {
        if (callback instanceof IMessageResultCallback) {
            IMessageProgressCallback progressCallback = (IMessageProgressCallback) callback;
            progressCallback.onStart();
        }
    }

    public  void onStop(IMessageCallback callback) {
        if (callback instanceof IMessageResultCallback) {
            IMessageProgressCallback progressCallback = (IMessageProgressCallback) callback;
            progressCallback.onStop();
        }
    }

    public  void onProgress(IMessageCallback callback, ProgressEnum progress) {
        if (callback instanceof IMessageProgressCallback) {
            IMessageProgressCallback progressCallback = (IMessageProgressCallback) callback;
            progressCallback.onProgress(progress);
        }
    }

    public  void onFail(IMessageCallback callback, int errorCode) {
        if (callback instanceof IMessageResultCallback) {
            LogUtil.printMainProcess("callback onFail: errorCode=" + errorCode);
            IMessageResultCallback resultCallback = (IMessageResultCallback) callback;
            resultCallback.onFail(errorCode);
        }
        else if(callback instanceof IReloginCallback)
        {
            IReloginCallback resultCallback = (IReloginCallback) callback;
            resultCallback.reloginFail(errorCode);
        } else if (callback instanceof IRegAppCallback) {
            IRegAppCallback resultCallback = (IRegAppCallback) callback;
            resultCallback.regAppFail(errorCode);
        }
    }

    public  void onSuccess(IMessageCallback callback, String description, byte[] data) {
        if (callback instanceof IMessageResultCallback) {
            LogUtil.printMainProcess("callback onSuccess: description=" + description);
            IMessageResultCallback resultCallback = (IMessageResultCallback) callback;
            resultCallback.onSuccess(description, data);
        }
        else if(callback instanceof IReloginCallback)
        {
        	IReloginCallback resultCallback = (IReloginCallback) callback;
            resultCallback.reloginSuccess();
        }
        else if(callback instanceof IRegAppCallback)
        {
        	IRegAppCallback resultCallback = (IRegAppCallback) callback;
            resultCallback.regAppSucess();
        }
    }
    
}
