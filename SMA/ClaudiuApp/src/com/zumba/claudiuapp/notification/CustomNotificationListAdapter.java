package com.zumba.claudiuapp.notification;

import java.util.List;

import tabele.Notificare;

import com.zumba.claudiuapp.R;
import com.zumba.claudiuapp.utils.ExpandableTextView;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomNotificationListAdapter extends ArrayAdapter<Notificare> {
	private Context context_;
	private int layoutResourceId_;
	private List<Notificare> data_;
	
	public CustomNotificationListAdapter(Context context, int layoutResourceId, List<Notificare> data) {
		super(context, layoutResourceId, data);
		
		context_ = context;
		layoutResourceId_ = layoutResourceId;
		data_ = data;
	}
	
	@Override
	public Notificare getItem(int position) {
		return data_.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		
		if (null == row) {
			LayoutInflater inflater = ((Activity)context_).getLayoutInflater();
			row = inflater.inflate(layoutResourceId_, parent, false);
			
			TextView titleView = (TextView)row.findViewById(R.id.titleLine);
			ExpandableTextView msgView   = (ExpandableTextView)row.findViewById(R.id.msgLine);
			ImageView imgView  = (ImageView)row.findViewById(R.id.icon);
			
			Notificare msg = getItem(position);
			titleView.setText(msg.getTitlu());
			msgView.setText(msg.getMesaj());
			
			Log.d("notification type", msg.getNotificationType().toString());
			switch(msg.getNotificationType()) {
				case INFO:
					Log.d("notification type", "R.drawable.info");
					imgView.setImageResource(R.drawable.info); 
					break;
				case URGENT:
					Log.d("notification type", "R.drawable.urgent");
					imgView.setImageResource(R.drawable.urgent);
					break;
				case WARNING:
					Log.d("notification type", "R.drawable.warning");
					imgView.setImageResource(R.drawable.warning);
					break;
			}
		}
		
		return row;
	}
}