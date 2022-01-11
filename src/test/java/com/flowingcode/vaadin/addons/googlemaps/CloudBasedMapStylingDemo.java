package com.flowingcode.vaadin.addons.googlemaps;

import com.flowingcode.vaadin.addons.demo.DemoSource;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMap.MapType;
import com.vaadin.flow.router.PageTitle;

@PageTitle("MapId Styling Demo")
@DemoSource(
    "https://github.com/FlowingCode/GoogleMapsAddon/blob/master/src/test/java/com/flowingcode/vaadin/addons/googlemaps/CloudBasedMapStylingDemo.java")
@SuppressWarnings("serial")
public class CloudBasedMapStylingDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.ROADMAP);
    gmaps.setSizeFull();
    gmaps.setCenter(new LatLon(-31.636036, -60.7055271));
    gmaps.setMapId("8e0a97af9386fef");
    add(gmaps);
  }

}
