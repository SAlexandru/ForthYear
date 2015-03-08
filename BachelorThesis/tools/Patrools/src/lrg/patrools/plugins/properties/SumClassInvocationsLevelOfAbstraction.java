package lrg.patrools.plugins.properties;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.plugins.properties.PropertyComputer;

public class SumClassInvocationsLevelOfAbstraction extends PropertyComputer{
	
	public SumClassInvocationsLevelOfAbstraction() {
		super("sum class invocations level of abstraction", "sum class invocations level of abstraction", "class", "numeric");
	}
	
	public ResultEntity compute(final AbstractEntityInterface aClass) {
		GroupEntity methods = aClass.getGroup("method group");
		
		double sum = 0; 
		boolean exists = false;
		for(int i = 0; i < methods.size(); i++) {
			double value = (Double)methods.getElementAt(i).getProperty("sum invocations level of abstraction").getValue();
			if(value >= 0) {
				if(!exists) {
					sum = value;
					exists = true;
				} else {
					sum += value;
				}
			}
		}
		if(exists) {
			return new ResultEntity(sum);
		} else {
			return new ResultEntity(-1);
		}
	}

}
