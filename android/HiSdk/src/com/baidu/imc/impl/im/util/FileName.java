package com.baidu.imc.impl.im.util;


public class FileName {

    /**
     * Get a file name for FileMessageContent getLocalPath
     * 
     * Save file after Download/Upload File; FileMessageContent.getLocalResource
     * 
     * @author liubinzhe
     * 
     */
    public static String getFileName(String fid, String md5) {
        return Integer.toString((fid + md5).hashCode());
    }
}
