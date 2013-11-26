package es.rczone.reckoner.controllers;

import java.util.ArrayList;

import android.os.Handler;
import android.os.HandlerThread;
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
	@Override
	public boolean handleMessage(int what, Object data) {
		switch(what) {
							
			case MESSAGE_ADD_FORMULA:
				return addFormula((String)data);
			
		}
		return false;
	}

	private boolean addFormula(final String formula) {
		
		final ArrayList<String> varList = new ArrayList<String>();
		char l;
		
		for(int i=0; i<formula.length(); i++){
			l = formula.charAt(i);
			if(Character.isLetter(l))
				varList.add(""+l);
			
		}
		
		if(varList.size()==0)
			return false;
		
		workerHandler.post(new Runnable() {
			@Override
			public void run() {
				Formula f = new Formula("", formula, varList);
				FormulaDAO dao = new FormulaDAO();
				dao.insert(f);
				
			}
		});
		
		return true;
	}

}
