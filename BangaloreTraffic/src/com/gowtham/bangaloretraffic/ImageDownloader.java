/**
 * 
 */
package com.gowtham.bangaloretraffic;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * @author Gowtham
 *
 */
public class ImageDownloader extends AsyncTask<URL, Void, Bitmap> {

	private ImageView imageView;
	private Bitmap bitmap;
	
	public ImageDownloader(ImageView imageView) {
		this.imageView = imageView;
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
		imageView.setImageBitmap(bitmap);
	}

}
