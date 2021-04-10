package com.huawei.bean;

/**
 * 测量算法的接口
 *
 */
public interface Measurable {
	/**
	 * 测量obj,得到值
	 * @param obj 要测量的对象
	 * @return   double 值
	 */
	public double measure(Object obj);
}
