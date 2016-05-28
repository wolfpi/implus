package com.baidu.im.frame.utils.test;

import android.test.InstrumentationTestCase;

import com.baidu.im.frame.pb.ObjInformMessage.InformMessage;
import com.baidu.im.frame.utils.NotificationUtil;

public class NotificationUtilTest extends InstrumentationTestCase {

    public static final String TAG = "NotificationUtilTest";
   
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void tesshowNormal() {
	   InformMessage  inf = new InformMessage();
	  NotificationUtil.showNormal(null, inf,0);
	  NotificationUtil.showNormal(this.getInstrumentation().getContext(), null,0);
	  NotificationUtil.showNormal(null, null,0);
	  
   }
   
}