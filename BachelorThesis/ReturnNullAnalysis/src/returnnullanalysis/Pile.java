package returnnullanalysis;

import java.util.ArrayList;
import java.util.List;

import models.MetModel;
import models.OuterMethodModel;
import models.VarModel;

public class Pile {
	private static Pile instance = new Pile();
	private List<VarModel> variables = new ArrayList<VarModel>();
	private List<MetModel> methods = new ArrayList<MetModel>();

	private Pile() {
	}

	public static Pile getInstance() {
		return instance;
	}

	public void addMethod(MetModel m, OuterMethodModel outerMethod) {
		if (methods.contains(m))
			methods.get(methods.indexOf(m)).addOuterMethod(outerMethod);
		else {
			methods.add(m);
			methods.get(methods.indexOf(m)).addOuterMethod(outerMethod);
		}
	}

	public void addVariable(VarModel v, OuterMethodModel outerMethod) {
		if (variables.contains(v))
			variables.get(variables.indexOf(v)).addOuterMethod(outerMethod);
		else {
			variables.add(v);
			variables.get(variables.indexOf(v)).addOuterMethod(outerMethod);
		}
	}

	public List<VarModel> getVariables() {
		return variables;
	}

	public List<MetModel> getMethods() {
		return methods;
	}

	public void clear() {
		variables.clear();
		methods.clear();
	}

	public String toString() {
		String s = "Variables: [ ";
		int i;
		for (i = 0; i < variables.size(); i++)
			s = s + variables.get(i).toString() + " , ";
		s = s + "]   \n Methods: [ ";
		for (i = 0; i < methods.size(); i++) {
			s = s + methods.get(i).toString() + ", ";
		}
		s = s + "]\n";
		return s;
	}
}
