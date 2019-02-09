package com.example.hp.backbasecodingchallenge.repository;

import android.content.Context;

import com.example.hp.backbasecodingchallenge.model.City;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Repository {

    private static Repository sInstance;

    private Set<City> mData;

    private Repository(Context context){
        mData = new TreeSet<City>(new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                int cmp =  o1.getName().compareTo(o2.getName());
                if(cmp == 0) {
                    cmp = (o1.getId() < o2.getId()) ? -1 : ((o1.getId() == o2.getId()) ? 0 : 1);
                }
                return cmp;
            }
        });

        CityJsonReader cityJsonReader = new CityJsonReader();
        cityJsonReader.readJson(context, mData);
    }

    public static  Repository getInstance(Context context){
        if(sInstance == null) {
            sInstance = new Repository(context);
        }

        return sInstance;
    }

    public List<City> getData() {
        return new ArrayList<>(mData);
    }
}
