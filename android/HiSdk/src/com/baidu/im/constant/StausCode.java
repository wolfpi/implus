package com.baidu.im.constant;


public enum StausCode {
    SUCCESS(200), 
    ADD_GROUP(201),
    SUCCESS_NO_MORE(202), 
    ADD_FRIEND(204),
    HAS_MORE(210),
    LOCAL_SYNC(220),/** 时间戳已经是最新 **/
    GRPQCRT_NORIGHT(251), 
    GRPQCRT_OVGRPNUM(252),
    ADD_PART_SUCCESS(281),
    ADD_ALL_FAIL(282),
    PROTOCOL_ERROR(400), 
    NO_USER(401), 
    PASSWORD_ERROR(402), 
    LOW_VERSION(403), 
    CANNT_LOGIN(404),
    NEED_VERIFY_CODE(405),
    VERSION_NOT_RELEASED(451),
    
    BAD_CONTENT(406),//解析content失败
    UID_NOTEXIST(407),//uid不存在
    USERNAME_NOTEXIST(408),//用户名不存在
    
    VCODE_ERROR(410), 
    VCODE_TIME_OUT(411), 
    
    CLIENT_NOT_SUPPORT(412),//对方客户端不支持
    CLIENT_CONFILTER(413),//违禁词过滤
    PCCODE_BLOCKED(414),//机器码封禁
    ENCODE_UNSUPPORT(415),//编码不支持
    UID_FORGE(417),//imp包中的uid与登录uid不一样
    
    KICKOUT_CONFLICT(418),
    FRIEND_ALREADY_BE_MY_FRIEND(444),
    
    PUBLIC_NO_RIGHT(449),//imp包中的uid和登陆时的uid不一样
    SEND_TSMG_NO_RIGHT(451),//好友关系错误
    
    GROUP_OPOVNUM(451),//操作次数太多
    GROUP_BLOCKED(452), //群被封禁
    GROUP_OVNUM(453), //个人所有在的群数目太多
    GROUP_BADOBJ(454),//操作目标不符合要求
    GROUP_NOTEXIST(455), //群不存在
    GROUP_OBJISBLACK(456),//操作目标是黑名单
    GROUP_REPEATOP(457),//目标已经在列表中，重复操作
    GROUP_OBJISMEMBER(458),//操作目标是群成员
    GROUP_NOTMEMBER(459),//自己不是群成员
    GROUP_NOT_FOR_TOPIC(460),//讨论组不能使用的协议

    GROUP_ERROR(481), 
    NOT_ACTIVATING(483), 
    NO_USER_NAME_OR_CANNT_SEND_CODE_FILE(484), 
    USERNAME_ALREADY_USED_OR_CANNT_SEND_VOICE(485), 
    
    SERVER_ERROR(500),//服务器端失败
    SERVER_TIMEOUT(506),//
    SERVER_FAIL_UNSUPPORT(507),//
    SERVER_FAIL_TS(511),//
    SERVER_FAIL_CS(512),//
    SERVER_FAIL_DB(513),//
    SERVER_FAIL_TJ(514),//
    SERVER_FAIL_PSP(515),//
    SERVER_FAIL_RT(516),//
    SERVER_FAIL_MSGSTORE(517),//
    SERVER_FAIL_RELAY(518),//
    SERVER_FAIL_BAIDUMSG(519),//
    SERVER_FAIL_CSBGMGR(520),//
    SERVER_FAIL_CONFILTER(521),//
    SERVER_FAIL_CSLRT(522),//
    SERVER_FAIL_GIDGEN(523),//
    SERVER_FAIL_EMAILBIND(524),//
    
    SRV_DB_QGINFO(551),//发出获取群信息包失败
    SRV_DB_QGINFO_RESP(552),//解析获取群信息包失败
    SRV_DB_QUINFO(553),//发出获取用户信息包失败
    SRV_DB_QUINFO_RESP(554),//解析获取用户信息包失败
    SRV_DB_QGUINFO(555),//发出获取群用户信息包失败
    SRV_DB_QGUINFO_RESP(556),//解析获取群用户信息包失败
    SRV_DB_OPG(557),//发出操作群的包失败
    SRV_DB_OPG_RESP(558),//解析操作群的包失败
    SRV_DB_OPU(559),//发出操作用户的包失败
    SRV_DB_OPU_RESP(560),//解析操作用户的包失败
    SRV_DB_OPGU(561),//发出操作群用户信息的包失败
    SRV_DB_OPGU_RESP(562),//解析操作群用户信息的包失败
    SRV_DB_OPREQ(563),//发出操作申请的包失败
    SRV_DB_OPREQ_RESP(564),//解析操作申请的包失败


    IM_UNKNOWN(10000), 
    URL_ERROR(10001), 
    DOWNLOAD_ERROR(10002), 
    NET_ERROR_HTTP(10003), 
    ERROR_SDCARD_FULL(10004), 
    FILE_NOT_EXISTS(10005), 
    FILE_READ_ERROR(10006),
    NETWORK_EXCEPTION(100001);

    private int value;

    StausCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static StausCode valueOf(int value) {
        switch (value) {
        case 200:
            return SUCCESS;
        case 201:
        	return ADD_GROUP;
        case 202:
        	return SUCCESS_NO_MORE;
        case 204:
            return ADD_FRIEND;
        case 210:
            return HAS_MORE;
        case 220:
            /** 时间戳是最新 **/
            return LOCAL_SYNC;
        case 251:
        	return GRPQCRT_NORIGHT;
        case 252:
        	return GRPQCRT_OVGRPNUM;
        case 281:
        	return ADD_PART_SUCCESS;
        case 282:
            return ADD_ALL_FAIL;
        case 400:
            return PROTOCOL_ERROR;
        case 401:
        case 408:
            return NO_USER;
        case 402:
            return PASSWORD_ERROR;
        case 403:
            return LOW_VERSION;
        case 451:
            return VERSION_NOT_RELEASED;
        case 404:
            return CANNT_LOGIN;
        case 405:
            return NEED_VERIFY_CODE;
        case 410:
            return VCODE_ERROR;
        case 418:
        	return KICKOUT_CONFLICT;
        case 411:
            return VCODE_TIME_OUT;
        case 452:
        	return GROUP_BLOCKED;
        case 444:
        	return FRIEND_ALREADY_BE_MY_FRIEND;
        case 453:
        	return GROUP_OVNUM;
        case 455:
            return GROUP_NOTEXIST;
        case 457:
        	return GROUP_REPEATOP;
        case 481:
            return GROUP_ERROR;
        case 483:
            return NOT_ACTIVATING;
        case 484:
            return NO_USER_NAME_OR_CANNT_SEND_CODE_FILE;
        case 485:
            return USERNAME_ALREADY_USED_OR_CANNT_SEND_VOICE;
        case 406:
        case 413:
        case 421:
        case 420:

        case 482:
        case 301:
        case 500:
        case 506:
        case 507:
        case 511:
        case 512:
        case 513:
        case 514:
        case 515:
        case 516:
        case 517:
        case 518:
        case 519:
        case 520:
        case 521:
        case 522:
        case 523:
        case 524:
        case 551:
        case 557:
        case 558:
        case 559:
        case 560:
            return SERVER_ERROR;
        case 100001:
        	return NETWORK_EXCEPTION;
        default:
            /** 未录入错误返回 **/
            return IM_UNKNOWN;
            // return SERVER_ERROR;
        }
    }
}
