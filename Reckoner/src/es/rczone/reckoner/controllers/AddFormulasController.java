package es.rczone.reckoner.controllers;

import java.util.ArrayList;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.SparseArray;
import es.rczone.dariuslib.me.Parser;
import es.rczone.dariuslib.me.SyntaxException;
import es.rczone.reckoner.activitys.adapters.AddFormulaAdapter;
import es.rczone.reckoner.dao.FormulaDAO;
import es.rczone.reckoner.model.Formula;
import es.rczone.reckoner.tools.Tools;


public class AddFormulasController extends Controller{
	
	@SuppressWarnings("unused")
	private static final String TAG = AddFormulasController.class.getSimpleName();
	private HandlerThread workerThread;
	private Handler workerHandler;
	
	
	public static final int MESSAGE_ADD_FORMULA = 13;
	public static final int MESSAGE_TO_VIEW_MODEL_UPDATED = 21;

	public AddFormulasController(){
		//workerthread has created just for get a looper instance
		workerThread = new HandlerThread("Worker Thread");
		workerThread.start();
		workerHandler = new Handler(workerThread.getLooper());
	}
	
	@Override
	public void dispose() {
		super.dispose();
		workerThread.getLooper().quit();
	}

	/**
	 * This method receive message from view(activity) and notifies(with a boolean value) whether the message was received (handle)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean handleMessage(int what, Object data) {
		boolean r = false;
		switch(what) {
							
			case MESSAGE_ADD_FORMULA:
				r = addFormula((SparseArray<String>)data);
				break;
			case 33: break;
			
		}
		return r;
	}

	private boolean addFormula(SparseArray<String> args) {
		
		final String formula = args.get(AddFormulaAdapter.FORMULA_FORMULA);
		final String name = args.get(AddFormulaAdapter.FORMULA_NAME).trim();
		
		try {
			Parser.parse(formula);
		} catch (SyntaxException e) {
			Log.e("Parser",e.explain());
			errorMessage = e.explain();
			return false;
		}
		
		String formulaTemp=formula;
		
		for(String s : Parser.procs1){
			if(formulaTemp.contains(s)){
				formulaTemp = formulaTemp.replace(s, "");
			}
		}
		
		for(String s : Parser.procs2){
			if(formulaTemp.contains(s)){
				formulaTemp = formulaTemp.replace(s, "");
			}
		}
		
		
		final ArrayList<String> varList = new ArrayList<String>();
		
		char l;
		for(int i=0; i<formulaTemp.length(); i++){
			l = formulaTemp.charAt(i);
			if(Character.isLetter(l))
				varList.add(""+l);
			
		}
		
		if(varList.size()==0)
			return false;
		
		Formula f = new Formula(name, formula, varList);
		FormulaDAO dao = new FormulaDAO();
		boolean query = dao.insert(f);
		if(!query) {
			errorMessage = "There is a formula with the same name, please use another name.";
			return false;
		}
		
		workerHandler.post(new Runnable() {
			@Override
			public void run() {
				Tools.getImage(Formula.PATH_FOLDER, name+".gif", "http://latex.codecogs.com/gif.latex?%5Chuge%20"+formula);    			
			}
		});
		
		return query;
	}

}
