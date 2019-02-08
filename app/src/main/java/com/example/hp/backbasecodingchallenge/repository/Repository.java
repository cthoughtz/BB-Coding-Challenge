package com.example.hp.backbasecodingchallenge.repository;

import android.content.Context;

import com.example.hp.backbasecodingchallenge.Utility;
import com.example.hp.backbasecodingchallenge.model.City;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Repository {

    private static Repository sInstance;

    private Set<City> mData;

    private Repository(Context context){
        String flattenCitiesJsonArray = Utility.readAssetFile(context, "cities.json");
        mData = new TreeSet<>();
        try {
            JSONArray jsonArray  = new JSONArray(flattenCitiesJsonArray);
            for(int i = 0; i < jsonArray.length();i++) {
                mData.add(City.parse(jsonArray.getJSONObject(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  Repository getInstance(Context context){
        if(sInstance == null) {
            sInstance = new Repository(context);
        }

        return sInstance;
    }

    public Set<City> getData() {
        return mData;
    }
}
