package com.baidu.im.mock;

import com.baidu.im.frame.NetworkChannel;
import com.baidu.im.frame.outapp.MockServer;
import com.baidu.im.frame.pb.ObjBizDownPacket.BizDownPackage;
import com.baidu.im.frame.pb.ObjDownPacket.DownPacket;
import com.baidu.im.frame.pb.ObjUpPacket.UpPacket;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.mock.pb.DownPacketAppLogin;
import com.baidu.im.mock.pb.DownPacketAppLogout;
import com.baidu.im.mock.pb.DownPacketHeartbeat;
import com.baidu.im.mock.pb.DownPacketLogin;
import com.baidu.im.mock.pb.DownPacketLogout;
import com.baidu.im.mock.pb.DownPacketPush;
import com.baidu.im.mock.pb.DownPacketPushConfirm;
import com.baidu.im.mock.pb.DownPacketRegApp;
import com.baidu.im.mock.pb.DownPacketRegChannel;
import com.baidu.im.mock.pb.DownPacketSendData;
import com.baidu.im.mock.pb.DownPacketSetAppStatus;
import com.baidu.im.mock.pb.DownPacketUnRegApp;
import com.baidu.im.mock.pb.DownPacketUpdateConfig;
import com.baidu.im.outapp.network.ProtocolConverter;
import com.baidu.im.outapp.network.ProtocolConverter.MethodNameEnum;
import com.baidu.im.outapp.network.ProtocolConverter.ServiceNameEnum;
import com.baidu.im.testutil.MockObj;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

import android.content.Context;
import android.util.Log;

public class MockNetworkFramework {

    public static final String TAG = MockNetworkFramework.class.getSimpleName();

    private Context context;
    private MockObj mockPara;

    /**
     * @return the mockPara
     */
    public MockObj getMockPara() {
        return mockPara;
    }

    /**
     * @param mockPara the mockPara to set
     */
    public void setMockPara(MockObj mockPara) {
        this.mockPara = mockPara;

    }

    private static MockServer mockServer;
    private static MockNetworkFramework mockNetworkFramework = new MockNetworkFramework();

    private MockNetworkFramework() {
    }

    public synchronized static MockNetworkFramework getInstance() {
        if (mockServer == null) {
            mockServer = new MockServer();
        }
        return mockNetworkFramework;
    }

    public enum MockServerType {
        normal, exception
    }

    public void setChannel(Context context, NetworkChannel networkChannel) {
        this.context = context;
        mockServer.setNewNetworkChannel(networkChannel);
    }

    public void startMock() {
        mockServer.startMockServer();
    }

    public void stopMock() {
        mockServer.stopMockServer();
    }

    /**
     * 接收包
     *
     * @param upPacket
     * @param mockPara
     */
    public void receiveDownPacketByUpPacket(UpPacket upPacket, MockObj mockPara) {
        int seq = upPacket.getSeq();
        int bizCode = mockPara.getBizCode().getCode();
        int channelCode = mockPara.getChannelCode().getCode();
        ServiceNameEnum serviceName = ServiceNameEnum.valueOf(upPacket
                .getServiceName());
        MethodNameEnum methodName = MethodNameEnum.valueOf(upPacket
                .getMethodName());
        if (mockPara.getServiceName() != null) {
            serviceName = mockPara.getServiceName();
        }
        if (mockPara.getMethodName() != null) {
            methodName = mockPara.getMethodName();
        }
        DownPacket generateDownPacket = generateDownPacket(serviceName,
                methodName, seq, bizCode, channelCode);
        if (generateDownPacket == null) {
            Log.e(TAG, serviceName + "   " + methodName);
        }
        if (mockPara.isNullDownPacket()) {
            generateDownPacket = null;
        }
        if (!mockPara.isNoResponsePacketFlag()) {
            receiveDownPacket(generateDownPacket);
        }
    }

    /**
     * 接收正常包
     *
     * @param upPacket
     */
    public void receiveDownPacketByUpPacket(UpPacket upPacket) {
        receiveDownPacketByUpPacket(upPacket, new MockObj());
    }

    public void receiveDownPacket(
            ProtocolConverter.ServiceNameEnum serviceName,
            ProtocolConverter.MethodNameEnum methodName, int seq) {
        DownPacket generateDownPacket = generateDownPacket(serviceName,
                methodName, seq);
        receiveDownPacket(generateDownPacket);
    }

    public void receiveDownPacket(
            ProtocolConverter.ServiceNameEnum serviceName,
            ProtocolConverter.MethodNameEnum methodName, int seq, int bizCode) {
        DownPacket generateDownPacket = generateDownPacket(serviceName,
                methodName, seq, bizCode);
        receiveDownPacket(generateDownPacket);
    }

    public void receiveDownPacket(
            ProtocolConverter.ServiceNameEnum serviceName,
            ProtocolConverter.MethodNameEnum methodName, int seq, int bizCode,
            int channelCode) {
        DownPacket generateDownPacket = generateDownPacket(serviceName,
                methodName, seq, bizCode, channelCode);
        receiveDownPacket(generateDownPacket);
    }

    public void receiveDownPacket(DownPacket downPacket) {
        mockServer.sendDownPakcet(downPacket);
    }

    /**
     * Default bizCode = 0; <br>
     * Default channelCode = 200;
     *
     * @param serviceName
     * @param methodName
     * @param seq
     * @param bizCode
     *
     * @return
     */
    public DownPacket generateDownPacket(
            ProtocolConverter.ServiceNameEnum serviceName,
            ProtocolConverter.MethodNameEnum methodName, int seq) {
        return generateDownPacket(serviceName, methodName, seq, 0);
    }

    /**
     * Default channelCode = 200;
     *
     * @param serviceName
     * @param methodName
     * @param seq
     * @param bizCode
     *
     * @return
     */
    public DownPacket generateDownPacket(
            ProtocolConverter.ServiceNameEnum serviceName,
            ProtocolConverter.MethodNameEnum methodName, int seq, int bizCode) {
        return generateDownPacket(serviceName, methodName, seq, bizCode, 200);
    }

    public DownPacket generateDownPacket(
            ProtocolConverter.ServiceNameEnum serviceName,
            ProtocolConverter.MethodNameEnum methodName, int seq, int bizCode,
            int channelCode) {
        DownPacket downPacket = null;
        switch (methodName) {
            case RegChannel:
                downPacket = DownPacketRegChannel.getSuccess(context);
                break;

            case RegApp:
                downPacket = DownPacketRegApp.getSuccess(context);
                break;

            case UnRegApp:
                downPacket = DownPacketUnRegApp.getSuccess(context);
                break;

            case Login:
                downPacket = DownPacketLogin.getSuccess(context);
                break;

            case Logout:
                downPacket = DownPacketLogout.getSuccess(context);
                break;

            case AppLogin:
                downPacket = DownPacketAppLogin.getSuccess(context);
                break;

            case AppLogout:
                downPacket = DownPacketAppLogout.getSuccess(context);
                break;

            case Heartbeat:
                downPacket = DownPacketHeartbeat.getSuccess(context);
                break;

            case SetAppStatus:
                downPacket = DownPacketSetAppStatus.getSuccess(context);
                break;

            case Push:
                downPacket = DownPacketPush.getSuccess(context);
                break;

            case PushConfirm:
                downPacket = DownPacketPushConfirm.getSuccess(context);
                break;

            case SendData:
                downPacket = DownPacketSendData.getSuccess(context);
                break;
            case UpdateConfig:
                downPacket = DownPacketUpdateConfig.getSuccess(context);
                break;
            default:
                break;
        }
        if (downPacket != null) {

            DownPacket downPacketBuilder = null;
            try {
                downPacketBuilder = DownPacket.parseFrom(downPacket.toByteArray());

                downPacketBuilder.setSeq(seq);
                downPacketBuilder.setChannelCode(channelCode);

                BizDownPackage bizDownPackageBuilder =
                        BizDownPackage.parseFrom(downPacketBuilder.getBizPackage().toByteArray());
                bizDownPackageBuilder.setBizCode(bizCode);
                downPacketBuilder.setBizPackage(bizDownPackageBuilder);
                return downPacketBuilder;
            } catch (InvalidProtocolBufferMicroException e) {
                e.printStackTrace();
            }

        } else {
            LogUtil.e("testsave", serviceName + " " + methodName);
        }

        return null;
    }
}
