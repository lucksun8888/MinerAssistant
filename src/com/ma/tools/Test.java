package com.ma.tools;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

public class Test {

    protected Shell shell;

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
        try {
            Test window = new Test();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shell = new Shell();
        shell.setSize(450, 300);
        shell.setText("实时刷新时间");

        final Label label = new Label(shell, SWT.NONE);
        label.setFont(SWTResourceManager.getFont("微软雅黑", 13, SWT.NORMAL));
        label.setBounds(69, 48, 174, 41);
        new Thread() {//线程操作
               public void run() {
                   while(true){
                       try {
                           //对Label进行实时刷新，需要加上这句
                        label.getDisplay().asyncExec(new Runnable() {       
                            @Override
                            public void run() {
                                // 设置时间 ，格式化输出时间
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String s = sdf.format(new Date());
                                label.setText(s);//输出到Label上            
                            }
                        });
                        Thread.sleep(1000);//每隔一秒刷新一次
                    } catch (Exception e) {
                    }

                   }
               }
        }.start();
    }
}