package com.pa189.ims;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.AdapterView.OnItemClickListener;

public class CatQues extends Activity{
String tag;
ArrayList<item> questions;
JSONParser jsonParser=new JSONParser();
list la;
ListView lv;
String NAME,PAID;
String url="http://www.ims4maths.com/imsAppfolder/GetTag.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_category);
		TextView user=(TextView)findViewById(R.id.user);
		user.setText(getIntent().getExtras().getString("username"));
		tag=getIntent().getExtras().getString("tag");
		 lv=(ListView)findViewById(R.id.lvExp);
			NAME=getIntent().getExtras().getString("NAME");
			PAID=getIntent().getExtras().getString("PAID");
		 questions=new ArrayList<item>();
  	new CreateNewProduct().execute();
  	
	  	
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
						i.putExtra("NAME", NAME);
						 i.putExtra("PAID", PAID);
						i.putExtra("ans", questions.get(arg2).getAns());
						i.putExtra("imgans", questions.get(arg2).getImgAns());
					
						i.putExtra("no_sub", questions.get(arg2).getNoSub());
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
	}
	class CreateNewProduct extends AsyncTask<String,String,String> {
		
		protected void onPreExecute()
		{
			
			
		}
			@Override
			protected String doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("tag",tag));
				
				String json;
					
					json=jsonParser.makeHttpRequest(url, "POST", params);
					
				return json;
				
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
				la=new list(CatQues.this,questions);
			       lv.setAdapter(la);
			}
			catch(JSONException e){
				e.printStackTrace();
			} 
			}
		}

}
