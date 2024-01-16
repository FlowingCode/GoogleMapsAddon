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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Marker Clustering Demo")
@DemoSource
@Route(value = "googlemaps/markerclustering", layout = GooglemapsDemoView.class)
@SuppressWarnings("serial")
public class MarkerClusteringDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.ROADMAP);
    gmaps.setSizeFull();
    gmaps.setZoom(5);
    gmaps.setCenter(new LatLon(-31.636036, -60.7055271));
    
    gmaps.addMarker("Marker 1", new LatLon(-31.646036, -60.7055290), true, Markers.PINK);    
    gmaps.addMarker("Marker 2", new LatLon(-31.562346, -60.6176364), true, Markers.PINK);    
    gmaps.addMarker("Marker 3", new LatLon(-31.531917, -60.8456027), true, Markers.PINK);
    gmaps.addMarker("Marker 4", new LatLon(-31.651667, -60.9555557), true, Markers.PINK);
    
    gmaps.addMarker("Marker 5", new LatLon(-30.997815, -65.60542944), false, Markers.BLUE);
    gmaps.addMarker("Marker 6", new LatLon(-31.298693, -66.50630836), false, Markers.BLUE);
    gmaps.addMarker("Marker 7", new LatLon(-31.878914, -66.28658178), false, Markers.BLUE);
    
    gmaps.addMarker("Marker 8", new LatLon(-33.138889, -65.95699194), false, Markers.GREEN);
    gmaps.addMarker("Marker 9", new LatLon(-33.322683, -65.12203100), false, Markers.GREEN);
    gmaps.addMarker("Marker 10", new LatLon(-33.652538, -65.737265381), false, Markers.GREEN);
    
    gmaps.enableMarkersClustering();
    
    add(gmaps);    
  }

}
