package lrg.patrools.actions;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.managers.EntityTypeManager;
import lrg.patrools.wala.extensions.InputContext;
import lrg.patrools.wala.extensions.IntraProceduralSCA;
import lrg.patrools.wala.extensions.RepresentationsConversionUtilities;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.jdt.core.IMethod;

import plugins.ModelElement;
import plugins.Wrapper;

import com.ibm.wala.cast.java.ipa.callgraph.JavaSourceAnalysisScope;
import com.ibm.wala.cast.java.translator.jdt.JDTClassLoaderFactory;
import com.ibm.wala.cast.java.translator.jdt.JDTSourceLoaderImpl;
import com.ibm.wala.cast.loader.AstMethod;
import com.ibm.wala.ide.util.EclipseProjectPath;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ssa.SSAInstruction;

public class PatroolsTestAction implements IObjectActionDelegate {

	private ISelection theSelection;
	
	public PatroolsTestAction() {}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {}

	public void run(IAction action) {
		if(theSelection instanceof IStructuredSelection) {
		try {
			IMethod a = (IMethod)((IStructuredSelection)theSelection).getFirstElement();
			Wrapper.forceClearAll();
			/*Wrapper wrappedMet = Wrapper.createInstance(ModelElement.createInstance(a));
			wrappedMet.setEntityType(EntityTypeManager.getEntityTypeForName("method"));
			MicroprintBuilder mic = new MicroprintBuilder();
			mic.buildFigure(wrappedMet, null);
			
			Figure f = mic.getFigure();
			ViewRenderer v = new ViewRenderer();
			f.renderOn(v);
			v.open();*/
			
			Wrapper wrapped = Wrapper.createInstance(a);
			wrapped.setEntityType(EntityTypeManager.getEntityTypeForName("method"));
			System.out.println(wrapped.getGroup("method chain").getElements());
			
			//Testing
			/*EclipseProjectPath eprj = EclipseProjectPath.make(a.getJavaProject(), true, false);	
			AnalysisScope scope = eprj.toAnalysisScope(new JavaSourceAnalysisScope());			
			JDTClassLoaderFactory factory = new JDTClassLoaderFactory(scope.getExclusions());
			ClassHierarchy ch = ClassHierarchy.make(scope,factory);
			
			AstMethod tmp = RepresentationsConversionUtilities.eclipse2wala(a, ch);
			System.out.println(tmp.cfg());
			for(int i = 0; i < tmp.cfg().getInstructions().length; i++) {
				SSAInstruction k = (SSAInstruction)tmp.cfg().getInstructions()[i];
				
				if(k != null) {
					System.out.println(k + " " + tmp.getSourcePosition(i));
					System.out.println(k.getClass());
				}
			}
								
			//IntraProceduralSCA.getInstance().execute(wrapped,tmp);
			//Return the control flow graph - tmp.cfg()	
			//SSAOptions opt = new SSAOptions();
			//opt.setPiNodePolicy(SSAOptions.getAllBuiltInPiNodes());
			//IR tmpIR = AstIRFactory.makeDefaultFactory().makeIR(tmp, Everywhere.EVERYWHERE, opt);
							
							/*SSAOptions opt = new SSAOptions();
							opt.setPiNodePolicy(SSAOptions.getAllBuiltInPiNodes());
							AstIRFactoryExtended myFactory = new AstIRFactoryExtended();
							IR tmpIR = myFactory.makeIR(tmp, Everywhere.EVERYWHERE, opt);
							//AstJavaTypeInference ti = new AstJavaTypeInference(tmpIR,ch,false);
							//System.out.println(ti);
							System.out.println(tmpIR);
							/*Iterator<ISSABasicBlock> bbIt = tmpIR.getControlFlowGraph().iterator();
							while(bbIt.hasNext()) {
								ISSABasicBlock bb = bbIt.next();
								Iterator<SSAPhiInstruction> phIt = bb.iteratePhis();
								while(phIt.hasNext()) {
									System.out.println(">>>" + phIt.next());
								}
								Iterator<SSAPiInstruction> piIt = bb.iteratePis();
								while(piIt.hasNext()) {
									System.out.println(">>>" + piIt.next());
								}
							}
							//System.out.println(tmp.getSourcePosition()); - the position of the body
							//for(int i = 0; i < tmpIR.getInstructions().length; i++) { - the position of each instruction
							//	System.out.println(tmp.getSourcePosition(i));
							//}*/			
					    
		} catch(Throwable a) {
			a.printStackTrace();
		}}
	}

	
	public void selectionChanged(IAction action, ISelection selection) {
		theSelection = selection;
	}

}
