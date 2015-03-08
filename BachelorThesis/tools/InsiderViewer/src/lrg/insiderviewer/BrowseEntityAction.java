package lrg.insiderviewer;

import lrg.common.abstractions.managers.EntityTypeManager;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import codepro.preferences.CodeProPreferencePage;

import plugins.ModelElement;
import plugins.Wrapper;

public class BrowseEntityAction implements IObjectActionDelegate {

	private ISelection theSelection;
	
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		theSelection = selection;
	}

	@Override
	public void run(IAction action) {
		if (theSelection instanceof IStructuredSelection){
			IJavaElement entity = (IJavaElement)((IStructuredSelection)theSelection).getFirstElement();
			String type;
			if(entity instanceof IType) {
				type = "class";
			} else if(entity instanceof IMethod) {
				type = "method";
			} else if(entity instanceof IField) {
				type = "attribute";
			} else if(entity instanceof IPackageFragment) {
				//If its depth > 3 (optional) -> package
				//Else -> subsystem
				if (entity.getElementName().split("\\.").length > CodeProPreferencePage.getNestingLevel()) {
					type = "package";					
				} else {
					//Skip subsystem coprepro type
					entity = entity.getJavaProject();
					type = "system";
				}
			} else if(entity instanceof IPackageFragmentRoot) {
				//Skip package root codepro type
				entity = entity.getJavaProject();
				type = "system";
			} else if(entity instanceof IJavaProject){
				type = "system";
			} else {
				return;
			}
			Wrapper wrapped = Wrapper.createInstance(ModelElement.createInstance(entity));
			wrapped.setEntityType(EntityTypeManager.getEntityTypeForName(type));
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try {
				page.showView("InsiderViewer.Insider");
			} catch (PartInitException e) {
				e.printStackTrace();
				return;
			}
			InsiderView view = (InsiderView)page.findView("InsiderViewer.Insider");
			view.displayEntity(wrapped);
		}
	}


}
