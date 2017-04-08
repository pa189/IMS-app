package com.pa189.ims;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.view.View;
import android.view.Window;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Password extends Activity {
ProgressDialog pDialog;
JSONParser jsonParser=new JSONParser();
String url="http://www.ims4maths.com/imsAppfolder/security_ques.php";
String NAME, PAID;
TextView tv2;
EditText et;
String username;
String sec_ques,sec_ans,newpass;
String res;
String password="http://www.ims4maths.com/imsAppfolder/pass_change.php";

int success;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_password);
		username=getIntent().getExtras().getString("username");
		
		
		
		tv2=(TextView)findViewById(R.id.et1);
		new CreateNewProduct().execute();
		et=(EditText)findViewById(R.id.et2);
		et.setHint("Security answer");
	
		Button b=(Button)findViewById(R.id.proceed);
		b.setText("PROCEED");
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
				Boolean isInternetPresent = cd.isConnectingToInternet();
				if(!isInternetPresent)
				{Toast abc=Toast.makeText(getApplicationContext(),"NO INTERNET CONNECTION!",Toast.LENGTH_LONG);
				abc.show();}
				else
				{String ans=et.getText().toString();
				if(ans.equalsIgnoreCase(sec_ans))
				{
					
					final EditText passet=(EditText)findViewById(R.id.et3);
					Button set=(Button)findViewById(R.id.set);
					set.setVisibility(View.VISIBLE);
					passet.setHint("New password");
					passet.setVisibility(View.VISIBLE);
					
					set.setText("CHANGE PASSWORD");
					set.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							newpass=passet.getText().toString();
							new NewPassword().execute();
						}
					});
				}
				else
				{
					Toast abc=Toast.makeText(getApplicationContext(),ans,Toast.LENGTH_LONG);
					abc.show();
				}
			}}
		});
	}
	
class CreateNewProduct extends AsyncTask<String,String,String> {
			
			protected void onPreExecute()
			{
				
				
				pDialog=new ProgressDialog(Password.this);
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
						
						json=jsonParser.makeHttpRequest(url, "POST", params);
						
					return json;
					
				}
				protected void onPostExecute(String response){
					pDialog.dismiss();
					try
					{JSONObject js=new JSONObject(response);
					success=js.getInt("success");
					if(success==1){
						JSONObject obj=js.getJSONObject("user");
						sec_ques=obj.getString("sec_ques");
						tv2.setText(sec_ques);
						sec_ans=obj.getString("sec_ans");
						NAME=obj.getString("name");
						PAID=obj.getString("paid");
						
					}
					else{
						String ans=js.getString("user");
						Toast abc=Toast.makeText(getApplicationContext(),ans,Toast.LENGTH_LONG);
					abc.show();
					finish();
				}
				}
				catch(JSONException e){
					e.printStackTrace();
				} 
				}
			}
class NewPassword extends AsyncTask<String,String,String> {
	
	protected void onPreExecute()
	{
		
		
		pDialog=new ProgressDialog(Password.this);
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
			params.add(new BasicNameValuePair("pass",newpass));
			
			String json;
				
				json=jsonParser.makeHttpRequest(password, "POST", params);
				
			return json;
			
		}
		protected void onPostExecute(String response){
			pDialog.dismiss();
			try
			{JSONObject js=new JSONObject(response);
			success=js.getInt("success");
			if(success==1){
				String ans=js.getString("message");
				Toast abc=Toast.makeText(getApplicationContext(),ans,Toast.LENGTH_LONG);
				abc.show();
				Intent i=new Intent(getApplicationContext(),Expandable.class);
				i.putExtra("username", username);
				i.putExtra("NAME", NAME);
				i.putExtra("PAID", PAID);
				
				startActivity(i);
			}
			else{
				String ans=js.getString("message");
				Toast abc=Toast.makeText(getApplicationContext(),ans,Toast.LENGTH_LONG);
			abc.show();
			
		}
		}
		catch(JSONException e){
			e.printStackTrace();
		} 
		}
	}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	finish();
}

	}

	


