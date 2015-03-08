package lrg.patrools.plugins.properties;

import java.util.Iterator;

import org.eclipse.jdt.core.IMethod;

import plugins.Wrapper;
import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.plugins.properties.PropertyComputer;
import lrg.patrools.wala.extensions.RepresentationsConversionUtilities;

public class OverriddesALibraryMethod extends PropertyComputer {

	public OverriddesALibraryMethod() {
		super("overriddes a library method", "overriddes a library method", "method", "boolean");
	}

	 public ResultEntity compute(AbstractEntityInterface theMethod) {	 
		Wrapper aMethod = (Wrapper)theMethod;
		Wrapper aType = (Wrapper)aMethod.belongsTo("class"); 

		GroupEntity methodsOfAncestors = aType.getGroup("all ancestors").contains("method group");
		Iterator it = methodsOfAncestors.iterator();
		Wrapper ancestorMethod;

		while (it.hasNext()) {
			ancestorMethod = (Wrapper) it.next();
			if (!(Boolean)ancestorMethod.getProperty("model entity").getValue() && isOverriding(aMethod, ancestorMethod)) {
				return new ResultEntity(true);
			}
		}
		return new ResultEntity(false);
	}

	private boolean isOverriding(Wrapper aMethod, Wrapper ancestorMethod) {
		if (aMethod.getName().compareTo(ancestorMethod.getName()) != 0) return false;

		if(ancestorMethod.getElement() instanceof IMethod == false) 
			return false;

		IMethod unwrappedMethod = (IMethod) aMethod.getElement();
		IMethod unwrappedAncestorMethod = (IMethod) ancestorMethod.getElement();
		
		String methodSignature = RepresentationsConversionUtilities.fullyQualifiedSignatureEclipse(unwrappedMethod).replace('/', '.');
		String ancestorSignature = RepresentationsConversionUtilities.fullyQualifiedSignatureEclipse(unwrappedAncestorMethod).replace('/', '.');
		return methodSignature.compareTo(ancestorSignature) == 0;
	}


}
