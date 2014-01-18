package es.rczone.reckoner.math.formulas.equations.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ListDAO {
	
	static final String TABLE = "list";
	static final String NAME = "name";
	
	public ArrayList<String> getAllLists() {
		
		SQLiteDatabase db = new DatabaseHelper().getReadableDatabase();
		Cursor cursor = db.query(TABLE, null, null, null, null, null, null);

		String name;
		ArrayList<String> lists = new ArrayList<String>();
		if(cursor.moveToFirst()){
			
			do{
				name = cursor.getString(cursor.getColumnIndex(NAME));
				lists.add(name);
			}
			while(cursor.moveToNext());
		}
		
		cursor.close();
		db.close();
		return lists;
	}
	
	
	//Create
	public boolean insert(String name) {
		SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NAME, name);
		long num;
		
		try{
			num = db.insertOrThrow(TABLE, null, values);
			db.close();
			if(num!=-1)
				return true;
			else
				return false;
				
			
		} catch (SQLiteConstraintException e) {
	        Log.d("FormulaDAO", "failure to insert list", e);
	    	db.close();
			return false;
	    }
		
	}
	
	
	//Remove
	public void delete(String name) {
		SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
		db.delete(TABLE, NAME+"=?", new String[]{name});
		db.close();
	}
	
	public void deleteAll() {
		SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
		db.delete(TABLE, null, null);
		db.close();
	}
	
}