package es.rczone.reckoner.math.formulas.equations.controllers;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.SparseArray;
import es.rczone.reckoner.math.formulas.equations.activitys.AddFormulasListActivity;
import es.rczone.reckoner.math.formulas.equations.dao.RFormulaListDAO;


public class AddFormulasListController extends Controller{
	
	@SuppressWarnings("unused")
	private static final String TAG = AddFormulasListController.class.getSimpleName();
	private HandlerThread workerThread;
	private Handler workerHandler;
	
	
	public static final int MESSAGE_ADD_FORMULAS_LIST = 11;
	public static final int MESSAGE_TO_VIEW_MODEL_OPEN_FORMULAS_PICKER = 12;
	public static final int MESSAGE_TO_VIEW_MODEL_UPDATED = 21;

	public AddFormulasListController(){
	
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
							
			case MESSAGE_ADD_FORMULAS_LIST:
				r = addFormulaList((SparseArray<String>)data);
				break;
			case 33: break;
			
		}
		return r;
	}

	private boolean addFormulaList(SparseArray<String> args) {
		
		String name = args.get(AddFormulasListActivity.LIST_NAME).trim();
		
		return new RFormulaListDAO().exists(name);
	}
	
	private void addFormulas() {
		workerHandler.post(new Runnable() {
			@Override
			public void run() {
				//Send asynchronous message to the view
				notifyOutboxHandlers(MESSAGE_TO_VIEW_MODEL_OPEN_FORMULAS_PICKER, 0, 0, null);
				
			}
		});
		
	}

}
