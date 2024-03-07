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

@PageTitle("Customized Marker Icons Demo")
@DemoSource
@Route(value = "googlemaps/markericons", layout = GooglemapsDemoView.class)
@SuppressWarnings("serial")
public class CustomizedMarkerIconsDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.ROADMAP);
    gmaps.setSizeFull();
    gmaps.setCenter(new LatLon(-31.635175, -60.698405));
    gmaps.setZoom(16);

    // marker ONLY using an url for the marker's icon image
    gmaps.addMarker("Marker with only an URL as icon", gmaps.getCenter(), false,
        "https://www.flowingcode.com/wp-content/uploads/2020/06/FCMarker.png");

    // marker using a MarkerIcon, url icon image + customization
    MarkerIcon markerIcon = new MarkerIcon(Markers.GREEN);
    markerIcon.setAnchor(new GoogleMapPoint(15.0, 10.0));
    gmaps.addMarker(new GoogleMapMarker("Marker with MarkerIcon defining url and anchor points",
        new LatLon(-31.636027, -60.703253), false, markerIcon));

    // marker using a Symbol, svg path for the icon's image + customization
    Symbol symbol = new Symbol(
        "M-1.547 12l6.563-6.609-1.406-1.406-5.156 5.203-2.063-2.109-1.406 1.406zM0 0q2.906 0 4.945 2.039t2.039 4.945q0 1.453-0.727 3.328t-1.758 3.516-2.039 3.070-1.711 2.273l-0.75 0.797q-0.281-0.328-0.75-0.867t-1.688-2.156-2.133-3.141-1.664-3.445-0.75-3.375q0-2.906 2.039-4.945t4.945-2.039z");
    symbol.setFillColor("purple");
    symbol.setFillOpacity(1.0);
    symbol.setAnchor(new GoogleMapPoint(20.0, -0.5));
    symbol.setRotation(180.0);
    symbol.setScale(1.5);
    gmaps.addMarker(new GoogleMapMarker(
        "Marker with Symbol defining path, anchor points, rotation, scale, fill color and fill opacity",
        new LatLon(-31.637307, -60.695934), false, symbol));

    add(gmaps);
  }
  
}
