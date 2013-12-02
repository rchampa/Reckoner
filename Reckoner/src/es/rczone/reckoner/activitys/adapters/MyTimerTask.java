package es.rczone.reckoner.activitys.adapters;

import java.util.TimerTask;

import android.util.Log;

public class MyTimerTask extends TimerTask{

	@Override
	public void run() {
		Log.d("Timer", "make a query");
	}

}
