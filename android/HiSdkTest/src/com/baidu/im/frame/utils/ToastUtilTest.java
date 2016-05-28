/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.im.frame.utils;

import android.test.AndroidTestCase;

/**
 * 
 * @author zhaowei10
 * 
 */
public class ToastUtilTest extends AndroidTestCase {

    public void testToast() {
        ToastUtil.cancelCurrentToast();
        ToastUtil.showMessage(null, null);
        ToastUtil.showMessageLong(null, null);
        ToastUtil.toast(null);
    }
}
