package com.ma.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


/**
 * ���������ļ�����ȡ�ڵ���Ϣ
 * 
 * @author lucksun
 *
 */
public class Xmlreader {
	HashMap<String, Serializable> nodeMap = new HashMap<String, Serializable>();

	public Xmlreader(String filename) throws Exception {
		parserXml(filename);
	}

	public int getLength() {
		return nodeMap.size();
	}

	public void parserXml(String fileName) {
		try {
			File f = new File(fileName);
			InputStream in = new FileInputStream(f);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(in);
			Element root = doc.getRootElement();
			readNode(root, nodeMap);
			System.out.println(nodeMap);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void readNode(Element root, HashMap<String, Serializable> map) {
		if (root == null)
			return;
		// ��ȡ����
		if (root.getData() != null && !"".equals(root.getData().toString().trim())) {
			map.put(root.getName(), (String) root.getData());
		}  
		
		// ��ȡ�����ӽڵ�
		List<Element> childNodes = root.elements();
		if(childNodes.size()>0)
		{
			HashMap<String, Serializable> m = new HashMap<String, Serializable>();
			map.put(root.getName(), m);
			for (Element e : childNodes) {
				readNode(e, m);
			}
		}		
	}

	public HashMap<String, Serializable> getMap() {
		return nodeMap;
	}
	
	
	/**
	 * ���������ļ�
	 * @param configMap 
	 * @param xmlPath 
	 */
	public void build01(String xmlPath, HashMap<String, String> configMap){
        try {
            //DocumentHelper�ṩ�˴���Document����ķ���
            Document document = DocumentHelper.createDocument();
            // ��ӽڵ���Ϣ
//            Element rootElement = document.addElement("param");
            // ������Լ�������ӽڵ㣬Ҳ����ָ������
            Element element = document.addElement("param");
            // ѭ�������������
            for(String key : configMap.keySet()) {
            	Element nameElement = element.addElement(key);
            	nameElement.setText(configMap.get(key));
            }
            System.out.println(document.asXML()); //��document�ĵ�����ֱ��ת�����ַ������
            

            OutputFormat format = OutputFormat.createPrettyPrint();  
            OutputStream out = new java.io.FileOutputStream(xmlPath);
            Writer fileWriter = new java.io.OutputStreamWriter(out, "UTF-8");
            
            //dom4j�ṩ��ר��д���ļ��Ķ���XMLWriter
            XMLWriter xmlWriter = new XMLWriter(fileWriter,format);
            xmlWriter.write(document);
            xmlWriter.flush();
            xmlWriter.close();
            System.out.println("xml�ĵ���ӳɹ���");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
}
