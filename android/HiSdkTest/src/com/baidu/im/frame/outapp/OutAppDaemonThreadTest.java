//package com.baidu.im.frame.outapp;
//
//import android.test.InstrumentationTestCase;
//
//import com.baidu.im.frame.pb.EnumAppStatus.EAppStatus;
//import com.baidu.im.frame.utils.PreferenceKey;
//import com.baidu.im.frame.utils.PreferenceUtil;
//import com.baidu.im.outapp.OutAppApplication;
//import com.baidu.im.testutil.SetUpUtil;
//import com.baidu.im.testutil.Sleeper;
//
//public class OutAppDaemonThreadTest extends InstrumentationTestCase {
//
//    @Override
//    protected void setUp() {
//        SetUpUtil.initialize(this.getInstrumentation());
//    }
//
//    public void testHeartbeat() throws Exception {
//    }
//
//    public void testCheckAppStatus() {
//
//        OutAppApplication.getInstance().getOutAppDaemonThread().stop();
//        Sleeper.waitWithoutBlock(10000);
//       // PreferenceUtil.save(PreferenceKey., EAppStatus.APP_ONLINE_VALUE);
//        OutAppApplication.getInstance().getOutAppDaemonThread().checkAppStatus();
//
//        Sleeper.waitWithoutBlock(10000);
//
//    }
//
//    @Override
//    protected void tearDown() throws Exception {
//        SetUpUtil.destroy();
//        super.tearDown();
//    }
//}
