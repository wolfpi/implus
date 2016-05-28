package com.baidu.im.frame.utils.test;

import junit.framework.Assert;
import android.test.InstrumentationTestCase;

import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.pb.ProUserLogin;
import com.baidu.im.frame.utils.ProtobufLogUtil;
import com.baidu.im.mock.pb.DownPacketAppLogin;
import com.baidu.im.outapp.network.ProtocolConverter;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.google.protobuf.micro.ByteStringMicro;

public class ProtobufLogUtilTest extends InstrumentationTestCase {

    public static final String TAG = "ProtobufLogUtilTest";
    
    @Override
    protected void setUp() {
   
    }
    
   @Override
   public void tearDown() {
     
   }
   
   public void testPacketLog() {
	   DownPacket dp = DownPacketAppLogin.getSuccess(this.getInstrumentation().getContext());
	   
	   Assert.assertTrue(ProtobufLogUtil.print(dp) != null);
	   
	   ProUserLogin.LoginReq appLoginReqBuilder = new ProUserLogin.LoginReq();
       appLoginReqBuilder.setChannelKey("channel");
       ProUserLogin.LoginReq appLoginReq = appLoginReqBuilder;

       com.baidu.im.frame.pb.ObjUpPacket.UpPacket upPacketBuilder = new UpPacket();
       upPacketBuilder.setServiceName(ServiceNameEnum.CoreSession.name());
       upPacketBuilder.setMethodName(MethodNameEnum.AppLogin.name());

       UpPacket upPacket = ProtocolConverter.convertUpPacket(ByteStringMicro.copyFrom(appLoginReq.toByteArray()), upPacketBuilder);
       
       Assert.assertTrue(ProtobufLogUtil.print(upPacket) != null);
   }
   
   
}