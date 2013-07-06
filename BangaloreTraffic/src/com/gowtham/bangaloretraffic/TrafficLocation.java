/**
 * 
 */
package com.gowtham.bangaloretraffic;

import java.util.Comparator;

import android.graphics.Bitmap;

/**
 * @author Gowtham
 *
 */
public class TrafficLocation implements Comparable<TrafficLocation> {
	private String name;
	private double lattitude, longitude;
	private int id;
	private Bitmap image;
	
	public TrafficLocation(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getLattitude() {
		return lattitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}
	
	public Bitmap getImage() {
		return image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrafficLocation other = (TrafficLocation) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(TrafficLocation another) {
		return name.compareTo(another.name);
	}
	
}
