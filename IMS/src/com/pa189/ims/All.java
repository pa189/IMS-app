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

import android.view.View;
import android.view.Window;


import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class All extends Activity{
	List<item> quest;
	private ProgressDialog pDialog;
ListView lv;
list la;
	JSONParser jsonParser=new JSONParser();
	
	String url2="http://www.ims4maths.com/imsAppfolder/show_user_sub.php";
	String username;
	String NAME,PAID;
	String ans;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_all);
	
		username=getIntent().getExtras().getString("username");
		NAME=getIntent().getExtras().getString("NAME");
		PAID=getIntent().getExtras().getString("PAID");
		TextView tv=(TextView)findViewById(R.id.user);
		tv.setText(username);
		lv=(ListView)findViewById(R.id.listView);
		 quest=new ArrayList<item>();
     	new CreateNewProduct().execute();
     	 
	
         lv.setOnItemClickListener(new OnItemClickListener()
         {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent i=new Intent(getApplicationContext(),SubAct.class);
					i.putExtra("ques",quest.get(arg2).getQues());
					i.putExtra("tag", quest.get(arg2).getTag());
					i.putExtra("date", quest.get(arg2).getdate());
					i.putExtra("imgques", quest.get(arg2).getImgQues());
					i.putExtra("ans", quest.get(arg2).getAns());
					i.putExtra("NAME", NAME);
					 i.putExtra("PAID", PAID);
					i.putExtra("username", username);
					i.putExtra("imgans", quest.get(arg2).getImgAns());
				i.putExtra("ques_no", quest.get(arg2).getQuesno());
					i.putExtra("no_sub", 1);
				String x="sub";
				String y="user";
				String z="right";
				String u="imgsub";
				int j=0;
			
				i.putExtra(x+j, quest.get(arg2).getChildren().get(j).getSub());
				i.putExtra(y+j, quest.get(arg2).getChildren().get(j).getUser());
				i.putExtra(z+j, quest.get(arg2).getChildren().get(j).getRight());
				i.putExtra(u+j, quest.get(arg2).getChildren().get(j).getImgsub());
			
			startActivity(i);
				}
         });
	}

	

class CreateNewProduct extends AsyncTask<String,String,String> {
	
protected void onPreExecute()
{
	pDialog=new ProgressDialog(All.this);
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
			
			json=jsonParser.makeHttpRequest(url2, "POST", params);
			
		return json;
		
	}
	protected void onPostExecute(String response){
		pDialog.dismiss();
		try
		{
			JSONObject js=new JSONObject(response);
		JSONArray success=js.getJSONArray("users");
		for(int i=success.length()-1;i>=0;--i)
		{JSONObject obj=success.getJSONObject(i);
			item par=new item();
			par.setQues(obj.getString("question"));
			par.setDate(obj.getString("date"));
			par.setTag(obj.getString("tag"));
			Child ch=new Child();
			ch.setSub(obj.getString("submission"));
			par.setImgQues(obj.getString("image"));
			ch.setRight(obj.getString("status"));
			ch.setUser(" ");
			par.setImgAns(obj.getString("imgans"));
			par.setAns(obj.getString("answer"));
			ch.setImgsub(obj.getString("imgsub"));
			par.setQuesno(obj.getString("s_no"));
			ArrayList<Child> child=new ArrayList<Child>();
			child.add(ch);
			par.setChildren(child);
			quest.add(par);
		}
		la=new list(All.this,quest);
        lv.setAdapter(la);
        
	}
	catch(JSONException e){
		e.printStackTrace();
	} 
	}
}

class CreateOldProduct extends AsyncTask<String,String,String> {
	
protected void onPreExecute()
{
	pDialog=new ProgressDialog(All.this);
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
			
			json=jsonParser.makeHttpRequest(url2, "POST", params);
			
		return json;
		
	}
	protected void onPostExecute(String response){
		pDialog.dismiss();
		try
		{
			quest=new ArrayList<item>();
			JSONObject js=new JSONObject(response);
		JSONArray success=js.getJSONArray("users");
		for(int i=success.length()-1;i>=0;--i)
		{JSONObject obj=success.getJSONObject(i);
			item par=new item();
			par.setQues(obj.getString("question"));
			par.setDate(obj.getString("date"));
			par.setTag(obj.getString("tag"));
			Child ch=new Child();
			ch.setSub(obj.getString("submission"));
			par.setImgQues(obj.getString("image"));
			ch.setRight(obj.getString("status"));
			ch.setUser(" ");
			par.setImgAns(obj.getString("imgans"));
			par.setAns(obj.getString("answer"));
			ch.setImgsub(obj.getString("imgsub"));
			par.setQuesno(obj.getString("s_no"));
			ArrayList<Child> child=new ArrayList<Child>();
			child.add(ch);
			par.setChildren(child);
			quest.add(par);
		}
		la.notifyDataSetChanged();
        
	}
	catch(JSONException e){
		e.printStackTrace();
	} 
	}
}

@Override
protected void onRestart() {
	// TODO Auto-generated method stub
	super.onRestart();
	new CreateOldProduct().execute();
}


}
