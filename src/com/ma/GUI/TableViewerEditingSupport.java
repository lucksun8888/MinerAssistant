package com.ma.GUI;

import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;

import com.ma.Bo.ConfigBO;
import com.ma.Bo.LogViewBO;
import com.ma.Bo.LogViewBOList;

/** 
 * 定义表格编辑器(内部类) 
 * 
 */  
public class TableViewerEditingSupport extends EditingSupport {  
  
    int column;  
    private TableViewer columnViewer;  
    private CellEditor editor;  
    private List<LogViewBO> dbColumnList;  
      
    public TableViewerEditingSupport(ColumnViewer viewers, int column,final List<LogViewBO> dbColumnList) {  
        super(viewers);  
        this.dbColumnList = dbColumnList;  
        this.columnViewer = (TableViewer) viewers;
		//此处是创建控件的.
        switch (column) {  
        case 1:  
            editor = new TextCellEditor(columnViewer.getTable(), SWT.BORDER);  
            break;  
        case 2:  
            editor = new TextCellEditor(columnViewer.getTable(), SWT.BORDER);  
            break;  
        case 3:  
            editor = new TextCellEditor(columnViewer.getTable(), SWT.BORDER);  
            break;  
        case 4:  
            editor = new TextCellEditor(columnViewer.getTable(), SWT.BORDER);  
            break;  
        case 5:  
            editor = new TextCellEditor(columnViewer.getTable(), SWT.BORDER);  
            break;  
        case 6:  
            editor = new TextCellEditor(columnViewer.getTable(), SWT.BORDER);  
            break;  
        case 7:  
            editor = new TextCellEditor(columnViewer.getTable(), SWT.BORDER);  
            break;  
        case 8:  
            editor = new TextCellEditor(columnViewer.getTable(), SWT.BORDER);  
            break;  
        case 9:  
            editor = new TextCellEditor(columnViewer.getTable(), SWT.BORDER);  
            break;  
        case 10:  
            editor = new TextCellEditor(columnViewer.getTable(), SWT.BORDER);  
            break; 
        case 11:  
            editor = new TextCellEditor(columnViewer.getTable(), SWT.BORDER);  
            break; 
        
        }  
        this.column = column;  
    }  
  
    @Override  
    protected boolean canEdit(Object element) {  
        return false;  
    }  
  
    @Override  
    protected CellEditor getCellEditor(Object element) {  
        return null;  
    }  
  
    @Override  
    protected Object getValue(Object element) {  
//    	ConfigBO dbColumn = (ConfigBO) element;
//		//此处是点击控件时.
//        switch (column) {
//        case 1:  
//            return dbColumn.getKey();  
//        case 2:  
//            return dbColumn.getValue();  
//        }  
        return null;  
    }  
  
    @Override  
    protected void setValue(Object element, Object value) {  
    	ConfigBO dbColumn = (ConfigBO) element;  
        switch (column) {
        case 1:  
            dbColumn.setKey(value.toString());  
            break;  
        case 2:  
            dbColumn.setValue(value.toString());  
            break;
        case 3:  
            dbColumn.setValue(value.toString());  
            break;
        case 4:  
            dbColumn.setValue(value.toString());  
            break;
        case 5:  
            dbColumn.setValue(value.toString());  
            break;
        case 6:  
            dbColumn.setValue(value.toString());  
            break;
        case 7:  
            dbColumn.setValue(value.toString());  
            break;
        case 8:  
            dbColumn.setValue(value.toString());  
            break;
        case 9:  
            dbColumn.setValue(value.toString());  
            break;
        case 10:  
            dbColumn.setValue(value.toString());  
            break;
        case 11:  
            dbColumn.setValue(value.toString());  
            break;
        }  
        columnViewer.refresh(true);  
    }  
}  

