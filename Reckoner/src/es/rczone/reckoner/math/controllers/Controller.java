package es.rczone.reckoner.math.controllers;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;

abstract class Controller {
	
	private final List<Handler> outboxHandlers = new ArrayList<Handler>();
	protected String errorMessage;

	public Controller() {
		errorMessage="";
	}
	
	public void dispose() {}
	
	abstract public boolean handleMessage(int what, Object data);

	public boolean handleMessage(int what) {
		return handleMessage(what, null);
	}
	
	public final void addOutboxHandler(Handler handler) {
		outboxHandlers.add(handler);
	}

	public final void removeOutboxHandler(Handler handler) {
		outboxHandlers.remove(handler);
	}
	
	protected final void notifyOutboxHandlers(int what, int arg1, int arg2, Object obj) {
		if (!outboxHandlers.isEmpty()) {
			for (Handler handler : outboxHandlers) {
				Message msg = Message.obtain(handler, what, arg1, arg2, obj);
				msg.sendToTarget();
			}
		}
	}
	
	public String getErrorMessage(){
		return this.errorMessage;
	}
	public void setErrorMessage(String msg){
		this.errorMessage = msg;
	}
}
