package com.flowingcode.vaadin.addons.googlemaps;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@SuppressWarnings("serial")
@StyleSheet("context://frontend/styles/google-maps/demo-styles.css")
public abstract class AbstractGoogleMapsDemo extends VerticalLayout {

  public AbstractGoogleMapsDemo() {
    this.setSizeFull();
    String apiKey = System.getProperty("google.maps.api");
    if (apiKey == null) {
      add(
          new H2(
              "Api key is needded to run the demo, pass it using the following system property: '-Dgoogle.maps.api=<your-api-key>'"));
    } else {
      createGoogleMapsDemo(apiKey);
    }
  }

  protected abstract void createGoogleMapsDemo(String apiKey);
}
