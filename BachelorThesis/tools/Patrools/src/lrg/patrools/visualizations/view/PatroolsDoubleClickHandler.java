package lrg.patrools.visualizations.view;


import lrg.jMondrian.view.ShapeElementFactory;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;

import plugins.Wrapper;

public class PatroolsDoubleClickHandler {
	
	private final MouseEvent e;
	private final ShapeElementFactory factory;

	public PatroolsDoubleClickHandler(MouseEvent e, ShapeElementFactory factory){
		this.e = e;
		this.factory = factory;
	}
	
	public void handleDoubleClick(){
		Object ent = factory.findEntity(e.x,e.y);
        if(ent != null && ent instanceof Wrapper) {
        	final Wrapper wrapper = (Wrapper)ent;
        	Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					try {
						Wrapper.clear();
						JavaUI.openInEditor((IJavaElement) wrapper.getElement());
					} catch (PartInitException e) {
						e.printStackTrace();
					} catch (JavaModelException e) {
						e.printStackTrace();
					}
				}

			});
        }
	}
}
