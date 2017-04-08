package com.pa189.ims;

//import java.io.BufferedInputStream;
import java.io.BufferedReader;

//import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.NameValuePair; 
//import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import android.app.Activity;
import android.app.ProgressDialog;


//import android.app.ProgressDialog;
import android.content.Intent;

//import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

//import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.AdapterView.OnItemClickListener;

//import android.widget.ExpandableListView.OnChildClickListener;
//import android.widget.ExpandableListView.OnGroupClickListener;
//import android.widget.ExpandableListView.OnGroupCollapseListener;
//import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TabHost.TabSpec;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
 
public class Expandable extends Activity {
	
	JSONParser jsonParser=new JSONParser();
	String ans;
	List<item> questions;
	ListView lv;
    ListView expListView2;
    String NAME,PAID;
    
    List<item> new_ques;
    TextView totsub,corsub;
   list la;
   list la2;
    TextView user;
    ArrayList<String> nextViews;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    //Parent par;
    String show_ques="http://www.ims4maths.com/imsAppfolder/show_questions.php";
    String show_new_ques="http://www.ims4maths.com/imsAppfolder/new_questions.php";
    String url="http://www.ims4maths.com/imsAppfolder/nsub.php";
   Button slide;
   
 String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
        setContentView(R.layout.activity_expandable);
     
       user=(TextView)findViewById(R.id.user);
       name=getIntent().getExtras().getString("username");
       lv=(ListView)findViewById(R.id.lvExp);
		NAME=getIntent().getExtras().getString("NAME");
		PAID=getIntent().getExtras().getString("PAID");
		
		 questions=new ArrayList<item>();
  	new CreateNewProduct().execute();
  	
	  	new Nosub().execute();
	          lv.setOnItemClickListener(new OnItemClickListener()
	            {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Intent i=new Intent(getApplicationContext(),SubAct.class);
						i.putExtra("ques",questions.get(arg2).getQues());
						i.putExtra("tag", questions.get(arg2).getTag());
						i.putExtra("date", questions.get(arg2).getdate());
						i.putExtra("imgques", questions.get(arg2).getImgQues());
						i.putExtra("ans", questions.get(arg2).getAns());
						i.putExtra("NAME", NAME);
						 i.putExtra("PAID", PAID);
						i.putExtra("imgans", questions.get(arg2).getImgAns());
						i.putExtra("ques_no", questions.get(arg2).getQuesno());
						i.putExtra("no_sub", questions.get(arg2).getNoSub());
						i.putExtra("username", name);
					String x="sub";
					String y="user";
					String z="right";
					String u="imgsub";
					int j;
				for(j=0;j<(questions.get(arg2).getNoSub());++j)
				{
					i.putExtra(x+j, questions.get(arg2).getChildren().get(j).getSub());
					i.putExtra(y+j, questions.get(arg2).getChildren().get(j).getUser());
					i.putExtra(z+j, questions.get(arg2).getChildren().get(j).getRight());
					i.putExtra(u+j, questions.get(arg2).getChildren().get(j).getImgsub());
				}
				startActivity(i);
					}
	            });
	     
       expListView2 = (ListView)findViewById(R.id.lvExp2);
       new_ques= new ArrayList<item>();
      
      new GetNewQuestions().execute();

    
      expListView2.setOnItemClickListener(new OnItemClickListener()
      {

      	@Override
      	public void onItemClick(AdapterView<?> arg0, View arg1,
      			int arg2, long arg3) {
      		Intent i=new Intent(getApplicationContext(),ThisQuestion.class);
      		i.putExtra("username", name);
      		i.putExtra("ques", new_ques.get(arg2).getQues());
      		i.putExtra("quesno", new_ques.get(arg2).getQuesno());
      		 i.putExtra("NAME", NAME);
			 i.putExtra("PAID", PAID);
      		i.putExtra("quesimg", new_ques.get(arg2).getImgQues());
      		if(new_ques.get(arg2).getNoSub()==1)
      		{
      		i.putExtra("type", "edit");}
      		else
      		{
      			
          		i.putExtra("type", "submit");
      		}
      		i.putExtra("subimg", "null");
      		i.putExtra("sub", "");
      		i.putExtra("activity", "expandable");
      		startActivity(i);
      	}});
       user.setText(name);
       TextView names=(TextView)findViewById(R.id.name);
       totsub=(TextView)findViewById(R.id.totsub);
       corsub=(TextView)findViewById(R.id.corsub);
       names.setText(NAME);
       if(PAID.equals("0"))
    	   corsub.setVisibility(View.GONE);
       mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
       mDrawerList = (ListView) findViewById(R.id.left_drawer);
       slide=(Button)findViewById(R.id.slide);
       TabHost th=(TabHost)findViewById(R.id.tabhost);
		th.setup();
		TabSpec specs=th.newTabSpec("tag1");
		specs.setContent(R.id.tab1);
		specs.setIndicator("All Questions");
		th.addTab(specs);
		specs=th.newTabSpec("tag2");
		specs.setContent(R.id.tab2);
		specs.setIndicator("New Questions");
		th.addTab(specs);
       slide.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			mDrawerLayout.openDrawer(Gravity.LEFT);
		}
	});
nextViews=new ArrayList<String>();
nextViews.add("About Us");
nextViews.add("My submissions");
nextViews.add("Leaderboard");
nextViews.add("Categories");
nextViews.add("Contact Us");
nextViews.add("Help");
nextViews.add("Settings");
ArrayAdapter<String> arrayAdapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, nextViews);
mDrawerList.setAdapter(arrayAdapter);
mDrawerList.setOnItemClickListener(new OnItemClickListener()
{

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1,
			int arg2, long arg3) {
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		Boolean isInternetPresent = cd.isConnectingToInternet();
		if(!isInternetPresent)
		{Toast abc=Toast.makeText(getApplicationContext(),"NO INTERNET CONNECTION!",Toast.LENGTH_LONG);
		abc.show();}
		else
		{if(arg2==0)
		{
			Intent i=new Intent(getApplicationContext(),Director.class);
			i.putExtra("act", "all");
			startActivity(i);
		}
		else if(arg2==1)
		{
			Intent i=new Intent(getApplicationContext(),All.class);
			i.putExtra("username", name);
			i.putExtra("NAME", NAME);
			 i.putExtra("PAID", PAID);
			startActivity(i);
		}
		else if(arg2==2)
		{
			Intent i=new Intent(getApplicationContext(),Leaderboard.class);
			i.putExtra("username", name);
			startActivity(i);
		}
		else if(arg2==3)
		{
			Intent i=new Intent(getApplicationContext(),Category.class);
			i.putExtra("username", name);
			i.putExtra("NAME", NAME);
			 i.putExtra("PAID", PAID);
			startActivity(i);
		}
		else if(arg2==4)
		{
			Intent i=new Intent(getApplicationContext(),NewPass.class);
			i.putExtra("username", name);
			startActivity(i);
		}
		else if(arg2==5)
		{
			Intent i=new Intent(getApplicationContext(),MainAct.class);
			i.putExtra("username", name);
			startActivity(i);
		}
		else if(arg2==6)
		{
			Intent i=new Intent(getApplicationContext(),Settings.class);
			i.putExtra("username", name);
			
			startActivity(i);
		}
		}
	}});
      
       
    }

class CreateNewProduct extends AsyncTask<String,String,String> {
		
		protected void onPreExecute()
		{
		
		}
			@Override
			protected String doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				
    			try{
    			URL url=new URL(show_ques);
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
				for(int i=success.length()-1;i>=0;--i)
				{JSONObject obj=success.getJSONObject(i);
					item it=new item();
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
					questions.add(it);
				}
				 la=new list(Expandable.this,questions);
			       lv.setAdapter(la);
			}
			catch(JSONException e){
				e.printStackTrace();
			} 
			}
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
				for(int i=success.length()-1;i>=0;--i)
				{JSONObject obj=success.getJSONObject(i);
					item it=new item();
					it.setQues(obj.getString("question"));
					it.setTag(obj.getString("tag"));
					it.setImgQues(obj.getString("image"));
					String x=obj.getString("s_no");
					it.setQuesno(x);
					it.setAns("  ");
					it.setDate(obj.getString("date"));
				
					if(!obj.getString("username").equals("null"))
    				{while(i>=0)
    				{
    					JSONObject obx=success.getJSONObject(i);
    					String y=obx.getString("s_no");
    					if(y.equals(x))
    					{
String user=obx.getString("username");
if(user.equals(name))
						{it.setNoSub(1);
						}
    						--i;
    					}
    					else
    						{++i;
    						break;
    						}
    				}}
    				//new GetSubmissions().execute();
    				
					new_ques.add(it);
				}
				la2=new list(Expandable.this,new_ques);
			      expListView2.setAdapter(la2);}
    		catch(JSONException e){
    			e.printStackTrace();
    		} 
    		}
}
class Nosub extends AsyncTask<String,String,String> {
	
protected void onPreExecute()
{
	
}
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		String username=user.getText().toString();
		
		
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username",username));
		
		
		String json;
			
			json=jsonParser.makeHttpRequest(url, "POST", params);
			
		return json;
		
	}
	protected void onPostExecute(String response){
		
		try
		{JSONObject js=new JSONObject(response);
		int total=js.getInt("total");
		int correct=js.getInt("correct");
		totsub.setText("Submissions: "+total);
		corsub.setText("Correct: "+correct);
		}
		
	
	catch(JSONException e){
		e.printStackTrace();
	} 
	}
}


}
