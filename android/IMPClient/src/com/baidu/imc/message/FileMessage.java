package com.baidu.imc.message;

import com.baidu.imc.message.content.FileMessageContent;

/**
*
* <b>文件消息</b>
* <p>可附属多个文件</p>
*
* @since 1.0
* @author WuBin
*
*/
public interface FileMessage extends Message {
    
    /**
     * <b>设置消息文件[必须]</b>
     * 
     * @since 1.0
     * 
     * @param file 文件内容
     */
    public void setFile(FileMessageContent file);

}
