package com.ma.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 执行windows的cmd命令工具类
 * @author dufei
 *
 */
public class CMDUtil {

	public static Process PROCESS = null;
	/**
	 * 执行一个cmd命令
	 * @param cmdCommand cmd命令
	 * @return 命令执行结果字符串，如出现异常返回null
	 */
	public static String excuteCMDCommand(String cmdCommand) 
	{
		StringBuilder stringBuilder = new StringBuilder();
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmdCommand);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(process.getInputStream(), "GBK"));
			String line = null;
			while((line=bufferedReader.readLine()) != null) 
			{
				stringBuilder.append(line+"\n");
			}
			return stringBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 执行bat文件，
	 * @param file bat文件路径
	 * @param isCloseWindow 执行完毕后是否关闭cmd窗口
	 * @return bat文件输出log
	 */
	public static void excuteBatFile(String file, boolean isCloseWindow) 
	{
		String cmdCommand = null;
		if(isCloseWindow) 
		{
			cmdCommand = "cmd.exe /c "+file;
		}else 
		{
			cmdCommand = "cmd.exe /k "+file;
		}
//		StringBuilder stringBuilder = new StringBuilder();
		try {
			PROCESS = Runtime.getRuntime().exec(cmdCommand);
			
			StreamHandler errorStreamHandler = new StreamHandler(PROCESS.getErrorStream(), "ERROR");
			errorStreamHandler.start();
			
//			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
//			String line = null;
//			while((line=bufferedReader.readLine()) != null) 
//			{
//				stringBuilder.append(line+"\n");
//			}
		} catch (Exception e) {
			PROCESS.destroy();
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行bat文件,新开窗口
	 * @param file bat文件路径
	 * @param isCloseWindow 执行完毕后是否关闭cmd窗口
	 * @return bat文件输出log
	 */
	public static String excuteBatFileWithNewWindow(String file, boolean isCloseWindow) 
	{
		String cmdCommand = null;
		if(isCloseWindow) 
		{
			cmdCommand = "cmd.exe /c start"+file;
		}else 
		{
			cmdCommand = "cmd.exe /k start"+file;
		}
		StringBuilder stringBuilder = new StringBuilder();
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmdCommand);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(process.getInputStream(), "GBK"));
			String line = null;
			while((line=bufferedReader.readLine()) != null) 
			{
				stringBuilder.append(line+"\n");
			}
			return stringBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
