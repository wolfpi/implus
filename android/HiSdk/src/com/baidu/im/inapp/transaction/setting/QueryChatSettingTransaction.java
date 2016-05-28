package com.baidu.im.inapp.transaction.setting;

import java.util.List;
import com.baidu.im.frame.inappCallback.QueryChatSettingCallback;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.inapp.transaction.setting.processor.QueryChatSettingProcessor;
import com.baidu.imc.impl.im.store.ChatSetting;
import com.baidu.imc.impl.im.store.IMsgStore;
import com.baidu.imc.impl.im.store.MemoryMsgStore;
import com.baidu.imc.impl.im.transaction.IMTransactionStart;

/**
 * Created by gerald on 3/24/16.
 */
public class QueryChatSettingTransaction implements IMTransactionStart, QueryChatSettingCallback {

    private static final String TAG = "QueryChatSetting";
    private final MemoryMsgStore msgStore;

    public QueryChatSettingTransaction(IMsgStore msgStore) {
        this.msgStore = (MemoryMsgStore) msgStore;
    }

    @Override
    public void startWorkFlow() throws Exception {
        // build request and send
        final long lastQueryTime = msgStore.getChatSettingLastQueryTime();
        final QueryChatSettingProcessor proc = new QueryChatSettingProcessor(this, lastQueryTime);
        proc.startWorkFlow();
    }

    @Override
    public void chatSettingResult(List<ChatSetting> settings, long lastQueryTime) {
        // save to database
        LogUtil.i(TAG, "Saving Incoming ChatSettings");
        int newCount = msgStore.insertOrUpdateChatSetting(settings);
        msgStore.setChatSettingLastQueryTime(lastQueryTime);
        LogUtil.i(TAG, "Saving Incoming ChatSettings OK, count = "+ newCount);
        // notify?
    }
}