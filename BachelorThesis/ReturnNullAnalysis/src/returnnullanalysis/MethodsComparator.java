package returnnullanalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import models.OuterMethodModel;

import org.eclipse.jdt.core.dom.Statement;

import utils.ListViewHelper;

public class MethodsComparator
	{
	private OuterMethodModel outerMethod;
	
	public MethodsComparator(OuterMethodModel outerMethod)
		{
		this.outerMethod=outerMethod;
		}
	
	public Stack<Object> compare ()
		{
		List<OuterMethodModel> outerMethods=new ArrayList<OuterMethodModel>(ListViewHelper.getInstance().getOuterMethods());
		boolean ok=false;
		Stack<Object> result=new Stack<Object>();
		outerMethods.remove(outerMethod);
		for (OuterMethodModel m:outerMethods)
			{
			if (areEqual(outerMethod.getThenBlock(),m.getThenBlock()))
				{
				result.push(m);
				ok=true;
				}
			}
		result.push(outerMethod);
		result.push(ok);
		return result;
		}

	public boolean areEqual(Statement m1, Statement m2)
		{
		if (m1!=null && m2!=null)
			{
			String s1=m1.toString();
			String s2=m2.toString();
			return s1.equals(s2);
			/*String []s1=m1.toString().replace("{","").replace("}","").split("\n");
			String []s2=m2.toString().replace("{","").replace("}","").split("\n");
			int l1=s1.length;
			int l2=s2.length;
			boolean []ok=new boolean[l1];
			int i,j;
			for (i=0;i<l1;i++)
				{
				for (j=0;j<l2;j++)
					{
					if (s1[i].equals(s2[j]))
						ok[i]=true;
					}
				if (ok[i]==false)
					return false;
				}
			//for (i=0;i<l1;i++)
				//System.out.println(ok[i]);
			return true;*/
			}
		return false;
		}
	}
