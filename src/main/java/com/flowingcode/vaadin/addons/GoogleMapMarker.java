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

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

/**
 * The class representing a marker of the Google Map.
 */
@SuppressWarnings("serial")
@Tag("google-map-marker")
@JsModule("@flowingcode/google-map/google-map-marker.js")
@NpmPackage(value = "@flowingcode/google-map", version = "3.0.0")
public class GoogleMapMarker extends Component {

    private static long idCounter = 0;

    private long id;

    private LatLon position = new LatLon(0, 0);

    private String caption = "";

    private boolean draggable = false;

    private String iconUrl = null;

    private boolean animationEnabled = true;

    private boolean optimized = true;

    /**
     * Instantiates a new GoogleMapMarker.
     */
    public GoogleMapMarker() {
        id = idCounter;
        idCounter++;
    }

    /**
     * Instantiates a new GoogleMapMarker
     *
     * @param caption   The caption to use.
     * @param position  The position of the marker
     * @param draggable Can marker be dragged?
     */
    public GoogleMapMarker(String caption, LatLon position, boolean draggable) {
        this();
        this.caption = caption;
        this.position = position;
        this.draggable = draggable;
        this.getElement().setAttribute("slot", "markers");
    }

    /**
     * Instantiates a new GoogleMapMarker
     *
     * @param caption   The caption to use.
     * @param position  The position of the marker
     * @param draggable Can marker be dragged?
     */
    public GoogleMapMarker(String caption, LatLon position, boolean draggable,
        String iconUrl) {
        this(caption, position, draggable);
        this.iconUrl = iconUrl;
        this.initialize();
    }

    private void initialize() {
		this.getElement().setAttribute("title", caption);
		this.getElement().setAttribute("latitude", ""+position.getLat());
		this.getElement().setAttribute("longitude", ""+position.getLon());
		this.getElement().setAttribute("draggable", draggable);
	}

	/**
     * Returns the position of the marker.
     *
     * @return The position of the marker.
     */
    public LatLon getPosition() {
        return position;
    }

    /**
     * Sets the position of the marker.
     *
     * @param position The new position of the marker.
     */
    public void setPosition(LatLon position) {
        this.position = position;
    }

    /**
     * Gets the caption of the marker.
     *
     * @return The caption of the marker.
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Sets the caption of the marker.
     *
     * @param caption The new caption of the marker.
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * Checks if the marker is draggable.
     *
     * @return true, if it is draggable
     */
    public boolean isDraggable() {
        return draggable;
    }

    /**
     * Enables/disables dragging of the marker.
     *
     * @param draggable Set to true to enable dragging.
     */
    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    /**
     * Returns the url of the icon of the marker.
     *
     * @return the url of the icon, default null.
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * Sets the url of the icon of the marker.
     *
     * @param iconUrl The new url of the icon.
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * Checks if marker animation is enabled.
     *
     * @return true, if enabled
     */
    public boolean isAnimationEnabled() {
        return animationEnabled;
    }

    /**
     * Enables/disables marker animation.
     *
     * @param animationEnabled Set true to enable (default true).
     */
    public void setAnimationEnabled(boolean animationEnabled) {
        this.animationEnabled = animationEnabled;
    }

    /**
     * Checks if optimization is enabled.
     *
     * @return true, if enabled
     */
    public boolean isOptimized() {
        return optimized;
    }

    /**
     * Enables/disables marker optimization. If enabled, many markers are
     * rendered as a single static element. Disable if you want to use animated
     * GIFs or PNGs.
     *
     * @param optimized Set true to enable (default true).
     */
    public void setOptimized(boolean optimized) {
        this.optimized = optimized;
    }

    public long getIdentifier() {
        return id;
    }

    public void setIdentifier(long id) {
        this.id = id;
    }

}
