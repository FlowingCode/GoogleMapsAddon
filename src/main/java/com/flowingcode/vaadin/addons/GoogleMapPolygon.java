package com.flowingcode.vaadin.addons;

import java.util.List;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.shared.Registration;

/**
 * A class representing a polygon overlay of Google Maps.
 */
@SuppressWarnings("serial")
@Tag("google-map-poly")
@JsModule("@flowingcode/google-map/google-map-poly.js")
@JsModule("@flowingcode/google-map/google-map-point.js")
@NpmPackage(value = "@flowingcode/google-map", version = "3.0.0")
public class GoogleMapPolygon extends Component {

	public enum StrokePosition {
		CENTER, INSIDE, OUTSIDE
	}

	public GoogleMapPolygon(List<GoogleMapPoint> points) {
		getElement().removeProperty("draggable");
		setClosed(true);
		setFillColor("blue");
		setFillOpacity(0.5d);
		setPoints(points);
	}

	public void setFillOpacity(double opacity) {
		this.getElement().setProperty("fillOpacity", opacity);
	}

	public double getFillOpacity() {
		return this.getElement().getProperty("fillOpacity", 1d);
	}

	public void setStrokeOpacity(double opacity) {
		this.getElement().setProperty("strokeOpacity", opacity);
	}

	public double getStrokeOpacity() {
		return this.getElement().getProperty("strokeOpacity", 1d);
	}

	public void setStrokePosition(StrokePosition position) {
		this.getElement().setProperty("strokePosition", position.name().toLowerCase());
	}

	public StrokePosition getStrokePosition() {
		return StrokePosition.valueOf(this.getElement().getProperty("strokePosition").toUpperCase());
	}

	public void setStrokeWeight(double weight) {
		this.getElement().setProperty("strokeWeight", weight);
	}

	public double getStrokeWeight() {
		return this.getElement().getProperty("strokeWeight", 1d);
	}

	public void setZIndex(double zindex) {
		this.getElement().setProperty("zIndex", zindex);
	}

	public double getZIndex() {
		return this.getElement().getProperty("zIndex", 1d);
	}

	public void setFillColor(String string) {
		this.getElement().setProperty("fillColor", string);
	}

	public String getFillColor() {
		return this.getElement().getProperty("fillColor");
	}

	public void setStrokeColor(String string) {
		this.getElement().setProperty("strokeColor", string);
	}

	public String getStrokeColor() {
		return this.getElement().getProperty("strokeColor");
	}

	public void setClosed(boolean b) {
		this.getElement().setProperty("closed", b);
	}

	public boolean isClosed() {
		return this.getElement().getProperty("closed", false);
	}

	public void setGeodesic(boolean b) {
		this.getElement().setProperty("geodesic", b);
	}

	public boolean isGeodesic() {
		return this.getElement().getProperty("geodesic", false);
	}

	public void setPoints(List<GoogleMapPoint> points) {
		points.forEach(point -> this.getElement().appendChild(point.getElement()));
	}

	public GoogleMapPoint addPoint(LatLon position) {
		GoogleMapPoint point = new GoogleMapPoint(position.getLat(), position.getLon());
		this.getElement().appendChild(point.getElement());
		return point;
	}

	public void addPoint(GoogleMapPoint point) {
		this.getElement().appendChild(point.getElement());
	}

	public void removePoint(GoogleMapPoint point) {
		this.getElement().removeChild(point.getElement());
	}

	@DomEvent("google-map-poly-click")
	public static class GoogleMapPolygonClickEvent extends ClickEvent<GoogleMapPolygon> {
		public GoogleMapPolygonClickEvent(GoogleMapPolygon source, boolean fromClient) {
			super(source);
		}
	}

	public Registration addClickListener(ComponentEventListener<GoogleMapPolygonClickEvent> listener) {
		this.getElement().setProperty("clickable", true);
		this.getElement().setProperty("clickEvents", true);
		return addListener(GoogleMapPolygonClickEvent.class, listener);
	}

}
