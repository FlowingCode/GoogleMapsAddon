/*-
 * #%L
 * Google Maps Addon
 * %%
 * Copyright (C) 2020 Flowing Code
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

import java.util.Arrays;

import com.flowingcode.vaadin.addons.googlemaps.GoogleMap;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMapPoint;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMapPolygon;
import com.flowingcode.vaadin.addons.googlemaps.LatLon;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMap.MapType;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@Route("")
public class DemoView extends VerticalLayout {

    public DemoView() {
    	this.setSizeFull();
    	String apiKey = System.getProperty("google.maps.api");
    	if (apiKey==null) {
    		add(new H2("Api key is needded to run the demo, pass it using the following system property: '-Dgoogle.maps.api=<your-api-key>'"));
    	} else {
            GoogleMap gmaps = new GoogleMap(apiKey,null,null);
            gmaps.setMapType(MapType.SATELLITE);
            gmaps.setSizeFull();
            gmaps.setCenter(new LatLon(-31.636036,-60.7055271));
            gmaps.addMarker("Center", new LatLon(-31.636036,-60.7055271), true, "https://www.flowingcode.com/wp-content/uploads/2020/06/FCMarker.png");
            GoogleMapPolygon gmp = gmaps.addPolygon(Arrays.asList(
	    		new GoogleMapPoint(gmaps.getCenter().getLat(),gmaps.getCenter().getLon()+1),
	    		new GoogleMapPoint(gmaps.getCenter().getLat()+1,gmaps.getCenter().getLon()),
	    		new GoogleMapPoint(gmaps.getCenter().getLat(),gmaps.getCenter().getLon()-1),
	    		new GoogleMapPoint(gmaps.getCenter().getLat()-1,gmaps.getCenter().getLon())));
            gmp.addClickListener(ev->Notification.show("polygon clicked"));
            Button center = new Button("Show Coordinates", ev-> {
            	Notification.show("Center coordinates: " + gmaps.getCenter());
            });
            Button addMarker = new Button("Add Marker", ev-> {
            	gmaps.addMarker("New Marker", gmaps.getCenter(), true, "");
            });
            Button addPoint = new Button("Add Polygon", ev-> {
            	gmaps.addPolygon(Arrays.asList(new GoogleMapPoint(gmaps.getCenter()),
            			new GoogleMapPoint(gmaps.getCenter().getLat(),gmaps.getCenter().getLon()+1),
            			new GoogleMapPoint(gmaps.getCenter().getLat()+1,gmaps.getCenter().getLon())));
            });
            add(gmaps, new HorizontalLayout(center, addMarker, addPoint));
    	}

    }
}
