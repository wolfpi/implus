package com.baidu.im.frame.utils.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.utils.PreferenceKey;
import com.baidu.im.frame.utils.PreferenceUtil;

public class PreferenceUtilTest extends InstrumentationTestCase {

    public static final String TAG = "PreferenceUtilTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testPreference() {
	   PreferenceUtil util = new PreferenceUtil();
	   util.initialize(this.getInstrumentation().getContext(), null);
	   Assert.assertTrue(util.getChannelkey().equalsIgnoreCase(""));
	   util.getSeq();
	   util.clear();
       util.getInt(null);
       util.getBoolean(null, false);
       util.getBoolean(PreferenceKey.uid, false);
       util.getLastHeartbeatTime();
       util.getLong(null);
       util.getLong(PreferenceKey.deviceId);
       util.getBoolean(PreferenceKey.apiKey);
       util.remove(PreferenceKey.appId);
       util.getString(null);
       util.getString(PreferenceKey.deviceId);
   }
   
}