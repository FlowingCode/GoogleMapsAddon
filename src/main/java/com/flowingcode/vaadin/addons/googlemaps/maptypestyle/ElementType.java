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

import lombok.Getter;

/**
 * Enum representing supported element types. Elements are subdivisions of a feature.
 */
public enum ElementType {

  /**
   * Selects all elements of the specified feature. Is the default option.
   */
  ALL("all"),

  /**
   * Selects all geometric elements of the specified feature.
   */
  GEOMETRY("geometry"),

  /**
   * Selects only the fill of the feature's geometry.
   */
  GEOMETRY_FILL("geometry.fill"),

  /**
   * Selects only the stroke of the feature's geometry.
   */
  GEOMETRY_STROKE("geometry.stroke"),

  /**
   * Selects the textual labels associated with the specified feature.
   */
  LABELS("labels"),

  /**
   * Selects only the icon displayed within the feature's label.
   */
  LABELS_ICON("labels.icon"),

  /**
   * Selects only the text of the label.
   */
  LABELS_TEXT("labels.text"),

  /**
   * Selects only the fill of the label. The fill of a label is typically rendered as a colored
   * outline that surrounds the label text.
   */
  LABELS_TEXT_FILL("labels.text.fill"),

  /**
   * Selects only the stroke of the label's text.
   */
  LABELS_TEXT_STROKE("labels.text.stroke");

  @Getter
  private String value;

  ElementType(String value) {
    this.value = value;
  }

}
