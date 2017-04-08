package com.pa189.ims;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.View;
import android.view.Window;

import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

public class MainAct extends Activity {
TextView sendto;
TextView emailadd;
TextView subhead;
TextView msghead;
EditText sub;
EditText msg;
String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
		setContentView(R.layout.activity_main_act);
		name=getIntent().getExtras().getString("username");
		sendto=(TextView)findViewById(R.id.sendto);
		emailadd=(TextView)findViewById(R.id.email);
		subhead=(TextView)findViewById(R.id.subhead);
		msghead=(TextView)findViewById(R.id.msghead);
		sub=(EditText)findViewById(R.id.sub);
		msg=(EditText)findViewById(R.id.msg);
		sendto.setText("SEND EMAIL TO");
		emailadd.setText("ims4maths@gmail.com");
		subhead.setText("SUBJECT");
		msghead.setText("YOUR QUERY");
		Button send=(Button)findViewById(R.id.send);
		send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String id="ims4maths@gmail.com";
				String arr[]={id};
				String subject=sub.getText().toString();
				String body="Query from user "+name+"\n"+msg.getText().toString();
				Intent abc=new Intent(android.content.Intent.ACTION_SEND);
				abc.putExtra(android.content.Intent.EXTRA_EMAIL, arr);
				abc.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
				abc.setType("plain/text");
				abc.putExtra(android.content.Intent.EXTRA_TEXT,body);
				startActivity(abc);
			}
		});
	}

	

}
