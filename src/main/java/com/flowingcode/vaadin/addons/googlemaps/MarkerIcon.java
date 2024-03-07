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
 * A structure representing a Marker icon image.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class MarkerIcon implements GoogleMapIcon {

  /**
   * The URL of the image or sprite sheet.
   */
  @NonNull
  private String url;

  /**
   * The position at which to anchor an image in correspondence to the location of the marker on the
   * map. By default, the anchor is located along the center point of the bottom of the image.
   */
  private GoogleMapPoint anchor;

  /**
   * The origin of the label relative to the top-left corner of the icon image, if a label is
   * supplied by the marker. By default, the origin is located in the center point of the image.
   */
  private GoogleMapPoint labelOrigin;

  /**
   * The position of the image within a sprite, if any.
   */
  private GoogleMapPoint originPoint;

  /**
   * The display size of the sprite or image. When using sprites, you must specify the sprite size.
   * If the size is not provided, it will be set when the image loads.
   */
  private Size size;

  /**
   * The size of the entire image after scaling, if any. Use this property to stretch/shrink an
   * image or a sprite.
   */
  private Size scaledSize;

  @Override
  public JsonObject getJson() {
    JsonObject js = Json.createObject();
    js.put("url", url);
    Optional.ofNullable(getAnchor()).ifPresent(value -> {
      js.put("anchor", JsonIconUtils.getPointJson(value));
    });
    Optional.ofNullable(getLabelOrigin()).ifPresent(value -> {
      js.put("labelOrigin", JsonIconUtils.getPointJson(value));
    });
    Optional.ofNullable(getOriginPoint()).ifPresent(value -> {
      js.put("originPoint", JsonIconUtils.getPointJson(value));
    });
    Optional.ofNullable(getSize()).ifPresent(value -> {
      js.put("size", value.getSizeJson());
    });
    Optional.ofNullable(getScaledSize()).ifPresent(value -> {
      js.put("scaledSize", value.getSizeJson());
    });
    return js;
  }

}
