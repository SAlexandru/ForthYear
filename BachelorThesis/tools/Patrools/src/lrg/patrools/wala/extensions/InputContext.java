package lrg.patrools.wala.extensions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import plugins.Wrapper;

import lrg.common.abstractions.entities.AbstractEntityInterface;

import com.ibm.wala.ssa.SSACheckCastInstruction;
import com.ibm.wala.cast.ir.ssa.AssignInstruction;
import com.ibm.wala.cast.ir.ssa.AstPreInstructionVisitor;
import com.ibm.wala.cast.java.ssa.AstJavaAbstractInstructionVisitor;
import com.ibm.wala.cast.loader.AstMethod;
import com.ibm.wala.ssa.ConstantValue;
import com.ibm.wala.ssa.SSAGetCaughtExceptionInstruction;
import com.ibm.wala.ssa.SSAGetInstruction;
import com.ibm.wala.ssa.SSAInstruction;
import com.ibm.wala.ssa.SSAPutInstruction;
import com.ibm.wala.types.FieldReference;
import com.ibm.wala.types.MethodReference;
import com.ibm.wala.cast.java.ssa.AstJavaInvokeInstruction;

public class InputContext {

	private static PreProcessorVisitor visitorFields = new PreProcessorVisitor();
	private static PreProcessorLocalsScope visitorLocals = new PreProcessorLocalsScope();

	private AstMethod theMethod;
	private Set<MethodReference> calleeFilter;
	
	private Map<Integer,ObjectFieldPair> entryAdditions = new HashMap<Integer,ObjectFieldPair>();
	
	private List<Integer> localsName = new ArrayList<Integer>();
	private List<Integer> localsScopeBegin = new ArrayList<Integer>();
	private List<Integer> localsScopeEnd = new ArrayList<Integer>();
	
	private List<Integer> tempName = new ArrayList<Integer>();
	private List<Integer> tempBegin = new ArrayList<Integer>();
	private List<Integer> tempEnd = new ArrayList<Integer>();
	
	private Map<SSAInstruction,Set<Integer>> joinedChar = new HashMap<SSAInstruction,Set<Integer>>();
	private Set<Integer> interestingVars = null;
	
	public InputContext(AbstractEntityInterface codeproMethod,AstMethod theMethod, Set<MethodReference> calleeFilter) {
		this.theMethod = theMethod;
		this.calleeFilter = calleeFilter;
		visitorFields.process(theMethod,entryAdditions);
		visitorLocals.process(codeproMethod,theMethod,localsName,localsScopeBegin,localsScopeEnd,joinedChar);
		processTemporaries();
	}
	
	Set<MethodReference> getCalleeFilter() {
		return calleeFilter;
	}
	
	void setInterestingVars(Set<Integer> t) {
		interestingVars = t;
	}
	
	Set<Integer> getInterestingVars() {
		return interestingVars;
	}

	Map<Integer,ObjectFieldPair> getArtificialExtryData() {
		return entryAdditions;
	}
	
	private boolean isTemporary(int val) {
		return !theMethod.symbolTable().isParameter(val) && theMethod.symbolTable().getValue(val) == null;
	}
	
	private void processTemporaries() {
		//Only temp. variables used exclusively for chained invocations should be rendered 
		Object[] instr = theMethod.cfg().getInstructions();
		for(int i = 0; i < instr.length; i++) {
			if(instr[i] != null) {
				SSAInstruction instruction = (SSAInstruction)instr[i];
				//The temporary is the result of an invocation or cast
				if(instruction instanceof AstJavaInvokeInstruction || instruction instanceof SSACheckCastInstruction) {
					for(int j = 0; j < instruction.getNumberOfDefs(); j++) {
						if(isTemporary(instruction.getDef(j))) {
							assert tempName.contains(instruction.getDef(j)) == false;
							tempName.add(instruction.getDef(j));
							tempBegin.add(i);					
							tempEnd.add(i);
						}
					}
				}
				//The temporary is used only as the parameter of an invocation of cast
				if(instruction instanceof AstJavaInvokeInstruction) {
					for(int j = 0; j < instruction.getNumberOfUses(); j++) {
						if(isTemporary(instruction.getUse(j))) {
							int pos = tempName.indexOf(instruction.getUse(j));
							if(pos < 0) continue;
							tempEnd.remove(pos);
							tempEnd.add(pos,i);
						}
					}
				}
			}
		}
		//Eliminate irrelevant temp
		int i = 0;
		while(i < tempName.size()) {
			if(tempBegin.get(i) == tempEnd.get(i)) {
				tempName.remove(i);
				tempBegin.remove(i);
				tempEnd.remove(i);
			} else {
				i++;
			}
		}
	}
		
	public boolean isInScope(Integer use, int instrPos) {
		if(!interestingVars.contains(use)) return false;
		if(entryAdditions.containsKey(use)) {
			ObjectFieldPair data = entryAdditions.get(use);
			if(data.obj == null || data.obj.equals(0)) return true;
			else
				return false;
		}
		if(theMethod.symbolTable().isParameter(use)) return true;
		if(theMethod.symbolTable().getValue(use) != null && !(theMethod.symbolTable().getValue(use) instanceof ConstantValue)) {
			int pos = localsName.indexOf(use);
			if(pos < 0) {
				//This might be the case when an inner instance accesses fields/methods of the container
				return false;
			}
			int start = theMethod.getSourcePosition(instrPos).getFirstOffset();
			int stop = theMethod.getSourcePosition(instrPos).getLastOffset();
			if(start >= localsScopeBegin.get(pos) && stop<= localsScopeEnd.get(pos)) return true;
		}
		if(isTemporary(use)) {
			int pos = tempName.indexOf(use);
			if(pos < 0) return false;
			if(instrPos > tempBegin.get(pos) && instrPos <= tempEnd.get(pos)) return true;
		}
		return false;
	}

	public Set<Integer> getJoinedChars(SSAInstruction instr) {
		return joinedChar.get(instr);
	}
	
	Integer getValueNumberForField(FieldReference declaredField) {
		ObjectFieldPair p = new ObjectFieldPair(null,declaredField);
		if(entryAdditions.containsValue(p)) {
			for(Integer tmp : entryAdditions.keySet()) {
				ObjectFieldPair d = entryAdditions.get(tmp);
				if(d.equals(p)) return tmp; 
			}
		}
		return null;
	}

		
	public static class ObjectFieldPair {
		Integer obj;
		FieldReference field;
		ObjectFieldPair(Integer obj, FieldReference field) {
			this.obj = obj;
			this.field = field;
		}
		
		public boolean equals(Object o) {
			if(o instanceof ObjectFieldPair) {
				ObjectFieldPair ofp = (ObjectFieldPair)o;
				if(obj == null || ofp.obj == null) {
					if(obj == ofp.obj && field.equals(ofp.field)) return true;
					return false;
				}
				if(obj.equals(ofp.obj) && field.equals(ofp.field)) return true;
			}
			return false;
		}
		
		public int hashCode() {
			return (obj == null) ? 37 * field.hashCode() : 37 * field.hashCode() + obj.hashCode();
		}
	}
	
	private static class PreProcessorLocalsScope extends ASTVisitor {
		
		//All are temporaries
		private boolean inProcessedMethod = false;
		private Stack<Integer> blockStackEnd = new Stack<Integer>();
		private List<Integer> localsName;
		private List<Integer> localsScopeBegin;
		private List<Integer> localsScopeEnd;
		private AbstractEntityInterface codeproMethod;
		private Map<SSAInstruction,Set<Integer>> joinedChar;
		private AstMethod theMethod;

		
		public void process(AbstractEntityInterface codeproMethod, AstMethod theMethod, List<Integer> names, List<Integer> begin, List<Integer> end, Map<SSAInstruction,Set<Integer>> joinedChar) {
			this.localsName = names;
			this.localsScopeBegin = begin;
			this.localsScopeEnd = end;
			this.codeproMethod = codeproMethod;
			this.joinedChar = joinedChar;
			this.theMethod = theMethod;
			IMethod tmp = (IMethod)((Wrapper)codeproMethod).getElement();
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource(tmp.getCompilationUnit());
			parser.setResolveBindings(true);
			CompilationUnit cu = (CompilationUnit)parser.createAST(null);
			cu.accept(this);
		}
	
		public boolean visit(MethodDeclaration m) {		
			IMethod myMethod = (IMethod)m.resolveBinding().getJavaElement();
			if(myMethod.equals((IMethod)((Wrapper)codeproMethod).getElement())) {
				inProcessedMethod = true;
			}
			if(m.getBody()!=null) {
				m.getBody().accept(this);
			}
			return false;
		}
		
		public void endVisit(MethodDeclaration m) {
			IMethod myMethod = (IMethod)m.resolveBinding().getJavaElement();
			if(myMethod.equals((IMethod)((Wrapper)codeproMethod).getElement())) {
				inProcessedMethod = false;
			}
		}

		public boolean visit(TypeDeclaration m) {											
			if(inProcessedMethod) {
				return false;
			}
			return true;
		}

		public boolean visit(Block node) {
			if(inProcessedMethod) {
				blockStackEnd.push(node.getStartPosition() + node.getLength());
			}
			return true;
		}
		
		public void endVisit(Block node) {
			if(inProcessedMethod)
				blockStackEnd.pop();
		}
						
		public boolean visit(VariableDeclarationFragment node) {
			if(inProcessedMethod) {
				boolean found = false;
				if(node.getParent() instanceof VariableDeclarationStatement) {
					found = ((VariableDeclarationStatement)node.getParent()).fragments().get(0) == node;
				} else if(node.getParent() instanceof VariableDeclarationExpression) {
					found = ((VariableDeclarationExpression)node.getParent()).fragments().get(0) == node;					
				}
				if(found) {
					Integer res[] = convertLocalName2ValueNumber(node);
					if(res != null) {
						int valueNumber = res[0];
						HashSet<Integer> s = new HashSet<Integer>();
						for(int j = node.getParent().getStartPosition(); j < theMethod.getSourcePosition(res[1]).getFirstOffset(); j++) {
							s.add(j);
						}
						joinedChar.put((SSAInstruction)theMethod.cfg().getInstructions()[res[1]], s);						
						localsName.add(valueNumber);
						localsScopeBegin.add(node.getStartPosition() + node.getLength() + 1);
						localsScopeEnd.add(blockStackEnd.peek());
					}
				}
			}
			return false;
		}
		
		private Integer[] convertLocalName2ValueNumber(VariableDeclaration node) {
			int valueNumber = 0;
			String [][] names = theMethod.debugInfo().getSourceNamesForValues();
			do {
				while(valueNumber < names.length && (names[valueNumber].length == 0 || !names[valueNumber][0].equals(node.getName().getIdentifier()))) valueNumber++;
				if(valueNumber == names.length) {
					System.err.println("Cannot find the value number of a local!");
					break;
				}
				Object[] instr = theMethod.cfg().getInstructions();
				for(int i = 0; i < instr.length; i++) {
					SSAInstruction theInstr = (SSAInstruction)instr[i];
					if(theInstr != null) {
						if(theInstr.hasDef() && theInstr.getDef() == valueNumber) {
							if(theInstr instanceof SSAGetCaughtExceptionInstruction) {
								//The code associated to the instr. must include de declaration
								if(theMethod.getSourcePosition(i).getFirstOffset() <= node.getStartPosition() && node.getStartPosition() + node.getLength() <= theMethod.getSourcePosition(i).getLastOffset()) {
									return new Integer[]{valueNumber,i};
								}
							} else {
								//In order to be the corresponding initialization, the instruction must be in
								//the code associated to the initializer
								int initEndOffset = theMethod.getSourcePosition(i).getLastOffset();
								if(node.getParent().getStartPosition() <= initEndOffset && initEndOffset <= node.getStartPosition() + node.getLength()) {
									return new Integer[]{valueNumber,i};
								}
							}
						}
					}
				}
				valueNumber++;
			}while(true);
			return null;
		}
			
		public boolean visit(CatchClause node) {
			if(inProcessedMethod) {
				Integer res[] = convertLocalName2ValueNumber(node.getException());
				if(res != null) {
					localsName.add(res[0]);
					localsScopeBegin.add(node.getBody().getStartPosition());
					localsScopeEnd.add(node.getBody().getStartPosition() + node.getBody().getLength());					
				}
			}
			node.getBody().accept(this);
			return false;
		}

		public boolean visit(ForStatement node) {
			if(inProcessedMethod) {
				blockStackEnd.push(node.getBody().getStartPosition() + node.getBody().getLength());
			}
			return true;
		}
		
		public void endVisit(ForStatement node) {
			if(inProcessedMethod) {
				blockStackEnd.pop();
			}
		}
		
		public boolean visit(EnhancedForStatement node) {
			if(inProcessedMethod) {
				Integer res[] = convertLocalName2ValueNumber(node.getParameter());
				if(res != null) {
					localsName.add(res[0]);
					localsScopeBegin.add(node.getBody().getStartPosition());
					localsScopeEnd.add(node.getBody().getStartPosition() + node.getBody().getLength());
				}
			}
			node.getBody().accept(this);
			return false;
		}
		
	}
	
	private static class PreProcessorVisitor extends AstJavaAbstractInstructionVisitor implements AstPreInstructionVisitor {

		private int nextCode;
		private Map<Integer,ObjectFieldPair> entryAdditions; 
		private AstMethod theMethod;
		
		public void process(AstMethod theMethod, Map<Integer,ObjectFieldPair> entryAdditions) {
			nextCode = -2;
			this.theMethod = theMethod;
			this.entryAdditions = entryAdditions;
			Object[] instr = theMethod.cfg().getInstructions();
			for(int i = 0; i < instr.length; i++) {
				if(instr[i] != null) ((SSAInstruction)instr[i]).visit(visitorFields);
			}
		}
		
		public void visitAssign(AssignInstruction inst) {}
		
		public void visitGet(SSAGetInstruction instr) {
			if(instr.isStatic() || (!theMethod.isStatic() && instr.getRef() == 1)) {
				ObjectFieldPair p = new ObjectFieldPair(null,instr.getDeclaredField());
				if(!entryAdditions.containsValue(p)) {
					entryAdditions.put(nextCode, p);
					nextCode--;
				}
			}
		}
		
		public void visitPut(SSAPutInstruction instr) {
			if(instr.isStatic() || (!theMethod.isStatic() && instr.getRef() == 1)) {
				ObjectFieldPair p = new ObjectFieldPair(null,instr.getDeclaredField());
				if(!entryAdditions.containsValue(p)) {
					entryAdditions.put(nextCode, p);
					nextCode--;
				}
			}
		}
	}
	
}
