package es.rczone.reckoner.math.formulas.equations.activitys.customlayouts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyButton extends Button {

	public enum StateButton{INIT,INVALID,READY};
	
	private Context context;
	private String varName;
	private double varValue;
	private StateButton state;
	private OnChangeValueListener listener;
	
	Pattern pattern = Pattern.compile("(\\-)?(\\d)+(\\.[\\d]+)?");
	
	
	public MyButton(Context context, String varName, OnChangeValueListener listener) {
		super(context);
		this.context = context;
		this.varName = varName;
		this.setOnClickListener(new VarOnClick());
		this.setText(varName);
		this.state = StateButton.INIT;
		this.listener = listener;
	}
	
	public void setName(String name){
		this.varName = name;
	}
	
	public String getName(){
		return this.varName;
	}
	
	public double getValue(){
		return this.varValue;
	}
	
	public StateButton getState(){
		return this.state;
	}

	
	private class VarOnClick implements OnClickListener {

		private String m_Text = "";
		
		@Override
		public void onClick(View v) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("Enter a new value");

			// Set up the input
			final EditText input = new EditText(context);
			input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
			builder.setView(input);

			// Set up the buttons
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        m_Text = input.getText().toString();
			        Matcher m = pattern.matcher(m_Text);
		        	if(m.matches()){
		        	
			          	varValue = Double.parseDouble(m_Text);
			        	
			        	MyButton.this.setText(varName+"="+ m_Text);
			        	state = StateButton.READY;
			        	listener.onChange(MyButton.this);
		        	}
		        	else{		        
			        	Toast.makeText(context, "The value should be a real number", Toast.LENGTH_SHORT).show();
			        	state = StateButton.INVALID;
		        	}
			    }
			});
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        dialog.cancel();
			    }
			});

			builder.show();
		}
		
	}


	
}
