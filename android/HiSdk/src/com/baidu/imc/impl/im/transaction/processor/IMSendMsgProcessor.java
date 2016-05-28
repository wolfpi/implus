package com.baidu.imc.impl.im.transaction.processor;

import com.baidu.im.sdk.BinaryMessage;
import com.baidu.im.sdk.ChannelSdk;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.impl.im.transaction.processor.callback.SendMsgCallback;
import com.baidu.imc.impl.im.transaction.request.SendMsgRequest;
import com.baidu.imc.impl.im.transaction.response.SendMsgResponse;

public class IMSendMsgProcessor implements IMProcessorStart {

    private static final String TAG = "IMSendMsgProcessor";
    private SendMsgRequest sendMsg;
    private SendMsgResponse response;
    private SendMsgCallback mCallback = null;

    public IMSendMsgProcessor(SendMsgRequest sendMsg,SendMsgCallback callback) {
        this.sendMsg = sendMsg;
        this.mCallback = callback;
    }

    public String getProcessorName() {
        return TAG;
    }

    @Override
    public void startWorkFlow() throws Exception {
    	 
    	if(sendMsg == null)
    		return;
    	BinaryMessage  msg = sendMsg.createRequest();
    	if(msg == null)
    		return ;
    	
    	ChannelSdk.send(msg, new IMessageResultCallback() {

             @Override
             public void onSuccess(String description, byte[] data) {
                 // LogUtil.printImE("QueryActiveContacts Callback is called here");
                 if(mCallback != null) {
               	  mCallback.onSendMsgCallback(
               			  new SendMsgResponse(description, data, 0));
                 }
             }

             @Override
             public void onFail(int errorCode) {
           	  if(mCallback != null) {
               	  mCallback.onSendMsgCallback(
               			  new SendMsgResponse(null, null, errorCode));
                 }
             }
         });
    	/*
        if (send(sendMsg.createRequest())) {
            response = new SendMsgResponse(getDescription(), getData(), getErrCode());
        } else {
            response = new SendMsgResponse(getDescription(), getData(), -1);
        }*/
    }

    public SendMsgResponse getResponse() {
        return response;
    }

}
