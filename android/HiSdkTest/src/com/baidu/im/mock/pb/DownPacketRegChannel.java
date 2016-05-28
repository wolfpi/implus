package com.baidu.im.mock.pb;

import android.content.Context;

import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.testutil.AssertUtil;

public class DownPacketRegChannel {

    public static DownPacket getSuccess(Context context) {

        try {
            return DownPacket.parseFrom(AssertUtil.readFile(context,
                    "downPacket__seq(1148)_channelCode(200)_bizCode(0)"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
