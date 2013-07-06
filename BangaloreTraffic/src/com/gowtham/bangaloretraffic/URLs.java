/**
 * 
 */
package com.gowtham.bangaloretraffic;

import android.app.Activity;

/**
 * @author Gowtham
 *
 */
public class URLs {
	
	public static String getLocationListURL(Activity activity) {
		return activity.getResources().getString(R.string.btis_url_refresh_camera);
	}
	
	public static String getTrafficImageURL(Activity activity, int id) {
		String url = activity.getResources().getString(R.string.btis_url_camera_image);
		url.replace("$ID$", String.valueOf(id));
		long epoch = System.currentTimeMillis() / 1000;
		url.replace("$TIME$", String.valueOf(epoch));
		return url;
	}

}
