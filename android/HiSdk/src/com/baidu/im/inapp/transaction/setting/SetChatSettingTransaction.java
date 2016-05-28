package com.baidu.im.inapp.transaction.setting;

import java.util.List;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.inapp.transaction.setting.processor.SetChatSettingProcessor;
import com.baidu.im.sdk.IMessageResultCallback;
import com.baidu.imc.callback.ResultCallback;
import com.baidu.imc.impl.im.store.ChatSetting;
import com.baidu.imc.impl.im.store.MemoryMsgStore;
import com.baidu.imc.impl.im.transaction.IMTransactionStart;

/**
 * Created by gerald on 3/24/16.
 */
public class SetChatSettingTransaction implements IMTransactionStart{

    private static final String TAG = "QueryChatSetting";
    private List<ChatSetting> settingList;
    private final ResultCallback resultCallback;
    private final MemoryMsgStore msgStore;


    public SetChatSettingTransaction(List<ChatSetting> settingList, ResultCallback resultCallback,
                                     MemoryMsgStore msgStore) {
        this.settingList = settingList;
        this.resultCallback = resultCallback;
        this.msgStore = msgStore;
    }

    @Override
    public void startWorkFlow() throws Exception {
        // build request and send
        SetChatSettingProcessor proc = new SetChatSettingProcessor(new IMessageResultCallback() {
            @Override
            public void onSuccess(String description, byte[] data) {
                // save setting to db

                int affectedLines = msgStore.insertOrUpdateChatSetting(settingList);
                LogUtil.i(TAG, "insertOrUpdate into db, affected lines = "+ affectedLines);
                if(resultCallback != null) {
                    resultCallback.result(true, null);
                }
                LogUtil.e(TAG, "setChatSettingTransaction Success");
            }

            @Override
            public void onFail(int errorCode) {
                if(resultCallback != null) {
                    resultCallback.result(false, null);
                }
                LogUtil.e(TAG, "setChatSettingTransaction Fail");
            }
        }, settingList);
        proc.startWorkFlow();
    }
}