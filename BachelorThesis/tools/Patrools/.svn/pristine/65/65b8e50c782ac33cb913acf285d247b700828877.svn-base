package lrg.patrools.plugins.groups;

import java.util.ArrayList;

import lrg.common.abstractions.entities.AbstractEntity;
import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.plugins.groups.GroupBuilder;

public class MethodChain extends GroupBuilder {

	public MethodChain() {
		super("method chain","method chain","method");
	}

	@Override
	public ArrayList<AbstractEntityInterface> buildGroup(AbstractEntityInterface aMethod) {
       
		if((Boolean)((ResultEntity)aMethod.getProperty("is private")).getValue())
			return new ArrayList<AbstractEntityInterface>();
		if((Boolean)((ResultEntity)aMethod.getProperty("is static")).getValue())
			return new ArrayList<AbstractEntityInterface>();
		if((Boolean)((ResultEntity)aMethod.getProperty("is constructor")).getValue())
			return new ArrayList<AbstractEntityInterface>();
		
		GroupEntity allResolutionMethods = aMethod.getGroup("methods overriden").union(aMethod.getGroup("methods overriding")).union((AbstractEntity)aMethod).distinct();
        return allResolutionMethods.getElements();
	}
	
}
