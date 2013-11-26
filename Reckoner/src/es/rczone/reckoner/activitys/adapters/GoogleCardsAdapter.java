package es.rczone.reckoner.activitys.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.haarman.listviewanimations.ArrayAdapter;

import es.rczone.reckoner.R;
import es.rczone.reckoner.controllers.AddFormulasController;

public class GoogleCardsAdapter extends ArrayAdapter<String> {
	private Context mContext;
	private AddFormulasController controller;

	public GoogleCardsAdapter(Context context, AddFormulasController controller) {
		mContext = context;
		this.controller = controller;
	}

	
	private final OnClickListener addFormulalistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			GoogleCardsAdapter.this.controller.handleMessage(AddFormulasController.MESSAGE_ADD_FORMULA, null);
			
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
		ViewHolderFormulaName viewHolder;
		View view = convertView;
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.activity_googlecards_formula_name, parent, false);
			viewHolder = new ViewHolderFormulaName();
			viewHolder.formulaName = (EditText) view.findViewById(R.id.activity_googlecards_card_et_formula_name);
			view.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolderFormulaName) view.getTag();
		}
		
		return view;
	}

	

	private static class ViewHolderFormulaName {
		EditText formulaName;
	}
	
	
	//Formula Card
	private View prepareCardFormula(View convertView, ViewGroup parent){
		ViewHolderFormula viewHolder;
		View view = convertView;
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.activity_googlecards_add_formula, parent, false);
			viewHolder = new ViewHolderFormula();
			viewHolder.etFormula = (EditText) view.findViewById(R.id.activity_googlecards_card_et_formula);
			view.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolderFormula) view.getTag();
		}
		
		return view;
	}

	

	private static class ViewHolderFormula {
		EditText etFormula;
	}
	
	//Buttons Card
	private View prepareCardFormulaButtons(View convertView, ViewGroup parent){
		ViewHolderFormulaButtons viewHolder;
		View view = convertView;
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.activity_googlecards_buttons, parent, false);
			viewHolder = new ViewHolderFormulaButtons();
			viewHolder.addButton = (Button) view.findViewById(R.id.buttton_add_formula);
			viewHolder.addButton.setOnClickListener(addFormulalistener);
			
			view.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolderFormulaButtons) view.getTag();
		}

		return view;
	}

	

	private static class ViewHolderFormulaButtons {
		Button addButton;
	}
}