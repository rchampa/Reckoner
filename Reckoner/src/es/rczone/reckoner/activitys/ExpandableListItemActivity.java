package es.rczone.reckoner.activitys;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.haarman.listviewanimations.itemmanipulation.ExpandableListItemAdapter;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter.DeleteItemCallback;
import com.haarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;

import es.rczone.reckoner.R;
import es.rczone.reckoner.activitys.customlayouts.FormulaLayout;
import es.rczone.reckoner.dao.FormulaDAO;
import es.rczone.reckoner.model.Formula;

public class ExpandableListItemActivity extends BaseActivity implements DeleteItemCallback{

	private MyExpandableListItemAdapter mExpandableListItemAdapter;
	private boolean mLimited;
	
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_mylist);
		mListView = (ListView) findViewById(R.id.activity_mylist_listview);
		mListView.setDivider(null);

		mExpandableListItemAdapter = new MyExpandableListItemAdapter(this, getItems_F());
		ScaleInAnimationAdapter alphaInAnimationAdapter = new ScaleInAnimationAdapter(mExpandableListItemAdapter);
		alphaInAnimationAdapter.setAbsListView(mListView);
		alphaInAnimationAdapter.setInitialDelayMillis(500);
		mListView.setAdapter(alphaInAnimationAdapter);
	
//		ContextualUndoAdapter adapter = new ContextualUndoAdapter(mExpandableListItemAdapter, R.layout.undo_row, R.id.undo_row_undobutton);
//		adapter.setAbsListView(getListView());
//		adapter.setDeleteItemCallback(this);
//		getListView().setAdapter(adapter);
		
		

		Toast.makeText(this, R.string.explainexpand, Toast.LENGTH_SHORT).show();
	}
	
	public ArrayList<Formula> getItems_F() {
		return new FormulaDAO().getAllFormulas();
	}
	
	
	@Override
	public void deleteItem(int position) {
		mExpandableListItemAdapter.remove(position);
		mExpandableListItemAdapter.notifyDataSetChanged();
	}

	private static class MyExpandableListItemAdapter extends ExpandableListItemAdapter<Formula> {

		private Context mContext;
		private List<Formula> items;

		/**
		 * Creates a new ExpandableListItemAdapter with the specified list, or an empty list if
		 * items == null.
		 */
		private MyExpandableListItemAdapter(Context context, List<Formula> items) {
			super(context, R.layout.activity_expandablelistitem_card, R.id.activity_expandablelistitem_card_title, R.id.activity_expandablelistitem_card_content, items);
			mContext = context;
			this.items = items;
		}

		@Override
		public View getTitleView(int position, View convertView, ViewGroup parent) {
			TextView tv = (TextView) convertView;
			if (tv == null) {
				tv = new TextView(mContext);
			}
			tv.setText(items.get(position).getFunctionFormula());
			return tv;
		}

		@Override
		public View getContentView(int position, View convertView, ViewGroup parent) {
			FormulaLayout linear = (FormulaLayout) convertView;
			if (linear == null) {
				linear = new FormulaLayout(mContext,LinearLayout.HORIZONTAL, items.get(position));
				
				Log.i("LinearLayout", "Nuevo objeto");
			}
			Log.i("LinearLayout", "Despliega");
			
			return linear;
		}

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_expandablelistitem, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_expandable_limit:
			mLimited = !mLimited;
			item.setChecked(mLimited);
			mExpandableListItemAdapter.setLimit(mLimited ? 2 : 0);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
