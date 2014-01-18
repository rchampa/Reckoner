package es.rczone.reckoner.math.formulas.equations.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.haarman.listviewanimations.ArrayAdapter;

import es.rczone.reckoner.math.formulas.equations.R;

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
					case 1: intent = new Intent(MainMenuActivity.this, AddListActivity.class);
			            	startActivity(intent);
			            	break;
					case 2: intent = new Intent(MainMenuActivity.this, FormulasListActivity.class);
			            	startActivity(intent);
			            	break;
					case 3: intent = new Intent(MainMenuActivity.this, DeleteFormulaActivity.class);
			            	startActivity(intent);
			            	break;
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	     MenuInflater inflater=getMenuInflater();
	     inflater.inflate(R.menu.main_menu, menu);
	     return super.onCreateOptionsMenu(menu);

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Intent intent;
	     switch(item.getItemId())
	     {
		     case R.id.help:	intent = new Intent(MainMenuActivity.this, HelpActivity.class);
						        	startActivity(intent);
						        	break;
		     case R.id.license:		intent = new Intent(MainMenuActivity.this, LicenseActivity.class);
						        	startActivity(intent);
						        	break;
	     }
	     return true;
	}
	private ArrayList<String> getItems() {
		ArrayList<String> items = new ArrayList<String>();
		items.add("Formula");
		items.add("AddList");
		items.add("ShowLists");
		items.add("Delete");		
		return items;
	}
	
	private class AddFormulaAdapter extends ArrayAdapter<String> {
		
		private Context mContext;
				
		private View formulaCard;
		private View addListCard;
		private View showListCard;
		private View deleteCard;
		

		public AddFormulaAdapter(Context context) {
			mContext = context;
		}

		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
						
			switch(position){
				case 0: return prepareCardFormulas(convertView, parent);
				case 1: return prepareAddListCard(convertView, parent);
				case 2: return prepareCardList(convertView, parent);
				case 3: return prepareCardDelete(convertView, parent);
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
		
		
		private View prepareAddListCard(View convertView, ViewGroup parent){
			
			if (addListCard == null) {
				addListCard = LayoutInflater.from(mContext).inflate(R.layout.layout_mainmenu_addlist, parent, false);
			} 
			
			return addListCard;
		}
		
		private View prepareCardList(View convertView, ViewGroup parent){
			
			if (showListCard == null) {
				showListCard = LayoutInflater.from(mContext).inflate(R.layout.layout_mainmenu_lists, parent, false);
			} 
			
			return showListCard;
		}

		

		//Buttons Card
		private View prepareCardDelete(View convertView, ViewGroup parent){

			if (deleteCard == null) {
				deleteCard = LayoutInflater.from(mContext).inflate(R.layout.layout_mainmenu_delete, parent, false);
				
			} 
			
			return deleteCard;
		}

		
	}
	
}
