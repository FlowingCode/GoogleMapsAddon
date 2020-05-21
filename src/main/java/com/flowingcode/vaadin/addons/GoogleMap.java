/*-
 * #%L
 * Google Maps Addon
 * %%
 * Copyright (C) 2020 Flowing Code
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.flowingcode.vaadin.addons;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

@SuppressWarnings("serial")
@Tag("google-map")
@JsModule("@flowingcode/google-map/google-map.js")
@NpmPackage(value = "@flowingcode/google-map", version = "3.0.0")
public class GoogleMap extends Component implements HasSize {

	/**
	 * Base map types supported by Google Maps.
	 */
	public enum MapType {
		ROADMAP, SATELLITE, HYBRID, TERRAIN
	}

	/**
	 * Initiates a new GoogleMap object with default settings from the
	 * {@link GoogleMapState state object}.
	 *
	 * @param apiKey   The Maps API key from Google. Not required when developing in
	 *                 localhost or when using a client id. Use null or empty string
	 *                 to disable.
	 * @param clientId Google Maps API for Work client ID. Use this instead of API
	 *                 key if available. Use null or empty string to disable.
	 * @param language The language to use with maps. See
	 *                 https://developers.google.com/maps/faq#languagesupport for
	 *                 the list of the supported languages. Use null or empty string
	 *                 to disable.
	 */
	public GoogleMap(String apiKey, String clientId, String language) {
		this.getElement().setAttribute("api-key", apiKey);
		if (!StringUtils.isEmpty(clientId)) {
			this.getElement().setAttribute("client-id", clientId);
		}
		if (!StringUtils.isEmpty(language)) {
			this.getElement().setAttribute("language", language);
		}
	}
	
	
	@Synchronize("google-map-idle")
	public Double getLatitude() {
		return this.getElement().getProperty("latitude",0d);
	}
	@Synchronize("google-map-idle")
	public Double getLongitude() {
		return this.getElement().getProperty("longitude",0d);
	}

	/**
	 * Sets the center of the map to the given coordinates.
	 *
	 * @param center The new coordinates of the center.
	 */
	public void setCenter(LatLon center) {
		this.getElement().setProperty("latitude", center.getLat());
		this.getElement().setProperty("longitude", center.getLon());
	}
	
	/**
	 * Returns the current position of the center of the map.
	 *
	 * @return Coordinates of the center.
	 */
	public LatLon getCenter() {
		final LatLon result = new LatLon();
		result.setLat(getLatitude());
		result.setLon(getLongitude());
		return result;
	}

	/**
	 * Zooms the map to the given value.
	 *
	 * @param zoom New amount of the zoom.
	 */
	public void setZoom(int zoom) {
		this.getElement().setAttribute("zoom", Integer.toString(zoom));
	}

	/**
	 * Returns the current zoom of the map.
	 *
	 * @return Current value of the zoom.
	 */
	@Synchronize("google-map-idle")
	public int getZoom() {
		return this.getElement().getProperty("zoom",0);
	}

	/**
	 * Adds a new marker to the map.
	 *
	 * @param caption   Caption of the marker shown when the marker is hovered.
	 * @param position  Coordinates of the marker on the map.
	 * @param draggable Set true to enable dragging of the marker.
	 * @param iconUrl   The url of the icon of the marker.
	 * @return GoogleMapMarker object created with the given settings.
	 */
	public GoogleMapMarker addMarker(String caption, LatLon position, boolean draggable, String iconUrl) {
		GoogleMapMarker gmm = new GoogleMapMarker(caption, position, draggable, iconUrl);
		this.getElement().appendChild(gmm.getElement());
		if (this.getElement().getParent()!=null) this.getElement().executeJs("this._updateMarkers()");
		return gmm;
	}
	
	public GoogleMapPolygon addPolygon(List<GoogleMapPoint> points) {
		GoogleMapPolygon polygon = new GoogleMapPolygon(points);
		this.getElement().appendChild(polygon.getElement());
		return polygon;
	}

    public void addPolygon(GoogleMapPolygon polygon) {
		this.getElement().appendChild(polygon.getElement());
    }
    
    public void removePolygon(GoogleMapPolygon polygon) {
    	this.getElement().removeChild(polygon.getElement());
    }

    /**
     * Adds a marker to the map.
     *
     * @param marker The marker to add.
     */
    public void addMarker(GoogleMapMarker marker) {
		this.getElement().appendChild(marker.getElement());
    }
    
    public void removeMarker(GoogleMapMarker marker) {
    	this.getElement().removeChild(marker.getElement());
    }

    /**
     * Sets the type of the base map.
     *
     * @param type The new MapType to use.
     */
    public void setMapType(MapType type) {
    	this.getElement().setProperty("mapType", type.name().toLowerCase());
    }

    /**
     * Returns the current type of the base map.
     *
     * @return The current MapType.
     */
    public MapType getMapType() {
    	return MapType.valueOf(this.getElement().getProperty("mapType").toUpperCase());
    }

    /**
     * Checks if the map is currently draggable.
     *
     * @return true, if the map draggable.
     */
    public boolean isDraggable() {
    	return this.getElement().getProperty("draggable", true);
    }

    /**
     * Enables/disables dragging of the map.
     *
     * @param draggable Set to true to enable dragging.
     */
    public void setDraggable(boolean draggable) {
    	this.getElement().setProperty("draggable", draggable);
    }

    /**
     * Sets the maximum allowed amount of zoom (default 21.0).
     *
     * @param maxZoom The maximum amount for zoom.
     */
    public void setMaxZoom(int maxZoom) {
    	this.getElement().setProperty("maxZoom", maxZoom);
    }

    /**
     * Returns the current maximum amount of zoom.
     *
     * @return maximum amount of zoom
     */
    public int getMaxZoom() {
    	return this.getElement().getProperty("maxZoom", 1);
    }

    /**
     * Sets the minimum allowed amount of zoom (default 0.0).
     *
     * @param minZoom The minimum amount for zoom.
     */
    public void setMinZoom(int minZoom) {
    	this.getElement().setProperty("minZoom", minZoom);
    }

    /**
     * Returns the current minimum amount of zoom.
     *
     * @return minimum amount of zoom
     */
    public int getMinZoom() {
    	return this.getElement().getProperty("minZoom", 1);
    }
    
    
    


}
