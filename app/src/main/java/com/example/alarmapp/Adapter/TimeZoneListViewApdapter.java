package com.example.alarmapp.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.alarmapp.Model.Time;
import com.example.alarmapp.R;
import com.example.alarmapp.SQLiteController;

import java.util.ArrayList;

public class TimeZoneListViewApdapter extends BaseAdapter {
    ArrayList<Time> listTimeZone;
    SQLiteController sqLiteController;

    public TimeZoneListViewApdapter(ArrayList<Time> listTimeZone, SQLiteController sqLiteController) {
        this.listTimeZone = listTimeZone;
        this.sqLiteController = sqLiteController;
    }

    @Override
    public int getCount() {
        return listTimeZone.size();
    }

    @Override
    public Object getItem(int position) {
        return listTimeZone.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listTimeZone.get(position).getCityID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewTimeZone;
        if (convertView == null) {
            viewTimeZone = View.inflate(parent.getContext(), R.layout.item_world_time, null);
        } else viewTimeZone = convertView;
        Button btn_delete_World_Time;
        btn_delete_World_Time = viewTimeZone.findViewById(R.id.btn_delete_World_Time);
        btn_delete_World_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listTimeZone.remove(position);
                notifyDataSetChanged();
            }
        });
        Time product = (Time) getItem(position);
        ((TextView) viewTimeZone.findViewById(R.id.txt_City)).setText(String.format("%s", product.getCityName()));
        ((TextView) viewTimeZone.findViewById(R.id.txt_Country)).setText(String.format("%s", product.getCountry()));
        ((TextView) viewTimeZone.findViewById(R.id.txt_Time_Zone)).setText(String.format("%s", product.getTimeZone()));
        ((TextView) viewTimeZone.findViewById(R.id.txt_WorldTime)).setText(String.format("%s", product.getTime()));
        return viewTimeZone;
    }
}
