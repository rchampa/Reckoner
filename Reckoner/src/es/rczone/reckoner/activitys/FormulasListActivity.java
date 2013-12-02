package es.rczone.reckoner.activitys;

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
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter.DeleteItemCallback;
import com.haarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;

import es.rczone.reckoner.R;
import es.rczone.reckoner.activitys.customlayouts.FormulaLayout;
import es.rczone.reckoner.dao.FormulaDAO;
import es.rczone.reckoner.model.Formula;
import es.rczone.reckoner.tools.Tools;

public class FormulasListActivity extends BaseActivity implements DeleteItemCallback, OnNavigationListener{

	private MyExpandableListItemAdapter mExpandableListItemAdapter;
	private boolean mLimited;
	private static SparseArray<FormulaLayout> bufferRows;
	
	private ListView mListView;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_mylist);
		
		bufferRows = new SparseArray<FormulaLayout>();
		
		mListView = (ListView) findViewById(R.id.activity_mylist_listview);
		mListView.setDivider(null);

		mExpandableListItemAdapter = new MyExpandableListItemAdapter(this, getItems_F());
		ScaleInAnimationAdapter alphaInAnimationAdapter = new ScaleInAnimationAdapter(mExpandableListItemAdapter);
		alphaInAnimationAdapter.setAbsListView(mListView);
		alphaInAnimationAdapter.setInitialDelayMillis(500);
		mListView.setAdapter(alphaInAnimationAdapter);
	

		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(new FormulasListAdapter(), this);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		
		

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

		@Override
		public View getTitleView(int position, View convertView, ViewGroup parent) {
			
			File f = Tools.getImage(Formula.PATH_FOLDER, items.get(position).getName()+".gif", null);
			
			if(f!=null){
				imageView = (ImageView) convertView;
				if (imageView == null) {
					imageView = new ImageView(mContext);
				}
				
				Bitmap bitmap = getBitmapFromMemCache(position);
				if (bitmap == null) {
					bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
					double height = bitmap.getHeight()*1.6d;
					double width = bitmap.getWidth()*1.6d;
					bitmap = Tools.getResizedBitmap(bitmap, (int)height, (int)width);
					addBitmapToMemoryCache(position, bitmap);
				}
				imageView.setImageBitmap(bitmap);
				
				return imageView;
			}
			else{
			
				textView = (TextView) convertView;
				if (textView == null) {
					textView = new TextView(mContext);
				}
				textView.setText(items.get(position).getFunctionFormula());
				return textView;
			}
			
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
			addAll("Alpha", "Left", "Right", "Bottom", "Bottom right", "Scale");
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
		// TODO Auto-generated method stub
		return false;
	}

}
