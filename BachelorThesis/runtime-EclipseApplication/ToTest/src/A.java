import xmetamodel.XClass;

import com.salexandru.corex.interfaces.IPropertyComputer;
import com.salexandru.corex.metaAnnotation.PropertyComputer;


@PropertyComputer
public class A implements IPropertyComputer<XClass> {

	@Override
	public double compute(XClass entity) {
		// TODO Auto-generated method stub
		return 0;
	}

}
