package es.rczone.reckoner.math.model;

import java.util.ArrayList;
import java.util.Date;

import android.os.Environment;

public class Formula {
	
	public static String PATH_FOLDER = Environment.getExternalStorageDirectory().getPath()+"/formulas/images/";
	private String name;
	private String formula;
	private ArrayList<String> variables;
	private Date createdAt;
	private Date modifiedAt;
	
	
	public Formula(String name, String formula, ArrayList<String> variables){
		createdAt = new Date();
		this.variables = new ArrayList<String>(variables);
		this.name = name;
		this.formula = formula;
	}
	
	public void setFormula(String formula, ArrayList<String> variables){
		modifiedAt = new Date();
		this.variables = new ArrayList<String>(variables);
		this.formula = formula;
	}
	
	public String getFunctionFormula(){
		return formula;
	}
	
	public String getName(){
		return name;
	}
	
	public Iterable<String> getVariables(){
		return variables;
	}
	
	public int getNumVars(){
		return variables.size();
	}

	@Override
	public String toString() {
		return name+" [formula=" + formula + ", variables=" + variables + "]";
	}

	
}
