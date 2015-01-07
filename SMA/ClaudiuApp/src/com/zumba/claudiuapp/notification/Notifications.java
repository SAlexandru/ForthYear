package com.zumba.claudiuapp.notification;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import tabele.Notificare;
import android.content.Context;
import android.content.SharedPreferences;

public class Notifications  {
	private static final String FILENAME = "OldNotificationsFile";
	
	private SharedPreferences file;
	private SortedSet<Notificare> oldNotif;
	
	private static class SingletonOldNotifications {
		public static final Notifications instance = new Notifications();
	}
	
	private Notifications () {
		oldNotif = new TreeSet<>(Collections.reverseOrder());
		
	}
	
	private synchronized Notifications setContenxt (Context context) {
		file = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		
		/*
		 *	should create a way for lazy initialization 
		 */
		for (Entry<String, ?> line: file.getAll().entrySet()) {
			try {
				oldNotif.add(new Notificare(line.getKey()));
			}
			catch (IllegalArgumentException e) {
				//someone has corrupted the file
				//assume that no values are valid
				file.edit().clear();
				file.edit().commit();
				oldNotif.clear();
				break;
			}
		}
		
		return this;
	}
	
	public static Notifications getInstance(Context context) {
		return SingletonOldNotifications.instance.setContenxt(context);
	}
	
	public int getNumberOfNotif() {return oldNotif.size();}
	
	public Notificare getOldestNotif() {return oldNotif.first();}
	public Notificare getRecentNotif() {return oldNotif.last();}

	public Iterator<Notificare> getAllOldNotifications() {return oldNotif.iterator();}
	
	
	
	

}
