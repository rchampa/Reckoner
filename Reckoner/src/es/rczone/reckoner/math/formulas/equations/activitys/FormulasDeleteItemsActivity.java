package es.rczone.reckoner.math.formulas.equations.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.haarman.listviewanimations.ArrayAdapter;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter.DeleteItemCallback;

import es.rczone.reckoner.math.formulas.equations.R;
import es.rczone.reckoner.math.formulas.equations.dao.RFormulaListDAO;
import es.rczone.reckoner.math.formulas.equations.model.Formula;
import es.rczone.reckoner.math.formulas.equations.model.FormulasList;

public class FormulasDeleteItemsActivity extends BaseActivity implements DeleteItemCallback{
	
	
	private MyListAdapter mAdapter;
	private ListView mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mylist);
		
		String name ="";
		
		mAdapter = new MyListAdapter(this, getItems_F(name));
		
		mListView = (ListView) findViewById(R.id.activity_mylist_listview);
		mListView.setDivider(null);
		
		setContextualUndoAdapter();

	}

	public ArrayList<Formula> getItems_F(String name) {
		FormulasList lista = new RFormulaListDAO().get(name);
		if(lista==null)
			return new ArrayList<Formula>();
		else{
			return lista.getCopyOfList();
		}
	}
	
	private void setContextualUndoAdapter() {
		ContextualUndoAdapter adapter = new ContextualUndoAdapter(mAdapter, R.layout.undo_row, R.id.undo_row_undobutton);
		adapter.setAbsListView(mListView);
		mListView.setAdapter(adapter);
		adapter.setDeleteItemCallback(this);
	}


	@Override
	public void deleteItem(int position) {
		mAdapter.remove(position);
		mAdapter.notifyDataSetChanged();
	}
	
	private static class MyListAdapter extends ArrayAdapter<Formula> {

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
			tv.setText("This is row number " + getItem(position));
			return tv;
		}
	}
}
