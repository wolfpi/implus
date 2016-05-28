package com.baidu.im.frame.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;

import com.baidu.im.constant.Constant;
import com.baidu.im.frame.pb.ObjInformDesc.InformDesc;
import com.baidu.im.frame.pb.ObjInformMessage.InformMessage;
import com.baidu.im.frame.pb.ObjKv.KV;

public class NotificationUtil {

    public static final String TAG = "NotificationUtil";

    //private static final int messageNotificationID = 0x125;

    private static ExecutorService downloadThreadPool;
    
    private static String getRootActivity(Context context)
    {
    	String acitivityName = "";
    	if(context == null)
    		return acitivityName;
    	
    	PackageManager pm = context.getPackageManager();
          if (null != pm) {
        	try {
				ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);
				
				if((appInfo != null) && (appInfo.metaData != null) ) {
					acitivityName=appInfo.metaData.getString("bootactivity");
					if(!TextUtils.isEmpty(acitivityName)) {
					  if(acitivityName.startsWith(".")) {
						  acitivityName = context.getPackageName() + acitivityName;
					  }
					 }
				}
			} catch (NameNotFoundException e) {
				LogUtil.i(TAG, "meta data is null");
			}
          }
    	
        //LogUtil.i(TAG, acitivityName);
        if(acitivityName == null) {
        	acitivityName = "";
        }
    	return acitivityName;
    }
    
    public static void showNormal(Context context, InformMessage informMessage, int appid) {

    	if(!useSDKNotification(context))
    		return ;
    	LogUtil.i(TAG, "show normal push");
        if (informMessage == null || context  == null) {
        	LogUtil.i(TAG, "show empty push");
            return;
        }
        List<InformDesc> informDescs = informMessage.getImformDescsList();
        InformDesc informDesc = null;
        if (informDescs != null && informDescs.size() > 0) {
            informDesc = informDescs.get(0);
        }

        // push content
        String title = informMessage.getTitle();
        String content = informMessage.getDescription();

       //LogUtil.i(TAG, title + content);
        // enable sound by default
        boolean enableSound = true;

        // enable vibrate by default
        boolean enableVibrate = true;

        int builderId = 0;

        // how to show and perform when click
        String clickUrl = null;
        if (informDesc != null) {
            // set click url if there is.
            if (informDesc.getOpenType() == 1) {
                try {
                    for (KV kv : informDesc.getOpenArgsList()) {
                        if ("url".equals(kv.getKey())) {
                            clickUrl = kv.getData();
                            break;
                        }
                    }
                } catch (Exception e) {
                }
            }

            builderId = informDesc.getBuilderId();
        }

        // check if sound/vibrate enable
        switch (builderId) {
            case 0:
                enableSound = true;
                enableVibrate = true;
                break;
            case 1:
                enableSound = true;
                enableVibrate = false;
                break;
            case 2:
                enableVibrate = true;
                enableSound = false;
                break;
            default:
                break;
        }

        // check if set the large logo.
        Bitmap largeLogo = null;
        if (informDesc != null && informDesc.getBuilderArgsCount() > 0) {
            for (KV kv : informDesc.getBuilderArgsList()) {
                if ("largeLogo".equals(kv.getKey())) {
                    String url = kv.getData();
                    if (!TextUtils.isEmpty(url)) {
                        LogUtil.printMainProcess(TAG, "largelogo = " + url);
                        largeLogo = getLargeLogo(context, url);
                    }
                    break;
                }
            }
        }

        /**
         * Build notification
         */
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        
        int iconId =  context.getApplicationInfo().icon;
        if(iconId == 0)
        {
        	iconId = R.drawable.sym_action_chat;
        }
        
        Builder builder =
                new NotificationCompat.Builder(context).setSmallIcon(iconId).setAutoCancel(
                        true);

        // set content and title
        String appName =  context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();  
        if(appName == null) 
        {
        	appName = "";
        }
        //appName += getRootActivity(context);
        builder.setTicker(content).setContentText(content).setContentTitle(appName);
        // set click action.
        /*
        if (!TextUtils.isEmpty(clickUrl)) {

            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(clickUrl);
            intent.setData(content_url);

            PendingIntent contentIntent =
                    PendingIntent
                            .getActivity(context, messageNotificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
        }*/
        
        
        {
        	Intent intent = new Intent();
        	String rootActivity = getRootActivity(context);
        	if(rootActivity.equals("")) {
        		return; 
        	}
            ComponentName comp = new ComponentName(context, rootActivity);
            intent.setComponent(comp);
        	//intent.setAction("com.baidu.im.sdk.boot");
        	/*
        	intent.putExtra(Constant.sdkBDChatType, chattype);
        	intent.putExtra(Constant.sdkBDTOUID, toID);
        	intent.putExtra(Constant.sdkBDFORMUID, fromID);
        	*/
            PendingIntent contentIntent =
                    PendingIntent
                            .getActivity(context, appid, 
                            		intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
        }

        // set large logo
        if (largeLogo != null) {
            builder.setLargeIcon(largeLogo);
        }

        int defaultFlag = 0;
        // enable/disable sound
        if (enableSound) {
            defaultFlag |= Notification.DEFAULT_SOUND;
        }
        // enable/disable vibrate
        if (enableVibrate) {
            defaultFlag |= Notification.DEFAULT_VIBRATE;
        }
        builder.setDefaults(defaultFlag);

        // build and notify.
        Notification notification = builder.build();
       
        notificationManager.cancel(appid);
        notificationManager.notify(appid, notification);
    }


    public static boolean  useSDKNotification(Context context)
    {
    	boolean use  = true;
    	PackageManager pm = context.getPackageManager();
    	try {
			ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);
			
			if((appInfo != null) && (appInfo.metaData != null) ) {
				use=appInfo.metaData.getBoolean("usesdknotification", true);
			}
		} catch (NameNotFoundException e) {
			LogUtil.i(TAG, "meta data is null");
		}
    	return use;
    }
//    public static void showNormal(Context context, InformMessage informMessage, int chattype, String toID, String fromID) {
//
//    	LogUtil.i(TAG, "show normal push");
//        if (informMessage == null || context  == null) {
//        	LogUtil.i(TAG, "show empty push");
//            return;
//        }
//        List<InformDesc> informDescs = informMessage.getImformDescsList();
//        InformDesc informDesc = null;
//        if (informDescs != null && informDescs.size() > 0) {
//            informDesc = informDescs.get(0);
//        }
//
//        // push content
//        String title = informMessage.getTitle();
//        String content = informMessage.getDescription();
//
//       //LogUtil.i(TAG, title + content);
//        // enable sound by default
//        boolean enableSound = true;
//
//        // enable vibrate by default
//        boolean enableVibrate = true;
//
//        int builderId = 0;
//
//        // how to show and perform when click
//        String clickUrl = null;
//        if (informDesc != null) {
//            // set click url if there is.
//            if (informDesc.getOpenType() == 1) {
//                try {
//                    for (KV kv : informDesc.getOpenArgsList()) {
//                        if ("url".equals(kv.getKey())) {
//                            clickUrl = kv.getData();
//                            break;
//                        }
//                    }
//                } catch (Exception e) {
//                }
//            }
//
//            builderId = informDesc.getBuilderId();
//        }
//
//        // check if sound/vibrate enable
//        switch (builderId) {
//            case 0:
//                enableSound = true;
//                enableVibrate = true;
//                break;
//            case 1:
//                enableSound = true;
//                enableVibrate = false;
//                break;
//            case 2:
//                enableVibrate = true;
//                enableSound = false;
//                break;
//            default:
//                break;
//        }
//
//        // check if set the large logo.
//        Bitmap largeLogo = null;
//        if (informDesc != null && informDesc.getBuilderArgsCount() > 0) {
//            for (KV kv : informDesc.getBuilderArgsList()) {
//                if ("largeLogo".equals(kv.getKey())) {
//                    String url = kv.getData();
//                    if (!TextUtils.isEmpty(url)) {
//                        LogUtil.printMainProcess(TAG, "largelogo = " + url);
//                        largeLogo = getLargeLogo(context, url);
//                    }
//                    break;
//                }
//            }
//        }
//
//        /**
//         * Build notification
//         */
//        NotificationManager notificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        
//        int iconId =  context.getApplicationInfo().icon;
//        if(iconId == 0)
//        {
//        	iconId = R.drawable.sym_action_chat;
//        }
//        
//        Builder builder =
//                new NotificationCompat.Builder(context).setSmallIcon(iconId).setAutoCancel(
//                        true);
//
//        // set content and title
//        String appName =  context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();  
//        if(appName == null) 
//        {
//        	appName = "";
//        }
//        appName +="@";
//        appName +=toID;
//        appName +="@";
//        appName +=fromID;
//        appName +="@";
//        appName += chattype;
//        //appName += getRootActivity(context);
//        builder.setTicker(content).setContentText(content).setContentTitle(appName);
//
//        // set click action.
//        /*
//        if (!TextUtils.isEmpty(clickUrl)) {
//
//            Intent intent = new Intent();
//            intent.setAction("android.intent.action.VIEW");
//            Uri content_url = Uri.parse(clickUrl);
//            intent.setData(content_url);
//
//            PendingIntent contentIntent =
//                    PendingIntent
//                            .getActivity(context, messageNotificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            builder.setContentIntent(contentIntent);
//        }*/
//        
//        int notificationID = getNotificationID(chattype,toID,fromID);
//        
//        {
//        	Intent intent = new Intent();
//        	intent.setAction("com.baidu.im.sdk.boot");
//        	/*
//        	intent.putExtra(Constant.sdkBDChatType, chattype);
//        	intent.putExtra(Constant.sdkBDTOUID, toID);
//        	intent.putExtra(Constant.sdkBDFORMUID, fromID);
//        	*/
//            PendingIntent contentIntent =
//                    PendingIntent
//                            .getActivity(context, notificationID, 
//                            		intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            builder.setContentIntent(contentIntent);
//        }
//
//        // set large logo
//        if (largeLogo != null) {
//            builder.setLargeIcon(largeLogo);
//        }
//
//        int defaultFlag = 0;
//        // enable/disable sound
//        if (enableSound) {
//            defaultFlag |= Notification.DEFAULT_SOUND;
//        }
//        // enable/disable vibrate
//        if (enableVibrate) {
//            defaultFlag |= Notification.DEFAULT_VIBRATE;
//        }
//        builder.setDefaults(defaultFlag);
//
//        // build and notify.
//        Notification notification = builder.build();
//       
//        notificationManager.cancel(notificationID);
//        notificationManager.notify(notificationID, notification);
//    }

    private static Bitmap getLargeLogo(Context context, String largeLogoUrl) {
    	
    	if(context == null)
    		return null;

        Bitmap largeLogoBitmap = null;

        String md5 = MD5Util.getMD5(largeLogoUrl);
        // Step 1.
        // Load the image from local if it had been downloaded.
        largeLogoBitmap = readLogo(md5);
        if (largeLogoBitmap != null) {
            LogUtil.printMainProcess(TAG, "get largelogo in local cache. " + largeLogoUrl);
            return largeLogoBitmap;
        }

        // Step 2.
        // download from Internet.
        CountDownLatch countdown = new CountDownLatch(1);
        downloadLogo(largeLogoUrl, countdown);
        try {
            countdown.await(4, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
        if (countdown.getCount() == 0) {
            LogUtil.printMainProcess(TAG, "download largelogo successfully. " + largeLogoUrl);
            // download successfully.
            largeLogoBitmap = readLogo(md5);
            if (largeLogoBitmap != null) {
                return largeLogoBitmap;
            }
        }

        // Step 3.
        // If still get nothing, use the app logo by default.
        LogUtil.printMainProcess(TAG, "can not download largelogo, use app logo." + largeLogoUrl);
        return BitmapFactory.decodeResource(context.getResources(), context.getApplicationInfo().logo);
    }

    private static void downloadLogo(String imageUrl, CountDownLatch countDown) {
        if (downloadThreadPool == null || downloadThreadPool.isShutdown() || downloadThreadPool.isTerminated()) {
            downloadThreadPool = Executors.newFixedThreadPool(2);
        }
        downloadThreadPool.execute(new DownloadImageRunnable(imageUrl, countDown));
    }

    /**
     * Get image from newwork
     * 
     * @param path The path of image
     * @return InputStream
     * @throws Exception
     */
    private static InputStream downloadImageStream(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return conn.getInputStream();
        }
        return null;
    }

    /**
     * Save logo.
     */
    private static void saveLogo(Bitmap bm, String fileName) {
        if (bm == null) {
            LogUtil.printMainProcess(TAG, "save logo error, downloaded bitmap is null.");
            return;
        }
        File dirFile = new File(Constant.sdkExternalLogoDir);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File imageFile = new File(dirFile, MD5Util.getMD5(fileName));
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imageFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            LogUtil.e(TAG, e);
        }
    }

    /**
     * Read logo
     */
    private static Bitmap readLogo(String fileName) {
    	
    	if(StringUtil.isStringInValid(fileName))
    		return null;
    	
        File dirFile = new File(Constant.sdkExternalLogoDir);
        File imageFile = new File(dirFile, fileName);
        if (imageFile.isFile()) {
            try {
                return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            } catch (Exception e) {
                LogUtil.e(TAG, e);
            }
        }
        return null;
    }

    private static class DownloadImageRunnable implements Runnable {
        private String imageUrl;
        private CountDownLatch countDown;

        public DownloadImageRunnable(String imageUrl, CountDownLatch countDown) {
            this.imageUrl = imageUrl;
            this.countDown = countDown;
        }

        @Override
        public void run() {

            if (!TextUtils.isEmpty(imageUrl)) {
                InputStream is;
                try {
                    is = downloadImageStream(imageUrl);
                    if (is != null) {
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        saveLogo(bitmap, imageUrl);
                        try {
                            is.close();
                            bitmap.recycle();
                        } catch (Exception e) {
                        }
                    }
                } catch (IOException e1) {
                    LogUtil.e(TAG, "download logo failed. " + e1.getMessage());
                }
            }

            countDown.countDown();
        }
    }

    public static boolean isNotificationEnable() {

        try {

            boolean flag = GlobalInstance.Instance().preferenceInstace().getBoolean(PreferenceKey.notificationDisableFlag);

            // returns false if user had set the disable flag
            if (flag) {
                return false;
            }
            String jsonStr = GlobalInstance.Instance().preferenceInstace().getString(PreferenceKey.notificationDisableDuration);
            
            if(jsonStr == null || jsonStr.length() <2) {
            	return true;
            }

            LogUtil.i(TAG, jsonStr);
            JSONObject jsonObject = new JSONObject(jsonStr);
            long startTime = jsonObject.getInt("startTime");
            int duration = jsonObject.getInt("duration");

            Calendar curDate = Calendar.getInstance();
            long beginTimeOfToday =
                    new GregorianCalendar(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH),
                            curDate.get(Calendar.DATE), 0, 0, 0).getTimeInMillis();
            long currentTime = System.currentTimeMillis() / 60000 * 60000;
            LogUtil.printMainProcess(TAG, "isNotificationEnable: startTime=" + startTime + "  beginTimeOfToday="
                    + beginTimeOfToday + "  duration=" + duration + "  currentTime" + currentTime);

            // returns false if it's silence time now.
            startTime += beginTimeOfToday;
            LogUtil.printMainProcess(TAG, "isNotificationEnable: startTime=" + startTime + "  currentTime="
                    + currentTime + "  startTime + duration=" + (startTime + duration));
            if ((currentTime >= startTime && currentTime <= startTime + duration)
                    || (currentTime >= startTime - 24 * 3600 * 1000 && currentTime <= startTime + duration - 24 * 3600
                            * 1000)) {
                return false;
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e);
        }
        return true;
    }
}
