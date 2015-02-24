package models;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Statement;

public class OuterMethodModel
	{
	private IMethod outerMethod;
	private InfixExpression.Operator operator;
	private Statement thenBlock;
	private Statement elseBlock;
	private int lineNumber;
	private int endingLine;
	
	public OuterMethodModel (IMethod outerMethod,int lineNumber,int endingLine,InfixExpression.Operator operator,Statement thenBlock,Statement elseBlock)
		{
		this.outerMethod=outerMethod;
		this.lineNumber=lineNumber;
		this.endingLine=endingLine;
		this.operator=operator;
		this.thenBlock=thenBlock;
		this.elseBlock=elseBlock;
		}
	
	public IMethod getMethod()
		{
		return outerMethod;
		}
	
	public Statement getThenBlock()
		{
		return thenBlock;
		}
	
	public Statement getElseBlock()
		{
		return elseBlock;
		}
	
	public InfixExpression.Operator getOperator()
		{
		return operator;
		}
	
	public int getLineNumber()
		{
		return lineNumber;
		}

	public String getCustomName()
		{
		try{
			String s=outerMethod.getDeclaringType().getElementName()+"::"+outerMethod.getElementName()
					+" : "+outerMethod.getReturnType();
			return s;
			}
		catch (Exception ex){}
		return null;
		}

	public int getEndingLine()
		{
		return endingLine;
		}
	}
