package es.rczone.reckoner.controllers;

import java.util.ArrayList;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.SparseArray;
import es.rczone.dariuslib.me.Parser;
import es.rczone.dariuslib.me.SyntaxException;
import es.rczone.reckoner.activitys.adapters.GoogleCardsAdapter;
import es.rczone.reckoner.dao.FormulaDAO;
import es.rczone.reckoner.model.Formula;


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
				r = addFormula((SparseArray<String>)data);//WTF
				break;
			case 33: break;
			
		}
		return r;
	}

	private boolean addFormula(SparseArray<String> args) {
		
		String formula = args.get(GoogleCardsAdapter.FORMULA_FORMULA);
		String name = args.get(GoogleCardsAdapter.FORMULA_NAME);
		
		try {
			Parser.parse(formula);
		} catch (SyntaxException e) {
			Log.e("Parser",e.explain());
			return false;
		}
		
		final ArrayList<String> varList = new ArrayList<String>();
		char l;
		
		for(int i=0; i<formula.length(); i++){
			l = formula.charAt(i);
			if(Character.isLetter(l))
				varList.add(""+l);
			
		}
		
		if(varList.size()==0)
			return false;
		
		Formula f = new Formula(name, formula, varList);
		FormulaDAO dao = new FormulaDAO();
		dao.insert(f);
	
		return true;
	}

}
