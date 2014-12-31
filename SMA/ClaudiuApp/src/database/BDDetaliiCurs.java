package database;
import tabele.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;


public class BDDetaliiCurs extends BDAcces {
	private ArrayList<DetaliuCurs> detaliiCurs = new ArrayList<DetaliuCurs>();
	
	public void detaliiCursuri(){
		getData("http://zumbatimisoara.com/AppZumbaPHPScripts/getAllCourseDetails.php");
	}
	public void detaliiCursDupaID(int id){
		getData("http://zumbatimisoara.com/AppZumbaPHPScripts/getCourseByID.php?id="+id);
	}
	public void updateDetaliiCurs(int id, String telefon, String site, String strada, int nr, String localitate, int instructor_id, String link_poza){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id",String.valueOf(id)));
	    nameValuePairs.add(new BasicNameValuePair("telefon",telefon));
	    nameValuePairs.add(new BasicNameValuePair("strada",strada));
	    nameValuePairs.add(new BasicNameValuePair("site",site));
	    nameValuePairs.add(new BasicNameValuePair("nr",String.valueOf(nr)));
	    nameValuePairs.add(new BasicNameValuePair("localitate",localitate));
	    nameValuePairs.add(new BasicNameValuePair("instructor_id",String.valueOf(instructor_id)));
	    nameValuePairs.add(new BasicNameValuePair("link_poza",link_poza));
	    
		putData("http://zumbatimisoara.com/AppZumbaPHPScripts/updateCourseDetails.php",nameValuePairs);
	}
	
	public void introducereDetaliiCurs(int id, String telefon, String site, String strada, int nr, String localitate, int instructor_id, String link_poza){
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id",String.valueOf(id)));
	    nameValuePairs.add(new BasicNameValuePair("telefon",telefon));
	    nameValuePairs.add(new BasicNameValuePair("site",site));
	    nameValuePairs.add(new BasicNameValuePair("nr",String.valueOf(nr)));
	    nameValuePairs.add(new BasicNameValuePair("localitate",localitate));
	    nameValuePairs.add(new BasicNameValuePair("instructor_id",String.valueOf(instructor_id)));
	    nameValuePairs.add(new BasicNameValuePair("link_poza",link_poza));
	    
		putData("http://zumbatimisoara.com/AppZumbaPHPScripts/addCourseDetails.php",nameValuePairs);
	}
	
	public void parse(String result){
		try{
    		JSONArray jArray = new JSONArray(result);
    		
    		for(int i=0; i< jArray.length();i++){
    			JSONObject json = jArray.getJSONObject(i);
    			DetaliuCurs dc = new DetaliuCurs(json.getInt("ID"),
    											 json.getString("Telefon"),
    											 json.getString("Site"),
    											 json.getString("Strada"),
    											 json.getInt("Numar"),
    											 json.getString("Localitate"),
    											 json.getInt("Instructor_ID"),
    											 json.getString("Link_poza"));
    			detaliiCurs.add(dc);
    		}
    	}
    	catch(Exception e){
    		Log.e("log_tag", "Error parsing data "+e.toString());
    	}
	}
	
	public ArrayList<DetaliuCurs> getDetaliiCurs(){
		return detaliiCurs;
	}
}
