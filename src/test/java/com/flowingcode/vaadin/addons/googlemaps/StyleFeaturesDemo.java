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
import com.flowingcode.vaadin.addons.googlemaps.maptypestyle.ElementType;
import com.flowingcode.vaadin.addons.googlemaps.maptypestyle.FeatureType;
import com.flowingcode.vaadin.addons.googlemaps.maptypestyle.MapStyle;
import com.flowingcode.vaadin.addons.googlemaps.maptypestyle.StyleRules;
import com.flowingcode.vaadin.addons.googlemaps.maptypestyle.Visibility;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Style Features")
@DemoSource
@Route(value = "googlemaps/stylefeatures", layout = GooglemapsDemoView.class)
@SuppressWarnings("serial")
public class StyleFeaturesDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps1 = createGoogleMap(apiKey);
    VerticalLayout withoutStyleChanges =
        new VerticalLayout(createSpan("Map without style changes"), gmaps1);
    withoutStyleChanges.setSizeFull();

    GoogleMap gmaps2 = createGoogleMap(apiKey);
    VerticalLayout withStyleChanges = new VerticalLayout(
        createSpan("Hide all POIs and sets color and saturation to labels of the roads"), gmaps2);
    withStyleChanges.setSizeFull();

    // create rules to hide POIs (points of interest)
    StyleRules styleRule = new StyleRules();
    styleRule.setVisibility(Visibility.OFF);
    MapStyle mapStyle = new MapStyle(FeatureType.POI, styleRule);

    // create rule to style local roads
    StyleRules styleRule2 = new StyleRules();
    styleRule2.setColor("#a98fcf");
    styleRule2.setSaturation(50);
    MapStyle mapStyle2 = new MapStyle(FeatureType.ROAD, ElementType.LABELS_TEXT_STROKE, styleRule2);

    // add the rules to the map
    gmaps2.setMapStyle(mapStyle, mapStyle2);

    VerticalLayout verticalLayout = new VerticalLayout(withoutStyleChanges, withStyleChanges);
    verticalLayout.setSizeFull();
    add(verticalLayout);
  }

  private GoogleMap createGoogleMap(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.ROADMAP);
    gmaps.setSizeFull();
    gmaps.setCenter(new LatLon(-31.635175, -60.698405));
    gmaps.setZoom(16);
    return gmaps;
  }

  private Span createSpan(String text) {
    Span span = new Span(text);
    span.getElement().getStyle().set("font-weight", "bold");
    return span;
  }

}
