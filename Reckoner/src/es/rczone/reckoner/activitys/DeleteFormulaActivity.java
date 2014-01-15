package es.rczone.reckoner.activitys;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.haarman.listviewanimations.ArrayAdapter;
import com.haarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter.CountDownFormatter;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter.DeleteItemCallback;

import es.rczone.reckoner.R;
import es.rczone.reckoner.dao.ListDAO;
import es.rczone.reckoner.dao.RFormulaListDAO;
import es.rczone.reckoner.model.Formula;
import es.rczone.reckoner.model.FormulasList;

public class DeleteFormulaActivity extends BaseActivity implements OnNavigationListener, OnDismissCallback, DeleteItemCallback {

	private ListView mListView;
	private ArrayList<String> lists;
	private MyListAdapter mAdapter;
	private String selectedList = "";
	private boolean showNames = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mylist);
		
		mListView = (ListView) findViewById(R.id.activity_mylist_listview);
		mListView.setDivider(null);

		
		lists = new ListDAO().getAllLists();
		
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(new FormulasListAdapter(), this);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

	}
	
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	     MenuInflater inflater=getMenuInflater();
	     inflater.inflate(R.menu.delete_formula_menu, menu);
	     return super.onCreateOptionsMenu(menu);

	 }
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	     switch(item.getItemId())
	     {
	     case R.id.show_names:
	    	 showNames = !showNames;
	    	 item.setChecked(showNames);
	    	 	    	 
	         break;
	     }
	     return true;
	 }

	@Override
	public void onDismiss(AbsListView listView, int[] reverseSortedPositions) {
		for (int position : reverseSortedPositions) {
			mAdapter.remove(position);
		}
		Toast.makeText(this, "Removed positions: " + Arrays.toString(reverseSortedPositions), Toast.LENGTH_SHORT).show();
	}

	private void setContextualUndoAdapter() {
		ContextualUndoAdapter adapter = new ContextualUndoAdapter(mAdapter, R.layout.undo_row, R.id.undo_row_undobutton);
		adapter.setAbsListView(mListView);
		mListView.setAdapter(adapter);
		adapter.setDeleteItemCallback(this);
	}

	@Override
	public void deleteItem(int position) {
		
		Formula f = mAdapter.getItem(position); 
		
		new RFormulaListDAO().delete(selectedList, f.getName());
		
		mAdapter.remove(position);
		mAdapter.notifyDataSetChanged();
	}

	private void setContextualUndoWithTimedDeleteAdapter() {
		ContextualUndoAdapter adapter = new ContextualUndoAdapter(mAdapter, R.layout.undo_row, R.id.undo_row_undobutton, 3000);
		adapter.setAbsListView(mListView);
		mListView.setAdapter(adapter);
		adapter.setDeleteItemCallback(this);
	}

	private void setContextualUndoWithTimedDeleteAndCountDownAdapter() {
		ContextualUndoAdapter adapter = new ContextualUndoAdapter(mAdapter, R.layout.undo_row, R.id.undo_row_undobutton, 3000, R.id.undo_row_texttv, new MyFormatCountDownCallback());
		adapter.setAbsListView(mListView);
		mListView.setAdapter(adapter);
		adapter.setDeleteItemCallback(this);
	}


	private class MyFormatCountDownCallback implements CountDownFormatter {

		@Override
		public String getCountDownString(long millisUntilFinished) {
			int seconds = (int) Math.ceil((millisUntilFinished / 1000.0));

			if (seconds > 0) {
				return getResources().getQuantityString(R.plurals.countdown_seconds, seconds, seconds);
			}
			return getString(R.string.countdown_dismissing);
		}
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		
		selectedList = this.lists.get(itemPosition);
		loadFormulas(selectedList);
		return true;
	}
	
	private synchronized void loadFormulas(String nameList){
		
		FormulasList list = new RFormulaListDAO().get(nameList);
		
		//XXX fix this 
		if(mAdapter!=null){
			mAdapter.clear();
			mAdapter.addAll(list.getCopyOfList());
			//adapter.setList(list.getCopyOfList());
			mAdapter.notifyDataSetChanged();
			
		}
		else{
			mAdapter = new MyListAdapter(this, list.getCopyOfList());
			/*ScaleInAnimationAdapter anim = new ScaleInAnimationAdapter(mExpandableListItemAdapter);
			anim.setAbsListView(mListView);
			anim.setInitialDelayMillis(500);*/
			mListView.setAdapter(mAdapter);
		}
		
		setContextualUndoWithTimedDeleteAndCountDownAdapter();
	}
	
	private class MyListAdapter extends ArrayAdapter<Formula> {

		private Context mContext;

		public MyListAdapter(Context context, ArrayList<Formula> items) {
			super(items);
			mContext = context;
		}

		@Override
		public long getItemId(int position) {
			return getItem(position).hashCode();
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = (TextView) convertView;
			if (tv == null) {
				tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.list_row, parent, false);
			}
			
			if(showNames){
				tv.setText(getItem(position).getName());
			}
			else{
				tv.setText(getItem(position).getFunctionFormula());
			}
			return tv;
		}
		
		/*public void setList(List<Formula> items){
			this.items = items;
		}*/
		
	}

	private class FormulasListAdapter extends ArrayAdapter<String> {

		public FormulasListAdapter() {
			
			addAll(lists);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = (TextView) convertView;
			if (tv == null) {
				tv = (TextView) LayoutInflater.from(DeleteFormulaActivity.this).inflate(android.R.layout.simple_list_item_1, parent, false);
			}

			tv.setText(getItem(position));

			return tv;
		}
	}
}
