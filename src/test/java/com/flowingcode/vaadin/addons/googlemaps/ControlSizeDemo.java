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
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Control Size Demo")
@DemoSource
@Route(value = "googlemaps/controlsize", layout = GooglemapsDemoView.class)
@SuppressWarnings("serial")
public class ControlSizeDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    
    GoogleMap gmaps1 = createGoogleMap(apiKey);
    gmaps1.setControlSize(20);    
    VerticalLayout controlSizeLayout = new VerticalLayout(createSpan("Map with custom size value for controls"), gmaps1);
    controlSizeLayout.setSizeFull();
    
    GoogleMap gmaps2 = createGoogleMap(apiKey);    
    VerticalLayout noControlSizeLayout = new VerticalLayout(createSpan("Map with default size value for controls"), gmaps2);
    noControlSizeLayout.setSizeFull();
    
    HorizontalLayout horizontalLayout = new HorizontalLayout(controlSizeLayout, noControlSizeLayout);
    horizontalLayout.setSizeFull();
    add(horizontalLayout);
  }
  
  private GoogleMap createGoogleMap(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.ROADMAP);
    gmaps.setSizeFull();
    gmaps.setZoom(5);
    gmaps.setCenter(new LatLon(-35.198155, -65.776366));
    return gmaps;
  }
    
  private Span createSpan(String text) {
    Span span = new Span(text);
    span.getElement().getStyle().set("font-weight", "bold");
    return span;    
  }

}
