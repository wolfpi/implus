package com.baidu.hi.sdk.activities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.hi.sdk.R;
import com.baidu.imc.IMPlusSDK;
import com.baidu.imc.client.IMInbox;
import com.baidu.imc.listener.IMInboxListener;
import com.baidu.imc.message.IMFileMessage;
import com.baidu.imc.message.IMImageMessage;
import com.baidu.imc.message.IMInboxEntry;
import com.baidu.imc.message.IMTextMessage;
import com.baidu.imc.message.IMTransientMessage;
import com.baidu.imc.message.IMVoiceMessage;
import com.baidu.imc.message.content.IMTextMessageContent;
import com.baidu.imc.message.content.VoiceMessageContent;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.NotificationType;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class InboxActivity extends Activity implements IMInboxListener {

    private Button btnBackButton;

    private List<String> mListItems;
    private PullToRefreshListView mPullRefreshListView;
    private ArrayAdapter<String> mAdapter;
    private List<IMInboxEntry> inboxs = new ArrayList<IMInboxEntry>();

    private String currentAccount;
    private String currentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        btnBackButton = (Button) findViewById(R.id.btn_back);
        btnBackButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        });

        currentAccount = getIntent().getStringExtra("CURRENT_ACCOUNT");
        currentID = getIntent().getStringExtra("CURRENT_ID");

        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.listview);
        mListItems = new ArrayList<String>();
        mAdapter = new ArrayAdapter<String>(InboxActivity.this, android.R.layout.simple_list_item_1, mListItems);
        ListView actualListView = mPullRefreshListView.getRefreshableView();
        actualListView.setAdapter(mAdapter);
        actualListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String conversationID = inboxs.get(position - 1).getID();
                Intent intent = new Intent(InboxActivity.this, ConversationActivity.class);
                intent.putExtra("CONVERSATION_ID", conversationID);
                intent.putExtra("CURRENT_ACCOUNT", currentAccount);
                intent.putExtra("CURRENT_ID", currentID);
                InboxActivity.this.startActivity(intent);
            }
        });
        actualListView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String conversationID = inboxs.get(position - 1).getID();
                IMInbox inbox = IMPlusSDK.getImpClient().getIMInbox();
                if (!TextUtils.isEmpty(conversationID) && null != inbox) {
                    inbox.deleteIMInboxEntry(conversationID);
                }
                return true;
            }

        });
        mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {

            }
        });
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> arg0) {
                // get label
                String label =
                        DateUtils.formatDateTime(InboxActivity.this, System.currentTimeMillis(),
                                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                mPullRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });

        IMPlusSDK.getImpClient().getIMInbox().setIMInboxListener(this);
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void...params) {
            List<IMInboxEntry> tmpConversations = IMPlusSDK.getImpClient().getIMInbox().getIMInboxEntryList();

            inboxs.clear();
            inboxs.addAll(tmpConversations);

            List<String> mListItems = new LinkedList<String>();
            for (IMInboxEntry inbox : inboxs) {
                StringBuilder sb = new StringBuilder();
                sb.append(inbox.getID());
                sb.append(" ");
                sb.append(inbox.getUnreadCount());
                sb.append(" ");
                if (inbox.getLastMessage() != null ? inbox.getLastMessage().getAddresserID().equals(currentID) : false) {
                    sb.append("我:");
                } else {
                    sb.append(inbox.getLastMessage() != null ? inbox.getLastMessage().getAddresserName() + ":" : "");
                }
                sb.append(" ");
                if (inbox.getLastMessage() instanceof IMTextMessage) {
                    sb.append("文字："
                            + (((IMTextMessageContent) ((IMTextMessage) (inbox.getLastMessage()))
                                    .getMessageContentList().get(0))).getText());
                } else if (inbox.getLastMessage() instanceof IMImageMessage) {
                    sb.append("图片：" + ((IMImageMessage) (inbox.getLastMessage())).getImage().getWidth() + "*"
                            + ((IMImageMessage) (inbox.getLastMessage())).getImage().getHeight());
                } else if (inbox.getLastMessage() instanceof IMVoiceMessage) {
                    sb.append("语音：");
                    VoiceMessageContent voiceMessageContent = ((IMVoiceMessage) (inbox.getLastMessage())).getVoice();
                    if (null != voiceMessageContent) {
                        sb.append(voiceMessageContent.getDuration());
                    }
                } else if (inbox.getLastMessage() instanceof IMFileMessage) {
                    sb.append("文件： " + ((IMFileMessage) (inbox.getLastMessage())).getFile().getFileSize());
                } else {
                    sb.append(inbox.getLastMessage().getCompatibleText());
                }
                String itemID = sb.toString();
                mListItems.add(itemID);
            }
            return mListItems.toArray(new String[mListItems.size()]);
        }

        @Override
        protected void onPostExecute(String[] result) {
            mListItems.clear();
            for (String item : result) {
                mListItems.add(item);
            }

            mAdapter.notifyDataSetChanged();

            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshListView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }

    @Override
    public void onIMInboxEntryChanged(final List<IMInboxEntry> list) {

        InboxActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new GetDataTask().execute();
            }

        });
    }

    @Override
    public void onNewIMTransientMessageReceived(final IMTransientMessage transientMessage) {
        InboxActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(InboxActivity.this,
                        transientMessage.getAddresserID() + ":" + transientMessage.getContent(), Toast.LENGTH_SHORT)
                        .show();
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IMPlusSDK.getImpClient().getIMInbox().setIMInboxListener(null);
    }

    @Override
    public void onNotificationTypeSetting(AddresseeType addresseeType, String s, NotificationType notificationType) {

    }
}
