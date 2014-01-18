package es.rczone.reckoner.math.formulas.equations.dao;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import es.rczone.reckoner.math.formulas.equations.ReckonerApp;


final class DatabaseHelper extends SQLiteOpenHelper {

	@SuppressWarnings("unused")
	private static final String TAG = DatabaseHelper.class.getSimpleName();
	private static final String DATABASE_NAME = "Reckoner";
	private static final int DATABASE_VERSION = 2;
	
	public DatabaseHelper() {
		super(ReckonerApp.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		
		final String formulas = "CREATE TABLE " + FormulaDAO.TABLE + "(" + 
								FormulaDAO.NAME + " varchar(25) primary key, " +
								FormulaDAO.FORMULA + " varchar(100) not null, " +
								FormulaDAO.VARIABLES + " varchar(20) not null)";
		
		final String lists = "CREATE TABLE " + ListDAO.TABLE + "(" + 
				ListDAO.NAME + " varchar(25) primary key )";
		
		final String formulasList = "CREATE TABLE " + RFormulaListDAO.TABLE + "(" + 
								RFormulaListDAO.NAME + " varchar(25), " +
								RFormulaListDAO.FORMULA_NAME + " varchar(25), " +
								"PRIMARY KEY ("+RFormulaListDAO.NAME+", "+RFormulaListDAO.FORMULA_NAME+") )";
		
		
		database.execSQL(formulas);
		database.execSQL(lists);
		database.execSQL(formulasList);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// first iteration. do nothing.
		
	}
	

}
