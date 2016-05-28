package com.baidu.imc.impl.im.client;

import com.baidu.imc.type.UserStatus;

public interface UserStatusListener {

    /**
     *
     * <b>用户登录状态发生变更</b>
     * <p>
     * 当用户登录状态发生变更时回调
     * </p>
     *
     * @since 1.0
     *
     * @param status
     */
    public void onUserStatusChanged(UserStatus status);

}
