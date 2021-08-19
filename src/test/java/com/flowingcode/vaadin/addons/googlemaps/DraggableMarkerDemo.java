package com.flowingcode.vaadin.addons.googlemaps;

import com.flowingcode.vaadin.addons.demo.DemoSource;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMap.MapType;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Draggable Marker Demo")
@DemoSource(
    "https://github.com/FlowingCode/GoogleMapsAddon/blob/master/src/test/java/com/flowingcode/vaadin/addons/googlemaps/DraggableMarkerDemo.java")
@SuppressWarnings("serial")
public class DraggableMarkerDemo extends VerticalLayout {
  
  public DraggableMarkerDemo() {
    this.setSizeFull();
    String apiKey = System.getProperty("google.maps.api");
    if (apiKey == null) {
      add(
          new H2(
              "Api key is needded to run the demo, pass it using the following system property: '-Dgoogle.maps.api=<your-api-key>'"));
    } else {
      GoogleMap gmaps = new GoogleMap(apiKey, null, null);
      gmaps.setMapType(MapType.ROADMAP);
      gmaps.setSizeFull();
      gmaps.setCenter(new LatLon(-31.636036, -60.7055271));
      
      gmaps.addMarker("Center", gmaps.getCenter(), true, Markers.PURPLE)
      .addDragEndEventListener(
          e -> 
            Notification.show("Lat: " + e.getLatitude() + " - Lng: " + e.getLongitude()));
                       
      add(gmaps);
    }
  }
}
