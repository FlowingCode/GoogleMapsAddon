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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Track Location Demo")
@DemoSource
@Route(value = "googlemaps/tracklocation", layout = GooglemapsDemoView.class)
@SuppressWarnings("serial")
public class TrackLocationDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.ROADMAP);
    gmaps.setSizeFull();
    gmaps.setZoom(15);
    add(gmaps);

    // create buttons to activate/stop location tracking
    Button startLocationTrackingButton = new Button("Start tracking my location");
    Button stopLocationTrackingButton = new Button("Stop tracking my location");
    startLocationTrackingButton.addClickListener(e -> {
      gmaps.trackLocation();
      stopLocationTrackingButton.setEnabled(true);
    });
    startLocationTrackingButton.setDisableOnClick(true);
    stopLocationTrackingButton.addClickListener(e -> {
      gmaps.stopTrackLocation();
      startLocationTrackingButton.setEnabled(true);
    });
    stopLocationTrackingButton.setEnabled(false);
    stopLocationTrackingButton.setDisableOnClick(true);
    add(new HorizontalLayout(startLocationTrackingButton, stopLocationTrackingButton));

    // create marker to track location
    GoogleMapMarker locationMarker = new GoogleMapMarker();
    locationMarker.setCaption("You're here");
    locationMarker.setDraggable(false);
    gmaps.addMarker(locationMarker);

    // add listener to show notification as track location is activated
    gmaps.addLocationTrackingActivatedEventListener(ev -> {
      Notification
          .show("Location tracking was activated with track id: " + gmaps.getTrackLocationId());
    });

    // add listener to know when location was updated and update location marker position
    gmaps.addCurrentLocationEventListener(e -> {
      locationMarker.setPosition(e.getSource().getCenter());
    });

    // add listener to capture geolocation error
    gmaps.addGeolocationErrorEventListener(e -> Notification.show(e.isBrowserHasGeolocationSupport()
        ? "The geolocation service failed on retrieving your location."
        : "Your browser doesn't support geolocation."));
  }
}
