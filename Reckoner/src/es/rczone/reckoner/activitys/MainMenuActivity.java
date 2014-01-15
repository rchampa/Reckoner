package es.rczone.reckoner.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.haarman.listviewanimations.ArrayAdapter;

import es.rczone.reckoner.R;

public class MainMenuActivity extends BaseActivity {

	
	private AddFormulaAdapter mGoogleCardsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_googlecards);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		
		ListView listView = (ListView) findViewById(R.id.activity_googlecards_listview);

		mGoogleCardsAdapter = new AddFormulaAdapter(this);
		mGoogleCardsAdapter.addAll(getItems());
		
		listView.setAdapter(mGoogleCardsAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				Intent intent;
				
				switch(position){
					case 0: intent = new Intent(MainMenuActivity.this, AddFormulaActivity.class);
				        	startActivity(intent);
				        	break;
					case 1: intent = new Intent(MainMenuActivity.this, FormulasListActivity.class);
			            	startActivity(intent);
			            	break;
					case 2: intent = new Intent(MainMenuActivity.this, DeleteFormulaActivity.class);
			            	startActivity(intent);
			            	break;
				}
			}
		});
		
	}

	private ArrayList<String> getItems() {
		ArrayList<String> items = new ArrayList<String>();
		items.add("Formula");
		items.add("List");
		items.add("Delete");		
		return items;
	}
	
	private class AddFormulaAdapter extends ArrayAdapter<String> {
		
		private Context mContext;
				
		private View formulaCard;
		private View formulaSpinnerCard;
		private View buttonsCard;
		

		public AddFormulaAdapter(Context context) {
			mContext = context;
		}

		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
						
			switch(position){
				case 0: return prepareCardFormulas(convertView, parent);
				case 1: return prepareCardList(convertView, parent);
				case 2: return prepareCardDelete(convertView, parent);
			}
			
			return convertView;
		}
		

		//Formula Card
		private View prepareCardFormulas(View convertView, ViewGroup parent){
			
			if (formulaCard == null) {
				formulaCard = LayoutInflater.from(mContext).inflate(R.layout.layout_mainmenu_formulas, parent, false);
			} 
			
			return formulaCard;
		}
		
		//Spinner list
		private View prepareCardList(View convertView, ViewGroup parent){
			
			if (formulaSpinnerCard == null) {
				formulaSpinnerCard = LayoutInflater.from(mContext).inflate(R.layout.layout_mainmenu_lists, parent, false);
			} 
			
			return formulaSpinnerCard;
		}

		

		//Buttons Card
		private View prepareCardDelete(View convertView, ViewGroup parent){

			if (buttonsCard == null) {
				buttonsCard = LayoutInflater.from(mContext).inflate(R.layout.layout_mainmenu_delete, parent, false);
				
			} 
			
			return buttonsCard;
		}

		
	}
	
}
