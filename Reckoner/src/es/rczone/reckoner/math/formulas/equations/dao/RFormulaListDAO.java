package es.rczone.reckoner.math.formulas.equations.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import es.rczone.reckoner.math.formulas.equations.model.Formula;
import es.rczone.reckoner.math.formulas.equations.model.FormulasList;

public class RFormulaListDAO {
	
	static final String TABLE = "r_formula_list";
	static final String NAME = "name";
	static final String FORMULA_NAME = "formula_name";
	
	
	//Read
	public FormulasList get(String name) {
		SQLiteDatabase db = new DatabaseHelper().getReadableDatabase();
		Cursor cursor = db.query(TABLE, null, NAME+"=?", new String[] {name}, null, null, null);
		
		FormulasList valueObject = new FormulasList(name);
		Formula f;
		String formulaName;
		if(cursor.moveToFirst()){
			
			do{
				name = cursor.getString(cursor.getColumnIndex(NAME));
				formulaName = cursor.getString(cursor.getColumnIndex(FORMULA_NAME));
				
				f = new FormulaDAO().get(formulaName);
				valueObject.addFormula(f);		
			}while(cursor.moveToNext());
		}
		
		cursor.close();
		db.close();
		
		
		return valueObject;
	}
	
	//Read
	public FormulasList getRemainingFormulas(String name) {
		
		String QUERY = "SELECT DISTINCT f.name as f_name FROM "+FormulaDAO.TABLE+" f where f.name not in (select formula_name from "+TABLE+" l where l.name=?)";
				
		
		SQLiteDatabase db = new DatabaseHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery(QUERY, new String[]{name});
		
		
		FormulasList valueObject = new FormulasList(name);
		Formula f;
		String formulaName;
		if(cursor.moveToFirst()){
					
			do{
				formulaName = cursor.getString(cursor.getColumnIndex("f_name"));
				
				f = new FormulaDAO().get(formulaName);
				valueObject.addFormula(f);
			}while(cursor.moveToNext());
		}
		
		cursor.close();
		db.close();
						
		
		return valueObject;
	}
	
	public List<String> getNamesOfAllFormulasList() {
		
		String QUERY = "SELECT DISTINCT name from formulas_list";
				
		
		SQLiteDatabase db = new DatabaseHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery(QUERY, null);
		
		
		List<String> list = new ArrayList<String>();
		String formulaListName;
		if(cursor.moveToFirst()){
			
			do{
				formulaListName = cursor.getString(cursor.getColumnIndex(NAME));
				list.add(formulaListName);			
			}while(cursor.moveToNext());
		}
		
		cursor.close();
		db.close();
		
		return list;
	}
	
	public boolean exists(String name) {
		SQLiteDatabase db = new DatabaseHelper().getReadableDatabase();
		Cursor cursor = db.query(TABLE, null, NAME+"=?", new String[] {name}, null, null, null);
		
		boolean exists = false;
		
		if (cursor.moveToFirst()) {
			exists = true;			
		}
		
		cursor.close();
		db.close();
		
		return exists;
	}
	
	//Create
	public boolean insert(String nameList, String nameFormula) {
		SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NAME, nameList);
		values.put(FORMULA_NAME, nameFormula);
		
		
		long num=-1;
		
		try{
			num = db.insertOrThrow(TABLE, null, values);
			db.close();
			if(num!=-1)
				return true;
			else
				return false;
				
			
		} catch (SQLiteConstraintException e) {
	        Log.d("FormulaDAO", "failure to insert formulasList", e);
	    	db.close();
			return false;
	    }
		
	}
	
	//Create
	public boolean insertMany(String nameList, String nameFormula) {
		SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NAME, nameList);
		values.put(FORMULA_NAME, nameFormula);
		
		
		long num=-1;
		
		try{
			num = db.insertOrThrow(TABLE, null, values);
			db.close();
			if(num!=-1)
				return true;
			else
				return false;
				
			
		} catch (SQLiteConstraintException e) {
	        Log.d("FormulaDAO", "failure to insert formulasList", e);
	    	db.close();
			return false;
	    }
		
	}
	
	
	//Remove
	public void delete(String listName, String formulaName) {
		SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
		db.delete(TABLE, NAME+"=? and "+FORMULA_NAME+"=?", new String[]{listName,formulaName});
		db.close();
	}
	
}
