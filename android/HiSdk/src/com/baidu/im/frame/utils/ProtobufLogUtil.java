package com.baidu.im.frame.utils;

import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;

/**
 * The util class is used to print the log related with proto buff.
 * 
 * @author zhaowei10
 * 
 */
public class ProtobufLogUtil {

    public static final String TAG = "ProtobufLogUtil";

    public static String print(DownPacket downPacket) {
    	
    	if(downPacket == null)
    		return null;

        StringBuffer sb = new StringBuffer();
        if (downPacket != null) {

            sb.append("seq:        ").append(downPacket.getSeq()).append("\r\n");
            sb.append("sessionId:  ").append(downPacket.getSessionId()).append("\r\n");
            sb.append("uid:        ").append(downPacket.getUid()).append("\r\n");
            sb.append("appId:      ").append(downPacket.getAppId()).append("\r\n");
            sb.append("channelCode:").append(downPacket.getChannelCode()).append("\r\n");

            if (downPacket.getBizPackage() != null) {

                sb.append("bizPackage.packetType:    ").append(downPacket.getBizPackage().getPacketType())
                        .append("\r\n");
                sb.append("bizPackage.bizCode:       ").append(downPacket.getBizPackage().getBizCode()).append("\r\n");

                if (downPacket.getBizPackage().getBizData() != null) {
                    sb.append("bizPackage.bizData:       ")
                            .append(downPacket.getBizPackage().getBizData().toStringUtf8()).append("\r\n");
                }
            }
        }
        return sb.toString();
    }

    public static String print(UpPacket upPacket) {
    	
    	if(upPacket == null)
    		return null;

        StringBuffer sb = new StringBuffer();
        if (upPacket != null) {

            sb.append("seq:        ").append(upPacket.getSeq()).append("\r\n");
            sb.append("serviceName:").append(upPacket.getServiceName()).append("\r\n");
            sb.append("methodName: ").append(upPacket.getMethodName()).append("\r\n");
            sb.append("sessionId:  ").append(upPacket.getSessionId()).append("\r\n");
            sb.append("uid:        ").append(upPacket.getUid()).append("\r\n");
            sb.append("appId:      ").append(upPacket.getAppId()).append("\r\n");
            sb.append("sysPackage: ").append(upPacket.getSysPackage()).append("\r\n");

            if (upPacket.getBua() != null) {

                sb.append("bua:        ").append(upPacket.getBua().getAppVer()).append("  ")
                        .append(upPacket.getBua().getMem()).append("  ").append(upPacket.getBua().getSdCard())
                        .append("\r\n");

                if (upPacket.getBua().getBuaInfo() != null) {
                    sb.append("bua.budInfo:").append(upPacket.getBua().getBuaInfo().getDeviceToken()).append("  ")
                            .append(upPacket.getBua().getBuaInfo().getOsVer()).append("  ")
                            .append(upPacket.getBua().getBuaInfo().getDeviceToken()).append("  ")
                            .append(upPacket.getBua().getBuaInfo().getApn()).append("  ")
                            .append(upPacket.getBua().getBuaInfo().getNetwork()).append("\r\n");
                }
            }
            if (upPacket.getBizPackage() != null) {
                sb.append("bizPackage.packageType:    ").append(upPacket.getBizPackage().getPacketType())
                        .append("\r\n");
                if (upPacket.getBizPackage().getBusiData() != null) {
                    sb.append("bizPackage.busiData:       ")
                            .append(upPacket.getBizPackage().getBusiData().toStringUtf8()).append("\r\n");
                }
            }
        }
        return sb.toString();
    }
}
