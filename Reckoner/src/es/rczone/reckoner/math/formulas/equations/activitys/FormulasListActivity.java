package es.rczone.reckoner.math.formulas.equations.activitys;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.haarman.listviewanimations.ArrayAdapter;
import com.haarman.listviewanimations.itemmanipulation.ExpandableListItemAdapter;

import es.rczone.reckoner.math.formulas.equations.R;
import es.rczone.reckoner.math.formulas.equations.ReckonerApp;
import es.rczone.reckoner.math.formulas.equations.activitys.customlayouts.FormulaLayout;
import es.rczone.reckoner.math.formulas.equations.dao.FormulaDAO;
import es.rczone.reckoner.math.formulas.equations.dao.ListDAO;
import es.rczone.reckoner.math.formulas.equations.dao.RFormulaListDAO;
import es.rczone.reckoner.math.formulas.equations.model.Formula;
import es.rczone.reckoner.math.formulas.equations.model.FormulasList;
import es.rczone.reckoner.math.formulas.equations.tools.Tools;

public class FormulasListActivity extends BaseActivity implements OnNavigationListener{

	private MyExpandableListItemAdapter mExpandableListItemAdapter;
	private boolean mLimited;
	private static SparseArray<FormulaLayout> bufferRows;
	
	private ListView mListView;
	
	ArrayList<String> lists;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_mylist);
		
		bufferRows = new SparseArray<FormulaLayout>();
		
		mListView = (ListView) findViewById(R.id.activity_mylist_listview);
		mListView.setDivider(null);

	
		lists = new ListDAO().getAllLists();

		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(new FormulasListAdapter(), this);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		
		Toast.makeText(this, R.string.explainexpand, Toast.LENGTH_SHORT).show();
		
	}
	
	public ArrayList<Formula> getItems_F() {
		return new FormulaDAO().getAllFormulas();
	}
	

	private static class MyExpandableListItemAdapter extends ExpandableListItemAdapter<Formula> {

		private Context mContext;
		private List<Formula> items;
		private FormulaLayout linear;
		private TextView textView;
		private ImageView imageView;
		
		private LruCache<Integer, Bitmap> mMemoryCache;

		/**
		 * Creates a new ExpandableListItemAdapter with the specified list, or an empty list if
		 * items == null.
		 */
		private MyExpandableListItemAdapter(Context context, List<Formula> items) {
			super(context, R.layout.activity_expandablelistitem_card, R.id.activity_expandablelistitem_card_title, R.id.activity_expandablelistitem_card_content, items);
			mContext = context;
			this.items = items;
			
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

		//FIXME optmize this please
		@Override
		public View getTitleView(int position, View convertView, ViewGroup parent) {
			
			
			Bitmap bitmap = getBitmapFromMemCache(position);
			if (bitmap == null) {
				//File f = Tools.getImage(Formula.PATH_FOLDER, items.get(position).getName()+".gif", null);
				File f = Tools.getImageFromInternal(ReckonerApp.getContext(), items.get(position).getName()+".gif", null);
				
				if(f!=null){
					
					bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
					/*double height = bitmap.getHeight()*1.6d;
					double width = bitmap.getWidth()*1.6d;
					bitmap = Tools.getResizedBitmap(bitmap, (int)height, (int)width);*/
					addBitmapToMemoryCache(position, bitmap);
					
					imageView = (ImageView) convertView;
					if (imageView == null) {
						imageView = new ImageView(mContext);
					}
					
					imageView.setImageBitmap(bitmap);
					return imageView;
				}
			}
			else{
				
				imageView = (ImageView) convertView;
				if (imageView == null) {
					imageView = new ImageView(mContext);
				}
				
				imageView.setImageBitmap(bitmap);
				return imageView;
				
			}
		
			textView = (TextView) convertView;
			if (textView == null) {
				textView = new TextView(mContext);
			}
			textView.setText(items.get(position).getFunctionFormula());
			return textView;
		
			
		}

		@Override
		public View getContentView(int position, View convertView, ViewGroup parent) {

			linear = bufferRows.get(position);
			if(linear==null){
				linear = new FormulaLayout(mContext,LinearLayout.HORIZONTAL, items.get(position));
				bufferRows.put(position, linear);
			}
			
			return linear;
		}
		
		private void addBitmapToMemoryCache(int key, Bitmap bitmap) {
			mMemoryCache.put(key, bitmap);
		}

		private Bitmap getBitmapFromMemCache(int key) {
			return mMemoryCache.get(key);
		}

		
		public void setList(List<Formula> items){
			this.items = items;
		}
		
		@Override
		public void clear(){
			mMemoryCache.evictAll();
			bufferRows.clear();
			super.clear();
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
	
	private class FormulasListAdapter extends ArrayAdapter<String> {

		public FormulasListAdapter() {
			
			addAll(lists);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = (TextView) convertView;
			if (tv == null) {
				tv = (TextView) LayoutInflater.from(FormulasListActivity.this).inflate(android.R.layout.simple_list_item_1, parent, false);
			}

			tv.setText(getItem(position));

			return tv;
		}
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		
		String name = this.lists.get(itemPosition);
		loadFormulas(name);
		return true;
	}
	
	private synchronized void loadFormulas(String nameList){
		
		FormulasList list = new RFormulaListDAO().get(nameList);
		
		//XXX fix this 
		if(mExpandableListItemAdapter!=null){
			mExpandableListItemAdapter.clear();
			mExpandableListItemAdapter.addAll(list.getCopyOfList());
			mExpandableListItemAdapter.setList(list.getCopyOfList());
			mExpandableListItemAdapter.notifyDataSetChanged();
			
		}
		else{
			mExpandableListItemAdapter = new MyExpandableListItemAdapter(this, list.getCopyOfList());

			mListView.setAdapter(mExpandableListItemAdapter);
			this.setAnimAdapter(mListView, mExpandableListItemAdapter);
		}
		
	}
	
	

}
