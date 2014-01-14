package es.rczone.reckoner.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import es.rczone.reckoner.R;

public class MainActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	     MenuInflater inflater=getMenuInflater();
	     inflater.inflate(R.menu.main_menu, menu);
	     return super.onCreateOptionsMenu(menu);

	 }
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	     switch(item.getItemId())
	     {
	     case R.id.animations:
	         break;
	     case R.id.parser:
	         break;
	     }
	     return true;
	 }
	public void onClick(View v) {
		
		Intent intent;
		
		switch(v.getId()){
			case R.id.button1:
	        	intent = new Intent(this, AddFormulaActivity.class);
	        	startActivity(intent);
	        	break;
		
            case R.id.button2:
            	intent = new Intent(this, FormulasListActivity.class);
            	startActivity(intent);
            	break;
            	
            case R.id.button3:
            	intent = new Intent(this,AddListActivity.class);
            	startActivity(intent);
            	break;
            	
            case R.id.button4:
            	intent = new Intent(this, DeleteFormulaActivity.class);
            	startActivity(intent);
            	break;
		}
	}

}
