package com.baidu.imp.push.client.impl;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.utils.GlobalInstance;
import com.baidu.im.frame.utils.PreferenceKey;
import com.baidu.im.testutil.TestFacade;
import com.baidu.imc.impl.push.client.PushClientImpl;
import com.baidu.imc.listener.ClientStatusListener;
import com.baidu.imc.listener.PushMessageListener;
import com.baidu.imc.type.ClientConnectStatus;
import com.baidu.imc.type.UserStatus;

public class PushClientImplTest extends InstrumentationTestCase {
	
	private TestFacade mTestFacade = new TestFacade();

    @Override
    protected void setUp() {
    	GlobalInstance.Instance().preferenceInstace().
    	initialize(this.getInstrumentation().getTargetContext(),mTestFacade.getAppkey());
    }
    
    @Override
   public void tearDown() {
     
    }
   
    public void testSetClientStatusListener(ClientStatusListener listener) {
        // TODO Auto-generated method stub

    }

    public void testConnect() {
        // TODO Auto-generated method stub

    }

    public void testDisconnect() {
        // TODO Auto-generated method stub

    }

    public void testLogin(String userID, String userToken) {
        // TODO Auto-generated method stub

    }

    public void testLogout() {
        // TODO Auto-generated method stub

    }

    public ClientConnectStatus testGetCurrentClientConnectStatus() {
        // TODO Auto-generated method stub
        return null;
    }

    public String testGetCurrentUserID() {
        // TODO Auto-generated method stub
        return null;
    }

    public UserStatus testGetCurrentUserStatus() {
        // TODO Auto-generated method stub
        return null;
    }

    public void testSetPushMessageListener(PushMessageListener listener) {
        // TODO Auto-generated method stub

    }
    
    public void testNormalStatusChange()
    {
    	PushClientImpl pushClient = new PushClientImpl();
    	pushClient.init(this.getInstrumentation().getTargetContext(), mTestFacade.getAppkey());
    	pushClient.statusChanged(0, 1);
    	//pushClient.disconnect();
    }
    
    public void testNormalConnect()
    {
    	PushClientImpl pushClient = new PushClientImpl();
    	pushClient.init(this.getInstrumentation().getTargetContext(), mTestFacade.getAppkey());
    	pushClient.connect();
    	pushClient.disconnect();
    }
    
    public void testAbNormalConnect()
    {
    	PushClientImpl pushClient = new PushClientImpl();
    	pushClient.init(this.getInstrumentation().getTargetContext(), mTestFacade.getAppkey());
    	pushClient.disconnect();
    	pushClient.connect();
    }
    
    public void testNormalLogin()
    {
    	PushClientImpl pushClient = new PushClientImpl();
    	pushClient.init(this.getInstrumentation().getTargetContext(), mTestFacade.getAppkey());
    	pushClient.login(String.valueOf(mTestFacade.getUid()), mTestFacade.getToken());
    }
    
    public void testAbNormalLogin()
    {
    	PushClientImpl pushClient = new PushClientImpl();
    	pushClient.init(this.getInstrumentation().getTargetContext(), mTestFacade.getAppkey());
    	
    	try{
    	pushClient.login(null, mTestFacade.getToken());
    	} catch(Exception e)
    	{
    		Assert.assertTrue(true);
    	}
    	try{
        	pushClient.login(null, null);
        } catch(Exception e)
        {
        	Assert.assertTrue(true);
        }
    }
    
    public void testNormalLoginAndLogout()
    {
    	PushClientImpl pushClient = new PushClientImpl();
    	pushClient.init(this.getInstrumentation().getTargetContext(), mTestFacade.getAppkey());
    	pushClient.login(String.valueOf(mTestFacade.getUid()), mTestFacade.getToken());
    	pushClient.logout();
    }
    
    public void testAbNormalLogout()
    {
    	PushClientImpl pushClient = new PushClientImpl();
    	pushClient.init(this.getInstrumentation().getTargetContext(), mTestFacade.getAppkey());
    	pushClient.logout();
    }
    
    
    
    public void testsetNoDisturbMode()
    {
    	PushClientImpl pushClient = new PushClientImpl();
    	pushClient.init(this.getInstrumentation().getTargetContext(), mTestFacade.getAppkey());
    	
    	
    	GlobalInstance.Instance().preferenceInstace()
         .save(PreferenceKey.notificationDisableDuration, "");
    	pushClient.setNoDisturbMode(22, 0, 23, 0);
    	
    	
    	String jsonDisnableDuration = GlobalInstance.Instance().preferenceInstace()
        .getString(PreferenceKey.notificationDisableDuration);
    	
    	Assert.assertTrue(jsonDisnableDuration != null && jsonDisnableDuration.length() != 0);
    	//Assert.assertTrue();
    	//enableNotification
    }
    
    public void testAbNormalsetNoDisturbMode()
    {
    	PushClientImpl pushClient = new PushClientImpl();
    	pushClient.init(this.getInstrumentation().getTargetContext(), mTestFacade.getAppkey());
    	
    	GlobalInstance.Instance().preferenceInstace()
         .save(PreferenceKey.notificationDisableDuration, "");
    	
    	try
    	{
    		pushClient.setNoDisturbMode(22, 0, 21, 0);
    	} catch(Exception e)
    	{
    		Assert.assertTrue(true);
    	}
    }
    
    public void testNotifyEnable()
    {
    	PushClientImpl pushClient = new PushClientImpl();
    	pushClient.init(this.getInstrumentation().getTargetContext(), mTestFacade.getAppkey());
    	
    	pushClient.disableNotification();
    	Assert.assertTrue(pushClient.isNotificationEnabled());
    	pushClient.enableNotification(); 
    	Assert.assertFalse(pushClient.isNotificationEnabled());
    	
    }
/*
    public void testEnableNotification() {
        SetUpUtil.initialize(this.getInstrumentation());

        PushClientImpl.getPushClient().disableNotification();
        assertEquals(NotificationUtil.isNotificationEnable(), false);

        PushClientImpl.getPushClient().enableNotification();
        assertEquals(NotificationUtil.isNotificationEnable(), true);

        SetUpUtil.destroy();
        OutAppApplication.getInstance().initialize(this.getInstrumentation().getTargetContext());
        DownPacket downPacket = MockDownPacket.mockOneOfflineMsgDownPacket(System.currentTimeMillis());

        try {
            new OutAppShowOffLineMessageProcessor(downPacket).process();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testDisableNotification() {
        SetUpUtil.initialize(this.getInstrumentation());

        PushClientImpl.getPushClient().enableNotification();
        assertEquals(NotificationUtil.isNotificationEnable(), true);

        PushClientImpl.getPushClient().disableNotification();
        assertEquals(NotificationUtil.isNotificationEnable(), false);

        SetUpUtil.destroy();
        OutAppApplication.getInstance().initialize(this.getInstrumentation().getTargetContext());
        DownPacket downPacket = MockDownPacket.mockOneOfflineMsgDownPacket(System.currentTimeMillis());

        try {
            new OutAppShowOffLineMessageProcessor(downPacket).process();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean testIsNotificationEnagled() {
        SetUpUtil.initialize(this.getInstrumentation());

        PushClientImpl.getPushClient().enableNotification();
        assertEquals(NotificationUtil.isNotificationEnable(), true);

        PushClientImpl.getPushClient().disableNotification();
        assertEquals(NotificationUtil.isNotificationEnable(), false);
        return false;
    }

    public void testSetNoDisturbMode() {

        SetUpUtil.initialize(this.getInstrumentation());

        int startHour = 0;
        int startMinute = 0;
        int endHour = 0;
        int endMinute = 0;

        for (int i = 0; i < 10; i++) {

            int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);

            startHour = new Random().nextInt(24);
            endHour = new Random().nextInt(24);
            startMinute = new Random().nextInt(60);
            endMinute = new Random().nextInt(60);

            LogUtil.printMainProcess("currentHour:" + currentHour + "   currentMinute:" + currentMinute + " startHour:"
                    + startHour + " startMinute:" + startMinute + " endHour:" + endHour + " endMinute:" + endMinute);

            if (currentHour * 60 + currentMinute <= endHour * 60 + endMinute
                    && currentHour * 60 + currentMinute >= startHour * 60 + startMinute) {
 
*/                /**
                 * disable now. 20:00 --- now ---- 23:00
                 */
    
    /*
                PushClientImpl.getPushClient().enableNotification();
                PushClientImpl.getPushClient().setNoDisturbMode(0, 0, 0, 0);
                assertEquals(NotificationUtil.isNotificationEnable(), true);
                PushClientImpl.getPushClient().setNoDisturbMode(startHour, startMinute, endHour, endMinute);
                assertEquals(NotificationUtil.isNotificationEnable(), false);
            } else if (startHour * 60 + startMinute >= endHour * 60 + endMinute
                    && (currentHour * 60 + currentMinute <= endHour * 60 + endMinute || currentHour * 60
                            + currentMinute >= startHour * 60 + startMinute)) {
                
                PushClientImpl.getPushClient().enableNotification();
                PushClientImpl.getPushClient().setNoDisturbMode(0, 0, 0, 0);
                assertEquals(NotificationUtil.isNotificationEnable(), true);
                PushClientImpl.getPushClient().setNoDisturbMode(startHour, startMinute, endHour, endMinute);
                assertEquals(NotificationUtil.isNotificationEnable(), false);
            } else {

                PushClientImpl.getPushClient().enableNotification();
                PushClientImpl.getPushClient().setNoDisturbMode(0, 0, 23, 59);
                assertEquals(NotificationUtil.isNotificationEnable(), false);
                PushClientImpl.getPushClient().setNoDisturbMode(startHour, startMinute, endHour, endMinute);
                assertEquals(NotificationUtil.isNotificationEnable(), true);
            }
        }

       
        try {

            PushClientImpl.getPushClient().setNoDisturbMode(32, 1, 1, 1);
            Assert.fail();
        } catch (IllegalParameterError e) {
        }

       
        try {

            PushClientImpl.getPushClient().setNoDisturbMode(1, 60, 1, 1);
            Assert.fail();
        } catch (IllegalParameterError e) {
        }

       
        try {

            PushClientImpl.getPushClient().setNoDisturbMode(1, 1, 24, 1);
            Assert.fail();
        } catch (IllegalParameterError e) {
        }

       
        try {

            PushClientImpl.getPushClient().setNoDisturbMode(1, 1, 1, 123);
            Assert.fail();
        } catch (IllegalParameterError e) {
        }
    }*/
}
