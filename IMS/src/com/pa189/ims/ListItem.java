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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListItem extends ArrayAdapter<Child>{
	private LayoutInflater infalInflater;
	   //ImageView iv[];
	    public ListItem(Context context, List<Child> items) {
	    	super(context, 0, items);
	       //iv=new ImageView[items.size()];
	        //inflater = LayoutInflater.from(context);
	    	this.infalInflater = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	    }
	    private class ViewHolder {  
			TextView user;
	        Button cor;
	       Button wro;
	    TextView sub;
	    ImageView i;
	   
	    } 
	@Override
	public View getView(int arg0, View convertView, final ViewGroup arg2) {
		
		final Child li = (Child)getItem(arg0);
	       if(convertView==null)
	       {
	            convertView = infalInflater.inflate(R.layout.adminuser, null);
	        	ViewHolder holder=new ViewHolder();
	      
	        holder.user = (TextView) convertView.findViewById(R.id.user);
	        holder.cor = (Button) convertView.findViewById(R.id.cor);
	        holder.wro = (Button) convertView.findViewById(R.id.wro);
	        holder.sub = (TextView) convertView.findViewById(R.id.sub);
	      holder.i=(ImageView)convertView.findViewById(R.id.subimg);
	      convertView.setTag(holder);}
	       final ViewHolder vh=(ViewHolder) convertView.getTag();
	      vh.cor.setText("C");
	      vh.wro.setText("W");
	       vh.user.setText(li.getUser());
	       vh.sub.setText(li.getSub());
	       if(li.getRight().equals("c"))
				{vh.cor.setBackgroundColor(Color.GREEN);
				vh.wro.setBackgroundColor(Color.GRAY);
				}
			else if(li.getRight().equals("w"))
				{vh.wro.setBackgroundColor(Color.GREEN);
				vh.cor.setBackgroundColor(Color.GRAY);
				}
			else
				{vh.wro.setBackgroundColor(Color.GRAY);
				vh.cor.setBackgroundColor(Color.GRAY);
				}
	       if(!li.getImgsub().equals("null"))
	       {
	    	   vh.i.setVisibility(View.VISIBLE);
	    	   //i.setI(li.getImgsub());
	    	   //i.setImageBitmap(li.getSubimg());
	    	   new DownloadFileFromURL(vh.i).execute("http://www.ims4maths.com/imsAppfolder/uploads/"+li.getImgsub());
	       }
	vh.cor.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new CreateNewProduct(li).execute("http://www.ims4maths.com/imsAppfolder/nstatus.php","c");
			vh.cor.setBackgroundColor(Color.GREEN);
			vh.wro.setBackgroundColor(Color.GRAY);
		}
	});
vh.wro.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new CreateNewProduct(li).execute("http://www.ims4maths.com/imsAppfolder/nstatus.php","w");
			vh.cor.setBackgroundColor(Color.GRAY);
			vh.wro.setBackgroundColor(Color.GREEN);
		}
	});
vh.i.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i=new Intent(arg2.getContext(),GetImg.class);
		
		i.putExtra("url", "http://www.ims4maths.com/imsAppfolder/uploads/"+li.getImgsub());
arg2.getContext().startActivity(i);
	}
});
	       return convertView;
}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	class DownloadFileFromURL extends AsyncTask<String, String, Bitmap> {

		ImageView iv;
		public DownloadFileFromURL(ImageView iv1)
		{
			this.iv=iv1;
		}
		@Override
		protected void onPreExecute() {
			
			
		}

		
		@Override
		protected Bitmap doInBackground(String... f_url) {
			int count;
			Bitmap bitmap=null;
			//type=f_url[1];
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
		
			iv.setImageBitmap(result);		}

	}
	class CreateNewProduct extends AsyncTask<String,String,String> {
		Child li;
		public CreateNewProduct(Child l)
		{
			this.li=l;
		}
		protected void onPreExecute()
		{
				}
			@Override
			protected String doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				
				
				
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("question_no",li.getQuesno()));
				params.add(new BasicNameValuePair("username",li.getUser()));
				params.add(new BasicNameValuePair("status",arg0[1]));
				JSONParser jsonParser=new JSONParser();
				String json;
					
					json=jsonParser.makeHttpRequest(arg0[0], "POST", params);
					
				return json;
				
			}
			protected void onPostExecute(String response){
				
			}
			
			}
		}

