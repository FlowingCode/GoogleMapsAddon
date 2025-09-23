/*-
 * #%L
 * Google Maps Addon
 * %%
 * Copyright (C) 2020 - 2024 Flowing Code
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

package com.flowingcode.vaadin.addons.googlemaps;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.shared.Registration;
import elemental.json.JsonObject;
import elemental.json.JsonValue;

/** The class representing a marker of the Google Map. */
@SuppressWarnings("serial")
@Tag("google-map-marker")
@JsModule("@flowingcode/google-map/google-map-marker.js")
@NpmPackage(value = "@flowingcode/google-map", version = "3.9.0")
@NpmPackage(value = "@googlemaps/markerclusterer", version = "2.0.8")
public class GoogleMapMarker extends Component {

  private static long idCounter = 0;

  private long id;

  private boolean animationEnabled = true;

  /** Instantiates a new GoogleMapMarker. */
  public GoogleMapMarker() {
    id = idCounter;
    idCounter++;
    this.getElement().setAttribute("slot", "markers");
  }

  /**
   * Instantiates a new GoogleMapMarker
   *
   * @param caption The caption to use.
   * @param position The position of the marker
   * @param draggable Can marker be dragged?
   */
  public GoogleMapMarker(String caption, LatLon position, boolean draggable) {
    this();
    this.setCaption(caption);
    this.setPosition(position);
    this.setDraggable(draggable);
  }

  /**
   * Instantiates a new GoogleMapMarker
   *
   * @param caption The caption to use.
   * @param position The position of the marker
   * @param draggable Can marker be dragged?
   * @param iconUrl the icon url as a string
   */
  public GoogleMapMarker(String caption, LatLon position, boolean draggable, String iconUrl) {
    this(caption, position, draggable);
    this.setIconUrl(iconUrl);
  }
  
  /**
   * Instantiates a new GoogleMapMarker
   *
   * @param caption The caption to use.
   * @param position The position of the marker
   * @param draggable Can marker be dragged?
   * @param icon the icon image for the marker 
   */
  public GoogleMapMarker(String caption, LatLon position, boolean draggable, GoogleMapIcon icon) {
    this(caption, position, draggable);
    this.setIcon(icon);
  }
  
  public void addInfoWindow(String htmlContent) {
    this.getElement().setProperty("innerHTML", htmlContent);
  }

  @Synchronize(
      value = {"google-map-marker-open", "google-map-marker-close"},
      property = "open")
  public boolean isInfoWindowVisible() {
    return this.getElement().getProperty("open", false);
  }

  public void setInfoWindowVisible(boolean visible) {
    this.getElement().setProperty("open", visible);
  }

  /**
   * Returns the position of the marker.
   *
   * @return The position of the marker.
   */
  public LatLon getPosition() {
    final LatLon latLon = new LatLon();
    latLon.setLat(this.getElement().getProperty("latitude", 0d));
    latLon.setLon(this.getElement().getProperty("longitude", 0d));
    return latLon;
  }

  /**
   * Sets the position of the marker.
   *
   * @param position The new position of the marker.
   */
  public void setPosition(LatLon position) {
    this.getElement().setProperty("latitude", position.getLat());
    this.getElement().setProperty("longitude", position.getLon());
  }

  /**
   * Gets the caption of the marker.
   *
   * @return The caption of the marker.
   */
  public String getCaption() {
    return this.getElement().getProperty("title");
  }

  /**
   * Sets the caption of the marker.
   *
   * @param caption The new caption of the marker.
   */
  public void setCaption(String caption) {
    this.getElement().setProperty("title", caption != null ? caption : "");
  }

  /**
   * Checks if the marker is draggable.
   *
   * @return true, if it is draggable
   */
  public boolean isDraggable() {
    return this.getElement().getProperty("draggable", false);
  }

  /**
   * Enables/disables dragging of the marker.
   *
   * @param draggable Set to true to enable dragging.
   */
  public void setDraggable(boolean draggable) {
    this.getElement().setProperty("draggable", draggable);
  }

  /**
   * Returns the url of the icon of the marker.
   *
   * @return the url of the icon, default null.
   */
  public String getIconUrl() {
    return this.getElement().getProperty("icon");
  }

  /**
   * Sets the url of the icon of the marker.
   *
   * @param iconUrl The new url of the icon.
   */
  public void setIconUrl(String iconUrl) {
    this.getElement().setProperty("icon", iconUrl);
  }
  
  /**
   * Sets the icon image of the marker
   * 
   * @param icon the icon image of the marker
   */
  public void setIcon(GoogleMapIcon icon) {
    this.getElement().setPropertyJson("icon", icon.getJson());
  }

  /**
   * Sets the label of the marker In order to set the label's position use
   * MarkerIcon::setLabelOrigin property.
   * 
   * @param label the new marker's label
   */
  public void setLabel(MarkerLabel label) {
    this.getElement().setPropertyJson("label", label.getJson());
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
    return this.getElement().getProperty("optimized", false);
  }

  /**
   * Enables/disables marker optimization. If enabled, many markers are rendered as a single static
   * element. Disable if you want to use animated GIFs or PNGs.
   *
   * @param optimized Set true to enable (default true).
   */
  public void setOptimized(boolean optimized) {
    this.getElement().setProperty("optimized", optimized);
  }

  public long getIdentifier() {
    return id;
  }

  public void setIdentifier(long id) {
    this.id = id;
  }

  /** Event that is called on marker's drag end. */
  @DomEvent("google-map-marker-dragend")
  public static class DragEndEvent extends ComponentEvent<GoogleMapMarker> {

    private final double lat;
    private final double lon;

    public DragEndEvent(
        GoogleMapMarker source,
        boolean fromClient,
        @EventData(value = "event.detail.latLng") JsonValue latLng) {
      super(source, fromClient);
      this.lat = ((JsonObject) latLng).getNumber("lat");
      this.lon = ((JsonObject) latLng).getNumber("lng");
    }

    public double getLatitude() {
      return this.lat;
    }

    public double getLongitude() {
      return this.lon;
    }
  }

  /**
   * Adds a DragEndEvent listener. The listener is called when a marker' drag ends.
   * 
   * @param listener
   * @return a handle that can be used for removing the listener
   */
  public Registration addDragEndEventListener(ComponentEventListener<DragEndEvent> listener) {
    this.getElement().setProperty("dragEvents", true);
    return addListener(DragEndEvent.class, listener);
  }
  
  /**
   * Event called on marker's click.
   */
  @DomEvent("google-map-marker-click")
  public static class GoogleMapMarkerClickEvent extends ClickEvent<GoogleMapMarker> {

    private final double lat;
    private final double lon;

    public GoogleMapMarkerClickEvent(GoogleMapMarker source, boolean fromClient,
        @EventData(value = "event.detail.latLng") JsonValue latLng) {
      super(source);
      this.lat = ((JsonObject) latLng).getNumber("lat");
      this.lon = ((JsonObject) latLng).getNumber("lng");
    }

    public double getLatitude() {
      return this.lat;
    }

    public double getLongitude() {
      return this.lon;
    }
  }

  /** 
   * Adds a click event listener to a marker.
   * 
   * @param listener
   * @return a handle that can be used for removing the listener
   */
  public Registration addClickListener(ComponentEventListener<GoogleMapMarkerClickEvent> listener) {
    this.getElement().setProperty("clickable", true);
    this.getElement().setProperty("clickEvents", true);
    return addListener(GoogleMapMarkerClickEvent.class, listener);
  }
  
  /**
   * Event called on marker's right click.
   */
  @DomEvent("google-map-marker-rightclick")
  public static class GoogleMapMarkerRightClickEvent extends ClickEvent<GoogleMapMarker> {

    private final double lat;
    private final double lon;

    public GoogleMapMarkerRightClickEvent(GoogleMapMarker source, boolean fromClient,
        @EventData("event.detail.domEvent.screenX") int screenX,
        @EventData("event.detail.domEvent.screenY") int screenY,
        @EventData("event.detail.domEvent.clientX") int clientX,
        @EventData("event.detail.domEvent.clientY") int clientY,
        @EventData("event.detail.domEvent.detail") int clickCount,
        @EventData("event.detail.domEvent.button") int button,
        @EventData("event.detail.domEvent.ctrlKey") boolean ctrlKey,
        @EventData("event.detail.domEvent.shiftKey") boolean shiftKey,
        @EventData("event.detail.domEvent.altKey") boolean altKey,
        @EventData("event.detail.domEvent.metaKey") boolean metaKey,
        @EventData(value = "event.detail.latLng") JsonValue latLng) {
      super(source, fromClient, screenX, screenY, clientX, clientY, clickCount, button, ctrlKey,
          shiftKey, altKey, metaKey);
      this.lat = ((JsonObject) latLng).getNumber("lat");
      this.lon = ((JsonObject) latLng).getNumber("lng");
    }

    public double getLatitude() {
      return this.lat;
    }

    public double getLongitude() {
      return this.lon;
    }
  }

  /**
   * Adds a right click event listener to a marker.
   * 
   * @param listener a right click event listener
   * @return a handle for the listener
   */
  public Registration addRightClickListener(
      ComponentEventListener<GoogleMapMarkerRightClickEvent> listener) {
    this.getElement().setProperty("clickable", true);
    this.getElement().setProperty("clickEvents", true);
    return addListener(GoogleMapMarkerRightClickEvent.class, listener);
  }

}
