package database;
import tabele.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;


public class BDTarife extends BDAcces {

	private ArrayList<Tarif> tarife = new ArrayList<Tarif>();
	
	public void tarife(){
		getData("http://zumbatimisoara.com/AppZumbaPHPScripts/getAllPrices.php");
	}
	public void tarifeDupaTip(String tip){
		getData("http://zumbatimisoara.com/AppZumbaPHPScripts/getPricesByType.php?type="+tip);
	}
	public void introducereTarif(int id, int tip_zumba_id, int nr_sedinte, int pret){
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id",String.valueOf(id)));
	    nameValuePairs.add(new BasicNameValuePair("tip_zumba_id",String.valueOf(tip_zumba_id)));
	    nameValuePairs.add(new BasicNameValuePair("nr_sedinte",String.valueOf(nr_sedinte)));
	    nameValuePairs.add(new BasicNameValuePair("pret",String.valueOf(pret)));
	    
		putData("http://zumbatimisoara.com/AppZumbaPHPScripts/addPrice.php",nameValuePairs);
	}
	
	@Override
	void parse(String result) {
		try{
    		JSONArray jArray = new JSONArray(result);
    		
    		for(int i=0; i< jArray.length();i++){
    			JSONObject json = jArray.getJSONObject(i);
    			Tarif t = new Tarif(json.getInt("ID"),
    								json.getInt("Tip_zumba_ID"),
    								json.getInt("Numar_sedinte"),
    								json.getInt("Pret"));
    			tarife.add(t);
    		}
    	}
    	catch(Exception e){
    		Log.e("log_tag", "Error parsing data "+e.toString());
    	}
	}
	
	public ArrayList<Tarif> getTarife(){
		return tarife;
	}

}
