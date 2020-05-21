package com.flowingcode.vaadin.addons;

import java.util.Arrays;

import com.flowingcode.vaadin.addons.GoogleMap.MapType;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class View extends VerticalLayout {

    public View() {
    	this.setSizeFull();
    	String apiKey = System.getProperty("google.maps.api");
    	if (apiKey==null) {
    		add(new H2("Api key is needded to run the demo, pass it using the following system property: '-Dgoogle.maps.api=<your-api-key>'"));
    	} else {
            GoogleMap gmaps = new GoogleMap(apiKey,null,null);
            gmaps.setMapType(MapType.SATELLITE);
            gmaps.setSizeFull();
            gmaps.setCenter(new LatLon(0,0));
            gmaps.addMarker("Center", new LatLon(0,0), true, "");
            GoogleMapPolygon gmp = gmaps.addPolygon(Arrays.asList(new GoogleMapPoint(gmaps.getCenter()),
    		new GoogleMapPoint(gmaps.getCenter().getLat(),gmaps.getCenter().getLon()+1),
    		new GoogleMapPoint(gmaps.getCenter().getLat()+1,gmaps.getCenter().getLon())));
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
