package com.pa189.ims;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;

import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SubAct extends Activity {
ArrayList<Child> child;
ListView lv;
SubListItem sli;
String NAME,PAID;
ImageView ivq,iva;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sub);
		lv=(ListView)findViewById(R.id.lvExp);
		final String ques=getIntent().getExtras().getString("ques");
		final String username=getIntent().getExtras().getString("username");
		NAME=getIntent().getExtras().getString("NAME");
		PAID=getIntent().getExtras().getString("PAID");
		
		TextView user=(TextView)findViewById(R.id.user);
		user.setText(username);
		final String ques_no=getIntent().getExtras().getString("ques_no");
		String tag=getIntent().getExtras().getString("tag");
		String date=getIntent().getExtras().getString("date");
		final String quesimg=getIntent().getExtras().getString("imgques");
		String ans=getIntent().getExtras().getString("ans");
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
			ch.setPaid(PAID);
			child.add(ch);
			//if(!ch.getImgsub().equals("null"))
				//new DownloadFileFromURL().execute("http://192.168.1.112/imsfolder/uploads/"+ch.getImgsub(),""+i);
		}
		sli=new SubListItem(SubAct.this,child);
		lv.setAdapter(sli);
		TextView tvques=(TextView)findViewById(R.id.ques);
		TextView tvtag=(TextView)findViewById(R.id.tag);
		TextView tvdate=(TextView)findViewById(R.id.date);
		TextView tvans=(TextView)findViewById(R.id.ans);
		TextView comm=(TextView)findViewById(R.id.comm);
		ivq=(ImageView)findViewById(R.id.quesimg);
		iva=(ImageView)findViewById(R.id.ansimg);
		if(PAID.equals("0"))
		{
			tvans.setVisibility(View.GONE);
			iva.setVisibility(View.GONE);
		}
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
		tvans.setText(ans);
		Button edit=(Button)findViewById(R.id.edit);
		
		if(child.size()==1&&child.get(0).getUser().equals(" "))
		{
			comm.setText("Your Submission");
			
		}
		else
		comm.setText("Submissions");
		if(child.size()==1&&child.get(0).getUser().equals(" ")&&ans.equals("null")&&ansimg.equals("null"))
			{edit.setVisibility(View.VISIBLE);
			tvans.setVisibility(View.GONE);
			edit.setText("Edit submission");
			}
		edit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),ThisQuestion.class);
				i.putExtra("quesno",ques_no);
				i.putExtra("ques",ques);
				i.putExtra("sub", child.get(0).getSub());
				i.putExtra("type", "edit");
				i.putExtra("username", username);
				i.putExtra("quesimg", quesimg);
				i.putExtra("NAME", NAME);
				i.putExtra("PAID", PAID);
				i.putExtra("subimg",child.get(0).getImgsub());
				i.putExtra("activity", "all");
				startActivity(i);
				finish();
			}
		});
		  lv.setOnItemClickListener(new OnItemClickListener()
          {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent i=new Intent(getApplicationContext(),GetImg.class);
					if(!child.get(arg2).getImgsub().equals("null"))
					{i.putExtra("url", "http://www.ims4maths.com/imsAppfolder/uploads/"+child.get(arg2).getImgsub());
			startActivity(i);
				}
					else
						{Toast abc=Toast.makeText(getApplicationContext(),"No image attached!",Toast.LENGTH_LONG);
					abc.show();	}	}
          });
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

}
