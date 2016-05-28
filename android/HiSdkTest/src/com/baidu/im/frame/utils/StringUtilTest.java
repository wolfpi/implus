package com.baidu.im.frame.utils;

import junit.framework.Assert;
import android.test.AndroidTestCase;

public class StringUtilTest extends AndroidTestCase {

    public void test() {
        String result = StringUtil.StringToMD5("Hello world.");
        String result2 = StringUtil.StringToMD5("Hello world.");
        Assert.assertEquals(result, result2);
    }

}
