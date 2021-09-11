package com.ma.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class DocxUtil{
	
	ArrayList<String> fileList = new ArrayList<String>();
	/**
	 * ����ģ��·����Ŀ��·�����Զ����������滻����ļ�������·����doc������docx���ļ�
	 * @param source
	 * @param tagert
	 * @param param
	 * @throws Exception
	 */
	public void dealDoc(String source,String tagert,Config paramcfg) throws Exception {
		// ���������Ѿ�����������ļ�,�ݹ����к�׺Ϊ.doc(docx)���ļ�
		File sourceF = new File(source);
		listDirectory(sourceF);
		
		// ��������source·����tagert·�����������滻doc�ļ�
		for(int i =0;i<fileList.size();i++) {
			// �ļ�����Ҳ���в��������滻 ��һ���ȼ�д��
			exportDoc(fileList.get(i),fileNameModify(fileList.get(i).replace(source, tagert),paramcfg),paramcfg);
		}
	}
	
	
	private String fileNameModify(String tagert, Config paramcfg) {
		// TODO Auto-generated method stub
		String s = "";
		s = tagert.replace("��Ŀ��������", paramcfg.configMap.get("p070")).replace("�汾��", paramcfg.configMap.get("version")).replace("��Ŀϵͳ����", paramcfg.configMap.get("p001"));
		return s;
	}
	
	 public void listDirectory(File dir) throws IOException{
	        // TODO Auto-generated method stub
	        if(!dir.exists()){
	            throw new IllegalArgumentException("Ŀ¼��"+dir+"������.");
	        }
	        if(!dir.isDirectory()){
	            throw new IllegalArgumentException(dir+"����Ŀ¼");
	        }
	        //���Ҫ������Ŀ¼�µ����ݾ���Ҫ�����File�������ݹ������File�ṩ��ֱ�ӷ�����Ŀ¼�ĳ���
	        File[] files = dir.listFiles();//ֱ�ӷ��ص���ֱ����Ŀ¼���ļ��ĳ���
	        if(files!=null&&files.length>0){
	            for (File file : files) {
	                if(file.isDirectory()){
	                    //�ݹ�
	                    listDirectory(file);
	                }
	                else{
	                	if(file.getAbsolutePath().endsWith("doc") || file.getAbsolutePath().endsWith("docx")) {
	                	fileList.add(file.getAbsolutePath());}
	                    System.out.println(file);
	                }
	            }
	        }
	    }
	
	
	public String exportDoc(String source, String tagert, Config paramcfg) {
		// ֻ֧��docx ����
		if(!source.endsWith("docx")) {
			return null;
		}
		OutputStream out = null;
		DocxUtil docxUtil = null;
		try {
			String rootPath = source;
			String destPath = tagert;
			File file = new File(tagert);
			if  (!file.getParentFile().exists())      
			{       
			    System.out.println("//������");  
			    file.getParentFile().mkdirs();    
			}
			Map<String, String> params = new HashMap<String, String>();
			docxUtil = new DocxUtil();
			XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(rootPath));// ��ȡWordģ��
			// ��param����ֱ�ӽ�������
			docxUtil.copyParamFromBean(paramcfg.configMap, params);// ����DocxUtil�е�copyParamFromBean������ΪVO��ÿ��ֵ������${valuename}����
			docxUtil.searchAndReplace(document, params);// �滻ģ���еĶ�Ӧ������
			out = new FileOutputStream(destPath);
			document.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != docxUtil) {
				docxUtil.close(out);// �ر���
			}
		}
		return null;
	}
	
	
	
	
    /**
      * @Description: ��t�е��ֶ�ת�����滻ģ����Ҫ������${�ֶ�}-->�ֶ�ֵ
*      ��wordģ���б���Ϊ${valuename},Ϊÿ��ֵ��һ���ԡ�${valuename}��Ϊ������value��Ϊֵ��Map���ϣ�
*      ���ü�ȥ��Wordģ����Ѱ����ȵı���
     */
    public  Map<String, String> copyParamFromBean(Map<String,String> m, Map<String, String> params) {
        String key;
        for (String field :  m.keySet()) {
            String fieldName = field;
            key = "${" + fieldName + "}";
            try {
                params.put(key, m.get(field));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return params;
    }
/***
*�滻Wordģ���еĶ�Ӧ������
*���������1-�����еı����� 2-����еı���
*/
 public void searchAndReplace(XWPFDocument document,Map<String, String> map) {
        try {
            // �滻�����е�ָ������
            Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();//���word�ж���
            while (itPara.hasNext()) {       //��������
                XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                Set<String> set = map.keySet();
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    List<XWPFRun> run=paragraph.getRuns();
                    for(int i=0;i<run.size();i++)
                    {
                        if(run.get(i).getText(run.get(i).getTextPosition())!=null &&
                                run.get(i).getText(run.get(i).getTextPosition()).equals(key))
                        {
                            /**
                             * ����0��ʾ���ɵ�������Ҫ����һ���ط���ʼ����,�������ִ�λ��0��ʼ
                             * �Ϳ��԰�ԭ�����滻��
                             */
                            run.get(i).setText(map.get(key),0);
                        }
                    }
                }
            }        
            
            
            
            // �滻����е�ָ������2
            Iterator<XWPFTable> itTable = document.getTablesIterator();//���Word�ı��
            while (itTable.hasNext()) { //�������
                XWPFTable table = (XWPFTable) itTable.next();
                int count = table.getNumberOfRows();//��ñ��������
                for (int i = 0; i < count; i++) { //��������ÿһ��
                    XWPFTableRow row = table.getRow(i);//��ñ�����
                    List<XWPFTableCell> cells = row.getTableCells();//����Ԫ���У���ñ��ĵ�Ԫ��
                    for (XWPFTableCell cell : cells) {   //������Ԫ��
                        for (Entry<String, String> e : map.entrySet()) {
                            if (cell.getText().equals(e.getKey())) {//�����Ԫ���еı����͡�������ȣ����á���������Ӧ�ġ�ֵ�����档
                                cell.removeParagraph(0);//���������Ҫ��ÿһ����Ԫ��ֻ����Ψһ�ı�����
                                cell.setText(e.getValue());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/**
     * �ر������
     *
     * @param os
     */
    public void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


