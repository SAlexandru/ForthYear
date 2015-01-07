package com.zumba.claudiuapp.notification;


import java.sql.Timestamp;
import java.util.List;
import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tabele.Notificare;

import com.zumba.claudiuapp.R;

import database.BDNotificari;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NewNotifications extends Activity {
	private ListView   lView;
	private Timestamp  lastTimestamp;
	private Thread     checkForNotifications_;
	
	private ArrayAdapter<Notificare> adapterL = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_notifications);
		
		lView = (ListView)findViewById(R.id.newNList);
		
		loadNotif();
		listenForNotif();
	}

	private void listenForNotif() {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		checkForNotifications_ = new Thread(new Runnable () {
			@Override
			public void run() {
				BDNotificari notificari = new BDNotificari();
				
				Timestamp newTimestamp = new Timestamp(lastTimestamp.getTime() + 500);
				notificari.notificariMaiNoiDecatData(newTimestamp);
				List<Notificare> lNotificare = notificari.getNotificari();
				Collections.reverse(lNotificare);
				for (Notificare notif : lNotificare) {
					adapterL.insert(notif, 0);
				}
				adapterL.notifyDataSetChanged();
			}
		});
		scheduler.scheduleAtFixedRate(checkForNotifications_, 30, 30, TimeUnit.SECONDS);
		
	}

	private Timestamp getOldNotifTimestamp() {
		Notifications notif = Notifications.getInstance(this);
		
		if (0 == notif.getNumberOfNotif()) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -14);
		
			return new Timestamp(calendar.getTime().getTime());
		}
		return notif.getOldestNotif().getData();
	}

	private void loadNotif() {
		BDNotificari notificari = new BDNotificari();
		
		lastTimestamp = getOldNotifTimestamp();
		notificari.notificariMaiNoiDecatData(getOldNotifTimestamp());
		
		adapterL = new CustomNotificationListAdapter(this, R.layout.news_notification_row, notificari.getNotificari());
		
		lView.setAdapter(adapterL);
	}
}
