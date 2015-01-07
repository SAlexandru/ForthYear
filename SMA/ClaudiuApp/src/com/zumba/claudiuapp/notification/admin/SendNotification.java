package com.zumba.claudiuapp.notification.admin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import tabele.Notificare;
import tabele.NotificationType;

import com.zumba.claudiuapp.R;

import database.BDNotificari;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SendNotification extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_notification);
		
		Button b = (Button)findViewById(R.id.bSend);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendData();
			}
		});
	}
	
	private void sendData () {
		EditText title = (EditText)findViewById(R.id.titleText);
		EditText message   = (EditText)findViewById(R.id.msgText);
		Spinner spinner = (Spinner)findViewById(R.id.notifType);
		
		String t = "", msg = "";
		NotificationType type;
		
		t = title.getText().toString().trim();
		msg = message.getText().toString().trim();
		try {
			type = NotificationType.valueOf((String)spinner.getSelectedItem());
		}
		catch (Exception e) {
			Toast.makeText(this, 
				       	  "Invalid notification type", 
				       	  Toast.LENGTH_LONG
				       ).show();
			return ;
		}
	
		try {
		 SentNotifications.addNotification(new Notificare(1, new Timestamp(new Date().getTime()), t, msg, type));
		
		new BDNotificari().introducereNotificare(UUID.randomUUID().hashCode(), 
												 new Timestamp(new Date().getTime()),
												 t, 
												 msg, 
												 type);
		}
		catch (IllegalArgumentException e) {
			Toast.makeText(this, 
			       	  e.getMessage(), 
			       	  Toast.LENGTH_LONG
			       ).show();
		return ;
		}
		
	}
}