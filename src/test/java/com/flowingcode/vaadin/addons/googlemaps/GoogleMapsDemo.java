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

import com.flowingcode.vaadin.addons.googlemaps.GoogleMap.MapType;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("serial")
@StyleSheet("context://frontend/styles/google-maps/demo-styles.css")
public class GoogleMapsDemo extends VerticalLayout {

  public GoogleMapsDemo() {
    this.setSizeFull();
    String apiKey = System.getProperty("google.maps.api");
    if (apiKey == null) {
      add(
          new H2(
              "Api key is needded to run the demo, pass it using the following system property: '-Dgoogle.maps.api=<your-api-key>'"));
    } else {
      GoogleMap gmaps = new GoogleMap(apiKey, null, null);
      gmaps.setMapType(MapType.SATELLITE);
      gmaps.setSizeFull();
      gmaps.setCenter(new LatLon(-31.636036, -60.7055271));

      gmaps.addClickListener(
          ev ->
              Notification.show(
                  "Left click at latitude: "
                      + ev.getLatitude()
                      + "; Longitude: "
                      + ev.getLongitude()));
      gmaps.addRightClickListener(
          ev ->
              Notification.show(
                  "Right click at latitude: "
                      + ev.getLatitude()
                      + "; Longitude: "
                      + ev.getLongitude()));

      GoogleMapMarker flowingmarker =
          gmaps.addMarker(
              "Center",
              new LatLon(-31.636036, -60.7055271),
              true,
              "https://www.flowingcode.com/wp-content/uploads/2020/06/FCMarker.png");
      flowingmarker.addInfoWindow("<h1>Flowing Code</h1>");
      GoogleMapPolygon gmp =
          gmaps.addPolygon(
              Arrays.asList(
                  new GoogleMapPoint(gmaps.getCenter().getLat(), gmaps.getCenter().getLon() + 1),
                  new GoogleMapPoint(gmaps.getCenter().getLat() + 1, gmaps.getCenter().getLon()),
                  new GoogleMapPoint(gmaps.getCenter().getLat(), gmaps.getCenter().getLon() - 1),
                  new GoogleMapPoint(gmaps.getCenter().getLat() - 1, gmaps.getCenter().getLon()),
                  new GoogleMapPoint(gmaps.getCenter().getLat(), gmaps.getCenter().getLon() + 1)));
      gmp.setClosed(false);
      gmp.setIcons(new Icon("M -2,0 0,-2 2,0 0,2 z", "#F00", "#FF0", 1, 25));
      gmp.addClickListener(ev -> Notification.show("polygon clicked"));
      Button center =
          new Button(
              "Show Coordinates",
              ev -> {
                Notification.show("Center coordinates: " + gmaps.getCenter());
              });
      Map<String, String> map = new HashMap<>();
      map.put("Red", Markers.RED);
      map.put("Pink", Markers.PINK);
      map.put("Blue", Markers.BLUE);
      map.put("Green", Markers.GREEN);
      map.put("Purple", Markers.PURPLE);
      map.put("Yellow", Markers.YELLOW);
      map.put("Orange", Markers.ORANGE);
      map.put("Light blue", Markers.LIGHTBLUE);
      ComboBox<String> colorCB = new ComboBox<>();
      colorCB.setDataProvider(DataProvider.fromStream(map.keySet().stream()));
      colorCB.setPlaceholder("Marker color");
      Button addMarker =
          new Button(
              "Add Marker",
              ev -> {
                String markerColor = Optional.ofNullable(map.get(colorCB.getValue())).orElse("");
                gmaps.addMarker("New Marker", gmaps.getCenter(), true, markerColor);
              });
      Button addPoint =
          new Button(
              "Add Polygon",
              ev -> {
                gmaps.addPolygon(
                    Arrays.asList(
                        new GoogleMapPoint(gmaps.getCenter()),
                        new GoogleMapPoint(
                            gmaps.getCenter().getLat(), gmaps.getCenter().getLon() + 1),
                        new GoogleMapPoint(
                            gmaps.getCenter().getLat() + 1, gmaps.getCenter().getLon())));
              });
      Button toggleInfoWindow =
          new Button(
              "Toggle Info Window",
              ev -> {
                flowingmarker.setInfoWindowVisible(!flowingmarker.isInfoWindowVisible());
              });

      FlexLayout layout = new FlexLayout();
      layout.setFlexWrap(FlexWrap.WRAP);
      center.addClassName("margin-button");
      addMarker.addClassName("margin-button");
      colorCB.addClassName("margin-button");
      addPoint.addClassName("margin-button");
      toggleInfoWindow.addClassName("margin-button");
      layout.add(center, addMarker, colorCB, addPoint, toggleInfoWindow);
      add(gmaps, layout);
    }
  }
}
