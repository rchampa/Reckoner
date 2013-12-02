package es.rczone.reckoner.activitys;

import android.os.Bundle;
import es.rczone.reckoner.R;

public class FormulasPickerActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formulas_picker);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String value = extras.getString("NAME");
		}
	}

}
