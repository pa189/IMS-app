package com.pa189.ims;

import android.os.Bundle;
import android.app.Activity;

import android.view.Window;

import android.widget.TextView;

public class NewPass extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
		setContentView(R.layout.activity_new_pass);
		TextView tv1=(TextView)findViewById(R.id.our);
		
		TextView office1=(TextView)findViewById(R.id.office1);
		TextView add1=(TextView)findViewById(R.id.add1);
		TextView office2=(TextView)findViewById(R.id.office2);
		TextView add2=(TextView)findViewById(R.id.add2);
		TextView office3=(TextView)findViewById(R.id.office3);
		TextView add3=(TextView)findViewById(R.id.add3);
		TextView emailhead=(TextView)findViewById(R.id.emailhead);
		TextView email=(TextView)findViewById(R.id.email);
		TextView sitehead=(TextView)findViewById(R.id.sitehead);
		TextView site=(TextView)findViewById(R.id.site);
		tv1.setText("Our Contact");
		office1.setText("Head Office:");
		add1.setText("25/8, Old Rajender Nagar Market, Delhi-110060\nPh.No.:01145629987, 09999197625");
		office2.setText("Branch Office: Delhi");
		add2.setText("105-106, Top Floor, Mukherjee Tower, Mukherjee Nagar, Delhi-110009");
		office3.setText("Branch Office: Hyderabad");
		add3.setText("H.No-1-10-237, 2nd Floor, Room No.202,\nR.K'S-Kacham's Blue Sapphire Ashok Nagar, Hyderabad-20\nPh. No.: 096-52-6611-52");
		emailhead.setText("Email Address:");
		email.setText("ims4maths@gmail.com");
		sitehead.setText("Official Website:");
		site.setText("http://www.ims4maths.com");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	

}
