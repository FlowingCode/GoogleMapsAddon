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
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Geolocation Demo")
@DemoSource
@DemoSource(
    value = "/src/test/resources/META-INF/resources/frontend/styles/google-maps/geolocation-demo-styles.css",
    caption = "geolocation-demo-styles.css")
@Route(value = "googlemaps/geolocation", layout = GooglemapsDemoView.class)
@CssImport("./styles/google-maps/geolocation-demo-styles.css")
@SuppressWarnings("serial")
public class GeolocationDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.ROADMAP);
    gmaps.setSizeFull();
    add(gmaps);

    // create custom control button to pan to current location
    Button currentLocationButton = new Button("Go to current location",
        VaadinIcon.CROSSHAIRS.create(), e -> gmaps.goToCurrentLocation());
    currentLocationButton.setClassName("geolocation-button");
    CustomControl geolocationControl =
        new CustomControl(currentLocationButton, ControlPosition.TOP_CENTER);
    gmaps.addCustomControls(geolocationControl);

    gmaps.addCurrentLocationEventListener(e -> gmaps
        .addMarker(new GoogleMapMarker("You are here!", gmaps.getCenter(), false, Markers.GREEN)));

    gmaps.addGeolocationErrorEventListener(e -> Notification.show(e.isBrowserHasGeolocationSupport()
        ? "The geolocation service failed on retrieving your location."
        : "Your browser doesn't support geolocation."));
  }
}
