package lrg.patrools.wala.extensions;

import java.io.IOException;

import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeParameter;
import org.eclipse.jdt.core.JavaModelException;

import com.ibm.wala.cast.java.ipa.callgraph.JavaSourceAnalysisScope;
import com.ibm.wala.cast.java.translator.jdt.JDTSourceLoaderImpl;
import com.ibm.wala.cast.loader.AstClass;
import com.ibm.wala.cast.loader.AstMethod;
import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.types.MethodReference;
import com.ibm.wala.types.TypeName;
import com.ibm.wala.types.TypeReference;

import org.eclipse.jdt.core.Signature; 
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;

public class RepresentationsConversionUtilities {
	
	public static IType wala2eclipse(TypeReference theWalaType, ClassHierarchy ch, IJavaProject proj) {
		try {
			final IClass walaClass = ch.lookupClass(theWalaType);
			String address = walaClass.getName().getClassName().toString();
			if(walaClass.getName().getPackage()!=null) {
				address = walaClass.getName().getPackage().toString() + "." + address;
			}
			if(!walaClass.getName().getClassName().toString().contains("<anonymous subclass")) {
				address = address.replace('/', '.');
				address = address.replace('$', '.');
				return proj.findType(address);
			} else {
				for(int i = 0; i < proj.getPackageFragmentRoots().length; i++) {
					IPackageFragmentRoot fragment = proj.getPackageFragmentRoots()[i];
					if(walaClass.getSourceFileName().startsWith(fragment.getUnderlyingResource().getLocation().toOSString())) {
						String relativePath = walaClass.getSourceFileName().substring(fragment.getUnderlyingResource().getLocation().toOSString().length()+1);
						ICompilationUnit icu = (ICompilationUnit)proj.findElement(Path.fromOSString(relativePath));
						ASTParser parser = ASTParser.newParser(AST.JLS3);
						parser.setSource(icu);
						parser.setResolveBindings(true);
						final CompilationUnit cu = (CompilationUnit)parser.createAST(null);
						final IType resultingType[] = new IType[1];
						cu.accept(new ASTVisitor() {						
							public boolean visit(AnonymousClassDeclaration m) {								
								if(((AstClass)walaClass).getSourcePosition().getFirstOffset() >= m.getStartPosition() &&
	 									((AstClass)walaClass).getSourcePosition().getLastOffset() <= (m.getStartPosition() + m.getLength())) {
									IType visitedType = (IType)m.resolveBinding().getJavaElement();
									resultingType[0] = visitedType;
								}
								return true;
							}
						});
						return resultingType[0];
					}
				}
				return null;
			}
		} catch (JavaModelException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static IMethod wala2eclipse(MethodReference theWalaMethod, ClassHierarchy ch, IJavaProject proj) {
		try {
			IType theEclipseDeclaringClass = wala2eclipse(theWalaMethod.getDeclaringClass(), ch, proj);
			for(IMethod anEclipseMethod : theEclipseDeclaringClass.getMethods()) {
				AstMethod aWalaMethod = eclipse2wala(anEclipseMethod, ch);
				if(aWalaMethod != null && aWalaMethod.getSignature().equals(theWalaMethod.getSignature())) {
					return anEclipseMethod;
				}
			}
			return null;
		} catch (JavaModelException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	//This is only for anonymous type for the moment
	private static String computePatroolsTypeSignature(IType eclipseType, char separator, Integer counter) throws JavaModelException {
		//Create a string identifier recognized by wala (it includes the signature of the method where the anonymous type is declared)
		String fullyQualifiedName = null;
		if(eclipseType.getParent() instanceof IMethod) {
			IMethod theEnclosingMethod = (IMethod)eclipseType.getParent();
			String theEnclosingMethodSignature;
			if(theEnclosingMethod.isConstructor()) {
				theEnclosingMethodSignature = "<init>";
			} else {
				theEnclosingMethodSignature = theEnclosingMethod.getElementName();			
			}
			String qn = fullyQualifiedSignatureEclipse(theEnclosingMethod);
			//Replace the return type separator with the specified separator
			qn = qn.replace('/', separator);
			//Continue
			theEnclosingMethodSignature+=qn;
			IType theEnclosingMethodContainerType = ((IType)theEnclosingMethod.getParent());
			String theEnclosingMethodContainer = theEnclosingMethodContainerType.getFullyQualifiedName();
			fullyQualifiedName = theEnclosingMethodContainer.replace('.',separator) + separator + theEnclosingMethodSignature;
		} else if(eclipseType.getParent() instanceof IField) {
			IField theEnclosingField = (IField)eclipseType.getParent();
			fullyQualifiedName = ((IType)theEnclosingField.getParent()).getFullyQualifiedName().replace('.', separator)  + separator + "<init>";
		}
		//Locate the supertype
		String[][] tmp = eclipseType.resolveType(eclipseType.getSuperclassName());
		if(tmp.length != 1) {
			return null;
		}
		IType theSuperType = eclipseType.getJavaProject().findType(tmp[0][0] + "." + tmp[0][1]);
		//Compute the final type identifier recognized by wala
		if(theSuperType.isInterface()) {
			fullyQualifiedName += separator + "<anonymous subclass of " + "java.lang.Object" + ">";
		} else {
			fullyQualifiedName += separator + "<anonymous subclass of " + tmp[0][0] + "." + tmp[0][1] + ">";					
			//Prepare the name for inner classes (the name must be in $ format to be recognized by wala)
			while(theSuperType.getDeclaringType() != null) {
				fullyQualifiedName = fullyQualifiedName.substring(0,fullyQualifiedName.lastIndexOf('.')) + "$" + fullyQualifiedName.substring(fullyQualifiedName.lastIndexOf('.') + 1);
				theSuperType = theSuperType.getDeclaringType();
			}
		}
		//This works only for java 1.4
		if(counter == null) {
			fullyQualifiedName += "$" + eclipseType.getOccurrenceCount();
		} else {
			fullyQualifiedName += "$" + counter;			
		}
		//Code for > 1.4
		//String rest = theEnclosingMethodContainerType.getTypeQualifiedName();
		//if(rest.indexOf('$') >= 0) {
		//	rest = rest.substring(rest.indexOf('$'));
		//}
		//fullyQualifiedName += rest + "$" + eclipseType.getOccurrenceCount(); 
		return fullyQualifiedName;
	}
	
	public static IClass eclipse2wala(ClassHierarchy ch, IType eclipseType) {
		try {
			if(eclipseType.isAnonymous()) {
				String fullyQualifiedName = "L" + computePatroolsTypeSignature(eclipseType,'/',null);
				TypeName tn = TypeName.string2TypeName(fullyQualifiedName);
				JDTSourceLoaderImpl sourceLoader = (JDTSourceLoaderImpl)ch.getFactory().getLoader(JavaSourceAnalysisScope.SOURCE, ch, ch.getScope());
				IClass result = sourceLoader.lookupClass(tn);
				if(result == null) {
					//Try a hack to solve the incompatibilities between wala and eclipse numbering of anonymous classes
					for(int i = 1 ; i < 100; i++) {
 						String possibleNames = fullyQualifiedName.substring(0,fullyQualifiedName.lastIndexOf('$') + 1) + i;
 						tn = TypeName.string2TypeName(possibleNames);
 						sourceLoader = (JDTSourceLoaderImpl)ch.getFactory().getLoader(JavaSourceAnalysisScope.SOURCE, ch, ch.getScope());
 						result = sourceLoader.lookupClass(tn);
 						if(result != null && result instanceof AstClass) {
 							//Verification heuristic (via source code position)
 							if(((AstClass)result).getSourcePosition().getFirstOffset() >= eclipseType.getSourceRange().getOffset() &&
 									((AstClass)result).getSourcePosition().getFirstOffset() <= (eclipseType.getSourceRange().getOffset() + eclipseType.getSourceRange().getLength())) {
 								break;
 							}
 						}
 						result = null;
					}
				}
				return result;
			} else {
				String fullyQualifiedName = eclipseType.getFullyQualifiedName();
				fullyQualifiedName = "L" + fullyQualifiedName.replace('.','/');
				TypeName tn = TypeName.string2TypeName(fullyQualifiedName);
				JDTSourceLoaderImpl sourceLoader = (JDTSourceLoaderImpl)ch.getFactory().getLoader(JavaSourceAnalysisScope.SOURCE, ch, ch.getScope());
				return sourceLoader.lookupClass(tn);
			}
		} catch(IOException e) {
			throw new RuntimeException(e.getMessage());			
		} catch (JavaModelException e) {
			throw new RuntimeException(e.getMessage());			
		}
	}
	
	public static AstMethod eclipse2wala(IMethod theMethodInEclipse, ClassHierarchy ch) {
		try {
			//Because Eclipse does not insert $ (but inserts .) where necessary when resolving the 
			//type from a method signature, we must replace $ (in both Eclispe and Wala addresses) with /.
			final IClass theClass = eclipse2wala(ch,theMethodInEclipse.getDeclaringType());
			String address;
			if(theMethodInEclipse.getDeclaringType().isAnonymous()) {
				int counter = theClass.getName().toString().lastIndexOf('$') + 1;
				counter = Integer.parseInt(theClass.getName().toString().substring(counter));
				address = computePatroolsTypeSignature(theMethodInEclipse.getDeclaringType(),'.',counter).replace('$', '/');				
			} else {
				address = theMethodInEclipse.getDeclaringType().getFullyQualifiedName().replace('$', '/');
			}
			if(theMethodInEclipse.isConstructor()) {
				address += ".<init>";
			} else {
				address += "." + theMethodInEclipse.getElementName();
			}
			address += RepresentationsConversionUtilities.fullyQualifiedSignatureEclipse(theMethodInEclipse);
			for(com.ibm.wala.classLoader.IMethod theMethodInWala : theClass.getAllMethods()) {
				if(theMethodInWala instanceof AstMethod) {
					AstMethod tmp = (AstMethod)theMethodInWala;
					String walaAddress = tmp.getSignature().replace('$', '/');
					if(walaAddress.equals(address)) {
						return tmp;
					}
				}
			}			
			return null;			
		} catch(JavaModelException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String fullyQualifiedSignatureEclipse(IMethod theMethod) {
		try {
			String rez = "(";
			for(String tsig : theMethod.getParameterTypes()) {
					rez += resolveTypeNameInContext(tsig,theMethod.getDeclaringType(),theMethod);
			}
			rez += ")";
			rez += resolveTypeNameInContext(theMethod.getReturnType(),theMethod.getDeclaringType(),theMethod);
			return rez;
		} catch(JavaModelException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String resolveTypeNameInContext(String tsig, IType contextType, IMethod theMethod) throws JavaModelException {
		int arrayCount = Signature.getArrayCount(tsig);
		if(tsig.charAt(arrayCount) == Signature.C_TYPE_VARIABLE) {
			String newSig = "" + Signature.C_TYPE_VARIABLE + Signature.C_SEMICOLON;
			for(int i = 0; i < arrayCount; i++) {
				newSig = Signature.C_ARRAY + newSig;
			}
			return newSig;
		}
		if(tsig.charAt(arrayCount) == Signature.C_UNRESOLVED) {
			int lastNamePos;
			String newSig;
			int bracket = tsig.indexOf(Signature.C_GENERIC_START, arrayCount + 1);
			if (bracket > 0) {
				lastNamePos = bracket;
				newSig = ";";
			} else {
				int semi= tsig.indexOf(Signature.C_SEMICOLON, arrayCount + 1);
				lastNamePos = semi;
				newSig = tsig.substring(lastNamePos);
			}
			String resolvedType = JavaModelUtil.getResolvedTypeName(tsig, contextType);	
			if(resolvedType == null) {
				//Generic (?)
				String potentialTemplateName = tsig.substring(arrayCount + 1,tsig.length()-1);
				ITypeParameter tp = contextType.getTypeParameter(potentialTemplateName);
				if(tp.exists()) {
					newSig = "" + Signature.C_TYPE_VARIABLE + Signature.C_SEMICOLON;
					for(int i = 0; i < arrayCount; i++) {
						newSig = Signature.C_ARRAY + newSig;
					}
					return newSig;
				}
				tp = theMethod.getTypeParameter(potentialTemplateName);
				if(tp.exists()) {
					newSig = "" + Signature.C_TYPE_VARIABLE + Signature.C_SEMICOLON;
					for(int i = 0; i < arrayCount; i++) {
						newSig = Signature.C_ARRAY + newSig;
					}
					return newSig;
				}
				throw new RuntimeException("Cannot resolve type " + tsig + " in context " + contextType.getFullyQualifiedName());
			}
			newSig = resolvedType.replace('.', '/') + newSig;
			newSig = Signature.C_RESOLVED + newSig;
			for(int i = 0; i < arrayCount; i++) {
				newSig = Signature.C_ARRAY + newSig;
			}
			return newSig;
		} else {
			return tsig;
		}
	}
}
