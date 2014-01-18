package es.rczone.reckoner.math.activitys.customlayouts;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import es.rczone.dariuslib.me.Expr;
import es.rczone.dariuslib.me.Parser;
import es.rczone.dariuslib.me.SyntaxException;
import es.rczone.dariuslib.me.Variable;
import es.rczone.reckoner.math.activitys.customlayouts.MyButton.StateButton;
import es.rczone.reckoner.math.model.Formula;

/**
 * LinearLayout.VERTICAL
 * LinearLayout.HORIZONTAL
 * @author Ricardo
 *
 */
public class FormulaLayout extends LinearLayout implements OnChangeValueListener{
	
	private List<MyButton> listButtons;
	private String formula;
	private TextView label;
	
	private boolean isChecked;
	private Expr expr;
	
	public FormulaLayout(Context context, int orientation, Formula formula) {
		super(context);
		this.formula = formula.getFunctionFormula();
		isChecked = false;
		listButtons = new ArrayList<MyButton>(formula.getNumVars());
		setOrientation(orientation);
		float total_weight = 100f;
		float result_weight = total_weight/5f;
		float var_weight = (total_weight-result_weight)/(float)formula.getNumVars();
		
		MyButton tv;	
		for(String var : formula.getVariables()){
			tv = new MyButton(context,var,this);
	        tv.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, var_weight));
	        this.addView(tv);
	        listButtons.add(tv);
		}

		
		label = new TextView(context);
		label.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, result_weight));
        this.addView(label);
	}
	
	@Override
	public void onChange(MyButton button) {
		
		boolean radians = false;
		
		String[] procs1 = {"acos", "asin", "atan","atan2", "cos","sin", "tan" };
		
		for(String s : procs1){
			if(formula.contains(s)){
				radians = true;
			}
		}
		
		Variable var;
		for(MyButton b : listButtons){
			if(b.getState()!=StateButton.READY){
				return;
			}
			else{
				var = Variable.make(b.getName());
				double d = b.getValue();
				if(radians) 
					d = Math.toRadians(d);
				var.setValue(d);
			}
		}
		
		
		if(!isChecked){
			
			try {
				expr = Parser.parse(formula);
				isChecked=true;
			} catch (SyntaxException e) {
				System.err.println(e.explain());
				return;
			}
		}
		
		label.setText("  "+expr.value());
				
	}
	
	
}
