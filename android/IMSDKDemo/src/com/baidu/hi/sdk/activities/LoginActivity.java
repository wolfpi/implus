package com.baidu.hi.sdk.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.hi.sdk.Config;
import com.baidu.hi.sdk.R;
import com.baidu.hi.sdk.utils.HttpUtil;
import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.IMPlusSDK;
import com.baidu.imc.listener.ClientStatusListener;
import com.baidu.imc.listener.PushMessageListener;
import com.baidu.imc.message.PushMessage;
import com.baidu.imc.type.ClientConnectStatus;
import com.baidu.imc.type.UserStatus;

public class LoginActivity extends Activity {

    private TextView statusTV;
    private TextView feedbackTV;
    private EditText accountET;
    private EditText passwordET;;
    private Button registerBtn;
    private Button loginBtn;

    private boolean isConnected = false;
    private boolean isLogined = false;

    private String currentAccount;
    private String currentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        statusTV = (TextView) findViewById(R.id.tv_status);
        feedbackTV = (TextView) findViewById(R.id.tv_feedback);

        accountET = (EditText) findViewById(R.id.et_account);
        passwordET = (EditText) findViewById(R.id.et_pwd);

        registerBtn = (Button) findViewById(R.id.btn_register);
        loginBtn = (Button) findViewById(R.id.btn_login);

        loginBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!isConnected) {
                    Toast.makeText(LoginActivity.this, "请等待连接...", Toast.LENGTH_LONG).show();
                    return;
                }
                final String account = accountET.getText().toString();
                final String password = passwordET.getText().toString();
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "请输入账号密码。", Toast.LENGTH_LONG).show();
                    return;
                }
                currentAccount = account;
                new Thread() {
                    public void run() {
                        String url = String.format(Config.LOGIN_URL, account, password);
                        String httpResult = HttpUtil.getHttpResponse(url);
                        if (TextUtils.isEmpty(httpResult)) {
                            return;
                        }
                        boolean result = false;
                        String token = null;
                        String uid = null;
                        try {
                            JSONObject jsonResult = new JSONObject(httpResult);
                            if (jsonResult.has("code") && jsonResult.has("token") && jsonResult.has("uid")) {
                                token = jsonResult.getString("token");
                                uid = jsonResult.getString("uid");
                                if (jsonResult.getInt("code") == 200000 && !TextUtils.isEmpty(token)
                                        && !TextUtils.isEmpty(uid)) {
                                    result = true;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (result) {
                            LoginActivity.this
                                    .getSharedPreferences("IMSDK" + IMPlusSDK.getImpClient().getCurrentUserID(),
                                            Context.MODE_PRIVATE).edit().putString("LOGIN_ACCOUNT", account)
                                    .putString("LOGIN_PASSWORD", password).commit();
                            currentID = uid;
                            IMPlusSDK.getImpClient().login(uid, token);
                        } else {
                            LoginActivity.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    closeLoginProgressDialog();
                                    Toast.makeText(LoginActivity.this, "登录失败。", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }.start();

                showLoginProgressDialog();
            }

        });

        registerBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }

        });

        IMPlusSDK.getImpClient().setClientStatusListener(clientStatusListener);
        IMPlusSDK.getImpClient().setPushMessageListener(new PushMessageListener() {

            @Override
            public boolean onPushMessageReceived(PushMessage pushmsg) {
            	
            	/*
            	 * pushmsg.getNotification().getTitle();
            	 * pushmsg.getNotification().getAlert();
            	 * 
            	 * 如果app 自己处理了 notification 则 返回 true
            	 * 没有处理 返回false
            	 */
                return false;
            }

        });
        isConnected = IMPlusSDK.getImpClient().getCurrentClientConnectStatus() == ClientConnectStatus.CONNECTED;
        if (isConnected) {
            statusTV.setText("连接成功");
        }
        feedbackTV.setText("ClientConnectStatus:" + IMPlusSDK.getImpClient().getCurrentClientConnectStatus().name()
                + "\n" + "UserStatus:" + IMPlusSDK.getImpClient().getCurrentUserStatus().name());
        new Thread() {
            public void run() {
                boolean autoLogin = false;
                if (autoLogin) {
                	
                    String url = String.format(Config.LOGIN_URL, "test100", "123");
                    String httpResult = HttpUtil.getHttpResponse(url);
                    if (TextUtils.isEmpty(httpResult)) {
                        return;
                    }
                    boolean result = false;
                    String token = null;
                    String uid = null;
                    try {
                        JSONObject jsonResult = new JSONObject(httpResult);
                        if (jsonResult.has("code") && jsonResult.has("token") && jsonResult.has("uid")) {
                            token = jsonResult.getString("token");
                            uid = jsonResult.getString("uid");
                            if (jsonResult.getInt("code") == 200000 && !TextUtils.isEmpty(token)
                                    && !TextUtils.isEmpty(uid)) {
                                result = true;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (result) {
                        currentID = uid;
                        currentAccount = "qwer";
                        LoginActivity.this.getSharedPreferences("IMSDK" + currentAccount, Context.MODE_PRIVATE).edit()
                                .putString("LOGIN_ACCOUNT", "asdf").putString("LOGIN_PASSWORD", "123").commit();
                       // IMPlusSDK.getImpClient().connect();
                        IMPlusSDK.getImpClient().login(uid, token);
                    } else {
                        LoginActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                closeLoginProgressDialog();
                                Toast.makeText(LoginActivity.this, "登录失败。", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    IMPlusSDK.getImpClient().connect();
                }
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String account =
                this.getSharedPreferences("IMSDK" + IMPlusSDK.getImpClient().getCurrentUserID(), Context.MODE_PRIVATE)
                        .getString("LOGIN_ACCOUNT", null);
        String password =
                this.getSharedPreferences("IMSDK" + IMPlusSDK.getImpClient().getCurrentUserID(), Context.MODE_PRIVATE)
                        .getString("LOGIN_PASSWORD", null);
        if (!TextUtils.isEmpty(account)) {
            accountET.setText(account);
        }
        if (!TextUtils.isEmpty(password)) {
            passwordET.setText(password);
        }
    }

    private ClientStatusListener clientStatusListener = new ClientStatusListener() {

        @Override
        public void onUserStatusChanged(final UserStatus status) {
            LoginActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, "UserStatus:" + status.name(), Toast.LENGTH_SHORT).show();
                    feedbackTV.setText("ClientConnectStatus:"
                            + IMPlusSDK.getImpClient().getCurrentClientConnectStatus().name() + "\n" + "UserStatus:"
                            + IMPlusSDK.getImpClient().getCurrentUserStatus().name());
                }

            });
            if (status == UserStatus.ONLINE && !isLogined) {
                isLogined = true;

                LoginActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        closeLoginProgressDialog();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("CURRENT_ACCOUNT", currentAccount);
                        intent.putExtra("CURRENT_ID", currentID);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }

        @Override
        public void onLoginError(final int code, final String errMessage) {
            LoginActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    closeLoginProgressDialog();
                    feedbackTV.setText(errMessage + "\r\n" + code);
                }
            });
        }

        @Override
        public void onConnectError(final int code, final String errMessage) {
            LoginActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    isConnected = false;
                    statusTV.setText("连接失败:" + errMessage + "(" + code + ")");
                }
            });
        }

        @Override
        public void onConnect(String arg0) {
            LoginActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    isConnected = true;
                    statusTV.setText("连接成功");
                }
            });
        }

        @Override
        public void onClientConnectStatusChanged(final ClientConnectStatus status) {
            LoginActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (IMPlusSDK.getImpClient().getCurrentClientConnectStatus() == ClientConnectStatus.CONNECTED) {
                        isConnected = true;
                    }
                    Toast.makeText(LoginActivity.this, "ClientConnectStatus:" + status.name(), Toast.LENGTH_SHORT)
                            .show();
                    feedbackTV.setText("ClientConnectStatus:"
                            + IMPlusSDK.getImpClient().getCurrentClientConnectStatus().name() + "\n" + "UserStatus:"
                            + IMPlusSDK.getImpClient().getCurrentUserStatus().name());
                }

            });
        }
    };

    private ProgressDialog progressDialog;

    /**
     * 显示提示dialog
     */
    private void showLoginProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在登陆...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭提示dialog
     */
    private void closeLoginProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    
}
