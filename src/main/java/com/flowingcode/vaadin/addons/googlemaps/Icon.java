/*-
 * #%L
 * Google Maps Addon
 * %%
 * Copyright (C) 2020 - 2023 Flowing Code
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

public class Icon {

  private String path;
  private String strokeColor;
  private String fillColor;
  private int fillOpacity;
  private int repeat;

  public Icon(String path, String strokeColor, String fillColor, int fillOpacity, int repeat) {
    super();
    this.path = path;
    this.strokeColor = strokeColor;
    this.fillColor = fillColor;
    this.fillOpacity = fillOpacity;
    this.repeat = repeat;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getStrokeColor() {
    return strokeColor;
  }

  public void setStrokeColor(String strokeColor) {
    this.strokeColor = strokeColor;
  }

  public String getFillColor() {
    return fillColor;
  }

  public void setFillColor(String fillColor) {
    this.fillColor = fillColor;
  }

  public int getFillOpacity() {
    return fillOpacity;
  }

  public void setFillOpacity(int fillOpacity) {
    this.fillOpacity = fillOpacity;
  }

  public int getRepeat() {
    return repeat;
  }

  public void setRepeat(int repeat) {
    this.repeat = repeat;
  }

  protected JsonObject getJson() {
    JsonObject options = Json.createObject();
    Optional.ofNullable(getPath()).ifPresent(value -> options.put("path", value));
    Optional.ofNullable(getStrokeColor()).ifPresent(value -> options.put("strokeColor", value));
    Optional.ofNullable(getFillColor()).ifPresent(value -> options.put("fillColor", value));
    Optional.ofNullable(getFillOpacity()).ifPresent(value -> options.put("fillOpacity", value));
        
    JsonObject js = Json.createObject();
    js.put("icon", options);
    Optional.ofNullable(getRepeat()).ifPresent(v -> js.put("repeat", v + "px"));
    return js;    
  }
}
