package lrg.patrools.plugins.properties;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;

import plugins.Wrapper;
import lrg.common.abstractions.entities.AbstractEntity;
import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.entities.ResultEntity;
import lrg.common.abstractions.managers.EntityTypeManager;
import lrg.common.abstractions.plugins.properties.PropertyComputer;
import lrg.patrools.util.MethodBodyVisitor;

public class AvgInvocationLevelOfAbstraction extends PropertyComputer {

	public AvgInvocationLevelOfAbstraction() {
		super("avg invocations level of abstraction", "avg invocations level of abstraction", "method", "numeric");
	}
	
	 public ResultEntity compute(final AbstractEntityInterface aMethod) {
	 
		 final IMethod theClient_Eclipse = (IMethod) ((Wrapper)aMethod).getElement();
		 MethodBodyVisitor astMethodVisitor = new MethodBodyVisitor(theClient_Eclipse);
		 final Double[] result = new Double[] {0.0, 0.0};
		 
		 astMethodVisitor.process(new ASTVisitor() {				 
			 public boolean visit(MethodInvocation invocation) {
				 
				 //Process each invocation in the current client
				 IMethodBinding theCalledMethodBinding = invocation.resolveMethodBinding();
				 if(theCalledMethodBinding == null) {
					 System.err.println("Cannot resolve the binding to " + invocation + " in " + theClient_Eclipse.getDeclaringType().getFullyQualifiedName() + ":" + theClient_Eclipse.getElementName());
					 return true;
				 }
				 if(theCalledMethodBinding.getJavaElement() == null) {
					 System.err.println("Cannot find JavaElement for " + invocation + " in " + theClient_Eclipse.getDeclaringType().getFullyQualifiedName() + ":" + theClient_Eclipse.getElementName());					 
					 return true;
				 }
				 Wrapper theInvokedMethod = Wrapper.createInstance(theCalledMethodBinding.getJavaElement());
				 theInvokedMethod.setEntityType(EntityTypeManager.getEntityTypeForName("method"));
				 
				 //Check if the call is to an interface method
				 if(!(Boolean)((ResultEntity)theInvokedMethod.getProperty("model entity")).getValue()) {
					 return true;			 
				 }
				 if((Boolean)((ResultEntity)theInvokedMethod.getProperty("overriddes a library method")).getValue()) {
					 return true;			 
				 }				 
				 if((Boolean)((ResultEntity)theInvokedMethod.getProperty("is static")).getValue()) {
					 return true;
				 }
				 if((Boolean)((ResultEntity)theInvokedMethod.getProperty("is constructor")).getValue()) {
					 return true;
				 }
				 if(!(Boolean)((ResultEntity)theInvokedMethod.getProperty("is public")).getValue() && !(Boolean)((ResultEntity)theInvokedMethod.getProperty("is protected")).getValue()) {
					 return true;			 
				 }
				 				 
				 //Find the highest declaration of the invoked method in the hierarchy
				 AbstractEntityInterface highestType = theInvokedMethod.getGroup("highest overridden method").getElementAt(0);
				 GroupEntity concreteCone = highestType.getGroup("concrete cone").distinct();
				 
				 //Find the static concrete cone from the static type of the target 
				 Expression theTargetStaticExpression = invocation.getExpression();
				 IType theTargetStaticType;
				 if(theTargetStaticExpression != null) {
					 ITypeBinding exprTypeBinding = theTargetStaticExpression.resolveTypeBinding();
					 if(exprTypeBinding == null) {
						 System.err.println("Cannot resolve the binding to " + theTargetStaticExpression + " in " + theClient_Eclipse.getDeclaringType().getFullyQualifiedName() + ":" + theClient_Eclipse.getElementName());
						 return true; 
					 }
					 if(exprTypeBinding.getJavaElement() instanceof IType) {
						 theTargetStaticType = (IType)exprTypeBinding.getJavaElement();						 
					 } else {
						 System.err.println("Cannot resolve the binding to (Generic?) " + theTargetStaticExpression + " in " + theClient_Eclipse.getDeclaringType().getFullyQualifiedName() + ":" + theClient_Eclipse.getElementName());						 
						 return true;
					 }
				 } else {
					 //If it is outside the hierarchy
					 if(highestType.getGroup("all descendants").union((AbstractEntity) highestType).isInGroup(aMethod.belongsTo("class"))) {
						 //The client is in hierarchy and so the target is this
						 theTargetStaticType = (IType) ((Wrapper)aMethod.belongsTo("class")).getElement();
					 } else {
						 //It should be a call to an enclosing instance this - ignore
						 return true;
					 }
				 }
				 Wrapper wrapper = Wrapper.createInstance(theTargetStaticType);
				 wrapper.setEntityType(EntityTypeManager.getEntityTypeForName("class"));
				 GroupEntity staticConcreteCone = wrapper.getGroup("concrete cone").distinct();
				 
				 //Characterize
				 if(staticConcreteCone.size() == 0) {
					 return true;
				 }
				 				 
				 GroupEntity intersection = staticConcreteCone.intersect(concreteCone);
				 if(intersection.size() != 0) {
						 if(intersection.size() != 1) {
							 //System.out.println((1.0 * staticConcreteCone.size() - 1) / (concreteCone.size() - 1));
							 result[0]+= (1.0 * staticConcreteCone.size() - 1) / (concreteCone.size() - 1);
							 result[1]++;
						 } else {
							 //System.out.println(0);
							 result[1]++;
						 }
				 }
				 return true;
			 }
		 });

		 if(result[1] != 0) {
			 return new ResultEntity(result[0]/result[1]);
		 } else {
			 return new ResultEntity(-1);
		 }
	 }
	 
}
