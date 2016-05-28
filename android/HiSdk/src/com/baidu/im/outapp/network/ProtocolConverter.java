/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.outapp.network;

import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.frame.inapp.SeqGenerator;
import com.baidu.im.frame.pb.EnumPacketType;
import com.baidu.im.frame.pb.ObjBizUpPackage.BizUpPackage;
import com.baidu.im.frame.pb.ObjBua.BUA;
import com.baidu.im.frame.pb.ObjBua.BUAInfo;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.utils.DeviceInfoMapUtil;
import com.google.protobuf.micro.ByteStringMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

/**
 * @author zhaowei10
 * 
 */
public final class ProtocolConverter {

    public static final String TAG = "ProtocolConverter";

    public static enum ServiceNameEnum {

        CoreAcc,

        CoreSession,

        // 下行文本
        CoreMsg,

        CoreConfig;

    }

    public static enum MethodNameEnum {

        RegApp,

        UnRegApp,

        Login,

        Logout,

        AppLogin,

        AppLogout,

        Heartbeat,

        SetAppStatus,

        LogoutNotify,

        RegChannel,

        SendData,

        // 下行文本
        Push,

        PushConfirm,

        ConfigChangedNotify,

        UpdateConfig;
    }

    /**
     * 转化成为downpacket.
     * 
     * @param data
     * @return
     * @throws InvalidProtocolBufferMicroException
     */
    public static DownPacket convertDownPacket(byte[] data) throws InvalidProtocolBufferMicroException {
        DownPacket downPacket = DownPacket.parseFrom(data);
        return downPacket;
    }

    /**
     * If inAppNetworkChanged is true, the first InApp UpPacket must contain BuaInfo.
     */
    public static boolean inAppNetworkChanged = true;

    /**
     * Convert UpPacket.<br/>
     * Most of InApp UpPacket need to contain bua but buaInfo.<br/>
     * 
     * eg. UpdateConfig, PushConfirm, AppLogin, AppLogout, RegApp, RegChannel, SetAppStatus, UnRegApp, UserLogin,
     * UserLogout.
     */
    public static UpPacket convertUpPacket(ByteStringMicro bizData,
            com.baidu.im.frame.pb.ObjUpPacket.UpPacket upPacketBuilder) {
        return convertUpPacket(bizData, true, false, true, upPacketBuilder);
    }

    /**
     * Convert UpPacket.<br/>
     * 
     * eg. Real Heartbeat do not contain both bua & buaInfo.
     */
    public static UpPacket convertUpPacket(ByteStringMicro bizData, boolean buaRequired,
            com.baidu.im.frame.pb.ObjUpPacket.UpPacket upPacketBuilder) {
        return convertUpPacket(bizData, buaRequired, false, true, upPacketBuilder);
    }

    /**
     * Convert UpPacket.<br/>
     * 
     * eg. SendMessage is not System Message.
     * 
     * @param bizData
     * @param buaRequired Default value is true except Heartbeat in Heartbeat Transaction
     * @param buaInfoRequired Default value is false except RegChannel, RegApp and Heartbeat in RegApp Transaction
     * @param sysMessage Default value is true except sendMessage
     * @param upPacketBuilder
     */
    public static UpPacket convertUpPacket(ByteStringMicro bizData, boolean buaRequired, boolean buaInfoRequired,
            boolean sysMessage, com.baidu.im.frame.pb.ObjUpPacket.UpPacket upPacketBuilder) {
    	
        com.baidu.im.frame.pb.ObjBizUpPackage.BizUpPackage bizUpPackageBuilder = new  BizUpPackage();  // migrate from builder
        bizUpPackageBuilder.setPacketType(EnumPacketType.REQUEST);
        bizUpPackageBuilder.setBusiData(bizData);
        BizUpPackage bizUpPackage = bizUpPackageBuilder;

        upPacketBuilder.setBizPackage(bizUpPackage);
        {
        	upPacketBuilder.setSeq(SeqGenerator.getSeq());
        	upPacketBuilder.setAppId(InAppApplication.getInstance().getSession().getApp().getAppId());
            upPacketBuilder.setUid(InAppApplication.getInstance().getSession().getSessionInfo().getUid());
        }

        if (buaRequired) {
        	
            BUA buaBuilder = new  BUA();  // migrate from builder
            DeviceInfoMapUtil.getBUA(InAppApplication.getInstance().getContext(), buaBuilder);
            /*if (buaInfoRequired) {
                BUAInfo infoBuilder = new  BUAInfo();  // migrate from builder
                DeviceInfoMapUtil.getBUAInfo(InAppApplication.getInstance().getContext(), infoBuilder);
                buaBuilder.setBuaInfo(infoBuilder);
            }*/
            upPacketBuilder.setBua(buaBuilder);
        }

        upPacketBuilder.setSysPackage(sysMessage);

        UpPacket upPacket = upPacketBuilder;

        return upPacket;

    }

}
