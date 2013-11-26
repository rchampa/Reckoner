package es.rczone.reckoner.controllers;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.HandlerThread;
import es.rczone.reckoner.dao.FormulaDAO;
import es.rczone.reckoner.model.Formula;


public class FormulasController extends Controller{
	
	@SuppressWarnings("unused")
	private static final String TAG = FormulasController.class.getSimpleName();
	private HandlerThread workerThread;
	private Handler workerHandler;
	
	
	public static final int MESSAGE_GET_FORMULAS_LIST = 11;
	public static final int MESSAGE_DELETE_FRIEND = 13;
	public static final int MESSAGE_TO_VIEW_MODEL_UPDATED = 21;

	
	private List<Formula> model;
	
	public FormulasController(List<Formula> model){
		this.model = model;
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
			case MESSAGE_GET_FORMULAS_LIST:
				getFriendsList();
				return true;
			
				
			case MESSAGE_DELETE_FRIEND:
				deleteCounter((Integer)data);
				getFriendsList();
				return true;
			
		}
		return false;
	}

	private void addFriend(final Formula friend) {
		workerHandler.post(new Runnable() {
			@Override
			public void run() {
				synchronized (friend) {
					FormulaDAO dao = new FormulaDAO();
					dao.insert(friend);
				}
			}
		});
		
	}

	private void deleteCounter(Integer data) {
		// TODO Auto-generated method stub
		
	}

	private void getFriendsList() {
		workerHandler.post(new Runnable() {
			@Override
			public void run() {
				//Make the changes persistence 
				FormulaDAO dao = new FormulaDAO();
				
				ArrayList<Formula> friends = dao.getAllFriends();
				synchronized (model) {
					//remove old elements
					model.clear();
					
					//re-populate the model with updated values
					for (Formula friend : friends){
						model.add(friend);
					}
					
					//Send asynchronous message to the view
					notifyOutboxHandlers(MESSAGE_TO_VIEW_MODEL_UPDATED, 0, 0, null);
				}
			}
		});
		
	}

}
