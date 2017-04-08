package com.pa189.ims;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

public class Login extends Activity implements View.OnClickListener{
EditText user,pass;
Button login,sign,forgot;
String NAME;
String PAID;
private ProgressDialog pDialog;
JSONParser jsonParser=new JSONParser();

String url="http://www.ims4maths.com/imsAppfolder/existing_user.php";

String json_string;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		//this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title); 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.login);
		
		login=(Button)findViewById(R.id.login);
		sign=(Button)findViewById(R.id.sign_up);
		forgot=(Button)findViewById(R.id.forgot);
		
		user=(EditText)findViewById(R.id.et1);
		pass=(EditText)findViewById(R.id.et2);
		user.setHint("Username");
		pass.setHint("Password");
		login.setText("LOGIN");
		sign.setText("SIGN UP");
		forgot.setText("FORGOT PASSWORD");
		forgot.setOnClickListener(this);
		login.setOnClickListener(this);
		sign.setOnClickListener(this);
	}
public void onClick(View v)
{
	switch(v.getId())
	{
	case R.id.login:
ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
Boolean isInternetPresent = cd.isConnectingToInternet();
if(!isInternetPresent)
{Toast abc=Toast.makeText(getApplicationContext(),"NO INTERNET CONNECTION!",Toast.LENGTH_LONG);
abc.show();}
else if(user.getText().toString().equals("admin")&&pass.getText().toString().equals("ims4maths"))
{
	Intent ij=new Intent(getApplicationContext(),Admin.class);
	startActivity(ij);
}
else
	new CreateNewProduct().execute();
		break;
	case R.id.sign_up:ConnectionDetector cd2 = new ConnectionDetector(getApplicationContext());
	Boolean isInternetPresent2 = cd2.isConnectingToInternet();
	if(!isInternetPresent2)
	{Toast abc=Toast.makeText(getApplicationContext(),"NO INTERNET CONNECTION!",Toast.LENGTH_LONG);
	abc.show();}
	else
		{Intent i=new Intent(getApplicationContext(),SignUp.class);
		startActivity(i);}
		break;
	case R.id.forgot:
		ConnectionDetector cd1 = new ConnectionDetector(getApplicationContext());
		Boolean isInternetPresent1 = cd1.isConnectingToInternet();
		if(!isInternetPresent1)
		{Toast abc=Toast.makeText(getApplicationContext(),"NO INTERNET CONNECTION!",Toast.LENGTH_LONG);
		abc.show();}
		else
		{String username=user.getText().toString();
		if(username.matches(""))
			{Toast.makeText(Login.this,"Please enter username"
      	           ,Toast.LENGTH_SHORT).show();}
		else
		{Intent j=new Intent(getApplicationContext(),Password.class);
			j.putExtra("username",username);
			startActivity(j);
		}}
		break;
	}
}
class CreateNewProduct extends AsyncTask<String,String,String> {
	
protected void onPreExecute()
{
	
	
	pDialog=new ProgressDialog(Login.this);
	pDialog.setMessage("Processing...");
	pDialog.setIndeterminate(false);
	pDialog.setCancelable(false);
	pDialog.show();
}
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		String username=user.getText().toString();
		String password=pass.getText().toString();
		
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username",username));
		params.add(new BasicNameValuePair("pass",password));
		
		String json;
			
			json=jsonParser.makeHttpRequest(url, "POST", params);
			
		return json;
		
	}
	protected void onPostExecute(String response){
		pDialog.dismiss();
		try
		{JSONObject js=new JSONObject(response);
		int success=js.getInt("success");
		if(success==1){
			JSONObject obj=js.getJSONObject("entry");
			String usern=obj.getString("username");
			NAME=obj.getString("name");
			PAID=obj.getString("paid");
			
			Intent i=new Intent(getApplicationContext(),Expandable.class);
			i.putExtra("username", usern);
			i.putExtra("NAME", NAME);
			i.putExtra("PAID", PAID);
			
			startActivity(i);
			
		}
		else{
			String ans=js.getString("entry");
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
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	pass.setText("");
}

}
