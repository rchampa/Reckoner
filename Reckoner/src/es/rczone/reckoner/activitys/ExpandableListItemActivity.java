package es.rczone.reckoner.activitys;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haarman.listviewanimations.itemmanipulation.ExpandableListItemAdapter;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter.DeleteItemCallback;
import com.haarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;

import es.rczone.reckoner.R;

public class ExpandableListItemActivity extends MyListActivity implements DeleteItemCallback{

	private MyExpandableListItemAdapter mExpandableListItemAdapter;
	private boolean mLimited;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mExpandableListItemAdapter = new MyExpandableListItemAdapter(this, getItems());
		ScaleInAnimationAdapter alphaInAnimationAdapter = new ScaleInAnimationAdapter(mExpandableListItemAdapter);
		alphaInAnimationAdapter.setAbsListView(getListView());
		alphaInAnimationAdapter.setInitialDelayMillis(500);
		getListView().setAdapter(alphaInAnimationAdapter);
	
		ContextualUndoAdapter adapter = new ContextualUndoAdapter(mExpandableListItemAdapter, R.layout.undo_row, R.id.undo_row_undobutton);
		adapter.setAbsListView(getListView());
		adapter.setDeleteItemCallback(this);
		getListView().setAdapter(adapter);
		
		

		Toast.makeText(this, R.string.explainexpand, Toast.LENGTH_LONG).show();
	}
	
	
	@Override
	public void deleteItem(int position) {
		mExpandableListItemAdapter.remove(position);
		mExpandableListItemAdapter.notifyDataSetChanged();
	}

	private static class MyExpandableListItemAdapter extends ExpandableListItemAdapter<Integer> {

		private Context mContext;

		/**
		 * Creates a new ExpandableListItemAdapter with the specified list, or an empty list if
		 * items == null.
		 */
		private MyExpandableListItemAdapter(Context context, List<Integer> items) {
			super(context, R.layout.activity_expandablelistitem_card, R.id.activity_expandablelistitem_card_title, R.id.activity_expandablelistitem_card_content, items);
			mContext = context;

		}

		@Override
		public View getTitleView(int position, View convertView, ViewGroup parent) {
			TextView tv = (TextView) convertView;
			if (tv == null) {
				tv = new TextView(mContext);
			}
			tv.setText(mContext.getString(R.string.expandorcollapsecard, getItem(position)));
			return tv;
		}

		@Override
		public View getContentView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = (ImageView) convertView;
			if (imageView == null) {
				imageView = new ImageView(mContext);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			}

			
			return imageView;
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
