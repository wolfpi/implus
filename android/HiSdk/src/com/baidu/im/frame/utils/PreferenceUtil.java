package com.baidu.im.frame.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author zhaowei10
 * 
 */
public class PreferenceUtil implements Preference{
    public static final String TAG = "PreferenceUtil";
    private static final String ChannelKey = "channelkey";
    private static final String DEVICE_TOKEN = "deviceToken";
    private static final String HeartBeatTime = "lastheartbeat";

    private  SharedPreferences globalPreferences;
    private  SharedPreferences seqPreferences;
    private  SharedPreferences apipreference = null;
    private  int mSeq = 0;
    private  Context ct = null;
        
    private Context getTargetContext(Context context) {  
       
    	/*try {
			return context.createPackageContext("com.baidu.hi.sdk", Context.CONTEXT_IGNORE_SECURITY);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}  
        LogUtil.i("preference", "get target is null");
        */
        return context;
    } 
    public void initialize(Context context, String apiKey) {

    	if(context == null)
    		return ;
    	ct = context.getApplicationContext() ;
        if (apiKey != null && globalPreferences == null) {
            globalPreferences = context.getSharedPreferences(apiKey, Context.MODE_MULTI_PROCESS);
        }
        if (seqPreferences == null) {
            seqPreferences = getTargetContext(context).getSharedPreferences("seq", Context.MODE_WORLD_READABLE+Context.MODE_MULTI_PROCESS+Context.MODE_WORLD_WRITEABLE);
        }
        if (apipreference == null) {
        	apipreference = context.getSharedPreferences("apikey", Context.MODE_MULTI_PROCESS);;
        }
    }

    public synchronized  int getSeq() {
    	if(seqPreferences != null) {
        int seq = seqPreferences.getInt("seq", 100);
        if (mSeq < seq) {
            mSeq = seq;
        }
        mSeq++;
        if (mSeq > Integer.MAX_VALUE / 2) {
            mSeq = 100;
        }
        Editor editor = seqPreferences.edit();
        editor.putInt("seq", mSeq);
        editor.commit();
        return mSeq;
    	}
    	return -1;
    }
    
    public synchronized  long getLastHeartbeatTime()
    {
    	if(seqPreferences != null) {
    	return seqPreferences.getLong(HeartBeatTime, 0);
    	}
    	return 0;
    }
    public synchronized void saveLastHeartbeatTime(long lastHeartbeatTime)
    {
    	if(seqPreferences != null) {
    	 Editor editor = seqPreferences.edit();
         editor.putLong(HeartBeatTime, lastHeartbeatTime);
         editor.commit();
    	}
    }

    public synchronized String getDeviceToken() {
        if (seqPreferences != null) {
            return seqPreferences.getString(DEVICE_TOKEN, "");
        }
        return "";
    }

    public synchronized void saveDeviceToken(String deviceToken) {
        if (seqPreferences != null) {
            Editor editor = seqPreferences.edit();
            editor.putString(DEVICE_TOKEN, deviceToken);
            editor.commit();
        }
    }
    
    public synchronized  String getChannelkey() 
    {
    	if(seqPreferences != null) {
        return seqPreferences.getString(ChannelKey, "");
    	}
    	return "";
    }
    
    public synchronized void  saveChanneKey(String channelKey)
    {
    	/*LogUtil.i("preference",ct.getApplicationContext().getPackageName());
    	LogUtil.i("preference",ct.getPackageName());
    	
    	Field field;
		try {
			field = ContextWrapper.class.getDeclaredField("mBase");
			field.setAccessible(true);
			// 获取mBase变量的值
			Object obj;
			try {
				obj = field.get(ct);
				field = obj.getClass().getDeclaredField("mPreferencesDir");
				field.setAccessible(true);
				//LogUtil.i("preference",field.get(obj).toString());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		*/
		
		
    	if(seqPreferences != null) {
    	 Editor editor = seqPreferences.edit();
         editor.putString(ChannelKey, channelKey);
 //        editor.apply();
         editor.commit();
    	}
    }
    
    public  boolean save(PreferenceKey key, String value) {
    	if(globalPreferences != null) {
        Editor editor = globalPreferences.edit();
        if(value == null)
        	value = "";
        editor.putString(key.name(), value);
        return editor.commit();
    	}
    	return false;
    }

    public  boolean save(PreferenceKey key, long value) {
    	if(globalPreferences != null) {
        Editor editor = globalPreferences.edit();
        editor.putLong(key.name(), value);
        return editor.commit();
    	}
    	return false;
    }

    public  boolean save(PreferenceKey key, int value) {
    	
    	if(key == PreferenceKey.appId)
    	{
    		 Editor editor = apipreference.edit();
            String name = appendDeviceToken(key);
            editor.putInt(name, value);
    	     return editor.commit();
    	}
    	else if(globalPreferences != null) {
        Editor editor = globalPreferences.edit();
        editor.putInt(key.name(), value);
        return editor.commit();
    	}
    	return false;
    }

    /**
     * NOTE 这里为了保持DeviceToken与 appId 成对出现, 所以存取 appId 时加入 当前运行时的 deviceToken 为第二个factor
     * @param key
     * @return
     */
    private String appendDeviceToken(PreferenceKey key) {
        return key.name() + "|" + DeviceInfoUtil.getDeviceToken(ct);
    }

    public  boolean save(PreferenceKey key, boolean value) {
    	if(globalPreferences != null) {
        Editor editor = globalPreferences.edit();
        editor.putBoolean(key.name(), value);
        return editor.commit();
    	}
    	return false;
    }

    public  String getString(PreferenceKey key) {
    	if(globalPreferences != null) {
        return globalPreferences.getString(key.name(), "");
    	}
    	return "";
    }

    public  long getLong(PreferenceKey key) {
    	if(globalPreferences != null) {
        return globalPreferences.getLong(key.name(), 0);
    	}
    	return 0;
    }

    public  boolean getBoolean(PreferenceKey key) {
        return getBoolean(key, false);
    }

    public  boolean getBoolean(PreferenceKey key, boolean defaultValue) {
    	if(globalPreferences != null) {
        return globalPreferences.getBoolean(key.name(), defaultValue);
    	}
    	return false;
    }

    public  int getInt(PreferenceKey key) {
        return getInt(key, 0);
    }

    public  int getInt(PreferenceKey key, int defaultValue) {
    	
    	if(key == PreferenceKey.appId && apipreference != null)
    	{
            String name = appendDeviceToken(key);
            return apipreference.getInt(name,defaultValue);
    	}
    	if(globalPreferences != null ) {
    		return globalPreferences.getInt(key.name(), defaultValue);
    	}
    	return 0;
    }

    public void clear() {
    	if(globalPreferences != null)  {
        Editor editor = globalPreferences.edit();
        editor.clear();
        editor.commit();
    	}
    }

    public void remove(PreferenceKey key) {
    	if(globalPreferences != null)  {
        Editor editor = globalPreferences.edit();
        editor.remove(key.name());
        editor.commit();
    	}
    }
}
