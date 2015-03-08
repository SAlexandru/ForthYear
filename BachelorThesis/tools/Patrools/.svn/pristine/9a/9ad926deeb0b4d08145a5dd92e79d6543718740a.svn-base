package lrg.patrools.plugins.properties;

import org.eclipse.jdt.core.IType;

import plugins.Wrapper;
import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.plugins.properties.PropertyComputer;

public class FullQualifiedClassName extends PropertyComputer {

	public FullQualifiedClassName() {
		super("full qualified class name", "full qualified class name", "class", "string");
	}
	
	 public ResultEntity compute(AbstractEntityInterface aClass) {
		 return new ResultEntity(((IType)((Wrapper)aClass).getElement()).getFullyQualifiedName());
	 }

}
