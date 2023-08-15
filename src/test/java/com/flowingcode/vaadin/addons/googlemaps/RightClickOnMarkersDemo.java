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
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Right Click on Marker Demo")
@DemoSource
@Route(value = "googlemaps/right-click-on-marker", layout = GooglemapsDemoView.class)
@SuppressWarnings("serial")
public class RightClickOnMarkersDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.ROADMAP);
    gmaps.setSizeFull();
    gmaps.setCenter(new LatLon(-31.636036, -60.7055271));
    gmaps.setZoom(12);

    gmaps.addMarker("Marker", gmaps.getCenter(), true, Markers.GREEN)
        .addRightClickListener(e -> {

          Div text = new Div(new Text("Alt key: " + e.isAltKey()), new HtmlComponent("br"),
              new Text("Shift key: " + e.isShiftKey()), new HtmlComponent("br"),
              new Text("Ctrol key: " + e.isCtrlKey()), new HtmlComponent("br"),
              new Text("Click counts: " + e.getClickCount()), new HtmlComponent("br"),
              new Text("Latitude: " + e.getLatitude()), new HtmlComponent("br"),
              new Text("Longitude: " + e.getLongitude()));
          
          Notification notification = new Notification();
          
          Button closeButton = new Button(new Icon(VaadinIcon.CLOSE_SMALL));
          closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
          closeButton.addClickListener(event -> {
              notification.close();
          });

          HorizontalLayout layout = new HorizontalLayout(text, closeButton);
          layout.setAlignItems(Alignment.CENTER);
          
          notification.add(layout);
          notification.open();
        });
    
    add(gmaps);
  }
}