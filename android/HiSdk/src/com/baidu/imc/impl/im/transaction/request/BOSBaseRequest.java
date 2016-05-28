package com.baidu.imc.impl.im.transaction.request;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.baidu.im.frame.utils.LogUtil;
import com.baidu.imc.impl.im.protocol.file.BossHttpRequest;

public abstract class BOSBaseRequest implements Request<BossHttpRequest> {
    @Override
    public abstract String getRequestName();

    @Override
    public abstract BossHttpRequest createRequest();

    /**
     * Formats the specified date as an RFC 822 string.
     * 
     * @param date The date to format.
     * 
     * @return The RFC 822 string representing the specified date.
     */
    public static String formatRfc822Date(Date date) {
        SimpleDateFormat rfc822DateFormat1 = new SimpleDateFormat(format, Locale.US);
        return rfc822DateFormat1.format(date);
    }

    private final static String format = "EEE, dd MMM yyyy HH:mm:ss 'GMT'";
    public final static String format2 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    
    // /** RFC 822 format */
    // protected final static SimpleDateFormat rfc822DateFormat = new
    // SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'",
    // Locale.US);
    //
    // protected final static SimpleDateFormat rfc822DateFormat2 = new SimpleDateFormat("yyyy-MMM-dd'T'HH:mm :ss'Z'",
    // Locale.US);

    public static Date getUTCTime() {
        // 1、取得本地时间：
        Calendar cal = java.util.Calendar.getInstance(Locale.getDefault());
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));

        return cal.getTime();
    }

    public static String getUTCTimeString(String sign) {
        SimpleDateFormat rfc822DateFormat2 = new SimpleDateFormat(format2, Locale.US);
        String utcDateString  = null;
        try {
            String[] results = sign.split("/");
            String tmp = results[2];
            Date date = rfc822DateFormat2.parse(tmp);
            Calendar cal = java.util.Calendar.getInstance(Locale.getDefault());
            cal.setTime(date);
            cal.add(java.util.Calendar.MILLISECOND, -(8 * 3600 * 1000));
            utcDateString = rfc822DateFormat2.format(cal.getTime());
        } catch (Exception e) {
            LogUtil.printImE("", e);
            utcDateString = rfc822DateFormat2.format(getUTCTime());
        }
        // System.out.println(utcDateString);
        return utcDateString;
    }
}
