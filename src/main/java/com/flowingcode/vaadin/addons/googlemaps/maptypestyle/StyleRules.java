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

package com.flowingcode.vaadin.addons.googlemaps.maptypestyle;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import java.io.Serializable;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;


/**
 * Representation of the formatting options that can be applied to map features and elements.
 */
@Getter
@Setter
public class StyleRules implements Serializable {

  private static final long serialVersionUID = -7506228136304010510L;

  /**
   * Indicates whether and how the element appears on the map. Possible values: on, off, or
   * simplified.
   */
  private Visibility visibility;

  /**
   * An RGB hex string of format #RRGGBB that sets the color of the feature.
   */
  private String color;

  /**
   * An integer value, greater than or equal to zero to sets the weight of the feature, in pixels.
   */
  private Integer weight;

  /**
   * If true it inverts the existing lightness. This is useful, for example, for quickly switching
   * to a darker map with white text.
   */
  private boolean invertLightness;

  /**
   * Indicates the amount of gamma correction to apply to the element (a floating point value
   * between 0.01 and 10.0, where 1.0 applies no correction).
   */
  private double gamma;

  /**
   * Indicates the percentage change in intensity of the basic color to apply to the element (a
   * floating point value between -100 and 100).
   */
  private double saturation;

  /**
   * Indicates the percentage change in brightness of the element (a floating point value between
   * -100 and 100). Negative values increase darkness (where -100 specifies black) while positive
   * values increase brightness (where +100 specifies white).
   */
  private double lightness;

  /**
   * An RGB hex string of format #RRGGBB that indicates the basic color.
   */
  private String hue;

  /**
   * Converts {@code StyleRules} to a JSON array.
   *
   * @return a {@code JsonArray} representing the style rules array
   */
  protected JsonArray getJson() {
    JsonArray jsonArray = Json.createArray();
    Optional.ofNullable(visibility).ifPresent(v -> {
      JsonObject js = Json.createObject();
      js.put(Stylers.VISIBILITY.getValue(), v.getValue());
      jsonArray.set(jsonArray.length(), js);
    });
    Optional.ofNullable(color).ifPresent(v -> {
      JsonObject js = Json.createObject();
      js.put(Stylers.COLOR.getValue(), v);
      jsonArray.set(jsonArray.length(), js);
    });
    Optional.ofNullable(weight).ifPresent(v -> {
      JsonObject js = Json.createObject();
      js.put(Stylers.WEIGHT.getValue(), v);
      jsonArray.set(jsonArray.length(), js);
    });
    Optional.ofNullable(invertLightness).ifPresent(v -> {
      JsonObject js = Json.createObject();
      js.put(Stylers.INVERT_LIGHTNESS.getValue(), v);
      jsonArray.set(jsonArray.length(), js);
    });
    Optional.ofNullable(gamma).ifPresent(v -> {
      JsonObject js = Json.createObject();
      js.put(Stylers.GAMMA.getValue(), v);
      jsonArray.set(jsonArray.length(), js);
    });
    Optional.ofNullable(saturation).ifPresent(v -> {
      JsonObject js = Json.createObject();
      js.put(Stylers.SATURATION.getValue(), v);
      jsonArray.set(jsonArray.length(), js);
    });
    Optional.ofNullable(lightness).ifPresent(v -> {
      JsonObject js = Json.createObject();
      js.put(Stylers.LIGHTNESS.getValue(), v);
      jsonArray.set(jsonArray.length(), js);
    });
    Optional.ofNullable(hue).ifPresent(v -> {
      JsonObject js = Json.createObject();
      js.put(Stylers.HUE.getValue(), v);
      jsonArray.set(jsonArray.length(), js);
    });
    return jsonArray;
  }

}
