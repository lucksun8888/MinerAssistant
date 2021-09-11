package com.ma.GUI;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.ma.Bo.LogViewBO;

public class TableColumnProvider extends LabelProvider implements ITableLabelProvider {  
    /** 
     * 返回前面的图片 
     */  
    public Image getColumnImage(Object element, int columnIndex) {  
        Image iconImage = null;  
        iconImage = super.getImage(element);  
        return iconImage;  
    }  
  
    /** 
     * 返回用于显示数据的字符串 
     */  
    public String getColumnText(Object element, int columnIndex) {  
        String returnValue = "";  
        LogViewBO column = (LogViewBO) element; 
		//此处是现table里面的. 也就是你把值设置完成后的显示.最终的显示结果
        switch (columnIndex) {
        case 0:  
            returnValue = column.getID();  
            break;  
        case 1:  
            returnValue = column.getDevice();  
            break;
        case 2:  
            returnValue = column.getHashrate();  
            break; 
        case 3:  
            returnValue = column.getCClk();  
            break;  
        case 4:  
            returnValue = column.getGMClk();  
            break;      
        case 5:  
            returnValue = column.getTemp();  
            break;
        case 6:  
            returnValue = column.getFan();  
            break;  
        case 7:  
            returnValue = column.getPowr();  
            break;
        case 8:  
            returnValue = column.getAccept();  
            break;  
        case 9:  
            returnValue = column.getReject();  
            break;
        case 10:  
            returnValue = column.getInv();  
            break;  
       
        }  
        return returnValue;  
    }  
}  