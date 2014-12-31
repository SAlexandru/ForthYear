package com.zumba.claudiuapp;

import tabele.Utilizator;
import database.BDUtilizatori;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;

// **************************************************** call activity with parameter USER_ID

public class UserProfileActivity extends Activity {

	private int user_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		
		//************************************************************************************************
        StrictMode.enableDefaults();
        //************************************************************************************************
		
        Bundle b = getIntent().getExtras();
        user_id = b.getInt("USER_ID");
        
        EditText etName = (EditText) findViewById(R.id.editTextProfileName);
        EditText etForname = (EditText) findViewById(R.id.editTextfProfileForname);
        EditText etMail = (EditText) findViewById(R.id.editTextProfileMail);
        EditText etPass = (EditText) findViewById(R.id.editTextProfilePass);
        EditText etTelephone = (EditText) findViewById(R.id.editTextProfileTelephone);
        
        BDUtilizatori bdUtilizatori = new BDUtilizatori();
        bdUtilizatori.utilizatorDupaID(user_id);
        Utilizator u = bdUtilizatori.getUtilizatori().get(0);
        
        etName.setText(u.getNume());
        etForname.setText(u.getPrenume());
        etMail.setText(u.getMail());
        etPass.setText(u.getParola());
        etTelephone.setText(u.getTelefon());
     
	}
	
	public void saveChanges(View v){
		EditText etName = (EditText) findViewById(R.id.editTextProfileName);
        EditText etForname = (EditText) findViewById(R.id.editTextfProfileForname);
        EditText etMail = (EditText) findViewById(R.id.editTextProfileMail);
        EditText etPass = (EditText) findViewById(R.id.editTextProfilePass);
        EditText etTelephone = (EditText) findViewById(R.id.editTextProfileTelephone);
        
        BDUtilizatori bdUtilizatori = new BDUtilizatori();
        bdUtilizatori.updateUtilizator(user_id,
        							   etName.getText().toString(),
        							   etForname.getText().toString(),
        							   etMail.getText().toString(),
        							   etPass.getText().toString(),
        							   etTelephone.getText().toString());
	}
}
