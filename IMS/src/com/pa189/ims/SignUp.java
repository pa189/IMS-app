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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUp extends Activity{
private ProgressDialog pDialog;
JSONParser jsonParser=new JSONParser();
EditText user,pass,name,add,phone,email,ans;
String username,password,NAME,ADD,ph,em,ANS;
ArrayList<String> ques = new ArrayList<String>();
String url="http://www.ims4maths.com/imsAppfolder/new_user.php";

String sec_ques;

private static final String tag_success="success";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.sign_up);
	
		
		user=(EditText)findViewById(R.id.et1);
		pass=(EditText)findViewById(R.id.et2);
		name=(EditText)findViewById(R.id.et3);
		add=(EditText)findViewById(R.id.et4);
		phone=(EditText)findViewById(R.id.et5);
		email=(EditText)findViewById(R.id.et6);
		ans=(EditText)findViewById(R.id.et7);
		
		Button sign=(Button)findViewById(R.id.sign);
		
		sign.setText("SIGN UP");
		user.setHint("Username");
		pass.setHint("Password");
		name.setHint("Name");
		add.setHint("Address");
		phone.setHint("Contact Number");
		email.setHint("Email Id");
		ans.setHint("Security Answer");
		
		
	
		Spinner spinner = (Spinner) findViewById(R.id.spinner1); 
	    
		  ques.add("What’s your first teacher’s name?");
		  ques.add("What is your father’s middle name?");
		  ques.add("What is your city of birth?");
		  ques.add("What was your first pet’s name?");
		 
		 
		  // Create the ArrayAdapter
		  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SignUp.this
		            ,android.R.layout.simple_spinner_item,ques);
		  
		                 // Set the Adapter
		  spinner.setAdapter(arrayAdapter);
		  
		  // Set the ClickListener for Spinner
		  spinner.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() { 

		              public void onItemSelected(AdapterView<?> adapterView, 
		             View view, int i, long l) { 
		             // TODO Auto-generated method stub
		          sec_ques=ques.get(i);
		             
		               }

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}
		               
		  });
		            	 
		sign.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
				Boolean isInternetPresent = cd.isConnectingToInternet();
				if(!isInternetPresent)
				{Toast abc=Toast.makeText(getApplicationContext(),"NO INTERNET CONNECTION!",Toast.LENGTH_LONG);
				abc.show();}
				else
				{username=user.getText().toString();
				password=pass.getText().toString();
				NAME=name.getText().toString();
				ADD=add.getText().toString();
				ph=phone.getText().toString();
				em=email.getText().toString();
				ANS=ans.getText().toString();
		if(username.matches("")||password.matches("")||NAME.matches("")||ADD.matches("")||ph.matches("")||em.matches("")||ANS.matches(""))
				{Toast.makeText(SignUp.this,"Please fill all the entries"
         	           ,Toast.LENGTH_SHORT).show();}
		else
				new CreateNewProduct().execute();
				
			}}
		});
	}
class CreateNewProduct extends AsyncTask<String,String,String> {
	
protected void onPreExecute()
{
	
	
	pDialog=new ProgressDialog(SignUp.this);
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
		params.add(new BasicNameValuePair("pass",password));
		params.add(new BasicNameValuePair("name",NAME));
		params.add(new BasicNameValuePair("address",ADD));
		params.add(new BasicNameValuePair("phone_no",ph));
		params.add(new BasicNameValuePair("email",em));
		params.add(new BasicNameValuePair("sec_ans",ANS));
		params.add(new BasicNameValuePair("sec_ques",sec_ques));
		
		String json;
			
			json=jsonParser.makeHttpRequest(url, "POST", params);
			
		return json;
		
	}
	protected void onPostExecute(String response){
		pDialog.dismiss();
		try
		{JSONObject js=new JSONObject(response);
		int success=js.getInt(tag_success);
		if(success==1){
			Intent i=new Intent(getApplicationContext(),Expandable.class);
			i.putExtra("username", user.getText().toString());
			i.putExtra("NAME", name.getText().toString());
			i.putExtra("PAID","0");
			
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
