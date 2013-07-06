package com.gowtham.bangaloretraffic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class TrafficActivity extends Activity {

	private ListView trafficImageListView;
	
	public TrafficActivity() {
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traffic);
		
		trafficImageListView = (ListView) findViewById(R.id.list_traffic);
		
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
		
		updateList(data);
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
}
