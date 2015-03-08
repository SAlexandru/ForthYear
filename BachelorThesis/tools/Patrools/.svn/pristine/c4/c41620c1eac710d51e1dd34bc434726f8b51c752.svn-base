package lrg.patrools.plugins.properties;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.plugins.properties.PropertyComputer;

public class MaxClassInvocationsLevelOfAbstraction extends PropertyComputer{
	
	public MaxClassInvocationsLevelOfAbstraction() {
		super("max class invocations level of abstraction", "max class invocations level of abstraction", "class", "numeric");
	}
	
	public ResultEntity compute(final AbstractEntityInterface aClass) {
		GroupEntity methods = aClass.getGroup("method group");
		
		double max = 0; 
		boolean exists = false;
		for(int i = 0; i < methods.size(); i++) {
			double value = (Double)methods.getElementAt(i).getProperty("max invocations level of abstraction").getValue();
			if(value >= 0) {
				if(!exists) {
					max = value;
					exists = true;
				} else if(max < value) {
					max = value;
				}
			}
		}
		if(exists) {
			return new ResultEntity(max);
		} else {
			return new ResultEntity(-1);
		}
	}

}
