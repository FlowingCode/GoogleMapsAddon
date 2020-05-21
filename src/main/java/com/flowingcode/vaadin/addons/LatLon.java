package com.flowingcode.vaadin.addons;

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(lat);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lon);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        LatLon other = (LatLon) obj;
        if (Double.doubleToLongBits(lat) != Double
            .doubleToLongBits(other.lat)) {
            return false;
        }
        if (Double.doubleToLongBits(lon) != Double
            .doubleToLongBits(other.lon)) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "LatLon [lat=" + lat + ", lon=" + lon + "]";
	}

    
    
}
