package com.baidu.im.frame.utils;

import junit.framework.Assert;
import android.test.AndroidTestCase;

public class SignatureUtilTest extends AndroidTestCase {

    public void testGetSecureKey() {
        String secureKey = SignatureUtil.getSecureKey(this.getContext());
        LogUtil.printMainProcess("secureKey = " + secureKey);
        Assert.assertNotNull(secureKey);
    }

    public void testGetSha1() {
        Assert.assertNotNull(SignatureUtil.getSha1(this.getContext(), this.getContext().getPackageName()));
    }

    public void testGetSha1ByCer() {
        Assert.assertNotNull(SignatureUtil.getSha1ByCer(null));
    }

    public void testConvertToSha1() {
        Assert.assertNotNull(SignatureUtil.convertToSha1(null));
    }
}
