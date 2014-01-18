package es.rczone.reckoner.math.formulas.equations.tools;

import java.util.TimerTask;

import es.rczone.reckoner.math.formulas.equations.dao.RFormulaListDAO;
import es.rczone.reckoner.math.formulas.equations.model.FormulasList;

import android.util.Log;

public class MyTimerTask extends TimerTask{
	
	private String nameList;
	private OnChangeFormulaListListener listener;
	
	public MyTimerTask(){
		this("");
	}
	
	public MyTimerTask(String nameList){
		super();
		this.nameList = nameList;
	}

	@Override
	public void run() {
		Log.d("Timer", "make a query");
		
		RFormulaListDAO dao = new RFormulaListDAO();
		FormulasList list = dao.get(nameList);
		
		if(list!=null){
			listener.onChangeList(list);
		}
	}

}
