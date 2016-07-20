package com.example.jaineek.meeplemain.model;

import android.location.Location;

/**
 * Created by Krishnak97 on 7/19/2016.
 */

public class MeepleLocation extends Location {
    // Merely for Firebase purposes

    public double latitude;
    public double longitude;

    public MeepleLocation() {
        // Default empty constructor for FireBase
        super("");
    }

    public MeepleLocation(String provider) {
        super(provider);
    }

    public MeepleLocation(Location l) {
        super(l);
        latitude = l.getLatitude();
        longitude = l.getLongitude();
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

    @Override
    public String toString() {
        return "Lat: " + getLatitude() +
                " Lon: " + getLongitude();
    }
}
