package com.ma.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CBanner;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

public class CompositeDemo_05 {

	public void testCbanner() {

		Display display = new Display();

		// 窗口

		final Shell topShell = new Shell(display, SWT.SHELL_TRIM | SWT.BORDER);

		topShell.setText("测试自testCbanner");

		topShell.setLayout(new FillLayout());

		CBanner cBanner = new CBanner(topShell, SWT.NONE);

		cBanner.setLayout(new FillLayout());

		// 设置椭圆

		cBanner.setSimple(false);

		Composite child1 = new Composite(cBanner, SWT.NONE);

		child1.setLayout(new FillLayout());

		new Text(child1, SWT.SINGLE).setText("窗口1");

		Composite child2 = new Composite(cBanner, SWT.NONE);

		child2.setLayout(new FillLayout());

		new Text(child2, SWT.SINGLE).setText("窗口2");

		Composite child3 = new Composite(cBanner, SWT.NONE);

		child3.setLayout(new FillLayout());

		new Text(child3, SWT.SINGLE).setText("窗口3");

		cBanner.setLeft(child1);

		cBanner.setRight(child2);

		cBanner.setBottom(child3);

		topShell.open();

		while (!topShell.isDisposed()) {

			if (!display.readAndDispatch()) {

				display.sleep();

			}

		}

		display.dispose();

	}

	public void testSashForm() {

		Display display = new Display();

		// 窗口

		final Shell topShell = new Shell(display, SWT.SHELL_TRIM | SWT.BORDER);

		topShell.setText("测试自sashform");

		topShell.setLayout(new FillLayout());

		// SWT.HORIZONTAL决定了分割的方向，后边的数组决定了这个方向下的个数

		SashForm sashForm = new SashForm(topShell, SWT.HORIZONTAL | SWT.SMOOTH);

		sashForm.setLayout(new FillLayout());

		Composite child1 = new Composite(sashForm, SWT.NONE);

		child1.setLayout(new FillLayout());

		new Text(child1, SWT.SINGLE).setText("窗口1");

		Composite child2 = new Composite(sashForm, SWT.NONE);

		child2.setLayout(new FillLayout());

		new Text(child2, SWT.SINGLE).setText("窗口2");

		sashForm.setWeights(new int[] { 30, 70 });

		topShell.open();

		while (!topShell.isDisposed()) {

			if (!display.readAndDispatch()) {

				display.sleep();

			}

		}

		display.dispose();

	}

	public void testCTabFolder() {

		// 负责和操作系统交互，如读取底层事件等

		Display display = new Display();

		// 窗口

		final Shell topShell = new Shell(display, SWT.SHELL_TRIM | SWT.BORDER);

		topShell.setText("测试自定义选项卡控件");

		topShell.setSize(800, 500);

		// 不设置布局不显示

		topShell.setLayout(new GridLayout());

		final CTabFolder tabFolder = new CTabFolder(topShell, SWT.TOP);

		// 这里不是layout，grapExcess:fit=true抓住过剩

		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		tabFolder.setTabHeight(20);

		// 内容的padding

		tabFolder.marginHeight = 10;

		tabFolder.marginWidth = 10;

		// 设置最大化最小化

		tabFolder.setMaximizeVisible(true);

		tabFolder.setMinimizeVisible(true);

		// 设置渐变色

		Color colors[] = new Color[4];

		colors[0] = display.getSystemColor(SWT.COLOR_DARK_BLUE);

		colors[1] = display.getSystemColor(SWT.COLOR_BLUE);

		colors[2] = display.getSystemColor(SWT.COLOR_WHITE);

		colors[3] = display.getSystemColor(SWT.COLOR_WHITE);

		int percents[] = new int[] { 25, 50, 100 };

		tabFolder.setSelectionBackground(colors, percents);

		// 椭圆

		tabFolder.setSimple(false);

		// 增加最大化最小化监听

		tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {

			@Override

			public void minimize(CTabFolderEvent event) {

				// 所有事件的父类

				TypedEvent parentEvent;

				// 获得事件源：CTabFolder

				Widget widget = event.widget;

				System.out.println(widget.getClass().getSimpleName());

				// 事件发生的时间

				System.out.println(event.time);

				// 最小化了就只显示最大化按钮了

				tabFolder.setMinimized(true);

				// 通过设置布局实现最大化最小化复原

				tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

				// 使设置生效

				topShell.layout(true);

			}

			@Override

			public void maximize(CTabFolderEvent event) {

				tabFolder.setMaximized(true);

				tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

				// 使设置生效

				topShell.layout(true);

			}

			@Override

			public void restore(CTabFolderEvent event) {

				tabFolder.setMinimized(false);

				tabFolder.setMaximized(false);

				tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

				// 使设置生效

				topShell.layout(true);

			}

		});

		// 设置最大文字数(?)

		tabFolder.setMinimumCharacters(4);

		// 设置右上角

//      tabFolder.setTopRight(null);

		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE | SWT.CLOSE);

		tabItem.setText("审核前审核前审核前审核前审核前审核前审核前");

		Label label1 = new Label(tabFolder, SWT.LEFT);

		label1.setText("审核前表格");

		tabItem.setControl(label1);

		CTabItem tabItem2 = new CTabItem(tabFolder, SWT.NONE);

		tabItem2.setText("审核后");

		Label label2 = new Label(tabFolder, SWT.LEFT);

		label2.setText("审核后表格");

		tabItem2.setControl(label2);

		CTabItem tabItem3 = new CTabItem(tabFolder, SWT.NONE);

		tabItem3.setText("选项卡3");

		Text text = new Text(tabFolder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.WRAP);

		text.setText("审核后表格3");

		tabItem3.setControl(text);

		CTabItem tabItem4 = new CTabItem(tabFolder, SWT.BORDER);

		tabItem4.setText("选项卡4");

		Text text4 = new Text(tabFolder, SWT.MULTI);

		text4.setText("审核后表格4");

		tabItem4.setControl(text4);

		tabFolder.pack();

		// topShell.pack();

		topShell.open();

		while (!topShell.isDisposed()) {

			if (!display.readAndDispatch()) {

				display.sleep();

			}

		}

		display.dispose();

	}

	public void testTabFolder() {

		// 负责和操作系统交互，如读取底层事件等

		Display display = new Display();

		// 窗口

		Shell topShell = new Shell(display, SWT.SHELL_TRIM | SWT.BORDER);

		topShell.setText("测试选项卡控件");

		topShell.setSize(800, 500);

		// 不设置布局不显示

		topShell.setLayout(new GridLayout());

		TabFolder tabFolder = new TabFolder(topShell, SWT.BOTTOM);

		TabItem tabItem = new TabItem(tabFolder, SWT.BORDER);

		tabItem.setText("审核前");

		Label label1 = new Label(tabFolder, SWT.LEFT);

		label1.setText("审核前表格");

		tabItem.setControl(label1);

		TabItem tabItem2 = new TabItem(tabFolder, SWT.BORDER);

		tabItem2.setText("审核后");

		Label label2 = new Label(tabFolder, SWT.LEFT);

		label2.setText("审核前的表格");

		tabItem2.setControl(label2);

		// topShell.pack();

		topShell.open();

		while (!topShell.isDisposed()) {

			if (!display.readAndDispatch()) {

				display.sleep();

			}

		}

		display.dispose();

	}

	public  static  void main(String[] args) {
		CompositeDemo_05 cd = new CompositeDemo_05();
		cd.testSashForm();
	}
}