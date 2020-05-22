package com.flowingcode.vaadin.addons.googlemaps;

import java.io.Serializable;

/**
 * Class representing coordinates of a point.
 */
public class LatLon implements Serializable {
    private static final long serialVersionUID = 646346543243L;

    private double lat = 0.0;
    private double lon = 0.0;

    public LatLon() {
    }

    public LatLon(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

	@Override
	public String toString() {
		return "LatLon [lat=" + lat + ", lon=" + lon + "]";
	}
    
}
