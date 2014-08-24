package com.gowtham.bangaloretraffic;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class TrafficActivity extends Activity implements 
					OnClickListener, OnItemClickListener, OnLongClickListener {
	private ListView trafficImageListView;
	private TextView statusTextView;
	private Button refreshButton;
	private List<TrafficLocation> trafficLocations;
	
	public TrafficActivity() {
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traffic);
		
		statusTextView = (TextView) findViewById(R.id.status_textview);
		refreshButton = (Button) findViewById(R.id.refresh_button);
		trafficImageListView = (ListView) findViewById(R.id.list_traffic);

		refreshButton.setOnClickListener(this);
		trafficImageListView.setOnItemClickListener(this);
		trafficImageListView.setOnLongClickListener(this);
		trafficImageListView.setLongClickable(true);
		
		//updateList(getDummyData());
	}

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.traffic, menu);
		return true;
	}

	public void updateList(List<TrafficLocation> trafficLocations) {
		trafficImageListView.setAdapter(null);
		TrafficImageListAdapter adapter = new TrafficImageListAdapter(this, trafficLocations);
		trafficImageListView.setAdapter(adapter);
	}
	
	public void updateList() {
		updateList(trafficLocations);
	}
	
	public void updateImage(int index, Bitmap bitmap) {
	    View v = trafficImageListView.getChildAt(index - trafficImageListView.getFirstVisiblePosition());
	    ImageView imageView = (ImageView) v.findViewById(R.id.traffic_image);
	    imageView.setImageBitmap(bitmap);
	}
	
	private void startDownloadTrafficData() {
		try {
			String url = URLs.getLocationListURL(this);
			AsyncTask<URL,String,List<TrafficLocation>> task = new TrafficDataDownloader(statusTextView, this).execute(new URL(url));
			trafficLocations = task.get();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch(ExecutionException e) {
			e.printStackTrace();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		
		if(refreshButton.getId() == id) {
			startDownloadTrafficData();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		TrafficLocation location = trafficLocations.get(position);
		String url = URLs.getTrafficImageURL(this, location.getId());
		Log.d("Image", url);
		try {
			new ImageDownloader(location, this, position).execute(new URL(url));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onLongClick(View v) {
		int id = v.getId();
        AlertDialog ad = new AlertDialog.Builder(this).create();
        ImageView imageView = (ImageView) v.findViewById(R.id.traffic_image);
        Bitmap bmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ad.setIcon(new BitmapDrawable(bmp));
        ad.show();
        return true;
	}
	
}
