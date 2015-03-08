package lrg.patrools.plugins.properties;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.plugins.properties.PropertyComputer;

public class MinClassInvocationsLevelOfAbstraction extends PropertyComputer{
	
	public MinClassInvocationsLevelOfAbstraction() {
		super("min class invocations level of abstraction", "min class invocations level of abstraction", "class", "numeric");
	}
	
	public ResultEntity compute(final AbstractEntityInterface aClass) {
		GroupEntity methods = aClass.getGroup("method group");
		
		double min = 0; 
		boolean exists = false;
		for(int i = 0; i < methods.size(); i++) {
			double value = (Double)methods.getElementAt(i).getProperty("min invocations level of abstraction").getValue();
			if(value >= 0) {
				if(!exists) {
					min = value;
					exists = true;
				} else if(min > value) {
					min = value;
				}
			}
		}
		if(exists) {
			return new ResultEntity(min);
		} else {
			return new ResultEntity(-1);
		}
	}

}
