package com.example.hp.backbasecodingchallenge.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hp.backbasecodingchallenge.R;
import com.example.hp.backbasecodingchallenge.model.City;
import com.example.hp.backbasecodingchallenge.model.Coordinate;
import com.example.hp.backbasecodingchallenge.repository.Repository;
import com.example.hp.backbasecodingchallenge.ui.adapter.CityListAdapter;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

public class CityListFragment extends Fragment {

    public static CityListFragment getFragment() {
        return new CityListFragment();
    }

    private CityListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        adapter = new CityListAdapter(Repository.getInstance(view.getContext()).getData(), onItemClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            City city = (City) v.getTag();
            Log.i("test", "city is "+city.getName());
            Log.d("coords", "city is: "+city.getCoordinate().getLat()+ " "+ city.getCoordinate().getLon());

            Mapbox.getInstance(getActivity(),"pk.eyJ1IjoiY3Rob3VnaHR6IiwiYSI6ImNqcnZuZGFhbjAzdzM0OW96cnN3cjRvMTIifQ.715lP84Vkxrkirfph_4rwg");

            SupportMapFragment mapFragment;
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

            MapboxMapOptions options = new MapboxMapOptions();
            options.camera(new CameraPosition.Builder()
                    .target(new LatLng(city.getCoordinate().getLat(),city.getCoordinate().getLon()))
                    .zoom(11)
                    .build());

            mapFragment = SupportMapFragment.newInstance(options);
            fragmentTransaction.replace(R.id.fragment_container,mapFragment);
            fragmentTransaction.commit();

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull MapboxMap mapboxMap) {
                    mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {

                        }
                    });
                }
            });
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem menuItemSearch = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItemSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(adapter != null) {
                    adapter.getFilter().filter(s);
                    return true;
                }
                return false;
            }
        });
    }
}
