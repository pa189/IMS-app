package com.pa189.ims;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Admin extends Activity{
	List<item> quest;
	private ProgressDialog pDialog;
ListView lv;
list la;
EditText newuser;
Button done;
	JSONParser jsonParser=new JSONParser();
	int no=0;
	String url2="http://www.ims4maths.com/imsAppfolder/nadminques.php";
	String url="http://www.ims4maths.com/imsAppfolder/npaid.php";
	String ans;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_admin);
		Button tv=(Button)findViewById(R.id.newq);
		tv.setText("Click to add new question!");
		lv=(ListView)findViewById(R.id.listView);
		 quest=new ArrayList<item>();
     	new CreateNewProduct().execute();
     	 
	
         lv.setOnItemClickListener(new OnItemClickListener()
         {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent i=new Intent(getApplicationContext(),Adminsub.class);
					i.putExtra("ques",quest.get(arg2).getQues());
					i.putExtra("tag", quest.get(arg2).getTag());
					i.putExtra("date", quest.get(arg2).getdate());
					i.putExtra("imgques", quest.get(arg2).getImgQues());
					i.putExtra("ans", quest.get(arg2).getAns());
					i.putExtra("imgans", quest.get(arg2).getImgAns());
					i.putExtra("ques_no", quest.get(arg2).getQuesno());
					i.putExtra("no_sub", quest.get(arg2).getNoSub());
					
				String x="sub";
				String y="user";
				String z="right";
				String u="imgsub";
				int j;
			for(j=0;j<(quest.get(arg2).getNoSub());++j)
			{
				i.putExtra(x+j, quest.get(arg2).getChildren().get(j).getSub());
				i.putExtra(y+j, quest.get(arg2).getChildren().get(j).getUser());
				i.putExtra(z+j, quest.get(arg2).getChildren().get(j).getRight());
				i.putExtra(u+j, quest.get(arg2).getChildren().get(j).getImgsub());
			}
			startActivity(i);
				}
         });
                  Button paid=(Button)findViewById(R.id.paid);
         paid.setText("New payment");
         final LinearLayout ll=(LinearLayout)findViewById(R.id.payment);
         paid.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ll.setVisibility(View.VISIBLE);
			}
		});
         newuser=(EditText)findViewById(R.id.newuser);
         done=(Button)findViewById(R.id.done);
         newuser.setHint("Enter username");
         done.setText("Paid");
         done.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Payment().execute();
				ll.setVisibility(View.GONE);
			}
		});
         tv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Addques.class);
				i.putExtra("quesno", no+1);
				i.putExtra("ques", "");
				i.putExtra("tag", "");
				i.putExtra("date", "");
				i.putExtra("type", "submit");
				i.putExtra("quesimg", "null");
				startActivity(i);
			}
		});
	}

	

class CreateNewProduct extends AsyncTask<String,String,String> {
	
protected void onPreExecute()
{
	pDialog=new ProgressDialog(Admin.this);
	pDialog.setMessage("Processing...");
	pDialog.setIndeterminate(false);
	pDialog.setCancelable(false);
	pDialog.show();
}
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		try{
		URL url=new URL(url2);
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
		pDialog.dismiss();
		
		try
		{
			JSONObject js=new JSONObject(response);
		JSONArray success=js.getJSONArray("users");
		for(int i=success.length()-1;i>=0;--i)
		{JSONObject obj=success.getJSONObject(i);
			item it=new item();
			++no;
			it.setQues(obj.getString("question"));
			it.setTag(obj.getString("tag"));
			it.setImgQues(obj.getString("image"));
			ArrayList<Child> child=new ArrayList<Child>();
			it.setAns(obj.getString("answer"));
			it.setImgAns(obj.getString("imgans"));
			String x=obj.getString("s_no");
			it.setQuesno(x);
			it.setDate(obj.getString("date"));
			int count=0;
			if(!obj.getString("username").equals("null"))
			{while(i>=0)
			{
				JSONObject obx=success.getJSONObject(i);
				String y=obx.getString("s_no");
				if(y.equals(x))
				{++count;

				Child ch=new Child();
				ch.setSub(obx.getString("submission"));
				
				ch.setImgsub(obx.getString("imgsub"));
				
				ch.setRight(obx.getString("status"));
				ch.setUser(obx.getString("username"));
				child.add(ch);
					--i;
				}
				else
					{++i;
					break;
					}
			}}
			//new GetSubmissions().execute();
			else
			{
			count=0;
				
			}
			it.setNoSub(count);
			it.setChildren(child);
			quest.add(it);
		}
		 la=new list(Admin.this,quest);
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
	pDialog=new ProgressDialog(Admin.this);
	pDialog.setMessage("Processing...");
	pDialog.setIndeterminate(false);
	pDialog.setCancelable(false);
	pDialog.show();
}
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		try{
		URL url=new URL(url2);
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
		pDialog.dismiss();
		
		try
		{
			quest=new ArrayList<item>();
			JSONObject js=new JSONObject(response);
		JSONArray success=js.getJSONArray("users");
		for(int i=success.length()-1;i>=0;--i)
		{JSONObject obj=success.getJSONObject(i);
			item it=new item();
			++no;
			it.setQues(obj.getString("question"));
			it.setTag(obj.getString("tag"));
			it.setImgQues(obj.getString("image"));
			ArrayList<Child> child=new ArrayList<Child>();
			it.setAns(obj.getString("answer"));
			it.setImgAns(obj.getString("imgans"));
			String x=obj.getString("s_no");
			it.setQuesno(x);
			it.setDate(obj.getString("date"));
			int count=0;
			if(!obj.getString("username").equals("null"))
			{while(i>=0)
			{
				JSONObject obx=success.getJSONObject(i);
				String y=obx.getString("s_no");
				if(y.equals(x))
				{++count;

				Child ch=new Child();
				ch.setSub(obx.getString("submission"));
				
				ch.setImgsub(obx.getString("imgsub"));
				
				ch.setRight(obx.getString("status"));
				ch.setUser(obx.getString("username"));
				child.add(ch);
					--i;
				}
				else
					{++i;
					break;
					}
			}}
			//new GetSubmissions().execute();
			else
			{
			count=0;
				
			}
			it.setNoSub(count);
			it.setChildren(child);
			quest.add(it);
		}
		la=new list(Admin.this,quest);
	       lv.setAdapter(la);
	}
	catch(JSONException e){
		e.printStackTrace();
	} 
	}
}


/*@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	Intent i=new Intent(getApplicationContext(),Expandable.class);
	i.putExtra("username", username);
	startActivity(i);
	finish();
}*/
class Payment extends AsyncTask<String,String,String> {
	
protected void onPreExecute()
{
	
	
	pDialog=new ProgressDialog(Admin.this);
	pDialog.setMessage("Processing...");
	pDialog.setIndeterminate(false);
	pDialog.setCancelable(false);
	pDialog.show();
}
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		String username=newuser.getText().toString();
		String paid="1";
		
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username",username));
		params.add(new BasicNameValuePair("paid",paid));
		
		String json;
			
			json=jsonParser.makeHttpRequest(url, "POST", params);
			
		return json;
		
	}
	protected void onPostExecute(String response){
		pDialog.dismiss();
		Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
	}
}


@Override
protected void onRestart() {
	// TODO Auto-generated method stub
	super.onRestart();
	new CreateOldProduct().execute();
}

}
