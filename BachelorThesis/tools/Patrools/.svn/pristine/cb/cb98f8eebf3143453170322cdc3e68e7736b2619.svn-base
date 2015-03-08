package lrg.patrools.plugins.groups;

import java.util.ArrayList;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.plugins.filters.composed.NotComposedFilteringRule;
import lrg.common.abstractions.plugins.groups.GroupBuilder;
import lrg.insider.plugins.filters.memoria.classes.IsInner;
import lrg.insider.plugins.filters.memoria.classes.IsInterface;

public class ModelClassesWithoutInterfacesAndInner  extends GroupBuilder {

	public ModelClassesWithoutInterfacesAndInner() {
		super("model classes without inner and interfaces","model classes without inner and interfaces","system");
	}

	@Override
	public ArrayList buildGroup(AbstractEntityInterface aSystem) {
    	GroupEntity resultGroup = 
    		aSystem.getGroup("class group").
    			applyFilter("model class").
    			applyFilter(new NotComposedFilteringRule(new IsInterface())).
    			applyFilter(new NotComposedFilteringRule(new IsInner()));
    	return resultGroup.getElements();
	}
}
