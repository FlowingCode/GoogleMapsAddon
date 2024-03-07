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

import elemental.json.Json;
import elemental.json.JsonObject;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Two-dimensional size, where width is the distance on the x-axis, and height is the distance on
 * the y-axis.
 */
@Getter
@Setter
@AllArgsConstructor
@SuppressWarnings("serial")
public class Size implements Serializable {

  /**
   * The width along the x-axis, in pixels.
   */
  private Double width;

  /**
   * The height along the y-axis, in pixels.
   */
  private Double height;

  protected JsonObject getSizeJson() {
    JsonObject sizeJs = Json.createObject();
    sizeJs.put("width", width);
    sizeJs.put("height", height);
    return sizeJs;
  }
}
