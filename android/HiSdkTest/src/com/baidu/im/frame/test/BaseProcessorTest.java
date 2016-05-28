package com.baidu.im.frame.test;

import com.baidu.im.frame.BaseProcessor;
import com.baidu.im.frame.MessageResponser;
import com.baidu.im.frame.NetworkChannel.NetworkChannelStatus;
import com.baidu.im.frame.ProcessorResult;
import com.baidu.im.frame.SequenceDispatcher;
import com.baidu.im.frame.SequenceSender;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;

import android.test.InstrumentationTestCase;
import junit.framework.Assert;

public class BaseProcessorTest extends InstrumentationTestCase {

    public static final String TAG = "BaseProcessorTest";

    @Override
    protected void setUp() {

    }

    @Override
    public void tearDown() {

    }

    public void testNormalSend() {
        SequenceDispatcher sd = new SequenceDispatcher();
        dummySequenceSender dss = new dummySequenceSender();
        dummyMessageResponser dmr = new dummyMessageResponser();
        BaseProcessor baseProcessor = new BaseProcessor(dss, sd, dmr);
        UpPacket up = new UpPacket();

        Assert.assertTrue(baseProcessor.send(up));
    }

    public void testAbNormalSend() {
        SequenceDispatcher sd = new SequenceDispatcher();
        dummySequenceSender dss = new dummySequenceSender();
        dummyMessageResponser dmr = new dummyMessageResponser();
        BaseProcessor baseProcessor = new BaseProcessor(dss, sd, dmr);
        UpPacket up = null;

        Assert.assertTrue(!baseProcessor.send(up));
    }

    public void testNormalReceive() {
        SequenceDispatcher sd = new SequenceDispatcher();
        dummySequenceSender dss = new dummySequenceSender();
        dummyMessageResponser dmr = new dummyMessageResponser();
        BaseProcessor baseProcessor = new BaseProcessor(dss, sd, dmr);
        DownPacket dp = new DownPacket();

        Assert.assertTrue(baseProcessor.onReceive(dp));
    }

    public void testAbNormalReceive() {
        SequenceDispatcher sd = new SequenceDispatcher();
        dummySequenceSender dss = new dummySequenceSender();
        dummyMessageResponser dmr = new dummyMessageResponser();
        BaseProcessor baseProcessor = new BaseProcessor(dss, sd, dmr);

        Assert.assertTrue(!baseProcessor.onReceive(null));
    }

    public void testAbNormalParameter() {
        BaseProcessor baseProcessor = new BaseProcessor(null, null, null);

        Assert.assertTrue(!baseProcessor.send(null));
        Assert.assertTrue(!baseProcessor.onReceive(null));
    }
}

class dummySequenceSender implements SequenceSender {

    @Override
    public void send(UpPacket upPacket) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendReconnect() {
        // TODO Auto-generated method stub

    }

    @Override
    public NetworkChannelStatus getNetworkChannelStatus() {
        // TODO Auto-generated method stub
        return null;
    }

}

class dummyMessageResponser implements MessageResponser {

    @Override
    public ProcessorResult onReceive(DownPacket downPacket, UpPacket upPacket) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getProcessorName() {
        // TODO Auto-generated method stub
        return null;
    }

}