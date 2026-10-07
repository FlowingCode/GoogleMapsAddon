/*-
 * #%L
 * Google Maps Addon
 * %%
 * Copyright (C) 2020 - 2026 Flowing Code
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
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Advanced Markers Demo")
@DemoSource
@Route(value = "googlemaps/advancedmarkers", layout = GooglemapsDemoView.class)
@SuppressWarnings("serial")
public class AdvancedMarkersDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.ROADMAP);
    gmaps.setSizeFull();
    gmaps.setCenter(new LatLon(-31.636036, -60.7055271));
    gmaps.setZoom(14);
    // Advanced markers require a Map ID. "DEMO_MAP_ID" can be used for testing; use your own Map ID
    // in production.
    gmaps.setMapId("DEMO_MAP_ID");

    // A marker with a customized pin and an info window
    GoogleMapAdvancedMarker flowingCode =
        new GoogleMapAdvancedMarker("Flowing Code", new LatLon(-31.636036, -60.7055271), false);
    flowingCode.setPinBackground("#1a73e8");
    flowingCode.setPinBorderColor("#0b4f9c");
    flowingCode.setPinGlyphColor("white");
    flowingCode.addInfoWindow("<b>Flowing Code</b>");
    gmaps.addMarker(flowingCode);

    // A draggable marker
    GoogleMapAdvancedMarker draggable =
        new GoogleMapAdvancedMarker("Drag me", new LatLon(-31.6300, -60.6980), true);
    draggable.setPinScale(1.4);
    draggable.addDragEndEventListener(e -> Notification.show(
        "Dragged to -> Latitude: " + e.getLatitude() + " - Longitude: " + e.getLongitude()));
    gmaps.addMarker(draggable);

    // A marker with an image as icon
    gmaps.addMarker(new GoogleMapAdvancedMarker("Icon", new LatLon(-31.6420, -60.7120), false,
        Markers.GREEN));

    // A marker using a MarkerIcon: url icon image + anchor point
    MarkerIcon markerIcon = new MarkerIcon(Markers.PURPLE);
    markerIcon.setAnchor(new GoogleMapPoint(15.0, 10.0));
    gmaps.addMarker(new GoogleMapAdvancedMarker("MarkerIcon", new LatLon(-31.6335, -60.7110), false,
        markerIcon));

    // A marker using a Symbol: svg path for the icon's image + customization
    Symbol symbol = new Symbol(
        "M-1.547 12l6.563-6.609-1.406-1.406-5.156 5.203-2.063-2.109-1.406 1.406zM0 0q2.906 0 4.945 2.039t2.039 4.945q0 1.453-0.727 3.328t-1.758 3.516-2.039 3.070-1.711 2.273l-0.75 0.797q-0.281-0.328-0.75-0.867t-1.688-2.156-2.133-3.141-1.664-3.445-0.75-3.375q0-2.906 2.039-4.945t4.945-2.039z");
    symbol.setFillColor("purple");
    symbol.setFillOpacity(1.0);
    symbol.setAnchor(new GoogleMapPoint(20.0, -0.5));
    symbol.setRotation(180.0);
    symbol.setScale(1.5);
    gmaps.addMarker(new GoogleMapAdvancedMarker("Symbol", new LatLon(-31.6380, -60.7010), false,
        symbol));

    // A marker with a label, that handles clicks and right clicks (touch-and-hold on touch devices)
    GoogleMapAdvancedMarker labeled =
        new GoogleMapAdvancedMarker("Label", new LatLon(-31.6400, -60.6960), false);
    MarkerLabel label = new MarkerLabel("1");
    label.setColor("white");
    labeled.setLabel(label);
    labeled.addClickListener(e -> Notification.show("Clicked"));
    labeled.addRightClickListener(e -> Notification.show("Right clicked"));
    gmaps.addMarker(labeled);

    // Legacy markers can be used on the same map
    gmaps.addMarker(new GoogleMapMarker("Legacy marker", new LatLon(-31.6310, -60.7150), false));

    TextField labelText = new TextField();
    labelText.setPlaceholder("Label Text");
    Checkbox isDraggable = new Checkbox("Draggable");
    Button addMarker = new Button("Add Advanced Marker", ev -> {
      GoogleMapAdvancedMarker marker =
          new GoogleMapAdvancedMarker("New Marker", gmaps.getCenter(), isDraggable.getValue());
      if (!labelText.getValue().isEmpty()) {
        marker.setLabel(new MarkerLabel(labelText.getValue()));
      }
      gmaps.addMarker(marker);
    });

    FlexLayout layout = new FlexLayout();
    layout.setFlexWrap(FlexWrap.WRAP); // hide-source
    layout.getStyle().set("gap", "var(--lumo-space-s, var(--vaadin-gap-m))"); // hide-source
    layout.add(labelText, isDraggable, addMarker);
    layout.setAlignItems(Alignment.BASELINE); // hide-source
    layout.getStyle().set("margin-top", "0"); // hide-source
    add(gmaps, layout);
  }
}
