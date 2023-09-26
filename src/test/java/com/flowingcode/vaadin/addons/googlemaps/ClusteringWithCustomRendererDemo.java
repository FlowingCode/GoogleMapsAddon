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
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Clustering With Custom Renderer Demo")
@DemoSource
@DemoSource(
    value = "/src/test/resources/META-INF/resources/frontend/src/clustering-custom-renderer-example.js",
    caption = "clustering-custom-renderer-example.js")
@Route(value = "googlemaps/clustering-custom-renderer", layout = GooglemapsDemoView.class)
@JsModule("./src/clustering-custom-renderer-example.js")
@SuppressWarnings("serial")
public class ClusteringWithCustomRendererDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.ROADMAP);
    gmaps.setSizeFull();
    gmaps.setZoom(5);
    gmaps.setCenter(new LatLon(-31.636036, -60.7055271));
    
    gmaps.addMarker("Marker 1", new LatLon(-31.646036, -58.7055290), true, Markers.PURPLE);    
    gmaps.addMarker("Marker 2", new LatLon(-31.562346, -58.6176364), true, Markers.PURPLE);    
    gmaps.addMarker("Marker 3", new LatLon(-31.531917, -58.8456027), true, Markers.PURPLE);
    gmaps.addMarker("Marker 4", new LatLon(-31.651667, -58.9555557), true, Markers.PURPLE);
    
    gmaps.addMarker("Marker 5", new LatLon(-30.997815, -68.60542944), false, Markers.BLUE);
    gmaps.addMarker("Marker 6", new LatLon(-31.298693, -68.50630836), false, Markers.BLUE);
    gmaps.addMarker("Marker 7", new LatLon(-31.878914, -68.28658178), false, Markers.BLUE);
    
    gmaps.addMarker("Marker 8", new LatLon(-35.138889, -65.95699194), false, Markers.GREEN);
    gmaps.addMarker("Marker 9", new LatLon(-35.322683, -65.12203100), false, Markers.GREEN);
    gmaps.addMarker("Marker 10", new LatLon(-35.652538, -65.737265381), false, Markers.GREEN);
    gmaps.addMarker("Marker 11", new LatLon(-35.512548, -65.565465771), false, Markers.GREEN);
    gmaps.addMarker("Marker 12", new LatLon(-35.650638, -65.707366381), false, Markers.YELLOW);
    gmaps.addMarker("Marker 13", new LatLon(-35.550638, -65.703360000), false, Markers.YELLOW);
    
    // enable clustering
    gmaps.enableMarkersClustering();
    
    /*
     * set custom renderer for clustering (see
     * /google-maps/src/test/resources/META-INF/resources/frontend/src/clustering-custom-renderer-
     * example.js for definition)
     */
    gmaps.setClusteringRenderer("customRenderer");
    
    add(gmaps);    
  }

}
