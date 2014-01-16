package es.rczone.reckoner.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import es.rczone.reckoner.R;



public class SplashActivity extends Activity {

	// used to know if the back button was pressed in the splash screen activity and avoid opening the next activity
    private boolean mIsBackButtonPressed;
    private static final int SPLASH_DURATION = 3000; // 3 seconds
 
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       
 
        setContentView(R.layout.activity_splash);
 
        Handler handler = new Handler();
 
        // run a thread after 2 seconds to start the home screen
        handler.postDelayed(new Runnable() {
 
            @Override
            public void run() {
               
               
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

}
