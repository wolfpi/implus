package com.baidu.im.inapp.transaction.setting;

import java.util.ArrayList;
import java.util.List;

import com.baidu.im.frame.inappCallback.QueryChatSettingCallback;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.im.inapp.transaction.setting.processor.GetChatSettingProcessor;
import com.baidu.imc.impl.im.store.ChatSetting;
import com.baidu.imc.impl.im.store.MemoryMsgStore;
import com.baidu.imc.impl.im.transaction.IMTransactionStart;
import com.baidu.imc.listener.IMInboxListener;
import com.baidu.imc.type.AddresseeType;

/**
 * Created by gerald on 3/24/16.
 */
public class GetChatSettingTransaction implements IMTransactionStart, QueryChatSettingCallback {

    private static final String TAG = "QueryChatSetting";
    private final AddresseeType addresseeType;
    private final String addresseeID;
    private final MemoryMsgStore msgStore;

    public GetChatSettingTransaction(MemoryMsgStore msgStore, AddresseeType addresseeType, String addresseeID) {
        this.addresseeType = addresseeType;
        this.addresseeID = addresseeID;
        this.msgStore = msgStore;
    }

    @Override
    public void startWorkFlow() throws Exception {
        // build request and send
        final GetChatSettingProcessor proc = new GetChatSettingProcessor(addresseeType, addresseeID, this);
        proc.startWorkFlow();
    }

    @Override
    public void chatSettingResult(List<ChatSetting> settings, long lastQueryTime) {
        // save to database
        LogUtil.i(TAG, "Saving Incoming ChatSettings");
        int changedCount = msgStore.insertOrUpdateChatSetting(settings);
        LogUtil.i(TAG, "Saving Incoming ChatSettings OK, changed "+ changedCount+" items");
        if(changedCount > 0) {
            // notify
            final List<IMInboxListener> listeners = new ArrayList<IMInboxListener>(msgStore.getInboxListeners());
            for (IMInboxListener listener : listeners) {
                for (ChatSetting setting : settings) {
                    listener.onNotificationTypeSetting(setting.getChatType(), setting.getTargetID(), setting.getReceiveMode());
                }
            }
        }


    }

}