package lrg.patrools.visualizations.view;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.jMondrian.view.ShapeElementFactory;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PartInitException;

import plugins.Wrapper;

public class PatroolsRightClickHandler {
	
	protected Menu popup;
	protected final MouseEvent e;
	protected final ShapeElementFactory factory;
	protected Point p;
	protected Control control;
	protected final PatroolsViewRenderer theRenderer;

	public PatroolsRightClickHandler(MouseEvent e, ShapeElementFactory factory,
			PatroolsViewRenderer theRenderer, Control control) {
		this.e = e;
		this.factory = factory;
		this.theRenderer = theRenderer;
		this.control = control;
	}

	public void handleRightClick(){
		popup = new Menu(control);
		Object ent = factory.findEntity(e.x,e.y);
		if(ent != null && ent instanceof Wrapper) {
			final Wrapper wrapper = (Wrapper)ent;
			MenuItem openDeclaration = new MenuItem(popup,SWT.PUSH);
			openDeclaration.setText("Go to declaration");
			openDeclaration.addSelectionListener(new SelectionListener(){
				public void widgetDefaultSelected(SelectionEvent e) {
				}
				public void widgetSelected(SelectionEvent e) {
					theRenderer.cleanup();
					popup.setVisible(false);
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							try {
								JavaUI.openInEditor((IJavaElement) wrapper.getElement());
							} catch (PartInitException e) {
								e.printStackTrace();
							} catch (JavaModelException e) {
								e.printStackTrace();
							}
						}
					});
				}
			});
			addPopupMenuItems(wrapper,popup);
			Display.getDefault().syncExec(new Runnable(){
				public void run() {
					p =  Display.getDefault().getCursorLocation();
				}
			});
			if (p!=null) {
				popup.setLocation(p.x, p.y);
			}   	
			popup.setVisible(true);
		}
	}
	
	protected void addPopupMenuItems(AbstractEntityInterface ent, Menu menu) { 
		return;
	};

	public void hide() {
		popup.setVisible(false);
	}

}
