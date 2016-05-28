package com.baidu.imc.client;

import com.baidu.imc.listener.ProgressListener;


/**
 *
 * <b>远程资源管理器</b>
 * <p>
 *  用于远程资源的下载，用户可提供自己的远程资源管理器负责保存用户发送的文件，
 *  或使用SDK默认的，需要在管理后台进行配置进行开启，否则所有文件传输消息均会失败
 * </p>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface RemoteResourceManager {
	
	/**
	 *
	 * <b>上传文件</b>
	 * <p>
	 * 上传成功后，progressListener的onSuccess方法应返回文件下载路径downloadURL，
	 * 开发者实现时，应将错误归结到以下两类错误码中，以便SDK完成附件上传和重试逻辑
	 * </p>
	 * <b>错误码</b>
	 * <ul>
	 *   <li>500 : Internal Server Error ， 当服务器无法完成本次上传时返回，该文件将标记为上传失败</li>
	 *   <li>408 : Request Timeout  ， 当因为网络问题无法完成上传时返回，该文件会在网络恢复后重新开始上传</li>
	 * </ul>
	 *
	 * @since 1.0
	 *
	 * @param filePath 本地资源管理器文件路径
	 * @param progressListener   进度监听器
	 */
	public void uploadFile(String filePath,ProgressListener<String> progressListener);
	
	/**
	 *
	 * <b>下载文件</b>
	 * <p>
	 * 下载成功后，progressListener的onSuccess方法应返回文件的本地资源管理器路径filePath，
	 * 开发者实现时，应将错误归结到以下两类错误码中，以避免对无效资源的多次重复下载申请
	 * </p>
	 * <b>错误码</b>
	 * <ul>
	 *   <li>404 : File Not Found  ， 下次获取该资源时将直接返回该错误码</li>
	 *   <li>408 : Request Timeout ， 下次获取该资源时将会继续从服务器端获取</li>
	 * </ul>
	 *
	 * @since 1.0
	 *
	 * @param downloadURL   远程资源管理器的下载地址
	 * @param progressListener    进度监听器，下载成功返回本地文件路径
	 */
	public void downloadFile(String downloadURL,ProgressListener<String> progressListener);
}
