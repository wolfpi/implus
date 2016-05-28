package com.baidu.imc.message;

import com.baidu.imc.message.content.FileMessageContent;


/**
 *
 * <b>文件消息</b>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface IMFileMessage extends IMMessage {

    /**
     * <b>获得文件内容</b>
     * 
     * @return
     */
    public FileMessageContent getFile();

}
