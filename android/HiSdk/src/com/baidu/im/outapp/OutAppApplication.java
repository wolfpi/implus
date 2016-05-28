package com.baidu.im.outapp;

import java.io.IOException;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.baidu.im.constant.Constant;
import com.baidu.im.frame.outapp.AppMap;
import com.baidu.im.frame.outapp.NetworkLayer;
import com.baidu.im.frame.outapp.OutAppConnection;
import com.baidu.im.frame.outapp.Router;
import com.baidu.im.frame.utils.ConfigUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.outapp.transaction.OutAppTransactionFlow;

/**
 * HiCore Service的系统实例。
 * 
 * @author zhaowei10
 * 
 */
public class OutAppApplication {

    public static final String TAG = "OutAppApplication";

    /**
     * HiCoreService context.
     */
    private Context context;

    private static OutAppApplication outAppApplication = new OutAppApplication();

    private OutAppConnection outAppConnection;
    private OutAppConfig outAppConfig;
    private NetworkLayer networkLayer;
    private Router router;
    private Handler handler;
    private OutAppTransactionFlow transactionFlow;
    private AppMap mAppMap = null;
    private PreferenceUtil mPreference = null;
    private int  mSeq = 100 ;
    private int  mOutAppSeq = 9000100;
    private int  mCurOutAppSeq = mOutAppSeq;
    private String mChannelKey = null;
   // private GlobalTimer mGlobalTimer = new GlobalTimerTasks();

    
    private OutAppApplication() {
    }

    static {
        System.loadLibrary("hichannel-jni");
    }

    /**
     * 获取全局实例。
     */
    public static synchronized OutAppApplication getInstance() {
        return outAppApplication;
    }

    /**
     * 获取Hi core service的 context.
     */
    public Context getContext() {
        return context;
    }

    /**
     * 用于和in-app的部分通信。
     * 
     * @return
     */
    public OutAppConnection getOutAppConnection() {
        return outAppConnection;
    }
    
    public AppMap getAppMap()
    {
    	return mAppMap;
    }

    /**
     * @return the router
     */
    public Router getRouter() {
        return router;
    }

    public int getOutSeq()
    {
    	if(++mCurOutAppSeq > 99999100 )
    	{
    		mCurOutAppSeq = mOutAppSeq;
    	}
    	 return mCurOutAppSeq;
    }
    
    public int getInSeq()
    {
    	int retSeq = mSeq;
    	mSeq += Constant.seqSize;
    	if(mSeq >= mOutAppSeq)
    	{
    		mSeq = 100;
    	}
    	return retSeq;
    }
    
    public void setChannelKey(String channelKey)
    {
    	mChannelKey = channelKey;
    }
    public String getChannelKey()
    {
    	return mChannelKey;
    }
    /**
     * @return the networkLayer
     */
    public NetworkLayer getNetworkLayer() {
        return networkLayer;
    }

    public void destroy() {

        if (transactionFlow != null) {
            transactionFlow.destroy();
            transactionFlow = null;
        }

        if (networkLayer != null) {
            networkLayer.destroy();
            networkLayer = null;
        }

        if (router != null) {
            router.destroy();
            router = null;
        }

        if (outAppConnection != null) {
            outAppConnection.destroy();
            outAppConnection = null;
        }
        this.context = null;
    }

    public void initialize(Context context) {
        handler = new Handler(Looper.getMainLooper());

        outAppConfig = new OutAppConfig();
        outAppConfig.init(context);

        mPreference = new PreferenceUtil();
        mPreference.initialize(context, null);
        
        ConfigUtil.load();
        
        router = new Router();

        this.context = context.getApplicationContext();
        mAppMap = new AppMap(context);
        
        transactionFlow = new OutAppTransactionFlow();
        transactionFlow.initialize(mAppMap,mPreference);

        try {
            networkLayer = new NetworkLayer(outAppConfig, mPreference);
           
        } catch (IOException e) {
            LogUtil.printMainProcess(TAG, "networkLayer initialize error." + e.getMessage());
            LogUtil.e(TAG, e);
        }
        outAppConnection = new OutAppConnection(router,mAppMap,networkLayer);
        networkLayer.Initialized(outAppConnection);
    }

    public void runOnMainThread(Runnable runnable) {

        handler.post(runnable);
    }

    public OutAppTransactionFlow getTransactionFlow() {
        return transactionFlow;
    }
}
