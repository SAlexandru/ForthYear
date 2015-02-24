package utils;

import java.util.ArrayList;
import java.util.List;

import models.OuterMethodModel;

public class ListViewHelper	//holds the methods from the listview
	{
	private static ListViewHelper instance=new ListViewHelper();
	private List<OuterMethodModel> outerMethods=new ArrayList<OuterMethodModel>();
	
	private ListViewHelper(){}
	
	public static ListViewHelper getInstance()
		{
		return instance;
		}

	public List<OuterMethodModel> getOuterMethods()
		{
		return outerMethods;
		}

	public void addOutherMethod (OuterMethodModel m)
		{
		outerMethods.add(m);
		}
	
	public void clear()
		{
		outerMethods.clear();
		}

	}
