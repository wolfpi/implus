package com.baidu.im.mock.pb;

import android.content.Context;

import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.testutil.MockDownPacket;

public class DownPacketPush {

    public static final String MOCK_MESSAGE = "message";

    public static DownPacket getSuccess(Context context) {
        return MockDownPacket.mockOneOnlineMsgDownPacket(System.currentTimeMillis(), MOCK_MESSAGE.getBytes());
    }
}
