package com.pa189.ims;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.TextView;

public class SubListItem extends ArrayAdapter<Child>{
	private LayoutInflater infalInflater;
	   //ImageView iv[];
	    public SubListItem(Context context, List<Child> items) {
	    	super(context, 0, items);
	       //iv=new ImageView[items.size()];
	        //inflater = LayoutInflater.from(context);
	    	this.infalInflater = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	    }
	    private class ViewHolder {  
			TextView user;
	        TextView right;
	       
	    TextView sub;
	    ImageView i;
	   
	    } 
	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		
		final Child li = (Child)getItem(arg0);
	       if(convertView==null)
	       {
	            convertView = infalInflater.inflate(R.layout.sign, null);
	        	ViewHolder holder=new ViewHolder();
	      
	        holder.user = (TextView) convertView.findViewById(R.id.user);
	        holder.right = (TextView) convertView.findViewById(R.id.right);
	        holder.sub = (TextView) convertView.findViewById(R.id.sub);
	      holder.i=(ImageView)convertView.findViewById(R.id.subimg);
	      convertView.setTag(holder);}
	       ViewHolder vh=(ViewHolder) convertView.getTag();
	       if(li.getUser().equals(" "))
	    	   vh.user.setVisibility(View.GONE);
	       else
	       vh.user.setText(li.getUser());
	       vh.sub.setText(li.getSub());
	       if(li.getPaid().equals("0"))
	    	   vh.right.setVisibility(View.GONE);
	       else
	       {if(li.getRight().equals("c"))
				vh.right.setBackgroundResource(R.drawable.tick);
			else if(li.getRight().equals("w"))
				vh.right.setBackgroundResource(R.drawable.cross);
			else
				vh.right.setVisibility(View.GONE);}
	       if(!li.getImgsub().equals("null"))
	       {
	    	   vh.i.setVisibility(View.VISIBLE);
	    	   //i.setI(li.getImgsub());
	    	   //i.setImageBitmap(li.getSubimg());
	    	   new DownloadFileFromURL(vh.i).execute("http://www.ims4maths.com/imsAppfolder/uploads/"+li.getImgsub());
	       }
	
	       return convertView;
}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return true;
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
		
			iv.setImageBitmap(result);
		}

	}

}

