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
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Disable UI controls Demo")
@DemoSource
@Route(value = "googlemaps/disablecontrols", layout = GooglemapsDemoView.class)
@SuppressWarnings("serial")
public class DisableUIControlsDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.SATELLITE);
    gmaps.setSizeFull();
    gmaps.setCenter(new LatLon(-31.636036, -60.7055271));

    FlexLayout layout = new FlexLayout();
    layout.setFlexWrap(FlexWrap.WRAP);

    Checkbox disableMapTypeControl =
        new Checkbox("Disable map type control", e -> gmaps.disableMapTypeControl(e.getValue()));
    Checkbox disableStreetViewControl = new Checkbox("Disable street view control",
        e -> gmaps.disableStreetViewControl(e.getValue()));
    Checkbox disableZoomControl =
        new Checkbox("Disable zoom control", e -> gmaps.disableZoomControl(e.getValue()));
    Checkbox disableRotateControl =
        new Checkbox("Disable rotate control", e -> gmaps.disableRotateControl(e.getValue()));
    Checkbox disableFullScreenControl = new Checkbox("Disable full screen control",
        e -> gmaps.disableFullScreenControl(e.getValue()));
    Checkbox disableScaleControls =
        new Checkbox("Disable scale control", e -> gmaps.disableScaleControl(e.getValue()));

    layout.add(disableMapTypeControl, disableFullScreenControl, disableRotateControl,
        disableStreetViewControl, disableZoomControl, disableScaleControls);

    add(gmaps, layout);
  }
}
