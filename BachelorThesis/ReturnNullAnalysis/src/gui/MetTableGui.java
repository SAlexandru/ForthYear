package gui;

import listeners.TableListener;
import models.MetModel;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class MetTableGui extends AbstractTableGui
	{

	public MetTableGui(Composite parent, TableListener listener)
		{
		super(parent,listener);
		createColumns();
		}

	@Override
	protected void createColumns()
		{
		TableViewerColumn nameColumn=new TableViewerColumn(viewer,SWT.NONE);
		TableViewerColumn declaringClassColumn=new TableViewerColumn(viewer,SWT.NONE);	
		TableViewerColumn wrappingMethodsColumn=new TableViewerColumn(viewer,SWT.NONE);
		nameColumn.getColumn().setText("Tested Method");
		nameColumn.getColumn().setWidth(100);
		nameColumn.setLabelProvider(new ColumnLabelProvider(){
		 @Override
		  public String getText(Object element) {
		   return ((MetModel)(element)).getName();
		  }
		});		
		
		declaringClassColumn.getColumn().setText("Declaring Class");
		declaringClassColumn.getColumn().setWidth(100);
		declaringClassColumn.setLabelProvider(new ColumnLabelProvider(){
		 @Override
		  public String getText(Object element) {
		   return ((MetModel)(element)).getDeclaringClass();
		  }
		});	
		
		wrappingMethodsColumn.getColumn().setText("Wrapping Methods Number");
		wrappingMethodsColumn.getColumn().setWidth(150);
		wrappingMethodsColumn.setLabelProvider(new ColumnLabelProvider(){
		 @Override
		  public String getText(Object element) {
		   return ((MetModel)(element)).getOuterMethods().size()+"";
		  }
		});	
		}
	}
