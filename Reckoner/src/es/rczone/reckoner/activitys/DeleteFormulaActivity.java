package es.rczone.reckoner.activitys;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
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
import es.rczone.reckoner.tools.Tools;

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
		//mListView.setDivider(null);
		mListView.setDivider(new ColorDrawable(0xFFC9C9C9));
		mListView.setDividerHeight(10);
		
		lists = new ListDAO().getAllLists();
		
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(new FormulasListAdapter(), this);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		
	}
	
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {//delete_formula_menu
		getMenuInflater().inflate(R.menu.delete_formula_menu, menu);
		return super.onCreateOptionsMenu(menu);
	 }
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	     switch(item.getItemId())
	     {
	     case R.id.show_names:
	    	 showNames = !showNames;
	    	 item.setChecked(showNames);
	 		 loadFormulas(selectedList);	    	 
	         break;
	     }
	     return super.onOptionsItemSelected(item);
	 }

	@Override
	public void onDismiss(AbsListView listView, int[] reverseSortedPositions) {
		for (int position : reverseSortedPositions) {
			mAdapter.remove(position);
		}
		Toast.makeText(this, "Removed positions: " + Arrays.toString(reverseSortedPositions), Toast.LENGTH_SHORT).show();
	}


	@Override
	public void deleteItem(int position) {
		
		Formula f = mAdapter.getItem(position); 
		
		new RFormulaListDAO().delete(selectedList, f.getName());
		
		mAdapter.remove(position);
		mAdapter.notifyDataSetChanged();
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
			mListView.setAdapter(mAdapter);
			this.setAnimAdapter(mListView, mAdapter);
		}
		
		setContextualUndoWithTimedDeleteAndCountDownAdapter();
	}
	
	private class MyListAdapter extends ArrayAdapter<Formula> {

		private Context mContext;
		private LruCache<Integer, Bitmap> mMemoryCache;
		private ImageView imageView;
		private TextView tv;

		public MyListAdapter(Context context, ArrayList<Formula> items) {
			super(items);
			mContext = context;
			
			final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

			// Use 1/8th of the available memory for this memory cache.
			final int cacheSize = maxMemory;
			mMemoryCache = new LruCache<Integer, Bitmap>(cacheSize) {
				@Override
				protected int sizeOf(Integer key, Bitmap bitmap) {
					// The cache size will be measured in kilobytes rather than
					// number of items.
					return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
				}
			};
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
			
			if(showNames){
				tv = (TextView) convertView;
				
				if (tv == null) {
					tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.list_row, parent, false);
				}
				tv.setText(getItem(position).getName());
				return tv;
			}
			else{
				imageView = (ImageView) convertView;
				if (imageView == null) {
					imageView = new ImageView(mContext);
				}
				
				Bitmap bitmap = getBitmapFromMemCache(position);
				if (bitmap == null) {
					File f = Tools.getImage(Formula.PATH_FOLDER, getItem(position).getName()+".gif", null);
					if(f!=null){
						bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
						double height = bitmap.getHeight()*2d;
						double width = bitmap.getWidth()*2d;
						bitmap = Tools.getResizedBitmap(bitmap, (int)height, (int)width);
						addBitmapToMemoryCache(position, bitmap);
					}
					else{
						tv = (TextView) convertView;
						
						if (tv == null) {
							tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.list_row, parent, false);
						}
						tv.setText(getItem(position).getName());
						return tv;
					}
				}
				imageView.setImageBitmap(bitmap);
				return imageView;
				
			}

		}
		
		private void addBitmapToMemoryCache(int key, Bitmap bitmap) {
			mMemoryCache.put(key, bitmap);
		}

		private Bitmap getBitmapFromMemCache(int key) {
			return mMemoryCache.get(key);
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
				tv = (TextView) LayoutInflater.from(DeleteFormulaActivity.this).inflate(android.R.layout.simple_list_item_1, parent, false);
			}

			tv.setText(getItem(position));

			return tv;
		}
	}
}
