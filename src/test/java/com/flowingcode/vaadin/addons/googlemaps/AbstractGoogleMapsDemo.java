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
