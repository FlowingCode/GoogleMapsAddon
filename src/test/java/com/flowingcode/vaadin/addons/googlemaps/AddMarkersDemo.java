/*-
 * #%L
 * Google Maps Addon
 * %%
 * Copyright (C) 2020 - 2025 Flowing Code
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
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@PageTitle("Add Markers Demo")
@DemoSource
@Route(value = "googlemaps/addmarkers", layout = GooglemapsDemoView.class)
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
    
    ComboBox<String> colorCB = new ComboBox<>("Color");
    ReflectionUtil.setItems(colorCB, markerColorsMap.keySet());
    colorCB.setPlaceholder("Choose color");
    
    Checkbox draggable = new Checkbox("Draggable");    
    Checkbox withRightClick = new Checkbox("Right Click");
    TextField labelText = new TextField();
    labelText.setPlaceholder("Label Text"); // hide-source
    labelText.setEnabled(false); // hide-source
    Checkbox withLabel = new Checkbox("With Label");
    // #if vaadin eq 0
    withLabel.addValueChangeListener(event -> {
      labelText.setEnabled(event.getValue());
    });
    // #endif
      
    Button addMarker =
        new Button(
            "Add Marker",
            ev -> {
              String markerColor =
                  Optional.ofNullable(markerColorsMap.get(colorCB.getValue())).orElse("");
              Boolean isDraggable = draggable.getValue();
              GoogleMapMarker marker =
                  new GoogleMapMarker("New Marker", gmaps.getCenter(), isDraggable, markerColor);

              if (isDraggable) {
                marker.addDragEndEventListener(e -> Notification.show("Dragged to -> Latitude: "
                    + e.getLatitude() + " - Longitude: " + e.getLongitude()));
              }

              if (withRightClick.getValue()) {
                marker.addRightClickListener(e -> {
                  Div text = new Div(new Text("Alt key: " + e.isAltKey()), new HtmlComponent("br"),
                      new Text("Shift key: " + e.isShiftKey()), new HtmlComponent("br"),
                      new Text("Ctrol key: " + e.isCtrlKey()), new HtmlComponent("br"),
                      new Text("Click counts: " + e.getClickCount()), new HtmlComponent("br"),
                      new Text("Latitude: " + e.getLatitude()), new HtmlComponent("br"),
                      new Text("Longitude: " + e.getLongitude()));

                  Notification notification = new Notification();

                  Button closeButton = new Button(new Icon(VaadinIcon.CLOSE_SMALL));
                  closeButton.addClickListener(event -> {
                    notification.close();
                  });

                  HorizontalLayout layout = new HorizontalLayout(text, closeButton);
                  layout.setAlignItems(Alignment.CENTER);

                  notification.add(layout);
                  notification.open();
                });
              }

              if(withLabel.getValue()) {
                if(labelText.getValue().isEmpty()) {
                  Notification.show("Please select a label for the marker");
                  return;
                }
                MarkerLabel label = new MarkerLabel(labelText.getValue());
                label.setColor("white");
                label.setFontWeight("bold");
                marker.setLabel(label);
              }

              gmaps.addMarker(marker);
              // #if vaadin eq 0
              // Reset form
              colorCB.clear();
              draggable.setValue(false);
              withRightClick.setValue(false);
              labelText.clear();
              withLabel.setValue(false);
              // #endif
            });

    FlexLayout layout = new FlexLayout();
    layout.setFlexWrap(FlexWrap.WRAP); // hide-source
    addMarker.addClassName("margin-button"); // hide-source
    colorCB.addClassName("margin-button"); // hide-source
    labelText.addClassName("margin-button"); // hide-source
    layout.add(colorCB, draggable, withRightClick, withLabel, labelText, addMarker);
    layout.setAlignItems(Alignment.BASELINE); // hide-source
    layout.getStyle().set("margin-top", "0"); // hide-source
    add(gmaps, layout);
  }
}
