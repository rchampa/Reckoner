package es.rczone.reckoner.math.formulas.equations.activitys;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.haarman.listviewanimations.ArrayAdapter;
import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;

import es.rczone.reckoner.math.formulas.equations.R;
import es.rczone.reckoner.math.formulas.equations.controllers.AddFormulasListController;

public class AddFormulasListActivity extends BaseActivity implements Handler.Callback{
	
	public static final int LIST_NAME = 0;
	
	private AddFormulasListAdapter mGoogleCardsAdapter;
	private AddFormulasListController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_googlecards);

		controller = new AddFormulasListController(); 
		
		ListView listView = (ListView) findViewById(R.id.activity_googlecards_listview);

		mGoogleCardsAdapter = new AddFormulasListAdapter(this);

		AnimationAdapter animAdapter = new ScaleInAnimationAdapter(mGoogleCardsAdapter);
		animAdapter.setAbsListView(listView);
		animAdapter.setInitialDelayMillis(500);
		listView.setAdapter(animAdapter);

		mGoogleCardsAdapter.addAll(getItems());
		
		
	}

	private ArrayList<String> getItems() {
		ArrayList<String> items = new ArrayList<String>();
		items.add("Name");
		items.add("Buttons");
		
		return items;
	}

	@Override
	public boolean handleMessage(Message message) {
		switch(message.what) {
		case AddFormulasListController.MESSAGE_TO_VIEW_MODEL_UPDATED:
			this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					
				}
			});
			return true;
		}
		return false;
	}
	
	/*
	 * ArrayAdapter has the Google Card Style 
	 */
	private class AddFormulasListAdapter extends ArrayAdapter<String> {
				
		private AddFormulasListActivity mContext;
		
		private View formulaNameCard;
		private EditText formulaName;
		
		private View buttonsCard;
		private Button addButton;
		

		public AddFormulasListAdapter(AddFormulasListActivity context) {
			mContext = context;
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
				boolean isOk = AddFormulasListActivity.this.controller.handleMessage(AddFormulasListController.MESSAGE_ADD_FORMULAS_LIST, args);
				if(isOk){
					Intent i = new Intent(mContext, FormulasDeleteItemsActivity.class);
					i.putExtra("NAME",name);
					mContext.startActivityForResult(i, 1);
				}
				else{
					String msg = AddFormulasListActivity.this.controller.getErrorMessage();
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
				formulaNameCard = LayoutInflater.from(mContext).inflate(R.layout.layout_formulas_list_name, parent, false);
				formulaName = (EditText) formulaNameCard.findViewById(R.id.activity_googlecards_card_et_formulas_list_name);
			} 
			
			return formulaNameCard;
		}

		//Buttons Card
		private View prepareCardFormulaButtons(View convertView, ViewGroup parent){

			if (buttonsCard == null) {
				buttonsCard = LayoutInflater.from(mContext).inflate(R.layout.layout_formulas_list_buttons, parent, false);
				addButton = (Button) buttonsCard.findViewById(R.id.buttton_add_formulas_list);
				addButton.setOnClickListener(addFormulaOnClick);			
			} 
			
			return buttonsCard;
		}


		
	}
	
}
