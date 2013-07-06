/**
 * 
 */
package com.gowtham.bangaloretraffic;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

/**
 * @author Gowtham
 *
 */
public class Downloader {

	private URL url;
	
	public Downloader(URL url) {
		this.url = url;
	}
	
	public CharSequence download() throws IOException {
	    InputStream is = null;
	        
	    try {
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000);
	        conn.setConnectTimeout(15000);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        
	        // Starts the query
	        Log.d("Downloader", "Starting download: " + this.url);
	        conn.connect();
	        int response = conn.getResponseCode();
	        Log.d("Downloader", "The response is: " + response);
	        
	        is = conn.getInputStream();
	        return readIt(is);
	        
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}
	
	private CharSequence readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
	    Reader reader = null;
	    reader = new InputStreamReader(stream, "UTF-8");        
	    StringBuilder sb = new StringBuilder();
	    char[] buf = new char[1024];
	    int read;
	    do {
	    	read = reader.read(buf, 0, buf.length);
	    	if(read < 0)
	    		break;
	    	sb.append(buf);
	    	
	    }while(true);
	    
	    return sb;
	}
}
