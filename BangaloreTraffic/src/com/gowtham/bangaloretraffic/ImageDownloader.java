/**
 * 
 */
package com.gowtham.bangaloretraffic;

import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * @author Gowtham
 *
 */
public class ImageDownloader extends AsyncTask<URL, Void, Bitmap> {

	private TrafficLocation location;
	private TrafficActivity activity;
	private int position;
	private Bitmap bitmap;
	
	public ImageDownloader(TrafficLocation location, TrafficActivity activity, int position) {
		this.location = location;
		this.activity = activity;
		this.position = position;
	}
	
	@Override
	protected Bitmap doInBackground(URL... urls) {
		URL url = urls[0];
		
		try {
			InputStream is = url.openStream();
			bitmap = BitmapFactory.decodeStream(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bitmap;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		location.setImage(bitmap);
		activity.updateImage(position, bitmap);
	}

}
