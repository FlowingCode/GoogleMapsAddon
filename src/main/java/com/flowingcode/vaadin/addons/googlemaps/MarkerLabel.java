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
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;
import java.util.Optional;

/**
 * Class representing a marker label.
 * 
 * @see <a href=
 *      "https://developers.google.com/maps/documentation/javascript/reference/marker#MarkerLabel">Google
 *      Maps MarkerLabel</a>
 */
@Getter
@Setter
public class MarkerLabel {
  /**
   * The text to be displayed in the label. Required field.
   */
  @NonNull
  private String text;

  /**
   * Optional. The color of the label text. Defaults to: <code>'black'</code>.
   */
  private String color;

  /**
   * Optional. The font family of the label text (equivalent to the CSS font-family property).
   */
  private String fontFamily;

  /**
   * Optional. The font size of the label text (equivalent to the CSS font-size property). Defaults
   * to: <code>'14px'</code>.
   */
  private String fontSize;

  /**
   * Optional. The font weight of the label text (equivalent to the CSS font-weight property).
   */
  private String fontWeight;

  /**
   * The className property of the label's element (equivalent to the element's class attribute).
   * Multiple space-separated CSS classes can be added. The font color, size, weight, and family can
   * only be set via the other properties of <code>MarkerLabel</code>.
   * 
   * <p>
   * <strong>Note:</strong> At the moment, there is no direct way to apply the className by
   * importing a stylesheet into the component's shadow root. For alternative workarounds, see
   * <a href="https://github.com/FlowingCode/GoogleMapsAddon/issues/159#issuecomment-3325302923">
   * this comment on GitHub</a>.
   */
  private String className;

  public MarkerLabel(String text) {
    this.text = Objects.requireNonNull(text, "Text cannot be null");
  }

  public MarkerLabel(String text, String color, String fontSize) {
    this.text = Objects.requireNonNull(text, "Text cannot be null");
    this.color = color;
    this.fontSize = fontSize;
  }

  protected JsonObject getJson() {
    JsonObject js = Json.createObject();
    Optional.of(getText()).ifPresent(value -> js.put("text", value));
    Optional.ofNullable(getColor()).ifPresent(value -> js.put("color", value));
    Optional.ofNullable(getFontFamily()).ifPresent(value -> js.put("fontFamily", value));
    Optional.ofNullable(getFontSize()).ifPresent(value -> js.put("fontSize", value));
    Optional.ofNullable(getFontWeight()).ifPresent(value -> js.put("fontWeight", value));
    Optional.ofNullable(getClassName()).ifPresent(value -> js.put("className", value));
    return js;
  }
}
