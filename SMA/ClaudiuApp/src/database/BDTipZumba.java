package database;
import tabele.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;


public class BDTipZumba extends BDAcces {

	private ArrayList<TipZumba> tipuriZumba = new ArrayList<TipZumba>();
	
	public void tipZumba(){
		getData("http://zumbatimisoara.com/AppZumbaPHPScripts/getAllZumbaTypes.php");
	}
	public void zumbaDupaTip(String tip){
		getData("http://zumbatimisoara.com/AppZumbaPHPScripts/getZumbaByType.php?type="+tip);
	}
	public void introducereTipZumba(int id, String nume, String descriere){
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id",String.valueOf(id)));
	    nameValuePairs.add(new BasicNameValuePair("nume",nume));
	    nameValuePairs.add(new BasicNameValuePair("descriere",descriere));
	    
		putData("http://zumbatimisoara.com/AppZumbaPHPScripts/addZumbaType.php",nameValuePairs);
	}
	
	@Override
	void parse(String result) {
		try{
    		JSONArray jArray = new JSONArray(result);
    		
    		for(int i=0; i< jArray.length();i++){
    			JSONObject json = jArray.getJSONObject(i);
    			TipZumba tz = new TipZumba(json.getInt("ID"),
    									   json.getString("Nume"),
    									   json.getString("Descriere"));
    			tipuriZumba.add(tz);
    		}
    	}
    	catch(Exception e){
    		Log.e("log_tag", "Error parsing data "+e.toString());
    	}

	}
	
	public ArrayList<TipZumba> getTipuriZumba(){
		return tipuriZumba;
	}

}
