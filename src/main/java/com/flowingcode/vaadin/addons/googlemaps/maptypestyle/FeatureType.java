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
 * Enum representing supported feature types. Features are geographic characteristics on the
 * map, including roads, parks, bodies of water, businesses, and more.
 */
public enum FeatureType {

  /**
   * Selects all features. Is the default option.
   */
  ALL("all"),

  /**
   * Selects all administrative areas. Styling affects only the labels of administrative areas, not
   * the geographical borders or fill.
   */
  ADMINISTRATIVE("administrative"),

  /**
   * Selects countries.
   */
  ADMINISTRATIVE_COUNTRY("administrative.country"),

  /**
   * Selects land parcels.
   */
  ADMINISTRATIVE_LAND_PARCEL("administrative.land_parcel"),


  /**
   * Selects localities.
   */
  ADMINISTRATIVE_LOCALITY("administrative.locality"),


  /**
   * Selects neighborhoods.
   */
  ADMINISTRATIVE_NEIGHBORHOOD("administrative.neighborhood"),


  /**
   * Selects provinces.
   */
  ADMINISTRATIVE_PROVINCE("administrative.province"),

  /**
   * Selects all landscapes.
   */
  LANDSCAPE("landscape"),

  /**
   * Selects man-made features, such as buildings and other structures.
   */
  LANDSCAPE_MAN_MADE("landscape.man_made"),

  /**
   * Selects natural features, such as mountains, rivers, deserts, and glaciers..
   */
  LANDSCAPE_NATURAL("landscape.natural"),

  /**
   * Selects land cover features, the physical material that covers the earth's surface, such as
   * forests, grasslands, wetlands, and bare ground..
   */
  LANDSCAPE_NATURAL_LANDCOVER("landscape.natural.landcover"),

  /**
   * Selects terrain features of a land surface, such as elevation, slope, and orientation.
   */
  LANDSCAPE_NATURAL_TERRAIN("landscape.natural.terrain"),

  /**
   * Selects all points of interest.
   */
  POI("poi"),

  /**
   * Selects tourist attractions.
   */
  POI_ATTRACTION("poi.attraction"),

  /**
   * Selects businesses.
   */
  POI_BUSINESS("poi.business"),

  /**
   * Selects government buildings.
   */
  POI_GOVERNMENT("poi.government"),

  /**
   * Selects emergency services, including hospitals, pharmacies, police, doctors, and others.
   */
  POI_MEDICAL("poi.medical"),

  /**
   * Selects parks.
   */
  POI_PARK("poi.park"),

  /**
   * Selects places of worship, including churches, temples, mosques, and others.
   */
  POI_PLACE_OF_WORSHIP("poi.place_of_worship"),

  /**
   * Selects schools.
   */
  POI_SCHOOL("poi.school"),

  /**
   * Selects sports complexes.
   */
  POI_SPORTS_COMPLEX("poi.sports_complex"),

  /**
   * Selects all roads.
   */
  ROAD("road"),

  /**
   * Selects arterial roads.
   */
  ROAD_ARTERIAL("road.arterial"),

  /**
   * Selects highways.
   */
  ROAD_HIGHWAY("road.highway"),

  /**
   * Selects highways with controlled access.
   */
  ROAD_HIGHWAY_CONTROLLED_ACCESS("road.highway.controlled_access"),

  /**
   * Selects local roads.
   */
  ROAD_LOCAL("road.local"),

  /**
   * Selects all transit stations and lines.
   */
  TRANSIT("transit"),

  /**
   * Selects transit lines.
   */
  TRANSIT_LINE("transit.line"),

  /**
   * Selects transit stations.
   */
  TRANSIT_STATION("transit.station"),

  /**
   * Selects airports.
   */
  TRANSIT_STATION_AIRPORT("transit.station.airport"),

  /**
   * Selects bus stops.
   */
  TRANSIT_STATION_BUS("transit.station.bus"),

  /**
   * Selects rail stations.
   */
  TRANSIT_STATION_RAIL("transit.station.rail"),

  /**
   * Selects bodies of water.
   */
  WATER("water");

  @Getter
  private String value;

  FeatureType(String value) {
    this.value = value;
  }

}
