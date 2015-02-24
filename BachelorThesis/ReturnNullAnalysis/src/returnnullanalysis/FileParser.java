package returnnullanalysis;

import models.MetModel;
import models.OuterMethodModel;
import models.VarModel;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.SimpleName;

public class FileParser
	{
	private ICompilationUnit compilationUnit;
	private IMethod outerMethod;	//the method where the if (...==null) is located
	private CompilationUnit cUnit;
	private int lineNumber;
	private int endingLine;
	
	class MyASTVisitor extends ASTVisitor
		{
		public boolean visit(MethodDeclaration method)
			{
			if (method.resolveBinding()!=null)
				outerMethod=(IMethod) method.resolveBinding().getJavaElement();
			return true;
			}
	
		public boolean visit (final IfStatement node)
			{
			node.getExpression().accept(new ASTVisitor(){
			public boolean visit(InfixExpression ex)
				{
				Expression left,right;
				InfixExpression.Operator operator;
				lineNumber=cUnit.getLineNumber(node.getStartPosition());
				endingLine=cUnit.getLineNumber(node.getThenStatement().getStartPosition()+node.getThenStatement().getLength());
				
				left=ex.getLeftOperand();
				right=ex.getRightOperand();
				operator=ex.getOperator();
				try
					{
					if (left instanceof NullLiteral)
						{
						if (right instanceof MethodInvocation)
							{
							processMethod((MethodInvocation)right, node, operator);
							}
						else if (right instanceof SimpleName)
							{
							processVariable((SimpleName)right,node, operator);
							}
						}
					else if (right instanceof NullLiteral)
						{
						if (left instanceof MethodInvocation)
							{
							processMethod((MethodInvocation)left, node, operator);
							}
						else if (left instanceof SimpleName)
							{
							processVariable((SimpleName)left,node, operator);
							}
						}
					}
				catch (Exception ex1)
					{
					ex1.printStackTrace();
					}
				return true;
				}
			});
			return true;
			}
		}
	
	private void processMethod(MethodInvocation m, IfStatement node, InfixExpression.Operator op)
		{
		IMethodBinding typeBinding=m.resolveMethodBinding();
		IJavaElement javaElem=typeBinding.getJavaElement();
		if (javaElem instanceof IMethod)
			Pile.getInstance().addMethod(new MetModel((IMethod)javaElem,javaElem.getParent()),
					new OuterMethodModel(outerMethod,lineNumber,endingLine,op,node.getThenStatement(),node.getElseStatement()));
		}

	private void processVariable(SimpleName var, IfStatement node, InfixExpression.Operator op) throws JavaModelException
		{
		IVariableBinding variableBinding = (IVariableBinding) ((SimpleName)var).resolveBinding();
		IJavaElement javaElem=variableBinding.getJavaElement();
		if (variableBinding.isField())
			{
			Pile.getInstance().addVariable(new VarModel(javaElem,javaElem.getParent(),true),
					new OuterMethodModel(outerMethod,lineNumber,endingLine,op,node.getThenStatement(),node.getElseStatement()));
			}
		else
			{
			Pile.getInstance().addVariable(new VarModel(javaElem,javaElem.getParent().getParent(),false),
					new OuterMethodModel(outerMethod,lineNumber,endingLine,op,node.getThenStatement(),node.getElseStatement()));
			}
		}
	
	public CompilationUnit createNewParser ()
		{
		ASTParser parser=ASTParser.newParser(AST.JLS4);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(compilationUnit);
		parser.setResolveBindings(true);
		cUnit=(CompilationUnit)parser.createAST(null);
		return cUnit;
		}

	public void setCompilationUnit(ICompilationUnit compilationUnit)
		{
		this.compilationUnit=compilationUnit;
		}
	
	public void startParsing()
		{
		createNewParser().accept(new MyASTVisitor());
		}
	}