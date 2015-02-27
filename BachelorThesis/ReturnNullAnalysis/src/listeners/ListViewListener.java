package listeners;

import gui.ComparisonResultWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import returnnullanalysis.MethodsComparator;
import returnnullanalysis.Refactoring;
import utils.ListViewHelper;
import utils.Utils;

public final class ListViewListener implements ISelectionChangedListener {
	private ListViewer listViewer;
	private Composite parent;
	private Refactoring refactoring = new Refactoring();

	public ListViewListener(Composite parent, ListViewer listViewer) {
		this.listViewer = listViewer;
		this.parent = parent;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		Menu popupMenu = new Menu(parent);
		MenuItem viewEditorItem = new MenuItem(popupMenu, SWT.NONE);
		viewEditorItem.setText(Utils.VIEW_IN_EDITOR);
		MenuItem refactorItem = new MenuItem(popupMenu, SWT.NONE);
		refactorItem.setText(Utils.REFACTOR);
		MenuItem compareItem = new MenuItem(popupMenu, SWT.NONE);
		compareItem.setText(Utils.COMPARE);
		popupMenu.setVisible(true);

		viewEditorItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				IStructuredSelection selection = (IStructuredSelection) listViewer
						.getSelection();
				String selectedOuterMethod = (String) selection
						.getFirstElement();
				if (selectedOuterMethod != null) {
					int i = 0;
					for (i = 0; i < ListViewHelper.getInstance()
							.getOuterMethods().size(); i++)
						if (selectedOuterMethod.equals(ListViewHelper
								.getInstance().getOuterMethods().get(i)
								.getCustomName())) {
							openFileInEditor(ListViewHelper.getInstance()
									.getOuterMethods().get(i).getMethod()
									.getCompilationUnit(), ListViewHelper
									.getInstance().getOuterMethods().get(i)
									.getLineNumber());
							break;
						}
				}
			}
		});

		refactorItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				IStructuredSelection selection = (IStructuredSelection) listViewer
						.getSelection();
				String selectedOuterMethod = (String) selection
						.getFirstElement();
				if (selectedOuterMethod != null) {
					int i = 0;
					for (i = 0; i < ListViewHelper.getInstance()
							.getOuterMethods().size(); i++)
						if (selectedOuterMethod.equals(ListViewHelper
								.getInstance().getOuterMethods().get(i)
								.getCustomName())) {
							refactoring.createClass(ListViewHelper
									.getInstance().getOuterMethods().get(i));

							break;
						}
				}
			}
		});

		compareItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				IStructuredSelection selection = (IStructuredSelection) listViewer
						.getSelection();
				String selectedOuterMethod = (String) selection
						.getFirstElement();
				if (selectedOuterMethod != null) {
					int i = 0;
					for (i = 0; i < ListViewHelper.getInstance()
							.getOuterMethods().size(); i++)
						if (selectedOuterMethod.equals(ListViewHelper
								.getInstance().getOuterMethods().get(i)
								.getCustomName())) {
							new ComparisonResultWindow(new MethodsComparator(
									ListViewHelper.getInstance()
											.getOuterMethods().get(i))
									.compare());
							break;
						}
				}
			}
		});
	}

	private void openFileInEditor(ICompilationUnit cUnit, int lineNumber) {
		try {
			String path = null;
			File fileToOpen = null;
			IFile ifile = null;
			Map<String, Integer> map = new HashMap<String, Integer>();
			// IFileStore fileStore;
			IWorkbenchPage page;
			IMarker marker;
			IResource resource = cUnit.getUnderlyingResource();
			if (resource.getType() == IResource.FILE) {
				ifile = (IFile) resource;
				path = ifile.getRawLocation().toString();
			}
			fileToOpen = new File(path);
			if (fileToOpen.exists() && fileToOpen.isFile()) {
				// fileStore =
				// EFS.getLocalFileSystem().getStore(fileToOpen.toURI());
				page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage();
				map.put(IMarker.LINE_NUMBER, new Integer(lineNumber));
				marker = ifile.createMarker(IMarker.LINE_NUMBER);
				marker.setAttributes(map);
				IDE.openEditor(page, marker);
				marker.delete();
			} else
				throw new FileNotFoundException();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}