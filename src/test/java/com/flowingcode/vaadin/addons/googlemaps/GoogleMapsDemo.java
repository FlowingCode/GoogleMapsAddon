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
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.Arrays;

@PageTitle("Google Maps Demo")
@DemoSource
@Route(value = "googlemaps/googlemaps", layout = GooglemapsDemoView.class)
@SuppressWarnings("serial")
public class GoogleMapsDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.SATELLITE);
    gmaps.setSizeFull();
    gmaps.setCenter(new LatLon(-31.620173186615883, -60.67964404821396));

    // add click listener to get latitude and longitude on left click
    gmaps.addClickListener(
        ev ->
            Notification.show(
                "Left click at latitude: "
                    + ev.getLatitude()
                    + "; Longitude: "
                    + ev.getLongitude()));

    // add click listener to get latitude and longitude on right click
    gmaps.addRightClickListener(
        ev ->
            Notification.show(
                "Right click at latitude: "
                    + ev.getLatitude()
                    + "; Longitude: "
                    + ev.getLongitude()));

    // create marker on map center and add info window to it
    GoogleMapMarker flowingmarker =
        gmaps.addMarker(
            "Center",
            gmaps.getCenter(),
            false,
            "https://www.flowingcode.com/wp-content/uploads/2025/09/marker_1.png");
    flowingmarker.addInfoWindow("<h1>Flowing Code</h1>");

    // add polygon with icons and click listener
    GoogleMapPolygon gmp =
        gmaps.addPolygon(
            Arrays.asList(
                new GoogleMapPoint(gmaps.getCenter().getLat(), gmaps.getCenter().getLon() + 1),
                new GoogleMapPoint(gmaps.getCenter().getLat() + 1, gmaps.getCenter().getLon()),
                new GoogleMapPoint(gmaps.getCenter().getLat(), gmaps.getCenter().getLon() - 1),
                new GoogleMapPoint(gmaps.getCenter().getLat() - 1, gmaps.getCenter().getLon()),
                new GoogleMapPoint(gmaps.getCenter().getLat(), gmaps.getCenter().getLon() + 1)));
    gmp.setClosed(false);

    gmp.setIcons(new IconSequence(new Symbol("M -2,0 0,-2 2,0 0,2 z", "#F00", "#FF0", 1.0), "25px"));
    
    gmp.addClickListener(e -> Notification.show("Polygon clicked"));

    // add button to show center coordinates
    Button center =
        new Button(
            "Show Coordinates",
            ev -> {
              Notification.show("Center coordinates: " + gmaps.getCenter());
            });

    // add button to toggle visibility of marker's info window
    Button toggleInfoWindow =
        new Button(
            "Toggle Info Window",
            ev -> {
              flowingmarker.setInfoWindowVisible(!flowingmarker.isInfoWindowVisible());
            });

    FlexLayout layout = new FlexLayout();
    layout.setFlexWrap(FlexWrap.WRAP);
    center.addClassName("margin-button");
    toggleInfoWindow.addClassName("margin-button");
    layout.add(center, toggleInfoWindow);
    add(gmaps, layout);
  }
}
