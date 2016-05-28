//package com.baidu.im.testutil;
//
//import java.io.File;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Instrumentation;
//
//import com.baidu.im.constant.Constant;
//import com.baidu.im.constant.Constant.EChannelType;
//import com.baidu.im.frame.inapp.InAppApplication;
//import com.baidu.im.frame.utils.AccountUtil;
//import com.baidu.im.frame.utils.FileUtil;
//import com.baidu.im.inapp.transaction.session.AppLoginTransactionTest;
//import com.baidu.im.inapp.transaction.session.RegAppTransactionTest;
//import com.baidu.im.inapp.transaction.session.UnRegAppTransactionTest;
//import com.baidu.im.inapp.transaction.session.UserLoginTransactionTest;
//import com.baidu.im.mock.MockNetworkFramework;
//import com.baidu.im.outapp.OutAppApplication;
//import com.baidu.im.outapp.network.LocalMockNetworkChannel;
//import com.baidu.im.outapp.network.LocalMockNetworkWithExceptionChannel;
//import com.baidu.im.sdk.ChannelSdk;
//import com.baidu.im.testutil.MockUser.UserEnum;
//
///**
// * 为单测准备环境。
// * 
// * @author zhaowei10
// * 
// */
//public class SetUpUtil {
//
//    public static boolean asyncThreadResult = false;
//    public static EChannelInfo eChannelInfo = EChannelInfo.localMock;
//
//    public static void initialize(Instrumentation instrumentation) {
//
//        destroy();
//
//        generateConfig(eChannelInfo);
//        //ChannelSdk.initialize(instrumentation.getTargetContext(), "AppDemo");
//        //InAppApplication.getInstance().getSession().setChannelKey("test channelkey for UT");
//
//        Sleeper.waitWithoutBlock(3000);
//        if (eChannelInfo == EChannelInfo.localMock) {
//            for (int i = 0; i < 5; i++) {
//                if (OutAppApplication.getInstance().getNetworkLayer() != null) {
//                    break;
//                }
//                Sleeper.waitWithoutBlock(1000);
//            }
//            MockNetworkFramework.getInstance().setChannel(instrumentation.getContext(), new LocalMockNetworkChannel());
//            MockNetworkFramework.getInstance().startMock();
//        }
//        InAppApplication.getInstance().getInAppHeartbeat().stop();
//        OutAppApplication.getInstance().getOutAppDaemonThread().stop();
//        Sleeper.waitWithoutBlock(3000);
//    }
//
//    public static void destroy() {
//        if (eChannelInfo == EChannelInfo.localMock) {
//            MockNetworkFramework.getInstance().stopMock();
//        }
//        clearConfig();
//        ChannelSdk.destroy();
//        Sleeper.waitWithoutBlock(2000);
//    }
//
//    /**
//     * 设置异常channel 并发出异常包
//     * 
//     * @param instrumentation
//     */
//    public static void setExceptionChannel(Instrumentation instrumentation, MockObj mockPara) {
//        if (eChannelInfo == EChannelInfo.localMock) {
//            MockNetworkFramework.getInstance().setChannel(instrumentation.getContext(),
//                    new LocalMockNetworkWithExceptionChannel(mockPara));
//        }
//    }
//
//    /**
//     * 准备好通道
//     * 
//     * @throws InterruptedException
//     */
//    public static void getRegAppReady() throws Exception {
//        new RegAppTransactionTest().testTransaction();
//    }
//
//    /**
//     * 指定用户登陆完成
//     * 
//     * @param userenum
//     */
//    public static void getUserLoginReady(UserEnum userenum) throws Exception {
//        getRegAppReady();
//        new UserLoginTransactionTest(userenum).testLoginWithBduss();
//    }
//
//    /**
//     * App登陆完成
//     */
//    public static void getAppLoginReady() throws Exception {
//        getRegAppReady();
//        new AppLoginTransactionTest().testTransaction();
//    }
//
//    /**
//     * App 注销完成
//     */
//    public static void getUnRegReady() {
//        new UnRegAppTransactionTest().testTransaction();
//    }
//
//    public static JSONObject generateConfig(EChannelInfo eChannelInfo) {
//        JSONObject config = new JSONObject();
//        try {
//            config.put("ip", eChannelInfo.getIp());
//            config.put("port", eChannelInfo.getPort());
//            config.put("channel", eChannelInfo.eChannelType.name());
//
//            File file = new File(Constant.sdkExternalDir + "/config.txt");
//            file.delete();
//
//            FileUtil.writeStringToFileInSdkFolder("config.txt", config.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//        return config;
//    }
//
//    public static void clearConfig() {
//        File file = new File(Constant.sdkExternalDir + "/config.txt");
//        file.delete();
//    }
//
//    public enum EChannelInfo {
//
//        imPlatform_integration(EChannelType.imPlatform, "10.44.88.50", 8001),
//
//        imPlatform_qa(EChannelType.imPlatform, "10.44.88.50", 8001),
//
//       // tieba_integration(EChannelType.tieba, "tc-testing-all-forum34-vm.epc.baidu.com", 8800),
//
//        //tieba_qa(EChannelType.tieba, "", 8800),
//
//        localMock(EChannelType.localMock, "", 1);
//
//        EChannelType eChannelType;
//        String ip;
//        int port;
//
//        private EChannelInfo(EChannelType eChannelType, String ip, int port) {
//            this.eChannelType = eChannelType;
//            this.ip = ip;
//            this.port = port;
//        }
//
//        public String getIp() {
//            return ip;
//        }
//
//        public int getPort() {
//            return port;
//        }
//    }
//
//    public static String getBduss(UserEnum userEnum) {
//
//        if (eChannelInfo == EChannelInfo.localMock) {
//            return "aaa";
//        } else {
//            return AccountUtil.getBduss(userEnum.name(), userEnum.getPassword());
//        }
//    }
//
//    public static String getBduss() {
//
//        if (eChannelInfo == EChannelInfo.localMock) {
//            return "aaa";
//        } else {
//            return AccountUtil.getBduss(UserEnum.imrd_333.name(), UserEnum.imrd_333.getPassword());
//        }
//    }
//}
