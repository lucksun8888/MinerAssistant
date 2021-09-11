package com.ma.GUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.jface.util.Policy;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.TableViewerColumnSorter;

import com.ma.Bo.LogViewBOList;
import com.ma.tools.CMDUtil;
import com.ma.tools.Config;
import com.ma.tools.LogView;
import com.ma.tools.TransLog;
import org.eclipse.swt.custom.CBanner;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class GUIMain {

	protected Shell shlVer;
	private Table table;
//	Config cfg;
	private Text text;
	Composite composite_1;
	Composite composite;
	TabFolder tabFolder;
	TabItem tabItem;
	TabItem tabItem_1;
//	String CFGPath;
	TableViewer tableViewer;
	private Text text_kg;
	private Text text_wallet;
	private Combo combo_bz;
	private Combo combo_kc;
	static String batstr;
	
	Shell shell;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GUIMain window = new GUIMain();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @throws Exception 
	 */
	public void open() throws Exception {
		Display display = Display.getDefault();
		createContents();
		shlVer.open();
		shlVer.layout();
		while (!shlVer.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * @throws Exception 
	 */
	protected void createContents() throws Exception {
		shlVer = new Shell();
		shlVer.setMinimumSize(new Point(640, 480));
		shlVer.setImage(SWTResourceManager.getImage(GUIMain.class, "/icon/nano_icon_pack_6.png"));
		shlVer.setSize(1036, 685);
		shlVer.setText("\u77FF\u5DE5\u52A9\u624B Ver0.1");
		shlVer.setLayout(new FillLayout(SWT.HORIZONTAL));
//		if(cfg ==null)
//			cfg = new Config("paramCfg");
		
		
		Menu menu = new Menu(shlVer, SWT.BAR);
		shlVer.setMenuBar(menu);
		
		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setText("\u6587\u4EF6");
		
		Menu menu_1 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_1);
		
		MenuItem mntmNewItem = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem.setImage(SWTResourceManager.getImage(GUIMain.class, "/icon/hipda.png"));
		mntmNewItem.setText("\u65B0\u5EFA\u6316\u77FF\u53C2\u6570");
		
		MenuItem mntmNewItem_1 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_1.setImage(SWTResourceManager.getImage(GUIMain.class, "/icon/ic_holo_ics.png"));
		mntmNewItem_1.setText("\u52A0\u8F7D\u6316\u77FF\u53C2\u6570");
		
		MenuItem menuItem = new MenuItem(menu_1, SWT.NONE);
		menuItem.setImage(SWTResourceManager.getImage(GUIMain.class, "/icon/game_fj_wolf.png"));
		menuItem.setText("\u4FDD\u5B58\u53C2\u6570");
		
		MenuItem menuItem_2 = new MenuItem(menu_1, SWT.NONE);
		menuItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				saveOtherParam();
				
			}
		});
		menuItem_2.setImage(SWTResourceManager.getImage(GUIMain.class, "/icon/drawable.png"));
		menuItem_2.setText("\u53C2\u6570\u53E6\u5B58\u4E3A");
		
		MenuItem menuItem_1 = new MenuItem(menu_1, SWT.NONE);
		menuItem_1.setImage(SWTResourceManager.getImage(GUIMain.class, "/icon/get_browser.png"));
		menuItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlVer.dispose();
			}
		});
		menuItem_1.setText("\u9000\u51FA");
		
		MenuItem mntmNewSubmenu_1 = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu_1.setText("\u8FD0\u884C");
		
		Menu menu_2 = new Menu(mntmNewSubmenu_1);
		mntmNewSubmenu_1.setMenu(menu_2);
		
		MenuItem mntmNewItem_3 = new MenuItem(menu_2, SWT.NONE);
		mntmNewItem_3.setImage(SWTResourceManager.getImage(GUIMain.class, "/icon/game_falloutshelter.png"));
		mntmNewItem_3.setText("\u5F00\u59CB\u6316\u77FF");
		
		
		// 停止挖矿开关
		MenuItem menuItem_4 = new MenuItem(menu_2, SWT.NONE);
		menuItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					CMDUtil.PROCESS.destroy();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				// 刷新界面
				try {
					composite_1.dispose();
					composite.layout();
				} catch (Exception e1) {
						e1.printStackTrace();
				}
				
				
			
			}
		});
		menuItem_4.setText("\u505C\u6B62\u6316\u77FF");
		
		MenuItem mntmNewSubmenu_3 = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu_3.setText("\u5E2E\u52A9");
		
		Menu menu_4 = new Menu(mntmNewSubmenu_3);
		mntmNewSubmenu_3.setMenu(menu_4);
		
		MenuItem menuItem_3 = new MenuItem(menu_4, SWT.NONE);
		menuItem_3.setImage(SWTResourceManager.getImage(GUIMain.class, "/icon/ic_holo_ics.png"));
		menuItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		menuItem_3.setText("\u5E2E\u52A9\u6587\u6863");
		
		tabFolder = new TabFolder(shlVer, SWT.NONE);
		
		tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("\u540D\u79F0/\u5730\u5740/\u94B1\u5305");
		
		createComposite(tabFolder,tabItem);
		
		TabItem tabItem_3 = new TabItem(tabFolder, SWT.NONE);
		tabItem_3.setText("\u8BBE\u7F6E");
		
		tabItem_1 = new TabItem(tabFolder, SWT.NONE);
		tabItem_1.setText("\u8BBE\u5907/\u6316\u77FF \u72B6\u6001");
		
		createComposite2(tabFolder,tabItem_1);
		
		TabItem tabItem_2 = new TabItem(tabFolder, SWT.NONE);
		tabItem_2.setText("\u5176\u4ED6\u4FE1\u606F");
		
		CBanner banner = new CBanner(tabFolder, SWT.NONE);
		tabItem_2.setControl(banner);
		
		// 初始化
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
//					cfg = new Config("paramCfg");
					composite_1.dispose();
					createComposite2(tabFolder,tabItem_1);
					// 必须加这个，否则刷新完有白板
					composite.layout();
					composite_1.layout();
//					CFGPath = null;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// 读取参数文件
				mntmNewItem_1.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						JFileChooser jf = new JFileChooser();
						
						// 设置默认路径
						String defalutDirectory = "./";
						// 默认文件名
						jf.setCurrentDirectory(new File(defalutDirectory));
				        jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
				        jf.showDialog(null,null);
				        File fi = jf.getSelectedFile();
				        if(fi == null)
				        {
				        	return;
				        }
//				        CFGPath = fi.getAbsolutePath();
				        // 读取配置文件并重载页面
				        try {
//							cfg = new Config("",CFGPath);
							composite_1.dispose();
							createComposite2(tabFolder,tabItem_1);
							// 必须加这个，否则刷新完有白板
							composite.layout();
							composite_1.layout();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				        
					}
				});
		// 保存参数文件
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveParam();
				int style = SWT.APPLICATION_MODAL | SWT.OK;
				MessageBox messageBox = new MessageBox(shlVer, style);
				messageBox.setText("提示");
				messageBox.setMessage("保存成功");
				e.doit = messageBox.open() == SWT.YES;
			}
		});
		
		// 保存并执行文件 
		// 同时启动日志监视器
		
		
		/**
		 * 启动程序
		 */
		mntmNewItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 启动日志监视器
				if(!LogView.POWERUP) {
				LogView view = new LogView();
				final File tmpLogFile = new File("C:\\qskg\\log\\20210329_112409_ETH_log.txt");
				if(!tmpLogFile.exists())
					try {
						tmpLogFile.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				view.lastTimeFileSize = 0;
				try {
					view.realtimeShowLog(tmpLogFile);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				}
				// 根据选择的内核类型加载启动脚本
				batstr = loadParam();
				
				// 启动挖矿内核程序
				new Thread() {// 线程操作
					public void run() {
							try {
								CMDUtil.excuteBatFileWithNewWindow(batstr, false);
//								adb.setText(string);
							} catch (Exception e) {
							}
					}
				}.start();
				
				// 刷新界面
				try {
					// 设置时间 ，格式化输出时间
					composite_1.dispose();
					createComposite2(tabFolder, tabItem_1);
					// 必须加这个，否则刷新完有白板
					composite.layout();
					composite_1.layout();
					new Thread() {// 线程操作
						public void run() {
							while (true) {
								try {
									// 对Label进行实时刷新，需要加上这句
									composite_1.getDisplay().asyncExec(new Runnable() {
										@Override
										public void run() {
											// 设置时间 ，格式化输出时间
											refreshComposite2();
										}
									});
									Thread.sleep(3000);// 每隔一秒刷新一次
								} catch (Exception e) {
								}
							}
						}
					}.start();

				} catch (Exception e1) {
						e1.printStackTrace();
				}
				
				
//				// 如果没有保存过则默认弹出选择保存位置，否则直接保存
//				saveParam();
//				DocxUtil d = new DocxUtil();
//				XlsxUtil x = new XlsxUtil();
//				try {
//					d.dealDoc(text.getText(), text_1.getText(), cfg);
//					x.dealXls(text.getText(), text_1.getText(), cfg);
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				int style = SWT.APPLICATION_MODAL | SWT.OK;
//				MessageBox messageBox = new MessageBox(shlVer, style);
//				messageBox.setText("提示");
//				messageBox.setMessage("完成文档处理");
//				e.doit = messageBox.open() == SWT.YES;
			}

			
			/**
			 * 根据录入内容创建执行命令
			 * @return
			 */
			private String loadParam() {
				// TODO Auto-generated method stub
//				String minierType = text.getText();
//				String bz=combo_bz.getText();
//				String worker=text_kg.getText();
//				String wallet=text_wallet.getText();
//				String pool=combo_kc.getText();
//				
				String minierType = "C:\\qskg\\exts\\nbminer_37_1\\";
				String pool = "cn.sparkpool.com:3333";
				String wallet = "sp_lucksun888";
				String worker = "002";
				
				// 不同的内核格式不同，需要重新适配
				File f = new File(minierType+"start_eth_byma.bat");
				try {
				 
			          if (!f.exists()) {
			               f.createNewFile();
			           }
			           FileWriter fw = new FileWriter(f.getAbsoluteFile());
			           BufferedWriter bw = new BufferedWriter(fw);
			           String wr = minierType+"nbminer -a ethash -o stratum+tcp://"+pool+" -u "+ wallet+"."+ worker+ " -log-file " + "\"C:\\qskg\\log\\20210329_112409_ETH_log.txt\"";
			           System.out.println(wr);
			           bw.write(wr);//换行符，等价于 = bw.newLine();
			           bw.close();
			       } catch (IOException e) {
			           e.printStackTrace();
			       }
				
				return f.getAbsolutePath();
			}
		});
	}

	protected void saveParam() {
//		// TODO Auto-generated method stub
//		
//		// 读取变量页面的数据，生成map传给保存组件				
//		HashMap m = new HashMap();
//		TableItem[] items = table.getItems();
//		for (int i = 0; i < items.length; i++) {
//				m.put(items[i].getText(0), items[i].getText(1));
//		}				
//		// 如果没有保存过则默认弹出选择保存位置，否则直接保存
//		if(CFGPath!=null) {
//			cfg.saveXml(m,CFGPath);
//		}else {
//			JFileChooser jf = new JFileChooser();
//			// 设置默认路径
//			String defalutDirectory = "./";
//			// 默认文件名
//			String defaultFilename = "newFile.xml";
//			jf.setCurrentDirectory(new File(defalutDirectory));
//			jf.setSelectedFile(new File(defaultFilename));
//			jf.setFileFilter(new FileNameExtensionFilter("XML", new String[]{".xml"}));
//	        jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
//	        jf.showSaveDialog(null);
//	        File fi = jf.getSelectedFile();
//	        if(fi ==null)return;
//	        try {
//	        	CFGPath = cfg.saveXml(m,fi.getAbsolutePath());
//				cfg = new Config("",CFGPath);
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}				
	
	}
	
	
	protected void saveOtherParam() {
		// TODO Auto-generated method stub
		
		// 读取变量页面的数据，生成map传给保存组件				
		HashMap m = new HashMap();
		TableItem[] items = table.getItems();
		for (int i = 0; i < items.length; i++) {
				m.put(items[i].getText(0), items[i].getText(1));
		}				
		// 如果没有保存过则默认弹出选择保存位置，否则直接保存
		
			JFileChooser jf = new JFileChooser();
			// 设置默认路径
			String defalutDirectory = "./";
			// 默认文件名
			String defaultFilename = "newFile.xml";
			jf.setCurrentDirectory(new File(defalutDirectory));
			jf.setSelectedFile(new File(defaultFilename));
			jf.setFileFilter(new FileNameExtensionFilter("XML", new String[]{".xml"}));
	        jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        jf.showSaveDialog(null);
	        File fi = jf.getSelectedFile();
	        if(fi ==null)return;
	        try {
//	        	CFGPath = cfg.saveXml(m,fi.getAbsolutePath());
//				cfg = new Config("",CFGPath);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
						
	
	}
	
	private void refreshComposite2() {
		LogViewBOList LVBOList = new LogViewBOList(TransLog.GPUM);		
		tableViewer.setInput(LVBOList.getLVBoList());
	}
	

	private void createComposite2(TabFolder tabFolder,TabItem tabItem_1) {
			// TODO Auto-generated method stub
			composite_1 = new Composite(tabFolder, SWT.NONE);
			tabItem_1.setControl(composite_1);
			composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
			
			tableViewer = new TableViewer(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
			table = tableViewer.getTable();
			table.setToolTipText("");
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			
			LogViewBOList LVBOList = new LogViewBOList(TransLog.GPUM);		
			
			// 设备序号列 ----start----------------------------------------------------------------------
			TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
			TableColumn tblclmnColumn = tableViewerColumn.getColumn();
			tblclmnColumn.setWidth(65);
			tblclmnColumn.setText("\u8BBE\u5907\u5E8F\u53F7");
			
			
			// 设备型号列 ----start----------------------------------------------------------------------
			TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
			TableColumn tblclmnColumn_1 = tableViewerColumn_1.getColumn();
			tblclmnColumn_1.setWidth(85);
			tblclmnColumn_1.setText("\u8BBE\u5907\u578B\u53F7/\u540D\u79F0");
			
			
			// 算力			
			TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
			TableColumn tableColumn = tableViewerColumn_2.getColumn();
			tableColumn.setWidth(89);
			tableColumn.setText("\u7B97\u529B");
			
			// 核心频率
			TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(tableViewer, SWT.NONE);
			TableColumn tableColumn_7 = tableViewerColumn_9.getColumn();
			tableColumn_7.setWidth(100);
			tableColumn_7.setText("\u6838\u5FC3\u9891\u7387");
			
			//内存频率
			TableViewerColumn tableViewerColumn_10 = new TableViewerColumn(tableViewer, SWT.NONE);
			TableColumn tableColumn_8 = tableViewerColumn_10.getColumn();
			tableColumn_8.setWidth(100);
			tableColumn_8.setText("\u663E\u5B58\u9891\u7387");
			
			// 温度
			TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
			TableColumn tableColumn_1 = tableViewerColumn_3.getColumn();
			tableColumn_1.setWidth(100);
			tableColumn_1.setText("\u6838\u5FC3\u6E29\u5EA6/\u663E\u5B58\u6E29\u5EA6");
			
			// 风扇
			TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewer, SWT.NONE);
			TableColumn tableColumn_2 = tableViewerColumn_4.getColumn();
			tableColumn_2.setWidth(62);
			tableColumn_2.setText("\u98CE\u6247\u8F6C\u901F");
			
			// 功率
			TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(tableViewer, SWT.NONE);
			TableColumn tableColumn_3 = tableViewerColumn_5.getColumn();
			tableColumn_3.setWidth(61);
			tableColumn_3.setText("\u529F\u8017");
			tableViewerColumn_5.setEditingSupport(new TableViewerEditingSupport(tableViewer, 6, LVBOList.getLVBoList())); 
			
			// 接受
			TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(tableViewer, SWT.NONE);
			TableColumn tableColumn_4 = tableViewerColumn_6.getColumn();
			tableColumn_4.setWidth(58);
			tableColumn_4.setText("\u63A5\u53D7");
			
			// 拒绝
			TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(tableViewer, SWT.NONE);
			TableColumn tableColumn_5 = tableViewerColumn_7.getColumn();
			tableColumn_5.setWidth(64);
			tableColumn_5.setText("\u62D2\u7EDD");
			
			// 无效列
			TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(tableViewer, SWT.NONE);
			TableColumn tableColumn_6 = tableViewerColumn_8.getColumn();
			tableColumn_6.setWidth(61);
			tableColumn_6.setText("\u65E0\u6548");
			
			tableViewer.setContentProvider(new TableViewerContentProvider());
			tableViewer.setLabelProvider(new TableColumnProvider());
			
			tableViewer.setInput(LVBOList.getLVBoList());
			
		
	}

	private void createComposite(TabFolder tabFolder,TabItem tabItem) {
		composite = new Composite(tabFolder, SWT.NONE);
		composite.setBackgroundImage(SWTResourceManager.getImage(GUIMain.class, "/icon/bg.png"));
		tabItem.setControl(composite);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		Label label = new Label(composite, SWT.NONE);
		label.setAlignment(SWT.CENTER);
		label.setText("\u9009\u62E9\u5185\u6838");
		
		text = new Text(composite, SWT.BORDER);
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				JFileChooser jf = new JFileChooser();
		        jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        jf.showDialog(null,null);
		        File fi = jf.getSelectedFile();
		        if(fi==null)return;
		        String f = fi.getAbsolutePath();
		        text.setText(f);
			}
		});
		btnNewButton.setText("...");
		
		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setAlignment(SWT.RIGHT);
		label_1.setText("\u5E01\u79CD");
		// 币种
		combo_bz = new Combo(composite, SWT.NONE);
		combo_bz.setItems(new String[] { "eth", "btc"});
		
		Label label_2 = new Label(composite, SWT.NONE);
		label_2.setAlignment(SWT.RIGHT);
		label_2.setText("\u77FF\u5DE5\u540D\u79F0");
		
		text_kg = new Text(composite, SWT.BORDER);
		
		Label label_3 = new Label(composite, SWT.NONE);
		label_3.setAlignment(SWT.RIGHT);
		label_3.setText("\u94B1\u5305");
		
		text_wallet = new Text(composite, SWT.BORDER);
		
		Label label_4 = new Label(composite, SWT.NONE);
		label_4.setAlignment(SWT.RIGHT);
		label_4.setText("\u77FF\u6C60");
		// 矿池
		combo_kc = new Combo(composite, SWT.NONE);
		combo_kc.setItems(new String[] { "cn.sparkpool.com:3333"});
		}
	
	private TableViewerColumn[] getTableViewerColumns(TableViewer tableViewer) {
	    TableColumn[] columns = tableViewer.getTable().getColumns();
	    TableViewerColumn[] viewerColumns = new TableViewerColumn[columns.length];
	    for (int i = 0; i < columns.length; i++) {
	        TableColumn tableColumn = columns[i];
	        viewerColumns[i] = (TableViewerColumn) tableColumn.getData(Policy.JFACE + ".columnViewer"); //$NON-NLS-1$
	    }
	    return viewerColumns;
	}
}
