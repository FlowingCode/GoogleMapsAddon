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

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

@SuppressWarnings("serial")
@Tag("google-map-point")
@JsModule("@flowingcode/google-map/google-map-point.js")
@NpmPackage(value = "@flowingcode/google-map", version = "3.6.1")
@NpmPackage(value = "@googlemaps/markerclusterer", version = "2.0.8")
public class GoogleMapPoint extends Component {

  public GoogleMapPoint(LatLon latlon) {
    this(latlon.getLat(), latlon.getLon());
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
