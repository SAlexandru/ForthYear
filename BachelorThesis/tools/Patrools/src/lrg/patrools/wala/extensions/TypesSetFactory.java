package lrg.patrools.wala.extensions;

import java.io.IOException;
import java.util.Collections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.ibm.wala.cast.java.ipa.callgraph.JavaSourceAnalysisScope;
import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.types.TypeName;
import com.ibm.wala.types.TypeReference;

public class TypesSetFactory {
	
	private static TypesSetFactory instance;
	
	public static TypesSetFactory getInstance() {
		instance = instance == null ? new TypesSetFactory() : instance;
		return instance;
	}
	
	private IClassHierarchy ch;
	private TypesUsingConeObject coneObject;
	private int maxSize;
	
	public void init(IClassHierarchy ch) {
		cache.clear();
		this.ch = ch;
		TypeName tn = TypeName.string2TypeName("Ljava/lang/Object");
		try {
			coneObject = new TypesUsingConeObject(ch.getFactory().getLoader(JavaSourceAnalysisScope.SOURCE, ch, ch.getScope()).lookupClass(tn).getReference());
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		maxSize = ((TypesUsingSet)concreteCone(coneObject.tr)).theSet.size();
	}
	
	public TypesSet makeCone(TypeReference tr) {
		if(tr.getName().toString().equals("Ljava/lang/Object")) {
			return coneObject;
		} else {
			return concreteCone(tr);
		}
	}
	
	public TypesSet makeSingleType(TypeReference tr) {
		TypesUsingSet res = new TypesUsingSet();
		res.add(tr);
		return res;
	}
		
	public TypesSet makeEmptyTypes() {
		TypesUsingSet res = new TypesUsingSet();
		return res;
	}
		
	private static HashMap<TypeReference,TypesSet> cache = new HashMap<TypeReference,TypesSet>();
	private TypesSet concreteCone(TypeReference klass) {
		assert klass.isClassType();
		if(cache.containsKey(klass)) return cache.get(klass);
		TypesUsingSet res = new TypesUsingSet();
		IClass theRootClass = ch.lookupClass(klass);
		if(theRootClass.isInterface()) {
			for(IClass implementor : ch.getImplementors(klass)) {
				//if(!implementor.isAbstract() && !implementor.isInterface()) {
				//	res.add(implementor.getReference());
				//}
				TypesSet tmp = concreteCone(implementor.getReference());
				for(TypeReference tr : tmp.toSet()) {
					res.add(tr);
				}
			}			
		} else {
			if(!theRootClass.isAbstract()) {
				res.add(klass);
			}
			for(IClass subClass : ch.computeSubClasses(klass)) {
				if(!subClass.isAbstract())
					res.add(subClass.getReference());
			}			
		}
		cache.put(klass,res);
		return res;
	}
	
	private class TypesUsingConeObject extends TypesSet {
		
		private TypeReference tr;
		
		public TypesUsingConeObject(TypeReference tr) {
			this.tr = tr;
		}
				
		public TypesSet union(TypesSet op) {
			return this;
		}
		
		public TypesSet intersection(TypesSet op) {
			return op;
		}
		
		public TypesSet difference(TypesSet op) {
			return concreteCone(tr).difference(op);
		}

		public Set<TypeReference> toSet() {
			return concreteCone(tr).toSet();
		}
		
		public boolean equals(Object o) {
			return o instanceof TypesUsingConeObject;		
		}
		
		public String toString() {
			return "ConeOfObject";
		}
	}
	
	private class TypesUsingSet extends TypesSet {

		private HashSet<TypeReference> theSet = new HashSet<TypeReference>();
		
		private void add(TypeReference e) {
			theSet.add(e);
		}
		
		public TypesSet difference(TypesSet op) {
			if(op instanceof TypesUsingConeObject)
				return TypesSetFactory.this.makeEmptyTypes();
			TypesUsingSet tmp = (TypesUsingSet)op;
			TypesUsingSet res = new TypesUsingSet();
			res.theSet.addAll(theSet);
			res.theSet.removeAll(tmp.theSet);
			return res;
		}

		public TypesSet intersection(TypesSet op) {
			if(op instanceof TypesUsingConeObject)
				return this;
			TypesUsingSet tmp = (TypesUsingSet)op;
			TypesUsingSet res = new TypesUsingSet();
			res.theSet.addAll(theSet);
			res.theSet.retainAll(tmp.theSet);
			return res;
		}

		public Set<TypeReference> toSet() {
			return Collections.unmodifiableSet(this.theSet);
		}

		public TypesSet union(TypesSet op) {
			if(op instanceof TypesUsingConeObject) {
				return op;
			}
			TypesUsingSet tmp = (TypesUsingSet)op;
			TypesUsingSet res = new TypesUsingSet();
			res.theSet.addAll(theSet);
			res.theSet.addAll(tmp.theSet);
			if(res.theSet.size() >= maxSize) {
				if(res.theSet.size() == maxSize) {
					return coneObject;					
				} else {
					System.err.print("More elements than in Object cone:");
					HashSet<TypeReference> t = new HashSet<TypeReference>();
					t.addAll(res.theSet);
					t.removeAll(concreteCone(coneObject.tr).toSet());
					System.err.println(t + "(aditional types)");
				}
			}
			return res;			
		}

		public boolean equals(Object o) {
			if(o instanceof TypesUsingSet) {
				return theSet.equals(((TypesUsingSet)o).theSet);
			}
			return false;
		}
	
		public String toString() {
			return theSet.toString();
		}
	}
}
