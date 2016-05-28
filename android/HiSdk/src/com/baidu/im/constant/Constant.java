package com.baidu.im.constant;

import android.os.Environment;

public interface Constant {

    public static final boolean DEBUG = true;

    public static final BuildMode buildMode = BuildMode.Online;

    public static final String SDK_ROOT_PATH = "/baidu/imsdk";

    public static final String sdkExternalDir = Environment.getExternalStorageDirectory() + "/baidu/imsdk";

    public static final String sdkExternalLogoDir = sdkExternalDir + "/logo";

    public static final String sdkExternalLogDir = SDK_ROOT_PATH + "/log/";
    
    public static final int seqSize = 10000;
    
    public static final String  sdkPushAction = "com.baidu.im.sdk.push";
    
    public static final String  sdkBDAppId = "appid";
    
    public static final String  sdkBDData = "infodata";
    
    public static final String  sdkBDTOUID = "toid";
    public static final String  sdkBDFORMUID = "fromid";
    public static final String  sdkBDChatType =  "chattype";

    public enum BuildMode {
        Online("https://passport.baidu.com", EChannelInfo.imPlatform_online, "PRODUCT"),

        QA("https://passport.qatest.baidu.com", EChannelInfo.imPlatform_qa, "DEVELOP"),

        RD("https://passport.rdtest.baidu.com", EChannelInfo.imPlatform_rd, "QUALITY");

        private String passport;
        private EChannelInfo channelInfo;
        private String mode;

        BuildMode(String passport, EChannelInfo channelInfo, String mode) {
            this.passport = passport;
            this.channelInfo = channelInfo;
            this.mode = mode;
        }

        public String getPassport() {
            return passport;
        }

        public EChannelInfo getEChannelInfo() {
            return channelInfo;
        }

        public String getMode() {
            return mode;
        }

        public static BuildMode parse(String mode) {
            BuildMode[] buildModes = BuildMode.values();
            for (BuildMode buildMode : buildModes) {
                if (buildMode.getMode().equals(mode)) {
                    return buildMode;
                }
            }
            return Online;
        }
    }

    public enum EChannelType {
        // tieba,

        imPlatform,

        localMock
    }

    public enum EChannelInfo {

        imPlatform_rd(EChannelType.imPlatform, "10.44.88.50", 8001),

        imPlatform_qa(EChannelType.imPlatform, "10.44.88.50", 8001),

        imPlatform_online(EChannelType.imPlatform, "10.81.10.243", 14000),

        // tieba_rd(EChannelType.tieba, "tc-testing-all-forum34-vm.epc.baidu.com", 8800),

        // tieba_online(EChannelType.tieba, "sh01-backup-pool380.sh01", 8800),

        // tieba_qa(EChannelType.tieba, "", 8800),

        localMock(EChannelType.localMock, "", 1);

        EChannelType eChannelType;
        String ip;
        int port;

        private EChannelInfo(EChannelType eChannelType, String ip, int port) {
            this.eChannelType = eChannelType;
            this.ip = ip;
            this.port = port;
        }

        public String getIp() {
            return ip;
        }

        public int getPort() {
            return port;
        }

        public EChannelType getChannelType() {
            return eChannelType;
        }
    }
}
