package com.zumba.claudiuapp.notification.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tabele.Notificare;
import tabele.NotificationType;

import com.zumba.claudiuapp.R;
import com.zumba.claudiuapp.notification.CustomNotificationListAdapter;

import database.BDNotificari;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SentNotifications extends Activity {
	private static ListView   lView;
	private static ArrayAdapter<Notificare> adapterL = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sent_notifications);
		
		
		BDNotificari notificari = new BDNotificari();
		
		lView = (ListView)findViewById(R.id.newSList);
		
		List<Notificare> l = notificari.getNotificari();
		if (null == l) {
			l = new ArrayList<Notificare>();
		}
		l.add(new Notificare(1, new Timestamp(new Date().getTime()), "Ana", "Are mere multe si\nfrumoase dar sunt prea multe\nsi prea frumoase", NotificationType.INFO ));
		l.add(new Notificare(2, new Timestamp(new Date().getTime()), "Ana", "Are mere multe si\nfrumoase dar sunt prea multe\nsi prea frumoase", NotificationType.URGENT ));
		l.add(new Notificare(3, new Timestamp(new Date().getTime()), "Ana", "Are mere multe si\nfrumoase dar sunt prea multe\nsi prea frumoase", NotificationType.WARNING ));
		
		adapterL = new CustomNotificationListAdapter(this, R.layout.news_notification_row, l);
		
		lView.setAdapter(adapterL);
		
	}
	
	public static void addNotification(Notificare notif) {
		if (null == adapterL) {
			return;
		}
		adapterL.add(notif);
		adapterL.notifyDataSetChanged();
	}
}
