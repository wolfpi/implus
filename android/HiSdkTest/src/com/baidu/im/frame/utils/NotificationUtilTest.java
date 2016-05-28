//package com.baidu.im.frame.utils;
//
//import android.test.InstrumentationTestCase;
//
//import com.baidu.im.frame.pb.ObjInformDesc.InformDesc;
//import com.baidu.im.frame.pb.ObjInformMessage.InformMessage;
//import com.baidu.im.frame.pb.ObjKv.KV;
//import com.baidu.im.testutil.SetUpUtil;
//
//public class NotificationUtilTest extends InstrumentationTestCase {
//
//    public void testShowNormalWithSoundOnly() {
//        InformMessage informMessageBuilder = new InformMessage();
//        informMessageBuilder.setTitle("title");
//        informMessageBuilder.setDescription("description");
//
//        InformDesc informDescBuilder = new InformDesc();
//        informDescBuilder.setBuilderId(1);
//
//        informDescBuilder.setOpenType(1);
//        KV kvBuilder = new KV();
//        kvBuilder.setKey("url");
//        kvBuilder.setData("customschema://aaa/bbb");
//        informDescBuilder.addOpenArgs(kvBuilder);
//
//        informMessageBuilder.addImformDescs(informDescBuilder);
//        NotificationUtil.showNormal(this.getInstrumentation().getTargetContext(), informMessageBuilder.build());
//    }
//
//    public void testShowNormalWithVibrateOnly() {
//        InformMessage informMessageBuilder = new InformMessage();
//        informMessageBuilder.setTitle("title");
//        informMessageBuilder.setDescription("description");
//
//        InformDesc informDescBuilder = new InformDesc();
//        informDescBuilder.setBuilderId(2);
//        informDescBuilder.setOpenType(0);
//
//        informMessageBuilder.addImformDescs(informDescBuilder);
//        NotificationUtil.showNormal(this.getInstrumentation().getTargetContext(), informMessageBuilder.build());
//    }
//
//    public void testShowNormal() {
//        InformMessage informMessageBuilder = new InformMessage();
//        informMessageBuilder.setTitle("title");
//        informMessageBuilder.setDescription("description");
//
//        InformDesc informDescBuilder = new InformDesc();
//        informDescBuilder.setBuilderId(0);
//
//        informDescBuilder.setOpenType(1);
//        KV kvBuilder = new KV();
//        kvBuilder.setKey("url");
//        kvBuilder.setData("customschema://aaa/bbb");
//        informDescBuilder.addOpenArgs(kvBuilder);
//
//        informMessageBuilder.addImformDescs(informDescBuilder);
//        NotificationUtil.showNormal(this.getInstrumentation().getTargetContext(), informMessageBuilder.build());
//    }
//
//    public void testShowNormolWithLargeLogo() {
//        InformMessage informMessageBuilder = new InformMessage();
//        informMessageBuilder.setTitle("title");
//        informMessageBuilder.setDescription("description");
//
//        InformDesc informDescBuilder = new InformDesc();
//
//        informDescBuilder.setBuilderId(0);
//        KV kvBuilder1 = new KV();
//        kvBuilder1.setKey("largeLogo");
//        kvBuilder1.setData("http://img2.imgtn.bdimg.com/it/u=3884358878,1708776640&fm=21&gp=0.jpg");
//        informDescBuilder.addBuilderArgs(kvBuilder1);
//
//        informDescBuilder.setOpenType(1);
//        KV kvBuilder2 = new KV();
//        kvBuilder2.setKey("url");
//        kvBuilder2.setData("customschema://aaa/bbb");
//        informDescBuilder.addOpenArgs(kvBuilder2);
//
//        informMessageBuilder.addImformDescs(informDescBuilder);
//        NotificationUtil.showNormal(this.getInstrumentation().getTargetContext(), informMessageBuilder.build());
//    }
//
//    public void testShowNormolWithErrorLargeLogo() {
//        InformMessage informMessageBuilder = new InformMessage();
//        informMessageBuilder.setTitle("title");
//        informMessageBuilder.setDescription("description");
//
//        InformDesc informDescBuilder = new InformDesc();
//
//        informDescBuilder.setBuilderId(0);
//        KV kvBuilder1 = new KV();
//        kvBuilder1.setKey("largeLogo");
//        kvBuilder1.setData("http://fd.fsd.fd.fds.pfdsfd");
//        informDescBuilder.addBuilderArgs(kvBuilder1);
//
//        informDescBuilder.setOpenType(1);
//        KV kvBuilder2 = new KV();
//        kvBuilder2.setKey("url");
//        kvBuilder2.setData("customschema://aaa/bbb");
//        informDescBuilder.addOpenArgs(kvBuilder2);
//
//        informMessageBuilder.addImformDescs(informDescBuilder);
//        NotificationUtil.showNormal(this.getInstrumentation().getTargetContext(), informMessageBuilder.build());
//    }
//
//    public void testShowNormolWithoutNullInformDesc() {
//        InformMessage informMessageBuilder = new InformMessage();
//        informMessageBuilder.setTitle("title");
//        informMessageBuilder.setDescription("description");
//
//        NotificationUtil.showNormal(this.getInstrumentation().getTargetContext(), informMessageBuilder.build());
//    }
//
//}
