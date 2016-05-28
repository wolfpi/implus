package com.baidu.hi.sdk.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.baidu.hi.sdk.R;
import com.baidu.hi.sdk.utils.ImageLoader;
import com.baidu.imc.message.content.FileMessageContent;

public class ImageDetailActivity extends Activity {

    private ImageView imageView;
    private String fileName;
    public static FileMessageContent fileMessageContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        imageView = (ImageView) findViewById(R.id.image);
        fileName = getIntent().getStringExtra("FILE_NAME");
        if (!TextUtils.isEmpty(fileName) && null != fileMessageContent) {
            ImageLoader.getInstance().displayImage(fileName, imageView, fileMessageContent);
        } else {
            finish();
        }
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        });
    }

}
