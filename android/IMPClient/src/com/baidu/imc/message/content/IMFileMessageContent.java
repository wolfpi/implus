package com.baidu.imc.message.content;

import com.baidu.imc.listener.ProgressListener;

/**
*
* <b>文件内容[接收]</b>
* 
* @since 1.0
* @author WuBin
*
*/
public interface IMFileMessageContent extends IMMessageContent {
	
	/**
	 * <b>获得文件名</b>
	 * 
	 * <b>设置的文件名</b>
	 * 
	 * @return
	 * 
	 * @since 1.0
	 * 
	 */
	public String getFileName();
	
	/**
	 * <b>文件大小</b>
	 * 
	 * @since 1.0
	 * 
	 * @return 文件大小
	 */
	public long getFileSize();
	
	/**
	 *
	 * <b>获取消息附件的本地路径</b>
	 * <p>
	 * 当本地不存在时返回null
	 * </p>
	 *
	 * @since 1.0
	 * 
	 * @return  消息的本地路径
	 */
	public String getLocalResource();
	
	/**
	 *
	 * <b>获取消息资源</b>
	 * <p>
	 * 当指定资源在本地不存在时，将从服务器端下载指定资源，如果某次下载时服务器端返回代码为404时，将保留该代码，下次请求时
	 * 直接返回 404 错误
	 * </p>
	 * <b>错误码</b>
	 * <ul>
	 *   <li>404 : File Not Found  ， 下次获取该资源时将直接返回该错误码</li>
	 *   <li>408 : Request Timeout ， 下次获取该资源时将会继续从服务器端获取</li>
	 * </ul>
	 *
	 * @since 1.0
	 *
	 * @param progressListener   进度监听器，成功时返回文件的本地路径
	 */
	public void loadResource(ProgressListener<String> progressListener);
	
	/**
	 * <b>文件是否已经上传到服务器</b>
	 * <p>接收到的文件/发送成功：true<br>发送中的: false</p>
	 * 
	 *   
	 * @return
	 */
	public boolean isFileUploaded();
	
	/**
	 * <b>设置文件上传监听器</b>
	 * <p>特别说明：如果设置时文件已经上传成功，则立刻调用ProgressListener的onSuccess方法</p>
	 * 
	 * @param progressListener  成功返回上传成功的文件URL
	 */
	public void setFileUploadListener(ProgressListener<String> progressListener);
}
