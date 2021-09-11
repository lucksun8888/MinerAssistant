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
 
		//定义一个菜单,当点击File的Select Me Now选项时触发子线程中的循环打印
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
				// 默认情况下监听事件时在主线程里执行
				// 在子线程执行任务避免阻塞主线程
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
 
                //输出重定向设置
		MyPrintStream mps = new MyPrintStream(System.out, text);
		System.setOut(mps);
		System.setErr(mps);
 
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		System.exit(0); // 杀掉子线程,终止jvm
	}
 
}
 
//定义一个PrintStream子类,将打印语句输出流重定向到Text组件中显示
class MyPrintStream extends PrintStream {
 
	private Text text;
 
	public MyPrintStream(OutputStream out, Text text) {
		super(out);
		this.text = text;
	}
	// 重写父类write方法,这个方法是所有打印方法里面都要调用的方法
	public void write(byte[] buf, int off, int len) {
		final String message = new String(buf, off, len);
 
		// SWT非界面线程访问组件的方式
		Display.getDefault().syncExec(new Thread() {
			public void run() {
				// 把信息添加到组件中
				if (text != null && !text.isDisposed()) {
					text.append(message);
				}
			}
		});
	}
 
}
 
//在非UI线程中不断执行System.out.println()方法
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