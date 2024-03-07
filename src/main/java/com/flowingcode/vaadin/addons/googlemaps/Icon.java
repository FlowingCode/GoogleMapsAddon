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

/**
 * @deprecated, use {@link IconSequence} instead.
 */
@Deprecated
public class Icon extends Symbol {

  private int repeat;

  public Icon(String path, String strokeColor, String fillColor, int fillOpacity, int repeat) {
    super(path);
    this.setStrokeColor(strokeColor);
    this.setFillColor(fillColor);
    this.setFillOpacity(Double.valueOf(fillOpacity));
    this.repeat = repeat;
  }

  public int getRepeat() {
    return repeat;
  }

  public void setRepeat(int repeat) {
    this.repeat = repeat;
  }

  @Override
  public JsonObject getJson() {
    JsonObject js = Json.createObject();
    JsonObject iconJs = super.getJson();
    js.put("icon", iconJs);
    Optional.ofNullable(getRepeat()).ifPresent(v -> js.put("repeat", v + "px"));
    return js;
  }
}
