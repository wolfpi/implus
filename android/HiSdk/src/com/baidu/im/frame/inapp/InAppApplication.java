package com.baidu.im.frame.inapp;

import android.content.Context;

import com.baidu.im.frame.NetworkChannel.NetworkChannelStatus;
import com.baidu.im.frame.utils.ConfigUtil;
import com.baidu.im.frame.utils.GlobalInstance;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.inapp.messagecenter.MessageCenter;
import com.baidu.im.inapp.transaction.InAppTransactionFlow;
import com.baidu.im.outapp.network.ProtocolConverter;
import com.baidu.im.sdk.IMessageResultCallback;

/**
 * In-app的application.
 * 
 * @version 0.1
 * 
 */
public class InAppApplication {

    /**
     * Application context.
     */
    private Context context;

    private static InAppApplication inAppApplication = new InAppApplication();

    private InAppConnection inAppConnection;

    private MessageCenter messageCenter;

    private IAppSession session;

    private InAppTransactionFlow transactionFlow;

    private InAppHeartbeat inAppHeartbeat;

    private boolean isConnected = false;

    private boolean updateConfigRequired;

    private IAppSession.IAppSessionListener sessionListener;
    
    private IStatusListener mConnectionListener = null;

    private PreferenceUtil mPreference = GlobalInstance.Instance().preferenceInstace();
    private BizThread      mBizThread  = new BizThread();
    
    private long     mlastHeartBeatTimer = 0;
    private long 	 mtimeDelta = 0;
    
    public void sendReconnect()
    {
    	if(getSession().getChannelStatus() != NetworkChannelStatus.Connected)
    	{
    		inAppConnection.sendReconnect();
    	}
    }
    public void setLastInappHearbeat()
    {
    	mlastHeartBeatTimer = System.currentTimeMillis();
    }
    
    public void setTimeDelta(long time)
    {
    	mtimeDelta = time;
    }
    public long getTimeDelta()
    {
    	return mtimeDelta;
    }
    public boolean NeedToSendHeartBeat()
    {
    	if(System.currentTimeMillis() -mlastHeartBeatTimer > 5*60*1000 )
    		return true;
    	else
    		return false;
    }
    
    //private GlobalTimer mGlobalTimer = new GlobalTimerTasks();

    private InAppApplication() {
    	
    }

    public void setConnnectionListener(IStatusListener listener)
    {
    	mConnectionListener = listener;
    }
    
    public void netWorkChanged(NetworkChannelStatus networkChannelStatus)
    {
    	if(mConnectionListener != null)
    	{
    		mConnectionListener.statusChange(networkChannelStatus);
    	}
    }
    /**
     * 获取全局实例。
     */
    public synchronized static InAppApplication getInstance() {
        return inAppApplication;
    }

    
    public PreferenceUtil getPreference()
    {
    	return mPreference;
    }
    
    public void initSession(Context context, String apiKey) {
    	
    	if(context == null || apiKey == null || apiKey.isEmpty())
    	{
    		return ;
    	}

        if (mPreference != null)
            mPreference.initialize(context, apiKey);
        if (mPreference == null) {
            LogUtil.printMainProcess("TeT@@@@@@@");
            return;
        }
        if (session == null) {
            this.context = context.getApplicationContext();

            session = new AppSessionImpl();
            session.init(apiKey, mPreference);
            session.setListener(sessionListener);
        }
    }

    public void initialize(Context context, String apiKey, IMessageResultCallback regAppCallback,
            IMessageResultCallback heartbeatCallback) {

        LogUtil.printMainProcess("InAppApplication initializing... " + inAppApplication.hashCode());
        
        if(context == null ||apiKey == null || apiKey.isEmpty() )
        	return ;

        initSession(context, apiKey);

        if (mPreference == null) {
            LogUtil.printMainProcess("Error in mPreference is null !");
        }
        messageCenter = new MessageCenter();
        messageCenter.initialize();

        transactionFlow = new InAppTransactionFlow(mPreference, messageCenter);
        // transactionFlow.initialize();

        inAppConnection = new InAppConnection();
        inAppConnection.initialize(mPreference);

        setUpdateConfigRequired(true);

        inAppHeartbeat = new InAppHeartbeat();
        startHeartbeat(regAppCallback, heartbeatCallback);

        ConfigUtil.load();
    }

    public void startHeartbeat(IMessageResultCallback regAppCallback, IMessageResultCallback heartbeatCallback) {
        inAppHeartbeat.start(regAppCallback, heartbeatCallback);
    }

    /**
     * 获取App的Application context.
     */
    public Context getContext() {
        return context;
    }

    /**
     * @return the messageCenter
     */
    public MessageCenter getMessageCenter() {
        return messageCenter;
    }

    public InAppConnection getInAppConnection() {
        return inAppConnection;
    }

    public IAppSession getSession() {
        return session;
    }

    public InAppTransactionFlow getTransactionFlow() {
        return transactionFlow;
    }

    public BizThread getBizThread() {
        return mBizThread;
    }

    public InAppHeartbeat getInAppHeartbeat() {
        return inAppHeartbeat;
    }

    public void setSessionListener(IAppSession.IAppSessionListener sessionListener) {
        this.sessionListener = sessionListener;
        if (session != null) {
            session.setListener(sessionListener);
        }
    }

    public boolean isConnected() {
    	if(!isConnected)
    	{
    		LogUtil.printIm("is connect is called in sdk");
    		inAppConnection.sendReconnect();
    	}
        return isConnected;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isUpdateConfigRequired() {
        return updateConfigRequired;
    }

    public void setUpdateConfigRequired(boolean updateConfigRequired) {
        this.updateConfigRequired = updateConfigRequired;
    }

    public void setInAppNetworkChanged(boolean inAppNetworkChanged) {
        ProtocolConverter.inAppNetworkChanged = inAppNetworkChanged;
    }

    public void destroy() {

        if (inAppHeartbeat != null) {
            inAppHeartbeat.stop();
            inAppHeartbeat = null;
        }

        if (messageCenter != null) {
            messageCenter.destroy();
            messageCenter = null;
        }
        if (inAppConnection != null) {
            inAppConnection.destroy();
            inAppConnection = null;
        }

        if (transactionFlow != null) {
            transactionFlow.destroy();
            transactionFlow = null;
        }
        if (session != null) {
            session.destroy(); // session不设置为null,因为api需要访问
        }
        
        mBizThread.stop();
        context = null;
    }
}
