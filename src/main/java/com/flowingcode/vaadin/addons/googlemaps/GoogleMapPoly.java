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
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.shared.Registration;
import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;
import java.util.List;

@SuppressWarnings("serial")
@Tag("google-map-poly")
@JsModule("@flowingcode/google-map/google-map-poly.js")
@JsModule("@flowingcode/google-map/google-map-point.js")
@NpmPackage(value = "@flowingcode/google-map", version = "3.8.0")
@NpmPackage(value = "@googlemaps/markerclusterer", version = "2.0.8")
public abstract class GoogleMapPoly extends Component {

  private static final double DEFAULT_FILL_OPACITY = 0.5d;
  private static final String DEFAULT_FILL_COLOR = "blue";

  public enum StrokePosition {
    CENTER,
    INSIDE,
    OUTSIDE
  }

  public GoogleMapPoly(List<GoogleMapPoint> points) {
    getElement().removeProperty("draggable");
    setFillColor(DEFAULT_FILL_COLOR);
    setFillOpacity(DEFAULT_FILL_OPACITY);
    setPoints(points);
  }

  public void setFillOpacity(double opacity) {
    getElement().setProperty("fillOpacity", opacity);
  }

  public double getFillOpacity() {
    return getElement().getProperty("fillOpacity", 1d);
  }

  public void setStrokeOpacity(double opacity) {
    getElement().setProperty("strokeOpacity", opacity);
  }

  public double getStrokeOpacity() {
    return getElement().getProperty("strokeOpacity", 1d);
  }

  public void setStrokePosition(StrokePosition position) {
    getElement().setProperty("strokePosition", position.name().toLowerCase());
  }

  public StrokePosition getStrokePosition() {
    return StrokePosition.valueOf(getElement().getProperty("strokePosition").toUpperCase());
  }

  public void setStrokeWeight(double weight) {
    getElement().setProperty("strokeWeight", weight);
  }

  public double getStrokeWeight() {
    return getElement().getProperty("strokeWeight", 1d);
  }

  public void setZIndex(double zindex) {
    getElement().setProperty("zIndex", zindex);
  }

  public double getZIndex() {
    return getElement().getProperty("zIndex", 1d);
  }

  public void setFillColor(String string) {
    getElement().setProperty("fillColor", string);
  }

  public String getFillColor() {
    return getElement().getProperty("fillColor");
  }

  public void setStrokeColor(String string) {
    getElement().setProperty("strokeColor", string);
  }

  public String getStrokeColor() {
    return getElement().getProperty("strokeColor");
  }

  public void setClosed(boolean b) {
    getElement().setProperty("closed", b);
  }

  public boolean isClosed() {
    return getElement().getProperty("closed", false);
  }

  public void setGeodesic(boolean b) {
    getElement().setProperty("geodesic", b);
  }

  public boolean isGeodesic() {
    return getElement().getProperty("geodesic", false);
  }

  public void setPoints(Iterable<GoogleMapPoint> points) {
    points.forEach(point -> getElement().appendChild(point.getElement()));
  }

  public GoogleMapPoint addPoint(LatLon position) {
    GoogleMapPoint point = new GoogleMapPoint(position.getLat(), position.getLon());
    getElement().appendChild(point.getElement());
    return point;
  }

  public void addPoint(GoogleMapPoint point) {
    getElement().appendChild(point.getElement());
  }

  @SuppressWarnings("squid:S3242")
  public void removePoint(GoogleMapPoint point) {
    getElement().removeChild(point.getElement());
  }

  @DomEvent("google-map-poly-click")
  public static class GoogleMapPolyClickEvent extends ClickEvent<GoogleMapPolygon> {

    private final double lat;
    private final double lon;

    public GoogleMapPolyClickEvent(
        GoogleMapPoly source,
        boolean fromClient,
        @EventData(value = "event.detail.latLng") JsonValue latLng) {
      super(source);
      lat = ((JsonObject) latLng).getNumber("lat");
      lon = ((JsonObject) latLng).getNumber("lng");
    }

    public double getLatitude() {
      return lat;
    }

    public double getLongitude() {
      return lon;
    }
  }

  public Registration addClickListener(
      ComponentEventListener<GoogleMapPolyClickEvent> listener) {
    getElement().setProperty("clickable", true);
    getElement().setProperty("clickEvents", true);
    return addListener(GoogleMapPolyClickEvent.class, listener);
  }

  /**
   * @deprecated Use {@link #setIcons(IconSequence...)} instead.
   */
  @Deprecated
  public void setIcons(Icon... icons) {
    JsonArray jsonArray = Json.createArray();
    for (int i = 0; i < icons.length; i++) {
      jsonArray.set(i, icons[i].getJson());
    }
    getElement().setPropertyJson("icons", jsonArray);
  }

  /**
   * Set icons to the polygon/polyline.
   *
   * @param icons the icons to set
   */
  public void setIcons(IconSequence... icons) {
    JsonArray jsonArray = Json.createArray();
    for (int i = 0; i < icons.length; i++) {
      jsonArray.set(i, icons[i].getJson());
    }
    getElement().setPropertyJson("icons", jsonArray);
  }

}
