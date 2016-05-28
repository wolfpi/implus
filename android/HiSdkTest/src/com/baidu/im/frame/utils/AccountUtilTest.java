package com.baidu.im.frame.utils;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import android.text.TextUtils;

import com.baidu.im.testutil.MockUser;

public class AccountUtilTest extends AndroidTestCase {

    /**
     * Get bduss.
     */
    public void testGetBduss(String username, String password) {

        Assert.assertTrue(!TextUtils.isEmpty(AccountUtil.getBduss(MockUser.UserEnum.imrd_333.name(),
                MockUser.UserEnum.imrd_333.getPassword())));

        Assert.assertTrue(!TextUtils.isEmpty(AccountUtil.getUid()));
    }
}
