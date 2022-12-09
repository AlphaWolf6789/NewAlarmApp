package com.example.alarmapp.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.alarmapp.Model.City;
import com.example.alarmapp.R;

import java.util.ArrayList;

public class CityListViewAdapter extends BaseAdapter implements Filterable {
    ArrayList<City> listCity;
    ArrayList<City> listCityOld;

    public CityListViewAdapter(ArrayList<City> listCity) {
        this.listCity = listCity;
        this.listCityOld = listCity;
    }

    @Override
    public int getCount() {
        return listCity.size();
    }

    @Override
    public Object getItem(int position) {
        return listCity.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listCity.get(position).getCityID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewCity;
        if (convertView == null) {
            viewCity = View.inflate(parent.getContext(), R.layout.item_time_zone, null);
        } else viewCity = convertView;

        City product = (City) getItem(position);
        ((TextView) viewCity.findViewById(R.id.city)).setText(String.format("%s", product.getCityName()));
        ((TextView) viewCity.findViewById(R.id.country)).setText(String.format("%s", product.getCountry()));
        ((TextView) viewCity.findViewById(R.id.time_zone)).setText(String.format("%s", product.getTimeZone()));

        return viewCity;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()){
                    listCity = listCityOld;
                }
                else {
                    ArrayList<City> filteredList = new ArrayList<>();
                    for(City city : listCityOld){
                        if (city.getCityName().toLowerCase().contains(charString.toLowerCase()) ||
                                city.getCountry().toLowerCase().contains(charString)){
                            filteredList.add(city);
                        }
                    }
                    listCity = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listCity;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listCity = (ArrayList<City>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
