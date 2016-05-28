package com.baidu.hi.sdk.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.baidu.hi.sdk.R;
import com.baidu.hi.sdk.adapters.ChatMsgEntity;
import com.baidu.hi.sdk.adapters.ChatMsgViewAdapter;
import com.baidu.hi.sdk.utils.MessageUtil;
import com.baidu.imc.IMPlusSDK;
import com.baidu.imc.callback.PageableResult;
import com.baidu.imc.client.IMChatHistory;
import com.baidu.imc.impl.im.message.BDHiIMMessage;
import com.baidu.imc.message.IMMessage;
import com.baidu.imc.type.AddresseeType;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class HistoryActivity extends Activity {

    private Button btBack;
    private Button btDeleteAll;
    private String conversationID;
    private String oppsiteID;
    private AddresseeType oppsiteType;
    // private String currentAccount;
    // private String currentID;

    private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();

    private ChatMsgViewAdapter mAdapter;
    private PullToRefreshListView mPullRefreshListView;
    private ListView mListView;

    private static IMChatHistory conversation;
    private int pageNum;
    private static final int PAGE_SIZE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        conversationID = getIntent().getStringExtra("CONVERSATION_ID");
        // System.out.println(conversationID);
        String[] results = conversationID.split(":");
        oppsiteType = AddresseeType.valueOf(results[0]);
        oppsiteID = results[1];
        // currentAccount = getIntent().getStringExtra("CURRENT_ACCOUNT");
        // System.out.println(currentAccount);
        // currentID = getIntent().getStringExtra("CURRENT_ID");
        // System.out.println(currentID);

        btBack = (Button) findViewById(R.id.btn_back);
        btBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        });
        btDeleteAll = (Button) findViewById(R.id.btn_deleteall);
        btDeleteAll.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread() {

                    @Override
                    public void run() {
                        conversation.deleteAllMessage();
                        HistoryActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                pageNum = 0;
                                mDataArrays.clear();
                                mAdapter.notifyDataSetChanged();
                            }

                        });
                    }

                }.start();
            }
        });
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.listview);
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                new Thread() {

                    @Override
                    public void run() {
                        final PageableResult<IMMessage> result = conversation.getMessageList(pageNum, PAGE_SIZE);
                        if (null != result && null != result.getList() && !result.getList().isEmpty()) {
                            final List<ChatMsgEntity> tmpDatas = new ArrayList<ChatMsgEntity>();
                            for (int i = 0; i < result.getList().size(); i++) {
                                IMMessage message = (BDHiIMMessage) result.getList().get(i);
                                tmpDatas.add(MessageUtil.getChatMsgEntity((BDHiIMMessage) message));
                            }
                            final int beforePosition = tmpDatas.size();
                            pageNum++;
                            HistoryActivity.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    // Toast.makeText(HistoryActivity.this,
                                    // "Total:" + result.getTotal() + " size:" + result.getList().size(),
                                    // Toast.LENGTH_SHORT).show();
                                    // Toast.makeText(HistoryActivity.this, "beforePosition:" + beforePosition,
                                    // Toast.LENGTH_SHORT).show();
                                    mDataArrays.addAll(0, tmpDatas);
                                    mPullRefreshListView.onRefreshComplete();
                                    mAdapter.notifyDataSetChanged();
                                    mListView.setSelection(beforePosition + 1);
                                }

                            });
                        } else {
                            HistoryActivity.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    mPullRefreshListView.onRefreshComplete();
                                }

                            });
                        }
                    }

                }.start();
            }

        });
        mListView = mPullRefreshListView.getRefreshableView();

        mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
        mListView.setAdapter(mAdapter);

        conversation = IMPlusSDK.getImpClient().getIMChatHistory(oppsiteType, oppsiteID);
        pageNum = 0;
    }

}
