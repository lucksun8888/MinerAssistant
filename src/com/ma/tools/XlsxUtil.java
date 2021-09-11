package com.ma.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxUtil {
	
	ArrayList<String> fileList = new ArrayList<String>();
	
	/**
	 * ����ģ��·����Ŀ��·�����Զ����������滻����ļ�������·����doc������docx���ļ�
	 * @param source
	 * @param tagert
	 * @param param
	 * @throws Exception
	 */
	public void dealXls(String source,String tagert,Config paramcfg) throws Exception {
		// ���������Ѿ�����������ļ�,�ݹ����к�׺Ϊ.xls(xlsx)���ļ�
		File sourceF = new File(source);
		listDirectory(sourceF);
		
		// ��������source·����tagert·�����������滻doc�ļ�
		for(int i =0;i<fileList.size();i++) {
			
			// �ļ�����Ҳ���в��������滻 ��һ���ȼ�д��
			exportExcel(fileList.get(i),fileNameModify(fileList.get(i).replace(source, tagert),paramcfg),paramcfg);
		}
	}
	
	
	private String fileNameModify(String tagert, Config paramcfg) {
		// TODO Auto-generated method stub
		String s = "";
		s = tagert.replace("��Ŀ��������", paramcfg.configMap.get("p070")).replace("�汾��", paramcfg.configMap.get("version")).replace("��Ŀϵͳ����", paramcfg.configMap.get("p001"));
		return s;
	}


	/**
	 * ��������excel
	 * @param string
	 * @param replace
	 * @param paramcfg
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private void exportExcel(String source, String tagert, Config paramcfg) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		String rootPath = source;
		File file = new File(tagert);
		if  (!file.getParentFile().exists())      
		{       
		    System.out.println("//������");  
		    file.getParentFile().mkdirs();    
		}
		Map<String, String> params = new HashMap<String, String>();
		// ��ȡexcelģ��
		if(source.endsWith("xlsx")) {
			XSSFWorkbook workbook=new XSSFWorkbook(new FileInputStream(new File(rootPath)));
		    XSSFSheet sheet=null;
		    
		    XlsxUtil xlsxutil = new XlsxUtil();
		    xlsxutil.copyParamFromBean(paramcfg.configMap, params);// ����DocxUtil�е�copyParamFromBean������ΪVO��ÿ��ֵ������${valuename}����
		    
		    // ��Ҫѭ��ÿ��ҳǩ�����滻
		    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// ��ȡÿ��Sheet��
				sheet = workbook.getSheetAt(i);
				for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {// ��ȡÿ��
					XSSFRow row = sheet.getRow(j);
					if(row!=null) {
						for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {// ��ȡÿ����Ԫ��
							
							 for (Entry<String, String> e : params.entrySet()) {
								 if ((row.getCell(k)!=null&&row.getCell(k).getCellType()!=1) || row.getCell(k)==null) {
										continue; 
									 }
		                         if (e.getKey().equals(row.getCell(k).getStringCellValue())) {//�����Ԫ���еı����͡�������ȣ����á���������Ӧ�ġ�ֵ�����档
		                        	 row.getCell(k).setCellValue(e.getValue());//���������Ҫ��ÿһ����Ԫ��ֻ����Ψһ�ı�����
		                         }
		                     }
							System.out.print(row.getCell(k) + "\t");
						}
					}					
					System.out.println("---Sheet��" + i + "�������---");
				}
			}
		    
		    
		    OutputStream out = null;
	        try {
	            out = new FileOutputStream(file);
	            workbook.write(out);
	            out.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        System.out.println("�����Ѿ�д��excel"); //��ܰ��ʾ
		    
		    
		    
		}else {
			HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(new File(rootPath)));
		    HSSFSheet sheet=null;
		    
		    XlsxUtil xlsxutil = new XlsxUtil();
		    xlsxutil.copyParamFromBean(paramcfg.configMap, params);// ����DocxUtil�е�copyParamFromBean������ΪVO��ÿ��ֵ������${valuename}����
		    
		    // ��Ҫѭ��ÿ��ҳǩ�����滻
		    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// ��ȡÿ��Sheet��
				sheet = workbook.getSheetAt(i);
				for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {// ��ȡÿ��
					HSSFRow row = sheet.getRow(j);
					if(row!=null) {
						for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {// ��ȡÿ����Ԫ��
							
							 for (Entry<String, String> e : params.entrySet()) {
								 if ((row.getCell(k)!=null&&row.getCell(k).getCellType()!=1) || row.getCell(k)==null) {
									continue; 
								 }
		                         if (e.getKey().equals(row.getCell(k).getStringCellValue())) {//�����Ԫ���еı����͡�������ȣ����á���������Ӧ�ġ�ֵ�����档
		                        	 row.getCell(k).setCellValue(e.getValue());//���������Ҫ��ÿһ����Ԫ��ֻ����Ψһ�ı�����
		                         }
		                     }
							System.out.print(row.getCell(k) + "\t");
						}
					}						
					
					System.out.println("---Sheet��" + i + "�������---");
				}
			}
		    
		    
		    OutputStream out = null;
	        try {
	            out = new FileOutputStream(file);
	            workbook.write(out);
	            out.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        System.out.println("�����Ѿ�д��excel"); //��ܰ��ʾ
		    
		    
		    
		    
		}
		
		
	    
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
	                	if(file.getAbsolutePath().endsWith("xls") || file.getAbsolutePath().endsWith("xlsx")) {
	                	fileList.add(file.getAbsolutePath());}
	                    System.out.println(file);
	                }
	            }
	        }
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
