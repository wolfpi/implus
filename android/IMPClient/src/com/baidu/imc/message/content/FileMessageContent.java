package com.baidu.imc.message.content;

/**
*
* <b>文件消息内容</b>
*
* @since 1.0
* @author WuBin
*
*/
public interface FileMessageContent extends IMFileMessageContent{
	
	/**
	 * <b>设置文件名[可选]</b>
	 * 
	 * @param fileName 文件名
	 * 
	 * @since 1.0
	 */
	public void setFileName(String fileName);
	
	/**
	 * <b>设置文件路径[必须]</b>
	 * 
	 * <p>设置具体文件时，需要先使用LocalResourceManager将文件保存到本地临时资源库</p>
	 * 
	 * @param filePath 文件本地资源库路径
	 * 
	 * @since 1.0
	 */
	public void setFilePath(String filePath);
}
