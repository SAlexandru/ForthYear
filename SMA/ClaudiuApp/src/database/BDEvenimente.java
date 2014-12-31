package database;
import tabele.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;


public class BDEvenimente extends BDAcces {
	private ArrayList<Eveniment> evenimente = new ArrayList<Eveniment>();
	
	public void evenimente(){
		getData("http://zumbatimisoara.com/AppZumbaPHPScripts/getAllEvents.php");
	}
	public void introducereEveniment(int id, int notificare_id, Timestamp data){
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id",String.valueOf(id)));
	    nameValuePairs.add(new BasicNameValuePair("notificare_id",String.valueOf(notificare_id)));
	    nameValuePairs.add(new BasicNameValuePair("data",String.valueOf(data.getTime()*1000)));
	    
		putData("http://zumbatimisoara.com/AppZumbaPHPScripts/addEvent.php",nameValuePairs);
	}
	
	
	@Override
	void parse(String result) {
		try{
    		JSONArray jArray = new JSONArray(result);
    		
    		for(int i=0; i< jArray.length();i++){
    			JSONObject json = jArray.getJSONObject(i);
    			Eveniment e = new Eveniment(json.getInt("ID"),
    										json.getInt("Notificare_ID"),
    										new Timestamp(json.getLong("Data")));	
    			evenimente.add(e);
    		}
    	}
    	catch(Exception e){
    		Log.e("log_tag", "Error parsing data "+e.toString());
    	}
	}
	
	public ArrayList<Eveniment> getEvenimente(){
		return evenimente;
	}

}
