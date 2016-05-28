package com.baidu.hi.sdk.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.hi.sdk.R;
import com.baidu.imc.IMPlusSDK;
import com.baidu.imc.impl.im.message.IMInboxEntryImpl;
import com.baidu.imc.message.IMInboxEntry;
import com.baidu.imc.type.AddresseeType;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ContactActivity extends Activity {

    private Button btnAddButton;
    private Button btnBackButton;

    private List<String> mListItems;
    private List<IMInboxEntry> inboxs = new ArrayList<IMInboxEntry>();
    private PullToRefreshListView mPullRefreshListView;
    private ArrayAdapter<String> mAdapter;

    private String currentAccount;
    private String currentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        btnAddButton = (Button) findViewById(R.id.btn_add);
        btnAddButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, AddContactActivity.class);
                intent.putExtra("CURRENT_ACCOUNT", currentAccount);
                intent.putExtra("CURRENT_ID", currentID);
                startActivity(intent);
            }

        });

        btnBackButton = (Button) findViewById(R.id.btn_back);
        btnBackButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        });

        currentAccount = getIntent().getStringExtra("CURRENT_ACCOUNT");
        System.out.println(currentAccount);
        currentID = getIntent().getStringExtra("CURRENT_ID");
        System.out.println(currentID);

        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.listview);
        mListItems = new ArrayList<String>();
        mAdapter = new ArrayAdapter<String>(ContactActivity.this, android.R.layout.simple_list_item_1, mListItems);
        ListView actualListView = mPullRefreshListView.getRefreshableView();
        actualListView.setAdapter(mAdapter);
        actualListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String conversationID = inboxs.get(position - 1).getID();
                Intent intent = new Intent(ContactActivity.this, ConversationActivity.class);
                intent.putExtra("CONVERSATION_ID", conversationID);
                intent.putExtra("CURRENT_ACCOUNT", currentAccount);
                intent.putExtra("CURRENT_ID", currentID);
                ContactActivity.this.startActivity(intent);
            }
        });
        actualListView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String conversationID = inboxs.get(position - 1).getID();
                deleteContact(conversationID);
                return false;
            }

        });
        mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {

            }
        });
    }

    @Override
    protected void onResume() {
        refreshAdapter();
        super.onResume();
    }

    private void refreshAdapter() {
        inboxs.clear();
        inboxs.addAll(getIMInboxEntries());
        mListItems.clear();
        for (IMInboxEntry inbox : inboxs) {
            StringBuilder sb = new StringBuilder();
            sb.append(inbox.getID());
            sb.append(" ");
            sb.append(((IMInboxEntryImpl) inbox).getAddresseeName());
            String item = sb.toString();
            mListItems.add(item);
        }
        mAdapter.notifyDataSetChanged();
    }

    private List<IMInboxEntry> getIMInboxEntries() {
        List<IMInboxEntry> list = new ArrayList<IMInboxEntry>();
        String contactsStr =
                ContactActivity.this.getSharedPreferences("IMSDK" + IMPlusSDK.getImpClient().getCurrentUserID(),
                        ContactActivity.MODE_PRIVATE).getString("CONTACTS", "");
        if (TextUtils.isEmpty(contactsStr)) {
            return list;
        }
        try {
            JSONArray contactsJsonArray = new JSONArray(contactsStr);
            for (int i = 0; i < contactsJsonArray.length(); i++) {
                JSONObject contactJsonObject = (JSONObject) contactsJsonArray.get(i);
                String name = (contactJsonObject.has("NAME") ? contactJsonObject.getString("NAME") : "");
                String id = (contactJsonObject.has("ID") ? contactJsonObject.getString("ID") : "");
                String type = contactJsonObject.has("TYPE") ? contactJsonObject.getString("TYPE") : "";
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(id)) {
                    continue;
                }

                IMInboxEntryImpl entry = new IMInboxEntryImpl();
                entry.setAddresseeName(name);
                entry.setAddresseeID(id);
                if ("USER".equals(type)) {
                    entry.setAddresseeType(AddresseeType.USER);
                } else if ("GROUP".equals(type)) {
                    entry.setAddresseeType(AddresseeType.GROUP);
                } else if ("SERVICE".equals(type)) {
                    entry.setAddresseeType(AddresseeType.SERVICE);
                } else {
                    entry.setAddresseeType(AddresseeType.USER);
                }
                list.add(entry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void deleteContact(String conversationID) {
        if (!TextUtils.isEmpty(conversationID)) {
            String[] results = conversationID.split(":");
            if (null != results && results.length == 2) {
                String contactsStr =
                        ContactActivity.this.getSharedPreferences(
                                "IMSDK" + IMPlusSDK.getImpClient().getCurrentUserID(), ContactActivity.MODE_PRIVATE)
                                .getString("CONTACTS", "");
                if (TextUtils.isEmpty(contactsStr)) {
                    return;
                }
                try {
                    JSONArray contactsJsonArray = new JSONArray(contactsStr);
                    for (int i = 0; i < contactsJsonArray.length(); i++) {
                        JSONObject contactJsonObject = (JSONObject) contactsJsonArray.get(i);
                        String id = (contactJsonObject.has("ID") ? contactJsonObject.getString("ID") : "");
                        String type = contactJsonObject.has("TYPE") ? contactJsonObject.getString("TYPE") : "";
                        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(type)) {
                            continue;
                        }
                        if (results[1].equals(id)) {
                            AddresseeType addresseeType = AddresseeType.valueOf(results[0]);
                            if (addresseeType == AddresseeType.USER && ("USER".equals(type) || TextUtils.isEmpty(type))) {
                                contactsJsonArray = jsonArrayRemovedElementAt(contactsJsonArray, i);
                                ContactActivity.this
                                        .getSharedPreferences("IMSDK" + IMPlusSDK.getImpClient().getCurrentUserID(),
                                                Context.MODE_PRIVATE).edit()
                                        .putString("CONTACTS", contactsJsonArray.toString()).commit();
                                refreshAdapter();
                                break;
                            } else if (addresseeType == AddresseeType.GROUP && "GROUP".equals(type)) {

                            } else if (addresseeType == AddresseeType.SERVICE && "SERVICE".equals(type)) {

                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 安卓JSONArray.remove(i) 需要 API > 19
     * @param jsonArray
     * @param index
     * @return
     */
    private JSONArray jsonArrayRemovedElementAt(JSONArray jsonArray, int index) {
        if(Build.VERSION.SDK_INT >= 19){
            jsonArray.remove(index);
            return jsonArray;
        }

        JSONArray list = new JSONArray();
        if (jsonArray != null) {
            final int len = jsonArray.length();

            for (int i = 0; i < len; i++) {
                //Excluding the item at position
                if (i != index) {
                    try {
                        list.put(jsonArray.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return list;
    }
}
