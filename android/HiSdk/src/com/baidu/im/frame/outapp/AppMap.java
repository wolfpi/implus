package com.baidu.im.frame.outapp;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import android.content.Context;
import android.os.Messenger;

import com.baidu.im.frame.utils.PreferenceUtil;
import com.baidu.im.frame.utils.StringUtil;

public class AppMap {

    private Map<String, String> mAppkey2ID = new HashMap<String, String>();
    private Map<String, String> mID2AppKey = new HashMap<String, String>();
    private Map<String, String> mSeq2AppKey = new HashMap<String, String>();
    private Map<String, Messenger> mSeq2Msg = new HashMap<String, Messenger>();
    private Map<String, PreferenceUtil> mAppID2Pref = new HashMap<String, PreferenceUtil>();
    private HashSet<Messenger> unKnownSet = new HashSet<Messenger>();

    private Context mContext = null;

    public AppMap(Context context) {
        mContext = context;
        // throw new RuntimeException("Context can not be null in appMap");
    }

    public void addUnknowMsger(Messenger msger) {
        synchronized (AppMap.class) {
            unKnownSet.add(msger);
        }
    }

    public void addSeq2AppKey(String seq, String appkey, Messenger msger) {
        if (StringUtil.isStringInValid(seq) || StringUtil.isStringInValid(appkey) || msger == null) {
            return;
        }

        synchronized (AppMap.class) {
            if (unKnownSet.contains(msger)) {
                unKnownSet.remove(msger);
            }
            mSeq2AppKey.put(seq, appkey);
            mSeq2Msg.put(seq, msger);
        }
    }

    public Messenger getMessgenger(String seq) {
        if (StringUtil.isStringInValid(seq)) {
            return null;
        }

        synchronized (AppMap.class) {
            if (mSeq2Msg.containsKey(seq)) {
                return mSeq2Msg.get(seq);
            }
        }
        return null;
    }

    public void addAppId(String seq, String appId) {
        if (StringUtil.isStringInValid(seq) || StringUtil.isStringInValid(appId)) {
            return;
        }

        synchronized (AppMap.class) {
            if (mSeq2AppKey.containsKey(seq)) {
                String appKey = this.mSeq2AppKey.get(seq);
                mAppkey2ID.put(appKey, appId);
                mID2AppKey.put(appId, appKey);
                mSeq2AppKey.remove(seq);
                mSeq2Msg.remove(seq);
            }
        }
    }

    public void addAppId(String appKey, int appId) {
        if (StringUtil.isStringInValid(appKey)) {
            return;
        }

        synchronized (AppMap.class) {
            if (!mSeq2AppKey.containsKey(appKey)) {
                mAppkey2ID.put(appKey, String.valueOf(appId));
                mID2AppKey.put(String.valueOf(appId), appKey);
            }
        }
    }

    public void removeAppid(String appId) {
        if (StringUtil.isStringInValid(appId)) {
            return;
        }

        synchronized (AppMap.class) {
            if (this.mID2AppKey.containsKey(appId)) {
                String appkey = this.mID2AppKey.get(appId);
                this.mAppkey2ID.remove(appkey);
                this.mID2AppKey.remove(appId);
                this.mAppID2Pref.remove(appId);
            }
        }
    }

    public PreferenceUtil getPreferenceByID(String appId) {
        if (StringUtil.isStringInValid(appId)) {
            return null;
        }

        PreferenceUtil preference = null;

        synchronized (AppMap.class) {
            if (this.mID2AppKey.containsKey(appId)) {
                preference = new PreferenceUtil();
                String appKey = this.mID2AppKey.get(appId);
                preference.initialize(mContext, appKey);
                mAppID2Pref.put(appId, preference);
            }
        }
        return preference;
    }

    public PreferenceUtil getSeqPreference() {
        PreferenceUtil preference = new PreferenceUtil();
        preference.initialize(mContext, null);
        return preference;
    }

    /**
     * 未注册的App
     * 
     * @return
     */

    public Collection<Messenger> getAllMessengers() {
        synchronized (AppMap.class) {
            if (this.mSeq2Msg.size() > 0) {
                Map<String, Messenger> map = new HashMap<String, Messenger>();
                map.putAll(mSeq2Msg);
                return map.values();
            }
        }
        return null;
    }

    public Collection<Messenger> getUnknowMsger() {
        synchronized (AppMap.class) {
            Collection<Messenger> copy = new HashSet<Messenger>(unKnownSet);
            return copy;
        }
    }

    public void removeMsger(Messenger msger) {
        synchronized (AppMap.class) {
            if (unKnownSet.contains(msger)) {
                unKnownSet.remove(msger);
            }
        }
    }
}
