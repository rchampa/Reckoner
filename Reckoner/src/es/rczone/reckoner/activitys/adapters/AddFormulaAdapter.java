package es.rczone.reckoner.activitys.adapters;

import android.content.Context;
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
import es.rczone.reckoner.controllers.AddFormulasController;

/*
 * ArrayAdapter has the Google Card Style 
 */
public class AddFormulaAdapter extends ArrayAdapter<String> {
	
	public static final int FORMULA_NAME = 0;
	public static final int FORMULA_FORMULA = 1;
	
	private Context mContext;
	private AddFormulasController controller;
	
	private View formulaNameCard;
	private EditText formulaName;
	
	private View formulaCard;
	private EditText etFormula;
	
	private View buttonsCard;
	private Button addButton;
	

	public AddFormulaAdapter(Context context, AddFormulasController controller) {
		mContext = context;
		this.controller = controller;
	}

	
	private final OnClickListener addFormulaOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			String name = formulaName.getText().toString().trim();
			String formula = etFormula.getText().toString().trim();
			
			if("".equals(name)){
				Toast.makeText(mContext, "Please fill the name", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if("".equals(formula)){
				Toast.makeText(mContext, "Please fill the formula", Toast.LENGTH_SHORT).show();
				return;
			}
				
			
			SparseArray<String> args = new SparseArray<String>(2);
			args.put(FORMULA_NAME, name);
			args.put(FORMULA_FORMULA, formula);
			boolean isOk = AddFormulaAdapter.this.controller.handleMessage(AddFormulasController.MESSAGE_ADD_FORMULA, args);
			if(isOk){
				Toast.makeText(mContext, "Your formula was added succesfully.", Toast.LENGTH_SHORT).show();
				formulaName.setText("");
				etFormula.setText("");
			}
			else{
				String msg = AddFormulaAdapter.this.controller.getErrorMessage();
				Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
					
		switch(position){
			case 0: return prepareCardNameFormula(convertView, parent);
			case 1: return prepareCardFormula(convertView, parent);
			case 2: return prepareCardFormulaButtons(convertView, parent);
		}
		
		return convertView;
	}
	
	//Name of Formula Card
	private View prepareCardNameFormula(View convertView, ViewGroup parent){
		
		if (formulaNameCard == null) {
			formulaNameCard = LayoutInflater.from(mContext).inflate(R.layout.googlecard_formula_name, parent, false);
			formulaName = (EditText) formulaNameCard.findViewById(R.id.activity_googlecards_card_et_formula_name);
			
		} 
		
		return formulaNameCard;
	}

	

	
	//Formula Card
	private View prepareCardFormula(View convertView, ViewGroup parent){
		
		if (formulaCard == null) {
			formulaCard = LayoutInflater.from(mContext).inflate(R.layout.googlecard_add_formula, parent, false);
			etFormula = (EditText) formulaCard.findViewById(R.id.activity_googlecards_card_et_formula);
		} 
		
		return formulaCard;
	}

	

	//Buttons Card
	private View prepareCardFormulaButtons(View convertView, ViewGroup parent){

		if (buttonsCard == null) {
			buttonsCard = LayoutInflater.from(mContext).inflate(R.layout.googlecard_add_formula_buttons, parent, false);
			addButton = (Button) buttonsCard.findViewById(R.id.buttton_add_formula);
			addButton.setOnClickListener(addFormulaOnClick);			
		} 
		
		return buttonsCard;
	}

	
}