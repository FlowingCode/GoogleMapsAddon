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
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

/**
 * Describes how icons are to be rendered on a line (for polygons and polylines).
 */
@Getter
@Setter
public class IconSequence {

  /**
   * The icon to render on the line.
   */
  private Symbol symbol;

  /**
   * The distance between consecutive icons on the line. This distance may be expressed as a
   * percentage or in pixels. To disable repeating of the icon, specify '0'.
   */
  private String repeat;

  /**
   * If true, each icon in the sequence has the same fixed rotation regardless of the angle of the
   * edge on which it lies. If false, case each icon in the sequence is rotated to align with its
   * edge. Default value: false.
   */
  private boolean fixedRotation;

  /**
   * The distance from the start of the line at which an icon is to be rendered. This distance may
   * be expressed as a percentage or in pixels.
   */
  private String offset;
  

  public IconSequence(Symbol symbol) {
    this.symbol = symbol;
  }

  public IconSequence(Symbol symbol, String repeat) {
    this(symbol);
    this.repeat = repeat;
  }  
  
  protected JsonObject getJson() {
    JsonObject js = Json.createObject();
    JsonObject symbolJs = symbol.getJson();
    js.put("icon", symbolJs);
    Optional.ofNullable(getRepeat()).ifPresent(value -> js.put("repeat", value));
    Optional.ofNullable(isFixedRotation()).ifPresent(value -> js.put("fixedRotation", value)); 
    Optional.ofNullable(getOffset()).ifPresent(value -> js.put("offset", value)); 
    return js;    
  }
  
}
