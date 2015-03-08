package lrg.patrools.wala.extensions;

import com.ibm.wala.cast.loader.AstMethod;
import com.ibm.wala.ssa.SSAConditionalBranchInstruction;
import com.ibm.wala.ssa.SSAInstanceofInstruction;

public class IfInstanceofFilter extends TypeFilter {
	
	protected IfInstanceofFilter(TypeFilter next) {
		super(next);
	}

	@Override
	protected boolean canHandle(AstMethod theMethod, int pos, SCAVariable var, boolean isTrueBranch) {
		SSAConditionalBranchInstruction theBranch = (SSAConditionalBranchInstruction)theMethod.cfg().getInstructions()[pos];
		if(theMethod.cfg().getInstructions()[pos - 1] instanceof SSAInstanceofInstruction) {
			SSAInstanceofInstruction theInstanceof = (SSAInstanceofInstruction)theMethod.cfg().getInstructions()[pos - 1];
			if(theBranch.getUse(0) == theInstanceof.getDef(0)) {
				value = theInstanceof.getUse(0);
				types = var.getTypeForVar(value);
				TypesSet theCone = TypesSetFactory.getInstance().makeCone(theInstanceof.getCheckedType());
				if(isTrueBranch) {
					types = types.intersection(theCone);
				} else {
					types = types.difference(theCone);
				}
				discriminatingInstr.add(pos);
				int i = pos - 1;
				while(i>=0 && theMethod.cfg().getInstructions()[i]!=null && theMethod.getSourcePosition(i).getFirstOffset() >= theMethod.getSourcePosition(pos).getFirstOffset() && theMethod.getSourcePosition(i).getLastOffset() <= theMethod.getSourcePosition(pos).getLastOffset()) {
					discriminatingInstr.add(i);
					i--;
				}
				return true;
			}
		}
		return false;
	}

}
