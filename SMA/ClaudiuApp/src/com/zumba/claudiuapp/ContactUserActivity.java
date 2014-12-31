package com.zumba.claudiuapp;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import tabele.DetaliuCurs;
import tabele.Utilizator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;
import android.widget.TextView;
import database.BDDetaliiCurs;
import database.BDUtilizatori;

public class ContactUserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_user);
		
		//************************************************************************************************
        StrictMode.enableDefaults();
        //************************************************************************************************
		
		TextView tvInstructor = (TextView) findViewById(R.id.textViewContactInstructor);
		TextView tvAdresa = (TextView) findViewById(R.id.textViewContactAddress);
		TextView tvTelefon = (TextView) findViewById(R.id.textViewContactTelephone);
		TextView tvSite = (TextView) findViewById(R.id.textViewContactSite);
		ImageView ivLocatie = (ImageView) findViewById(R.id.imageViewContact);
		
		BDDetaliiCurs bdDetaliiCurs = new BDDetaliiCurs();
		bdDetaliiCurs.detaliiCursDupaID(1);
		ArrayList<DetaliuCurs> detaliuCurs = bdDetaliiCurs.getDetaliiCurs();
		
		for(DetaliuCurs it : detaliuCurs){
			// *************************************** set site
			tvSite.setText(it.getSite());
			// *************************************** set telephone
			tvTelefon.setText(it.getTelefon());
			// *************************************** set address
			
			String nr = "";
			if(it.getNr()!=0)
				nr = String.valueOf(it.getNr()) + " ";
			tvAdresa.setText(it.getStrada() + " " + nr + it.getLocalitate());
			
			// ********************************************* get instructor name
			int instructor_id = it.getInstructor_id();
			BDUtilizatori bdut = new BDUtilizatori();
			bdut.utilizatorDupaID(instructor_id);
			Utilizator instructor = bdut.getUtilizatori().get(0);
			
			tvInstructor.setText(instructor.getNume() + " " + instructor.getPrenume());
			
			// ********************************************* get image for location
			Bitmap bitmap = getBitmapFromURL(it.getLink_poza());
			ivLocatie.setImageBitmap(bitmap);
		}
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
