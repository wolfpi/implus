/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.frame.outapp;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.os.Messenger;
import android.text.TextUtils;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.frame.utils.StringUtil;

/**
 * @author zhaowei10
 * 
 */
public class Router {

    public static final String TAG = "Router";

    @SuppressLint("UseSparseArrays")
    private Map<Integer, ClientHandler> providersByClientId = Collections
            .synchronizedMap(new HashMap<Integer, ClientHandler>());
    private Map<String, ClientHandler> providersByAppId = Collections
            .synchronizedMap(new HashMap<String, ClientHandler>());

    /**
     * Register client messenger when in-app-sdk binds.
     */
    public void register(int clientId, Messenger messenger) {
    	if(messenger == null)
    		return ;
    	
    	if( providersByClientId.get(clientId) == null )
    	{
        ClientHandler clientHandler = new ClientHandler(clientId, messenger);
        providersByClientId.put(clientId, clientHandler);

        LogUtil.printMainProcess(TAG, "register on remote service success. clientId=" + clientId + ", clientMessenger="
                + messenger.hashCode());
    	}
    }

    /**
     * Register client messenger when in-app-sdk binds.
     */
    public void register(String appId, ClientHandler clientHandler) {
    	if(StringUtil.isStringInValid(appId)|| clientHandler == null)
    		return;
    	
        providersByAppId.put(appId, clientHandler);
    }

    public void unRegister(int clientId) {
        ClientHandler clientHandler = providersByClientId.remove(clientId);
        if (clientHandler != null && !TextUtils.isEmpty(clientHandler.getAppId())) {
            providersByAppId.remove(clientHandler.getAppId());
        }
    }

    public ClientHandler getClientHandler(int clientId) {
        return providersByClientId.get(clientId);
    }

    public ClientHandler getClientHandler(String appId) {
    	if(StringUtil.isStringInValid(appId))
    		return null;
    	
        return providersByAppId.get(appId);
    }

    /**
     * 返回 Client端(in-app-sdk)的messenger，用于发送aidl消息。
     * 
     * @param clientId
     * @return
     */
    public Messenger getMessenger(int clientId) {
        ClientHandler clientHandler = getClientHandler(clientId);
        if (clientHandler == null) {
            return null;
        } else {
            return clientHandler.getMessenger();
        }
    }

    public final Set<Integer> getAllClientIds() {
        return providersByClientId.keySet();
    }
    
    public final Collection<ClientHandler> getAllMessengers()
    {
    	return providersByClientId.values();
    }

    public void destroy() {
        providersByClientId.clear();
    }

}
