package es.rczone.reckoner.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import es.rczone.reckoner.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	public void onClick(View v) {
		
		Intent intent;
		
		switch(v.getId()){
			case R.id.button1:
	        	intent = new Intent(this, AddFormulaActivity.class);
	        	startActivity(intent);
	        	break;
		
            case R.id.button2:
            	intent = new Intent(this, ExpandableListItemActivity.class);
            	startActivity(intent);
            	break;
		}
	}

}
