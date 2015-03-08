package lrg.patrools.plugins.filters;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.plugins.Descriptor;
import lrg.common.abstractions.plugins.filters.FilteringRule;

public class IsAbstractMethod extends FilteringRule {

	public IsAbstractMethod() {
		super(new Descriptor("is abstract method", "method"));
	}

    public boolean applyFilter(AbstractEntityInterface aMethod) {  
    	boolean isAbstract = (Boolean)aMethod.getProperty("is abstract").getValue();
    	isAbstract |= (Boolean)aMethod.belongsTo("class").getProperty("is interface").getValue();
    	return isAbstract;
    }

}
