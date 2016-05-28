package com.baidu.imc.type;

/**
 *
 * <b>IM消息状态</b>
 * <ul>
 *   <li>RECEIVE_NOTIFICATION : 接收并提醒</li>
 *   <li>RECEIVE_MESSAGE_ONLY ： 接收不提醒</li>
 *   <li>BLOCK_MESSAGE : 不接收消息</li>
 *   <li></li>
 * </ul>
 * @since 1.0
 * @author ZhengXiao
 *
 */
public enum NotificationType {
    RECEIVE_NOTIFICATION,
    RECEIVE_MESSAGE_ONLY,
    BLOCK_MESSAGE
    }
