package lrg.patrools.wala.extensions;

import com.ibm.wala.cast.loader.AstMethod;
import com.ibm.wala.shrikeBT.IUnaryOpInstruction;
import com.ibm.wala.ssa.SSAConditionalBranchInstruction;
import com.ibm.wala.ssa.SSAInstanceofInstruction;
import com.ibm.wala.ssa.SSAUnaryOpInstruction;

public class IfNotInstanceOfFilter extends TypeFilter {
	
	protected IfNotInstanceOfFilter(TypeFilter next) {
		super(next);
	}

	@Override
	protected boolean canHandle(AstMethod theMethod, int pos, SCAVariable var, boolean isTrueBranch) {
		SSAConditionalBranchInstruction theBranch = (SSAConditionalBranchInstruction)theMethod.cfg().getInstructions()[pos];
		if(!(theMethod.cfg().getInstructions()[pos - 1] instanceof SSAUnaryOpInstruction)) return false;
		SSAUnaryOpInstruction theNotOp = (SSAUnaryOpInstruction)theMethod.cfg().getInstructions()[pos - 1];
		if(theNotOp.getOpcode() != IUnaryOpInstruction.Operator.NEG || theNotOp.getDef() != theBranch.getUse(0)) return false;
		if(!(theMethod.cfg().getInstructions()[pos - 2] instanceof SSAInstanceofInstruction)) return false;
		SSAInstanceofInstruction theInstanceof = (SSAInstanceofInstruction)theMethod.cfg().getInstructions()[pos - 2];
		if(theInstanceof.getDef() != theNotOp.getUse(0)) return false;
		value = theInstanceof.getUse(0);
		types = var.getTypeForVar(value);
		TypesSet theCone = TypesSetFactory.getInstance().makeCone(theInstanceof.getCheckedType());
		if(!isTrueBranch) {
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
