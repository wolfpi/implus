package com.baidu.hi.sdk.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.baidu.hi.sdk.R;
import com.baidu.imc.IMPlusSDK;
import com.baidu.imc.type.UserStatus;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent;
        if (IMPlusSDK.getImpClient().getCurrentUserStatus() == UserStatus.ONLINE) {
            intent = new Intent(SplashActivity.this, MainActivity.class);
            String currentID = IMPlusSDK.getImpClient().getCurrentUserID();
            intent.putExtra("CURRENT_ID", currentID);
            String currentAccount =
                    this.getSharedPreferences("IMSDK" + IMPlusSDK.getImpClient().getCurrentUserID(),
                            Context.MODE_PRIVATE).getString("CURRENT_ACCOUNT", "");
            intent.putExtra("CURRENT_ACCOUNT", currentAccount);
        } else {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
