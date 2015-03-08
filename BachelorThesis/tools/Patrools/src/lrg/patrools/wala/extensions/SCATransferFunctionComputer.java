package lrg.patrools.wala.extensions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.ibm.wala.cast.ir.ssa.AssignInstruction;
import com.ibm.wala.cast.ir.ssa.AstPreInstructionVisitor;
import com.ibm.wala.cast.java.ssa.AstJavaAbstractInstructionVisitor;
import com.ibm.wala.cast.java.ssa.AstJavaInvokeInstruction;
import com.ibm.wala.cast.loader.AstMethod;
import com.ibm.wala.cfg.IBasicBlock;
import com.ibm.wala.dataflow.graph.AbstractMeetOperator;
import com.ibm.wala.dataflow.graph.ITransferFunctionProvider;
import com.ibm.wala.fixedpoint.impl.UnaryOperator;
import com.ibm.wala.fixpoint.IVariable;
import com.ibm.wala.ssa.SSACheckCastInstruction;
import com.ibm.wala.ssa.SSAGetInstruction;
import com.ibm.wala.ssa.SSAInstruction;
import com.ibm.wala.ssa.SSANewInstruction;
import com.ibm.wala.ssa.SSAPutInstruction;

final class SCATransferFunctionComputer implements ITransferFunctionProvider<IBasicBlock<SSAInstruction>,SCAVariable> {
	
	private final SCABasicBlockTransferFunction flowVisitor = new SCABasicBlockTransferFunction();

	public UnaryOperator<SCAVariable> getEdgeTransferFunction(IBasicBlock<SSAInstruction> src, IBasicBlock<SSAInstruction> dst) {
		return new UnaryOperator<SCAVariable>() {

			@Override
			public byte evaluate(SCAVariable lhs, SCAVariable rhs) {
				SCAVariable newrhs = TypeFilter.shouldFilter(theMethod, lhs, rhs);
				if(lhs.equalState(newrhs)) {
					return NOT_CHANGED;
				} else {
					lhs.copyState(newrhs);
					return CHANGED;
				}					
			}

			@Override
			public boolean equals(Object o) {
				return this == o;
			}

			@Override
			public int hashCode() {
				return System.identityHashCode(this);
			}

			@Override
			public String toString() {		
				return "Edge_Transfer_Function";
			}
			
		};
	}

	public AbstractMeetOperator<SCAVariable> getMeetOperator() {
		return new AbstractMeetOperator<SCAVariable>() {

			@Override
			public boolean equals(Object o) {
				return this == o;
			}

			@Override
			public byte evaluate(SCAVariable lhs, IVariable[] rhs) {
				SCAVariable tmp = ((SCAVariable)rhs[0]).meet(rhs);
				if (tmp.equalState(lhs)) {
					return NOT_CHANGED;
				} else {
					lhs.copyState(tmp);
					return CHANGED;
				}
			}

			@Override
			public int hashCode() {
				return System.identityHashCode(this);
			}

			@Override
			public String toString() {
				return "MEET_OPERATOR";
			}			
		};
	}

	public UnaryOperator<SCAVariable> getNodeTransferFunction(final IBasicBlock<SSAInstruction> node) {
		return new UnaryOperator<SCAVariable>() {

			@Override
			public byte evaluate(SCAVariable out, SCAVariable in) {
	            SCAVariable newOut = flowVisitor.flow(in,node);
				if (newOut.equalState(out)) {
	              return NOT_CHANGED;
	            } else {
	              out.copyState(newOut);
	              return CHANGED;
	            }
			}

			@Override
			public boolean equals(Object o) {
				return this == o;
			}

			@Override
			public int hashCode() {
				return System.identityHashCode(this);
			}

			@Override
			public String toString() {
				return "BasicBlock_Transfer_Function";
			}
			
		};
	}

	public boolean hasEdgeTransferFunctions() {
		return true;
	}

	public boolean hasNodeTransferFunctions() {
		return true;
	}
	
	public List<SCAVariable> detailedFlow(SCAVariable in, IBasicBlock<SSAInstruction> basicBlock, AstMethod theMethod) {
		return this.flowVisitor.detailedFlow(in, basicBlock, theMethod);
	}
	
	//Temporary fields & methods
	
	private AstMethod theMethod;
	private InputContext pre;
	private Map<SSAInstruction,Set<Integer>> interestingVars = new HashMap<SSAInstruction,Set<Integer>>();
	
	void finalyzeAnalysis() {
		HashSet<Integer> interestingRes = new HashSet<Integer>();
		for(Set<Integer> r : interestingVars.values())
			interestingRes.addAll(r);
		pre.setInterestingVars(interestingRes);	
	}
	
	void setAnalyzedMethod(AstMethod theMethod, InputContext pre) {
		this.theMethod = theMethod;
		this.pre = pre;
		interestingVars.clear();
	}
	
	private class SCABasicBlockTransferFunction extends AstJavaAbstractInstructionVisitor implements AstPreInstructionVisitor {
		
		//Temporary fields
		private SCAVariable cumulative;
		private boolean inAnalysis = false;
		private SSAInstruction previous = null;
		
		//The entry method
		SCAVariable flow(SCAVariable in, IBasicBlock<SSAInstruction> basicBlock) {
			cumulative = in.duplicate();
			inAnalysis = true;
			previous = null;
			Object[] instructions = theMethod.cfg().getInstructions();
			for(int i = basicBlock.getFirstInstructionIndex(); i <= basicBlock.getLastInstructionIndex(); i++) {
				if(instructions[i] != null) {
					previous = (SSAInstruction)((i != 0) ? instructions[i-1] : null);
					((SSAInstruction)instructions[i]).visit(this);
				}
			}
		    return cumulative;
		}
		
		List<SCAVariable> detailedFlow(SCAVariable in, IBasicBlock<SSAInstruction> basicBlock, AstMethod theMethod) {
			ArrayList<SCAVariable> res = new ArrayList<SCAVariable>();
			cumulative = in.duplicate();
			inAnalysis = false;
			previous = null;
			Object[] instructions = theMethod.cfg().getInstructions();
			for(int i = basicBlock.getFirstInstructionIndex(); i <= basicBlock.getLastInstructionIndex(); i++) {
				res.add(cumulative.duplicate());
				if(instructions[i] != null) {
					previous = (SSAInstruction)((i != 0) ? instructions[i-1] : null);
					((SSAInstruction)instructions[i]).visit(this);
				}
			}
		    return res;
		}
				
		private HashSet<Integer> getNewIntegerSet() {
			return new HashSet<Integer>();
		}

		//Visitor methods
		/*New is going to be treated at the next instruction (the constructor invocation)
		public void visitNew(SSANewInstruction instruction) {
			if(instruction.getConcreteType().isClassType()) {
				TypesSet res = TypesSetFactory.getInstance().makeSingleType(instruction.getConcreteType());
				cumulative.replaceTypesForVar(instruction.getDef(),res);
				cumulative.replaceCopiesOfVar(instruction.getDef(),getNewIntegerSet());
			}
		}*/

		public void visitAssign(AssignInstruction inst) {
			cumulative.replaceTypesForVar(inst.getDef(), cumulative.getTypeForVar(inst.getUse(0)));
			HashSet<Integer> res = getNewIntegerSet();
			res.addAll(cumulative.getCopiesOf(inst.getUse(0)));
			res.add(inst.getUse(0));
			cumulative.replaceCopiesOfVar(inst.getDef(), res);
		}
		
		public void visitJavaInvoke(AstJavaInvokeInstruction instruction) {
			if(previous != null && previous instanceof SSANewInstruction && instruction.isSpecial()) {
				SSANewInstruction pre = (SSANewInstruction)previous;
				if(pre.getConcreteType().isClassType()) {
					TypesSet res = TypesSetFactory.getInstance().makeSingleType(pre.getConcreteType());
					cumulative.replaceTypesForVar(pre.getDef(),res);
					cumulative.replaceCopiesOfVar(pre.getDef(),getNewIntegerSet());
				}
			}
			if(instruction.getDeclaredResultType().isClassType()) {
				TypesSet res = TypesSetFactory.getInstance().makeCone(instruction.getDeclaredResultType());
				cumulative.replaceTypesForVar(instruction.getDef(), res);			
				cumulative.replaceCopiesOfVar(instruction.getDef(),getNewIntegerSet());
			}
			if(inAnalysis && instruction.isDispatch()) {
				if(pre.getCalleeFilter().contains(instruction.getDeclaredTarget())) {
					HashSet<Integer> res = new HashSet<Integer>();
					res.add(instruction.getReceiver());
					res.addAll(cumulative.getCopiesOf(instruction.getReceiver()));
					interestingVars.put(instruction, res);
				}
			}
		}
		
		public void visitCheckCast(SSACheckCastInstruction instruction) {
			if(instruction.getDeclaredResultType().isClassType()) {
				TypesSet tmp = cumulative.getTypeForVar(instruction.getUse(0));
				tmp = tmp.intersection(TypesSetFactory.getInstance().makeCone(instruction.getDeclaredResultType()));
				cumulative.replaceTypesForVar(instruction.getDef(),tmp);
				HashSet<Integer> res = getNewIntegerSet();
				res.addAll(cumulative.getCopiesOf(instruction.getUse(0)));
				res.add(instruction.getUse(0));
				cumulative.replaceCopiesOfVar(instruction.getDef(0), res);
			}
	    }
		
		public void visitGet(SSAGetInstruction instruction) {
			if(instruction.getDeclaredFieldType().isClassType() && (instruction.isStatic() || (!theMethod.isStatic() && instruction.getRef() == 1))) {
				Integer use = pre.getValueNumberForField(instruction.getDeclaredField());
				if(use != null) {
					cumulative.replaceTypesForVar(instruction.getDef(0), cumulative.getTypeForVar(use));
					HashSet<Integer> res = getNewIntegerSet();
					res.addAll(cumulative.getCopiesOf(use));
					res.add(use);
					cumulative.replaceCopiesOfVar(instruction.getDef(0), res);
				}
			}
		}

		public void visitPut(SSAPutInstruction instruction) {
			if(instruction.getDeclaredFieldType().isClassType() && (instruction.isStatic() || (!theMethod.isStatic() && instruction.getRef() == 1))) {
				Integer use = pre.getValueNumberForField(instruction.getDeclaredField());
				if(use != null) {
					cumulative.replaceTypesForVar(use, cumulative.getTypeForVar(instruction.getVal()));
					HashSet<Integer> res = getNewIntegerSet();
					res.addAll(cumulative.getCopiesOf(instruction.getVal()));
					res.add(instruction.getVal());
					cumulative.replaceCopiesOfVar(use, res);
				}
			}
		}

	}
}
