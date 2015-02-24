package returnnullanalysis;


import gui.AbstractTableGui;
import gui.MetTableGui;
import gui.VarTableGui;
import listeners.ListViewListener;
import listeners.MetTableListener;
import listeners.VarTableListener;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.packageview.PackageFragmentRootContainer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

public class ReturnNullView extends ViewPart 
{
	public static final String ID = "returnnullanalysis.ReturnNullView";
	private AbstractTableGui varTableViewer,metTableViewer;
	public static ListViewer listViewer;
	private IJavaProject javaProject; //selected project
	private ViewContentProvider contentProvider=new ViewContentProvider();
	private Refactoring refactoring=new Refactoring();
	private IProject oldProject;
	private ISelectionListener listener = new ISelectionListener() 
		{
		public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) 
			{
			contentProvider.setViewSite(getViewSite());
			if (sourcepart != ReturnNullView.this) 
				{
				IProject project=getProject(sourcepart, selection);
			    if (project!=null)	//change view only when the user clicks on a project
			    	{
			    	if (oldProject==project)
			    		return;
			    	Pile.getInstance().clear();
					oldProject=project;
				    javaProject=JavaCore.create(project);  
				    contentProvider.setJavaProject(javaProject);
				    try
						{
						contentProvider.showView();
						varTableViewer.getViewer().setInput(Pile.getInstance().getVariables());
					    metTableViewer.getViewer().setInput(Pile.getInstance().getMethods());
						} 
				    catch (JavaModelException e)
						{
						e.printStackTrace();
						}
			    	}
				}
			}
		};

	//returns the project that is clicked
	private IProject getProject(IWorkbenchPart sourcepart, ISelection selection) 
		{
		IProject project=null;
		if(selection instanceof IStructuredSelection)
			{
			Object element = ((IStructuredSelection)selection).getFirstElement();
			if (element instanceof IResource) 
				{
				project=  ((IResource)element).getProject();
				} 
			else 
				if (element instanceof PackageFragmentRootContainer) 
					{
					IJavaProject jProject = ((PackageFragmentRootContainer)element).getJavaProject();
					project =  jProject.getProject();
					} 
				else if (element instanceof IJavaElement) 
						{
						IJavaProject jProject= ((IJavaElement)element).getJavaProject();
						project = jProject.getProject();
						}
					else
						return null;
			}
		return project;
		}
	
	public void createPartControl(final Composite parent) 
		{
		parent.setLayout(new FillLayout());
		varTableViewer=new VarTableGui(parent,new VarTableListener());
		metTableViewer=new MetTableGui(parent,new MetTableListener());
		listViewer=new ListViewer(parent);
		listViewer.addSelectionChangedListener(new ListViewListener(parent,listViewer));
		
		
		varTableViewer.getViewer().setContentProvider(new ArrayContentProvider());
		varTableViewer.getViewer().setInput(Pile.getInstance().getVariables());
		metTableViewer.getViewer().setContentProvider(new ArrayContentProvider());
		metTableViewer.getViewer().setInput(Pile.getInstance().getVariables());

		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(listener);
		// Create the help context id for the viewer's control
		//PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "TreePlugin.viewer");
		}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus()
		{
		varTableViewer.getViewer().getControl().setFocus();
		}
	
	
	

}