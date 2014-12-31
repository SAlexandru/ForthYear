package com.zumba.claudiuapp;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import tabele.DetaliuCurs;
import tabele.Utilizator;
import database.BDDetaliiCurs;
import database.BDUtilizatori;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class ContactInstructorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_instructor);
		
		//************************************************************************************************
        StrictMode.enableDefaults();
        //************************************************************************************************
		
		EditText etInstructor = (EditText) findViewById(R.id.editTextContactInstructor);
		EditText etAdresaStrada = (EditText) findViewById(R.id.editTextContactAdresaStrada);
		EditText etAdresaNr = (EditText) findViewById(R.id.editTextContactAdresaNr);
		EditText etAdresaLocalitate = (EditText) findViewById(R.id.editTextContactAdresaLocalitate);
		EditText etTelefon = (EditText) findViewById(R.id.editTextContactTelefon);
		EditText etSite = (EditText) findViewById(R.id.editTextContactSite);
		EditText etPhotoLink = (EditText) findViewById(R.id.editTextContactPhotoLink);
		ImageView ivLocatie = (ImageView) findViewById(R.id.imageViewContactInstructor);
		
		BDDetaliiCurs bdDetaliiCurs = new BDDetaliiCurs();
		bdDetaliiCurs.detaliiCursDupaID(1);
		ArrayList<DetaliuCurs> detaliuCurs = bdDetaliiCurs.getDetaliiCurs();
		
		for(DetaliuCurs it : detaliuCurs){
			// *************************************** set site
			etSite.setText(it.getSite());
			// *************************************** set telephone
			etTelefon.setText(it.getTelefon());
			// *************************************** set telephone
			etPhotoLink.setText(it.getLink_poza());
			// *************************************** set address
			
			etAdresaStrada.setText(it.getStrada());
			etAdresaNr.setText(String.valueOf(it.getNr()));
			etAdresaLocalitate.setText(it.getLocalitate());
			
			// ********************************************* get instructor name
			int instructor_id = it.getInstructor_id();
			BDUtilizatori bdut = new BDUtilizatori();
			bdut.utilizatorDupaID(instructor_id);
			Utilizator instructor = bdut.getUtilizatori().get(0);
			
			etInstructor.setText(instructor.getNume() + " " + instructor.getPrenume());
			
			// ********************************************* get image for location
			Bitmap bitmap = getBitmapFromURL(it.getLink_poza());
			ivLocatie.setImageBitmap(bitmap);

		}
	}
	
	public void saveContactChanges(View v){
		//EditText etInstructor = (EditText) findViewById(R.id.editTextContactInstructor);
		EditText etAdresaStrada = (EditText) findViewById(R.id.editTextContactAdresaStrada);
		EditText etAdresaNr = (EditText) findViewById(R.id.editTextContactAdresaNr);
		EditText etAdresaLocalitate = (EditText) findViewById(R.id.editTextContactAdresaLocalitate);
		EditText etTelefon = (EditText) findViewById(R.id.editTextContactTelefon);
		EditText etSite = (EditText) findViewById(R.id.editTextContactSite);
		EditText etPhotoLink = (EditText) findViewById(R.id.editTextContactPhotoLink);
	
		BDDetaliiCurs bdDetaliiCurs = new BDDetaliiCurs();
		bdDetaliiCurs.updateDetaliiCurs(1, etTelefon.getText().toString(), 
												etSite.getText().toString(), 
												etAdresaStrada.getText().toString(), 
												Integer.parseInt(etAdresaNr.getText().toString()), 
												etAdresaLocalitate.getText().toString(), 
												1, //  *************************************** hard codat
												etPhotoLink.getText().toString());
		Intent intent = new Intent(this, ContactInstructorActivity.class);
		startActivity(intent);
	}

	public Bitmap getBitmapFromURL(String src){
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(input);
			return bitmap;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
