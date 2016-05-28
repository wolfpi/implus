package com.baidu.imc.callback;

import java.util.List;


/**
 * 
 *
 * <b>分页结果集</b>
 *
 * @since 1.0
 * @author WuBin
 *
 */
public interface PageableResult<T> {
	
	/**
	 * 
	 * <b>总数</b>
	 * 
	 * @since 1.0
	 * 
	 * @return 总数
	 */
	public int getTotal();
	
	
	/**
	 * <b>结果集列表</b>
	 * 
	 * @since 1.0
	 * 
	 * @return 结果集
	 */
	public List<T> getList();
}
