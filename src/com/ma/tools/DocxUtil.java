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
	 * 输入模板路径、目标路径，自动批量生成替换后的文件，包含路径和doc（包含docx）文件
	 * @param source
	 * @param tagert
	 * @param param
	 * @throws Exception
	 */
	public void dealDoc(String source,String tagert,Config paramcfg) throws Exception {
		// 加载所有已经保存的配置文件,递归所有后缀为.doc(docx)的文件
		File sourceF = new File(source);
		listDirectory(sourceF);
		
		// 遍历所有source路径、tagert路径进行批量替换doc文件
		for(int i =0;i<fileList.size();i++) {
			// 文件名称也进行参数化的替换 第一版先简单写死
			exportDoc(fileList.get(i),fileNameModify(fileList.get(i).replace(source, tagert),paramcfg),paramcfg);
		}
	}
	
	
	private String fileNameModify(String tagert, Config paramcfg) {
		// TODO Auto-generated method stub
		String s = "";
		s = tagert.replace("项目立项名称", paramcfg.configMap.get("p070")).replace("版本号", paramcfg.configMap.get("version")).replace("项目系统名称", paramcfg.configMap.get("p001"));
		return s;
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
	                	if(file.getAbsolutePath().endsWith("doc") || file.getAbsolutePath().endsWith("docx")) {
	                	fileList.add(file.getAbsolutePath());}
	                    System.out.println(file);
	                }
	            }
	        }
	    }
	
	
	public String exportDoc(String source, String tagert, Config paramcfg) {
		// 只支持docx 处理
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
			    System.out.println("//不存在");  
			    file.getParentFile().mkdirs();    
			}
			Map<String, String> params = new HashMap<String, String>();
			docxUtil = new DocxUtil();
			XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(rootPath));// 读取Word模板
			// 从param里面直接解析出来
			docxUtil.copyParamFromBean(paramcfg.configMap, params);// 调用DocxUtil中的copyParamFromBean方法，为VO的每个值建立“${valuename}”键
			docxUtil.searchAndReplace(document, params);// 替换模板中的对应变量。
			out = new FileOutputStream(destPath);
			document.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != docxUtil) {
				docxUtil.close(out);// 关闭流
			}
		}
		return null;
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
/***
*替换Word模板中的对应变量。
*两种情况：1-段落中的变量。 2-表格中的变量
*/
 public void searchAndReplace(XWPFDocument document,Map<String, String> map) {
        try {
            // 替换段落中的指定文字
            Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();//获得word中段落
            while (itPara.hasNext()) {       //遍历段落
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
                             * 参数0表示生成的文字是要从哪一个地方开始放置,设置文字从位置0开始
                             * 就可以把原变量替换掉
                             */
                            run.get(i).setText(map.get(key),0);
                        }
                    }
                }
            }        
            
            
            
            // 替换表格中的指定文字2
            Iterator<XWPFTable> itTable = document.getTablesIterator();//获得Word的表格
            while (itTable.hasNext()) { //遍历表格
                XWPFTable table = (XWPFTable) itTable.next();
                int count = table.getNumberOfRows();//获得表格总行数
                for (int i = 0; i < count; i++) { //遍历表格的每一行
                    XWPFTableRow row = table.getRow(i);//获得表格的行
                    List<XWPFTableCell> cells = row.getTableCells();//在行元素中，获得表格的单元格
                    for (XWPFTableCell cell : cells) {   //遍历单元格
                        for (Entry<String, String> e : map.entrySet()) {
                            if (cell.getText().equals(e.getKey())) {//如果单元格中的变量和‘键’相等，就用‘键’所对应的‘值’代替。
                                cell.removeParagraph(0);//所以这里就要求每一个单元格只能有唯一的变量。
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


