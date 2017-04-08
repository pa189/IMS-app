package com.pa189.ims;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;

import android.widget.TableRow.LayoutParams;
import android.app.Activity;
import android.graphics.Color;

import android.util.TypedValue;

import android.view.Window;


import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Leaderboard extends Activity {
	String show_new_ques="http://www.ims4maths.com/imsAppfolder/leaderboard.php";
	TableLayout tl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	    	
		setContentView(R.layout.activity_leaderboard);
		String name=getIntent().getExtras().getString("username");
		TextView user=(TextView)findViewById(R.id.user);
		tl=(TableLayout)findViewById(R.id.table);
	
		TableRow tr = new TableRow(getApplicationContext());
		  LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		  tr.setLayoutParams(lp);
		  TextView tvLeft = new TextView(getApplicationContext());
		  tvLeft.setLayoutParams(lp);
		
		  tvLeft.setTextColor(Color.WHITE);
		  tvLeft.setText("RANK     ");
		  
		  tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		  TextView tvCenter = new TextView(getApplicationContext());
		  tvCenter.setLayoutParams(lp);
		  tvCenter.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		  
		  tvCenter.setTextColor(Color.WHITE);
		  tvCenter.setText("USERNAME                                ");
		  TextView tvRight = new TextView(getApplicationContext());
		  tvRight.setLayoutParams(lp);
		  tvRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		 
		  tvRight.setTextColor(Color.WHITE);
		  tvRight.setText("SUCCESSFUL\nSUBMISSIONS");

		  tr.addView(tvLeft);
		  tr.addView(tvCenter);
		  tr.addView(tvRight);

		  tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));	
		user.setText(name);
		new GetNewQuestions().execute();
	}
class GetNewQuestions extends AsyncTask<String,String,String> {
    	
    	protected void onPreExecute()
    	{
    	}
    		@Override
    		protected String doInBackground(String... arg0) {
    			// TODO Auto-generated method stub
    			
    			try{
    			URL url=new URL(show_new_ques);
					HttpURLConnection httpUrlConnection=(HttpURLConnection)url.openConnection();
					InputStream is=httpUrlConnection.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(is));
					StringBuilder sb=new StringBuilder();
					String line=null;
					while((line=reader.readLine())!=null){
						sb.append(line+"\n");
					}
					reader.close();
					is.close();
					httpUrlConnection.disconnect();
					return sb.toString().trim();
				} catch(MalformedURLException e){
					e.printStackTrace();
				}
    			catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    			
    			return null;
    			
    			
    		}
    		protected void onPostExecute(String response){
    			
    			
    			try
    			{
    			
    				JSONObject js=new JSONObject(response);
    				
    			JSONArray success=js.getJSONArray("users");
    			
    			for(int i=0;i<success.length();++i)
    			{JSONObject obj=success.getJSONObject(i);
    			if(obj.getString("count(status)").equals("0"))
    			break;
    			TableRow tr = new TableRow(getApplicationContext());
    			  LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    			  tr.setLayoutParams(lp);
    			  TextView tvLeft = new TextView(getApplicationContext());
    			  tvLeft.setLayoutParams(lp);
    			
    			  tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    			  tvLeft.setTextColor(Color.WHITE);
    			  int y=i+1;
    			  tvLeft.setText(y+".");
    			  TextView tvCenter = new TextView(getApplicationContext());
    			  tvCenter.setLayoutParams(lp);
    			  tvCenter.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    			  
    			  tvCenter.setTextColor(Color.WHITE);
    			  tvCenter.setText(obj.getString("username"));
    			  TextView tvRight = new TextView(getApplicationContext());
    			  tvRight.setLayoutParams(lp);
    			 
    			  tvRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    			  tvRight.setTextColor(Color.WHITE);
    			  tvRight.setText("       "+obj.getString("count(status)"));

    			  tr.addView(tvLeft);
    			  tr.addView(tvCenter);
    			  tr.addView(tvRight);

    			  tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));	
    		}}
    		catch(JSONException e){
    			e.printStackTrace();
    		} 
    		}
}
	
}
