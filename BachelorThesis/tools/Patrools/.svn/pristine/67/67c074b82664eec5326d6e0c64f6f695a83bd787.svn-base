package lrg.patrools.plugins.properties;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.plugins.properties.PropertyComputer;

import org.eclipse.jdt.core.IType;

import plugins.Wrapper;

public class ClassDefinitionFile extends PropertyComputer {

	public ClassDefinitionFile() {
		super("class definition file", "class definition file", "class", "string");
	}
	
	 public ResultEntity compute(AbstractEntityInterface aClass) {
		 return new ResultEntity(((IType)((Wrapper)aClass).getElement()).getResource().getName());
	 }

}
