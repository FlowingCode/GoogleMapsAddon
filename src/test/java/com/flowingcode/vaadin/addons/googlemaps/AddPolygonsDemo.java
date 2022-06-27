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

import com.flowingcode.vaadin.addons.demo.DemoSource;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMap.MapType;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.Arrays;

@PageTitle("Add Polygons Demo")
@DemoSource
@Route(value = "googlemaps/addplygons", layout = GooglemapsDemoView.class)
@SuppressWarnings("serial")
public class AddPolygonsDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.SATELLITE);
    gmaps.setSizeFull();
    gmaps.setCenter(new LatLon(-31.636036, -60.7055271));

    Button addPoint =
        new Button(
            "Add Polygon",
            ev -> {
              gmaps
                  .addPolygon(
                      Arrays.asList(
                          new GoogleMapPoint(gmaps.getCenter()),
                          new GoogleMapPoint(
                              gmaps.getCenter().getLat(), gmaps.getCenter().getLon() + 1),
                          new GoogleMapPoint(
                              gmaps.getCenter().getLat() + 1, gmaps.getCenter().getLon())))
                  .addClickListener(
                      e ->
                          Notification.show(
                              "Latitude: "
                                  + e.getLatitude()
                                  + " - Longitude: "
                                  + e.getLongitude()));
            });

    FlexLayout layout = new FlexLayout();
    layout.setFlexWrap(FlexWrap.WRAP);
    layout.add(addPoint);
    add(gmaps, layout);
  }
}
