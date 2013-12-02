package es.rczone.reckoner.activitys;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.haarman.listviewanimations.ArrayAdapter;
import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;

import es.rczone.reckoner.R;
import es.rczone.reckoner.activitys.adapters.AddFormulaAdapter;
import es.rczone.reckoner.controllers.AddFormulasController;

public class AddFormulaActivity extends BaseActivity {
	
	private AddFormulaAdapter mGoogleCardsAdapter;
	private AddFormulasController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_googlecards);

		controller = new AddFormulasController(); 
		
		ListView listView = (ListView) findViewById(R.id.activity_googlecards_listview);

		mGoogleCardsAdapter = new AddFormulaAdapter(this,controller);

		AnimationAdapter animAdapter = new ScaleInAnimationAdapter(mGoogleCardsAdapter);
		animAdapter.setAbsListView(listView);
		animAdapter.setInitialDelayMillis(500);
		listView.setAdapter(animAdapter);

		mGoogleCardsAdapter.addAll(getItems());
		
		
	}

	private ArrayList<String> getItems() {
		ArrayList<String> items = new ArrayList<String>();
		items.add("Name");
		items.add("Formula");
		items.add("Buttons");
		
		return items;
	}
	
}
