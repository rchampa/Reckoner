package es.rczone.reckoner.controllers;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.SparseArray;
import es.rczone.reckoner.activitys.adapters.AddFormulasListAdapter;


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
				r = addFormula((SparseArray<String>)data);
				break;
			case 33: break;
			
		}
		return r;
	}

	private boolean addFormula(SparseArray<String> args) {
		
		final String name = args.get(AddFormulasListAdapter.LIST_NAME).trim();
		return true;
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
