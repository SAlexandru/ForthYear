package lrg.patrools.wala.extensions;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ibm.wala.cast.loader.AstMethod;
import com.ibm.wala.cfg.IBasicBlock;
import com.ibm.wala.dataflow.graph.BasicFramework;
import com.ibm.wala.dataflow.graph.IKilldallFramework;
import com.ibm.wala.ssa.SSAInstruction;
import com.ibm.wala.util.CancelException;

public final class IntraProceduralSCA {
	
	private SCATransferFunctionComputer transferFunctionsComputer = new SCATransferFunctionComputer();
	
	private static IntraProceduralSCA instance = null;
	
	private IntraProceduralSCA() {}
	
	public static IntraProceduralSCA getInstance() {
		return instance == null ? instance = new IntraProceduralSCA() : instance;
	}
	
	public Map<Integer,SCAVariable> execute(InputContext context, AstMethod theMethod) throws CancelException {
		TypeFilter.clearDiscriminatingInstrcutionsSet();
		transferFunctionsComputer.setAnalyzedMethod(theMethod,context);
		IKilldallFramework<IBasicBlock<SSAInstruction>,SCAVariable> problem = new BasicFramework<IBasicBlock<SSAInstruction>,SCAVariable>(theMethod.cfg(), transferFunctionsComputer);
		SCADataflowSolver solver = new SCADataflowSolver(problem,theMethod,context);
		solver.solve(null);
		transferFunctionsComputer.finalyzeAnalysis();
		return solver.getVariablesBeforeBlocks();
	}

	public List<SCAVariable> beforeEachInstructionFrom(SCAVariable in, IBasicBlock<SSAInstruction> basicBlock, AstMethod theMethod) {
		return transferFunctionsComputer.detailedFlow(in, basicBlock, theMethod);
	}

	public Set<Integer> discriminatingInstructions() {
		return TypeFilter.getDiscriminatingInstrcutions();	
	}
}
