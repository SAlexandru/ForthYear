package database;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.NameValuePair;

import android.util.Log;

public abstract class BDAcces {

    public void getData(String URL){
    	String result="";
    	InputStream isr = null;
    	
    	try{
    		HttpClient httpClient = new DefaultHttpClient();
    		HttpPost httpPost = new HttpPost(URL);
    		HttpResponse response = httpClient.execute(httpPost);
    		HttpEntity entity = response.getEntity();
    		isr = entity.getContent();
    	}
    	catch(Exception e){
    		Log.e("log_tag","Error in http connection "+e.toString());
    		result = "Could not connect to database.";
    	}
    	
    	// convert response to string
    	try{
    		BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
    		StringBuilder sb = new StringBuilder();
    		String line = null;
    		while((line = reader.readLine()) != null){
    			sb.append(line + "\n");
    		}
    		isr.close();
    		result = sb.toString();
    	}
    	catch(Exception e){
    		Log.e("log_tag", "Error converting result "+e.toString());
    	}
    	
    	// parse json data
    	
    	parse(result);
    	
    }
    
    
    public void putData(String URL, List<NameValuePair> nameValuePairs){
    	InputStream isr = null;
    	
    	try{
    		HttpClient httpClient = new DefaultHttpClient();
    		HttpPost httpPost = new HttpPost(URL);
    		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));	
    		HttpResponse response = httpClient.execute(httpPost);
    		HttpEntity entity = response.getEntity();
    		isr = entity.getContent();
    	}
    	catch(Exception e){
    		Log.e("log_tag","Error in http connection "+e.toString());
    	}

    	try{
    		BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
    		StringBuilder sb = new StringBuilder();
    		String line = null;
    		while((line = reader.readLine()) != null){
    			sb.append(line + "\n");
    		}
    		isr.close();
    	}
    	catch(Exception e){
    		Log.e("log_tag", "Error converting result "+e.toString());
    	}
    }
    
    abstract void parse(String result);
		
}
