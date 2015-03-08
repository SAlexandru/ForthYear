package lrg.patrools.plugins.properties;

import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.plugins.properties.PropertyComputer;

public class NoOfExternalClientsMethod extends PropertyComputer {

	public NoOfExternalClientsMethod(){
		super("no of external clients","no of external clients", "method", "numeric");
	}
	
	 public ResultEntity compute(AbstractEntityInterface anEntity)
	    {
	        return new ResultEntity(anEntity.getGroup("base class external clients").size());
	    }
}
