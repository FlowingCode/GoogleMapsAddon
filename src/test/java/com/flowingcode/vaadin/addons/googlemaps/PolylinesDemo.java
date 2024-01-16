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

import com.flowingcode.vaadin.addons.demo.DemoSource;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMap.MapType;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.Arrays;

@PageTitle("Polylines Demo")
@DemoSource
@Route(value = "googlemaps/polylines", layout = GooglemapsDemoView.class)
@SuppressWarnings("serial")
public class PolylinesDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.TERRAIN);
    gmaps.setSizeFull();
    gmaps.setZoom(3);
    gmaps.setCenter(new LatLon(0, -180));

    GoogleMapPoint point1 = new GoogleMapPoint(37.772, -122.214);
    GoogleMapPoint point2 = new GoogleMapPoint(21.291, -157.821);
    GoogleMapPoint point3 = new GoogleMapPoint(-18.142, 178.431);
    GoogleMapPoint point4 = new GoogleMapPoint(-27.467, 153.027);

    GoogleMapPolyline polyline =
        new GoogleMapPolyline(Arrays.asList(point1, point2, point3, point4));
    polyline.setStrokeColor("#ed0e0e");
    polyline.setStrokeWeight(3.0);

    polyline.addClickListener(e -> Notification
        .show("Latitude: " + e.getLatitude() + " - Longitude: " + e.getLongitude()));

    gmaps.addPolyline(polyline);

    add(gmaps);
  }
}
