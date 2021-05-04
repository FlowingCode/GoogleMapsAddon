/*-
 * #%L
 * Google Maps Addon
 * %%
 * Copyright (C) 2020 - 2021 Flowing Code
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
package com.flowingcode.vaadin.addons.googlemaps.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.flowingcode.vaadin.addons.DemoLayout;
import com.flowingcode.vaadin.addons.googlemaps.GooglemapsDemoView;
import com.vaadin.flow.router.Route;
import org.junit.Test;

public class LayoutTest {

  @Test
  public void testDemoLayout() {
    Route route = GooglemapsDemoView.class.getAnnotation(Route.class);
    assertEquals("com.flowingcode.vaadin.addons.DemoLayout", DemoLayout.class.getName());
    assertEquals(DemoLayout.class, route.layout());
    assertNotEquals("", route.value());
  }
}
