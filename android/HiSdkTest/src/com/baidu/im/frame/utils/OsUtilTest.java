package com.baidu.im.frame.utils;

import junit.framework.Assert;
import android.test.AndroidTestCase;

public class OsUtilTest extends AndroidTestCase {

    public void testOsUtil() {
        Assert.assertNotNull(OsUtil.getProcessNameBy(getContext()));
    }
}
