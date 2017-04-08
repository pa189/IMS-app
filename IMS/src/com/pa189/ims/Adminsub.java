package com.pa189.ims;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;

import android.view.View;
import android.view.Window;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Adminsub extends Activity {
ArrayList<Child> child;
ListView lv;
ListItem sli;
ImageView ivq,iva;
private ProgressDialog pDialog;
String ques_no;
JSONParser jsonParser=new JSONParser();
String dele="http://www.ims4maths.com/imsAppfolder/ndel.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_adminsub);
		lv=(ListView)findViewById(R.id.lvExp);
		final String ques=getIntent().getExtras().getString("ques");
		ques_no=getIntent().getExtras().getString("ques_no");
		final String tag=getIntent().getExtras().getString("tag");
		final String date=getIntent().getExtras().getString("date");
		final String quesimg=getIntent().getExtras().getString("imgques");
		final String ans=getIntent().getExtras().getString("ans");
		final String ansimg=getIntent().getExtras().getString("imgans");
		int no_sub=getIntent().getIntExtra("no_sub", 0);
		child=new ArrayList<Child>();
		int i;
		for(i=0;i<no_sub;++i)
		{
			Child ch=new Child();
			ch.setSub(getIntent().getExtras().getString("sub"+i));
			ch.setUser(getIntent().getExtras().getString("user"+i));
			ch.setRight(getIntent().getExtras().getString("right"+i));
			ch.setImgsub(getIntent().getExtras().getString("imgsub"+i));
			ch.setQuesno(ques_no);
			child.add(ch);
			//if(!ch.getImgsub().equals("null"))
				//new DownloadFileFromURL().execute("http://192.168.1.112/imsfolder/uploads/"+ch.getImgsub(),""+i);
		}
		sli=new ListItem(Adminsub.this,child);
		lv.setAdapter(sli);
		TextView tvques=(TextView)findViewById(R.id.ques);
		TextView tvtag=(TextView)findViewById(R.id.tag);
		TextView tvdate=(TextView)findViewById(R.id.date);
		TextView tvans=(TextView)findViewById(R.id.ans);
		TextView comm=(TextView)findViewById(R.id.comm);
		ivq=(ImageView)findViewById(R.id.quesimg);
		iva=(ImageView)findViewById(R.id.ansimg);
		Button del=(Button)findViewById(R.id.del);
		del.setText("Delete");
		del.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new CreateNewProduct().execute();
			}
		});
		if(!quesimg.equals("null"))
		{
			ivq.setVisibility(View.VISIBLE);
			new DownloadFileFromURL().execute("http://www.ims4maths.com/imsAppfolder/uploads/"+quesimg,"ques");
		}
		if(!ansimg.equals("null"))
		{
			iva.setVisibility(View.VISIBLE);
			new DownloadFileFromURL().execute("http://www.ims4maths.com/imsAppfolder/uploads/"+ansimg,"ans");
		}
		tvques.setText(ques);
		tvtag.setText(tag);
		tvdate.setText(date);
		
		Button editques=(Button)findViewById(R.id.editques);
		editques.setText("Edit question");
		final Button editans=(Button)findViewById(R.id.editans);
		if(ans.equals("null")&&ansimg.equals("null"))
		{
			editans.setText("Submit answer");
			tvans.setVisibility(View.GONE);
		}
		else
		{
			editans.setText("Edit answer");
			tvans.setText(ans);
		}
		comm.setText("Submissions");
		
		editques.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Addques.class);
				i.putExtra("quesno", ques_no);
				i.putExtra("ques", ques);
				i.putExtra("tag", tag);
				i.putExtra("date", date);
				i.putExtra("quesimg", quesimg);
				i.putExtra("type", "edit");
				startActivity(i);
				finish();}
		});
		editans.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Adsubmit.class);
				i.putExtra("quesno", ques_no);
				i.putExtra("ques", ques);
				i.putExtra("quesimg", quesimg);
				if(editans.getText().toString().equals("Submit answer"))
				{i.putExtra("ans", "");
				i.putExtra("ansimg", "null");}
				else
				{
					i.putExtra("ans", ans);
					i.putExtra("ansimg", ansimg);
				}
				startActivity(i);
				finish();
			}
		});
		  lv.setOnItemClickListener(null);
		  ivq.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),GetImg.class);
				
				i.putExtra("url", "http://www.ims4maths.com/imsAppfolder/uploads/"+quesimg);
		startActivity(i);
			
			}
		});
		  iva.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i=new Intent(getApplicationContext(),GetImg.class);
					i.putExtra("url", "http://www.ims4maths.com/imsAppfolder/uploads/"+ansimg);
			startActivity(i);
				
				}
			});
	}

	class DownloadFileFromURL extends AsyncTask<String, String, Bitmap> {

		String type;
		@Override
		protected void onPreExecute() {
			
			
		}

		
		@Override
		protected Bitmap doInBackground(String... f_url) {
			int count;
			Bitmap bitmap=null;
			type=f_url[1];
	        try {
	            URL url = new URL(f_url[0]);
	            HttpURLConnection conection = (HttpURLConnection)url.openConnection();
	            conection.connect();
	           

	            // download the file
	            InputStream input = new BufferedInputStream(url.openStream(), 8192);

	            // Output stream
	            ByteArrayOutputStream output = new ByteArrayOutputStream();

	            byte data[] = new byte[1024];


	            while ((count = input.read(data)) != -1) {
	                
	                output.write(data, 0, count);
	            }
	            bitmap = BitmapFactory.decodeByteArray(output.toByteArray(), 0, output.size());
	            // flushing output
	            output.flush();
	conection.disconnect();
	            // closing streams
	            output.close();
	            input.close();

	        } catch (Exception e) {
	        	Log.e("Error: ", e.getMessage());
	        }

	        return bitmap;
		}

		
		
		@Override
		protected void onPostExecute(Bitmap result) {
		
			if(type.equals("ques"))
				ivq.setImageBitmap(result);
			else if(type.equals("ans"))
				iva.setImageBitmap(result);
			
		}

	}
class CreateNewProduct extends AsyncTask<String,String,String> {
		
		protected void onPreExecute()
		{
			
			
			pDialog=new ProgressDialog(Adminsub.this);
			pDialog.setMessage("Processing...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
			@Override
			protected String doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("s_no",ques_no));
				
				String json;
					
					json=jsonParser.makeHttpRequest(dele, "POST", params);
					
				return json;
				
			}
			protected void onPostExecute(String response){
				pDialog.dismiss();
				 Toast.makeText(getApplicationContext(),"Deleted!",Toast.LENGTH_LONG).show();
				finish();
		}}
	
}