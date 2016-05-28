package com.baidu.im.frame.utils;

import java.util.Date;
import java.util.Locale;

public class BaseLogUtil {

    private final static String LOG_ENTRY_FORMAT = "[%tF %tT][%s][%s]%s"; // [2010-01-22 13:39:1][D][com.a.c]msg

    protected static String formatMessage(String level, String tag, String msg) {
    	if(level == null || tag == null || msg == null)
    		return null;
        Date now = new Date();
        return String.format(Locale.getDefault(), LOG_ENTRY_FORMAT, now, now, level, tag, msg);
    }

}
