package returnnullanalysis;

import models.OuterMethodModel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.ExtractMethodDescriptor;
import org.eclipse.ltk.core.refactoring.*;
import org.eclipse.ltk.core.refactoring.resource.RenameResourceDescriptor;
import org.eclipse.jdt.internal.corext.refactoring.code.ExtractMethodRefactoring;

import utils.Utils;

public class Refactoring {
	public void createClass(OuterMethodModel m) {
		/*
		 * IJavaProject
		 * javaProject=m.getMethod().getCompilationUnit().getJavaProject();
		 * IProject project=javaProject.getProject(); IFolder folder =
		 * project.getFolder("src"); IPackageFragmentRoot srcFolder =
		 * javaProject.getPackageFragmentRoot(folder); StringBuilder content=new
		 * StringBuilder(); String returnType=""; String parameters=""; String
		 * className=m.getMethod().getElementName(); char[]
		 * c=className.toCharArray(); c[0]=Character.toUpperCase(c[0]);
		 * className=new String(c); try { IPackageFragment fragment =
		 * srcFolder.createPackageFragment("nullObjectRefactor",true,null);
		 * content.append("package "+fragment.getElementName()+";\n\n");
		 * content.append("public interface "+className+"\n\t{\n");
		 * content.append
		 * ("\tpublic abstract "+returnType+Utils.GENERIC_METHOD_NAME
		 * +"("+parameters+");\n"); content.append("\t}"); ICompilationUnit
		 * abstractClass
		 * =fragment.createCompilationUnit(className+".java",content
		 * .toString(),true,null); content.delete(0,content.capacity());
		 * content.append("package "+fragment.getElementName()+";\n\n");
		 * content.
		 * append("public class "+Utils.NOT_NULL_CLASS+className+"\n\t{\n");
		 * content.append("\t}"); ICompilationUnit
		 * notNullClass=fragment.createCompilationUnit
		 * (Utils.NOT_NULL_CLASS+className
		 * +".java",content.toString(),true,null);
		 * content.delete(0,content.capacity());
		 * content.append("package "+fragment.getElementName()+";\n\n");
		 * content.append("public class "+Utils.NULL_CLASS+className+"\n\t{\n");
		 * content.append("\t}"); ICompilationUnit
		 * nullClass=fragment.createCompilationUnit
		 * (Utils.NULL_CLASS+className+".java",content.toString(),true,null);
		 */extractMethod(m);
		/*
		 * } catch (JavaModelException e) { e.printStackTrace(); }
		 */
	}

	@SuppressWarnings({ "restriction", "deprecation" })
	public void extractMethod(OuterMethodModel m) {
		/*
		 * PerformRefactoringOperation refactoringOperation; String
		 * project=m.getMethod
		 * ().getCompilationUnit().getJavaProject().getElementName();
		 * RefactoringContribution
		 * rc=RefactoringCore.getRefactoringContribution(
		 * IJavaRefactorings.EXTRACT_METHOD ); RefactoringDescriptor
		 * rd=rc.createDescriptor();
		 */

		// refactoring status
		// ITextSelection selection = staticHelper.getITextSelection();
		int start = m.getLineNumber() + 2;
		int length = m.getEndingLine() - 1;

		System.out.println("START: " + start + " STOP: " + length);
		// The following line is part of the internal API
		try {
			NullProgressMonitor pm = new NullProgressMonitor();
			RefactoringStatus rs;
			ExtractMethodRefactoring tempR = new ExtractMethodRefactoring(m
					.getMethod().getCompilationUnit(), start, length);
			tempR.setMethodName("ExtractedMethod");
			tempR.setVisibility(Flags.AccPublic);
			tempR.setReplaceDuplicates(true);
			tempR.setValidationContext(null);
			if ((rs = tempR.checkInitialConditions(pm)).getSeverity() != RefactoringStatus.FATAL)
				System.out.println("OK");
			else
				System.out.println(rs.getMessageMatchingSeverity(rs
						.getSeverity()));
			rs = tempR.checkAllConditions(pm);

			// if (rs.getSeverity()!=RefactoringStatus.FATAL)
			{
				Change change = tempR.createChange(pm);
				change.perform(pm);
			}
			// else {System.out.println("FATAL ERROR");}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void moveMethod() {

	}
}
