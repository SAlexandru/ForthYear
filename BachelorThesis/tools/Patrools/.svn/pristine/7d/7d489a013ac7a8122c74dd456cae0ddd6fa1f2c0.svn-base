package lrg.patrools.plugins.groups;

import java.util.ArrayList;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.plugins.groups.GroupBuilder;

public class HighestClassOfOverriddenMethod extends GroupBuilder {

	public HighestClassOfOverriddenMethod() {
		super("highest overridden method", "highest overridden method", "method");
	}

	public ArrayList<AbstractEntityInterface> buildGroup(AbstractEntityInterface aMethod) {   
		ArrayList<AbstractEntityInterface> res = new ArrayList();
		GroupEntity allOverriddenMethods = aMethod.getGroup("methods overriden");
		AbstractEntityInterface theMaxHitType = aMethod.belongsTo("class");
		for(int i = 0; i < allOverriddenMethods.size(); i++) {
			AbstractEntityInterface aSuperMethod = allOverriddenMethods.getElementAt(i);
			if(((Double)theMaxHitType.getProperty("HIT").getValue()) < (Double)aSuperMethod.belongsTo("class").getProperty("HIT").getValue()) {
				theMaxHitType = aSuperMethod.belongsTo("class");
			} else if(((Double)theMaxHitType.getProperty("HIT").getValue()).equals((Double)aSuperMethod.belongsTo("class").getProperty("HIT").getValue())) {
				 System.out.println("Weird case!");
			}
		 }
		res.add(theMaxHitType);
        return res;
	}

}
