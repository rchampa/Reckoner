package es.rczone.reckoner.math.formulas.equations.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import es.rczone.reckoner.math.formulas.equations.R;

public class LicenseActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_license);
	}
}
