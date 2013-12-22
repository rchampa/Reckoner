package es.rczone.reckoner.activitys;

import java.util.List;

import android.os.Bundle;
import es.rczone.reckoner.R;
import es.rczone.reckoner.dao.FormulasListDAO;
import es.rczone.reckoner.model.Formula;

public class FormulasPickerActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formulas_picker);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String value = extras.getString("NAME");
		    List<Formula> list =new FormulasListDAO().getRemainingFormulas(value);
		    
		    String f = "";
		    int i = (int)2.0;
		    boolean b = false;
		    boolean c = !!b;
		}
	}

}
