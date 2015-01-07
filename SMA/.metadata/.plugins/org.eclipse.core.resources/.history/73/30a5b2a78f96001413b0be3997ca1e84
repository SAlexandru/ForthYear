package com.zumba.claudiuapp;

import com.zumba.claudiuapp.notification.NotificationActivity;

import database.BDUtilizatori;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public static final String loggedUser = "loggedUser";
	private SharedPreferences loggedUserFile;
	
	@SuppressWarnings("unused")
	private boolean checkIfWasLoggedIn() {
		return loggedUserFile.getBoolean(loggedUserFile.getString("username", ""), false);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Intent i = new Intent(this, NotificationActivity.class);
		startActivity(i);
		
		Button register = (Button)findViewById(R.id.bRegister);
		
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this, RegisterUserActivity.class);
				startActivity(i);
			}
		});
		
		Button blogin = (Button)findViewById(R.id.bLogin);
		blogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText eUserName = (EditText)findViewById(R.id.eUserName);
				EditText ePassword = (EditText)findViewById(R.id.ePassword);
				
				
				String userName = eUserName.getText().toString().trim();
				String password = ePassword.getText().toString().trim();
				
				Log.d("claudiuapp", "username: " + userName);
				Log.d("claudiuapp", "password: " + password);
				if (userName.isEmpty() || password.isEmpty()) {
					Toast.makeText(LoginActivity.this, 
							       "Please enter both username and password", 
							       Toast.LENGTH_LONG
							       ).show();
				}
				else {
					doLogin(userName, password);
				}
			}			
		});
		
		
		
		loggedUserFile = getSharedPreferences(loggedUser, Context.MODE_PRIVATE);
	}

	private void doLogin(String userName, String password) {
		/*
		 *  invoke ioan's script
		 */
		int loggedIn = new BDUtilizatori().logareMailParola(userName, password);
		
		Log.d("claudiuapp", "loggedIn: " + loggedIn);
		
		if (-1 != loggedIn) {
			loggedUserFile.edit().putString("username", userName);
			loggedUserFile.edit().putBoolean(userName, true);
			loggedUserFile.edit().commit();
			
			if (1 == loggedIn) {
				Intent i = new Intent(this, AdminActivity.class);
				i.putExtra("USER_ID", 0); //aici cum fac ???
				startActivity(i);
				
			}
			else {
				Intent i = new Intent(this, UserActivity.class);
				i.putExtra("USER_ID", 0); //aici cum fac ???
				startActivity(i);
			}
		}
		else {
			Toast.makeText(this, 
					   	  "Invalid username or password!", 
					      Toast.LENGTH_LONG
					      ).show();
		}
		
	}
}
