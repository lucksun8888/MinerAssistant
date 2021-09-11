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
        shell.setText("ʵʱˢ��ʱ��");

        final Label label = new Label(shell, SWT.NONE);
        label.setFont(SWTResourceManager.getFont("΢���ź�", 13, SWT.NORMAL));
        label.setBounds(69, 48, 174, 41);
        new Thread() {//�̲߳���
               public void run() {
                   while(true){
                       try {
                           //��Label����ʵʱˢ�£���Ҫ�������
                        label.getDisplay().asyncExec(new Runnable() {       
                            @Override
                            public void run() {
                                // ����ʱ�� ����ʽ�����ʱ��
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String s = sdf.format(new Date());
                                label.setText(s);//�����Label��            
                            }
                        });
                        Thread.sleep(1000);//ÿ��һ��ˢ��һ��
                    } catch (Exception e) {
                    }

                   }
               }
        }.start();
    }
}