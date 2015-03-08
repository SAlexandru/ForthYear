package lrg.insiderviewer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.EntityType;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.managers.EntityTypeManager;
import lrg.jMondrian.figures.Figure;
import lrg.jMondrian.view.OrganicViewRenderer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ITableLabelProvider;

import plugins.Wrapper;

public class InsiderView extends ViewPart {

	public InsiderView() {}

	private static int MAX = 12;
	
	private TableViewer viewer;
		
	private Stack<List<AbstractEntityInterface>> data = new Stack<List<AbstractEntityInterface>>();
	
	private Stack<List<String>> prop = new Stack<List<String>>();

	private class InsiderContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if(data.isEmpty()) {
				return new Object[] {};
			} else {
				return data.peek().toArray();
			}
		}

		@Override
		public void dispose() {}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
	
	};
		
	private class InsiderLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if(prop.isEmpty() || columnIndex >= prop.peek().size()) {
				return "";
			} else {
				return ((AbstractEntityInterface)element).getProperty(prop.peek().get(columnIndex)).toString();
			}
		}
		
	}
		
	private HashMap<String,Menu> types2menu = new HashMap<String,Menu>();

	private Composite parent;
	private boolean built = false;
	
	private void buildMenus(Composite parent) {
		if(built) return;
		built = true;
		
		Listener views = new Listener() {
			@Override
			public void handleEvent(Event event) {
				int selection = viewer.getTable().getSelectionIndex();
				if(selection >= 0) {
					Figure f = data.peek().get(selection).getVisualization(((MenuItem)event.widget).getText());
					OrganicViewRenderer ovr = new OrganicViewRenderer();
					f.renderOn(ovr);
					ovr.show();
				}
			}			
		};

		Listener groups = new Listener() {
			@Override
			public void handleEvent(Event event) {
				int selection = viewer.getTable().getSelectionIndex();
				if(selection >= 0) {
					GroupEntity group = data.peek().get(selection).getGroup(((MenuItem)event.widget).getText());
					ArrayList<String> properties = new ArrayList<String>();
					properties.add("Name");
					data.push(group.distinct().getElements());
					prop.push(properties);
					if(group.size() == 0) {
						buildView(null);						
					} else {
						buildView(group.getElementAt(0).getEntityType().getName());
					}
				}
			}			
		};
		
		Listener propListener = new Listener() {
			public void handleEvent(Event event) {
				if(!prop.peek().contains(((MenuItem)event.widget).getText())) {
					viewer.getTable().getColumn(prop.peek().size()).setText(((MenuItem)event.widget).getText());
					prop.peek().add(((MenuItem)event.widget).getText());
				} else if(!((MenuItem)event.widget).getText().equals("Name")) {
					for(int i = 0; i < viewer.getTable().getColumnCount(); i++) {
						if(viewer.getTable().getColumn(i).getText().equals(((MenuItem)event.widget).getText())) {
							if(viewer.getTable().getSortColumn() == viewer.getTable().getColumn(i)) {
								viewer.getTable().setSortColumn(null);
							}
							for(int j = i; j < viewer.getTable().getColumnCount() - 1; j++) {
								viewer.getTable().getColumn(j).setText(viewer.getTable().getColumn(j+1).getText());
							}
							viewer.getTable().getColumn( viewer.getTable().getColumnCount()-1).setText("");
							break;
						}
					}
					prop.peek().remove(((MenuItem)event.widget).getText());
				}
				viewer.refresh();
			}
		};
		
		Listener filterListener = new Listener() {
			public void handleEvent(Event event) {
				ArrayList<AbstractEntityInterface> res = new ArrayList<AbstractEntityInterface>();
				for(AbstractEntityInterface entity : data.peek()) {
					if(((Boolean)((ResultEntity)entity.getProperty(((MenuItem)event.widget).getText())).getValue())) {
						res.add(entity);
					}
				}
				data.push(res);
				prop.push(prop.peek());
				if(data.peek().isEmpty()) {
					buildView(null);
				} else {
					buildView(data.peek().get(0).getEntityType().getName());					
				}
				viewer.refresh();
			}
		};

		for(EntityType eType : (Collection<EntityType>)EntityTypeManager.getAllTypes()) {
			Menu main = new Menu(parent);
			types2menu.put(eType.getName(),main);
			
			//Add property
			MenuItem propertyMenuItem = new MenuItem(main,SWT.CASCADE);
			propertyMenuItem.setText("Add property");
			Menu propertyMenu = new Menu(main); 
			propertyMenuItem.setMenu(propertyMenu);
			for(String gType : (List<String>)EntityTypeManager.getAllPropertiesForName(eType.getName())) {
				MenuItem item = new MenuItem(propertyMenu,SWT.NONE);
				item.setText(gType);
				item.addListener(SWT.Selection, propListener);
			}
			
			//Filters
			MenuItem filtersMenuItem = new MenuItem(main,SWT.CASCADE);
			filtersMenuItem.setText("Apply filter");
			Menu filterMenu = new Menu(main); 
			filtersMenuItem.setMenu(filterMenu);
			for(String gType : (List<String>)EntityTypeManager.getAllFiltersForName(eType.getName())) {
				MenuItem item = new MenuItem(filterMenu,SWT.NONE);
				item.setText(gType);
				item.addListener(SWT.Selection, filterListener);
			}
			
			new MenuItem(main, SWT.SEPARATOR);
			
			//Groups
			MenuItem groupsMenuItem = new MenuItem(main,SWT.CASCADE);
			groupsMenuItem.setText("Open group");
			Menu groupsMenu = new Menu(main); 
			groupsMenuItem.setEnabled(false);
			groupsMenuItem.setMenu(groupsMenu);
			for(String gType : (List<String>)EntityTypeManager.getAllGroupsForName(eType.getName())) {
				MenuItem item = new MenuItem(groupsMenu,SWT.NONE);
				item.setText(gType);
				item.addListener(SWT.Selection, groups);
			}

			new MenuItem(main, SWT.SEPARATOR);

			//Views
			MenuItem viewMenuItem = new MenuItem(main,SWT.CASCADE);
			viewMenuItem.setText("Open view");
			Menu viewMenu = new Menu(main); 
			viewMenuItem.setMenu(viewMenu);
			for(String gType : (List<String>)EntityTypeManager.getAllVisualizationsForName(eType.getName())) {
				MenuItem item = new MenuItem(viewMenu,SWT.NONE);
				item.setText(gType);
				item.addListener(SWT.Selection, views);
			}
		
		}		
	}
	
	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		
		IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
		mgr.add(new Action("Back") {
			public void run() {
				if(data.size() > 1) {
					prop.pop();
					data.pop();
					buildView(data.peek().get(0).getEntityType().getName());
				}
			}
		});
		mgr.add(new Action("Clear") {
			public void run() {
				data.clear();
				prop.clear();
				buildView(null);
				Wrapper.forceClearAll();
			}			
		});
		mgr.add(new Action("Save") {
			public void run() {
				if(prop.size() > 0 && data.size() > 0) {
					FileDialog fd = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.SAVE);
					String fileName = fd.open();
					if(fileName != null) {
						try {
							PrintStream ps = new PrintStream(new File(fileName));
							for(int i = 0; i < prop.peek().size(); i++) {
								String aProp = prop.peek().get(i);
								if(i == prop.peek().size() - 1) {
									ps.print(aProp);																		
								} else {
									ps.print(aProp + ",");									
								}
							}
							ps.println();
							for(AbstractEntityInterface anEntity : data.peek()) {
								for(int i = 0; i < prop.peek().size(); i++) {
									String aProp = prop.peek().get(i);
									if(i == prop.peek().size() - 1) {
										ps.print(anEntity.getProperty(aProp));																		
									} else {
										ps.print(anEntity.getProperty(aProp) + ",");								
									}
								}
								ps.println();
							}
							ps.flush();
							ps.close();
						} catch (FileNotFoundException e) {
							MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Patrools",e.getCause().getMessage());				
						}
					}
				}
			}
		});
		
		viewer = new TableViewer(parent,SWT.V_SCROLL|SWT.H_SCROLL| SWT.BORDER);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if(viewer.getTable().getMenu() != null) {
					if(viewer.getTable().getSelectionIndex() >= 0) {
						viewer.getTable().getMenu().getItem(3).setEnabled(true);					
					} else {
						viewer.getTable().getMenu().getItem(3).setEnabled(false);											
					}
				}
			}			
		});
		viewer.getTable().addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if(viewer.getTable().getSelectionIndex() >= 0) {
					AbstractEntityInterface entity = data.peek().get(viewer.getTable().getSelectionIndex());
					if(entity instanceof Wrapper) {
						if(((Wrapper)entity).getElement() instanceof IMethod || ((Wrapper)entity).getElement() instanceof IType) {
							try {
								JavaUI.openInEditor((IJavaElement) ((Wrapper)entity).getElement());
							} catch (PartInitException e1) {
								e1.printStackTrace();
							} catch (JavaModelException e1) {
								e1.printStackTrace();
							}							
						}
					}
				}
			}
			@Override
			public void mouseDown(MouseEvent e) {}

			@Override
			public void mouseUp(MouseEvent e) {}
		});
		
		for(int i = 0; i < MAX; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer,SWT.NONE);
			column.getColumn().setMoveable(true);
			column.getColumn().setResizable(true);
			column.getColumn().setText("");
			column.getColumn().setWidth(100);
			column.getColumn().addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					final TableColumn currentColumn = (TableColumn) event.widget;
					int dir = viewer.getTable().getSortDirection();
					if(viewer.getTable().getSortColumn() == currentColumn) {
						dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
					} else {
						dir = SWT.UP;
						viewer.getTable().setSortColumn(currentColumn);
					}
					final int multiply = (dir == SWT.UP) ? 1 : -1; 
					viewer.getTable().setSortDirection(dir);
					Collections.sort(data.peek(), new Comparator<AbstractEntityInterface>() {
						@Override
						public int compare(AbstractEntityInterface o1, AbstractEntityInterface o2) {
							ResultEntity r1 = o1.getProperty(currentColumn.getText());
							ResultEntity r2 = o2.getProperty(currentColumn.getText());
							if(r1.getValue() instanceof String) {
								return multiply * ((String)r1.getValue()).compareTo((String)r2.getValue());
							} else if(r1.getValue() instanceof Double) {
								return multiply * ((Double)r1.getValue()).compareTo((Double)r2.getValue());
							} else if(r1.getValue() instanceof Boolean) {
								return  multiply * ((Boolean)r1.getValue()).compareTo((Boolean)r2.getValue());								
							} else if(r1.getValue() instanceof Wrapper) {
								return multiply * ((Wrapper)r1.getValue()).toString().compareTo(((Wrapper)r2.getValue()).toString());								
							}
							return 0;
						}
					});
					viewer.refresh(true);
				}
			});
		}
		
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new InsiderContentProvider());
		viewer.setLabelProvider(new InsiderLabelProvider());
		viewer.setUseHashlookup(false);
		viewer.setInput(data);

	}
	
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	public void displayEntity(Wrapper entity) {
		ArrayList<AbstractEntityInterface> entities = new ArrayList<AbstractEntityInterface>();
		entities.add(entity);
		ArrayList<String> properties = new ArrayList<String>();
		properties.add("Name");
		try {
			data.push(entities);
			prop.push(properties);
			buildView(entity.getEntityType().getName());	
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private void buildView(String type) {
		buildMenus(parent);
		for(int i = 0; i < MAX; i++) {
			viewer.getTable().getColumn(i).setText("");
			viewer.getTable().getColumn(i).setWidth(100);
		}
		viewer.getTable().setMenu(types2menu.get(type));
		if(data.isEmpty() || data.peek().isEmpty()) {
			viewer.getTable().setMenu(null);			
		}
		for(int i = 0; !prop.isEmpty() && i < prop.peek().size(); i++) {
			viewer.getTable().getColumn(i).setText(prop.peek().get(i));
			viewer.getTable().getColumn(i).setWidth(100);
		}
		IStatusLineManager slm = getViewSite().getActionBars().getStatusLineManager();
		slm.setMessage("No. of entities:" + (data.isEmpty() ? 0 : data.peek().size()));
		viewer.getTable().setSortColumn(null);	
		viewer.getTable().setSortDirection(SWT.NONE);
		viewer.refresh(true);
	}
}
