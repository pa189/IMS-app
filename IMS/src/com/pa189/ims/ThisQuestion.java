package com.pa189.ims;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ThisQuestion extends Activity{
	private ProgressDialog pDialog;
	JSONParser jsonParser=new JSONParser();
String username;
String question_no;
EditText tv6;
String sub;
String type;
Button addimg;
ImageView qi,si;
String quesimg,subimg;
String submission;
String NAME,PAID;
private Bitmap bitmap=null;
LinearLayout llimg;
String act;
private Uri filePath;
private int PICK_IMAGE_REQUEST = 1;
	String urlsubmit="http://www.ims4maths.com/imsAppfolder/new_submission.php";
	String urledit="http://www.ims4maths.com/imsAppfolder/edit_submission.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.thisquestion);
		
		question_no=getIntent().getExtras().getString("quesno");
		String ques=getIntent().getExtras().getString("ques");
		sub=getIntent().getExtras().getString("sub");
		type=getIntent().getExtras().getString("type");
		username=getIntent().getExtras().getString("username");
		quesimg=getIntent().getExtras().getString("quesimg");
		subimg=getIntent().getExtras().getString("subimg");
		addimg=(Button)findViewById(R.id.addImg);
		act=getIntent().getExtras().getString("activity");
		NAME=getIntent().getExtras().getString("NAME");
		PAID=getIntent().getExtras().getString("PAID");
		TextView tv2=(TextView)findViewById(R.id.ques);
		qi=(ImageView)findViewById(R.id.quesimg);
		si=(ImageView)findViewById(R.id.subimg);
		llimg=(LinearLayout)findViewById(R.id.imgbar);
		if(!quesimg.equals("null"))
		{
			qi.setVisibility(View.VISIBLE);
			new DownloadFileFromURL().execute("http://www.ims4maths.com/imsAppfolder/uploads/"+quesimg,"ques");
		}
		if(!subimg.equals("null"))
		{
			llimg.setVisibility(View.VISIBLE);
			new DownloadFileFromURL().execute("http://www.ims4maths.com/imsAppfolder/uploads/"+subimg,"sub");
		}
		qi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			Intent i=new Intent(getApplicationContext(),GetImg.class);
			String url="http://www.ims4maths.com/imsAppfolder/uploads/"+quesimg;
			i.putExtra("url",url);
			startActivity(i);
			}
		});
		ImageButton bv=(ImageButton)findViewById(R.id.cross);
		bv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			bitmap=null;
			llimg.setVisibility(View.GONE);
			}
		});
		addimg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showFileChooser();
			}
		});

		//TextView tv5=(TextView)findViewById(R.id.subno);
		tv6=(EditText)findViewById(R.id.sub);
		Button b=(Button)findViewById(R.id.submit);
		//tv1.setText("QUESTION");
		tv2.setText(ques);
		//tv5.setText("YOUR SUBMISSION:");
		if(sub.matches(""))
			tv6.setHint("Enter answer here");
		else
		tv6.setText(sub);
		TextView im=(TextView)findViewById(R.id.im);
		im.setTextSize(8);
		im.setText("Image");
		b.setText("Submit");
		TextView tv7=(TextView)findViewById(R.id.user);
		tv7.setText(username);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
				Boolean isInternetPresent = cd.isConnectingToInternet();
				if(!isInternetPresent)
				{Toast abc=Toast.makeText(getApplicationContext(),"NO INTERNET CONNECTION!",Toast.LENGTH_LONG);
				abc.show();}
				else
				{submission=tv6.getText().toString();
				new CreateNewProduct().execute();
				
			}}
		});
	}
	 private void showFileChooser() {
	        Intent intent = new Intent();
	        intent.setType("image/*");
	        intent.setAction(Intent.ACTION_GET_CONTENT);
	        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
	    }
	 
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	 
	        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
	 
	            filePath = data.getData();
	            try {
	                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
	                bitmap = Bitmap.createScaledBitmap(bitmap,300, 300, true);
	               llimg.setVisibility(View.VISIBLE);
	               si.setImageBitmap(bitmap);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	 
	    public String getStringImage(Bitmap bmp){
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	       bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	        byte[] imageBytes = baos.toByteArray();
	        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
	        return encodedImage;
	    }
	class CreateNewProduct extends AsyncTask<String,String,String> {
		
		protected void onPreExecute()
		{
			
			
			pDialog=new ProgressDialog(ThisQuestion.this);
			pDialog.setMessage("Processing...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
			@Override
			protected String doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				String image="null";
				if(bitmap!=null)
				image = getStringImage(bitmap);
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username",username));
				params.add(new BasicNameValuePair("question_no",question_no));
				params.add(new BasicNameValuePair("submission",submission));
				params.add(new BasicNameValuePair("imgsub",image));
				String json;
					if(type.equals("submit"))
					json=jsonParser.makeHttpRequest(urlsubmit, "POST", params);
					else
						json=jsonParser.makeHttpRequest(urledit, "POST", params);
				return json;
				
			}
			protected void onPostExecute(String response){
				pDialog.dismiss();
				 Toast.makeText(getApplicationContext(),"Answer submitted!",Toast.LENGTH_LONG).show();
				 
				 finish();
			}
		}
	class DownloadFileFromURL extends AsyncTask<String, String, Bitmap> {

		String type;
		@Override
		protected void onPreExecute() {
			
			
		}

		
		@Override
		protected Bitmap doInBackground(String... f_url) {
			int count;
			Bitmap bit=null;
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
	            bit = BitmapFactory.decodeByteArray(output.toByteArray(), 0, output.size());
	            // flushing output
	            output.flush();
	conection.disconnect();
	            // closing streams
	            output.close();
	            input.close();

	        } catch (Exception e) {
	        	Log.e("Error: ", e.getMessage());
	        }

	        return bit;
		}

		
		
		@Override
		protected void onPostExecute(Bitmap result) {
		
			if(type.equals("ques"))
				qi.setImageBitmap(result);
			else
			{si.setImageBitmap(result);
			bitmap= result.copy(result.getConfig(), true);;
			}
		}

	}
	
	
}
