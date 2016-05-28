package com.baidu.im.outapp.transaction;
import com.baidu.im.frame.outapp.AppMap;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.utils.PreferenceUtil;

public class OutAppTransactionFlow {

    public static final String TAG = "OutAppTransactionFlow";

    //private OutAppTransactionThreadPool transactionThreadPool;
    private PreferenceUtil mPref = null;

    public void initialize(AppMap appMap,PreferenceUtil pref) {
    	/*
        transactionThreadPool = new OutAppTransactionThreadPool();
        transactionThreadPool.initialize();*/
        mPref =  pref;
    }

    public int outAppHeartbeat() {
    	
    	/*OutAppHeartbeatTransaction transaction = new OutAppHeartbeatTransaction(mPref);
        int transactionId = transaction.hashCode();
        transaction.startWorkFlow();
        return transactionId;*/
    	return 0;
    }

    public int outAppSetAppOffline(int appId) {
    	OutAppSetAppOffLineTransaction transaction = new OutAppSetAppOffLineTransaction(appId);
        int transactionId = transaction.hashCode();
        transaction.startWorkFlow();
        return transactionId;
    }

    public int outAppShowOffLineMessage(DownPacket downPacket) {
    	//if(OutAppApplication.getInstance().getRouter().getClientHandler(downPacket.getAppId()) == null)
    	{
        	OutAppShowOffLineMessageTransaction transaction = new OutAppShowOffLineMessageTransaction(downPacket);
        	transaction.startWorkFlow();
            int transactionId = transaction.hashCode();
            return transactionId;
        }
    	//return 0;
    }

    public void destroy() {
    	/*
        if (transactionThreadPool != null) {
            transactionThreadPool.destroy();
        }*/
    }
}
