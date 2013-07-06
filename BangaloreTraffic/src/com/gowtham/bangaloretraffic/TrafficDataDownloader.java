package com.gowtham.bangaloretraffic;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class TrafficDataDownloader extends AsyncTask<URL, String, List<TrafficLocation>> {

	private Throwable error;
	private TextView progressStatus;
	private TrafficActivity activity;
	
	public TrafficDataDownloader(TextView progressStatus, TrafficActivity activity) {
		this.progressStatus = progressStatus;
		this.activity = activity;
	}
	
	@Override
	protected List<TrafficLocation> doInBackground(URL... urls) {
		
		List<TrafficLocation> result = new ArrayList<TrafficLocation>();
		error = null;
		CharSequence json = "";

		publishProgress("Downloading...");
		try {
			json = new Downloader(urls[0]).download();
		} catch(Throwable error) {
			this.error = error;
			return result;
		}
		
		if(isCancelled())
			return result;
		
		publishProgress("Parsing...");
		// Parse
		JSONArray jsonArray = null;
		try {
			CharSequence jsonString = extractJSON(json);
			jsonArray = new JSONArray(jsonString.toString());
			
			Log.d("Downloader", "Got " + jsonArray.length() + " locations");
			
			for(int i=0; i<jsonArray.length(); ++i) {
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				String name = jsonObj.optString("name", "Unknown");
				int id = jsonObj.getInt("id");
				double lat = jsonObj.optDouble("lat", 0.0);
				double lon = jsonObj.optDouble("lon", 0.0);
				TrafficLocation location = new TrafficLocation(name, id);
				location.setLattitude(lat);
				location.setLongitude(lon);
				
				result.add(location);
			}
			
		} catch (JSONException e) {
			this.error = e;
			return result;
		}
		
		publishProgress(result.size() + " locations found");

		Collections.sort(result);
		return result;
	}
	
	@Override
	protected void onProgressUpdate(String... values) {
		progressStatus.setText(values[0]);
	}
	
	private CharSequence extractJSON(CharSequence result) {
		int parenStart = 0;
		while(result.charAt(parenStart)!='(')
			parenStart++;
		
		int semicolon = parenStart;
		while(result.charAt(semicolon)!=';')
			semicolon++;
		
		return result.subSequence(parenStart+1, semicolon);
	}

	@Override
	protected void onPostExecute(List<TrafficLocation> trafficLocations) {
		activity.updateList(trafficLocations);
	}
	
	public Throwable getError() {
		return error;
	}
}
