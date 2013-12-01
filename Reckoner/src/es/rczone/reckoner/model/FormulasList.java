package es.rczone.reckoner.model;

import java.util.ArrayList;


public class FormulasList {
	
	private String name;
	private ArrayList<Formula> list;
	
	
	public FormulasList(String name, ArrayList<Formula> list){
		this.name = name;
		this.list = new ArrayList<Formula>(list);
		
	}
	

}
