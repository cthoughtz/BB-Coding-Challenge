package com.example.hp.backbasecodingchallenge.repository;

import android.content.Context;
import android.util.JsonReader;

import com.example.hp.backbasecodingchallenge.model.City;
import com.example.hp.backbasecodingchallenge.model.Coordinate;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
class CityJsonReader {

    void readJson(Context context, Collection<City> collection) {
        JsonReader reader = null;
        try {
            reader = new JsonReader(
                    new InputStreamReader(context.getAssets().open("cities.json"), "UTF-8"));

            reader.beginArray();
            while(reader.hasNext()) {
                collection.add(readCity(reader));
            }
            reader.endArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private City readCity(JsonReader reader) throws IOException {
        String country = null;
        String name = null;
        int id = -1;
        Coordinate location = null;
        reader.beginObject();
        while(reader.hasNext()) {
            String key = reader.nextName();
            if(key.equals("country")) {
                country = reader.nextString();
            }
            else if(key.equals("name")){
                name = reader.nextString();
            }
            else if(key.equals("_id")) {
                id = reader.nextInt();
            }
            else if(key.equals("coord")) {
                location = readLocation(reader);
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new City(country, name, id, location);
    }

    private Coordinate readLocation(JsonReader reader) throws IOException {
        double lat = -1.0;
        double lon = -1.0;
        reader.beginObject();
        while(reader.hasNext()) {
            String key = reader.nextName();
            if(key.equals("lat")) {
                lat = reader.nextDouble();
            }
            else if(key.equals("lon")) {
                lon = reader.nextDouble();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Coordinate(lat, lon);
    }
}
