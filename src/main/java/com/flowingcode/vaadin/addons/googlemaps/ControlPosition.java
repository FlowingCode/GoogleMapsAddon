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

/**
 * Enum representing supported control positions for map control buttons. 
 */
public enum ControlPosition {

  /**
   * Indicates that the control should be placed along the top center of the map.
   */
  TOP_CENTER,

  /**
   * Indicates that the control should be placed along the top left of the map, with any
   * sub-elements of the control "flowing" towards the top center.
   */
  TOP_LEFT,

  /**
   * Indicates that the control should be placed along the top right of the map, with any
   * sub-elements of the control "flowing" towards the top center.
   */
  TOP_RIGHT,

  /**
   * Indicates that the control should be placed along the top left of the map, but below any
   * TOP_LEFT elements.
   */
  LEFT_TOP,

  /**
   * Indicates that the control should be placed along the top right of the map, but below any
   * TOP_RIGHT elements.
   */
  RIGHT_TOP,

  /**
   * Indicates that the control should be placed along the left side of the map, centered between
   * the TOP_LEFT and BOTTOM_LEFT positions
   */
  LEFT_CENTER,

  /**
   * Indicates that the control should be placed along the right side of the map, centered between
   * the TOP_RIGHT and BOTTOM_RIGHT positions.
   */
  RIGHT_CENTER,

  /**
   * Indicates that the control should be placed along the bottom left of the map, but above any
   * BOTTOM_LEFT elements.
   */
  LEFT_BOTTOM,

  /**
   * Indicates that the control should be placed along the bottom right of the map, but above any
   * BOTTOM_RIGHT elements.
   */
  RIGHT_BOTTOM,

  /**
   * Indicates that the control should be placed along the bottom center of the map.
   */
  BOTTOM_CENTER,

  /**
   * Indicates that the control should be placed along the bottom left of the map, with any
   * sub-elements of the control "flowing" towards the bottom center.
   */
  BOTTOM_LEFT,

  /**
   * Indicates that the control should be placed along the bottom right of the map, with any
   * sub-elements of the control "flowing" towards the bottom center.
   */
  BOTTOM_RIGHT;

}
