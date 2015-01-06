package com.zumba.claudiuapp;

import android.content.Intent;
import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class NotificationActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		
		//Resources ressources = getResources(); 
		TabHost tabHost = getTabHost(); 
		
		Intent newNotif = new Intent(this, NewNotifications.class);
		TabSpec tabSpecNewNotif = tabHost.newTabSpec("New Notifications").setContent(newNotif).setIndicator("New Notifications");
		
		Intent readNotif = new Intent(this, NewNotifications.class);
		TabSpec tabSpecReadNotif = tabHost.newTabSpec("Read Notifications").setContent(readNotif).setIndicator("Read Notifications");
		
		/*
		Intent newNotif = new Intent(this, NewNotifications.class);
		TabSpec tabSpecNotif = tabHost.newTabSpec("New Notifications").setContent(newNotif);
		*/
		
		tabHost.addTab(tabSpecNewNotif);
		tabHost.addTab(tabSpecReadNotif);
		tabHost.setCurrentTab(1);
		
		
		
	}
}
