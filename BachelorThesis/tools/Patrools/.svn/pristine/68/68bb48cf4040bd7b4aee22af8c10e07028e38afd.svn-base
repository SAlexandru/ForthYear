package lrg.patrools.wala.extensions;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ibm.wala.cfg.IBasicBlock;
import com.ibm.wala.fixedpoint.impl.AbstractVariable;
import com.ibm.wala.fixpoint.IVariable;
import com.ibm.wala.ssa.SSAInstruction;

class SCAVariable extends AbstractVariable<SCAVariable> {

	private HashMap<Integer,TypesSet> var2types = new HashMap<Integer,TypesSet>();
	private HashMap<Integer,Set<Integer>> varIsCopyOf = new HashMap<Integer,Set<Integer>>();
	private IBasicBlock<SSAInstruction> block;
	private IBasicBlock<SSAInstruction> to;
	
	SCAVariable(IBasicBlock<SSAInstruction> block) {
		this.block = block;
		this.to = null;
	}

	SCAVariable(IBasicBlock<SSAInstruction> from, IBasicBlock<SSAInstruction> to) {
		this.block = from;
		this.to = to;
	}
	
	Map<Integer,TypesSet> getVar2Types() {
		return Collections.unmodifiableMap(var2types);
	}
	
	SCAVariable meet(IVariable[] tab) {
		HashMap<Integer,TypesSet> tmpRez1 = new HashMap<Integer,TypesSet>();
		HashMap<Integer,Set<Integer>> tmpRez2 = new HashMap<Integer,Set<Integer>>();
		HashSet<Integer> second = new HashSet<Integer>();
		for(int i = 0; i < tab.length; i++) {
			SCAVariable v = (SCAVariable)tab[i];
			for(Integer key : v.var2types.keySet()) {
				TypesSet tmp = tmpRez1.get(key);
				if(tmp == null) {
					tmp = v.var2types.get(key);
				} else {
					tmp = tmp.union(v.var2types.get(key));
				}
				tmpRez1.put(key, tmp);
			}
			for(Integer key : v.varIsCopyOf.keySet()) {
				Set<Integer> tmp = tmpRez2.get(key);
				if(tmp == null) {
					second.add(key);
					tmp = v.varIsCopyOf.get(key);
					tmpRez2.put(key, tmp);
				} else {
					if(second.contains(key)) {
						second.remove(key);
						HashSet<Integer> d = new HashSet<Integer>();
						d.addAll(tmp);
						tmp = d;
						tmpRez2.put(key, d);
					}
					tmp.addAll(v.varIsCopyOf.get(key));
				}
			}
			//System.out.print("}");
		}
		SCAVariable rez = this.duplicate();
		rez.var2types.clear();
		rez.var2types.putAll(tmpRez1);
		rez.varIsCopyOf.clear();
		rez.varIsCopyOf.putAll(tmpRez2);
		/*for(int i = 1; i < tab.length; i++) {
			SCAVariable v = (SCAVariable)tab[i];
			Iterator<Integer> it = v.var2types.keySet().iterator();
			while(it.hasNext()) {
				Integer key = it.next();
				Set<TypeReference> tmp = rez.var2types.get(key);
				Set<TypeReference> newEntry = new HashSet<TypeReference>();
				if(tmp != null) {
					newEntry.addAll(tmp);
					System.out.print(tmp.size() + " ");
				}
				newEntry.addAll(v.var2types.get(key));
				System.out.print(v.var2types.get(key).size()+" ");
				rez.var2types.put(key, Collections.unmodifiableSet(newEntry));
			}
			System.out.print("B.");
			it = v.varIsCopyOf.keySet().iterator();
			while(it.hasNext()) {
				Integer key = it.next();
				Set<Integer> tmp = rez.varIsCopyOf.get(key);
				Set<Integer> newEntry = new HashSet<Integer>();
				if(tmp != null) {
					newEntry.addAll(tmp);
				}
				newEntry.addAll(v.varIsCopyOf.get(key));
				rez.varIsCopyOf.put(key, Collections.unmodifiableSet(newEntry));
			}
		}
		System.out.println();*/
		return rez;
	}
	
	void replaceTypesForVar(Integer varId, TypesSet types) {
		assert types != null;
		var2types.put(varId, types);
	}
	
	void replaceTypesForVarAndCopies(Integer varId, TypesSet types) {
		assert types != null;
		var2types.put(varId, types);
		Set<Integer> tmp = varIsCopyOf.get(varId);
		if(tmp != null) {
			for(Integer copy : tmp) {
				var2types.put(copy, types);				
			}
		}
	}
	
	
	void replaceCopiesOfVar(Integer varId, Set<Integer> copies) {
		assert copies != null;
		varIsCopyOf.put(varId, Collections.unmodifiableSet(copies));
	}

	public void copyState(SCAVariable v) {
		var2types.clear();
		var2types.putAll(v.var2types);
		varIsCopyOf.clear();
		varIsCopyOf.putAll(v.varIsCopyOf);
	}
	
	boolean equalState(SCAVariable v) {
		return var2types.equals(v.var2types) && varIsCopyOf.equals(v.varIsCopyOf);
	}
	
	SCAVariable duplicate() {
		SCAVariable var = new SCAVariable(block,to);
		var.var2types.putAll(var2types);
		var.varIsCopyOf.putAll(varIsCopyOf);
		return var;
	}

	public TypesSet getTypeForVar(int use) {
		TypesSet tmp = var2types.get(use);
		return tmp == null ? TypesSetFactory.getInstance().makeEmptyTypes() : tmp;
	}
	
	public Set<Integer> getCopiesOf(int use) {
		Set<Integer> tmp = varIsCopyOf.get(use);
		return tmp == null ? Collections.unmodifiableSet(new HashSet<Integer>()) : tmp;
	}

	public String toString() {
		return block.getNumber() + " " + var2types.toString();
	}

	IBasicBlock<SSAInstruction> getBlock() {
		return block;
	}

	IBasicBlock<SSAInstruction> getToBlock() {
		return to;
	}

}
