package com.pa189.ims;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.DefaultHttpClient;



import android.util.Log;

public class JSONParser {
InputStream is=null;

String json="";
public JSONParser(){
}
public String makeHttpRequest(String urli,String method,List<NameValuePair> params) {
		try
		{
		
			if(method=="POST"){
				DefaultHttpClient httpClient=new DefaultHttpClient();
				HttpPost httpPost=new HttpPost(urli);
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				HttpResponse httpResponse=httpClient.execute(httpPost);
				HttpEntity httpEntity=httpResponse.getEntity();
				is=httpEntity.getContent();
			}
			
		}
		catch(UnsupportedEncodingException e)
		{e.printStackTrace();}
		catch(ClientProtocolException e)
		{e.printStackTrace();}
		catch(IOException e)
		{e.printStackTrace();}
		try{
			BufferedReader reader=new BufferedReader(new InputStreamReader(is));
			StringBuilder sb=new StringBuilder();
			String line=null;
			while((line=reader.readLine())!=null){
				sb.append(line+"\n");
			}
			is.close();
			json=sb.toString().trim();			
	}
		catch(Exception e)
		{
			Log.e("Buffer Error","Error converting result "+e.toString());
		}
		
		return json;
}
}
