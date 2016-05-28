package com.baidu.imc.client;

import java.io.InputStream;

import com.baidu.imc.callback.ResultCallback;


/**
 *
 * <b>本地资源管理器</b>
 * <p>
 * 用于本地文件资源的读取和保存，SDK提供默认本地资源管理器，开发者可使用自己的本地资源管理器替代系统默认的
 * </p>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface LocalResourceManager {
	
	/**
	 *
	 * <b>保存文件数据到文件</b>
	 * <p>
	 * 当从远端下载文件到本地后，可保存到本地临时文件夹
	 * </p>
	 *
	 * @since 1.0
	 *
	 * @param data
	 * @return 保存的本地文件路径filePath
	 */
	public String saveFile(byte[] data);
	
	
	/**
	 * 
	 * <b>使用文件流保存数据</b>
	 * 
	 * 
	 * @param is  文件读取流
	 * @param result   保存完毕后返回文件路径
	 * @since 1.0
	 */
	public void saveFile(InputStream is, ResultCallback<String> result);
	
	
	/**
	 *
	 * <b>读取文件</b>
	 * <p>
	 * 使用给定filePath获取文件读取流
	 * </p>
	 *
	 * @since 1.0
	 *
	 * @param filePath 文件本地路径
	 * @return 文件读取流
	 */
	public InputStream getFileInputStream(String filePath);
	
	/**
	 *
	 * <b>读取文件</b>
	 * <p>
	 * 使用给定filePath获取文件内容
	 * </p>
	 *
	 * @since 1.0
	 *
	 * @param filePath 文件本地路径
	 * @return 文件内容
	 */
	public byte[] loadFile(String filePath);
	
	/**
	 *
	 * <b>清除所有本地临时文件</b>
	 * <p>该方法SDK不会使用，开发者可酌情实现</p>
	 *
	 * @since 1.0
	 *
	 */
	public void emptyFiles();
	
	/**
	 * <b>获得文件真实本地路径</b>
	 * 
	 * @param filePath 文件本地资源管理器路径
	 * @since 1.0
	 * @return
	 */
	public String getRealFilePath(String filePath);
	
	/**
	 * <b>获取临时文件使用容量</b>
	 * 
	 * @since 1.1
	 * @return 总容量，单位byte
	 */
	public long getTotalSize();
	
}
