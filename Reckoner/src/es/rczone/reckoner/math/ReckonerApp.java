package es.rczone.reckoner.math;

import android.app.Application;
import android.content.Context;
import android.util.Log;


public class ReckonerApp extends Application {
	
	private static final String TAG = ReckonerApp.class.getSimpleName();
	private static ReckonerApp INSTANCE;
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "Reckoner.onCreate was called");
		INSTANCE = this;
	}
	
	public static Context getContext() {
		return INSTANCE.getApplicationContext();
	}
}
