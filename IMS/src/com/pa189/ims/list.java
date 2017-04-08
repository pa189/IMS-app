package com.pa189.ims;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

public class list extends ArrayAdapter<item>{
	private LayoutInflater infalInflater;
	private List<item> _items;
	    public list(Context context, List<item> items) {
	    	super(context, 0, items);
	     
	        //inflater = LayoutInflater.from(context);
	    	this.infalInflater = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	this._items=items;
	    }
	
	@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return _items.size();
		}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		
		item li = (item)getItem(arg0);
	       if(convertView==null)
	       {
	            convertView = infalInflater.inflate(R.layout.item_lay, null);
	        	ViewHolder holder=new ViewHolder();
	      
	      holder.ques = (TextView) convertView.findViewById(R.id.ques);
	        holder.tag = (TextView) convertView.findViewById(R.id.tag);
	        holder.no_sub = (TextView) convertView.findViewById(R.id.no_sub);
	        holder.date = (TextView) convertView.findViewById(R.id.date);
	        convertView.setTag(holder);}
	       ViewHolder vh=(ViewHolder) convertView.getTag();
	       vh.ques.setText(li.getQues());
	       vh.tag.setText(li.getTag());
	       if(li.getAns().equals("  "))
	    	   vh.no_sub.setVisibility(View.GONE);
	       else if(li.getChildren().size()==0)
	    	   vh.no_sub.setText("No submissions");
	       else
	       {if(li.getChildren().get(0).getUser().equals(" "))
	    	   vh.no_sub.setVisibility(View.GONE);
	       
	       else
	       vh.no_sub.setText(li.getNoSub()+" submissions");}
	       vh.date.setText(li.getdate());
	
	       return convertView;
}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	private class ViewHolder {  
		TextView tag;
        TextView date;
       
    TextView ques;
    TextView no_sub;
   
    } 
}

