package com.ma.tools;
/**
 * ģ���࣬���岻ͬ��ģ�ͣ����ڽ�����־��demoʹ��nbminer
 * @author lucksun
 *
 */
public interface Model {
	
	// ��־��ʼ�������Ĳ��֣�������ʽ
	String getPattern();

	// �ָ�����ʲô
	String getSplit();

	// id�Ƿָ���ȡ�����ĵڼ�����ֵ
	int getIdi();

	int getStartI();

	int getEndI();
	
}
