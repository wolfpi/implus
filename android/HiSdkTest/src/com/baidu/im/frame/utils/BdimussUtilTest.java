package com.baidu.im.frame.utils;

import java.security.DigestException;
import java.security.NoSuchAlgorithmException;

import android.test.AndroidTestCase;

import junit.framework.Assert;

public class BdimussUtilTest extends AndroidTestCase {

    public void testEncodeBdimuss() {
        try {
            Assert.assertNotNull(BdimussUtil.EncodeBdimuss("password", new String("seed").getBytes(), 100));
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DigestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void testGetPassportParams() {
        Assert.assertNotNull(BdimussUtil.GetPassportParams("aa", "bb"));
    }
}
