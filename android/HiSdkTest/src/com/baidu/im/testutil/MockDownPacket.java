package com.baidu.im.testutil;

import com.baidu.im.frame.pb.EnumMessageType;
import com.baidu.im.frame.pb.EnumNotifyType;
import com.baidu.im.frame.pb.EnumPacketType;
import com.baidu.im.frame.pb.ObjBizDownPacket.BizDownPackage;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjInformMessage.InformMessage;
import com.baidu.im.frame.pb.ObjNormalMessage.NormalMessage;
import com.baidu.im.frame.pb.ObjNotifyMessage.NotifyMessage;
import com.baidu.im.frame.pb.ObjPushData.PushData;
import com.baidu.im.frame.pb.ProConfigNotify.ConfigChangedNotify;
import com.baidu.im.frame.pb.ProPush;
import com.baidu.im.frame.pb.ProPush.PushMsgs;
import com.baidu.im.frame.pb.ProPush.PushOneMsg;
import com.baidu.im.frame.utils.BizCodeProcessUtil;
import com.google.protobuf.micro.ByteStringMicro;
import com.google.protobuf.micro.ByteStringMicro;

public class MockDownPacket {
	
	private static TestFacade mGlobal = new TestFacade();

    public static DownPacket mockOneOnlineMsgDownPacket(long messageId, byte[] data) {
        PushOneMsg pushOneMsgBuilder = new PushOneMsg();
        pushOneMsgBuilder.setConfirmMode(ProPush.ALWAYS_YES);
        pushOneMsgBuilder.setMsgId(messageId);
        pushOneMsgBuilder.setOnlineMsg(ByteStringMicro.copyFrom(data));
//        pushOneMsgBuilder.setPushMsgType(EPushOneMsgType.TYPE_ONLINE);

        PushMsgs pushMsgsBuilder = new PushMsgs();
        pushMsgsBuilder.addMsgs(pushOneMsgBuilder);

        BizDownPackage bizDownPackageBuilder = new BizDownPackage();
        bizDownPackageBuilder.setBizCode(0);
        bizDownPackageBuilder.setPacketType(EnumPacketType.NOTIFYCATION);
        bizDownPackageBuilder.setBizData(ByteStringMicro.copyFrom(pushMsgsBuilder.toByteArray()));

        DownPacket downPacketBuilder = new DownPacket();
        downPacketBuilder.setAppId(mGlobal.getAppid());
        downPacketBuilder.setUid(mGlobal.getUid());
        downPacketBuilder.setSessionId("aa");
        downPacketBuilder.setChannelCode(200);
        downPacketBuilder.setSeq(0);
        downPacketBuilder.setBizPackage(bizDownPackageBuilder);

        return downPacketBuilder;
    }

    public static DownPacket mockOneOnlineMsgDownPacket(long messageId, byte[] data,
            BizCodeProcessUtil.BizCode bizCode, BizCodeProcessUtil.ChannelCode channelCode) {
        PushOneMsg pushOneMsgBuilder = new PushOneMsg();
        pushOneMsgBuilder.setConfirmMode(ProPush.ALWAYS_YES);
        pushOneMsgBuilder.setMsgId(messageId);
        pushOneMsgBuilder.setOnlineMsg(ByteStringMicro.copyFrom(data));

        PushMsgs pushMsgsBuilder = new PushMsgs();
        pushMsgsBuilder.addMsgs(pushOneMsgBuilder);

        BizDownPackage bizDownPackageBuilder = new BizDownPackage();
        bizDownPackageBuilder.setBizCode(bizCode.getCode());
        bizDownPackageBuilder.setPacketType(EnumPacketType.NOTIFYCATION);
        bizDownPackageBuilder.setBizData(ByteStringMicro.copyFrom(pushMsgsBuilder.toByteArray()));

        DownPacket downPacketBuilder = new DownPacket();
        downPacketBuilder.setAppId(mGlobal.getAppid());
        downPacketBuilder.setUid(mGlobal.getUid());
        downPacketBuilder.setSessionId("aa");
        downPacketBuilder.setChannelCode(channelCode.getCode());
        downPacketBuilder.setSeq(0);
        downPacketBuilder.setBizPackage(bizDownPackageBuilder);

        return downPacketBuilder;
    }

    public static DownPacket mockOneOnlineMsgEmptyDownPacket(long messageId, byte[] data) {
        PushOneMsg pushOneMsgBuilder = new PushOneMsg();
        pushOneMsgBuilder.setConfirmMode(ProPush.ALWAYS_YES);
        pushOneMsgBuilder.setMsgId(messageId);
        pushOneMsgBuilder.setOnlineMsg(ByteStringMicro.EMPTY);
 //       pushOneMsgBuilder.setPushMsgType(EPushOneMsgType.TYPE_ONLINE);

        PushMsgs pushMsgsBuilder = new PushMsgs();
        pushMsgsBuilder.addMsgs(pushOneMsgBuilder);

        BizDownPackage bizDownPackageBuilder = new BizDownPackage();
        bizDownPackageBuilder.setBizCode(0);
        bizDownPackageBuilder.setPacketType(EnumPacketType.NOTIFYCATION);
        bizDownPackageBuilder.setBizData(ByteStringMicro.EMPTY);

        DownPacket downPacketBuilder = new DownPacket();
        downPacketBuilder.setAppId(mGlobal.getAppid());
        downPacketBuilder.setUid(mGlobal.getUid());
        downPacketBuilder.setSessionId("aa");
        downPacketBuilder.setChannelCode(200);
        downPacketBuilder.setSeq(0);
        downPacketBuilder.setBizPackage(bizDownPackageBuilder);

        return downPacketBuilder;
    }

    public static DownPacket mockOneOfflineMsgDownPacket(long messageId) {

        InformMessage informMessageBuilder = new InformMessage();
        informMessageBuilder.setTitle("mocktile");
        informMessageBuilder.setDescription("this is a mock message from ut.");

        PushOneMsg pushOneMsgBuilder = new PushOneMsg();
        pushOneMsgBuilder.setConfirmMode(ProPush.ALWAYS_YES);
        pushOneMsgBuilder.setMsgId(messageId);
        pushOneMsgBuilder.setOfflineMsg(ByteStringMicro.copyFrom(informMessageBuilder.toByteArray()));
//        pushOneMsgBuilder.setPushMsgType(EPushOneMsgType.TYPE_OFFLINE);

        PushMsgs pushMsgsBuilder = new PushMsgs();
        pushMsgsBuilder.addMsgs(pushOneMsgBuilder);

        BizDownPackage bizDownPackageBuilder = new BizDownPackage();
        bizDownPackageBuilder.setBizCode(0);
        bizDownPackageBuilder.setPacketType(EnumPacketType.NOTIFYCATION);
        bizDownPackageBuilder.setBizData(ByteStringMicro.copyFrom(pushMsgsBuilder.toByteArray()));

        DownPacket downPacketBuilder = new DownPacket();
        downPacketBuilder.setAppId(mGlobal.getAppid());
        downPacketBuilder.setChannelCode(200);
        downPacketBuilder.setSeq(0);
        downPacketBuilder.setBizPackage(bizDownPackageBuilder);

        return downPacketBuilder;
    }

    public static ByteStringMicro mockOneOnlineSysNotifyDownPacketConfigChangedNotifyByteStringMicro() {
        ConfigChangedNotify builder = new ConfigChangedNotify();
        builder.setTimestamp(System.currentTimeMillis());
        return ByteStringMicro.copyFrom(builder.toByteArray());
    }

    public static DownPacket mockOneOnlineSysNotifyDownPacket() {
        ConfigChangedNotify configChangedNotifyBuilder = new ConfigChangedNotify();
        configChangedNotifyBuilder.setTimestamp(System.currentTimeMillis());

        NotifyMessage notifyMessageBuilder = new NotifyMessage();
        notifyMessageBuilder.setNotifyType(EnumNotifyType.CONFIG_CHANGED_NOTIFY);
        notifyMessageBuilder.setNotifyData(ByteStringMicro.copyFrom(configChangedNotifyBuilder.toByteArray()));

        PushData pushDataBuilder = new PushData();
        pushDataBuilder.setMessageType(EnumMessageType.SYS_NOTIFY_MSG);
        pushDataBuilder.setSysNotifyMessage(notifyMessageBuilder);

        PushOneMsg pushOneMsgBuilder = new PushOneMsg();
        pushOneMsgBuilder.setConfirmMode(ProPush.ALWAYS_YES);
        pushOneMsgBuilder.setMsgId(System.currentTimeMillis());
        pushOneMsgBuilder.setOnlineMsg(ByteStringMicro.copyFrom(pushDataBuilder.toByteArray()));
//        pushOneMsgBuilder.setPushMsgType(EPushOneMsgType.TYPE_ONLINE);

        PushMsgs pushMsgsBuilder = new PushMsgs();
        pushMsgsBuilder.addMsgs(pushOneMsgBuilder);

        BizDownPackage bizDownPackageBuilder = new BizDownPackage();
        bizDownPackageBuilder.setBizCode(0);
        bizDownPackageBuilder.setPacketType(EnumPacketType.NOTIFYCATION);
        bizDownPackageBuilder.setBizData(ByteStringMicro.copyFrom(pushMsgsBuilder.toByteArray()));

        DownPacket downPacketBuilder = new DownPacket();
        downPacketBuilder.setAppId(mGlobal.getAppid());
        downPacketBuilder.setSessionId("aa");
        downPacketBuilder.setChannelCode(200);
        downPacketBuilder.setSeq(0);
        downPacketBuilder.setBizPackage(bizDownPackageBuilder);

        return downPacketBuilder;
    }

    public static DownPacket mockOneOnlineNormalDownPacket(long messageId, byte[] data) {
        NormalMessage normalMessageBuilder = new NormalMessage();
        normalMessageBuilder.setContent(ByteStringMicro.copyFrom(data));

        PushData pushDataBuilder = new PushData();
        pushDataBuilder.setMessageType(EnumMessageType.NORMAL_MSG);
        pushDataBuilder.setNormalMessage(normalMessageBuilder);

        PushOneMsg pushOneMsgBuilder = new PushOneMsg();
        pushOneMsgBuilder.setConfirmMode(ProPush.ALWAYS_YES);
        pushOneMsgBuilder.setMsgId(messageId);
        pushOneMsgBuilder.setAckId(System.currentTimeMillis());
        pushOneMsgBuilder.setOnlineMsg(ByteStringMicro.copyFrom(pushDataBuilder.toByteArray()));

        PushMsgs pushMsgsBuilder = new PushMsgs();
        pushMsgsBuilder.addMsgs(pushOneMsgBuilder);

        BizDownPackage bizDownPackageBuilder = new BizDownPackage();
        bizDownPackageBuilder.setBizCode(0);
        bizDownPackageBuilder.setPacketType(EnumPacketType.NOTIFYCATION);
        bizDownPackageBuilder.setBizData(ByteStringMicro.copyFrom(pushMsgsBuilder.toByteArray()));

        DownPacket downPacketBuilder = new DownPacket();
        downPacketBuilder.setAppId(mGlobal.getAppid());
        downPacketBuilder.setSessionId("aa");
        downPacketBuilder.setChannelCode(200);
        downPacketBuilder.setSeq(0);
        downPacketBuilder.setBizPackage(bizDownPackageBuilder);

        return downPacketBuilder;
    }

    public static DownPacket mockOneOnlineEmptyNormalDownPacket() {

        PushMsgs pushMsgsBuilder = new PushMsgs();
        pushMsgsBuilder.clearMsgs();

        BizDownPackage bizDownPackageBuilder = new BizDownPackage();
        bizDownPackageBuilder.setBizCode(0);
        bizDownPackageBuilder.setPacketType(EnumPacketType.NOTIFYCATION);
        bizDownPackageBuilder.setBizData(ByteStringMicro.copyFrom(pushMsgsBuilder.toByteArray()));

        DownPacket downPacketBuilder = new DownPacket();
        downPacketBuilder.setAppId(mGlobal.getAppid());
        downPacketBuilder.setSessionId("aa");
        downPacketBuilder.setChannelCode(200);
        downPacketBuilder.setSeq(0);
        downPacketBuilder.setBizPackage(bizDownPackageBuilder);

        return downPacketBuilder;
    }
}
