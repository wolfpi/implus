package com.baidu.im.dlinterface;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.outapp.OutAppApplication;
import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.im.testutil.MockUser;
import com.baidu.im.testutil.Sleeper;

public class RebootTest extends InstrumentationTestCase {
//
//    public static final String TAG = "RebootTest";
//
//    int sendSuccessCount = 0;
//
//    public void tes1tRebootWithSendingMessage() {
//
//        Facade.initialize(this.getInstrumentation().getTargetContext(), "DemoApp");
//        Sleeper.waitWithoutBlock(2000);
//        Facade.login(MockUser.UserEnum.imrd_333.name(), MockUser.UserEnum.imrd_333.getPassword(), null);
//
//        sendSuccessCount = 0;
//        for (int i = 1; i < 1; i++) {
//
//            if (i % 5 == 0) {
//
//                Facade.destroy();
//                OutAppServiceFacade.onDestroy();
//                Sleeper.waitWithoutBlock(1000);
//                Log.e(TAG, "Facade.destroy()");
//                OutAppServiceFacade.onCreate(this.getInstrumentation().getTargetContext());
//                Sleeper.waitWithoutBlock(1000);
//                Facade.initialize(this.getInstrumentation().getTargetContext(), "DemoApp");
//                Log.e(TAG, "Facade.initialize()");
//            }
//            BinaryMessage message = new BinaryMessage();
//            message.setData(new String("test").getBytes());
//            message.setServiceName("msg");
//            message.setMethodName("msg_request");
//
//            Log.e(TAG, "Facade.send()");
//            Facade.send(message, new IMessageResultCallback() {
//
//                @Override
//                public void onSuccess(String description) {
//                    sendSuccessCount++;
//                    Log.e(TAG, "from onSuccess callback InAppApplication.hashCode() = "
//                            + InAppApplication.getInstance().getInAppConnection().hashCode());
//                    Log.e(TAG, "from onSuccess callback  OutAppApplication..hashCode() = "
//                            + OutAppApplication.getInstance().getNetworkLayer().hashCode());
//
//                }
//
//                @Override
//                public void onFail(int errorCode) {
//                    if (errorCode == -1120) {
//
//                        sendSuccessCount++;
//                    } else {
//
//                        Log.e(TAG, "errorCode = " + errorCode);
//                    }
//                }
//            });
//
//            Sleeper.waitWithoutBlock(1000);
//        }
//
//        Sleeper.waitWithoutBlock(3000);
//        Log.e(TAG, "sendSuccessCount = " + sendSuccessCount);
//    }
//
//    public void testRebootOutAppServiceFacade() {
//
//        for (int i = 0; i < 1; i++) {
//
//            Log.e(TAG, "Facade.initialize() start");
//            Facade.initialize(this.getInstrumentation().getTargetContext(), "DemoApp");
//            Log.e(TAG, "Facade.initialize() finish");
//            Log.e(TAG, "OutAppServiceFacade.initialize() start");
//            OutAppServiceFacade.onCreate(this.getInstrumentation().getTargetContext());
//            Log.e(TAG, "OutAppServiceFacade.initialize() finish");
//            Log.e(TAG, "OutAppServiceFacade.destroy() start.");
//            OutAppServiceFacade.onDestroy();
//            Log.e(TAG, "OutAppServiceFacade.destroy() finish.");
//            Log.e(TAG, "Facade.destroy() start.");
//            Facade.destroy();
//            Log.e(TAG, "Facade.destroy() finish.");
//
//        }
//    }
//
//    @Override
//    public void tearDown() {
//        OutAppServiceFacade.onCreate(this.getInstrumentation().getTargetContext());
//    }
}
