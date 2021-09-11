package com.ma.tools;

import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class MenuTest {
 
	public static void main(String[] args) {
 
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Console");
		shell.setBounds(100, 100, 500, 400);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		shell.setLayout(gridLayout);
 
		//����һ���˵�,�����File��Select Me Nowѡ��ʱ�������߳��е�ѭ����ӡ
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
 
		MenuItem fileMenuItems = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuItems.setText("&File");
		Menu subMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuItems.setMenu(subMenu);
		MenuItem selectItem = new MenuItem(subMenu, SWT.NULL);
		selectItem.setText("&Select Me Now");
		selectItem.setAccelerator(SWT.CTRL + 'S');
		selectItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// Ĭ������¼����¼�ʱ�����߳���ִ��
				// �����߳�ִ����������������߳�
				PrintThread printThread = new PrintThread("the First Item");
				printThread.start();
			}
		});
		MenuItem sep = new MenuItem(subMenu, SWT.SEPARATOR);
		MenuItem exitItem = new MenuItem(subMenu, SWT.NULL);
		exitItem.setText("&Exit");
 
		exitItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				shell.dispose();
			}
		});
 
		Text text = new Text(shell, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL));
 
                //����ض�������
		MyPrintStream mps = new MyPrintStream(System.out, text);
		System.setOut(mps);
		System.setErr(mps);
 
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		System.exit(0); // ɱ�����߳�,��ֹjvm
	}
 
}
 
//����һ��PrintStream����,����ӡ���������ض���Text�������ʾ
class MyPrintStream extends PrintStream {
 
	private Text text;
 
	public MyPrintStream(OutputStream out, Text text) {
		super(out);
		this.text = text;
	}
	// ��д����write����,������������д�ӡ�������涼Ҫ���õķ���
	public void write(byte[] buf, int off, int len) {
		final String message = new String(buf, off, len);
 
		// SWT�ǽ����̷߳�������ķ�ʽ
		Display.getDefault().syncExec(new Thread() {
			public void run() {
				// ����Ϣ��ӵ������
				if (text != null && !text.isDisposed()) {
					text.append(message);
				}
			}
		});
	}
 
}
 
//�ڷ�UI�߳��в���ִ��System.out.println()����
class PrintThread extends Thread {
	private String name;
 
	public PrintThread(String name) {
		this.name = name;
	}
	public void run() {
		while (true) {
			System.out.println(name + "was selected!");
		}
	}
}