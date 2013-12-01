package es.rczone.reckoner.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import es.rczone.reckoner.model.Formula;


public class FormulaDAO {
	
	static final String TABLE = "formulas";
	static final String NAME = "name";
	static final String PATH = "path";
	static final String FORMULA = "formula";
	static final String VARIABLES = "variables";
	
	
	
	public ArrayList<Formula> getAllFormulas() {
		
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
	public boolean insert(Formula formula) {
		SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NAME, formula.getName());
		values.put(FORMULA, formula.getFunctionFormula());
		
		String vars="";
		for(String var : formula.getVariables()){
			vars+= var+"-";
		}
		
		values.put(VARIABLES, vars);
		
		long num=-1;
		
		try{
			num = db.insertOrThrow(TABLE, null, values);
			db.close();
			if(num!=-1)
				return true;
			else
				return false;
				
			
		} catch (SQLiteConstraintException e) {
	        Log.d("FormulaDAO", "failure to insert formula", e);
	    	db.close();
			return false;
	    }
		
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
