package models;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Statement;

public class VarModel extends Model
	{
	private IJavaElement variable;
	private boolean isField;
	
	public VarModel(IJavaElement variable,IJavaElement declaringClass,boolean isField)
		{
		super(declaringClass);
		this.variable=variable;
		this.isField=isField;
		}
	
	@Override
	public boolean equals(Object o)
		{
		if (o instanceof VarModel)
			return this.variable.equals(((VarModel) o).getVariable());
		return false;
		}
	
	public IJavaElement getVariable()
		{
		return variable;
		}
	
	public String toString()
		{
		return variable.getElementName()+" "+super.toString();
		}
	
	public String getName()
		{
		return variable.getElementName();
		}
	
	public String getIsField()
		{
		if (isField==true)
			return "yes";
		return "no";
		}
	}
