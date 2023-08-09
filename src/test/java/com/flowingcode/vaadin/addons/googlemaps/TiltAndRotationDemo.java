/*-
 * #%L
 * Google Maps Addon
 * %%
 * Copyright (C) 2020 - 2022 Flowing Code
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
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Tilt & Rotation Demo")
@DemoSource
@Route(value = "googlemaps/tilt-rotation", layout = GooglemapsDemoView.class)
@SuppressWarnings("serial")
public class TiltAndRotationDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.ROADMAP);
    gmaps.setSizeFull();
    // coordinates & map id from
    // https://developers.google.com/maps/documentation/javascript/examples/webgl/webgl-tilt-rotation
    gmaps.setCenter(new LatLon(37.7893719, -122.3942));
    gmaps.setMapId("90f87356969d889c");
    gmaps.setTilt(47.5);
    gmaps.setHeading(300);
    gmaps.setZoom(16);

    FlexLayout layout = new FlexLayout();
    layout.setFlexWrap(FlexWrap.WRAP); // hide-source

    Span currentTiltTitle = new Span("Current tilt value: ");
    Span currentTilt = new Span();
    currentTilt.setText(String.valueOf(gmaps.getTilt()));
    Span currentHeadingTitle = new Span("Current heading value: ");
    Span currentHeading = new Span();
    currentHeading.setText(String.valueOf(gmaps.getHeading()));

    VerticalLayout currentValuesLayout =
        new VerticalLayout(new HorizontalLayout(currentTiltTitle, currentTilt),
            new HorizontalLayout(currentHeadingTitle, currentHeading));
    currentValuesLayout.setSpacing(false); // hide-source
    currentValuesLayout.getStyle().set("padding-left", "0"); // hide-source

    Span description = new Span("Initial Tilt value: 47.5 - Initial Heading value: 300");
    description.setWidthFull(); // hide-source

    Button tiltUpButton = new Button("Tilt Up", e -> {
      gmaps.setTilt(gmaps.getTilt() + 20);
      currentTilt.setText(String.valueOf(gmaps.getTilt()));
    });
    Button tiltDownButton = new Button("Tilt Down", e -> {
      gmaps.setTilt(gmaps.getTilt() - 20);
      currentTilt.setText(String.valueOf(gmaps.getTilt()));
    });
    Button rotateLeftButton = new Button("Rotate Left", e -> {
      gmaps.setHeading(gmaps.getHeading() + 20);
      currentHeading.setText(String.valueOf(gmaps.getHeading()));
    });
    Button rotateRightButton = new Button("Rotate Right", e -> {
      gmaps.setHeading(gmaps.getHeading() - 20);
      currentHeading.setText(String.valueOf(gmaps.getHeading()));
    });

    Button resetInitialButton = new Button("Reset to Initial Values", e -> {
      gmaps.setHeading(300);
      gmaps.setTilt(47.5);
      currentTilt.setText(String.valueOf(gmaps.getTilt()));
      currentHeading.setText(String.valueOf(gmaps.getHeading()));
    });

    Span buttonsDescription = new Span(
        "Play around with the following buttons to programmatically adjust tilt and heading in 20-degree increments:");
    buttonsDescription.setWidthFull(); // hide-source

    HorizontalLayout buttonsLayout = new HorizontalLayout(tiltUpButton, tiltDownButton,
        rotateLeftButton, rotateRightButton, resetInitialButton);
    buttonsLayout.getStyle().set("margin-top", "15px"); // hide-source

    layout.add(description, buttonsDescription, buttonsLayout, currentValuesLayout);

    add(gmaps, layout);
  }

}
