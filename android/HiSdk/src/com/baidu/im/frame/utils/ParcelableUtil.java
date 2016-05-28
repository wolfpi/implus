package com.baidu.im.frame.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableUtil {

    public static byte[] marshall(Parcelable parceable) {
    	if(parceable == null)
    		return null;
        Parcel parcel = Parcel.obtain();
        parceable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    public static <T> T unmarshall(byte[] bytes, Parcelable.Creator<T> creator) {
    	if(bytes == null  || creator == null)
    		return null;
        Parcel parcel = Parcel.obtain();
        try {
            parcel.unmarshall(bytes, 0, bytes.length);
            parcel.setDataPosition(0);
            return creator.createFromParcel(parcel);
        } finally {
            parcel.recycle();
        }
    }

}
