package lrg.patrools.actions;

import java.lang.reflect.InvocationTargetException;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.managers.EntityTypeManager;
import lrg.insider.visualizations.AbstractVisualizationWithProgressMonitor;
import lrg.insider.visualizations.MonitorCanceledException;
import lrg.jMondrian.figures.Figure;
import lrg.jMondrian.view.ViewRendererInterface;

import lrg.patrools.util.UselessOperation;
import lrg.patrools.visualizations.LevelOfAbstractionBuilder;
import lrg.patrools.visualizations.view.PatroolsViewRenderer;
import lrg.patrools.visualizations.view.PatroolsVisualizationView;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import plugins.ModelElement;
import plugins.Wrapper;

public class LevelOfAbstractionAction extends AbstractVisualizationWithProgressMonitor implements IRunnableWithProgress, IObjectActionDelegate {

	//IObjectdelegate
	private ISelection theSelection;
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {}

	public void selectionChanged(IAction action, ISelection selection) {
		theSelection = selection;
	}

	public void run(IAction action) {
		if (theSelection instanceof IStructuredSelection){
			IType type = (IType )((IStructuredSelection)theSelection).getFirstElement();
			Wrapper.forceClearAll();
			Wrapper wrappedType = Wrapper.createInstance(ModelElement.createInstance(type));
			wrappedType.setEntityType(EntityTypeManager.getEntityTypeForName("class"));
			try {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				page.showView("patrools.view");
				PatroolsVisualizationView view = (PatroolsVisualizationView)page.findView("patrools.view");
				view.refresh(wrappedType, this, "Type Highlighting for ");
				page.showView("patrools.view");
			} catch(PartInitException e) {
				assert false;
			}
		} else {
			assert false;
		}
	}
	
	//AbstractVisualizationWithProgressMonitor
	
	private AbstractEntityInterface entity;
	private PatroolsViewRenderer renderer;
	
	public LevelOfAbstractionAction() {
		super("LA", "Level of Abstraction", "class");
	}

	@Override
	public ViewRendererInterface createRenderer(AbstractEntityInterface entity) {
		this.entity = entity;
		ProgressMonitorDialog progressMonitor = new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		try {
			progressMonitor.run(true, true, this);
		} catch (InvocationTargetException e) {
			if(e.getCause() instanceof UselessOperation) {
				MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Patrools",e.getCause().getMessage());				
			} else {
				e.printStackTrace();				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return renderer;
	}

	@Override
	public String getContextHelp() {
		return null;
	}

	@Override
	public String getHelpPath() {
		return null;
	}

	public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		try {
			LevelOfAbstractionBuilder b = new LevelOfAbstractionBuilder(); 
			b.buildFigure(entity, monitor);
			Figure f = b.getFigure();
			renderer = new PatroolsViewRenderer();
			f.renderOn(renderer);
		} catch (MonitorCanceledException e) {}
		finally {
			monitor.done();
		}
	}
}
