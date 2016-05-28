package com.baidu.im.inapp.transaction;


/**
 * Transaction基类，实现final的run方法。
 * 
 * @author zhaowei10
 * 
 */

public abstract class InAppBaseTransaction /*implements Transaction*/ {

	/*
    private JSONArray inAppProtocolArray = new JSONArray();

    @Override
    public final void run() {
        long beginTime = System.currentTimeMillis();
        ProcessorResult processorResult = null;
        try {
            Thread.currentThread().setName(getThreadName());
            processorResult = startWorkFlow();
        } catch (Throwable e) {
            LogUtil.fError(Thread.currentThread(), e);
            processorResult = new ProcessorResult(ProcessorCode.UNKNOWN_ERROR);
        } finally {
            long endTime = System.currentTimeMillis();
            JSONObject statObject = new JSONObject();
            try {
                statObject.put(TIME, endTime);
                statObject.put(KEY, this.hashCode());
                statObject.put(ACTION, getThreadName());
                statObject.put(RCODE, processorResult.getProcessorCode().getCode());
                statObject.put(COST, endTime - beginTime);
                JSONObject clientObject =
                        DeviceInfoMapUtil.getStatClient(InAppApplication.getInstance().getContext(), new JSONObject());
                if (null != clientObject) {
                    statObject.put(CLIENT, clientObject);
                }
                statObject.put(PROTOCOL, inAppProtocolArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogUtil.printStat(statObject.toString());
            ChannelSdkImpl.callback(this.hashCode(), processorResult);
            terminate();
        }

    }

    @Override
    public void terminate() {
    }

    @Override
    public ProcessorResult runProcessor(BaseProcessor processor) throws Exception {

        long beginTime = System.currentTimeMillis();
        ProcessorResult result = processor.process();
        long endTime = System.currentTimeMillis();
        JSONObject protocolObj = new JSONObject();
        try {
            protocolObj.put(
                    ID,
                    MurmurHash3.murmurhash3_x86_32(DeviceInfoUtil.getDeviceToken(InAppApplication.getInstance()
                            .getContext()) + processor.getListenSeq()));
            protocolObj.put(NAME, processor.getProcessorName());
            protocolObj.put(COST, endTime - beginTime);
            protocolObj.put(RCODE, result.getProcessorCode().getCode());
            protocolObj.put(TIME, endTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        inAppProtocolArray.put(protocolObj);
        return result;
    }*/

    /**
     * runProcessor and resolve bizCode 10103 & 10120
     */
	/*
    public ProcessorResult runProcessor2(BaseProcessor processor) throws Exception {
        ProcessorResult preProcessorResult = runProcessor(processor);
        if (preProcessorResult.getProcessorCode() != ProcessorCode.SUCCESS) {
            ProcessorResult postProcessorResult = processProcessorCode(preProcessorResult);
            if (postProcessorResult.getProcessorCode() == ProcessorCode.SUCCESS) {
                ProcessorResult reProcessorResult = runProcessor(processor);
                return reProcessorResult;
            } else {
                return postProcessorResult;
            }
        } else {
            return preProcessorResult;
        }
    }

    protected ProcessorResult processProcessorCode(ProcessorResult processorResult) throws Exception {
        ProcessorResult reUserLoginResult = reUserLogin(processorResult);
        ProcessorResult reRegAppResult = reRegApp(reUserLoginResult);
        return reRegAppResult;
    }*/

    /**
     * Re-Register App 1. regApp 2. heart_beat
     */
	/*
    private ProcessorResult reRegApp(ProcessorResult processorResult) throws Exception {
        if (processorResult.getProcessorCode() == ProcessorCode.UNREGISTERED_APP) {
            RegAppProsessor regAppProsessor = new RegAppProsessor();
            ProcessorResult regAppProcessorResult = runProcessor(regAppProsessor);
            switch (regAppProcessorResult.getProcessorCode()) {
                case SUCCESS:
                    LogUtil.printMainProcess("Re-Register app success.");
                    HeartbeatProsessor heartbeatProsessor = new HeartbeatProsessor();
                    ProcessorResult heartbeatProsessorResult = runProcessor(heartbeatProsessor);
                    switch (heartbeatProsessorResult.getProcessorCode()) {
                        case SUCCESS:
                            LogUtil.printMainProcess("Re-Register heartbeat success.");
                            // 设置SDK为可用状态
                            InAppApplication.getInstance().setConnected(true);
                            break;
                        default:
                            LogUtil.printMainProcess("Re-Register heartbeat error.");
                            break;
                    }
                    processorResult.setProcessorCode(heartbeatProsessorResult.getProcessorCode());
                    break;
                default:
                    LogUtil.printMainProcess("Re-Register app error.");
                    processorResult.setProcessorCode(regAppProcessorResult.getProcessorCode());
                    break;
            }
        }
        return processorResult;
    }
	*/
    /**
     * Re-Login User 1. UserLogin 2. RegApp if needed 3. UserLogin if needed 4. HeartBeat
     */
	/*
    private ProcessorResult reUserLogin(ProcessorResult processorResult) throws Exception {
        if (processorResult.getProcessorCode() == ProcessorCode.SESSION_ERROR) {
            if (null == InAppApplication.getInstance().getSession().getSessionInfo().getToken()
                    || InAppApplication.getInstance().getSession().getSessionInfo().getToken().length() == 0) {
                return processorResult;
            }
            if (null == InAppApplication.getInstance().getSession().getSessionInfo().getAccountId()
                    || InAppApplication.getInstance().getSession().getSessionInfo().getAccountId().length() == 0) {
                return processorResult;
            }
            LoginMessage loginMessage = new LoginMessage();
            loginMessage.setToken(InAppApplication.getInstance().getSession().getSessionInfo().getToken());
            loginMessage.setAccountId(InAppApplication.getInstance().getSession().getSessionInfo().getAccountId());
            UserLoginProsessor userLoginProcessor = new UserLoginProsessor(loginMessage);
            ProcessorResult userLoginProsessorResult = runProcessor(userLoginProcessor);
            switch (userLoginProsessorResult.getProcessorCode()) {
                case SUCCESS:
                    LogUtil.printMainProcess("Re-UserLogin success.");
                    // 登陆后的第一个心跳，根据bizCode判断是否一切就绪。
                    HeartbeatProsessor heartbeatProsessor = new HeartbeatProsessor();
                    ProcessorResult heartbeatProsessorResult = runProcessor(heartbeatProsessor);
                    switch (heartbeatProsessorResult.getProcessorCode()) {
                        case SUCCESS:
                            LogUtil.printMainProcess("Re-UserLogin heartbeat success.");
                            break;
                        default:
                            LogUtil.printMainProcess("Re-UserLogin heartbeat error.");
                            break;
                    }
                    processorResult.setProcessorCode(heartbeatProsessorResult.getProcessorCode());
                    break;
                case UNREGISTERED_APP:
                    LogUtil.printMainProcess("Re-UserLogin App has not been registered on im server");
                    // Register app.
                    RegAppProsessor registerAppProsessor = new RegAppProsessor();
                    ProcessorResult registerAppProsessorResult = runProcessor(registerAppProsessor);
                    switch (registerAppProsessorResult.getProcessorCode()) {
                        case SUCCESS:
                            LogUtil.printMainProcess("Re-UserLogin Re-RegApp success.");
                            // user login
                            UserLoginProsessor reUserLoginProsessor = new UserLoginProsessor(loginMessage);
                            ProcessorResult reUserLoginProsessorResult = runProcessor(reUserLoginProsessor);
                            switch (reUserLoginProsessorResult.getProcessorCode()) {
                                case SUCCESS:
                                    LogUtil.printMainProcess("Re-UserLogin Re-UserLogin success.");
                                    // 登陆后的第一个心跳，根据bizCode判断是否一切就绪。
                                    HeartbeatProsessor heartbeatProsessor2 = new HeartbeatProsessor();
                                    ProcessorResult heartbeatProsessorResult2 = runProcessor(heartbeatProsessor2);
                                    switch (heartbeatProsessorResult2.getProcessorCode()) {
                                        case SUCCESS:
                                            LogUtil.printMainProcess("Re-UserLogin Re-UserLogin heartbeat success.");
                                            break;
                                        default:
                                            LogUtil.printMainProcess("Re-UserLogin Re-UserLogin heartbeat error.");
                                            break;
                                    }
                                    processorResult.setProcessorCode(heartbeatProsessorResult2.getProcessorCode());
                                    break;
                                default:
                                    LogUtil.printMainProcess("Re-UserLogin Re-UserLogin login error.");
                                    processorResult.setProcessorCode(reUserLoginProsessorResult.getProcessorCode());
                                    break;
                            }
                            break;
                        default:
                            LogUtil.printMainProcess("Re-UserLogin Register app error.");
                            processorResult.setProcessorCode(registerAppProsessorResult.getProcessorCode());
                            break;
                    }
                    break;
                default:
                    LogUtil.printMainProcess("Re-UserLogin error.");
                    processorResult.setProcessorCode(userLoginProsessorResult.getProcessorCode());
                    break;
            }
        }
        return processorResult;
    }*/
}
