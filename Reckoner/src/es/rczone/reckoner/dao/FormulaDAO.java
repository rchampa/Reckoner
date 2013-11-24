package es.rczone.reckoner.dao;

import java.util.ArrayList;

import es.rczone.reckoner.model.Formula;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class FormulaDAO {
	
	protected static final String TABLE = "formulas";
	protected static final String NAME = "name";
	protected static final String FORMULA = "formula";
	protected static final String VARIABLES = "variables";
	
	
	
	public ArrayList<Formula> getAllFriends() {
		
		ArrayList<Formula> list = new ArrayList<Formula>();
		SQLiteDatabase db = new DatabaseHelper().getReadableDatabase();
		Cursor cursor = db.query(TABLE, null, null, null, null, null, null);

		String name,formula,variables;
		Formula valueObject;
		ArrayList<String> varsList = new ArrayList<String>();
		if(cursor.moveToFirst()){
			
			do{
				name = cursor.getString(cursor.getColumnIndex(NAME));
				formula = cursor.getString(cursor.getColumnIndex(FORMULA));
				variables = cursor.getString(cursor.getColumnIndex(VARIABLES));
				
				String[] vars = variables.split("-");
				varsList.clear();
				for(String var : vars){
					varsList.add(var);
				}
				
				valueObject = new Formula(name, formula, varsList);
				
				list.add(valueObject);
			}while(cursor.moveToNext());
		}
		
		cursor.close();
		db.close();
		return list;
	}
	
	public Formula get(String name) {
		SQLiteDatabase db = new DatabaseHelper().getReadableDatabase();
		Cursor cursor = db.query(TABLE, null, NAME+"=?", new String[] {name}, null, null, null);
		Formula valueObject = null;
		String formula,variables;
		ArrayList<String> varsList = new ArrayList<String>();
		if (cursor.moveToFirst()) {
			formula = cursor.getString(cursor.getColumnIndex(FORMULA));
			variables = cursor.getString(cursor.getColumnIndex(VARIABLES));
			
			String[] vars = variables.split("-");
			varsList.clear();
			for(String var : vars){
				varsList.add(var);
			}
			
			valueObject = new Formula(name, formula, varsList);
		}
		
		cursor.close();
		db.close();
		return valueObject;
	}
	
	//Create
	public long insert(Formula formula) {
		SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NAME, formula.getName());
		values.put(FORMULA, formula.getFunctionFormula());
		
		String vars="";
		for(String var : formula.getVariables()){
			vars+= var+"-";
		}
		
		values.put(VARIABLES, vars);
		
		long num = db.insert(TABLE, null, values);
		db.close();
		return num;
	}
	
	//Update
	public int update(Formula formula) {
		SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NAME, formula.getName());
		values.put(FORMULA, formula.getFunctionFormula());
		
		String vars="";
		for(String var : formula.getVariables()){
			vars+= var+"-";
		}
		
		values.put(VARIABLES, vars);
		int num = db.update(TABLE, values, NAME + "=?", new String[]{formula.getName()});
		
		db.close();
		return num;
	}
	
	//Remove
	public void delete(String name) {
		SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
		db.delete(TABLE, NAME+"=?", new String[]{name});
		db.close();
	}
	
	//Remove
	public void delete(Formula formula) {
		delete(formula.getName());
	}
	
	public void deleteAll() {
		SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
		db.delete(TABLE, null, null);
		db.close();
	}

}
