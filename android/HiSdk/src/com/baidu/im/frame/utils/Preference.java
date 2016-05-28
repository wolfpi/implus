package com.baidu.im.frame.utils;

import android.content.Context;


public interface Preference {
    
    public void initialize(Context context, String apiKey) ;

    public  int getSeq() ;

    public  boolean save(PreferenceKey key, String value) ;

    public  boolean save(PreferenceKey key, long value) ;

    public  boolean save(PreferenceKey key, int value);

    public  boolean save(PreferenceKey key, boolean value) ;

    public  String getString(PreferenceKey key) ;
    
    public  long getLong(PreferenceKey key) ;
    
    public  boolean getBoolean(PreferenceKey key);

    public  boolean getBoolean(PreferenceKey key, boolean defaultValue);

    public  int getInt(PreferenceKey key) ;

    public  int getInt(PreferenceKey key, int defaultValue);

    public void clear() ;

    public void remove(PreferenceKey key) ;
}

