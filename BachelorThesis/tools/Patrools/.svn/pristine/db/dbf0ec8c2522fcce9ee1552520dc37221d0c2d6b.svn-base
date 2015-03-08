package lrg.patrools.wala.extensions.listeners;

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaProject;

import com.ibm.wala.cast.java.ipa.callgraph.JavaSourceAnalysisScope;
import com.ibm.wala.cast.java.translator.jdt.JDTClassLoaderFactory;
import com.ibm.wala.ide.util.EclipseProjectPath;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.impl.SetOfClasses;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;

public class WalaRepresentationBuilder implements IElementChangedListener {

	private static WalaRepresentationBuilder instance = null;
	
	public static WalaRepresentationBuilder getInstance() {
		return instance == null ? instance = new WalaRepresentationBuilder() : instance;
	}
	
	private WalaRepresentationBuilder() {}
	
	private SetOfClasses excludedClasses = null;
	private HashMap<IJavaProject,ClassHierarchy> cache = new HashMap<IJavaProject,ClassHierarchy>();

	public synchronized ClassHierarchy parseProject(IJavaProject proj) throws IOException, CoreException, ClassHierarchyException {
		if(cache.get(proj) == null) {
			EclipseProjectPath eprj = EclipseProjectPath.make(proj, true, false);
			AnalysisScope scope = eprj.toAnalysisScope(new JavaSourceAnalysisScope());
			scope.setExclusions(excludedClasses);
			JDTClassLoaderFactory factory = new JDTClassLoaderFactory(scope.getExclusions());
			ClassHierarchy ch = ClassHierarchy.make(scope,factory);
			cache.put(proj, ch);
			return ch;
		} else {
			return cache.get(proj);
		}
	}

	public synchronized void elementChanged(ElementChangedEvent event) {
		cache.clear();
	}
	
}
