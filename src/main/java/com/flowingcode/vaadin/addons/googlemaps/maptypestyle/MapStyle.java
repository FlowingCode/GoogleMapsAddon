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
import elemental.json.JsonObject;
import java.io.Serializable;
import java.util.Optional;

/**
 * Representation for the collection of selectors and stylers that define how the map should be
 * styled.
 * 
 * <p>A map style consists of one selector and its corresponding style rules. Selectors specify 
 * the map features and/or elements that should be affected, while the style rule define how those features 
 * and elements should be modified by using stylers. </p>
 * 
 * <p>This class provides methods to convert the style information into a JSON representation.</p>
 */
public class MapStyle implements Serializable {

  private static final long serialVersionUID = -3352482751629553500L;

  private FeatureType featureType;

  private ElementType elementType;

  private StyleRules styleRules;

  /**
   * Creates a new {@code MapStyle} with the specified feature type and style rules.
   *
   * @param featureType the feature type to be styled
   * @param styleRules the style rules to apply
   */
  public MapStyle(FeatureType featureType, StyleRules styleRules) {
    this.styleRules = styleRules;
    this.featureType = featureType;
  }

  /**
   * Creates a new {@code MapStyle} with the specified feature type, element type, and style rules.
   *
   * @param featureType the feature type to be styled
   * @param elementType the element type to be styled
   * @param styleRules the style rules to apply
   */
  public MapStyle(FeatureType featureType, ElementType elementType, StyleRules styleRules) {
    this(featureType, styleRules);
    this.elementType = elementType;
  }

  /**
   * Converts the {@code MapStyle} to a JSON object.
   *
   * <p>This method includes the feature type, element type, and style rules, if present.</p>
   *
   * @return a {@code JsonObject} representing the map style
   */
  public JsonObject getJson() {
    JsonObject jsonObject = Json.createObject();
    Optional.ofNullable(featureType)
        .ifPresent(v -> jsonObject.put("featureType", v.getValue()));
    Optional.ofNullable(elementType)
        .ifPresent(v -> jsonObject.put("elementType", v.getValue()));
    Optional.ofNullable(styleRules)
        .ifPresent(v -> jsonObject.put("stylers", styleRules.getJson()));
    return jsonObject;
  }
}
