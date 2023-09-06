[![Published on Vaadin Directory](https://img.shields.io/badge/Vaadin%20Directory-published-00b4f0.svg)](https://vaadin.com/directory/component/google-maps-addon)
[![Stars on vaadin.com/directory](https://img.shields.io/vaadin-directory/star/google-maps-addon.svg)](https://vaadin.com/directory/component/google-maps-addon)
[![Build Status](https://jenkins.flowingcode.com/job/GoogleMaps-14-addon/badge/icon)](https://jenkins.flowingcode.com/job/GoogleMaps-14-addon)

# Google Maps Add-on

Vaadin Flow add-on for [Google Maps Web Component](https://github.com/FlowingCode/google-map).

## Features

* Programmatic server-side panning and zooming
* Add/remove markers
* Add/remove polygons  
* Show info window on markers
* Geolocation
* Add draggable markers
* Define clustering for markers
* Enable/disable UI controls
* Define size of UI controls
* Kml support
* Tilt & rotation support
* MapId support
* Obtain map bounds
* & more

## Online demo

[Online demo here](http://addonsv24.flowingcode.com/googlemaps)

## Download release

[Available in Vaadin Directory](https://vaadin.com/directory/component/google-maps-addon)

### Maven install

Add the following dependencies in your pom.xml file:

```xml
<dependency>
   <groupId>com.flowingcode.vaadin.addons</groupId>
   <artifactId>google-maps</artifactId>
   <version>X.Y.Z</version>
</dependency>
```
<!-- the above dependency should be updated with latest released version information -->

```xml
<repository>
   <id>vaadin-addons</id>
   <url>https://maven.vaadin.com/vaadin-addons</url>
</repository>
```

For SNAPSHOT versions see [here](https://maven.flowingcode.com/snapshots/).

## Building and running demo

- git clone repository
- mvn clean install jetty:run

To see the demo, navigate to http://localhost:8080/

## Release notes

See [here](https://github.com/FlowingCode/GoogleMapsAddon/releases)

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome, but there are no guarantees that they are accepted as such. 

As first step, please refer to our [Development Conventions](https://github.com/FlowingCode/DevelopmentConventions) page to find information about Conventional Commits & Code Style requeriments.

Then, follow these steps for creating a contribution:

- Fork this project.
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- For commit message, use [Conventional Commits](https://github.com/FlowingCode/DevelopmentConventions/blob/main/conventional-commits.md) to describe your change.
- Send a pull request for the original project.
- Comment on the original issue that you have implemented a fix for it.

## License & Author

This add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.

Google Maps Add-on is written by Flowing Code S.A.

# Developer Guide

## Getting started

Create an instance of GoogleMap, configure it, add markers, polygons, etc:
```java
GoogleMap gmaps = new GoogleMap(apiKey,null,null);
gmaps.setMapType(MapType.SATELLITE);
gmaps.setSizeFull();
gmaps.setCenter(new LatLon(0,0));
gmaps.addMarker("Center", new LatLon(0,0), true, "");
GoogleMapPolygon gmp = gmaps.addPolygon(Arrays.asList(new GoogleMapPoint(gmaps.getCenter()),
new GoogleMapPoint(gmaps.getCenter().getLat(),gmaps.getCenter().getLon()+1),
new GoogleMapPoint(gmaps.getCenter().getLat()+1,gmaps.getCenter().getLon())));
gmp.addClickListener(ev->Notification.show("polygon clicked"));
```

## Special configuration when using Spring

By default, Vaadin Flow only includes ```com/vaadin/flow/component``` to be always scanned for UI components and views. For this reason, the add-on might need to be whitelisted in order to display correctly. 

To do so, just add ```com.flowingcode``` to the ```vaadin.whitelisted-packages``` property in ```src/main/resources/application.properties```, like:

```vaadin.whitelisted-packages = com.vaadin,org.vaadin,dev.hilla,com.flowingcode```
 
More information on Spring whitelisted configuration [here](https://vaadin.com/docs/latest/integrations/spring/configuration/#configure-the-scanning-of-packages).
