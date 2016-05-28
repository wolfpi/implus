package com.baidu.im.frame.utils;

import android.content.Context;
import android.test.AndroidTestCase;

public class AppStatusUtilTest extends AndroidTestCase {

    public void testIsBackground() {
        Context context = this.getContext();
        boolean result = AppStatusUtil.isBackground(context);
        // It should be in forground
        assertEquals(false, result);
        boolean result2 = AppStatusUtil.isBackground(null);
        // Default value is true.
        assertEquals(true, result2);
    }

    public void testIsVisible() {
        Context context = this.getContext();
        boolean result = AppStatusUtil.isVisible(context);
        // It should be in forground
        assertEquals(true, result);
        boolean result2 = AppStatusUtil.isVisible(null);
        // Default value is false.
        assertEquals(false, result2);
    }

    public void testIsTopActivity() {
        Context context = this.getContext();
        // may not have GET_TASKS permission
        AppStatusUtil.isTopActivity(context);
    }

    public void testIsScreenNotLocked() {
        Context context = this.getContext();
        AppStatusUtil.isScreenNotLocked(context);
    }
}
