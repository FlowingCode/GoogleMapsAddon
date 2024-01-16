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

import java.io.Serializable;
import elemental.json.JsonObject;

/**
 * Class representing bound coordinates.
 */
@SuppressWarnings("serial")
public class LatLonBounds implements Serializable {

  private double south;
  private double west;
  private double north;
  private double east;

  public LatLonBounds() {}

  public LatLonBounds(JsonObject bounds) {
    setSouth(bounds.getNumber("south"));
    setWest(bounds.getNumber("west"));
    setNorth(bounds.getNumber("north"));
    setEast(bounds.getNumber("east"));
  }

  public double getSouth() {
    return south;
  }

  public void setSouth(double south) {
    this.south = south;
  }

  public double getWest() {
    return west;
  }

  public void setWest(double west) {
    this.west = west;
  }

  public double getNorth() {
    return north;
  }

  public void setNorth(double north) {
    this.north = north;
  }

  public double getEast() {
    return east;
  }

  public void setEast(double east) {
    this.east = east;
  }

  @Override
  public String toString() {
    return "LatLon [south=" + south + ", west=" + west + ", north=" + north + ", east=" + east
        + "]";
  }
}
