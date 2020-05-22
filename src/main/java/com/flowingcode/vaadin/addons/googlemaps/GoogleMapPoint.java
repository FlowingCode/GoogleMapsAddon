package com.flowingcode.vaadin.addons.googlemaps;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

@SuppressWarnings("serial")
@Tag("google-map-point")
@JsModule("@flowingcode/google-map/google-map-point.js")
@NpmPackage(value = "@flowingcode/google-map", version = "3.0.0")
public class GoogleMapPoint extends Component {

	public GoogleMapPoint(LatLon latlon) {
		this(latlon.getLat(),latlon.getLon());
	}
	
	public GoogleMapPoint(Double latitude, Double longitude) {
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}
	
	public Double getLatitude() {
		return this.getElement().getProperty("latitude", 0d);
	}
	public void setLatitude(Double latitude) {
		this.getElement().setProperty("latitude", latitude);
	}
	public Double getLongitude() {
		return this.getElement().getProperty("longitude", 0d);
	}
	public void setLongitude(Double longitude) {
		this.getElement().setProperty("longitude", longitude);
	}
}
