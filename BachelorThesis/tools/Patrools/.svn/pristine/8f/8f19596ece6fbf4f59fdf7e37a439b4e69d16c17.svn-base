package lrg.patrools.plugins.filters;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.plugins.Descriptor;
import lrg.common.abstractions.plugins.filters.FilteringRule;

public class SourceClassesWithNameAsContainerFile extends FilteringRule {

	public SourceClassesWithNameAsContainerFile() {
		super(new Descriptor("source classes with name as their file", "class"));
	}

    public boolean applyFilter(AbstractEntityInterface aClass) {    	    
    	return (aClass.getName()+".java").equals(aClass.getProperty("class definition file").getValue());
    }


}
