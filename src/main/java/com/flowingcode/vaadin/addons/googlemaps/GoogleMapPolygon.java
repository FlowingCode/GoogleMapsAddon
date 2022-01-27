/*-
 * #%L
 * Google Maps Addon
 * %%
 * Copyright (C) 2020 - 2021 Flowing Code
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

/** A class representing a polygon overlay of Google Maps. */
@SuppressWarnings("serial")
@Tag("google-map-poly")
@JsModule("@flowingcode/google-map/google-map-poly.js")
@JsModule("@flowingcode/google-map/google-map-point.js")
@NpmPackage(value = "@flowingcode/google-map", version = "3.1.0")
public class GoogleMapPolygon extends Component {

  private static final double DEFAULT_FILL_OPACITY = 0.5d;
  private static final String DEFAULT_FILL_COLOR = "blue";

  public enum StrokePosition {
    CENTER,
    INSIDE,
    OUTSIDE
  }

  public GoogleMapPolygon(List<GoogleMapPoint> points) {
    getElement().removeProperty("draggable");
    setClosed(true);
    setFillColor(DEFAULT_FILL_COLOR);
    setFillOpacity(DEFAULT_FILL_OPACITY);
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

  public void setPoints(Iterable<GoogleMapPoint> points) {
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

  @SuppressWarnings("squid:S3242")
  public void removePoint(GoogleMapPoint point) {
    this.getElement().removeChild(point.getElement());
  }

  @DomEvent("google-map-poly-click")
  public static class GoogleMapPolygonClickEvent extends ClickEvent<GoogleMapPolygon> {

    private final double lat;
    private final double lon;

    public GoogleMapPolygonClickEvent(
        GoogleMapPolygon source,
        boolean fromClient,
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

  public Registration addClickListener(
      ComponentEventListener<GoogleMapPolygonClickEvent> listener) {
    this.getElement().setProperty("clickable", true);
    this.getElement().setProperty("clickEvents", true);
    return addListener(GoogleMapPolygonClickEvent.class, listener);
  }

  public void setIcons(Icon... icons) {
    JsonArray jsonArray = Json.createArray();
    for (int i = 0; i < icons.length; i++) {
      jsonArray.set(i, icons[i].getJson());
    }   
    this.getElement().setPropertyJson("icons", jsonArray);
  }
}
