/*-
 * #%L
 * Google Maps Addon
 * %%
 * Copyright (C) 2020 - 2023 Flowing Code
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

@PageTitle("Draggable Marker Demo")
@DemoSource
@Route(value = "googlemaps/draggablemarker", layout = GooglemapsDemoView.class)
@SuppressWarnings("serial")
public class DraggableMarkerDemo extends AbstractGoogleMapsDemo {
 
  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.ROADMAP);
    gmaps.setSizeFull();
    gmaps.setCenter(new LatLon(-31.636036, -60.7055271));
    
    gmaps.addMarker("Center", gmaps.getCenter(), true, Markers.PURPLE)
    .addDragEndEventListener(
        e -> 
          Notification.show("Lat: " + e.getLatitude() + " - Lng: " + e.getLongitude()));
                     
    add(gmaps);    
  }
}
