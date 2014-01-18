package es.rczone.reckoner.math.activitys;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import es.rczone.reckoner.R;
import es.rczone.reckoner.math.ReckonerApp;
import es.rczone.reckoner.math.controllers.AddFormulasController;
import es.rczone.reckoner.math.dao.FormulaDAO;
import es.rczone.reckoner.math.model.Formula;
import es.rczone.reckoner.math.tools.Tools;



public class SplashActivity extends Activity {

	// used to know if the back button was pressed in the splash screen activity and avoid opening the next activity
    private boolean mIsBackButtonPressed;
    private static final int SPLASH_DURATION = 1500; // 3 seconds
 
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       
 
        setContentView(R.layout.activity_splash);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
 
        Handler handler = new Handler();
 
        // run a thread after 2 seconds to start the home screen
        handler.postDelayed(new Runnable() {
 
            @Override
            public void run() {
               
            	new CheckImages().start();
                if (!mIsBackButtonPressed) {
                	
	                Intent intent = new Intent(SplashActivity.this, MainMenuActivity.class);
	                SplashActivity.this.startActivity(intent);
		            
                }
                //make sure we close the splash screen so the user won't come back when it presses back key
                finish();
            }
 
        }, SPLASH_DURATION); // time in milliseconds (1 second = 1000 milliseconds) until the run() method will be called
 
    }
 
    @Override
    public void onBackPressed() {
 
        // set the flag to true so the next activity won't start up
    	// anbd avoid interruput the splash activity
        mIsBackButtonPressed = true;
        super.onBackPressed();
 
    }
    
    private class CheckImages extends Thread{
    	@Override
    	public void run(){
    		ArrayList<Formula> lista = new FormulaDAO().getAllFormulas();
    		ContextWrapper cw = new ContextWrapper(SplashActivity.this);
    		File directory = cw.getDir("media", Context.MODE_PRIVATE);
    		File img;
    		for(Formula f : lista){
    			img = new File(directory.getAbsolutePath() +"/" + f.getName()+".gif");
        		// solo se descargan nuevas imagenes
        		if (!img.exists()) {
        			try {
        				
        				URL imageUrl = new URL(AddFormulasController.URL_LAtEX+f.getFunctionFormula());
        				InputStream in = imageUrl.openStream();
        				OutputStream out = new BufferedOutputStream(
        						new FileOutputStream(img));

        				for (int b; (b = in.read()) != -1;) {
        					out.write(b);
        				}
        				out.close();
        				in.close();
        			} catch (MalformedURLException e) {
        				Log.e("Buscador", "Malformed URLException capturado : " + e);
        				img = null;
        			} catch (IOException e) {
        				Log.e("Buscador", "IOException capturado : " + e);
        				img = null;
        			}
        		
        		}

    		}
    		
    	}
    }
}
