package com.baidu.im.frame.outapp.processor;

import android.test.AndroidTestCase;

import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.outapp.OutAppApplication;
import com.baidu.im.outapp.transaction.processor.OutAppShowOffLineMessageProcessor;
import com.baidu.im.testutil.MockDownPacket;
import com.baidu.im.testutil.Sleeper;

public class OutAppShowOffLineMessageProcessorTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        OutAppApplication.getInstance().initialize(this.getContext());
        super.setUp();
    }

    public void testShowOffLineMessage() {
        DownPacket downPacket = MockDownPacket.mockOneOfflineMsgDownPacket(System.currentTimeMillis());

        /*
        try {

            for (int i = 0; i < 2; i++) {
               // new OutAppShowOffLineMessageProcessor(downPacket).process();

                Sleeper.waitWithoutBlock(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}