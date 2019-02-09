package com.example.hp.backbasecodingchallenge.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.hp.backbasecodingchallenge.model.City;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityListItemViewHolder> implements Filterable {

    private List<City> data;
    private List<City> filteredData;
    private View.OnClickListener onClickListener;
    private ItemFilter itemFilter;

    public CityListAdapter(List<City> data, View.OnClickListener onClickListener) {
        this.data = data;
        this.filteredData =data;
        this.onClickListener = onClickListener;
        this.itemFilter = new ItemFilter(data);
    }

    @NonNull
    @Override
    public CityListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new CityListItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.two_line_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CityListItemViewHolder viewHolder, int position) {
        City city = filteredData.get(position);
        viewHolder.cityNameTextView.setText(city.getName());
        viewHolder.cityCountryTextView.setText(city.getCountry());
        viewHolder.itemView.setOnClickListener(onClickListener);
        viewHolder.itemView.setTag(city);
    }

    @Override
    public int getItemCount() {
        if(filteredData == null)
            return 0;
        return filteredData.size();
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    public  class CityListItemViewHolder extends RecyclerView.ViewHolder {
        public TextView cityNameTextView;
        public TextView cityCountryTextView;
        public CityListItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cityNameTextView = itemView.findViewById(android.R.id.text1);
            cityCountryTextView = itemView.findViewById(android.R.id.text2);
        }
    }

    public class ItemFilter extends Filter {
        List<City> list;

        ItemFilter(List<City> list) {
            this.list = list;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<City> filteredList = new ArrayList<>();

            String searchTerm = constraint.toString().toLowerCase();

            int position = Collections.binarySearch(list, new City("", searchTerm, -1, null), new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    if(o1 instanceof City && o2 instanceof City) {
                        return ((City)o1).getName().compareToIgnoreCase(((City)o2).getName());
                    }
                    else {
                        return -1;
                    }
                }
            });
            if (position < 0) {
                position = -(position + 1);
            }
            for(int j = position; j < list.size(); j++) {
                if(list.get(j).getName().toLowerCase().startsWith(searchTerm)) {
                    filteredList.add(list.get(j));
                }
                else {
                    break;
                }
            }

            filterResults.values = filteredList;
            filterResults.count = filteredList.size();

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (List<City>) results.values;
            notifyDataSetChanged();
        }
    }
}
