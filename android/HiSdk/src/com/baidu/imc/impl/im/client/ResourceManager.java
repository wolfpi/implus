package com.baidu.imc.impl.im.client;

import com.baidu.imc.client.LocalResourceManager;
import com.baidu.imc.client.RemoteResourceManager;

/**
 * ResourceManager.
 * 
 * 提供LocalResourceManager和RemoteResourceManger的默认实现，并且允许用户设置自己的资源管理器
 * 
 * @author liubinzhe
 */
public class ResourceManager {

    private static volatile ResourceManager resourceManager;

    private LocalResourceManager localResourceManager;
    private RemoteResourceManager remoteResourceManager;
    private BDHiLocalResourceManager bdhiLocalResoureManager;
    private BDHiRemoteResourceManager bdhiRemoteResourceManager;

    public static ResourceManager getInstance() {
        return resourceManager;
    }

    public static void init(IMConfig imConfig) {
        if (null == resourceManager) {
            synchronized (ResourceManager.class) {
                resourceManager = new ResourceManager(imConfig);
            }
        }
    }

    private ResourceManager(IMConfig imConfig) {
        this.bdhiLocalResoureManager = new BDHiLocalResourceManager();
        this.localResourceManager = bdhiLocalResoureManager;
        this.bdhiRemoteResourceManager = new BDHiRemoteResourceManager(localResourceManager, imConfig);
        this.remoteResourceManager = bdhiRemoteResourceManager;
    }

    /**
     * Get Local Resource Manager
     * 
     * @return localResourceManager
     */
    public LocalResourceManager getLocalResourceManager() {
        return localResourceManager;
    }

    /**
     * Set Local Resource Manager
     * 
     * @param localResourceManager
     */
    public void setLocalResourceManager(LocalResourceManager localResourceManager) {
        this.localResourceManager = localResourceManager;
    }

    /**
     * Get Remote Resource Manager
     * 
     * @return remoteResourceManager
     */
    public RemoteResourceManager getRemoteResourceManager() {
        return remoteResourceManager;
    }

    /**
     * Set Remote Resource Manager
     * 
     * @param remoteResourceManager
     */
    public void setRemoteResourceManager(RemoteResourceManager remoteResourceManager) {
        this.remoteResourceManager = remoteResourceManager;
    }

    /**
     * Get Default Local Resource Manager
     * 
     * @return BDHiLocalResourceManager
     */
    public LocalResourceManager getDefaultLocalResourceManager() {
        return bdhiLocalResoureManager;
    }

    /**
     * Get Default Remote Resource Manager
     * 
     * @return BDHiRemoteResourceManager
     */
    public RemoteResourceManager getDefaultRemoteRemoteManager() {
        return bdhiRemoteResourceManager;
    }
}
