package lrg.patrools.plugins.groups;

import java.util.ArrayList;

import lrg.common.abstractions.entities.AbstractEntity;
import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.plugins.filters.FilteringRule;
import lrg.common.abstractions.plugins.filters.composed.NotComposedFilteringRule;
import lrg.common.abstractions.plugins.groups.GroupBuilder;

public class ConcreteCone extends GroupBuilder {

	public ConcreteCone() {
		super("concrete cone","concrete cone","class");
	}

	@Override
	public ArrayList<AbstractEntityInterface> buildGroup(AbstractEntityInterface aClass) {
		GroupEntity allSubclasses = aClass.getGroup("all descendants").union((AbstractEntity)aClass).distinct();
        GroupEntity allConcrete = allSubclasses.
        	applyFilter(new NotComposedFilteringRule(new FilteringRule("is abstract","is true","class"))).
        	applyFilter(new NotComposedFilteringRule(new FilteringRule("is interface","is true","class")));
        return allConcrete.getElements();
	}

}
