package gui;

import listeners.TableListener;
import models.VarModel;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class VarTableGui extends AbstractTableGui
	{	
	public VarTableGui (Composite parent,TableListener listener)
		{
		super(parent,listener);
		createColumns();
		}
	
	@Override
	protected void createColumns()
		{
		TableViewerColumn nameColumn=new TableViewerColumn(viewer,SWT.NONE);
		TableViewerColumn declaringClassColumn=new TableViewerColumn(viewer,SWT.NONE);
		TableViewerColumn isFieldColumn=new TableViewerColumn(viewer,SWT.NONE);
		TableViewerColumn wrappingMethodsColumn=new TableViewerColumn(viewer,SWT.NONE);
		nameColumn.getColumn().setText("Tested Variable");
		nameColumn.getColumn().setWidth(100);
		nameColumn.setLabelProvider(new ColumnLabelProvider(){
		 @Override
		  public String getText(Object element) {
		   return ((VarModel)(element)).getName();
		  }
		});		
		
		declaringClassColumn.getColumn().setText("Declaring Class");
		declaringClassColumn.getColumn().setWidth(100);
		declaringClassColumn.setLabelProvider(new ColumnLabelProvider(){
		 @Override
		  public String getText(Object element) {
		   return ((VarModel)(element)).getDeclaringClass();
		  }
		});	
		
		isFieldColumn.getColumn().setText("Is Field?");
		isFieldColumn.getColumn().setWidth(60);
		isFieldColumn.setLabelProvider(new ColumnLabelProvider(){
		 @Override
		  public String getText(Object element) {
		   return ((VarModel)(element)).getIsField();
		  }
		});	
		
		wrappingMethodsColumn.getColumn().setText("Wrapping Methods Number");
		wrappingMethodsColumn.getColumn().setWidth(150);
		wrappingMethodsColumn.setLabelProvider(new ColumnLabelProvider(){
		 @Override
		  public String getText(Object element) {
		   return ((VarModel)(element)).getOuterMethods().size()+"";
		  }
		});			
		}
		
	public TableViewer getViewer()
		{
		return viewer;
		}

	}
