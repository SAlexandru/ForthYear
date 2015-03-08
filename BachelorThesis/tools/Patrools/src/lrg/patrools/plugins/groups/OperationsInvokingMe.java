package lrg.patrools.plugins.groups;

import java.util.ArrayList;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.managers.EntityTypeManager;
import lrg.common.abstractions.plugins.groups.GroupBuilder;
import lrg.insider.visualizations.MonitorCanceledException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;

import plugins.Wrapper;
import plugins.WrapperWithLocation;

public class OperationsInvokingMe extends GroupBuilder {

	public OperationsInvokingMe() {
		super("operations invoking me", "", new String[] { "global function","method" });
	}

	@Override
	public ArrayList buildGroup(AbstractEntityInterface anEntity, IProgressMonitor monitor) throws MonitorCanceledException{

		SearchPattern pattern = null;
		IJavaProject iJavaProject = null;
		IMethod iMethod = null;

		if (anEntity instanceof Wrapper == false)
			return new ArrayList();

		Wrapper wrappedOperation = (Wrapper) anEntity;
		ArrayList existentGroup = wrappedOperation.findGroup("operations invoking me");
        if (existentGroup!=null)
        	return existentGroup;
		if (wrappedOperation.getElement() instanceof IMethod){
			iMethod = (IMethod) wrappedOperation.getElement();

			iJavaProject = iMethod.getJavaProject();

			pattern = SearchPattern.createPattern(iMethod,
					IJavaSearchConstants.REFERENCES,
					SearchPattern.R_EXACT_MATCH);

		} else
			return new ArrayList();


		MySearchRequestor requestor = new MySearchRequestor();
		try {
			SearchEngine searchEngine = new SearchEngine();
			iJavaProject = iMethod.getJavaProject();
			IJavaElement searchScopeBinaries[] = iJavaProject.getPackageFragmentRoots();
			IJavaElement searchScope[]= new IJavaElement[searchScopeBinaries.length];
			int k=0;
			for (int i = 0 ; i<searchScopeBinaries.length;i++){
				if (!searchScopeBinaries[i].isReadOnly()){
					searchScope[k++]=searchScopeBinaries[i];
				}
			}
			IJavaSearchScope iJavaSearchScope = null;

			if (Flags.isPrivate(iMethod.getFlags())){
				iJavaSearchScope = SearchEngine.createJavaSearchScope(new IJavaElement[]{iMethod.getCompilationUnit()}, false);
			}
			else if (Flags.isProtected(iMethod.getFlags())){
				iJavaSearchScope = SearchEngine.createHierarchyScope(iMethod.getDeclaringType());
			}
			else
				iJavaSearchScope = SearchEngine.createJavaSearchScope(searchScope, false);
			searchEngine.search(pattern,
					new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() },
					iJavaSearchScope, requestor, new SubProgressMonitor(monitor,1));
			wrappedOperation.addGroup("operations invoking me", requestor.getCallers());
		} 
		catch (CoreException e1) {
			e1.printStackTrace();
		}

		return requestor.getCallers();

	}

	private	class MySearchRequestor extends SearchRequestor {
		private ArrayList<Wrapper> collectionOfWrappedMethods;

		public MySearchRequestor() {
			super();
			collectionOfWrappedMethods = new ArrayList<Wrapper>();
		}

		public void acceptSearchMatch(SearchMatch match) throws CoreException {
			WrapperWithLocation wrapper;
			if (match.getElement() instanceof IMethod) {
				if ((match.getRule() & 512) == 512 || match.isInsideDocComment()){
					return;
				}
				IMethod iMethod = (IMethod) match.getElement();
				int offset = match.getOffset();
				int length = match.getLength();
				wrapper = new WrapperWithLocation(iMethod,iMethod,offset,length);
				wrapper.setEntityType(EntityTypeManager.getEntityTypeForName("method"));
				collectionOfWrappedMethods.add(wrapper);
			}	
		}

		public ArrayList<Wrapper> getCallers() {
			return this.collectionOfWrappedMethods;
		}

	}

	@Override
	public ArrayList buildGroup(AbstractEntityInterface anEntity) {
		try {
			return buildGroup(anEntity, new NullProgressMonitor());
		} catch (MonitorCanceledException e) {
			e.printStackTrace();
		}
		return new ArrayList();
	} 

}
