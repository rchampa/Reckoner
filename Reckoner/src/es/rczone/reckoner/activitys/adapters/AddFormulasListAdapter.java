package es.rczone.reckoner.activitys.adapters;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.haarman.listviewanimations.ArrayAdapter;

import es.rczone.reckoner.R;
import es.rczone.reckoner.activitys.AddFormulasListActivity;
import es.rczone.reckoner.activitys.FormulasPickerActivity;
import es.rczone.reckoner.controllers.AddFormulasListController;

/*
 * ArrayAdapter has the Google Card Style 
 */
public class AddFormulasListAdapter extends ArrayAdapter<String> {
	
	public static final int LIST_NAME = 0;
	
	private AddFormulasListActivity mContext;
	private AddFormulasListController controller;
	
	private View formulaNameCard;
	private EditText formulaName;
	
	private View buttonsCard;
	private Button addButton;
	
	private Timer timer;
	private final long TIME = 1700;
	private TimerTask task;
	

	public AddFormulasListAdapter(AddFormulasListActivity context, AddFormulasListController controller) {
		mContext = context;
		this.controller = controller;
		timer = new Timer();
		task = new MyTimerTask();
	}

	
	private final OnClickListener addFormulaOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			String name = formulaName.getText().toString().trim();
			
			if("".equals(name)){
				Toast.makeText(mContext, "Please fill the name", Toast.LENGTH_SHORT).show();
				return;
			}
			
				
			
			SparseArray<String> args = new SparseArray<String>(2);
			args.put(LIST_NAME, name);
			boolean isOk = AddFormulasListAdapter.this.controller.handleMessage(AddFormulasListController.MESSAGE_ADD_FORMULAS_LIST, args);
			if(isOk){
				Intent i = new Intent(mContext, FormulasPickerActivity.class);
				i.putExtra("NAME",name);
				mContext.startActivityForResult(i, 1);
			}
			else{
				String msg = AddFormulasListAdapter.this.controller.getErrorMessage();
				Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
					
		switch(position){
			case 0: return prepareCardNameFormula(convertView, parent);
			case 1: return prepareCardFormulaButtons(convertView, parent);
		}
		
		return convertView;
	}
	
	//Name of Formula Card
	private View prepareCardNameFormula(View convertView, ViewGroup parent){
		
		if (formulaNameCard == null) {
			formulaNameCard = LayoutInflater.from(mContext).inflate(R.layout.googlecard_formulas_list_name, parent, false);
			formulaName = (EditText) formulaNameCard.findViewById(R.id.activity_googlecards_card_et_formulas_list_name);
			formulaName.addTextChangedListener(new TextWatcher() {
	            @Override
	            public void onTextChanged(CharSequence s, int start, int before, int count) {

	                Log.d("TextWatcher", "onTextChanged");
	                
	            }

	            @Override
	            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	            	Log.d("TextWatcher", "beforeTextChanged");
	            }

	            @Override
	            public void afterTextChanged(Editable s) {

	            	Log.d("TextWatcher", "afterTextChanged");
	            	
	            	
	            	task.cancel();
	            	
	            	if(s.toString().length()>0){
		            	task = new MyTimerTask();
		            	timer.schedule(task, TIME);
	            	}

	            }
	        });
			
		} 
		
		return formulaNameCard;
	}

	//Buttons Card
	private View prepareCardFormulaButtons(View convertView, ViewGroup parent){

		if (buttonsCard == null) {
			buttonsCard = LayoutInflater.from(mContext).inflate(R.layout.googlecard_formulas_list_buttons, parent, false);
			addButton = (Button) buttonsCard.findViewById(R.id.buttton_add_formulas_list);
			addButton.setOnClickListener(addFormulaOnClick);			
		} 
		
		return buttonsCard;
	}

	
}