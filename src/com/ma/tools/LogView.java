package com.ma.tools;
 
import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * �����ļ���Сʵʱ��ȡ����
 * @author admin
 *
 */
public class LogView {
 
	public long lastTimeFileSize = 0; //�ϴ��ļ���С
	private static final Logger logger = LoggerFactory.getLogger(LogView.class);
	private SimpleDateFormat  dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
	public static boolean POWERUP;
	
	ScheduledExecutorService  exec = Executors.newScheduledThreadPool(1);
	
	public void realtimeShowLog(final File logFile) throws Exception{
		
		if(logFile == null) {
			throw new IllegalStateException("logFile can not be null");
		}
		
		//����һ���߳�ÿ10���ȡ��������־��Ϣ
		exec.scheduleWithFixedDelay(new Runnable(){
			@Override
			public void run() {
				POWERUP = true;
				TransLog tl = new TransLog();
				//��ñ仯����
				try {
					
					long len = logFile.length();
					if(len < lastTimeFileSize){
						logger.info("Log file was reset. Restarting logging from start of file.");
						lastTimeFileSize = 0;
					}else{
					
						//ָ���ļ��ɶ���д
						RandomAccessFile  randomFile= new RandomAccessFile(logFile,"rw");
						
						//��ȡRandomAccessFile�����ļ�ָ���λ�ã���ʼλ����0
						System.out.println("RandomAccessFile�ļ�ָ��ĳ�ʼλ��:"+lastTimeFileSize); 
						
						randomFile.seek(lastTimeFileSize);//�ƶ��ļ�ָ��λ�� 
						
						String tmp = "";// һ��һ�ж�ȡ
						StringBuffer sb = new StringBuffer();// ƴд��������input���ڽ���
						while((tmp = randomFile.readLine()) != null) {
							sb.append(tmp);
							
							// System.out.println("info : " +new String(tmp.getBytes("utf-8")));
						}
						if(sb.length()>0) {
							tl.trans(sb.toString(),new ModelNBminer());
							if(tl.GPUM.size()>0)
							System.out.println("info : " +tl.GPUM);
						}
						lastTimeFileSize = randomFile.length();
						randomFile.close();
					}
					
				} catch (Exception e) {
					//ʵʱ��ȡ��־�쳣����Ҫ��¼ʱ���lastTimeFileSize �Ա�����ֶ�����
					logger.error(dateFormat.format(new Date())  + " File read error, lastTimeFileSize: "+lastTimeFileSize);
				} finally {
					//��lastTimeFileSize ����Ա��´�������ʱ��ֱ�Ӵ�ָ��λ�û�ȡ
				}
			}
			
		}, 0, 10, TimeUnit.SECONDS); // 0�뿪ʼ����ɺ�10����ٿ�ʼ��
		
	}
	public void stop(){
		if(exec != null){
			exec.shutdown();
			logger.info("file read stop ��");
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		LogView view = new LogView();
		final File tmpLogFile = new File("C:\\qskg\\log\\test.log");
		view.lastTimeFileSize = 0;
		view.realtimeShowLog(tmpLogFile);
		
	}
	
}