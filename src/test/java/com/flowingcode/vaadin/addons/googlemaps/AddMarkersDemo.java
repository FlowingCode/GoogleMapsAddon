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
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@PageTitle("Add Markers Demo")
@DemoSource(
    "https://github.com/FlowingCode/GoogleMapsAddon/blob/master/src/test/java/com/flowingcode/vaadin/addons/googlemaps/AddMarkersDemo.java")
@SuppressWarnings("serial")
public class AddMarkersDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.SATELLITE);
    gmaps.setSizeFull();
    gmaps.setCenter(new LatLon(-31.636036, -60.7055271));

    Map<String, String> markerColorsMap = new HashMap<>();
    markerColorsMap.put("Red", Markers.RED);
    markerColorsMap.put("Pink", Markers.PINK);
    markerColorsMap.put("Blue", Markers.BLUE);
    markerColorsMap.put("Green", Markers.GREEN);
    markerColorsMap.put("Purple", Markers.PURPLE);
    markerColorsMap.put("Yellow", Markers.YELLOW);
    markerColorsMap.put("Orange", Markers.ORANGE);
    markerColorsMap.put("Light blue", Markers.LIGHTBLUE);
    ComboBox<String> colorCB = new ComboBox<>();
    colorCB.setDataProvider(DataProvider.fromStream(markerColorsMap.keySet().stream()));
    colorCB.setPlaceholder("Marker color");
    Button addMarker =
        new Button(
            "Add Marker",
            ev -> {
              String markerColor =
                  Optional.ofNullable(markerColorsMap.get(colorCB.getValue())).orElse("");
              gmaps.addMarker("New Marker", gmaps.getCenter(), true, markerColor);
            });

    FlexLayout layout = new FlexLayout();
    layout.setFlexWrap(FlexWrap.WRAP);
    addMarker.addClassName("margin-button");
    colorCB.addClassName("margin-button");
    layout.add(addMarker, colorCB);
    add(gmaps, layout);
  }
}
