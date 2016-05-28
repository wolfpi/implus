package com.baidu.im.frame.inappCallback;

import java.util.List;

import com.baidu.imc.impl.im.store.ChatSetting;

/**
 * Created by gerald on 3/25/16.
 */
public interface QueryChatSettingCallback {

    void chatSettingResult(List<ChatSetting> settings, long lastQueryTime);
}
