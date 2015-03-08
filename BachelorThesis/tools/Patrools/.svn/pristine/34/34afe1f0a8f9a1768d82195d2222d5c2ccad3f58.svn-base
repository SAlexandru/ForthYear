package lrg.patrools.wala.extensions;

import java.util.Set;

import com.ibm.wala.types.TypeReference;

public abstract class TypesSet {
		
	public abstract TypesSet union(TypesSet op);

	public abstract TypesSet intersection(TypesSet op);

	public abstract TypesSet difference(TypesSet op);

	public abstract Set<TypeReference> toSet();
		
	public abstract boolean equals(Object o);

}
