package src;
import xmetamodel.XClass;
import xmetamodel.XCode;

import com.salexandru.corex.interfaces.Group;
import com.salexandru.corex.interfaces.IGroupBuilder;
import com.salexandru.corex.metaAnnotation.GroupBuilder;

@GroupBuilder
/**
 * 
 * @author salexandru
 *	mama are mere :D
 *
 */
public class B implements IGroupBuilder<XClass, XCode> {
	@Override
	public Group<XClass> buildGroup(XCode entity) {
		// TODO Auto-generated method stub
		return null;
	}
}
