package lrg.patrools.plugins.groups;

import java.util.ArrayList;

import lrg.common.abstractions.entities.AbstractEntity;
import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.plugins.groups.GroupBuilder;

@SuppressWarnings("serial")
public class ExternalClientsGroup extends GroupBuilder {

	public ExternalClientsGroup() {
		super("base class external clients","base class external clients","method");
	}

	@Override
	public ArrayList<AbstractEntityInterface> buildGroup(AbstractEntityInterface aMethod) {
       
		if((Boolean)((ResultEntity)aMethod.getProperty("is private")).getValue())
			return new ArrayList<AbstractEntityInterface>();
		if((Boolean)((ResultEntity)aMethod.getProperty("is static")).getValue())
			return new ArrayList<AbstractEntityInterface>();
		if((Boolean)((ResultEntity)aMethod.getProperty("is constructor")).getValue())
			return new ArrayList<AbstractEntityInterface>();
		
		GroupEntity allResolutionMethods = aMethod.getGroup("method chain");
        GroupEntity allClients = allResolutionMethods.getGroup("operations invoking me").distinct();
        GroupEntity allHierarchy = aMethod.belongsTo("class").getGroup("all ancestors").union(aMethod.belongsTo("class").
                    getGroup("all descendants")).union(aMethod.belongsTo("class")).distinct();

        ArrayList<AbstractEntityInterface> result = new ArrayList<AbstractEntityInterface>();
        for(AbstractEntityInterface aClient : (ArrayList<AbstractEntityInterface>)allClients.getElements()) {
        	//TODO: CodePro bug? (methods referring in its comments another method appears as invoking it - including of it is abstract 
        	//This is patch code
        	if((Boolean)aClient.getProperty("is abstract").getValue() || (Boolean)aClient.belongsTo("class").getProperty("is interface").getValue()) {
        		continue;
        	}
        	//End patch
        	if(!allHierarchy.isInGroup(aClient.belongsTo("class")) && !result.contains(aClient)) result.add(aClient);                        
        }    	

        return result;
	}

}
