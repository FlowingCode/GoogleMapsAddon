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
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.dom.DebouncePhase;
import com.vaadin.flow.dom.DomListenerRegistration;
import com.vaadin.flow.shared.Registration;
import elemental.json.JsonObject;
import elemental.json.JsonValue;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
@Tag("google-map")
@JsModule("@flowingcode/google-map/google-map.js")
@NpmPackage(value = "@flowingcode/google-map", version = "3.6.1")
@NpmPackage(value = "@googlemaps/markerclusterer", version = "2.0.8")
@JsModule("./googlemaps/geolocation.js")
public class GoogleMap extends Component implements HasSize {

  /** Base map types supported by Google Maps. */
  public enum MapType {
    ROADMAP,
    SATELLITE,
    HYBRID,
    TERRAIN
  }

  /**
   * Initiates a new GoogleMap object with default settings from the {@link GoogleMapState state
   * object}.
   *
   * @param apiKey The Maps API key from Google. Not required when developing in localhost or when
   *     using a client id. Use null or empty string to disable.
   * @param clientId Google Maps API for Work client ID. Use this instead of API key if available.
   *     Use null or empty string to disable.
   * @param language The language to use with maps. See
   *     https://developers.google.com/maps/faq#languagesupport for the list of the supported
   *     languages. Use null or empty string to disable.
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
    return this.getElement().getProperty("latitude", 0d);
  }

  @Synchronize("google-map-idle")
  public Double getLongitude() {
    return this.getElement().getProperty("longitude", 0d);
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
    return this.getElement().getProperty("zoom", 0);
  }

  /**
   * Adds a new marker to the map.
   *
   * @param caption Caption of the marker shown when the marker is hovered.
   * @param position Coordinates of the marker on the map.
   * @param draggable Set true to enable dragging of the marker.
   * @param iconUrl The url of the icon of the marker.
   * @return GoogleMapMarker object created with the given settings.
   */
  public GoogleMapMarker addMarker(
      String caption, LatLon position, boolean draggable, String iconUrl) {
    GoogleMapMarker gmm = new GoogleMapMarker(caption, position, draggable, iconUrl);
    addMarker(gmm);
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

  @SuppressWarnings("squid:S3242")
  public void removePolygon(GoogleMapPolygon polygon) {
    this.getElement().removeChild(polygon.getElement());
  }
  
  public GoogleMapPolyline addPolyline(List<GoogleMapPoint> points) {
    GoogleMapPolyline polyline = new GoogleMapPolyline(points);
    this.getElement().appendChild(polyline.getElement());
    return polyline;
  }

  public void addPolyline(GoogleMapPolyline polyline) {
    this.getElement().appendChild(polyline.getElement());
  }

  @SuppressWarnings("squid:S3242")
  public void removePolyline(GoogleMapPolyline polyline) {
    this.getElement().removeChild(polyline.getElement());
  }

  /**
   * Adds a marker to the map.
   *
   * @param marker The marker to add.
   */
  public void addMarker(GoogleMapMarker marker) {
    this.getElement().appendChild(marker.getElement());
    if (this.getElement().getParent() != null) {
      this.getElement().executeJs("this._updateMarkers()");
    }
  }

  @SuppressWarnings("squid:S3242")
  public void removeMarker(GoogleMapMarker marker) {
    this.getElement().removeChild(marker.getElement());
    // markers need to be updated on removal
    this.getElement().executeJs("this._updateMarkers()");
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

  /**
   * Allows to disable/enable rotate control.
   * 
   * @param disable Set true to disable rotate control
   */
  public void disableRotateControl(boolean disable) {
    this.getElement().setProperty("disableRotateControl", disable);
  }

  /**
   * Allows to disable/enable street view control.
   * 
   * @param disable Set true to disable street view control
   */
  public void disableStreetViewControl(boolean disable) {
    this.getElement().setProperty("disableStreetViewControl", disable);
  }

  /**
   * Allows to disable/enable map type control.
   * 
   * @param disable Set true to disable map type control
   */
  public void disableMapTypeControl(boolean disable) {
    this.getElement().setProperty("disableMapTypeControl", disable);
  }

  /**
   * Allows to disable/enable zoom control.
   * 
   * @param disable Set true to disable zoom control
   */
  public void disableZoomControl(boolean disable) {
    this.getElement().setProperty("disableZoomControl", disable);
  }

  /**
   * Allows to disable/enable full screen control.
   * 
   * @param disable Set true to disable full screen control
   */
  public void disableFullScreenControl(boolean disable) {
    this.getElement().setProperty("disableFullScreenControl", disable);
  }

  /**
   * Allows to disable/enable scale control.
   * 
   * @param disable Set true to disable scale control
   */
  public void disableScaleControl(boolean disable) {
    this.getElement().setProperty("disableScaleControl", disable);
  }

  /**
   * Sets the mapId. See
   * https://developers.google.com/maps/documentation/javascript/cloud-based-map-styling
   * 
   * @param mapId If set, the custom style associated with that map id is applied.
   */
  public void setMapId(String mapId) {
    this.getElement().setProperty("mapId", mapId);
  }

  /**
   * Returns the current map id.
   *
   * @return The current map id.
   */
  public String getMapId() {
    return this.getElement().getProperty("mapId");
  }
  
  /**
   * Sets the size of the control buttons appearing in the map. Must be specified when creating the
   * map. See
   * https://developers.google.com/maps/documentation/javascript/reference/map#MapOptions.controlSize
   * 
   * @param controlSize If set, control's size will be updated with this value.
   */
  public void setControlSize(int controlSize) {
    this.getElement().setProperty("controlSize", controlSize);
  }

  /**
   * Returns the current control size value.
   * 
   * @return The current size of the control buttons.
   */
  public int getControlSize() {
    return this.getElement().getProperty("controlSize", 0);
  }

  /**
   * Sets a KML or GeoRSS feed url to be displayed as a KML Layer in the map.
   * 
   * @param url to be displayed.
   */
  public void setKml(String kml){
    this.getElement().setProperty("kml", kml);
  }

  /**
   * Returns the current  KML or GeoRSS feed url associated with a KML Layer in the map.
   * 
   * @return the current url.
   */
  public String getKml() {
    return this.getElement().getProperty("kml");
  }
  
  /**
   * Enables markers clustering. 
   */
  public void enableMarkersClustering() {
    this.getElement().setProperty("enableMarkersClustering", true);
  }
    
  /**
   * Sets tilt value on a vector map.
   * 
   * @param tilt the value to set
   */
  public void setTilt(double tilt) {
    this.getElement().setProperty("tilt", tilt);
  }
  
  /**
   * Returns current tilt value.
   * 
   * @return tilt value
   */
  public double getTilt() {
    return this.getElement().getProperty("tilt", 45d);
  }
  
  /**
   * Sets heading (rotation) value on a vector map.
   * 
   * @param heading the value to set
   */
  public void setHeading(double heading) {
    this.getElement().setProperty("heading", heading);
  }
  
  /**
   * Returns the current heading value.
   * 
   * @return haeading value
   */
  public double getHeading() {
    return this.getElement().getProperty("heading", 0d);
  }
  
  public static class GoogleMapClickEvent extends ClickEvent<GoogleMap> {
    private final double lat;
    private final double lon;

    public double getLatitude() {
      return this.lat;
    }

    public double getLongitude() {
      return this.lon;
    }

    public GoogleMapClickEvent(
        GoogleMap source,
        boolean fromClient,
        @EventData(value = "event.detail.latLng") JsonValue latLng) {
      super(source);
      this.lat = ((JsonObject) latLng).getNumber("lat");
      this.lon = ((JsonObject) latLng).getNumber("lng");
    }
  }

  public Registration addClickListener(ComponentEventListener<GoogleMapClickEvent> listener) {
    this.getElement().setProperty("clickable", true);
    this.getElement().setProperty("clickEvents", true);
    DomListenerRegistration registration =
        this.getElement()
            .addEventListener(
                "google-map-click",
                ev -> {
                  JsonObject latLng = ev.getEventData().get("event.detail.latLng");
                  listener.onComponentEvent(new GoogleMapClickEvent(this, true, latLng));
                })
            .addEventData("event.detail.latLng");
    return registration::remove;
  }

  public Registration addRightClickListener(ComponentEventListener<GoogleMapClickEvent> listener) {
    this.getElement().setProperty("clickable", true);
    this.getElement().setProperty("clickEvents", true);
    DomListenerRegistration registration =
        this.getElement()
            .addEventListener(
                "google-map-rightclick",
                ev -> {
                  JsonObject latLng = ev.getEventData().get("event.detail.latLng");
                  listener.onComponentEvent(new GoogleMapClickEvent(this, true, latLng));
                })
            .addEventData("event.detail.latLng");
    return registration::remove;
  }

  /**
   * Sets current location on map.
   *
   * <p>Setting geolocation requires that the user gives consent to location sharing when prompted
   * by the browser.
   */
  public void goToCurrentLocation() {
    getElement().executeJs("geolocation.get($0)", this);
  }

  @ClientCallable
  private void handleGeolocation(double latitude, double longitude) {
    this.setCenter(new LatLon(latitude, longitude));
    ComponentUtil.fireEvent(this, new CurrentLocationEvent(this, false));
  }

  /**
   * Handles what to do if browser doesn't have permission to get the location or if browser doesn't
   * support geolocation.
   *
   * @param browserHasGeolocationSupport whether browser supports geolocation or not
   */
  @ClientCallable
  private void handleGeolocationError(boolean browserHasGeolocationSupport) {
    ComponentUtil.fireEvent(
        this, new GeolocationErrorEvent(this, false, browserHasGeolocationSupport));
  }

  /** Event that is fired when setting current location on map. */
  public class CurrentLocationEvent extends ComponentEvent<GoogleMap> {

    public CurrentLocationEvent(GoogleMap source, boolean fromClient) {
      super(source, fromClient);
    }
  }

  /** Event that is called when current location can't be found. */
  public class GeolocationErrorEvent extends ComponentEvent<GoogleMap> {

    private boolean browserHasGeolocationSupport;

    public GeolocationErrorEvent(
        GoogleMap source, boolean fromClient, boolean browserHasGeolocationSupport) {
      super(source, fromClient);
      this.browserHasGeolocationSupport = browserHasGeolocationSupport;
    }

    public boolean isBrowserHasGeolocationSupport() {
      return browserHasGeolocationSupport;
    }
  }

  /**
   * Adds a CurrentLocationEvent listener. The listener is called when setting the current location.
   *
   * @param listener
   * @return
   */
  public Registration addCurrentLocationEventListener(
      ComponentEventListener<CurrentLocationEvent> listener) {
    return addListener(CurrentLocationEvent.class, listener);
  }

  /**
   * Adds a GeolocationErrorEvent listener. The listener is called when current location can't be
   * found.
   *
   * @param listener
   * @return
   */
  public Registration addGeolocationErrorEventListener(
      ComponentEventListener<GeolocationErrorEvent> listener) {
    return addListener(GeolocationErrorEvent.class, listener);
  }
  
  /**
   * Returns a {@link CompletableFuture} containing the map current {@link LatLonBounds bounds}.
   * 
   * @return current {@link LatLonBounds bounds}
   */
  public CompletableFuture<LatLonBounds> getBounds() {    
    return this.getElement().executeJs("return this.map.getBounds()")
        .toCompletableFuture(JsonObject.class).thenApply(result -> {
          return new LatLonBounds(result);
        });
  }
  
  /**
   * Sets the custom renderer definition to be applied to the markers clustering. The custom
   * renderer needs to be define as a global JavaScript object conforming the Renderer interface
   * from
   * https://github.com/googlemaps/js-markerclusterer/blob/5ac92567dd0c52a1e1b897d791463a064656830c/src/renderer.ts#L65C2-L65C78
   * 
   * @param customRenderer the custom renderer definition
   */
  public void setClusteringRenderer(String customRenderer) {
    this.getElement().setProperty("customRenderer", customRenderer);
  }

  /**
   * Adds a GoogleMapIdleEvent listener. The listener is called when the map is in idle state (after
   * pan or zoom)
   * 
   * @param listener
   * @return
   */
  public Registration addMapIdleListener(ComponentEventListener<GoogleMapIdleEvent> listener) {
    DomListenerRegistration registration =
        this.getElement().addEventListener("google-map-idle", ev -> {
          listener.onComponentEvent(new GoogleMapIdleEvent(this, true));
        });
    return registration::remove;
  }

  public static class GoogleMapIdleEvent extends ComponentEvent<GoogleMap> {
    public GoogleMapIdleEvent(GoogleMap source, boolean fromClient) {
      super(source, true);
    }
  }

  /**
   * Adds a GoogleMapBoundsChangedEvent listener. The listener is called when the map is moved or
   * zoomed
   * 
   * @param listener
   * @return
   */
  public Registration addGoogleMapBoundsChangedListener(
      ComponentEventListener<GoogleMapBoundsChangedEvent> listener) {

    DomListenerRegistration registration =
        this.getElement().addEventListener("google-map-bounds_changed", ev -> {
          listener.onComponentEvent(new GoogleMapBoundsChangedEvent(this, true,
              new LatLonBounds(ev.getEventData().get("event.detail"))));
        }).debounce(1000, DebouncePhase.TRAILING).addEventData("event.detail");
    return registration::remove;
  }

  public static class GoogleMapBoundsChangedEvent extends ComponentEvent<GoogleMap> {

    private final LatLonBounds bounds;

    public GoogleMapBoundsChangedEvent(GoogleMap source, boolean fromClient,
        LatLonBounds latLonBounds) {
      super(source, true);
      this.bounds = latLonBounds;
    }

    public LatLonBounds getBounds() {
      return bounds;
    }
  }

}
