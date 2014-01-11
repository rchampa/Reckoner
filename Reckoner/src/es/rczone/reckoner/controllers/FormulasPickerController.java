package es.rczone.reckoner.controllers;

import java.util.List;

import android.os.Handler;
import android.os.HandlerThread;
import es.rczone.reckoner.dao.RFormulaListDAO;
import es.rczone.reckoner.model.Formula;


public class FormulasPickerController extends Controller{
	
	@SuppressWarnings("unused")
	private static final String TAG = FormulasPickerController.class.getSimpleName();
	private HandlerThread workerThread;
	private Handler workerHandler;
	private List<Formula> modelList;
	
	public static final int MESSAGE_LOAD_fORMULAS_NOT_ADDED = 11;
	public static final int MESSAGE_TO_VIEW_MODEL_OPEN_FORMULAS_PICKER = 12;
	public static final int MESSAGE_TO_VIEW_MODEL_UPDATED = 21;

	public FormulasPickerController(List<Formula> modelList){
		
		this.modelList = modelList;
	
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
							
			case MESSAGE_LOAD_fORMULAS_NOT_ADDED:
				r = loadDataList((String)data);
				break;
			case 33: break;
			
		}
		return r;
	}

	private boolean loadDataList(String args) {
		
		workerHandler.post(new Runnable() {
			@Override
			public void run() {
				//Send asynchronous message to the view
				notifyOutboxHandlers(MESSAGE_TO_VIEW_MODEL_OPEN_FORMULAS_PICKER, 0, 0, null);
				
			}
		});
		
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
	
	private void getRemainingFormulasList(final String name) {
		workerHandler.post(new Runnable() {
			@Override
			public void run() {
				//Make the changes persistence 
				RFormulaListDAO dao = new RFormulaListDAO();
				
				List<Formula> formulas = dao.getRemainingFormulas(name);
				synchronized (modelList) {
					//remove old elements
					modelList.clear();
					
					//re-populate the model with updated values
					for (Formula f : formulas){
						modelList.add(f);
					}
					
					//Send asynchronous message to the view
					notifyOutboxHandlers(MESSAGE_TO_VIEW_MODEL_UPDATED, 0, 0, null);
				}
			}
		});
		
	}

}
