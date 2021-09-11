package com.ma.tools;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将不同的内核输出的log转换成表格数据
 * @author lucksun
 *
 */
public class TransLog {
	// 日志获取的挖矿记录设置为全局变量
	public static HashMap<String, HashMap> GPUM = new HashMap<String, HashMap>();
	
	/**
	 * 将日志中的列表数据转换为map返回到map中，map按照id的顺序排序
	 * @param input
	 * @param mdl
	 */
	public void trans(String input,Model mdl) {
		getValue(input,mdl);
//		System.out.println(gpum);
	}

	/**
	 * 获取最近一次数据列表中的所有数据 是一个listmap，如果同一个id重复的话，取最新的，比如gpu0，在5秒内刷新了两次，则认为最后一次是需要展示的部分
	 * @param input
	 * @param mdl
	 * @return
	 */
	private void getValue(String input, Model mdl) {
		
		// TODO Auto-generated method stub
		// 从一段日志中截取完整的列表数据
		String pattern = mdl.getPattern();
		String split = mdl.getSplit();
//		int starti = mdl.getStartI();
		int endi = mdl.getEndI();
		int idi = mdl.getIdi();
//		String pattern = "(Eff\\/Watt\\|.*?=============================================================================)";
		 
//		input  = "[18:38:03] INFO - ethash - New job: cn.sparkpool.com:3333, ID: ac1a481c, DIFF: 8.000G[18:38:04] INFO - ======================== Summary 2021-03-27 18:38:04 ========================[18:38:04] INFO - |ID|Device|Hashrate|Accept|Reject|Inv|Powr|Temp|Fan|CClk|GMClk|MUtl|Eff/Watt|[18:38:04] INFO - | 0|  2060|  0.000 |     0|     0|  0|  81|  51|  0|1680| 6150|  81|  0.000 |[18:38:04] INFO - |------------------+------+------+---+----+---------------------------------|[18:38:04] INFO - |    Total:  0.000 |     0|     0|  0|  81| Uptime:  0D 00:00:21   CPU:  8% |[18:38:04] INFO - =============================================================================dfsfdsfsdfs";
	      // 创建 Pattern 对象
	      Pattern r = Pattern.compile(pattern);
//	      System.out.println(input.replace("\r\n", " "));
	      // 现在创建 matcher 对象
	      Matcher m = r.matcher(input.replace("\r\n", " "));
	      
	      while (m.find( )) {
	    	 HashMap<Integer, String> detailM = new HashMap<Integer, String>();
	         System.out.println("Found value: " + m.group(0) );
//	         String[] strs = m.group(0).split("\\|");
	         String[] strs = m.group(0).split(split);
	         for(int i=idi;i<endi;i++) {
	        	 detailM.put(i, strs[i].trim());
//	        	 System.out.println(strs[i].trim());
	         }
	         GPUM.put(strs[idi], detailM);
	       //  System.out.println("Found value: " + m.group(2) );
	       //  System.out.println("Found value: " + m.group(3) ); 
	      }  
		
	}

	
	
}

