package es.rczone.reckoner.activitys;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.haarman.listviewanimations.ArrayAdapter;

import es.rczone.reckoner.R;
import es.rczone.reckoner.controllers.AddFormulasController;
import es.rczone.reckoner.dao.ListDAO;

public class AddFormulaActivity extends BaseActivity {
	
	public static final int FORMULA_NAME = 0;
	public static final int FORMULA_FORMULA = 1;
	
	private AddFormulaAdapter mGoogleCardsAdapter;
	private AddFormulasController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_googlecards);

		controller = new AddFormulasController(); 
		
		ListView listView = (ListView) findViewById(R.id.activity_googlecards_listview);

		mGoogleCardsAdapter = new AddFormulaAdapter(this);

		this.setAnimAdapter(listView, mGoogleCardsAdapter);

		mGoogleCardsAdapter.addAll(getItems());
		
		
	}

	private ArrayList<String> getItems() {
		ArrayList<String> items = new ArrayList<String>();
		items.add("Name");
		items.add("Formula");
		items.add("Buttons");
		
		return items;
	}
	
	private class AddFormulaAdapter extends ArrayAdapter<String> {
		
		private Context mContext;
		
		private View formulaNameCard;
		private EditText formulaName;
		
		private View formulaCard;
		private EditText etFormula;
		
		private View formulaSpinnerCard;
		private Spinner spinner;
		
		private View buttonsCard;
		private Button addButton;
		private Button okButton;
		

		public AddFormulaAdapter(Context context) {
			mContext = context;
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
				boolean isOk = AddFormulaActivity.this.controller.handleMessage(AddFormulasController.MESSAGE_ADD_FORMULA, args);
				if(isOk){
					Toast.makeText(mContext, "Your formula was added succesfully.", Toast.LENGTH_SHORT).show();
					formulaName.setText("");
					etFormula.setText("");
				}
				else{
					String msg = AddFormulaActivity.this.controller.getErrorMessage();
					Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
				}
			}
			
		};
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
						
			switch(position){
				case 0: return prepareCardNameFormula(convertView, parent);
				case 1: return prepareCardFormula(convertView, parent);
				case 2: return prepareCardSpinnerFormulaList(convertView, parent);
				case 3: return prepareCardFormulaButtons(convertView, parent);
			}
			
			return convertView;
		}
		
		//Name of Formula Card
		private View prepareCardNameFormula(View convertView, ViewGroup parent){
			
			if (formulaNameCard == null) {
				formulaNameCard = LayoutInflater.from(mContext).inflate(R.layout.googlecard_add_formula_name, parent, false);
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
		
		//Spinner list
		private View prepareCardSpinnerFormulaList(View convertView, ViewGroup parent){
			
			if (formulaSpinnerCard == null) {
				formulaSpinnerCard = LayoutInflater.from(mContext).inflate(R.layout.googlecard_add_formula_spinner_formulas_list, parent, false);
				spinner = (Spinner) formulaSpinnerCard.findViewById(R.id.spinner_add_formula);
				List<String> list = new ListDAO().getAllLists();
				android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, list);
				spinner.setAdapter(adapter);
			} 
			
			return formulaSpinnerCard;
		}

		

		//Buttons Card
		private View prepareCardFormulaButtons(View convertView, ViewGroup parent){

			if (buttonsCard == null) {
				buttonsCard = LayoutInflater.from(mContext).inflate(R.layout.googlecard_add_formula_buttons, parent, false);
				addButton = (Button) buttonsCard.findViewById(R.id.buttton_add_formula);
				addButton.setOnClickListener(addFormulaOnClick);	
				okButton = (Button) buttonsCard.findViewById(R.id.buttton_add_formula);
				okButton.setOnClickListener(addFormulaOnClick);
			} 
			
			return buttonsCard;
		}

		
	}
	
}
