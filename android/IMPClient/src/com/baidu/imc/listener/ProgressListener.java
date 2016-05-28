package com.baidu.imc.listener;


/**
 *
 * <b>进度监听器</b>
 * <p>
 * 监听进度进展，并后的结果
 * </p>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface ProgressListener<T> {
	/**
	 *
	 * <b>当前进度</b>
	 * <p>
	 * 当有进度变更时调用，实现方的调用间隔不应小于0.1s
	 * </p>
	 *
	 * @since 1.0
	 *
	 * @param progress 当前进度1=100%
	 */
	public void onProgress(float progress);
	
	/**
	 *
	 * <b>失败时调用</b>
	 *
	 * @since 1.0
	 *
	 * @param code 错误码
	 * @param message 错误信息
	 */
	public void onError(int code,String message);
	
	/**
	 *
	 * <b>成功</b>
	 * <p>
	 * 当成功时调用
	 * </p>
	 *
	 * @since 1.0
	 *
	 * @param result 成功的结果
	 */
	public void onSuccess(T result);
}
