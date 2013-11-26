package es.rczone.reckoner.activitys;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;

import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;

import es.rczone.reckoner.R;
import es.rczone.reckoner.activitys.adapters.GoogleCardsAdapter;
import es.rczone.reckoner.controllers.AddFormulasController;

public class AddFormulaActivity extends BaseActivity {
	
	private GoogleCardsAdapter mGoogleCardsAdapter;
	private AddFormulasController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_googlecards);

		controller = new AddFormulasController(); 
		
		ListView listView = (ListView) findViewById(R.id.activity_googlecards_listview);

		mGoogleCardsAdapter = new GoogleCardsAdapter(this,controller);

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
