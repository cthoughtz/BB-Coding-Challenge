package com.example.hp.backbasecodingchallenge.model;

import android.nfc.FormatException;

import org.json.JSONException;
import org.json.JSONObject;

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

    public City(String country, String name, int id, int lat, int lon) {
        this.country = country;
        this.name = name;
        this.id = id;
        this.coordinate = new Coordinate(lat, lon);
    }

    public static City parse(String input) throws FormatException {
        try {
            JSONObject jsonObject = new JSONObject(input);
            return parse(jsonObject);
        }
        catch (JSONException je) {
            throw new FormatException(je.getMessage());
        }
    }

    public static City parse(JSONObject input) throws FormatException {
        try {
            String country = input.getString("country");
            String name = input.getString("city");
            int id = input.getInt("_id");
            Coordinate coordinate = Coordinate.parse(input.getJSONObject("coord"));
            return new City(country, name, id, coordinate);
        }
        catch (JSONException je) {
            throw new FormatException(je.getMessage());
        }
    }

    @Override
    public int compareTo(City o) {
        int cmp = this.name.compareTo(o.name);
        return cmp == 0 ? this.country.compareTo(o.country) : cmp;
    }
}
