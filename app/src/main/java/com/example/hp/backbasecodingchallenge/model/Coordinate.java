package com.example.hp.backbasecodingchallenge.model;

import android.nfc.FormatException;

import org.json.JSONException;
import org.json.JSONObject;

public class Coordinate {
    double lat;
    double lon;

    public Coordinate(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public static Coordinate parse(String input) throws FormatException {
        try {
            JSONObject jsonObject = new JSONObject(input);
            return parse(jsonObject);
        }
        catch (JSONException je) {
            throw new FormatException(je.getMessage());
        }

    }

    public static Coordinate parse(JSONObject input) throws FormatException {
        try {
            double lat = input.getDouble("lat");
            double lon = input.getDouble("lon");
            return new Coordinate(lat, lon);
        }
        catch (JSONException je) {
            throw new FormatException(je.getMessage());
        }
    }
}
