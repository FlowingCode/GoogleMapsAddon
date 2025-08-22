/*-
 * #%L
 * Google Maps Addon
 * %%
 * Copyright (C) 2020 - 2025 Flowing Code
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Representation of optional parameters for a geolocation request.
 * 
 * @see <a href=
 *      https://developer.mozilla.org/en-US/docs/Web/API/Geolocation/getCurrentPosition#options">Options
 *      documentation</a>
 */
@Getter
@Setter
@AllArgsConstructor
public class GeolocationOptions {
  /**
   * A boolean value that indicates the application would like to receive the best possible results.
   * If true and if the device is able to provide a more accurate position, it will do so. This may
   * result in slower response times or increased power consumption.
   */
  private boolean enableHighAccuracy;

  /**
   * A positive long value representing the maximum length of time (in milliseconds) the device is
   * allowed to take in order to return a position. 
   * If null, the device won't timeout until the position is available.
   */
  private Long timeout;

  /**
   * A positive long value representing the maximum age (in milliseconds) of a possible cached
   * position that is acceptable to return.
   */
  private long maximumAge;

  /**
   * Converts the options to a JsonObject to be sent to the client-side.
   * 
   * @return a JsonObject representing the configured options
   */
  public JsonObject toJson() {
    JsonObject json = Json.createObject();
    json.put("enableHighAccuracy", enableHighAccuracy);
    json.put("maximumAge", maximumAge);
    if (timeout != null) {
      json.put("timeout", timeout);
    }
    return json;
  }
}
