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

import com.flowingcode.vaadin.addons.googlemaps.maptypestyle.ElementType;
import com.flowingcode.vaadin.addons.googlemaps.maptypestyle.FeatureType;
import com.flowingcode.vaadin.addons.googlemaps.maptypestyle.MapStyle;
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
import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
@Tag("google-map")
@JsModule("@flowingcode/google-map/google-map.js")
@NpmPackage(value = "@flowingcode/google-map", version = "3.8.1")
@NpmPackage(value = "@googlemaps/markerclusterer", version = "2.0.8")
@JsModule("./googlemaps/geolocation.js")
public class GoogleMap extends Component implements HasSize {

  private Integer trackLocationId = null;

  private List<CustomControl> customControls = new ArrayList<>();

  /** Base map types supported by Google Maps. */
  public enum MapType {
    ROADMAP,
    SATELLITE,
    HYBRID,
    TERRAIN
  }

  /**
   * Initiates a new GoogleMap object.
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
    getElement().setAttribute("api-key", apiKey);
    if (!StringUtils.isEmpty(clientId)) {
      getElement().setAttribute("client-id", clientId);
    }
    if (!StringUtils.isEmpty(language)) {
      getElement().setAttribute("language", language);
    }
  }

  @Synchronize("google-map-idle")
  public Double getLatitude() {
    return getElement().getProperty("latitude", 0d);
  }

  @Synchronize("google-map-idle")
  public Double getLongitude() {
    return getElement().getProperty("longitude", 0d);
  }

  /**
   * Sets the center of the map to the given coordinates.
   *
   * @param center The new coordinates of the center.
   */
  public void setCenter(LatLon center) {
    getElement().setProperty("latitude", center.getLat());
    getElement().setProperty("longitude", center.getLon());
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
    getElement().setProperty("zoom", zoom);
  }

  /**
   * Returns the current zoom of the map.
   *
   * @return Current value of the zoom.
   */
  @Synchronize("google-map-idle")
  public int getZoom() {
    return getElement().getProperty("zoom", 0);
  }

  /**
   * Adds a new marker to the map.
   *
   * @param caption Caption of the marker shown when the marker is hovered.
   * @param position Coordinates of the marker on the map.
   * @param draggable Set true to enable dragging of the marker.
   * @param iconUrl The url of the icon of the marker.
   *
   * @return GoogleMapMarker object created with the given settings.
   */
  public GoogleMapMarker addMarker(
      String caption, LatLon position, boolean draggable, String iconUrl) {
    GoogleMapMarker gmm = new GoogleMapMarker(caption, position, draggable, iconUrl);
    addMarker(gmm);
    return gmm;
  }

  /**
   * Adds a new marker to the map.
   *
   * @param caption Caption of the marker shown when the marker is hovered.
   * @param position Coordinates of the marker on the map.
   * @param draggable Set true to enable dragging of the marker.
   * @param icon the icon image of the marker.
   *
   * @return GoogleMapMarker object created with the given settings.
   */
  public GoogleMapMarker addMarker(
      String caption, LatLon position, boolean draggable, GoogleMapIcon icon) {
    GoogleMapMarker gmm = new GoogleMapMarker(caption, position, draggable, icon);
    addMarker(gmm);
    return gmm;
  }

  /**
   * Creates a polygon with the given points and adds the new polygon to the map.
   *
   * @param points the points of the polygon
   *
   * @return the new created polygon
   */
  public GoogleMapPolygon addPolygon(List<GoogleMapPoint> points) {
    GoogleMapPolygon polygon = new GoogleMapPolygon(points);
    addPolygon(polygon);
    return polygon;
  }

  /**
   * Adds a new polygon to the map.
   *
   * @param polygon the polygon to be added to the map
   */
  public void addPolygon(GoogleMapPolygon polygon) {
    getElement().appendChild(polygon.getElement());
    if (getElement().getParent() != null) {
      getElement().executeJs("this._updateObjects()");
    }
  }

  /**
   * Removes a polygon from the map.
   *
   * @param polygon the polygon to be removed from the map
   */
  @SuppressWarnings("squid:S3242")
  public void removePolygon(GoogleMapPolygon polygon) {
    getElement().removeChild(polygon.getElement());
    getElement().executeJs("this._updateObjects()");
  }

  /**
   * Creates a polyline with the given points and adds the new polyline to the map.
   *
   * @param points the points of the polyline
   *
   * @return the new created polyline
   */
  public GoogleMapPolyline addPolyline(List<GoogleMapPoint> points) {
    GoogleMapPolyline polyline = new GoogleMapPolyline(points);
    addPolyline(polyline);
    return polyline;
  }

  /**
   * Adds a new polyline to the map.
   *
   * @param polyline the polyline to be added to the map
   */
  public void addPolyline(GoogleMapPolyline polyline) {
    getElement().appendChild(polyline.getElement());
    if (getElement().getParent() != null) {
      getElement().executeJs("this._updateObjects()");
    }
  }

  /**
   * Removes a polyline from the map.
   *
   * @param polyline the polyline to be removed from the map
   */
  @SuppressWarnings("squid:S3242")
  public void removePolyline(GoogleMapPolyline polyline) {
    getElement().removeChild(polyline.getElement());
    getElement().executeJs("this._updateObjects()");
  }

  /**
   * Adds a marker to the map.
   *
   * @param marker The marker to add.
   */
  public void addMarker(GoogleMapMarker marker) {
    getElement().appendChild(marker.getElement());
    if (getElement().getParent() != null) {
      getElement().executeJs("this._updateMarkers()");
    }
  }

  @SuppressWarnings("squid:S3242")
  public void removeMarker(GoogleMapMarker marker) {
    getElement().removeChild(marker.getElement());
    // markers need to be updated on removal
    getElement().executeJs("this._updateMarkers()");
  }

  /**
   * Sets the type of the base map.
   *
   * @param type The new MapType to use.
   */
  public void setMapType(MapType type) {
    getElement().setProperty("mapType", type.name().toLowerCase());
  }

  /**
   * Returns the current type of the base map.
   *
   * @return The current MapType.
   */
  public MapType getMapType() {
    return MapType.valueOf(getElement().getProperty("mapType").toUpperCase());
  }

  /**
   * Checks if the map is currently draggable.
   *
   * @return true, if the map draggable.
   */
  public boolean isDraggable() {
    return getElement().getProperty("draggable", true);
  }

  /**
   * Enables/disables dragging of the map.
   *
   * @param draggable Set to true to enable dragging.
   */
  public void setDraggable(boolean draggable) {
    getElement().setProperty("draggable", draggable);
  }

  /**
   * Sets the maximum allowed amount of zoom (default 21.0).
   *
   * @param maxZoom The maximum amount for zoom.
   */
  public void setMaxZoom(int maxZoom) {
    getElement().setProperty("maxZoom", maxZoom);
  }

  /**
   * Returns the current maximum amount of zoom.
   *
   * @return maximum amount of zoom
   */
  public int getMaxZoom() {
    return getElement().getProperty("maxZoom", 1);
  }

  /**
   * Sets the minimum allowed amount of zoom (default 0.0).
   *
   * @param minZoom The minimum amount for zoom.
   */
  public void setMinZoom(int minZoom) {
    getElement().setProperty("minZoom", minZoom);
  }

  /**
   * Returns the current minimum amount of zoom.
   *
   * @return minimum amount of zoom
   */
  public int getMinZoom() {
    return getElement().getProperty("minZoom", 1);
  }

  /**
   * Allows to disable/enable rotate control.
   *
   * @param disable Set true to disable rotate control
   */
  public void disableRotateControl(boolean disable) {
    getElement().setProperty("disableRotateControl", disable);
  }

  /**
   * Allows to disable/enable street view control.
   *
   * @param disable Set true to disable street view control
   */
  public void disableStreetViewControl(boolean disable) {
    getElement().setProperty("disableStreetViewControl", disable);
  }

  /**
   * Allows to disable/enable map type control.
   *
   * @param disable Set true to disable map type control
   */
  public void disableMapTypeControl(boolean disable) {
    getElement().setProperty("disableMapTypeControl", disable);
  }

  /**
   * Allows to disable/enable zoom control.
   *
   * @param disable Set true to disable zoom control
   */
  public void disableZoomControl(boolean disable) {
    getElement().setProperty("disableZoomControl", disable);
  }

  /**
   * Allows to disable/enable full screen control.
   *
   * @param disable Set true to disable full screen control
   */
  public void disableFullScreenControl(boolean disable) {
    getElement().setProperty("disableFullScreenControl", disable);
  }

  /**
   * Allows to disable/enable scale control.
   *
   * @param disable Set true to disable scale control
   */
  public void disableScaleControl(boolean disable) {
    getElement().setProperty("disableScaleControl", disable);
  }

  /**
   * Sets the mapId. See
   * https://developers.google.com/maps/documentation/javascript/cloud-based-map-styling
   *
   * @param mapId If set, the custom style associated with that map id is applied.
   */
  public void setMapId(String mapId) {
    getElement().setProperty("mapId", mapId);
  }

  /**
   * Returns the current map id.
   *
   * @return The current map id.
   */
  public String getMapId() {
    return getElement().getProperty("mapId");
  }

  /**
   * Sets the size of the control buttons appearing in the map. Must be specified when creating the
   * map. See
   * https://developers.google.com/maps/documentation/javascript/reference/map#MapOptions.controlSize
   *
   * @param controlSize If set, control's size will be updated with this value.
   */
  public void setControlSize(int controlSize) {
    getElement().setProperty("controlSize", controlSize);
  }

  /**
   * Returns the current control size value.
   *
   * @return The current size of the control buttons.
   */
  public int getControlSize() {
    return getElement().getProperty("controlSize", 0);
  }

  /**
   * Sets a KML or GeoRSS feed url to be displayed as a KML Layer in the map.
   *
   * @param kml the url to be displayed.
   */
  public void setKml(String kml){
    getElement().setProperty("kml", kml);
  }

  /**
   * Returns the current  KML or GeoRSS feed url associated with a KML Layer in the map.
   *
   * @return the current url.
   */
  public String getKml() {
    return getElement().getProperty("kml");
  }

  /**
   * Enables markers clustering.
   */
  public void enableMarkersClustering() {
    getElement().setProperty("enableMarkersClustering", true);
  }

  /**
   * Sets tilt value on a vector map.
   *
   * @param tilt the value to set
   */
  public void setTilt(double tilt) {
    getElement().setProperty("tilt", tilt);
  }

  /**
   * Returns current tilt value.
   *
   * @return tilt value
   */
  public double getTilt() {
    return getElement().getProperty("tilt", 45d);
  }

  /**
   * Sets heading (rotation) value on a vector map.
   *
   * @param heading the value to set
   */
  public void setHeading(double heading) {
    getElement().setProperty("heading", heading);
  }

  /**
   * Returns the current heading value.
   *
   * @return haeading value
   */
  public double getHeading() {
    return getElement().getProperty("heading", 0d);
  }

  public static class GoogleMapClickEvent extends ClickEvent<GoogleMap> {
    private final double lat;
    private final double lon;

    public double getLatitude() {
      return lat;
    }

    public double getLongitude() {
      return lon;
    }

    public GoogleMapClickEvent(
        GoogleMap source,
        boolean fromClient,
        @EventData(value = "event.detail.latLng") JsonValue latLng) {
      super(source);
      lat = ((JsonObject) latLng).getNumber("lat");
      lon = ((JsonObject) latLng).getNumber("lng");
    }
  }

  public Registration addClickListener(ComponentEventListener<GoogleMapClickEvent> listener) {
    getElement().setProperty("clickable", true);
    getElement().setProperty("clickEvents", true);
    DomListenerRegistration registration =
        getElement()
            .addEventListener("google-map-click", ev -> {
              JsonObject latLng = ev.getEventData().get("event.detail.latLng");
              listener.onComponentEvent(new GoogleMapClickEvent(this, true, latLng));
            }).addEventData("event.detail.latLng");
    return registration::remove;
  }

  public Registration addRightClickListener(ComponentEventListener<GoogleMapClickEvent> listener) {
    getElement().setProperty("clickable", true);
    getElement().setProperty("clickEvents", true);
    DomListenerRegistration registration =
        getElement()
            .addEventListener("google-map-rightclick", ev -> {
              JsonObject latLng = ev.getEventData().get("event.detail.latLng");
              listener.onComponentEvent(new GoogleMapClickEvent(this, true, latLng));
            }).addEventData("event.detail.latLng");
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
    setCenter(new LatLon(latitude, longitude));
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
   * @return a handle that can be used for removing the listener
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
   * @return a handle that can be used for removing the listener
   */
  public Registration addGeolocationErrorEventListener(
      ComponentEventListener<GeolocationErrorEvent> listener) {
    return addListener(GeolocationErrorEvent.class, listener);
  }

  /**
   * Activates tracking current location on map.
   *
   * <p>Uses <a href=
   * "https://developer.mozilla.org/en-US/docs/Web/API/Geolocation/watchPosition">geolocation#watchPosition</a>
   * method to track current position.</p>
   *
   * <p>Geolocation requires that the user gives consent to location sharing when prompted by the
   * browser.</p>
   *
   * @throws IllegalStateException if a tracking location session is already active.
   *         The current session must be stopped before starting a new one.
   */
  public void trackLocation() {
    if (getTrackLocationId() == null) {
      getElement().executeJs("return geolocation.trackLocation($0)", this).then(Integer.class,
          trackLocationId -> {
            this.trackLocationId = trackLocationId;
            ComponentUtil.fireEvent(this, new LocationTrackingActivatedEvent(this, false));
          });
    } else {
      throw new IllegalStateException(
          "A tracking location session is already active. Please stop the current session before starting a new one.");
    }
  }

  /**
   * Returns track location id if a location tracking was activated.
   *
   * @return the location tracking id
   */
  public Integer getTrackLocationId() {
    return trackLocationId;
  }

  /** Event that is fired when activating location tracking. */
  public class LocationTrackingActivatedEvent extends ComponentEvent<GoogleMap> {

    public LocationTrackingActivatedEvent(GoogleMap source, boolean fromClient) {
      super(source, fromClient);
    }
  }

  /**
   * Adds a LocationTrackingActivatedEvent listener. The listener is called when setting activating
   * tracking location.
   *
   * @param listener a listener for a LocationTrackingActivatedEvent
   * @return a handle for the listener
   */
  public Registration addLocationTrackingActivatedEventListener(
      ComponentEventListener<LocationTrackingActivatedEvent> listener) {
    return addListener(LocationTrackingActivatedEvent.class, listener);
  }

  /**
   * Stops the current location tracking session.
   */
  public void stopTrackLocation() {
    if(trackLocationId != null) {
      getElement().executeJs("geolocation.clearTracking($0)", trackLocationId);
      trackLocationId = null;
    }
  }

  /**
   * Returns a {@link CompletableFuture} containing the map current {@link LatLonBounds bounds}.
   *
   * @return current {@link LatLonBounds bounds}
   */
  public CompletableFuture<LatLonBounds> getBounds() {
    return getElement().executeJs("return this.map.getBounds()")
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
    getElement().setProperty("customRenderer", customRenderer);
  }

  /**
   * Adds a GoogleMapIdleEvent listener. The listener is called when the map is in idle state (after
   * pan or zoom)
   *
   * @param listener
   * @return a handle that can be used for removing the listener
   */
  public Registration addMapIdleListener(ComponentEventListener<GoogleMapIdleEvent> listener) {
    DomListenerRegistration registration =
        getElement().addEventListener("google-map-idle", ev -> {
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
   * @return a handle that can be used for removing the listener
   */
  public Registration addGoogleMapBoundsChangedListener(
      ComponentEventListener<GoogleMapBoundsChangedEvent> listener) {

    DomListenerRegistration registration =
        getElement().addEventListener("google-map-bounds_changed", ev -> {
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
      bounds = latLonBounds;
    }

    public LatLonBounds getBounds() {
      return bounds;
    }
  }

  /**
   * Adds custom control buttons to the map.
   *
   * @param customControls list of custom controls to add to the map
   *
   * @deprecated {@link #setCustomControls(CustomControl... customControls)} should be used instead.
   */
  @Deprecated
  public void addCustomControls(CustomControl... customControls) {
    JsonArray jsonArray = Json.createArray();
    for (int i = 0; i < customControls.length; i++) {
      CustomControl customControl = customControls[i];
      jsonArray.set(i, customControl.getJson(i));
      customControl.getControlButton().getElement().setAttribute("slot", "customControlSlot_" + i);
      getElement().appendChild(customControl.getControlButton().getElement());
    }
    getElement().setPropertyJson("customControls", jsonArray);
  }

  /**
   * Sets the custom control buttons to be displayed in the map.
   *
   * @param customControls list of custom controls to add to the map
   */
  public void setCustomControls(CustomControl... customControls) {
    this.customControls.clear();
    getElement().executeJs("this._removeCustomControls()").then((e) -> {
      JsonArray jsonArray = Json.createArray();
      for (int i = 0; i < customControls.length; i++) {
        CustomControl customControl = customControls[i];
        jsonArray.set(i, customControl.getJson(i));
        customControl.getControlButton().getElement().setAttribute("slot", "customControlSlot_" + i);
        getElement().appendChild(customControl.getControlButton().getElement());
        this.customControls.add(customControl);
      }
      getElement().setPropertyJson("customControls", jsonArray);
    });
  }

  /**
   * Adds a custom control to be displayed in the map.
   *
   * @param customControl the custom control to add to the map
   */
  public void addCustomControl(CustomControl customControl) {
    customControls.add(customControl);
    setCustomControls(customControls.stream().toArray(CustomControl[]::new));
  }

  /**
   * Removes a custom control added to the map.
   *
   * @param customControl the custom control to be removed
   */
  public void removeCustomControl(CustomControl customControl) {
    customControls.remove(customControl);
    setCustomControls(customControls.stream().toArray(CustomControl[]::new));
  }

  /**
   * Removes all custom controls added to the map.
   */
  public void removeCustomControls() {
    customControls.clear();
    getElement().executeJs("this._removeCustomControls()");
  }

  /**
   * Adds a FullScreenEvent listener. The listener is called to notify whether the map is in full
   * screen mode.
   *
   * @param listener a FullScreenEvent listener
   * @return Registration object to allow removing the listener
   */
  public Registration addFullScreenListener(ComponentEventListener<FullScreenEvent> listener) {
    DomListenerRegistration registration =
        getElement().addEventListener("fullscreenchange", ev -> {
          getElement()
          .executeJs(
              "var isFullScreen = document.fullScreen ||\r\n"
                  + " document.mozFullScreen ||\r\n"
                  + " document.webkitIsFullScreen;\r\n"
                  + " return isFullScreen != null ? isFullScreen : false;")
              .then(Boolean.class, isFullScreen -> listener
                  .onComponentEvent(new FullScreenEvent(this, true, isFullScreen)));
        });
    return registration::remove;
  }

  /**
   * Event fired when the full screen mode changes on the map.
   */
  public static class FullScreenEvent extends ComponentEvent<GoogleMap> {

    private boolean isFullScreen;

    /**
     * Creates a new FullScreenEvent.
     *
     * @param source the source component
     * @param fromClient whether the event originated from the client side
     * @param isFullScreen the full screen state
     */
    public FullScreenEvent(GoogleMap source, boolean fromClient, boolean isFullScreen) {
      super(source, true);
      this.isFullScreen = isFullScreen;
    }

    /**
     * Checks if the map is in full screen mode.
     *
     * @return true if the map is in full screen mode, false otherwise
     */
    public boolean isFullScreen() {
      return isFullScreen;
    }
  }

  /**
   * Exits the full screen mode on the map.
   */
  public void closeFullScreen() {
    getElement()
        .executeJs("document.exitFullscreen();");
  }

  /**
   * Sets style formatting to the {@link FeatureType features} and {@link ElementType elements} of the map.
   *
   * @param mapStyles formatting to be applied to the map features and elements
   */
  public void setMapStyle(MapStyle... mapStyles) {
    JsonArray jsonArray = Json.createArray();
    for (int i = 0; i < mapStyles.length; i++) {
      MapStyle mapStyle = mapStyles[i];
      jsonArray.set(i, mapStyle.getJson());
    }
    getElement().setPropertyJson("styles", jsonArray);
  }
}
