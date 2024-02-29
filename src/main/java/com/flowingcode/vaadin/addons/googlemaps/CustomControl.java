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

import com.vaadin.flow.component.button.Button;
import elemental.json.Json;
import elemental.json.JsonObject;
import java.io.Serializable;
import java.util.Optional;

/**
 * Represents a custom control that can be added to the map.
 * 
 * A button to represent the custom control and the {@link ControlPosition position} where the
 * button should be displayed within the map should be specified.
 * 
 */
public class CustomControl implements Serializable {

  private static final long serialVersionUID = -1821466465711569857L;

  private Button controlButton;

  private ControlPosition controlPosition;

  public CustomControl(Button controlButton, ControlPosition controlPosition) {
    this.controlButton = controlButton;
    this.controlPosition = controlPosition;
  }

  public Button getControlButton() {
    return controlButton;
  }

  public void setControlButton(Button controlButton) {
    this.controlButton = controlButton;
  }

  public ControlPosition getControlPosition() {
    return controlPosition;
  }

  public void setControlPosition(ControlPosition controlPosition) {
    this.controlPosition = controlPosition;
  }

  protected JsonObject getJson(int id) {
    JsonObject js = Json.createObject();
    js.put("id", id);
    Optional.ofNullable(controlPosition)
        .ifPresent(value -> js.put("position", controlPosition.name()));
    return js;
  }

}
