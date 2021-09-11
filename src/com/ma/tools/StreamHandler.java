package com.ma.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class StreamHandler extends Thread {
	InputStream m_inputStream;

	public StreamHandler(InputStream is, String type) {
		this.m_inputStream = is;
	}

	@Override
	public void run() {
		InputStreamReader isr = null;
		BufferedReader br = null;

		try {
			// 设置编码方式，否则输出中文时容易乱码
			isr = new InputStreamReader(m_inputStream, "GBK");
			br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if(line.length()>4)
				System.out.println(line);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				br.close();
				isr.close();
			} catch (IOException ex) {
				Logger.getLogger(StreamHandler.class.getName()).log(Level.ALL, null, ex);
			}
		}
	}
}