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
