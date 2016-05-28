package com.baidu.imc.impl.im.client;

import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class IMPreference {

    private SharedPreferences userPreference;
    private AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static final String LAST_QUERY_INBOX_TIME = "lastQueryInboxTime";

    private static final String CHAT_SETTING_LAST_QUERY_TIME = "chatSettingLastQueryTime";

    public IMPreference() {

    }

    /**
     * 登录后初始化
     */
    public void initialize(Context context, String userID) {
        if (null != context && !TextUtils.isEmpty(userID)) {
            if (isInitialized.compareAndSet(false, true)) {
                userPreference = context.getSharedPreferences(userID, Context.MODE_PRIVATE);
            }
        }
    }

    /**
     * 登出后销毁
     */
    public void destory() {
        userPreference = null;
        isInitialized.set(false);
    }

    public void clear(){
        if(userPreference != null){
            userPreference.edit().clear().apply();
        }
    }

    /**
     * 获取最后拉取会话时间戳，若无法获取则下次再次初始化
     */
    public long getLastQueryInboxTime() {
        if (null != userPreference) {
            return userPreference.getLong(LAST_QUERY_INBOX_TIME, 0);
        } else {
            isInitialized.set(false);
            return 0;
        }
    }

    /**
     * 设置最后拉取会话时间戳，若无法设置则下次再次初始化
     */
    public void setLastQueryInboxTime(long lastQueryInboxTime) {
        if (null != userPreference) {
            userPreference.edit().putLong(LAST_QUERY_INBOX_TIME, lastQueryInboxTime).apply();
        } else {
            isInitialized.set(false);
        }
    }

    public void setChatSettingLastQueryTime(long chatSettingLastQueryTime) {
        if(userPreference == null){
            isInitialized.set(false);
        }else {
            userPreference.edit().putLong(CHAT_SETTING_LAST_QUERY_TIME, chatSettingLastQueryTime).apply();
        }
    }

    public long getChatSettingLastQueryTime() {
        if(userPreference == null){
            isInitialized.set(false);
            return 0L;
        }else {
            return userPreference.getLong(CHAT_SETTING_LAST_QUERY_TIME, 0L);
        }
    }
}
