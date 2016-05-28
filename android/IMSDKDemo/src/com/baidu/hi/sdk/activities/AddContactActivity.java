package com.baidu.hi.sdk.activities;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.baidu.hi.sdk.Config;
import com.baidu.hi.sdk.Constant;
import com.baidu.hi.sdk.R;
import com.baidu.hi.sdk.utils.HttpUtil;
import com.baidu.hi.sdk.utils.ParamsGenerator;
import com.baidu.hi.sdk.utils.ResponsesParser;
import com.baidu.imc.IMPlusSDK;

public class AddContactActivity extends Activity {

    private TextView tvContent1;
    private TextView tvContent2;
    private EditText etContent1;
    private EditText etContent2;

    private Button btBack;
    private Button btConfirm;

    private RadioGroup rgType;

    private String currentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        currentID = getIntent().getStringExtra("CURRENT_ID");
        if (TextUtils.isEmpty(currentID)) {
            finish();
        }

        tvContent1 = (TextView) findViewById(R.id.tv_content1);
        tvContent2 = (TextView) findViewById(R.id.tv_content2);
        etContent1 = (EditText) findViewById(R.id.et_content1);
        etContent2 = (EditText) findViewById(R.id.et_content2);
        tvContent1.setText(R.string.tv_oppsite_name);
        tvContent2.setText(R.string.tv_oppsite_id);
        etContent1.setHint(R.string.et_oppsite_name_hint);
        etContent2.setHint(R.string.et_oppsite_id_hint);
        etContent2.setEnabled(true);

        btBack = (Button) findViewById(R.id.btn_back);
        btBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        });

        btConfirm = (Button) findViewById(R.id.btn_confirm);
        btConfirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String content1 = etContent1.getText().toString();
                String content2 = etContent2.getText().toString();
                if (rgType.getCheckedRadioButtonId() == R.id.rb_user) {
                    if (TextUtils.isEmpty(content1) || TextUtils.isEmpty(content2)) {
                        return;
                    }
                    addContact(content1, content2);
                    finish();
                } else if (rgType.getCheckedRadioButtonId() == R.id.rb_creategroup) {
                    if (TextUtils.isEmpty(content1) || TextUtils.isEmpty(content2)) {
                        return;
                    }
                    createGroup(content1, content2);
                } else if (rgType.getCheckedRadioButtonId() == R.id.rb_addmember) {
                    if (TextUtils.isEmpty(content1) || TextUtils.isEmpty(content2)) {
                        return;
                    }
                    addMember(content1, content2);
                } else if (rgType.getCheckedRadioButtonId() == R.id.rb_joingroup) {
                    if (TextUtils.isEmpty(content1)) {
                        return;
                    }
                    joinGroup(content1);
                }
            }

        });

        rgType = (RadioGroup) findViewById(R.id.rg_types);
        rgType.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_user) {
                    tvContent1.setText(R.string.tv_oppsite_name);
                    tvContent2.setText(R.string.tv_oppsite_id);
                    etContent1.setHint(R.string.et_oppsite_name_hint);
                    etContent2.setHint(R.string.et_oppsite_id_hint);
                    etContent2.setEnabled(true);
                } else if (checkedId == R.id.rb_creategroup) {
                    tvContent1.setText(R.string.tv_group_name);
                    tvContent2.setText(R.string.tv_group_desc);
                    etContent1.setHint(R.string.et_group_name_hint);
                    etContent2.setHint(R.string.et_group_desc_hint);
                    etContent2.setEnabled(true);
                } else if (checkedId == R.id.rb_addmember) {
                    tvContent1.setText(R.string.tv_group_id);
                    tvContent2.setText(R.string.tv_oppsite_id);
                    etContent1.setHint(R.string.et_group_id_hint);
                    etContent2.setHint(R.string.et_oppsite_id_hint);
                    etContent2.setEnabled(true);
                } else if (checkedId == R.id.rb_joingroup) {
                    tvContent1.setText(R.string.tv_group_id);
                    tvContent2.setText("");
                    etContent1.setHint(R.string.et_group_id_hint);
                    etContent2.setHint("");
                    etContent2.setEnabled(false);
                }
            }
        });
    }

    private void addContact(String name, String id) {
        String contactsStr =
                AddContactActivity.this.getSharedPreferences("IMSDK" + IMPlusSDK.getImpClient().getCurrentUserID(),
                        Context.MODE_PRIVATE).getString("CONTACTS", "");
        try {
            JSONArray contactsJsonArray;
            if (TextUtils.isEmpty(contactsStr)) {
                contactsJsonArray = new JSONArray();
            } else {
                contactsJsonArray = new JSONArray(contactsStr);
            }
            JSONObject contactObject = new JSONObject();
            contactObject.put("NAME", name);
            contactObject.put("ID", id);
            contactObject.put("TYPE", "USER");
            contactsJsonArray.put(contactObject);
            AddContactActivity.this
                    .getSharedPreferences("IMSDK" + IMPlusSDK.getImpClient().getCurrentUserID(), Context.MODE_PRIVATE)
                    .edit().putString("CONTACTS", contactsJsonArray.toString()).commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void saveGroup(String name, String desc, String id) {
        String contactsStr =
                AddContactActivity.this.getSharedPreferences("IMSDK" + IMPlusSDK.getImpClient().getCurrentUserID(),
                        Context.MODE_PRIVATE).getString("CONTACTS", "");
        try {
            JSONArray contactsJsonArray;
            if (TextUtils.isEmpty(contactsStr)) {
                contactsJsonArray = new JSONArray();
            } else {
                contactsJsonArray = new JSONArray(contactsStr);
            }
            JSONObject contactObject = new JSONObject();
            contactObject.put("NAME", name);
            contactObject.put("DESC", desc);
            contactObject.put("ID", id);
            contactObject.put("TYPE", "GROUP");
            contactsJsonArray.put(contactObject);
            AddContactActivity.this
                    .getSharedPreferences("IMSDK" + IMPlusSDK.getImpClient().getCurrentUserID(), Context.MODE_PRIVATE)
                    .edit().putString("CONTACTS", contactsJsonArray.toString()).commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createGroup(final String groupName, final String groupDesc) {
        new Thread() {

            @Override
            public void run() {
                Map<String, String> params = ParamsGenerator.getCreateGroupParameters(currentID, groupName, groupDesc);
                // System.out.println(params.toString());
                String url = Config.GROUP_URL;
                // System.out.println(url);
                String[] response = HttpUtil.getPostResponse(url, params, 10);
                // System.out.println(Arrays.toString(response));
                if (null != response && response[0].equals("200") && !TextUtils.isEmpty(response[2])) {
                    Map<String, String> responseMap = ResponsesParser.parseResponseString(response[2]);
                    if (null != responseMap && !responseMap.isEmpty()) {
                        for (Map.Entry<String, String> entry : responseMap.entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            System.out.println(key + " " + value);
                        }
                        String code = responseMap.get(Constant.CODE);
                        String info = responseMap.get(Constant.INFO);
                        String gid = responseMap.get(Constant.GID);
                        String name = responseMap.get(Constant.NAME);
                        String desc = responseMap.get(Constant.DESC);
                        System.out.println(code + " " + info + " " + gid + " " + name + " " + desc);
                        if ("100000".equals(code)) {
                            saveGroup(name, desc, gid);
                            finish();
                        }
                    }
                }

            }

        }.start();
    }

    private void addMember(final String groupID, final String userID) {
        new Thread() {

            @Override
            public void run() {
                Map<String, String> params = ParamsGenerator.getAddGroupMemberParameters(currentID, userID, groupID);
                // System.out.println(params.toString());
                String url = Config.GROUP_URL;
                // System.out.println(url);
                String[] response = HttpUtil.getPostResponse(url, params, 10);
                // System.out.println(Arrays.toString(response));
                if (null != response && response[0].equals("200") && !TextUtils.isEmpty(response[2])) {
                    Map<String, String> responseMap = ResponsesParser.parseResponseString(response[2]);
                    if (null != responseMap && !responseMap.isEmpty()) {
                        for (Map.Entry<String, String> entry : responseMap.entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            System.out.println(key + " " + value);
                        }
                        String code = responseMap.get(Constant.CODE);
                        String info = responseMap.get(Constant.INFO);
                        String gid = responseMap.get(Constant.GID);
                        String name = responseMap.get(Constant.NAME);
                        String desc = responseMap.get(Constant.DESC);
                        System.out.println(code + " " + info + " " + gid + " " + name + " " + desc);
                        if ("100000".equals(code)) {
                            finish();
                        }
                    }
                }
            }

        }.start();
    }

    private void joinGroup(String groupID) {
        // TODO
    }
}
