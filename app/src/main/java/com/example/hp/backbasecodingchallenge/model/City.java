package com.example.hp.backbasecodingchallenge.model;

public class City implements Comparable<City>{
    String country;
    String name;
    int id;
    Coordinate coordinate;

    public City(String country, String name, int id, Coordinate coordinate) {
        this.country = country;
        this.name = name;
        this.id = id;
        this.coordinate = coordinate;
    }

    @Override
    public int compareTo(City o) {
        int cmp = this.name.compareTo(o.name);
        return cmp == 0 ? this.country.compareTo(o.country) : cmp;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
