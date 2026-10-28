/*-
 * #%L
 * Google Maps Addon
 * %%
 * Copyright (C) 2020 - 2026 Flowing Code
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

import com.flowingcode.vaadin.jsonmigration.JsonMigration;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.shared.Registration;
import java.util.concurrent.atomic.AtomicLong;
import lombok.experimental.ExtensionMethod;

/**
 * A marker of the Google Map based on Google's Advanced Markers ({@code AdvancedMarkerElement}),
 * the replacement for the deprecated markers used by {@link GoogleMapMarker}. Both kinds of
 * markers can be used on the same map.
 *
 * <p>Advanced Markers require the map to have a Map ID (see {@link GoogleMap#setMapId(String)}),
 * set before the map is shown. Otherwise, an {@link IllegalStateException} is thrown when the
 * marker is attached.
 *
 * <p>These {@link GoogleMapMarker} features are not available, as Advanced Markers have no
 * equivalent: animation, optimization, icon sprites, a label together with an icon, and the font
 * and CSS class of a label.
 *
 * @since 2.7.0
 */
@SuppressWarnings("serial")
@Tag("google-map-advanced-marker")
@JsModule("@flowingcode/google-map/google-map-advanced-marker.js")
@ExtensionMethod(value = JsonMigration.class, suppressBaseMethods = true)
public class GoogleMapAdvancedMarker extends Component {

  private static final AtomicLong idCounter = new AtomicLong();

  private long id;

  /** Instantiates a new GoogleMapAdvancedMarker. */
  public GoogleMapAdvancedMarker() {
    id = idCounter.getAndIncrement();
    this.getElement().setAttribute("slot", "markers");
    // the position changes on the client when the marker is dragged
    this.getElement().addPropertyChangeListener("latitude", "latitude-changed", ev -> {});
    this.getElement().addPropertyChangeListener("longitude", "longitude-changed", ev -> {});
  }

  /**
   * Instantiates a new GoogleMapAdvancedMarker.
   *
   * @param caption The caption to use.
   * @param position The position of the marker
   * @param draggable Can marker be dragged?
   */
  public GoogleMapAdvancedMarker(String caption, LatLon position, boolean draggable) {
    this();
    this.setCaption(caption);
    this.setPosition(position);
    this.setDraggable(draggable);
  }

  /**
   * Instantiates a new GoogleMapAdvancedMarker.
   *
   * @param caption The caption to use.
   * @param position The position of the marker
   * @param draggable Can marker be dragged?
   * @param iconUrl the icon url as a string
   */
  public GoogleMapAdvancedMarker(String caption, LatLon position, boolean draggable,
      String iconUrl) {
    this(caption, position, draggable);
    this.setIconUrl(iconUrl);
  }

  /**
   * Instantiates a new GoogleMapAdvancedMarker.
   *
   * @param caption The caption to use.
   * @param position The position of the marker
   * @param draggable Can marker be dragged?
   * @param icon the icon image for the marker
   */
  public GoogleMapAdvancedMarker(String caption, LatLon position, boolean draggable,
      GoogleMapIcon icon) {
    this(caption, position, draggable);
    this.setIcon(icon);
  }

  /**
   * Sets the content of the info window that is shown when the marker is clicked.
   *
   * @param htmlContent the HTML content of the info window
   */
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
   * Sets the url of the icon of the marker, which is shown instead of the default pin.
   *
   * @param iconUrl The new url of the icon.
   */
  public void setIconUrl(String iconUrl) {
    this.getElement().setProperty("icon", iconUrl);
  }

  /**
   * Sets the icon image of the marker, which is shown instead of the default pin. A
   * {@link MarkerIcon} is shown with its url, scaled size and anchor; its size and origin are
   * ignored, as sprites are not supported. A {@link Symbol} is shown with its SVG path.
   *
   * @param icon the icon image of the marker, or null to show the default pin
   */
  public void setIcon(GoogleMapIcon icon) {
    if (icon == null) {
      this.getElement().setProperty("icon", (String) null);
    } else {
      this.getElement().setPropertyJson("icon", icon.getJson());
    }
  }

  /**
   * Sets the label of the marker, which is shown in the default pin. Only the text and the color
   * of the label are used: its font options and CSS class name are ignored. The label is not shown
   * when the marker has an icon.
   *
   * @param label the new marker's label, or null to remove it
   */
  public void setLabel(MarkerLabel label) {
    if (label == null) {
      this.getElement().setProperty("label", (String) null);
    } else {
      this.getElement().setPropertyJson("label", label.getJson());
    }
  }

  /**
   * Returns the background color of the default pin.
   *
   * @return the background color, or null if not set
   */
  public String getPinBackground() {
    return this.getElement().getProperty("pinBackground");
  }

  /**
   * Sets the background color of the default pin.
   *
   * @param color a CSS color
   */
  public void setPinBackground(String color) {
    this.getElement().setProperty("pinBackground", color);
  }

  /**
   * Returns the border color of the default pin.
   *
   * @return the border color, or null if not set
   */
  public String getPinBorderColor() {
    return this.getElement().getProperty("pinBorderColor");
  }

  /**
   * Sets the border color of the default pin.
   *
   * @param color a CSS color
   */
  public void setPinBorderColor(String color) {
    this.getElement().setProperty("pinBorderColor", color);
  }

  /**
   * Returns the color of the default pin's glyph.
   *
   * @return the glyph color, or null if not set
   */
  public String getPinGlyphColor() {
    return this.getElement().getProperty("pinGlyphColor");
  }

  /**
   * Sets the color of the default pin's glyph (the circle in its center).
   *
   * @param color a CSS color
   */
  public void setPinGlyphColor(String color) {
    this.getElement().setProperty("pinGlyphColor", color);
  }

  /**
   * Returns the scale of the default pin.
   *
   * @return the scale, 1 if not set
   */
  public double getPinScale() {
    return this.getElement().getProperty("pinScale", 1d);
  }

  /**
   * Sets the scale of the default pin.
   *
   * @param scale the scale, e.g. 1.5 for a pin 50% larger than the default
   */
  public void setPinScale(double scale) {
    this.getElement().setProperty("pinScale", scale);
  }

  /**
   * Checks that the map of this marker has a Map ID, which Advanced Markers require.
   *
   * @throws IllegalStateException if the map has no Map ID
   */
  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    getParent().filter(GoogleMap.class::isInstance).map(GoogleMap.class::cast)
        .ifPresent(GoogleMapAdvancedMarker::checkMapId);
  }

  // Without a Map ID the marker is silently not shown, and the Map ID of a map can't be changed
  // after the map is created, so this is reported as soon as possible.
  static void checkMapId(GoogleMap map) {
    if (map.getMapId() == null || map.getMapId().isEmpty()) {
      throw new IllegalStateException("Advanced markers require a Map ID. "
          + "Set it with GoogleMap.setMapId(...) before the map is shown.");
    }
  }

  public long getIdentifier() {
    return id;
  }

  public void setIdentifier(long id) {
    this.id = id;
  }

  /** Event that is called on marker's drag end. */
  @DomEvent("google-map-marker-dragend")
  public static class DragEndEvent extends ComponentEvent<GoogleMapAdvancedMarker> {

    private final double lat;
    private final double lon;

    /**
     * Creates a new event with the drag end coordinates.
     *
     * @param source the marker that was dragged
     * @param fromClient whether the event originated on the client side
     * @param lat the latitude of the drag end
     * @param lon the longitude of the drag end
     */
    public DragEndEvent(
        GoogleMapAdvancedMarker source,
        boolean fromClient,
        @EventData("event.detail.latLng.lat()") double lat,
        @EventData("event.detail.latLng.lng()") double lon) {
      super(source, fromClient);
      this.lat = lat;
      this.lon = lon;
    }

    public double getLatitude() {
      return this.lat;
    }

    public double getLongitude() {
      return this.lon;
    }
  }

  /**
   * Adds a DragEndEvent listener. The listener is called when a marker's drag ends.
   *
   * @param listener a drag end event listener
   * @return a handle that can be used for removing the listener
   */
  public Registration addDragEndEventListener(ComponentEventListener<DragEndEvent> listener) {
    this.getElement().setProperty("dragEvents", true);
    return addListener(DragEndEvent.class, listener);
  }

  /** Event called on marker's click. */
  @DomEvent("google-map-marker-click")
  public static class GoogleMapAdvancedMarkerClickEvent
      extends ClickEvent<GoogleMapAdvancedMarker> {

    private final double lat;
    private final double lon;

    /**
     * Creates a new event with the click data.
     *
     * @param source the marker that was clicked
     * @param fromClient whether the event originated on the client side
     * @param screenX the screen X coordinate of the click
     * @param screenY the screen Y coordinate of the click
     * @param clientX the client X coordinate of the click
     * @param clientY the client Y coordinate of the click
     * @param clickCount the click count
     * @param button the mouse button
     * @param ctrlKey whether Ctrl key was pressed
     * @param shiftKey whether Shift key was pressed
     * @param altKey whether Alt key was pressed
     * @param metaKey whether Meta key was pressed
     * @param lat the latitude of the marker
     * @param lon the longitude of the marker
     */
    public GoogleMapAdvancedMarkerClickEvent(GoogleMapAdvancedMarker source, boolean fromClient,
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
        @EventData("event.detail.latLng.lat()") double lat,
        @EventData("event.detail.latLng.lng()") double lon) {
      super(source, fromClient, screenX, screenY, clientX, clientY, clickCount, button, ctrlKey,
          shiftKey, altKey, metaKey);
      this.lat = lat;
      this.lon = lon;
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
   * @param listener a click event listener
   * @return a handle that can be used for removing the listener
   */
  public Registration addClickListener(
      ComponentEventListener<GoogleMapAdvancedMarkerClickEvent> listener) {
    this.getElement().setProperty("clickEvents", true);
    return addListener(GoogleMapAdvancedMarkerClickEvent.class, listener);
  }

  /**
   * Event called on marker's right click. On touch devices, a touch-and-hold on the marker fires
   * it too.
   */
  @DomEvent("google-map-marker-rightclick")
  public static class GoogleMapAdvancedMarkerRightClickEvent
      extends ClickEvent<GoogleMapAdvancedMarker> {

    private final double lat;
    private final double lon;

    /**
     * Creates a new event with the right click data.
     *
     * @param source the marker that was right clicked
     * @param fromClient whether the event originated on the client side
     * @param screenX the screen X coordinate of the click
     * @param screenY the screen Y coordinate of the click
     * @param clientX the client X coordinate of the click
     * @param clientY the client Y coordinate of the click
     * @param clickCount the click count
     * @param button the mouse button
     * @param ctrlKey whether Ctrl key was pressed
     * @param shiftKey whether Shift key was pressed
     * @param altKey whether Alt key was pressed
     * @param metaKey whether Meta key was pressed
     * @param lat the latitude of the marker
     * @param lon the longitude of the marker
     */
    public GoogleMapAdvancedMarkerRightClickEvent(GoogleMapAdvancedMarker source,
        boolean fromClient,
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
        @EventData("event.detail.latLng.lat()") double lat,
        @EventData("event.detail.latLng.lng()") double lon) {
      super(source, fromClient, screenX, screenY, clientX, clientY, clickCount, button, ctrlKey,
          shiftKey, altKey, metaKey);
      this.lat = lat;
      this.lon = lon;
    }

    public double getLatitude() {
      return this.lat;
    }

    public double getLongitude() {
      return this.lon;
    }
  }

  /**
   * Adds a right click event listener to a marker. On touch devices, a touch-and-hold on the
   * marker fires it too.
   *
   * @param listener a right click event listener
   * @return a handle for the listener
   */
  public Registration addRightClickListener(
      ComponentEventListener<GoogleMapAdvancedMarkerRightClickEvent> listener) {
    this.getElement().setProperty("clickEvents", true);
    return addListener(GoogleMapAdvancedMarkerRightClickEvent.class, listener);
  }

}
