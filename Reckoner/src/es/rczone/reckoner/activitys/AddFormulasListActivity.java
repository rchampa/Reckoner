package es.rczone.reckoner.activitys;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;

import es.rczone.reckoner.R;
import es.rczone.reckoner.activitys.adapters.AddFormulasListAdapter;
import es.rczone.reckoner.controllers.AddFormulasListController;

public class AddFormulasListActivity extends BaseActivity implements Handler.Callback{
	
	private AddFormulasListAdapter mGoogleCardsAdapter;
	private AddFormulasListController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_googlecards);

		controller = new AddFormulasListController(); 
		
		ListView listView = (ListView) findViewById(R.id.activity_googlecards_listview);

		mGoogleCardsAdapter = new AddFormulasListAdapter(this,controller);

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
	
}
