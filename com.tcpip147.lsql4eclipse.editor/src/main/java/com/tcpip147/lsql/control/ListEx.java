package com.tcpip147.lsql.control;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ListEx {

	private TableViewer viewer;
	private Table table;

	public ListEx(Composite parent) {
		viewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE |SWT.V_SCROLL);
		table = viewer.getTable();
		table.setHeaderVisible(false);
		TableColumn column = new TableColumn(table, SWT.NONE);

		table.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				column.setWidth(table.getClientArea().width);
			}
		});
		
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}			
		});

		viewer.setLabelProvider(new StyledCellLabelProvider() {
			@Override
			protected void erase(Event e, Object element) {
			    if ((e.detail & SWT.FOCUSED) != 0) {
			        e.detail &= ~SWT.FOCUSED;
			    }
				if ((e.detail & SWT.SELECTED) != 0) {
					e.detail &= ~SWT.SELECTED;
					e.gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_LIST_SELECTION));
					e.gc.fillRectangle(e.getBounds());
				}
				super.erase(e, element);
			}

			@Override
			protected void measure(Event e, Object element) {
				e.detail &= ~SWT.FOCUSED;
				e.detail &= ~SWT.SELECTED;
				super.measure(e, element);
			}

			@Override
			protected void paint(Event e, Object element) {
				e.detail &= ~SWT.FOCUSED;
				e.detail &= ~SWT.SELECTED;
				super.paint(e, element);
			}
		});
	}

	public void setLayoutData(GridData gridData) {
		table.setLayoutData(gridData);
	}

	public void add(String value) {
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(value);
	}
}
