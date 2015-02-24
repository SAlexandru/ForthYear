package models;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Statement;

public class MetModel extends Model
	{
	private IMethod nullMethod;
	
	public MetModel (IMethod nullMethod,IJavaElement declaringClass)
		{
		super(declaringClass);
		this.nullMethod=nullMethod;
		}
	
	public boolean equals(Object o)
		{
		if (o instanceof MetModel)
			return this.nullMethod.equals(((MetModel) o).getMethod());
		return false;
		}

	public String toString()
		{
		return nullMethod.getElementName()+" "+super.toString();
		}
	
	public String getName()
		{
		return nullMethod.getElementName();
		}
	
	public IMethod getMethod()
		{
		return nullMethod;
		}

	}
