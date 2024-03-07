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

import com.flowingcode.vaadin.addons.googlemaps.util.JsonIconUtils;
import elemental.json.Json;
import elemental.json.JsonObject;
import java.util.Optional;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Describes a symbol, which consists of a vector path with styling. A symbol can be used as the
 * icon of a marker, or placed on a polygon or polyline.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class Symbol implements GoogleMapIcon {

  /**
   * A custom path expressed using SVG path notation. Required.
   */
  @NonNull
  private String path;

  /**
   * The position of the symbol relative to the marker or polyline. The coordinates of the symbol's
   * path are translated left and up by the anchor's x and y coordinates respectively. The position
   * is expressed in the same coordinate system as the symbol's path.
   */
  private GoogleMapPoint anchor;

  /**
   * The origin of the label relative to the origin of the path, if label is supplied by the marker.
   * The origin is expressed in the same coordinate system as the symbol's path. This property is
   * unused for symbols on polylines.
   */
  private GoogleMapPoint labelOrigin;

  /**
   * The symbol's fill color.
   */
  private String fillColor;

  /**
   * The symbol's fill opacity.
   */
  private Double fillOpacity;

  /**
   * The angle by which to rotate the symbol, expressed clockwise in degrees.
   */
  private Double rotation;

  /**
   * The amount by which the symbol is scaled in size.
   */
  private Double scale;

  /**
   * The symbol's stroke color.
   */
  private String strokeColor;

  /**
   * The symbol's stroke opacity.
   */
  private Double strokeOpacity;

  /**
   * The symbol's stroke weight.
   * 
   */
  private Double strokeWeight;
  
  public Symbol(String path, String strokeColor, String fillColor, Double fillOpacity) {
    this(path);
    this.strokeColor = strokeColor;
    this.fillColor = fillColor;
    this.fillOpacity = fillOpacity;
  }

  @Override
  public JsonObject getJson() {
    JsonObject js = Json.createObject();
    js.put("path", path);
    Optional.ofNullable(getAnchor()).ifPresent(value -> {
      js.put("anchor", JsonIconUtils.getPointJson(value));
    });
    Optional.ofNullable(getLabelOrigin()).ifPresent(value -> {
      js.put("labelOrigin", JsonIconUtils.getPointJson(value));
    });
    Optional.ofNullable(getFillColor()).ifPresent(value -> js.put("fillColor", value));
    Optional.ofNullable(getFillOpacity()).ifPresent(value -> js.put("fillOpacity", value));
    Optional.ofNullable(getRotation()).ifPresent(value -> js.put("rotation", value));
    Optional.ofNullable(getScale()).ifPresent(value -> js.put("scale", value));
    Optional.ofNullable(getStrokeColor()).ifPresent(value -> js.put("strokeColor", value));
    Optional.ofNullable(getStrokeOpacity()).ifPresent(value -> js.put("strokeOpacity", value));
    Optional.ofNullable(getStrokeWeight()).ifPresent(value -> js.put("strokeWeight", value));
    return js;
  }

}
