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

import com.vaadin.flow.component.combobox.ComboBox;
import java.util.Collection;

public class ReflectionUtil {

  /** Reflective call in order to mantain binary compatibility with Vaadin 14 - 24 */
  static void setItems(ComboBox<String> combobox, Collection<String> items) {
    try {
      ComboBox.class.getMethod("setItems", Collection.class).invoke(combobox, items);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
