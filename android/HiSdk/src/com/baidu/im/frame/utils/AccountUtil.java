package com.baidu.im.frame.utils;

import java.util.HashMap;

import com.baidu.im.constant.Constant;

public class AccountUtil {

    public static HashMap<String, Object> responses;

    /**
     * Get bduss.
     * 
     * @param username
     * @param password
     * @return
     */
    public static String getBduss(String username, String password) {
    	if(StringUtil.isStringInValid(username) || StringUtil.isStringInValid(password))
    		return null;
    	
        String passportUrl = Constant.buildMode.getPassport() + "/v2/sapi/login";
        HashMap<String, String> params = BdimussUtil.GetPassportParams(username, password);
        String[] result =
                HttpClientUtil.GetPostResponse(passportUrl, params, null, Constant.buildMode.getPassport(), 20000);

        responses = BdimussUtil.parsePassportResponse(result[0]);
        if (responses.get("errno") != null) {
            if ((Integer) responses.get("errno") == 0) {
                return (String) responses.get("bduss");
            }
        }
        return null;
    }

    public static String getUid() {
        if (responses != null && responses.containsKey("uid")) {
            return responses.get("uid").toString();
        } else {
            return null;
        }
    }
}
