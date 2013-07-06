package com.gowtham.bangaloretraffic;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TrafficActivity extends Activity implements OnClickListener, OnItemClickListener {

	private ListView trafficImageListView;
	private TextView statusTextView;
	private Button refreshButton;
	
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
		
		
		updateList(getDummyData());
		
		
	}

	private List<Map<String, String>> getDummyData() {
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		Map<String,String> m1 = new HashMap<String, String>();
		m1.put(TrafficImageListAdapter.KEY_LOCATION, "KR Pura");
		Map<String,String> m2 = new HashMap<String, String>();
		m2.put(TrafficImageListAdapter.KEY_LOCATION, "HRBR Layout");
		Map<String,String> m3 = new HashMap<String, String>();
		m3.put(TrafficImageListAdapter.KEY_LOCATION, "Nandini Layout");
		Map<String,String> m4 = new HashMap<String, String>();
		m4.put(TrafficImageListAdapter.KEY_LOCATION, "Mahalakshmi Layout, Bangalore");
		
		data.add(m1);
		data.add(m2);
		data.add(m3);
		data.add(m4);
		return data;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.traffic, menu);
		return true;
	}

	private void updateList(List<Map<String,String>> trafficDataList) {
		TrafficImageListAdapter adapter = new TrafficImageListAdapter(this, trafficDataList);
		trafficImageListView.setAdapter(adapter);
	}
	
	private void startDownloadTrafficData() {
		try {
			String url = URLs.getLocationListURL(this);
			new TrafficDataDownloader(statusTextView).execute(new URL(url));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
}
