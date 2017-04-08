package com.pa189.ims;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;

import android.app.ListActivity;
import android.content.Intent;

import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Category extends ListActivity {
	ArrayList<String> classes;
	String NAME,PAID;
	String show_new_ques="http://www.ims4maths.com/imsAppfolder/categories.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		classes=new ArrayList<String>();
		NAME=getIntent().getExtras().getString("NAME");
		PAID=getIntent().getExtras().getString("PAID");
		new GetCategories().execute();
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent i=new Intent(getApplicationContext(),CatQues.class);
		i.putExtra("username", getIntent().getExtras().getString("username"));
		i.putExtra("NAME", NAME);
		 i.putExtra("PAID", PAID);
		i.putExtra("tag", classes.get(position));
		startActivity(i);
		
	}
class GetCategories extends AsyncTask<String,String,String> {
    	
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
					JSONArray success=new JSONArray(response);
				
				for(int i=0;i<success.length();++i)
				{JSONObject obj=success.getJSONObject(i);
					String tag=obj.getString("tag");
					classes.add(tag);
				}
				setListAdapter(new ArrayAdapter<String>(Category.this, android.R.layout.simple_list_item_1, classes));
				}
    		catch(JSONException e){
    			e.printStackTrace();
    		} 
    		}
}

}
