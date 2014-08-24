package com.gowtham.bangaloretraffic;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

public class TrafficActivity extends Activity implements 
					OnClickListener, OnItemClickListener {
	private ListView trafficImageListView;
	private Button refreshButton, loadOnlyFavouritesButton;
	private List<TrafficLocation> trafficLocations;
    private Settings settings = Settings.getInstance();
	
	public TrafficActivity() {
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traffic);

        settings.setContext(this);

		refreshButton = (Button) findViewById(R.id.refresh_button);
        loadOnlyFavouritesButton = (Button) findViewById(R.id.load_favourites);
		trafficImageListView = (ListView) findViewById(R.id.list_traffic);

        registerForContextMenu(trafficImageListView);
		refreshButton.setOnClickListener(this);
        loadOnlyFavouritesButton.setOnClickListener(this);
		trafficImageListView.setOnItemClickListener(this);
		trafficImageListView.setLongClickable(true);
	}

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.traffic, menu);
		return true;
	}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId() == R.id.list_traffic) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.traffic_context_menu, menu);
        } else {
            super.onCreateContextMenu(menu, v, menuInfo);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TrafficLocation location = trafficLocations.get(info.position);

        switch (item.getItemId()) {
            case R.id.add_to_fav:
                settings.addAsFavourite(location.getName(), location.getId());
                return true;
            case R.id.remove_from_fav:
                settings.removeFromFavourite(location.getName());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void updateList(List<TrafficLocation> trafficLocations) {
		trafficImageListView.setAdapter(null);
		TrafficImageListAdapter adapter = new TrafficImageListAdapter(this, trafficLocations);
		trafficImageListView.setAdapter(adapter);
	}
	
	public void updateImage(int index, Bitmap bitmap) {
	    View v = trafficImageListView.getChildAt(index - trafficImageListView.getFirstVisiblePosition());
	    ImageView imageView = (ImageView) v.findViewById(R.id.traffic_image);
	    imageView.setImageBitmap(bitmap);
	}
	
	private void startDownloadTrafficData() {
		try {
			String url = URLs.getLocationListURL(this);
			AsyncTask<URL,String,List<TrafficLocation>> task = new TrafficDataDownloader(null, this).execute(new URL(url));
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
		} else if(loadOnlyFavouritesButton.getId() == id) {
            loadFavourites(settings.getAllFavourites());
        }
	}

    private void loadFavourites(Map<String, ?> allFavourites) {
        List<TrafficLocation> locations = new ArrayList<TrafficLocation>();
        TreeMap<String,?> sortedFavourites = new TreeMap<String, Object>(allFavourites);
        for(Map.Entry<String,?> e : sortedFavourites.entrySet()) {
            String name = e.getKey();
            Integer id = (Integer) e.getValue();
            TrafficLocation location = new TrafficLocation(name, id);
            locations.add(location);
        }
        updateList(locations);
    }

    @Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		TrafficLocation location = trafficLocations.get(position);
		String url = URLs.getTrafficImageURL(this, location.getId());
		Log.d("Image", url);
		try {
			new ImageDownloader(location, this, position).execute(new URL(url));
		} catch (MalformedURLException e) {
            Log.e(this.getClass().toString(), "Image download failed", e);
			e.printStackTrace();
		}
	}
}
