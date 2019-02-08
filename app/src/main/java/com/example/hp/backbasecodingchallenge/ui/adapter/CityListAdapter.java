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
import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityListItemViewHolder> implements Filterable {

    private List<City> data;
    private List<City> filteredData;
    private View.OnClickListener onClickListener;
    private ItemFilter itemFilter;

    public CityListAdapter(List<City> data, View.OnClickListener onClickListener) {
        this.data = data;
        this.filteredData = data;
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

        public ItemFilter(List<City> list) {
            this.list = list;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<City> filteredList = new ArrayList<>();

            String filterString = constraint.toString().toLowerCase();
            for(City city : list) {
                if(city.getName().toLowerCase().startsWith(filterString)) {
                    filteredList.add(city);
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
