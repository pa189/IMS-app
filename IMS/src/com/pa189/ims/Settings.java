package com.pa189.ims;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends Activity {
String username, NAME,ADD,PHONE,EMAIL,SEC_ANS,SEC_QUES,PASS;
EditText euser,ename,eadd,ephone,eemail,eans;
private ProgressDialog pDialog;
JSONParser jsonParser=new JSONParser();

String url="http://www.ims4maths.com/imsAppfolder/editinfo.php";
String info="http://www.ims4maths.com/imsAppfolder/info.php";
TextView tuser,tname,tadd,tphone,temail,tans,tques,queshead;
TextView userhead;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_settings);
		final ScrollView sc=(ScrollView)findViewById(R.id.sc);
		username=getIntent().getExtras().getString("username");
		
		euser=(EditText)findViewById(R.id.euser);
		ename=(EditText)findViewById(R.id.ename);
		eadd=(EditText)findViewById(R.id.eadd);
		ephone=(EditText)findViewById(R.id.ephone);
		eemail=(EditText)findViewById(R.id.eemail);
		eans=(EditText)findViewById(R.id.eans);
		TextView my=(TextView)findViewById(R.id.my);
		my.setText("MY PERSONAL INFORMATION");
		userhead=(TextView)findViewById(R.id.userhead);
		userhead.setText("Username");
		tuser=(TextView)findViewById(R.id.tuser);
		tuser.setText(username);
		TextView namehead=(TextView)findViewById(R.id.namehead);
		namehead.setText("Name");
		tname=(TextView)findViewById(R.id.tname);
		
		TextView addhead=(TextView)findViewById(R.id.addhead);
		addhead.setText("ADDRESS");
		tadd=(TextView)findViewById(R.id.tadd);
		
		TextView phonehead=(TextView)findViewById(R.id.phonehead);
		phonehead.setText("Contact Number");
		tphone=(TextView)findViewById(R.id.tphone);
		
		TextView emailhead=(TextView)findViewById(R.id.emailhead);
		emailhead.setText("Email Address");
		temail=(TextView)findViewById(R.id.temail);
		
		queshead=(TextView)findViewById(R.id.queshead);
		queshead.setText("Security Question");
		tques=(TextView)findViewById(R.id.tques);
		
		TextView anshead=(TextView)findViewById(R.id.anshead);
		anshead.setText("Security Answer");
		tans=(TextView)findViewById(R.id.tans);
		
		new CreateOldProduct().execute();
		
		
		final Button edit=(Button)findViewById(R.id.edit);
		edit.setText("Edit Information");
		final Button submit=(Button)findViewById(R.id.submit);
		submit.setText("Submit");
		edit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				userhead.setText("Password");
				euser.setText(PASS);
				euser.setVisibility(View.VISIBLE);
				ename.setVisibility(View.VISIBLE);
				eadd.setVisibility(View.VISIBLE);
				ephone.setVisibility(View.VISIBLE);
				eemail.setVisibility(View.VISIBLE);
				eans.setVisibility(View.VISIBLE);
				ename.setText(NAME);
				eadd.setText(ADD);
				ephone.setText(PHONE);
				eemail.setText(EMAIL);
				eans.setText(SEC_ANS);
				tuser.setVisibility(View.GONE);
				tname.setVisibility(View.GONE);
				tadd.setVisibility(View.GONE);
				tphone.setVisibility(View.GONE);
				temail.setVisibility(View.GONE);
				tans.setVisibility(View.GONE);
				edit.setVisibility(View.GONE);
				submit.setVisibility(View.VISIBLE);
				sc.fullScroll(ScrollView.FOCUS_UP);
			}
		});
		
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new CreateNewProduct().execute();
				
			}
		});
	}

	class CreateNewProduct extends AsyncTask<String,String,String> {
		
		protected void onPreExecute()
		{
			
			
			pDialog=new ProgressDialog(Settings.this);
			pDialog.setMessage("Processing...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
			@Override
			protected String doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				
				
				
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username",username));
				params.add(new BasicNameValuePair("pass",euser.getText().toString()));
				params.add(new BasicNameValuePair("name",ename.getText().toString()));
				params.add(new BasicNameValuePair("address",eadd.getText().toString()));
				params.add(new BasicNameValuePair("phone_no",ephone.getText().toString()));
				params.add(new BasicNameValuePair("email",eemail.getText().toString()));
				params.add(new BasicNameValuePair("sec_ans",eans.getText().toString()));
				
				String json;
					
					json=jsonParser.makeHttpRequest(url, "POST", params);
					
				return json;
				
			}
			protected void onPostExecute(String response){
				pDialog.dismiss();
				
					Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
					finish();
				
		}
	}
	class CreateOldProduct extends AsyncTask<String,String,String> {
		
		protected void onPreExecute()
		{
			
			
			pDialog=new ProgressDialog(Settings.this);
			pDialog.setMessage("Processing...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
			@Override
			protected String doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				
				
				
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username",username));
				
				
				String json;
					
					json=jsonParser.makeHttpRequest(info, "POST", params);
					
				return json;
				
			}
			protected void onPostExecute(String response){
				pDialog.dismiss();
				try
				{
					JSONObject js=new JSONObject(response);
					JSONArray success=js.getJSONArray("users");
					
					JSONObject obj=success.getJSONObject(0);
						
					
					NAME=obj.getString("name");
					
					ADD=obj.getString("address");
					PHONE=obj.getString("phone_no");
					EMAIL=obj.getString("email");
					SEC_ANS=obj.getString("sec_ans");
					SEC_QUES=obj.getString("sec_ques");
					PASS=obj.getString("pass");
					
					tname.setText(NAME);
					
					tadd.setText(ADD);
					
					tphone.setText(PHONE);
					
					temail.setText(EMAIL);
					
					tques.setText(SEC_QUES);
					
					tans.setText(SEC_ANS);
			
			}
			catch(JSONException e){
				e.printStackTrace();
			} 
			}
		}
}
