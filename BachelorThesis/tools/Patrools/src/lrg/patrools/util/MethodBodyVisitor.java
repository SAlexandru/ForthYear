package lrg.patrools.util;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodBodyVisitor extends ASTVisitor {
	
	private IMethod theMethod;
	private ASTVisitor visitor;
	
	public MethodBodyVisitor(IMethod theMethod) {
		this.theMethod = theMethod;
	}
	
	public boolean visit(MethodDeclaration node) {
		if(node.resolveBinding() == null) {
			System.err.println("Cannot resolve " + node.getName().getFullyQualifiedName());
			return true;
		}
		if(node.resolveBinding().getJavaElement().equals(theMethod)) {
			node.accept(visitor);
			return false;
		}
		return true;
	}

	public void process(ASTVisitor astVisitor) {
		this.visitor = astVisitor;
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(theMethod.getCompilationUnit());
		parser.setResolveBindings(true);
		CompilationUnit cu = (CompilationUnit)parser.createAST(null);
		cu.accept(this);
	}

}
