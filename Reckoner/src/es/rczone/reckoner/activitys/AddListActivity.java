package es.rczone.reckoner.activitys;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.haarman.listviewanimations.ArrayAdapter;

import es.rczone.reckoner.R;
import es.rczone.reckoner.controllers.AddFormulasController;
import es.rczone.reckoner.controllers.AddListController;

public class AddListActivity extends BaseActivity {
	
	public static final int NAME_LIST = 100; 

	private AddListAdapter mGoogleCardsAdapter;
	private AddListController controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_googlecards);

		controller = new AddListController(); 
		
		ListView listView = (ListView) findViewById(R.id.activity_googlecards_listview);

		mGoogleCardsAdapter = new AddListAdapter();

		setAnimAdapter(listView, mGoogleCardsAdapter);

		mGoogleCardsAdapter.addAll(getItems());
		
		
	}
	
	private ArrayList<String> getItems() {
		ArrayList<String> items = new ArrayList<String>();
		items.add("Name");
		items.add("Buttons");
		
		return items;
	}
	
	private class AddListAdapter extends ArrayAdapter<String> {
			
		private View listNameCard;
		private EditText listName;
		
		private View buttonsCard;
		private Button addButton;
		private Button okButton;
		
		public AddListAdapter() {}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			switch(position){
				case 0: return prepareCardNameList(convertView, parent);
				case 1: return prepareCardListButtons(convertView, parent);
			}
		
			return convertView;
		}
		
		//Name of list Card
		private View prepareCardNameList(View convertView, ViewGroup parent){
			
			if (listNameCard == null) {
				listNameCard = LayoutInflater.from(AddListActivity.this).inflate(R.layout.googlecard_add_list_name, parent, false);
				listName = (EditText) listNameCard.findViewById(R.id.activity_googlecards_card_et_formula_name);		
			} 
			
			return listNameCard;
		}
		
		//Buttons Card
		private View prepareCardListButtons(View convertView, ViewGroup parent){

			if (buttonsCard == null) {
				buttonsCard = LayoutInflater.from(AddListActivity.this).inflate(R.layout.googlecard_add_list_buttons, parent, false);
				addButton = (Button) buttonsCard.findViewById(R.id.buttton_addlist_add);
				addButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String nameList = listName.getText().toString();
						if("".equals(nameList)){
							Toast.makeText(AddListActivity.this, "Please fill the name", Toast.LENGTH_SHORT).show();
							return;
						}
						
						boolean isOk = AddListActivity.this.controller.handleMessage(AddListController.MESSAGE_ADD_LIST, nameList);
						if(isOk){
							Toast.makeText(AddListActivity.this, "Your list was added succesfully.", Toast.LENGTH_SHORT).show();
							listName.setText("");
						}
						else{
							String msg = AddListActivity.this.controller.getErrorMessage();
							Toast.makeText(AddListActivity.this, msg, Toast.LENGTH_SHORT).show();

						}
						
					}
				});	
				okButton = (Button) buttonsCard.findViewById(R.id.buttton_addlist_finish);
				okButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						
					}
				});
			} 
			
			return buttonsCard;
		}
	}
}
