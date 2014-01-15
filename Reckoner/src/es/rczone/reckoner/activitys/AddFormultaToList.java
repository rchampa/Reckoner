package es.rczone.reckoner.activitys;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.haarman.listviewanimations.ArrayAdapter;
import com.haarman.listviewanimations.itemmanipulation.AnimateDismissAdapter;
import com.haarman.listviewanimations.itemmanipulation.OnDismissCallback;

import es.rczone.reckoner.R;
import es.rczone.reckoner.dao.ListDAO;
import es.rczone.reckoner.dao.RFormulaListDAO;
import es.rczone.reckoner.model.Formula;
import es.rczone.reckoner.model.FormulasList;

public class AddFormultaToList extends BaseActivity implements OnNavigationListener{

	private List<Integer> mSelectedPositions;
	private MyListAdapter mAdapter;
	private ListView mListView;
	private ArrayList<String> lists;
	private String selectedList = "";
	private ArrayList<String> formulas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animateremoval);

		lists = new ListDAO().getAllLists();
		mSelectedPositions = new ArrayList<Integer>();
		formulas = new ArrayList<String>();

		mListView = (ListView) findViewById(R.id.activity_animateremoval_listview);
		
		final AnimateDismissAdapter<String> animateDismissAdapter = new AnimateDismissAdapter<String>(mAdapter, new MyOnDismissCallback());
		animateDismissAdapter.setAbsListView(mListView);
		mListView.setAdapter(animateDismissAdapter);

		Button button = (Button) findViewById(R.id.activity_animateremoval_button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				animateDismissAdapter.animateDismiss(mSelectedPositions);				
				mSelectedPositions.clear();
				formulas.clear();
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CheckedTextView tv = ((CheckedTextView) view);
				tv.toggle();
				if (tv.isChecked()) {
					mSelectedPositions.add(position);
					//Log.d("Adapter",""+position);
					formulas.add(mAdapter.getItem(position).getFunctionFormula());
				} else {
					mSelectedPositions.remove((Integer) position);
					formulas.remove(mAdapter.getItem(position).getFunctionFormula());
				}
			}
		});
		
		
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(new FormulasListAdapter(), this);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
	}
	
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		
		selectedList = this.lists.get(itemPosition);
		loadFormulas(selectedList);
		return true;
	}
	
	private synchronized void loadFormulas(String nameList){
		
		FormulasList list = new RFormulaListDAO().getRemainingFormulas(nameList);
		
		//XXX fix this 
		if(mAdapter!=null){
			mAdapter.clear();
			mAdapter.addAll(list.getCopyOfList());
			//adapter.setList(list.getCopyOfList());
			mAdapter.notifyDataSetChanged();
			
		}
		else{
			mAdapter = new MyListAdapter(this, list.getCopyOfList());
			mListView.setAdapter(mAdapter);
		}
		
	}
	
	private class FormulasListAdapter extends ArrayAdapter<String> {

		public FormulasListAdapter() {
			
			addAll(lists);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = (TextView) convertView;
			if (tv == null) {
				tv = (TextView) LayoutInflater.from(AddFormultaToList.this).inflate(android.R.layout.simple_list_item_1, parent, false);
			}

			tv.setText(getItem(position));

			return tv;
		}
	}

	private class MyOnDismissCallback implements OnDismissCallback {

		@Override
		public void onDismiss(AbsListView listView, int[] reverseSortedPositions) {
			for (int position : reverseSortedPositions) {
				mAdapter.remove(position);
			}
		}
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
			tv.setText(getItem(position).getFunctionFormula());
			return tv;
		}
		
		/*public void setList(List<Formula> items){
			this.items = items;
		}*/
		
	}
}
