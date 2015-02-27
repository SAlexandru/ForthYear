package returnnullanalysis;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IViewSite;

public class ViewContentProvider implements IStructuredContentProvider {

	private IJavaProject javaProject;
	private ICompilationUnit[] compilationUnits;
	private IViewSite viewSite;
	private FileParser fileParser = new FileParser();

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		v.refresh();
	}

	public void dispose() {
	}

	public void showView() throws JavaModelException {
		int i;
		// model.reset();
		IPackageFragmentRoot[] packages = javaProject.getPackageFragmentRoots();
		for (IPackageFragmentRoot iPackageFragmentRoot : packages)
			if (!(iPackageFragmentRoot.isExternal())) {
				IJavaElement[] javaElements = iPackageFragmentRoot
						.getChildren();
				for (i = 0; i < javaElements.length; i++) {
					IPackageFragment packageFragment = (IPackageFragment) javaElements[i];
					compilationUnits = packageFragment.getCompilationUnits();
					for (ICompilationUnit compilationUnit : compilationUnits) // for
																				// every
																				// .java
																				// file
					{
						fileParser.setCompilationUnit(compilationUnit);
						fileParser.startParsing();
					}
				}
			}
	}

	public void setViewSite(IViewSite viewSite) {
		this.viewSite = viewSite;
	}

	public void setJavaProject(IJavaProject javaProject) {
		this.javaProject = javaProject;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return new String[] { Pile.getInstance().toString() };
	}
}
