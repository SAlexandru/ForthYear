package models;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.Statement;

abstract class Model {
	private IJavaElement declaringClass; // the class where the method/field is
											// declared
	private List<OuterMethodModel> outerMethods = new ArrayList<OuterMethodModel>(); // the
																						// methods
																						// where
																						// this
																						// method/variable
																						// is
																						// tested

	public Model(IJavaElement declaringClass) {
		this.declaringClass = declaringClass;
	}

	public void addOuterMethod(OuterMethodModel method) {
		outerMethods.add(method);
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("DECLARING CLASS: " + declaringClass.getElementName()
				+ "OUTER METHODS: ");
		return s.toString();
	}

	public String getDeclaringClass() {
		return declaringClass.getElementName();
	}

	public List<OuterMethodModel> getOuterMethods() {
		return outerMethods;
	}

	public String getOuterMethodsAsString() {
		String s = "";
		for (OuterMethodModel m : outerMethods)
			s = s + m.getMethod().getElementName() + " ";
		return s;
	}
}
