package lrg.patrools.wala.extensions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.ibm.wala.cast.loader.AstMethod;
import com.ibm.wala.cfg.IBasicBlock;
import com.ibm.wala.ssa.SSAConditionalBranchInstruction;
import com.ibm.wala.ssa.SSAInstruction;

abstract class TypeFilter {

	private static TypeFilter chain = new IfInstanceofFilter(new IfNotInstanceOfFilter(null));
	protected static Set<Integer> discriminatingInstr = new HashSet<Integer>();
	
	public static SCAVariable shouldFilter(AstMethod method, SCAVariable out, SCAVariable in) {
		if(out.getBlock().getLastInstructionIndex() < 0) return in;
		if(method.cfg().getInstructions()[out.getBlock().getLastInstructionIndex()] instanceof SSAConditionalBranchInstruction) {
			Object[] succ = method.cfg().getNormalSuccessors(out.getBlock()).toArray();
			//TODO: Wala 1.3 release probably contains a bug related to do-while loops
			//See edu.uci.ics.jung.random.generators.EppsteinPowerLawGenerator.generateGraph()
			//This code is a patch
			if(succ.length == 1) {
				return in;
			}
			//End patch
			assert succ.length == 2;
			int code = ((IBasicBlock<SSAInstruction>)succ[0]).getNumber() < ((IBasicBlock<SSAInstruction>)succ[1]).getNumber() ? ((IBasicBlock<SSAInstruction>)succ[0]).getNumber() : ((IBasicBlock<SSAInstruction>)succ[1]).getNumber();
			return chain.execute(method,out.getBlock().getLastInstructionIndex(), in, code == out.getToBlock().getNumber());
		}
		return in;
	}
	
	private TypeFilter next = null;

	//Temporary fields
	protected int value;
	protected TypesSet types;

	protected TypeFilter(TypeFilter next) {
		this.next = next;
	}
	
	private SCAVariable execute(AstMethod method, int pos, SCAVariable in, boolean isTrueBranch) {
		if(canHandle(method, pos, in, isTrueBranch)) {
			return handle(in);
		} else if(next != null) {
			return next.execute(method, pos, in, isTrueBranch);
		} else {
			return in;
		}
	}
	
	protected abstract boolean canHandle(AstMethod method, int pos, SCAVariable var, boolean isTrueBranch);
	
	private final SCAVariable handle(SCAVariable initial) {
		SCAVariable res = initial.duplicate();
		res.replaceTypesForVarAndCopies(value, types);
		return res;
	}

	public static Set<Integer> getDiscriminatingInstrcutions() {
		return Collections.unmodifiableSet(discriminatingInstr);
	}

	public static void clearDiscriminatingInstrcutionsSet() {
		discriminatingInstr = new HashSet<Integer>();
	}

}
