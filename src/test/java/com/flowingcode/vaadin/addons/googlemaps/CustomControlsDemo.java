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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Custom Controls Demo")
@DemoSource
@DemoSource(
    value = "/src/test/resources/META-INF/resources/frontend/styles/google-maps/custom-controls-demo-styles.css",
    caption = "custom-controls-demo-styles.css")
@Route(value = "googlemaps/custom-controls", layout = GooglemapsDemoView.class)
@CssImport("./styles/google-maps/custom-controls-demo-styles.css")
@SuppressWarnings("serial")
public class CustomControlsDemo extends AbstractGoogleMapsDemo {

  @Override
  protected void createGoogleMapsDemo(String apiKey) {
    GoogleMap gmaps = new GoogleMap(apiKey, null, null);
    gmaps.setMapType(MapType.ROADMAP);
    gmaps.setSizeFull();
    add(gmaps);

    Button customControlButton1 = new Button("Custom Control 1");
    customControlButton1.setClassName("custom-control-button");
    CustomControl customControl1 =
        new CustomControl(customControlButton1, ControlPosition.TOP_CENTER);
    Button customControlButton2 = new Button("Custom Control 2");
    customControlButton2.setClassName("custom-control-button");
    CustomControl customControl2 =
        new CustomControl(customControlButton2, ControlPosition.LEFT_CENTER);
    gmaps.setCustomControls(customControl1, customControl2);

    Button customControlButton3 = new Button("Custom Control 3");
    customControlButton3.setClassName("custom-control-button");
    CustomControl customControl3 =
        new CustomControl(customControlButton3, ControlPosition.BOTTOM_CENTER);

    Button addCustomControl3Button = createDemoButton("Add Custom Control 3");
    Button removeCustomControl3Button = createDemoButton("Remove Custom Control 3");
    Button removeAllCustomControlsButton = createDemoButton("Remove all controls");
    Button resetButton = createDemoButton("Reset");
    
    addCustomControl3Button.addClickListener(e -> {
      gmaps.addCustomControl(customControl3);
      removeCustomControl3Button.setEnabled(true); // hide-source
      removeAllCustomControlsButton.setEnabled(true); // hide-source
    });  
    
    removeCustomControl3Button.addClickListener(e -> {
      gmaps.removeCustomControl(customControl3);
      addCustomControl3Button.setEnabled(true); // hide-source
    });
    removeCustomControl3Button.setEnabled(false); 
    
    removeAllCustomControlsButton.addClickListener(e -> {
      gmaps.removeCustomControls();
      addCustomControl3Button.setEnabled(true); // hide-source
      removeCustomControl3Button.setEnabled(false); // hide-source
      resetButton.setEnabled(true); // hide-source
    });

    resetButton.addClickListener(e -> {
      gmaps.setCustomControls(customControl1, customControl2);
      removeAllCustomControlsButton.setEnabled(true); // hide-source
      addCustomControl3Button.setEnabled(true); // hide-source
      removeCustomControl3Button.setEnabled(false); // hide-source
    });
   
    add(new HorizontalLayout(addCustomControl3Button, removeCustomControl3Button,
        removeAllCustomControlsButton, resetButton));
  }

  private Button createDemoButton(String caption) {
    Button button = new Button(caption);
    button.setDisableOnClick(true);
    return button;
  }
}
