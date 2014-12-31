package database;
import tabele.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import android.util.Log;


public class BDUtilizatori extends BDAcces {

	private ArrayList<Utilizator> utilizatori = new ArrayList<Utilizator>();
	
	public void utilizatori(){
		getData("http://zumbatimisoara.com/AppZumbaPHPScripts/getAllParticipants.php");
	}
	public void utilizatorDupaID(int id){
		getData("http://zumbatimisoara.com/AppZumbaPHPScripts/getParticipantByID.php?id="+id);
	}
	public void updateUtilizator(int id, String nume, String prenume, String mail, String parola, String telefon){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id",String.valueOf(id)));
	    nameValuePairs.add(new BasicNameValuePair("nume",nume));
	    nameValuePairs.add(new BasicNameValuePair("prenume",prenume));
	    nameValuePairs.add(new BasicNameValuePair("mail",mail));
	    nameValuePairs.add(new BasicNameValuePair("parola",parola));
	    nameValuePairs.add(new BasicNameValuePair("telefon",telefon));
	    
		putData("http://zumbatimisoara.com/AppZumbaPHPScripts/updateParticipant.php",nameValuePairs);
	}
	public void introducereUtilizator(String nume, String prenume, String mail, String parola, String telefon, int e_instructor){
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("nume",nume));
	    nameValuePairs.add(new BasicNameValuePair("prenume",prenume));
	    nameValuePairs.add(new BasicNameValuePair("mail",mail));
	    nameValuePairs.add(new BasicNameValuePair("parola",parola));
	    nameValuePairs.add(new BasicNameValuePair("telefon",telefon));
	    nameValuePairs.add(new BasicNameValuePair("e_instructor",String.valueOf(e_instructor)));
	    
		putData("http://zumbatimisoara.com/AppZumbaPHPScripts/addParticipant.php",nameValuePairs);
		
		/*putData("http://zumbatimisoara.com/AppZumbaPHPScripts/test.php?nume="+nume+
																				"&prenume="+prenume+
																				"&mail="+mail+
																				"&parola="+parola+
																				"&telefon="+telefon+
																				"&e_instructor="+e_instructor);*/
	}
	
	public int logareMailParola(String mail, String parola){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("mail",mail));
	    nameValuePairs.add(new BasicNameValuePair("parola",parola));
	    
	    return getData("http://zumbatimisoara.com/AppZumbaPHPScripts/logareMailParola.php",nameValuePairs);
	}
	
	
	
	// ***************************** pentru logare, returneaza e_instructor sau -1 in caz de mail si/sau parola gresite
    public int getData(String URL, List<NameValuePair> nameValuePairs){
    	String result="";
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
    	int e_instructor=-1;
    	try{
    		JSONArray jArray = new JSONArray(result);
    		
    		for(int i=0; i< jArray.length();i++){
    			JSONObject json = jArray.getJSONObject(i);
    			e_instructor = json.getInt("e_instructor");
    		}
    	}
    	catch(Exception e){
    		Log.e("log_tag", "Error parsing data "+e.toString());
    	}
    	
    	return e_instructor;
    }
	
	@Override
	void parse(String result) {
		try{
    		JSONArray jArray = new JSONArray(result);
    		
    		for(int i=0; i< jArray.length();i++){
    			JSONObject json = jArray.getJSONObject(i);
    			Utilizator u = new Utilizator(json.getInt("ID"),
    										  json.getString("Nume"),
    										  json.getString("Prenume"),
    										  json.getString("Mail"),
    										  json.getString("Telefon"),
    										  json.getString("Parola"),
    										  json.getInt("E_instructor"));
    			utilizatori.add(u);
    		}
    	}
    	catch(Exception e){
    		Log.e("log_tag", "Error parsing data "+e.toString());
    	}

	}
	
	public ArrayList<Utilizator> getUtilizatori(){
		return utilizatori;
	}

}
