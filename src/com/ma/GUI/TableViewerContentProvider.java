package com.ma.GUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import com.ma.Bo.LogViewBO;


public class TableViewerContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		Object[] o;
		if (inputElement instanceof Collection<?>) {
			List<LogViewBO> s = new ArrayList<LogViewBO>();
			// 筛选出没有删除的列
			for (LogViewBO bo : (List<LogViewBO>) inputElement) {
				s.add(bo);
			}
			return s.toArray(new Object[0]);
		}
		return null;
	}
}
