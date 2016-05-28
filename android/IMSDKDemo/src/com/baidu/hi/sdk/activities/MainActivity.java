package com.baidu.hi.sdk.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.hi.sdk.R;
import com.baidu.imc.IMPlusSDK;

public class MainActivity extends Activity {

    private String currentAccount;
    private String currentID;

    private TextView currentAccountTV;
    private TextView currentIDTV;

    private Button inboxBtn;
    private Button friendBtn;
    private Button myselfBtn;
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentAccountTV = (TextView) findViewById(R.id.tv_current_account);
        currentIDTV = (TextView) findViewById(R.id.tv_current_id);

        currentAccount = getIntent().getStringExtra("CURRENT_ACCOUNT");
        currentID = getIntent().getStringExtra("CURRENT_ID");

        this.getSharedPreferences("IMSDK" + IMPlusSDK.getImpClient().getCurrentUserID(), Context.MODE_PRIVATE).edit()
                .putString("CURRENT_ACCOUNT", currentAccount).commit();

        if(currentID == null || currentID.equals(""))
        	currentID = IMPlusSDK.getImpClient().getCurrentUserID();
        currentAccountTV.setText(currentAccount);
        currentIDTV.setText(currentID);

        inboxBtn = (Button) findViewById(R.id.btn_inbox);
        friendBtn = (Button) findViewById(R.id.btn_friend);
        myselfBtn = (Button) findViewById(R.id.btn_myself);
        logoutBtn = (Button) findViewById(R.id.btn_logout);
        friendBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                intent.putExtra("CURRENT_ACCOUNT", currentAccount);
                intent.putExtra("CURRENT_ID", currentID);
                MainActivity.this.startActivity(intent);
            }
        });

        inboxBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InboxActivity.class);
                intent.putExtra("CURRENT_ACCOUNT", currentAccount);
                intent.putExtra("CURRENT_ID", currentID);
                MainActivity.this.startActivity(intent);
            }
        });

        myselfBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        logoutBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                IMPlusSDK.getImpClient().logout();
                finish();
            }
        });
    }

}
