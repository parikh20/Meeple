package com.example.jaineek.meeplemain.model;

import android.location.Location;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Krishnak97 on 7/19/2016.
 */

public class MeepleLocation extends Location {
    // Merely for Firebase purposes

    public double latitude;
    public double longitude;

    public String address;

    public String name;

    public MeepleLocation() {
        // Default empty constructor for FireBase
        super("");
    }

    // TODO: remove this shit
    public MeepleLocation(String provider) {
        super(provider);
    }

    public MeepleLocation(Location l) {
        // Constructor for user location
        super(l);
        setLatitude(l.getLatitude());
        setLongitude(l.getLongitude());
        setName("User Location");
        // address is null
    }

    public MeepleLocation(Place place) {
        super("");
        // Constructor for autocomplete Place

        LatLng latLng = place.getLatLng();
        setLatitude(latLng.latitude);
        setLongitude(latLng.longitude);
        setName(place.getName().toString());
        setAddress(place.getAddress().toString());
        setName(place.getName().toString());
    }

    /* GETTERS AND SETTERS */

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(double lat) {
        latitude = lat;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(double lon) {
        longitude = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        // Display's address of event
        if (address == null) {
            return name + ": " + getLatitude() + ", " + getLongitude();
        } else if (address.toLowerCase().contains(name.toLowerCase())) {
            // Check for redundancy in address
            return address;
        } else {
            return name + ":  " + address;
        }
    }
}
