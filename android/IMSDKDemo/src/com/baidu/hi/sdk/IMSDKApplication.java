package com.baidu.hi.sdk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.baidu.imc.IMPlusSDK;

public class IMSDKApplication extends Application {

    private static final String TAG = "IMSDKApplication";
    private static volatile IMSDKApplication instance = null;
    private Handler handler = null;

    public IMSDKApplication() {
        super();
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMPlusSDK SDK调用第一步 初始化
             */
            IMPlusSDK.init2("2if2neqvvnc4owk", this,"hisdk");
        	//IMPlusSDK.init("96fu8uxr1kco88o", this.getApplicationContext());

            /**
             * 配置DEMO环境
             */
            Config.init(this);

            /**
             * DEMO 展示使用
             */
            initFiles();
        }
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    private static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    private static void initFiles() {
        createFolder(Constant.IMAGE_DIR);
        createFolder(Constant.THUMBNAIL_DIR);
        createFile(Constant.IMAGE_NOMEDIA);
    }

    /**
     * sd卡中创建自定义文件夹
     * 
     * @param folderUrl :文件夹路径
     */
    private static void createFolder(String foldPath) {
        File file = new File(foldPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 创建目录和文件， 如果目录或文件不存在，则创建出来
     * 
     * @param filePath 文件路径
     * @return 创建后的文件
     * @throws IOException
     */
    private static File createFile(String filePath) {
        File file = new File(filePath);
        File parentFile = new File(file.getParent());
        try {
            if (!parentFile.exists()) {
                if (parentFile.mkdirs()) {
                    file.createNewFile();
                }
            } else {
                if (!file.exists()) {
                    file.createNewFile();
                }
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "", e);
        } catch (IOException e) {
            Log.e(TAG, "", e);
        }
        return file;
    }

    public Handler getHandler() {
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }

    public static IMSDKApplication getInstance() {
        return instance;
    }
}
