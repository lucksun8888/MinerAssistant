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
	 * 输入模板路径、目标路径，自动批量生成替换后的文件，包含路径和doc（包含docx）文件
	 * @param source
	 * @param tagert
	 * @param param
	 * @throws Exception
	 */
	public void dealXls(String source,String tagert,Config paramcfg) throws Exception {
		// 加载所有已经保存的配置文件,递归所有后缀为.xls(xlsx)的文件
		File sourceF = new File(source);
		listDirectory(sourceF);
		
		// 遍历所有source路径、tagert路径进行批量替换doc文件
		for(int i =0;i<fileList.size();i++) {
			
			// 文件名称也进行参数化的替换 第一版先简单写死
			exportExcel(fileList.get(i),fileNameModify(fileList.get(i).replace(source, tagert),paramcfg),paramcfg);
		}
	}
	
	
	private String fileNameModify(String tagert, Config paramcfg) {
		// TODO Auto-generated method stub
		String s = "";
		s = tagert.replace("项目立项名称", paramcfg.configMap.get("p070")).replace("版本号", paramcfg.configMap.get("version")).replace("项目系统名称", paramcfg.configMap.get("p001"));
		return s;
	}


	/**
	 * 批量生成excel
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
		    System.out.println("//不存在");  
		    file.getParentFile().mkdirs();    
		}
		Map<String, String> params = new HashMap<String, String>();
		// 读取excel模板
		if(source.endsWith("xlsx")) {
			XSSFWorkbook workbook=new XSSFWorkbook(new FileInputStream(new File(rootPath)));
		    XSSFSheet sheet=null;
		    
		    XlsxUtil xlsxutil = new XlsxUtil();
		    xlsxutil.copyParamFromBean(paramcfg.configMap, params);// 调用DocxUtil中的copyParamFromBean方法，为VO的每个值建立“${valuename}”键
		    
		    // 需要循环每个页签进行替换
		    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
				sheet = workbook.getSheetAt(i);
				for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {// 获取每行
					XSSFRow row = sheet.getRow(j);
					if(row!=null) {
						for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {// 获取每个单元格
							
							 for (Entry<String, String> e : params.entrySet()) {
								 if ((row.getCell(k)!=null&&row.getCell(k).getCellType()!=1) || row.getCell(k)==null) {
										continue; 
									 }
		                         if (e.getKey().equals(row.getCell(k).getStringCellValue())) {//如果单元格中的变量和‘键’相等，就用‘键’所对应的‘值’代替。
		                        	 row.getCell(k).setCellValue(e.getValue());//所以这里就要求每一个单元格只能有唯一的变量。
		                         }
		                     }
							System.out.print(row.getCell(k) + "\t");
						}
					}					
					System.out.println("---Sheet表" + i + "处理完毕---");
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
	        System.out.println("数据已经写入excel"); //温馨提示
		    
		    
		    
		}else {
			HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(new File(rootPath)));
		    HSSFSheet sheet=null;
		    
		    XlsxUtil xlsxutil = new XlsxUtil();
		    xlsxutil.copyParamFromBean(paramcfg.configMap, params);// 调用DocxUtil中的copyParamFromBean方法，为VO的每个值建立“${valuename}”键
		    
		    // 需要循环每个页签进行替换
		    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
				sheet = workbook.getSheetAt(i);
				for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {// 获取每行
					HSSFRow row = sheet.getRow(j);
					if(row!=null) {
						for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {// 获取每个单元格
							
							 for (Entry<String, String> e : params.entrySet()) {
								 if ((row.getCell(k)!=null&&row.getCell(k).getCellType()!=1) || row.getCell(k)==null) {
									continue; 
								 }
		                         if (e.getKey().equals(row.getCell(k).getStringCellValue())) {//如果单元格中的变量和‘键’相等，就用‘键’所对应的‘值’代替。
		                        	 row.getCell(k).setCellValue(e.getValue());//所以这里就要求每一个单元格只能有唯一的变量。
		                         }
		                     }
							System.out.print(row.getCell(k) + "\t");
						}
					}						
					
					System.out.println("---Sheet表" + i + "处理完毕---");
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
	        System.out.println("数据已经写入excel"); //温馨提示
		    
		    
		    
		    
		}
		
		
	    
	}

	
	 public void listDirectory(File dir) throws IOException{
	        // TODO Auto-generated method stub
	        if(!dir.exists()){
	            throw new IllegalArgumentException("目录："+dir+"不存在.");
	        }
	        if(!dir.isDirectory()){
	            throw new IllegalArgumentException(dir+"不是目录");
	        }
	        //如果要遍历子目录下的内容就需要构造成File对象做递归操作，File提供了直接返回子目录的抽象
	        File[] files = dir.listFiles();//直接返回的是直接子目录（文件的抽象）
	        if(files!=null&&files.length>0){
	            for (File file : files) {
	                if(file.isDirectory()){
	                    //递归
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
      * @Description: 将t中的字段转换成替换模板需要的数据${字段}-->字段值
*      在word模板中变量为${valuename},为每个值建一个以‘${valuename}’为键，‘value’为值的Map集合，
*      利用键去和Word模板中寻找相等的变量
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
         * 关闭输出流
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
