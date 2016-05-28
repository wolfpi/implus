package com.baidu.hi.sdk.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.widget.Toast;

import com.baidu.hi.sdk.Constant;

public class IntentUtil {

    public static void openImageAlbums(Context context) {
        try {
            // Launch picker to choose photo for selected contact
            final Intent intent = getPhotoPickIntent();
            ((Activity) context).startActivityForResult(intent, Constant.PHOTO_PICKED_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "无法打开相册", Toast.LENGTH_LONG).show();
        }
    }

    public static void openImageAlbums2(Context context) {
        try {
            // Launch picker to choose photo for selected contact
            final Intent intent = getPhotoPickIntent();
            ((Activity) context).startActivityForResult(intent, Constant.PHOTO_PICKED_WITH_DATA2);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "无法打开相册", Toast.LENGTH_LONG).show();
        }
    }

    public static void openImageAlbums3(Context context) {
        try {
            // Launch picker to choose photo for selected contact
            final Intent intent = getPhotoPickIntent();
            ((Activity) context).startActivityForResult(intent, Constant.PHOTO_PICKED_WITH_DATA3);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "无法打开相册", Toast.LENGTH_LONG).show();
        }
    }

    private static Intent getPhotoPickIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        return intent;
    }

}
