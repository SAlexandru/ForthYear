package lrg.patrools.visualizations.view;

import java.util.ArrayList;

import lrg.insider.visualizations.AbstractVisualizationWithProgressMonitor;
import lrg.patrools.init.Activator;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.ViewPart;

import plugins.Wrapper;
import plugins.actions.HelpAction;
import view.IStatusLineUpdater;

public class PatroolsVisualizationView extends ViewPart implements IStatusLineUpdater{

	private FigureCanvas canvas;	
	private PatroolsViewRenderer renderer;
	private PatroolsViewRenderer quickViewRenderer=null;//needed to fix a stupid bug
	private ArrayList<PatroolsViewRenderer> history=new ArrayList<PatroolsViewRenderer>();
	private IAction saveAction;
	private Action zoomInAction;
	private Action zoomOutAction;
	private Action backAction;
	private Composite parent;
	private HelpAction helpAction;

	public PatroolsVisualizationView() {
		super();

	}

	@Override
	protected void setContentDescription(String description){
		super.setContentDescription(description);
	}

	public void refresh(Wrapper wrapper, AbstractVisualizationWithProgressMonitor abstractVizualization, String rendererName){
		PatroolsViewRenderer aRenderer = (PatroolsViewRenderer)abstractVizualization.createRenderer(wrapper);
		if (aRenderer != null){
			helpAction.setContextHelp( abstractVizualization.getHelpPath());
			renderer = aRenderer;
			//renderer.fitToWindow(canvas);
			renderer.refresh();
			openVisualization();
			
		}
	}

	public void refresh(PatroolsViewRenderer viewRenderer, String rendererName, String helpPath){
		if (viewRenderer != null){
			helpAction.setContextHelp(helpPath);
			renderer = viewRenderer;
			renderer.fitToWindow(canvas);
			renderer.refresh();
			openVisualization();
		}
	}

	public void refresh(String helpPath, PatroolsViewRenderer copy, PatroolsViewRenderer viewRenderer){
		PatroolsViewRenderer aRenderer = copy;
		if (aRenderer == null)
			return;
		renderer = aRenderer;
		if(quickViewRenderer == null || !viewRenderer.getFigure().equals(quickViewRenderer.getFigure())){
			renderer.fitToWindow(canvas);
		}
		helpAction.setContextHelp(helpPath);
		quickViewRenderer = viewRenderer;
		openVisualization();
	}

	private void openVisualization() {
		if(!renderer.getContents().equals(canvas.getContents())){
			renderer.create(canvas, true, true, "patrools.view");
			history.add(renderer);
			canvas.setContents(renderer.getContents());
			canvas.setVisible(true);
			Display.getDefault().asyncExec(new Runnable(){
				public void run() {
					String title = renderer.getTitle();
					if (title != null)
						updateStatusText(title);
				}

			});
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		createCanvas(parent);
		createActions();
		createToolbar();
		activateContext();
		updateStatusText(" ");
	}

	private void activateContext() {
		IContextService contextService = (IContextService) getSite().getService(IContextService.class);
		contextService.activateContext("patrools.view");
	}

	private void createCanvas(Composite parent) {
		this.canvas = new FigureCanvas(parent);
		canvas.setSize(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		canvas.setBackground(ColorConstants.white);

		addF2Listener();
		addMouseListener();
	}

	private void addMouseListener() {
		canvas.addMouseListener(new MouseListener(){

			public void mouseDoubleClick(MouseEvent e) {
				renderer.cleanup();
			}

			public void mouseDown(MouseEvent e) {
				renderer.cleanup();
			}

			public void mouseUp(MouseEvent e) {
			}

		});
	}

	private void addF2Listener() {
		canvas.addKeyListener(new KeyListener(){

			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				if (e.keyCode == SWT.F2){
					f2Pressed();
				}
			}
			public void keyReleased(org.eclipse.swt.events.KeyEvent e) {
			}
		});

	}

	private void createActions() {
		saveAction = new Action("Save") {
			public void run() { 
				save();
			}
		};
		saveAction.setImageDescriptor(ImageDescriptor.createFromFile(null,Activator.getIconPath() + "save_edit.gif"));

		zoomInAction = new Action("Zoom In") {
			public void run() { 
				zoomIn();
			}
		};
		zoomInAction.setImageDescriptor(ImageDescriptor.createFromFile(null,Activator.getIconPath()+ "zoomIn.png"));

		zoomOutAction = new Action("Zoom Out") {
			public void run() { 
				zoomOut();
			}
		};
		zoomOutAction.setImageDescriptor(ImageDescriptor.createFromFile(null,Activator.getIconPath() + "zoomOut.png"));

		backAction = new Action("Back") {
			public void run() { 
				back();
			}
		};
		backAction.setImageDescriptor(ImageDescriptor.createFromFile(null,Activator.getIconPath() + "back.gif"));

		addHelpButtonToToolBar(); 
	}

	private void createToolbar() {
		IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
		mgr.add(saveAction);
		mgr.add(zoomInAction);
		mgr.add(zoomOutAction);
		mgr.add(backAction);
	}

	@Override
	public void setFocus() {
		if (canvas != null && renderer!=null){
			canvas.setFocus();
		}
	}

	public void f2Pressed() {
		//if (renderer != null)
			//renderer.showScrollableInfoView();
	}

	private void addHelpButtonToToolBar() {
		helpAction = new HelpAction();
		helpAction.setToolTipText("Open this visualisation's help");
		helpAction.setImageDescriptor(ImageDescriptor.createFromFile(null,Activator.getIconPath() + "help.gif"));
		getViewSite().getActionBars().getToolBarManager().add(helpAction);
	}

	public void zoomIn() {
		renderer.zoomIn();
	}

	public void zoomOut() {
		renderer.zoomOut();
	}

	public void back() {
		int size=history.size();
		if (size > 1){
			PatroolsViewRenderer vr = history.get(size-2);
			this.canvas.setContents(vr.getContents());
			this.renderer=vr;
			history.remove(size-1);
		}
	}

	public void save() {
		renderer.save();
	}

	public Shell getShell() {
		return parent.getShell();
	}

	public void updateStatusText(String newText){
		setContentDescription(newText);
	}

}
