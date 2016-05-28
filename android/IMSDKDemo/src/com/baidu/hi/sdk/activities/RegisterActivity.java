package com.baidu.hi.sdk.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.hi.sdk.Config;
import com.baidu.hi.sdk.R;
import com.baidu.hi.sdk.utils.HttpUtil;
import com.baidu.imc.IMPlusSDK;

public class RegisterActivity extends Activity {

    private EditText etAccount;
    private EditText etPassword;
    private EditText etRePassword;

    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_pwd);
        etRePassword = (EditText) findViewById(R.id.et_re_pwd);

        btRegister = (Button) findViewById(R.id.btn_register);

        btRegister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final String account = etAccount.getText().toString();
                final String password = etPassword.getText().toString();
                final String repassword = etRePassword.getText().toString();
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
                    Toast.makeText(RegisterActivity.this, "请输入账号和密码。", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread() {

                    @Override
                    public void run() {
                        String url = String.format(Config.REGISTER_URL, account, password, repassword);
                        String httpResult = HttpUtil.getHttpResponse(url);
                        if (TextUtils.isEmpty(httpResult)) {
                            return;
                        }
                        boolean result = false;
                        try {
                            JSONObject jsonResult = new JSONObject(httpResult);
                            if (jsonResult.has("code") && jsonResult.has("info")) {
                                if (jsonResult.getInt("code") == 100000) {
                                    result = true;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (result) {
                            RegisterActivity.this
                                    .getSharedPreferences("IMSDK" + IMPlusSDK.getImpClient().getCurrentUserID(),
                                            Context.MODE_PRIVATE).edit().putString("LOGIN_ACCOUNT", account)
                                    .putString("LOGIN_PASSWORD", password).commit();
                            RegisterActivity.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                }
                            });
                        } else {
                            RegisterActivity.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "注册失败。", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                }.start();
            }

        });
    }

}
