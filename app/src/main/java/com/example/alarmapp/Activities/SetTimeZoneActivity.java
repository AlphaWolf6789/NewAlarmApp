package com.example.alarmapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.alarmapp.Adapter.CityListViewAdapter;
import com.example.alarmapp.Fragment.WorldClockFragment;
import com.example.alarmapp.Model.City;
import com.example.alarmapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidx.appcompat.app.AppCompatActivity;

public class SetTimeZoneActivity extends AppCompatActivity {
    CityListViewAdapter cityListViewAdapter;
    ArrayList<City> listCity;
    ListView listViewCity;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_time_zone);

        listCity = new ArrayList<>();
        listCity.add(new City(1, "London", "England", "GMT+1:00"));
        listCity.add(new City(2, "Hanoi", "Vietnam", "GMT+7:00"));
        listCity.add(new City(3, "Bangkok", "Thailand", "GMT+7:00"));
        listCity.add(new City(4, "Beijing", "China", "GMT+8:00"));
        listCity.add(new City(5, "Amsterdam", "Netherlands", "GMT+2:00"));
        listCity.add(new City(6, "Atlanta", "United States", "GMT-4:00"));
        listCity.add(new City(7, "Moscow", "Russia", "GMT+3:00"));
        listCity.add(new City(8, "Barcelona", "Spain", "GMT+2:00"));
        listCity.add(new City(9, "Manchester", "England", "GMT+1:00"));
        listCity.add(new City(10, "Jakarta", "Indonesia", "GMT+7:00"));
        listCity.add(new City(11, "Manila", "Philippines", "GMT+8:00"));
        listCity.add(new City(12, "Phnom Penh", "Cambodia", "GMT+7:00"));
        listCity.add(new City(13, "Madrid", "Spain", "GMT+2:00"));
        listCity.add(new City(14, "Madison", "United States", "GMT-5:00"));
        listCity.add(new City(15, "Berlin", "Germany", "GMT+2:00"));
        listCity.add(new City(16, "Brussels", "Belgium", "GMT+2:00"));
        listCity.add(new City(17, "Cambridge", "England", "GMT+1:00"));
        listCity.add(new City(18, "Cario", "Egypt", "GMT+2:00"));
        listCity.add(new City(19, "Delhi", "India", "GMT+5:30"));
        listCity.add(new City(20, "Frankfurt", "Germany", "GMT+2:00"));
        listCity.add(new City(21, "HoChiMinh", "Vietnam", "GMT+7:00"));
        listCity.add(new City(22, "Kyiv", "Ukraine", "GMT+3:00"));
        listCity.add(new City(23, "Lisbon", "Portugal", "GMT+1:00"));
        listCity.add(new City(24, "Los Angeles", "United States", "GMT-7:00"));
        listCity.add(new City(25, "Mexico City", "Mexico", "GMT-5:00"));
        listCity.add(new City(26, "Milan", "Italia", "GMT+2:00"));
        listCity.add(new City(27, "Monaco", "Monaco", "GMT+2:00"));
        listCity.add(new City(28, "Osaka", "Japan", "GMT+9:00"));
        listCity.add(new City(29, "Rio de Janero", "Brazil", "GMT-3:00"));
        listCity.add(new City(30, "Seoul", "Korea", "GMT+9:00"));
        listCity.add(new City(31, "Paris", "France", "GMT+2:00"));
        listCity.add(new City(32, "Singapore", "Singapore", "GMT+8:00"));
        listCity.add(new City(33, "Queensland", "Australia", "GMT+10:00"));
        listCity.add(new City(34, "Roma", "Italia", "GMT+2:30"));
        listCity.add(new City(35, "Tokyo", "Japan", "GMT+9:00"));
        listCity.add(new City(36, "Vatican", "Vatican", "GMT+2:00"));
        listCity.add(new City(37, "Caracas", "Venezuela", "GMT-4:00"));
        listCity.add(new City(38, "Calgary", "Canada", "GMT-6:00"));
        listCity.add(new City(39, "Cape Town", "South Africa", "GMT-7:00"));
        listCity.add(new City(40, "Havana", "Cuba", "GMT-4:00"));
        Collections.sort(listCity, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                return o1.getCityName().compareTo(o2.getCityName());
            }
        });
        cityListViewAdapter = new CityListViewAdapter(listCity);

        editText = (EditText) findViewById(R.id.input_search);
        listViewCity = findViewById(R.id.list_view);
        listViewCity.setAdapter(cityListViewAdapter);

        listViewCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = (City) parent.getAdapter().getItem(position);
                Intent intent = new Intent(SetTimeZoneActivity.this, WorldClockFragment.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("city_object_fr",city);
                intent.putExtras(bundle);
                setResult(1000, intent);
                finish();
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SetTimeZoneActivity.this.cityListViewAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
