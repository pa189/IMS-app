package com.pa189.ims;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class Initial extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//for full screen activity
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.initial);
		ProgressBar spinner;
		spinner = (ProgressBar)findViewById(R.id.progressBar1);
		spinner.setVisibility(View.VISIBLE);	
		Thread timer=new Thread(){
			public void run(){
				try{
					sleep(3000);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally{
					Intent i=new Intent(getApplicationContext(),Director.class);
					i.putExtra("act", "initial");
					startActivity(i);
				}
			}
	};
timer.start();
}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
}
