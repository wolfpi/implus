package com.baidu.hi.sdk;

import android.os.Environment;

public class Constant {

    public enum DEMO_MODE {
        PRODUCT("PRODUCT"),

        DEVELOP("DEVELOP");

        String name;

        DEMO_MODE(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static DEMO_MODE parse(String name) {
            DEMO_MODE[] modes = DEMO_MODE.values();
            for (DEMO_MODE mode : modes) {
                if (mode.getName().equalsIgnoreCase(name)) {
                    return mode;
                }
            }
            return null;
        }
    }

    public static final String PRODUCT_BASE_URL = "http://rest.implus.baidu.com/demo";
    public static final String DEVELOP_BASE_URL = "http://rest.implus.baidu.com/demo/dev";

    public static final String LOGIN_URL = "/login?username=%1$s&password=%2$s";
    public static final String REGISTER_URL = "/regist?username=%1$s&password=%2$s&confirmpassword=%3$s";
    public static final String GROUP_URL = "/group";

    public static final String PRODUCT_SERVER_BASE_URL = "http://rest.implus.baidu.com";
    public static final String DEVELOP_SERVER_BASE_URL = "http://rest.implus.baidu.com/DEV";

    public static final String CREATE = "create";
    public static final String DISMISS = "dismiss";
    public static final String JOIN = "join";
    public static final String QUIT = "quit";
    public static final String QUERY = "query";
    public static final String ADDMEMBEBER = "addmember";
    public static final String DELMEMBEBER = "delmember";
    public static final String QUERYMEMBER = "querymember";
    public static final String QUERYGROUP = "querygroup";

    public static final String ACTION = "action";
    public static final String GID = "gid";
    public static final String HOSTID = "hostid";
    public static final String NAME = "name";
    public static final String DESC = "desc";
    public static final String MEMBER = "member";
    public static final String UID = "uid";

    public static final String CODE = "code";
    public static final String INFO = "info";

    /* 用来标识请求gallery的activity */
    public static final int PHOTO_PICKED_WITH_DATA = 3021;
    public static final int PHOTO_PICKED_WITH_DATA2 = 3022;
    public static final int PHOTO_PICKED_WITH_DATA3 = 3023;

    public static final String IMAGE_DIR = Environment.getExternalStorageDirectory() + "/baidu/imsdk/image";
    public static final String THUMBNAIL_DIR = Environment.getExternalStorageDirectory() + "/baidu/imsdk/thumbnail";

    public static final String IMAGE_NOMEDIA = Environment.getExternalStorageDirectory() + "/baidu/imsdk/.nomedia";

}
