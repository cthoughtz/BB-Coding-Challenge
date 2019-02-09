package com.example.hp.backbasecodingchallenge.model;

public class Coordinate {
    double lat;
    double lon;

    public Coordinate(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
