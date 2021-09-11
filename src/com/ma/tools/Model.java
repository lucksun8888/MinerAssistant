package com.ma.tools;
/**
 * 模型类，定义不同的模型，用于解析日志。demo使用nbminer
 * @author lucksun
 *
 */
public interface Model {
	
	// 日志开始、结束的部分，正则表达式
	String getPattern();

	// 分隔符是什么
	String getSplit();

	// id是分隔符取出来的第几个数值
	int getIdi();

	int getStartI();

	int getEndI();
	
}
