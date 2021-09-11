package com.ma.tools;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ma.Bo.ConfigBO;

/**
 * ��ȡ�����ļ�
 * @author lucksun
 *
 */
public class Config {
	public HashMap<String,String> configMap;
	public static String resourcePath;
	public List<ConfigBO> cfgBoList;
	public String xmlPath; 
	public Xmlreader cfg;
	
	public Config(String s) throws Exception {
		String path = this.getClass().getResource("").getPath().toString();
		path = path.substring(1,path.length());
		resourcePath = path.replace("bin/com/ma/tools/", "res/");
		xmlPath = resourcePath +"/"+s+".xml";
		cfg = new Xmlreader(xmlPath);
		configMap = (HashMap) cfg.getMap().get("param");
		sortMap(configMap);
	}
	
	/**
	 * ���ʱ����Ҫ��Ϊ��������÷�ʽ��������Ҳ���xml�ļ�
	 * @param m
	 */
	
//	@SuppressWarnings("unchecked")
//	public Config(String s) throws Exception {		
//		xmlPath = "./"+s+".xml";
//		cfg = new Xmlreader(xmlPath);
//		configMap = (HashMap) cfg.getMap().get("param");
//		sortMap(configMap);
//	}
	
	public void sortMap(Map m) {
		Iterator iter = m.entrySet().iterator(); 
		while (iter.hasNext()) { 
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    Object key = entry.getKey(); 
		    Object val = entry.getValue(); 
		} 
		System.out.println(m);
	}
	
	public Config(String a,String s) throws Exception {
		xmlPath = s;
		cfg = new Xmlreader(s);
		configMap = (HashMap) cfg.getMap().get("param");
	}
	
	public String saveXml(HashMap m,String path) {
		if(!path.endsWith(".xml")) {
			path = path+".xml";
		}
		xmlPath = path;
		cfg.build01(xmlPath, m);
		return xmlPath;
	}
}
