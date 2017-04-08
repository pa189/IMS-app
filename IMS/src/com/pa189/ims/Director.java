package com.pa189.ims;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class Director extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_director);
		String act=getIntent().getExtras().getString("act");
		TextView tv1=(TextView)findViewById(R.id.about);
		TextView tv2=(TextView)findViewById(R.id.director);
		TextView tv3=(TextView)findViewById(R.id.info);
		TextView tv4=(TextView)findViewById(R.id.desig);
		tv1.setText("About Us");
		tv2.setText("Director's Message");
		tv3.setText("With the emerging opportunities in the field of science and Tech, the importance of mathematics has become indispensable. Now-a-days mathematics is in use some way or the other in almost all dimensions of the human creativity. The increasing popularity of the said subject and its application has changed the system in manifold ways. Notwithstanding these positive aspects one thing that hampers the students which we call lack of guidance. The aspirants are the worst suffers of these short-comings.\nIMS approaches towards a near-proximity to an effective guidance.");
		tv4.setText("K. Venkanna\nDirector, IMS Institute of Mathematical Sciences\nNew Delhi - 110009");
		Button b=(Button)findViewById(R.id.next);
		if(act.equals("initial"))
		{
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Login.class);
				startActivity(i);
			}
		});
	}
		else
			b.setVisibility(View.INVISIBLE);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	
    
    
    }    

