package com.zumba.claudiuapp.notification;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tabele.Notificare;
import tabele.NotificationType;

import com.zumba.claudiuapp.LoginActivity;
import com.zumba.claudiuapp.R;

import database.BDNotificari;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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
					adapterL.add(notif);
					createNewNotification(notif);
				}
				adapterL.notifyDataSetChanged();
			}
		});
		scheduler.scheduleAtFixedRate(checkForNotifications_, 30, 30, TimeUnit.SECONDS);
		
	}
	
	private void createNewNotification(Notificare notif) {
		NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this);
		nBuilder.setSmallIcon(R.drawable.zumba)
				.setContentTitle(notif.getTitlu())
				.setContentText(notif.getMesaj().subSequence(0, 25));
		
		Intent resultIntent = new Intent(this, NewNotifications.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(NotificationActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		nBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
			    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			// mId allows you to update the notification later on.
			mNotificationManager.notify(notif.getId(), nBuilder.build());
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
		List<Notificare> l = notificari.getNotificari();
		if (null == l) {
			l = new ArrayList<Notificare>();
		}
		l.add(new Notificare(1, new Timestamp(new Date().getTime()), "Ana", "Are mere multe si\nfrumoase dar sunt prea multe\nsi prea frumoase", NotificationType.INFO ));
		l.add(new Notificare(2, new Timestamp(new Date().getTime()), "Ana", "Are mere multe si\nfrumoase dar sunt prea multe\nsi prea frumoase", NotificationType.URGENT ));
		l.add(new Notificare(3, new Timestamp(new Date().getTime()), "Ana", "Are mere multe si\nfrumoase dar sunt prea multe\nsi prea frumoase", NotificationType.WARNING ));
		
		for (Notificare notif: l) {
			createNewNotification(notif);
		}
		
		adapterL = new CustomNotificationListAdapter(this, R.layout.news_notification_row, l);
		
		lView.setAdapter(adapterL);
	}
}