package es.rczone.reckoner.activitys;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import es.rczone.reckoner.R;
import es.rczone.reckoner.dao.FormulaDAO;
import es.rczone.reckoner.tools.Tools;


public class TestActivity extends Activity {

	ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_image);
		
		
		load();		
		
	}
	
	public void load(){
		
		final AsyncTask<Void, Void, Void> t = new AsyncTask<Void, Void, Void>() {
        	
        	@Override
        	protected void onPreExecute() {
        		
        		progressDialog = new ProgressDialog(TestActivity.this);
        		progressDialog.setMessage("Loading equations....");
        		progressDialog.setIndeterminate(false);
        		progressDialog.setCancelable(false);
        		progressDialog.show();
        	}

    	   @Override
    	   protected Void doInBackground(Void... params) {
    		   
    		 //http://latex.codecogs.com/gif.latex?3*x/(4+2)
    		   
    		   
    		   File f = Tools.getImage("/sdcard/app/tmp/","f3.gif", "http://latex.codecogs.com/gif.latex?%5Chuge%20(3*x^3)/(4+2)+2+1+1+2+3+x-y");
    			final Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
    			
    			
    			runOnUiThread(new Runnable() {
    			     public void run() {

    			    	 ImageView image = (ImageView)findViewById(R.id.imageView1);
    			    	 image.setImageBitmap(bitmap);
    			    }
    			});
    			
    			
    			
				
    			return null;
    	   }

    	   @Override
    	   protected void onPostExecute(Void result) {
    		   progressDialog.dismiss();// ocultamos progess dialog.
    		   //t = null;
    	   }
    		
    };
    	t.execute();	}
	
	
}
