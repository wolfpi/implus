package com.baidu.im.mock.pb;

import android.content.Context;

import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.testutil.AssertUtil;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class DownPacketSendData {

    public static DownPacket getSuccess(Context context) {

        try {
            return DownPacket.parseFrom(AssertUtil.readFile(context,
                    "downPacket__seq(1112)_channelCode(200)_bizCode(0)"));
        } catch (InvalidProtocolBufferMicroException e) {
            e.printStackTrace();
        }
        return null;
    }
}
