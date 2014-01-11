package es.rczone.reckoner.model;

import java.util.ArrayList;


public class FormulasList {
	
	private String name;
	private ArrayList<Formula> list;
	
	
	public FormulasList(String name){
		this.name = name;
		this.list = new ArrayList<Formula>();
		
	}
	
	public void addFormula(Formula f){
		list.add(f);
	}
	
	public String getName(){
		return this.name;
	}
	
	public int size(){
		return list.size();
	}
	
	public ArrayList<Formula> getCopyOfList(){
		return new ArrayList<Formula>(this.list);
	}

}
