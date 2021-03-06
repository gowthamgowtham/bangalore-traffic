package com.gowtham.bangaloretraffic;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TrafficImageListAdapter extends BaseAdapter {
	
	public static final String KEY_LOCATION = "location";
	public static final String KEY_IMAGE = "image";

	private LayoutInflater layoutInflater;
	private List<TrafficLocation> trafficLocations;
	private TrafficImageItem trafficImageItem;
	
	public TrafficImageListAdapter(Activity activity, List<TrafficLocation> trafficLocations) {
		this.trafficLocations = trafficLocations;
		layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return trafficLocations.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		if(view == null) {
			view = layoutInflater.inflate(R.layout.traffic_image_list_row, null);
			trafficImageItem = new TrafficImageItem();
			trafficImageItem.location = (TextView) view.findViewById(R.id.traffic_location);
			trafficImageItem.trafficImage = (ImageView) view.findViewById(R.id.traffic_image);
			view.setTag(trafficImageItem);
		} else {
			trafficImageItem = (TrafficImageItem) view.getTag();
		}
		
		TrafficLocation trafficLocation = trafficLocations.get(pos);
		trafficImageItem.location.setText(trafficLocation.getName());
		Bitmap bmp = trafficLocation.getImage();
		if(bmp==null)
			trafficImageItem.trafficImage.setImageResource(R.drawable.ic_launcher);
		else
			trafficImageItem.trafficImage.setImageBitmap(bmp);
		
		return view;
	}

}
