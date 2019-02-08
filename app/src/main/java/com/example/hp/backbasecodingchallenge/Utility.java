package com.example.hp.backbasecodingchallenge;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Utility {
    public static String readAssetFile(Context context, String filename) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            return sb.toString();
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
        return null;
    }

    public static <T> List<T> setToList(Set<T> input) {
        ArrayList<T> list = new ArrayList<T>(input.size());
        for(T item: input) {
            list.add(item);
        }
        return list;
    }
}
