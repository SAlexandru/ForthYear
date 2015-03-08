package lrg.patrools.plugins.properties;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.plugins.properties.PropertyComputer;

public class AvgClassInvocationsLevelOfAbstraction extends PropertyComputer{
	
	public AvgClassInvocationsLevelOfAbstraction() {
		super("avg class invocations level of abstraction", "avg class invocations level of abstraction", "class", "numeric");
	}
	
	public ResultEntity compute(final AbstractEntityInterface aClass) {
		GroupEntity methods = aClass.getGroup("method group");
		
		double sum = 0, num = 0;
		for(int i = 0; i < methods.size(); i++) {
			double value = (Double)methods.getElementAt(i).getProperty("avg invocations level of abstraction").getValue();
			if(value >= 0) {
				sum+=value;
				num++;
			}
		}
		if(num > 0) {
			return new ResultEntity(sum/num);
		} else {
			return new ResultEntity(-1);
		}
	}

}
