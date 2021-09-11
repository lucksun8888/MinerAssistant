package com.ma.tools;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ExecTest {
	public static void main(String[] args) {
		try {
			Runtime runtime = Runtime.getRuntime();
			final Process process = runtime.exec("C:\\qskg\\exts\\nbminer_37_1\\nbminer -a ethash -o stratum+tcp://-o cn.sparkpool.com:3333 -u sp_lucksun888.002 -log-file \"C:\\qskg\\log\\20210329_112409_ETH_log.txt\"");
			StreamHandler errorStreamHandler = new StreamHandler(process.getErrorStream(), "ERROR");
			errorStreamHandler.start();
		} catch (IOException ex) {
			Logger.getLogger(ExecTest.class.getName()).log(Level.ALL, null, ex);
		}
	}

}