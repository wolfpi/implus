package com.baidu.imc.callback;


/**
*
* <b>通用回调接口</b>
*
* @since 1.0
* @author WuBin
*
*/
public interface ResultCallback<T> {
	
	/**
	 *
	 * <b>回调结果</b>
	 * <p>
	 * 当错误不为null时，表示调用出错
	 * </p>
	 *
	 * @since 1.0
	 *
	 * @param result  返回结果
	 * @param error   返回错误
	 */
	public void result(T result, Throwable error);
}
