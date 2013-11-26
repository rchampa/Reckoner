package es.rczone.reckoner.activitys.customlayouts;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * LinearLayout.VERTICAL
 * LinearLayout.HORIZONTAL
 * @author Ricardo
 *
 */
public class MyLinearLayout extends LinearLayout{
	
	LinearLayout lin, lin2;

	public MyLinearLayout(Context context, int orientation/*, List<String> vars*/) {
		super(context);
		setOrientation(orientation);
		        
		EditText tv = new EditText(context);
        tv.setText("X");
        tv.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));//
        
        
        
        EditText tv2 = new EditText(context);
        tv2.setText("y");
        tv2.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
        
        
        EditText tv3 = new EditText(context);
        tv3.setText("z");
        tv3.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
        
  
        this.addView(tv);
        this.addView(tv2);
        this.addView(tv3);      
        
       
        
	}
	
	
		

}
