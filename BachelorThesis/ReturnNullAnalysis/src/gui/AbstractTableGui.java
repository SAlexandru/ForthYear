package gui;

import listeners.TableListener;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public abstract class AbstractTableGui
	{
	protected TableViewer viewer;
	
	protected abstract void createColumns();
	
	public TableViewer getViewer()
		{
		return viewer;
		}
	
	public AbstractTableGui (Composite parent,TableListener listener)
		{
		viewer=new TableViewer(parent,SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		Table table=viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		listener.setTable(table);
		table.addListener(SWT.Selection,listener);
		}
	}
