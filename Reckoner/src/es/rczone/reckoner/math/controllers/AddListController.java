package es.rczone.reckoner.math.controllers;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.SparseArray;
import es.rczone.reckoner.math.activitys.AddListActivity;
import es.rczone.reckoner.math.dao.ListDAO;


public class AddListController extends Controller{
	
	@SuppressWarnings("unused")
	private static final String TAG = AddListController.class.getSimpleName();
	private HandlerThread workerThread;
	private Handler workerHandler;
	
	
	public static final int MESSAGE_ADD_LIST = 13;
	public static final int MESSAGE_TO_VIEW_MODEL_UPDATED = 21;

	public AddListController(){
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
							
			case MESSAGE_ADD_LIST:
				r = addList((SparseArray<String>)data);
				break;
			case 33: break;
			
		}
		return r;
	}

	private boolean addList(SparseArray<String> args) {
		
		final String name = args.get(AddListActivity.NAME_LIST);
		
		ListDAO dao = new ListDAO();
		boolean query = dao.insert(name);
		
		if(!query) {
			errorMessage = "There is a formula with the same name, please use another name.";
		}
		
		return query;
	}

}
