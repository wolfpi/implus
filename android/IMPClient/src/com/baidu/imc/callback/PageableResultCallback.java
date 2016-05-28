package com.baidu.imc.callback;

/**
 *
 * <b>分页查询回调接口</b>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface PageableResultCallback<T> {
	
	/**
	 *
	 * <b>分页结果集回调</b>
	 * <p>
	 * result 和 error 必须有一个为null
	 * </p>
	 *
	 * @since 1.0
	 *
	 * @param result  分页结果集
	 * @param error   错误信息
	 */
	public void result(PageableResult<T> result, Throwable error);
}
