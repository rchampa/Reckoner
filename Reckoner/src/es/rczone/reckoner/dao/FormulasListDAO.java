package es.rczone.reckoner.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import es.rczone.reckoner.model.Formula;
import es.rczone.reckoner.model.FormulasList;

public class FormulasListDAO {
	
	static final String TABLE = "formulas_list";
	static final String NAME = "name";
	static final String FORMULA_NAME = "formula_name";
	
	public FormulasList get(String name) {
		SQLiteDatabase db = new DatabaseHelper().getReadableDatabase();
		Cursor cursor = db.query(TABLE, null, NAME+"=?", new String[] {name}, null, null, null);
		
		FormulasList valueObject = new FormulasList(name);
		Formula f;
		String formulaName;
		if (cursor.moveToFirst()) {
			name = cursor.getString(cursor.getColumnIndex(NAME));
			formulaName = cursor.getString(cursor.getColumnIndex(FORMULA_NAME));
			
			f = new FormulaDAO().get(formulaName);
			valueObject.addFormula(f);			
		}
		
		cursor.close();
		db.close();
		
		if(valueObject.size()==0) return null;
		
		return valueObject;
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
	
	
}
