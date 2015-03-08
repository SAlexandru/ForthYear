package lrg.patrools.wala.extensions;

import java.util.HashMap;
import java.util.Map;

import com.ibm.wala.cast.loader.AstMethod;
import com.ibm.wala.cfg.IBasicBlock;
import com.ibm.wala.dataflow.graph.DataflowSolver;
import com.ibm.wala.dataflow.graph.IKilldallFramework;
import com.ibm.wala.ssa.SSAInstruction;
import com.ibm.wala.types.TypeReference;

final class SCADataflowSolver extends DataflowSolver<IBasicBlock<SSAInstruction>,SCAVariable> {
	
	private AstMethod theMethod;
	private Map<Integer,SCAVariable> inVar = new HashMap<Integer,SCAVariable>();
	private InputContext pre;
	
	SCADataflowSolver(IKilldallFramework<IBasicBlock<SSAInstruction>, SCAVariable> problem, AstMethod theMethod, InputContext pre) {
		super(problem);
		this.theMethod = theMethod;
		this.pre = pre;
	}
	
	@Override
	protected SCAVariable makeEdgeVariable(IBasicBlock<SSAInstruction> src, IBasicBlock<SSAInstruction> dst) {
		SCAVariable res = new SCAVariable(src,dst);
		if(theMethod.cfg().getPredNodeCount(dst) <= 1) {
			inVar.put(dst.getNumber(),res);
		}
		return res;
	}

	@Override
	protected SCAVariable makeNodeVariable(IBasicBlock<SSAInstruction> n, boolean IN) {
		SCAVariable res = new SCAVariable(n);
		if(IN && n.isEntryBlock()) {
			for(int i = 0; i < theMethod.getNumberOfParameters(); i++) {
				if(i == 0 && !theMethod.isStatic()) {
					continue; //the this reference
				}
				TypeReference paramType = theMethod.getParameterType(i);
				if(paramType.isClassType()) {
					res.replaceTypesForVar(i + 1, TypesSetFactory.getInstance().makeCone(paramType));			
				}
			}
			Map<Integer,InputContext.ObjectFieldPair> data = pre.getArtificialExtryData();
			for(Integer newNumber : data.keySet()) {
				InputContext.ObjectFieldPair fieldData = data.get(newNumber);
				if(fieldData.field.getFieldType().isClassType()) {
					res.replaceTypesForVar(newNumber, TypesSetFactory.getInstance().makeCone(fieldData.field.getFieldType()));
				}
			}			
		}
		if(IN && (n.isEntryBlock() || theMethod.cfg().getPredNodeCount(n) > 1)) {
			inVar.put(n.getNumber(), res);
		}
		return res;
	}

	Map<Integer, SCAVariable> getVariablesBeforeBlocks() {
		return inVar;
	}
	
}
